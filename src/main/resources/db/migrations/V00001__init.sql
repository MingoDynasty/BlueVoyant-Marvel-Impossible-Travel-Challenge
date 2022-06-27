CREATE SCHEMA IF NOT EXISTS marvel;
GRANT ALL ON SCHEMA marvel TO PUBLIC;

CREATE TABLE IF NOT EXISTS character
(
    id             INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    marvel_id      INT                                    NOT NULL UNIQUE,
    name           VARCHAR,
    description    VARCHAR,
    thumbnail_path VARCHAR,
    last_retrieved TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
);
