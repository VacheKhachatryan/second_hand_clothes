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
    ('c43b2106-efdc-48ac-b923-a2429f5219e3', 'Jeans'),
    ('7763549b-3ba4-45e1-ad92-e03f2d7cfe9b', 'Jacket'),
    ('5a1dae44-374e-491a-a0c6-302c760843a7', 'Dress'),
    ('5b402bcc-20fc-452f-bd79-a4ac407b85b3', 'Skirt'),
    ('a5ba4e1b-1904-4217-a5b7-d2a46df61992', 'Shirt'),
    ('cc5f842a-2e6b-44d4-8497-dcd5f312ed72', 'Shorts'),
    ('9d3f9042-4f67-4d54-aa79-6ddf6754f7f8', 'Sweater'),
    ('a977abd3-3ab6-4974-8dfe-5c5809e6514c', 'Coat'),
    ('a14e8870-a541-4fb8-b7fc-e9509e4f1a00', 'Blouse');

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