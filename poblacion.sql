-- Insertar categorías
INSERT INTO
    category (category_name)
VALUES ('Electrónica'),
    ('Hogar'),
    ('Deportes'),
    ('Juguetes'),
    ('Libros');

-- Insertar productos
INSERT INTO
    product (
        product_name,
        product_desc,
        price,
        stock,
        estate,
        category_id
    )
VALUES (
        'Smartphone',
        'Teléfono inteligente con pantalla AMOLED',
        599.99,
        5,
        'disponible',
        1
    ),
    (
        'Laptop',
        'Laptop ultraligera con procesador Intel i7',
        999.99,
        5,
        'disponible',
        1
    ),
    (
        'Televisor 4K',
        'Televisor con resolución 4K Ultra HD',
        699.99,
        5,
        'disponible',
        1
    ),
    (
        'Aspiradora',
        'Aspiradora inalámbrica de alta potencia',
        129.99,
        5,
        'disponible',
        2
    ),
    (
        'Sartén antiadherente',
        'Sartén de 28 cm con recubrimiento antiadherente',
        29.99,
        5,
        'disponible',
        2
    ),
    (
        'Pelota de fútbol',
        'Pelota de fútbol profesional talla 5',
        19.99,
        5,
        'disponible',
        3
    ),
    (
        'Bicicleta',
        'Bicicleta de montaña con 21 velocidades',
        249.99,
        5,
        'disponible',
        3
    ),
    (
        'Puzzle 1000 piezas',
        'Rompecabezas de 1000 piezas con diseño de paisajes',
        14.99,
        5,
        'disponible',
        4
    ),
    (
        'Peluches',
        'Set de 3 peluches pequeños',
        24.99,
        5,
        'disponible',
        4
    ),
    (
        'Novela',
        'Libro de ficción best-seller',
        12.99,
        5,
        'disponible',
        5
    );