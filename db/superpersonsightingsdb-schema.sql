DROP DATABASE IF EXISTS superpersonsightingsdb;
CREATE DATABASE superpersonsightingsdb;

USE superpersonsightingsdb;

CREATE TABLE superpower(
	superpowerId INT NOT NULL AUTO_INCREMENT,
    superpowerName VARCHAR(50) NOT NULL,
    CONSTRAINT pk_superpower PRIMARY KEY (superpowerId)
);

CREATE TABLE supertype(
	supertypeId INT NOT NULL AUTO_INCREMENT,
    supertypeName VARCHAR(50) NOT NULL,
    CONSTRAINT pk_supertype PRIMARY KEY (supertypeId)
);

CREATE TABLE superperson(
	superpersonId INT NOT NULL AUTO_INCREMENT,
    superpersonName VARCHAR(50) NOT NULL,
    superpersonDescription VARCHAR(100) NOT NULL,
    supertypeId INT NOT NULL,
    superpowerId INT NOT NULL,
    CONSTRAINT pk_superperson PRIMARY KEY (superpersonId),
    CONSTRAINT fk_superperson_supertype FOREIGN KEY (supertypeId)
		REFERENCES supertype(supertypeId),
	CONSTRAINT fk_superperson_superpower FOREIGN KEY (superpowerId)
		REFERENCES superpower(superpowerId)
);

CREATE TABLE address(
	addressId INT NOT NULL AUTO_INCREMENT,
    address VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip CHAR(5) NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (addressId)
);

CREATE TABLE location(
	locationId INT NOT NULL AUTO_INCREMENT,
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
	orgId INT NOT NULL AUTO_INCREMENT,
	orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(100) NOT NULL,
    addressId INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(14) NOT NULL,
    supertypeId INT NOT NULL,
    CONSTRAINT pk_organization PRIMARY KEY (orgId),
    CONSTRAINT fk_organization_address FOREIGN KEY (addressId)
		REFERENCES address (addressId),
	CONSTRAINT fk_organization_supertype FOREIGN KEY (supertypeId)
		REFERENCES supertype(supertypeId)
);

CREATE TABLE membership(
	superpersonId INT NOT NULL,
    orgId INT NOT NULL,
    CONSTRAINT pk_membership PRIMARY KEY (superpersonId, orgId),
    CONSTRAINT fk_membership_superperson FOREIGN KEY (superpersonId)
		REFERENCES superperson (superpersonId),
	CONSTRAINT fk_membership_organization FOREIGN KEY (orgId)
		REFERENCES organization (orgId)
);

CREATE TABLE sighting(
	sightingId INT NOT NULL AUTO_INCREMENT,
    superpersonId INT NOT NULL,
    locationId INT NOT NULL,
    sightingDatetime DATETIME NOT NULL,
    CONSTRAINT pk_sighting PRIMARY KEY (sightingId),
    CONSTRAINT fk_sighting_superperson FOREIGN KEY (superpersonId)
		REFERENCES superperson (superpersonId),
	CONSTRAINT fk_sighting_location FOREIGN KEY (locationId)
		REFERENCES location (locationId)
);