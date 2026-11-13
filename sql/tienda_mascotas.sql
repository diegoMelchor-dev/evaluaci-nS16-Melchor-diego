-- Sistema: Tienda de mascotas | Estudiante: Diego Alonso MELCHOR QUINTO
-- (En Aiven u otro servicio con base fija, quita las 2 primeras líneas y usa tu base, ej. defaultdb)
CREATE DATABASE IF NOT EXISTS tienda_mascotas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tienda_mascotas;

DROP TABLE IF EXISTS producto;
CREATE TABLE producto (
    id_producto       INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(100)  NOT NULL,
    categoria         VARCHAR(50)   NOT NULL,
    marca             VARCHAR(50)   NOT NULL,
    especie           VARCHAR(30)   NOT NULL,
    precio            DECIMAL(10,2) NOT NULL,
    stock             INT           NOT NULL DEFAULT 0,
    fecha_vencimiento DATE          NULL
);

INSERT INTO producto (nombre, categoria, marca, especie, precio, stock, fecha_vencimiento) VALUES
('Alimento adulto 15 kg',        'Alimento',   'Dog Chow',   'Perro',   129.90, 25, '2027-06-30'),
('Alimento cachorro 3 kg',       'Alimento',   'Pro Plan',   'Perro',    68.50, 18, '2027-03-15'),
('Alimento gato adulto 1.5 kg',  'Alimento',   'Whiskas',    'Gato',     32.00, 40, '2027-01-20'),
('Arena sanitaria 10 kg',        'Higiene',    'Pet Fresh',  'Gato',     29.90, 30, NULL),
('Shampoo antipulgas 250 ml',    'Higiene',    'Pet Clean',  'Perro',    22.50, 15, '2028-02-10'),
('Collar ajustable M',           'Accesorios', 'PetStyle',   'Perro',    18.00, 50, NULL),
('Rascador para gatos',          'Accesorios', 'MiauHome',   'Gato',     85.00,  8, NULL),
('Semillas mix para canarios',   'Alimento',   'Vitapet',    'Ave',      12.00, 35, '2026-12-31'),
('Pecera 20 L con filtro',       'Accesorios', 'AquaLife',   'Pez',     140.00,  5, NULL),
('Antipulgas pipeta',            'Salud',      'Frontline',  'Perro',    45.00, 20, '2027-08-01');
SELECT * FROM producto;
