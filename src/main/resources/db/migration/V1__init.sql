CREATE TABLE IF NOT EXISTS customer(
    id INT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS admin(
    id INT PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(32) NOT NULL,
    name VARCHAR(128) NOT NULL UNIQUE,
    price DECIMAL(21,4) NOT NULL,
    status VARCHAR(21) DEFAULT 'AVAILABLE'
);

CREATE TABLE IF NOT EXISTS deal(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(64) NOT NULL,
    policy VARCHAR(255) NOT NULL,
    apply_order INT NOT NULL UNIQUE,
    stackable BOOLEAN NOT NULL,
    CONSTRAINT idx_name_type UNIQUE(name, type)
);

INSERT INTO admin(id, name, password) VALUES(1, 'admin','$2a$10$FFl5kHqw3fIOdbTuHppmKOf1C7uVyqW.tWokYHSboyh3l3UZuKPd.');

INSERT INTO product(type, name, price) VALUES('COMPUTER','computer',10000.00);

INSERT INTO deal(name, type, policy, apply_order, stackable) VALUES('deal','BUY_ONE_GET_ONE_DISCOUNT','test test test', 1, true);