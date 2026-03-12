CREATE TABLE departments (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             description TEXT
);

CREATE TABLE positions (
                           id UUID PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           description TEXT
);
CREATE TABLE employees (
                           id UUID PRIMARY KEY,

                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,

                           email VARCHAR(150) UNIQUE NOT NULL,
                           phone VARCHAR(50),

                           salary NUMERIC(12,2),

                           department_id UUID,
                           position_id UUID,

                           status VARCHAR(30) NOT NULL,

                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_department
                               FOREIGN KEY (department_id)
                                   REFERENCES departments(id),

                           CONSTRAINT fk_position
                               FOREIGN KEY (position_id)
                                   REFERENCES positions(id)
);