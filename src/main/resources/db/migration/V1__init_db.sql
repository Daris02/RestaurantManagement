CREATE TABLE "restaurant" (
    id          SERIAL          PRIMARY KEY,
    location    VARCHAR(100)    NOT NULL
);

CREATE TABLE "menu" (
    id          SERIAL          PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    costPrice   DOUBLE PRECISION,
    sellPrice   DOUBLE PRECISION,
    createAt    TIMESTAMP       DEFAULT current_timestamp
);

CREATE TABLE "unity" (
    id          SERIAL          PRIMARY KEY,
    name        VARCHAR(10)     NOT NULL
);

CREATE TABLE "price" (
    id          SERIAL          PRIMARY KEY,
    value       DOUBLE PRECISION,
    createAt    TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE "ingredient" (
    id          SERIAL          PRIMARY KEY,
    name        VARCHAR(100)    NOT NULL,
    priceId     INT             REFERENCES "price"(id),
    unityId     INT             REFERENCES "unity"(id)
);

CREATE  TABLE "ingredientmenu" (
    id          SERIAL          PRIMARY KEY,
    quantity    DOUBLE PRECISION,
    menuId          INT         REFERENCES "menu"(id),
    ingredientId    INT         REFERENCES "ingredient"(id)
);

CREATE TABLE "stock" (
    id          SERIAL          PRIMARY KEY,
    value       DOUBLE PRECISION,
    createAt    TIMESTAMP       DEFAULT current_timestamp,
    ingredientId    INT         REFERENCES "ingredient"(id),
    restaurantId    INT         REFERENCES "restaurant"(id)
);

CREATE TABLE "stockmove" (
    id          SERIAL          PRIMARY KEY,
    value       DOUBLE PRECISION,
    type        VARCHAR(5)      NOT NULL,
    createAt    TIMESTAMP       DEFAULT current_timestamp,
    ingredientId    INT         REFERENCES "ingredient"(id),
    restaurantId    INT         REFERENCES "restaurant"(id)
);

CREATE TABLE "vente" (
    id          SERIAL          PRIMARY KEY,
    amount      DOUBLE PRECISION,
    createAt    TIMESTAMP       DEFAULT current_timestamp,
    restaurantId    INT         REFERENCES "restaurant"(id),
    menuId          INT         REFERENCES "menu"(id)
);
