-- Inserción de la Kawasaki Ninja 400
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Kawasaki', 'Ninja 400', 'KW4001', 5500000, 2023, 'Verde', 1500, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'KW4001');

-- Inserción de la Yamaha R6
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Yamaha', 'YZF-R6', 'YM6002', 12000000, 2022, 'Azul', 4500, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'YM6002');

-- Inserción de la Honda CBR500R
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Honda', 'CBR500R', 'HD5003', 6200000, 2024, 'Rojo', 800, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'HD5003');

-- Inserción de la Suzuki Gixxer 150
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Suzuki', 'Gixxer 150', 'SZ1504', 2400000, 2021, 'Negro', 12000, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'SZ1504');

-- Inserción de la Ducati Monster 821
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Ducati', 'Monster 821', 'DC8215', 9800000, 2020, 'Rojo', 8500, false
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'DC8215');

-- Inserción de la BMW G310 GS
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'BMW', 'G310 GS', 'BM3106', 5800000, 2023, 'Gris', 3200, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'BM3106');

-- Inserción de la KTM Duke 390
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'KTM', 'Duke 390', 'KT3907', 4900000, 2022, 'Naranja', 6000, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'KT3907');

-- Inserción de la Harley-Davidson Iron 883
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Harley-Davidson', 'Iron 883', 'HD8838', 11500000, 2019, 'Negro Mate', 14000, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'HD8838');

-- Inserción de la Triumph Tiger 900
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Triumph', 'Tiger 900', 'TR9009', 13500000, 2023, 'Blanco', 2100, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'TR9009');

-- Inserción de la Vespa Primavera 150
INSERT INTO bike (marca, modelo, patente, valor, annio, color, kilometraje, disponibilidad)
SELECT 'Vespa', 'Primavera 150', 'VS1500', 3800000, 2024, 'Celeste', 100, true
WHERE NOT EXISTS (SELECT 1 FROM bike WHERE patente = 'VS1500');