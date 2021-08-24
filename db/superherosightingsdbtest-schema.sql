DROP DATABASE IF EXISTS superherosightingsdbtest;
CREATE DATABASE superherosightingsdbtest;

USE superherosightingsdbtest;

CREATE TABLE superhero(
	superheroId INT NOT NULL,
    superheroName VARCHAR(50) NOT NULL,
    superheroDescription VARCHAR(100) NOT NULL,
    superpower VARCHAR(50) NOT NULL,
    CONSTRAINT pk_superhero PRIMARY KEY (superheroId)
);

CREATE TABLE address(
	addressId INT NOT NULL,
    address VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip CHAR(5) NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (addressId)
);

CREATE TABLE location(
	locationId INT NOT NULL,
    locationName VARCHAR(50) NOT NULL,
    locationDescription VARCHAR(100) NOT NULL,
    addressId INT NOT NULL,
    latitude DECIMAL(17, 15) NOT NULL,
    longitude DECIMAL(18, 15) NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (locationId),
    CONSTRAINT fk_location_address FOREIGN KEY (addressId)
		REFERENCES address (addressId)
);

CREATE TABLE organization(
	orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(100) NOT NULL,
    addressId INT NOT NULL,
    orgId INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(14) NOT NULL,
    CONSTRAINT pk_organization PRIMARY KEY (orgId),
    CONSTRAINT fk_organization_address FOREIGN KEY (addressId)
		REFERENCES address (addressId)
);

CREATE TABLE membership(
	superheroId INT NOT NULL,
    orgId INT NOT NULL,
    CONSTRAINT pk_membership PRIMARY KEY (superheroId, orgId),
    CONSTRAINT fk_membership_superhero FOREIGN KEY (superheroId)
		REFERENCES superhero (superheroId),
	CONSTRAINT fk_membership_organization FOREIGN KEY (orgId)
		REFERENCES organization (orgId)
);

CREATE TABLE sighting(
	sightingId INT NOT NULL,
    superheroId INT NOT NULL,
    locationId INT NOT NULL,
    sightingDate DATE NOT NULL,
    CONSTRAINT pk_sighting PRIMARY KEY (sightingId),
    CONSTRAINT fk_sighting_superhero FOREIGN KEY (superheroId)
		REFERENCES superhero (superheroId),
	CONSTRAINT fk_sighting_location FOREIGN KEY (locationId)
		REFERENCES location (locationId)
);