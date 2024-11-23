-- Crear tabla categoría
CREATE TABLE category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

-- Crear tabla producto
CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_desc TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    estate VARCHAR(50) NOT NULL CHECK (
        estate IN ('disponible', 'agotado')
    ),
    category_id INTEGER NOT NULL REFERENCES category (category_id)
);

-- Crear tabla cliente
CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    direction VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    client_number VARCHAR(20),
    client_password VARCHAR(100) UNIQUE NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY, -- Identificador único para cada registro
    table_name VARCHAR(255) NOT NULL, -- Nombre de la tabla afectada
    operation_type VARCHAR(50) NOT NULL, -- Tipo de operación: INSERT, UPDATE, DELETE
    changed_data JSONB, -- Datos cambiados en formato JSON (opcional)
    user_email VARCHAR(255), -- Usuario que realizó la operación (puedes enlazarlo con la tabla de usuarios)
    operation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha y hora de la operación
    client_id INT, -- ID del cliente afectado (si aplica)
    ip_address VARCHAR(45) -- Dirección IP desde donde se realizó la operación (IPv4 o IPv6)
);

-- Crear tabla orden
CREATE TABLE order_info (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estate VARCHAR(50) NOT NULL CHECK (
        estate IN (
            'pendiente',
            'pagada',
            'enviada'
        )
    ),
    client_id INTEGER NOT NULL REFERENCES client (client_id),
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0)
);

-- Crear tabla detalle_orden
CREATE TABLE order_detail (
    detail_id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES order_info (order_id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES product (product_id),
    amount INT NOT NULL CHECK (amount > 0),
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0)
);

-- Crear tabla bitacora de inserción
CREATE TABLE insertion_log (
    insertion_id SERIAL PRIMARY KEY,
    table_name_affected TEXT NOT NULL,
    operation_type TEXT NOT NULL,
    client_id INTEGER NOT NULL REFERENCES client (client_id),
    client_name VARCHAR(255),
    email TEXT,
    query_time TIMESTAMP DEFAULT NOW(),
    old_data JSONB,
    new_data JSONB
);

CREATE TABLE shop_alerts (
    alert_id SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL REFERENCES client (client_id),
    alert_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    alert_desc TEXT NOT NULL
);

-- Crear la tabla problematic_order
CREATE TABLE problematic_order (
    order_id INTEGER PRIMARY KEY REFERENCES order_info (order_id) ON DELETE CASCADE,
    stock_issues_count INT NOT NULL DEFAULT 0 CHECK (stock_issues_count >= 0)
);

--creamos la tabla de devoluciones
CREATE TABLE returns (
    return_id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES order_info (order_id) ON DELETE CASCADE,
    product_id INTEGER NOT NULL REFERENCES product (product_id),
    return_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount INT NOT NULL CHECK (amount > 0),
    reason TEXT
);

CREATE OR REPLACE FUNCTION insertion_trigger_function()
RETURNS TRIGGER AS $$
DECLARE
    v_client_id INT;
    v_client_name TEXT;
    v_email TEXT;
BEGIN

    -- Estos datos deben ser enviados por la aplicación al realizar una consulta.
    v_client_id := current_setting('app.client_id', true)::INT;
    v_client_name := current_setting('app.client_name', true);
    v_email := current_setting('app.email', true);

    INSERT INTO audit_log(
        table_name,
        operation_type,
        client_id,
        client_name,
        email,
        query_time,
        old_data,
        new_data
    )
    VALUES (
        TG_TABLE_NAME, -- Nombre de la tabla afectada
        TG_OP,         -- Tipo de operación (INSERT, UPDATE, DELETE)
        v_client_id,
        v_client_name,
        v_email,
        NOW(),
        CASE WHEN TG_OP = 'DELETE' THEN row_to_json(OLD) ELSE NULL END, -- Datos antiguos
        CASE WHEN TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN row_to_json(NEW) ELSE NULL END -- Datos nuevos
    );

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_client_query_report()
RETURNS TABLE(client_id INTEGER, query_count INTEGER) AS $$
BEGIN
    RETURN QUERY
    SELECT client_id, COUNT(*) AS query_count
    FROM insertion_log
    WHERE operation_type IN ('INSERT', 'UPDATE', 'DELETE')
    GROUP BY client_id
    ORDER BY query_count DESC;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION verify_reciently_shop()
