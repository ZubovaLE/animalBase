CREATE TABLE IF NOT EXISTS dogs
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    run_speed VARCHAR(100) NOT NULL
);