CREATE TABLE IF NOT EXISTS dogs
(
    id   SERIAL PRIMARY KEY,
    name varchar not null unique,
    run_speed varchar not null unique
);