RETURNS TRIGGER AS $$
DECLARE
    shops_in_24 INT;
BEGIN
    -- Contar las compras realizadas por el cliente en las últimas 24 horas
    SELECT COUNT(*)
    INTO shops_in_24
    FROM order_info
    WHERE client_id = NEW.client_id
      AND order_date >= NOW() - INTERVAL '24 hours';

    -- Si el cliente tiene más de una compra en las últimas 24 horas, registrar una alerta
    IF shops_in_24 > 1 THEN
        INSERT INTO shop_alerts (client_id, alert_desc)
        VALUES (
            NEW.client_id,
            'El cliente con ID ' || NEW.client_id ||
            ' ha realizado más de una compra en las últimas 24 horas.'
        );
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insertion_trigger AFTER
INSERT
    OR
UPDATE
OR DELETE ON category FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function ();

CREATE TRIGGER insertion_trigger AFTER
INSERT
    OR
UPDATE
OR DELETE ON product FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function ();

CREATE TRIGGER insertion_trigger AFTER
INSERT
    OR
UPDATE
OR DELETE ON client FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function ();

CREATE TRIGGER insertion_trigger AFTER
INSERT
    OR
UPDATE
OR DELETE ON order_info FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function ();

CREATE TRIGGER insertion_trigger AFTER
INSERT
    OR
UPDATE
OR DELETE ON order_detail FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function ();

CREATE TRIGGER verify_reciently_shop_trigger AFTER
INSERT
    ON order_info FOR EACH ROW
EXECUTE FUNCTION verify_reciently_shop ();

--trigger para actualizar stock
CREATE OR REPLACE FUNCTION update_stock_on_return()
RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar el stock del producto basado en la devolución registrada
    UPDATE product
    SET stock = stock + NEW.amount
    WHERE product_id = NEW.product_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--crear el trigger
CREATE TRIGGER update_stock_trigger AFTER
INSERT
    ON returns FOR EACH ROW
EXECUTE FUNCTION update_stock_on_return ();

--query ¿Qué porcentaje de las órdenes de cada cliente ha tenido problemas de stock (algún producto en la orden no estaba disponible al momento de la compra)?

-- Crear la función para el trigger
CREATE OR REPLACE FUNCTION update_problematic_order_on_stock_issue()
RETURNS TRIGGER AS $$
DECLARE
    current_issues_count INT;
BEGIN
    -- Verificar si el producto tiene un problema de stock
    IF NEW.amount > (SELECT stock FROM product WHERE product_id = NEW.product_id) THEN
        -- Obtener el conteo actual de problemas de stock para la orden
        SELECT stock_issues_count
        INTO current_issues_count
        FROM problematic_order
        WHERE order_id = NEW.order_id;

        -- Si la orden ya existe en `problematic_order`, incrementar el conteo
        IF FOUND THEN
            UPDATE problematic_order
            SET stock_issues_count = current_issues_count + 1
            WHERE order_id = NEW.order_id;
        ELSE
            -- Si no existe, insertar una nueva entrada con 1 problema de stock
            INSERT INTO problematic_order (order_id, stock_issues_count)
            VALUES (NEW.order_id, 1);
        END IF;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger
CREATE TRIGGER trigger_add_problematic_order AFTER
INSERT
    ON order_detail FOR EACH ROW
EXECUTE FUNCTION update_problematic_order_on_stock_issue ();

CREATE OR REPLACE FUNCTION update_stock_and_order_total()
RETURNS TRIGGER AS $$
BEGIN
    -- Reducir el stock del producto
    UPDATE product
    SET stock = stock - NEW.amount
    WHERE product_id = NEW.product_id;

    -- Verificar que el stock no sea negativo
    IF (SELECT stock FROM product WHERE product_id = NEW.product_id) < 0 THEN
        RAISE EXCEPTION 'No hay suficiente stock para el producto con ID %', NEW.product_id;
    END IF;

    -- Actualizar el total de la orden
    UPDATE order_info
    SET total = total + (NEW.amount * NEW.unit_price)
    WHERE order_id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger que llama a la función después de insertar en order_detail
CREATE TRIGGER update_stock_and_total_trigger AFTER
INSERT
    ON order_detail FOR EACH ROW
EXECUTE FUNCTION update_stock_and_order_total ();