CREATE TABLE IF NOT EXISTS addresses (
    id UUID PRIMARY KEY,
    country VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    street VARCHAR NOT NULL,
    zip_code VARCHAR NOT NULL,
    address1 VARCHAR NOT NULL,
    address2 VARCHAR
);


CREATE TABLE IF NOT EXISTS publishers (
    id uuid PRIMARY KEY,
    full_name VARCHAR NOT NULL,
    address_id UUID REFERENCES addresses(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    firstname VARCHAR NOT NULL,
    lastname VARCHAR NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    role VARCHAR(10) NOT NULL,
    publisher_id uuid REFERENCES publishers(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS garment_categories (
    id UUID PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

INSERT INTO garment_categories (id, name)
VALUES
    (gen_random_uuid(), 'Jeans'),
    (gen_random_uuid(), 'Jacket'),
    (gen_random_uuid(), 'Dress'),
    (gen_random_uuid(), 'Skirt'),
    (gen_random_uuid(), 'Shirt'),
    (gen_random_uuid(), 'Shorts'),
    (gen_random_uuid(), 'Sweater'),
    (gen_random_uuid(), 'Coat'),
    (gen_random_uuid(), 'Blouse');

CREATE TABLE IF NOT EXISTS garments (
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    category_id UUID REFERENCES  garment_categories(id) NOT NULL,
    price NUMERIC NOT NULL,
    size VARCHAR NOT NULL,
    is_available BOOLEAN NOT NULL,
    publisher_id UUID REFERENCES publishers(id) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    token VARCHAR PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
)