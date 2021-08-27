-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: superpersonsightingsdb
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `addressId` int NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip` char(5) NOT NULL,
  PRIMARY KEY (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'212 Central Park Rd','New York City','NY','10010'),(2,'PO Box 168','Yellowstone National Park','WY','82190'),(3,'4122 NE Sandy Blvd','Portland','OR','97212'),(4,'777 Justice Ln','Independence','TX','50053'),(5,'707 Funny Ln','Seattle','WA','90909'),(6,'1264 Wayne Ln','Gotham','CA','96790');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `locationId` int NOT NULL AUTO_INCREMENT,
  `locationName` varchar(50) NOT NULL,
  `locationDescription` varchar(100) NOT NULL,
  `addressId` int NOT NULL,
  `latitude` decimal(17,15) NOT NULL,
  `longitude` decimal(18,15) NOT NULL,
  PRIMARY KEY (`locationId`),
  KEY `fk_location_address` (`addressId`),
  CONSTRAINT `fk_location_address` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Central Park','The biggest park in NYC',1,40.764324000000000,-73.971750000000000),(2,'Yellowstone National Park','Home to buffalo and geysers',2,44.134860000000000,-110.666270000000000),(3,'Hollywood Theatre','Historic Movie Theatre in NE Portland',3,45.535620000000000,-122.620750000000000);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership`
--

DROP TABLE IF EXISTS `membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership` (
  `superpersonId` int NOT NULL,
  `orgId` int NOT NULL,
  PRIMARY KEY (`superpersonId`,`orgId`),
  KEY `fk_membership_organization` (`orgId`),
  CONSTRAINT `fk_membership_organization` FOREIGN KEY (`orgId`) REFERENCES `organization` (`orgId`),
  CONSTRAINT `fk_membership_superperson` FOREIGN KEY (`superpersonId`) REFERENCES `superperson` (`superpersonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership`
--

LOCK TABLES `membership` WRITE;
/*!40000 ALTER TABLE `membership` DISABLE KEYS */;
INSERT INTO `membership` VALUES (1,1),(3,2),(1,3),(2,3);
/*!40000 ALTER TABLE `membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `orgId` int NOT NULL AUTO_INCREMENT,
  `orgName` varchar(50) NOT NULL,
  `orgDescription` varchar(100) NOT NULL,
  `addressId` int NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(14) NOT NULL,
  `supertypeId` int NOT NULL,
  PRIMARY KEY (`orgId`),
  KEY `fk_organization_address` (`addressId`),
  KEY `fk_organization_supertype` (`supertypeId`),
  CONSTRAINT `fk_organization_address` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`),
  CONSTRAINT `fk_organization_supertype` FOREIGN KEY (`supertypeId`) REFERENCES `supertype` (`supertypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'Justice League','A group of heroes dedicated to fighting crime',4,'admin@jl.com','555-555-5555',1),(2,'Evil Clowns','A group of creepy evil clowns',5,'haha@clowns.com','123-456-7890',2),(3,'Gotham\'s Saviors','A group of heroes looking over Gotham',6,'bats@gotham.net','999-999-9191',1);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sighting`
--

DROP TABLE IF EXISTS `sighting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sighting` (
  `sightingId` int NOT NULL AUTO_INCREMENT,
  `superpersonId` int NOT NULL,
  `locationId` int NOT NULL,
  `sightingDatetime` datetime NOT NULL,
  PRIMARY KEY (`sightingId`),
  KEY `fk_sighting_superperson` (`superpersonId`),
  KEY `fk_sighting_location` (`locationId`),
  CONSTRAINT `fk_sighting_location` FOREIGN KEY (`locationId`) REFERENCES `location` (`locationId`),
  CONSTRAINT `fk_sighting_superperson` FOREIGN KEY (`superpersonId`) REFERENCES `superperson` (`superpersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sighting`
--

LOCK TABLES `sighting` WRITE;
/*!40000 ALTER TABLE `sighting` DISABLE KEYS */;
INSERT INTO `sighting` VALUES (1,1,2,'2021-08-10 12:22:00'),(2,2,3,'2021-08-07 11:00:00'),(3,3,3,'2021-08-06 01:11:00'),(4,3,1,'2021-08-26 13:12:00'),(5,2,2,'2021-08-18 21:10:00'),(6,2,1,'2021-08-02 11:00:00'),(7,1,2,'2021-08-01 17:30:00'),(8,1,3,'2021-08-01 04:30:00'),(9,3,2,'2021-08-04 03:12:00'),(10,2,2,'2021-08-17 05:55:00'),(11,3,2,'2021-08-16 06:16:00');
/*!40000 ALTER TABLE `sighting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `superperson`
--

DROP TABLE IF EXISTS `superperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `superperson` (
  `superpersonId` int NOT NULL AUTO_INCREMENT,
  `superpersonName` varchar(50) NOT NULL,
  `superpersonDescription` varchar(100) NOT NULL,
  `supertypeId` int NOT NULL,
  `superpowerId` int NOT NULL,
  PRIMARY KEY (`superpersonId`),
  KEY `fk_superperson_supertype` (`supertypeId`),
  KEY `fk_superperson_superpower` (`superpowerId`),
  CONSTRAINT `fk_superperson_superpower` FOREIGN KEY (`superpowerId`) REFERENCES `superpower` (`superpowerId`),
  CONSTRAINT `fk_superperson_supertype` FOREIGN KEY (`supertypeId`) REFERENCES `supertype` (`supertypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `superperson`
--

LOCK TABLES `superperson` WRITE;
/*!40000 ALTER TABLE `superperson` DISABLE KEYS */;
INSERT INTO `superperson` VALUES (1,'Superman','Man of Steel',1,1),(2,'Batman','He plays by his own rules',1,2),(3,'The Joker','No one quite understands him',2,3);
/*!40000 ALTER TABLE `superperson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `superpower`
--

DROP TABLE IF EXISTS `superpower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `superpower` (
  `superpowerId` int NOT NULL AUTO_INCREMENT,
  `superpowerName` varchar(50) NOT NULL,
  PRIMARY KEY (`superpowerId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `superpower`
--

LOCK TABLES `superpower` WRITE;
/*!40000 ALTER TABLE `superpower` DISABLE KEYS */;
INSERT INTO `superpower` VALUES (1,'Flight'),(2,'Superspeed'),(3,'Invisibility');
/*!40000 ALTER TABLE `superpower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supertype`
--

DROP TABLE IF EXISTS `supertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supertype` (
  `supertypeId` int NOT NULL AUTO_INCREMENT,
  `supertypeName` varchar(50) NOT NULL,
  PRIMARY KEY (`supertypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supertype`
--

LOCK TABLES `supertype` WRITE;
/*!40000 ALTER TABLE `supertype` DISABLE KEYS */;
INSERT INTO `supertype` VALUES (1,'Superhero'),(2,'Supervillain');
/*!40000 ALTER TABLE `supertype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-26 18:33:47
