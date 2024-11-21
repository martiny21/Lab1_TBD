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
    estate VARCHAR(50) NOT NULL CHECK (estate IN ('disponible', 'agotado')),
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

-- Crear tabla orden
CREATE TABLE order_info (
    order_id SERIAL PRIMARY KEY,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estate VARCHAR(50) NOT NULL CHECK (estate IN ('pendiente', 'pagada', 'enviada')),
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

CREATE TRIGGER insertion_trigger
AFTER INSERT OR UPDATE OR DELETE
ON category
FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function();

CREATE TRIGGER insertion_trigger
AFTER INSERT OR UPDATE OR DELETE
ON product
FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function();

CREATE TRIGGER insertion_trigger
AFTER INSERT OR UPDATE OR DELETE
ON client
FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function();

CREATE TRIGGER insertion_trigger
AFTER INSERT OR UPDATE OR DELETE
ON order_info
FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function();

CREATE TRIGGER insertion_trigger
AFTER INSERT OR UPDATE OR DELETE
ON order_detail
FOR EACH ROW
EXECUTE FUNCTION insertion_trigger_function();

CREATE TRIGGER verify_reciently_shop_trigger
AFTER INSERT ON order_info
FOR EACH ROW
EXECUTE FUNCTION verify_reciently_shop();