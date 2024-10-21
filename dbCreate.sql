CREATE DATABASE ecommerce;
\c
--Creacion tabla cliente
CREATE TABLE cliente(
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    email VARCHAR(100),
    telefono VARCHAR(20)
);

--Crear tabla categoria
CREATE TABLE categoria (
	id_categoria SERIAL PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL
);

--Crear tabla producto
CREATE TABLE producto (
	id_producto SERIAL PRIMARY KEY,
	nombre VARCHAR(255) NOT NULL,
	descripcion TEXT,
	precio DECIMAL(10, 2),
	stock INT NOT NULL,
	estado VARCHAR(50) NOT NULL,
	id_categoria INTEGER,
	FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria)
);


