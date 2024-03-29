CREATE TABLE IF NOT EXISTS birds
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL unique,
    flight_speed NUMERIC      NOT NULL
);