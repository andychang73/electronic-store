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
    stock INT NOT NULL,
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

INSERT INTO product(type, name, price, stock) VALUES('COMPUTER','Mac',50000.00,100);
INSERT INTO product(type, name, price, stock) VALUES('MOBILE_PHONE','iPhone',5000.00,1);
INSERT INTO product(type, name, price, stock) VALUES('TABLET','iPad',6000.00,0);

INSERT INTO deal(name, type, policy, apply_order, stackable) VALUES('Buy one get second one 50% off','BUY_ONE_GET_ONE_DISCOUNT','{"productId":1,"discount":0.5}', 1, true);