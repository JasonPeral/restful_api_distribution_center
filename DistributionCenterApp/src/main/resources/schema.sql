-- Create DistributionCenter table
CREATE TABLE distribution_center (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

-- Create Item table
CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    quantity INTEGER,
    distribution_center_id INTEGER REFERENCES distribution_center(id)
);

-- Create an index on distribution_center_id for efficient joins
CREATE INDEX idx_distribution_center_id ON item (distribution_center_id);
