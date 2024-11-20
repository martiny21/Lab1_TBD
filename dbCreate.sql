CREATE DATABASE ecommerce;
\c
-- Crear tabla categoría
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Crear tabla producto
CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    estado VARCHAR(50) NOT NULL CHECK (estate IN ('disponible', 'agotado')),
    id_categoria INTEGER NOT NULL REFERENCES category (id_category)
);

-- Crear tabla cliente
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    email VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    contrasena VARCHAR(100) UNIQUE NOT NULL,
    es_admin BOOLEAN DEFAULT FALSE
);

-- Crear tabla orden
CREATE TABLE orden (
    id_orden SERIAL PRIMARY KEY,
    fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL CHECK (estate IN ('pendiente', 'pagada', 'enviada')),
    id_cliente INTEGER NOT NULL REFERENCES client (id_client),
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0)
);

-- Crear tabla detalle_orden
CREATE TABLE detalle_orden (
    id_detalle SERIAL PRIMARY KEY,
    id_orden INTEGER NOT NULL REFERENCES order_info (id_order) ON DELETE CASCADE,
    id_producto INTEGER NOT NULL REFERENCES product (id_product),
    cantidad INT NOT NULL CHECK (amount > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0)
);