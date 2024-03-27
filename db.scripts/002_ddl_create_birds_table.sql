CREATE TABLE IF NOT EXISTS birds
(
    id   SERIAL PRIMARY KEY,
    name varchar not null unique,
    flight_speed varchar not null unique
);