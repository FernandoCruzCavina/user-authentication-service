CREATE TABLE user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(15) NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    birthday_date BIGINT NOT NULL,
    phone VARCHAR(14) NOT NULL,
    user_role VARCHAR(10) NOT NULL
);