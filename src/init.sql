
USE hotel;

-- Crear la tabla huespedes
CREATE TABLE huespedes (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(100) NOT NULL,
                           apellido VARCHAR(100) NOT NULL,
                           fecha_nacimiento DATE NOT NULL,
                           nacionalidad VARCHAR(50) NOT NULL,
                           telefono VARCHAR(20) NOT NULL
);

-- Crear la tabla reservas
CREATE TABLE reservas (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          huesped_id INT NOT NULL,
                          fecha_ingreso DATE NOT NULL,
                          fecha_egreso DATE NOT NULL,
                          valor_total DECIMAL(10, 2) NOT NULL,
                          metodo_pago VARCHAR(50) NOT NULL,
                          FOREIGN KEY (huesped_id) REFERENCES huespedes(id)
);
