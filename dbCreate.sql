CREATE DATABASE ecommerce;
\c
-- Crear tabla categoría
CREATE TABLE category (
    id_category SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Crear tabla producto
CREATE TABLE product (
    id_product SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    desc TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    estate VARCHAR(50) NOT NULL CHECK (estate IN ('disponible', 'agotado')),
    id_category INTEGER NOT NULL REFERENCES category (id_category)
);

-- Crear tabla cliente
CREATE TABLE client (
    id_client SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    direction VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    client_password VARCHAR(100) UNIQUE NOT NULL,
    number VARCHAR(20)
);

-- Crear tabla orden
CREATE TABLE order (
    id_order SERIAL PRIMARY KEY,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estate VARCHAR(50) NOT NULL CHECK (estate IN ('pendiente', 'pagada', 'enviada')),
    id_client INTEGER NOT NULL REFERENCES client (id_client),
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0)
);

-- Crear tabla detalle_orden
CREATE TABLE order_detail (
    id_detail SERIAL PRIMARY KEY,
    id_order INTEGER NOT NULL REFERENCES order (id_order) ON DELETE CASCADE,
    id_product INTEGER NOT NULL REFERENCES product (id_product),
    amount INT NOT NULL CHECK (amount > 0),
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0)
);