DROP DATABASE IF EXISTS demodb;
CREATE DATABASE demodb;
USE demodb;

-- tabla estudiante
DROP TABLE IF EXISTS estudiante;
CREATE TABLE estudiante (
    estudiante_id INT AUTO_INCREMENT,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    jornada VARCHAR(20) NOT NULL,
    estado_estudiante ENUM('ACTIVO', 'INACTIVO', 'GRADUADO', 'PERDIO CALIDAD ESTUDIANTIL') NOT NULL,
    cedula BIGINT NOT NULL UNIQUE,
    PRIMARY KEY(estudiante_id)
);

-- tabla materia
DROP TABLE IF EXISTS materia;
CREATE TABLE materia (
    materia_id INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY(materia_id)
);

DROP TABLE IF EXISTS semestre;
CREATE TABLE semestre (
    semestre_id INT AUTO_INCREMENT,
    estudiante_id INT NOT NULL,
    nivel TINYINT UNSIGNED NOT NULL CHECK (nivel BETWEEN 1 AND 10),
    periodo VARCHAR(10) NOT NULL,
    promedio_periodo DECIMAL(2,1) NOT NULL,
    promedio_final DECIMAL(2,1) NOT NULL,
    cantidad_asignaturas INT UNSIGNED NOT NULL,
    cantidad_creditos INT UNSIGNED NOT NULL,
    PRIMARY KEY(semestre_id),
    FOREIGN KEY(estudiante_id) REFERENCES estudiante(estudiante_id) ON DELETE CASCADE
);
-- tabla historial
CREATE TABLE historial (
    historial_id INT AUTO_INCREMENT,
    estudiante_id INT NOT NULL,
    semestre_id INT NOT NULL,
    materia_id INT NOT NULL,
    nota_materia DECIMAL(2,1),
    estado_materia ENUM('APROBADA', 'PERDIDA', 'HOMOLOGACION', 'EN CURSO', 'NO CURSADA') NOT NULL,
    PRIMARY KEY(historial_id),
    FOREIGN KEY(estudiante_id) REFERENCES estudiante(estudiante_id) ON DELETE CASCADE,
    FOREIGN KEY(semestre_id) REFERENCES semestre(semestre_id) ON DELETE CASCADE,
    FOREIGN KEY(materia_id) REFERENCES materia(materia_id) ON DELETE CASCADE
	);
insert into materia (nombre) values ("Historia");
select * from semestre;