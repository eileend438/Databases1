-- Таблица машин
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    price NUMERIC
);

-- Таблица людей
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INTEGER,
    has_license BOOLEAN,
    car_id INTEGER REFERENCES car(id)
);
