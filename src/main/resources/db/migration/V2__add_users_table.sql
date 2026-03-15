CREATE TABLE users (
                           id UUID PRIMARY KEY,
                           email VARCHAR(150) UNIQUE NOT NULL,
                           password VARCHAR(150)  NOT NULL,
                           role VARCHAR(50) NOT NULL
)