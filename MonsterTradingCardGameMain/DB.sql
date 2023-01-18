
DROP TABLE IF EXISTS packages CASCADE;
DROP TABLE IF EXISTS cards CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    username text PRIMARY KEY,
    pwd text NOT NULL,
    name text,
    bio text,
    image text,
    coins integer NOT NULL DEFAULT 20,
    games integer NOT NULL DEFAULT 0,
    wins integer NOT NULL DEFAULT 0,
    elo integer NOT NULL DEFAULT 100,
    token text NOT NULL,
    admin boolean NOT NULL DEFAULT false,
    logged boolean NOT NULL DEFAULT false
);

CREATE TABLE cards (
    cardid text PRIMARY KEY,
    name text NOT NULL,
    damage real NOT NULL,
    owner text REFERENCES users(username),
    collection text
);

CREATE TABLE packages (
    packageid SERIAL PRIMARY KEY,
    cardid_1 text REFERENCES cards(cardid),
    cardid_2 text REFERENCES cards(cardid),
    cardid_3 text REFERENCES cards(cardid),
    cardid_4 text REFERENCES cards(cardid),
    cardid_5 text REFERENCES cards(cardid)
);
