-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dc3010_app
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `favourites`
--

DROP TABLE IF EXISTS `favourites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favourites` (
  `user_id` int NOT NULL,
  `favourite_project_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`favourite_project_id`),
  KEY `favourite_project_id_idx` (`favourite_project_id`),
  CONSTRAINT `favourite_project_id` FOREIGN KEY (`favourite_project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favourites`
--

LOCK TABLES `favourites` WRITE;
/*!40000 ALTER TABLE `favourites` DISABLE KEYS */;
INSERT INTO `favourites` VALUES (1,24),(1,53);
/*!40000 ALTER TABLE `favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) NOT NULL,
  `project_name` varchar(255) NOT NULL,
  `grade_required` varchar(255) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `work_location` enum('Remote','Hybrid','Client_Site') NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role_description` text NOT NULL,
  `created_By` int NOT NULL,
  PRIMARY KEY (`project_id`),
  KEY `createdBy` (`created_By`),
  CONSTRAINT `createdBy` FOREIGN KEY (`created_By`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (24,'BEAN LTD','BEAN DEVELOPMENT','A5','2023-05-23','2023-05-28','Remote',NULL,'MAKE ALL THE BEANS',10),(50,'Company:334','Project:963','A2','2023-05-30','2023-12-31','Client_Site','Test Address: 707','This is a general description for the Client: Company:334 and Project:963',10),(51,'Company:539','Project:268','A9','2023-05-30','2023-12-31','Client_Site','Test Address: 586','This is a general description for the Client: Company:539 and Project:268',10),(52,'Company:906','Project:972','A10','2023-05-30','2023-12-31','Client_Site','Test Address: 484','This is a general description for the Client: Company:906 and Project:972',10),(53,'Company:448','Project:421','A9','2023-05-30','2023-12-31','Remote',NULL,'This is a general description for the Client: Company:448 and Project:421',10),(54,'Company:902','Project:890','A1','2023-05-30','2023-12-31','Remote',NULL,'This is a general description for the Client: Company:902 and Project:890',10),(55,'Company:468','Project:683','A3','2023-05-30','2023-12-31','Remote',NULL,'This is a general description for the Client: Company:468 and Project:683',10),(56,'Company:201','Project:802','A3',NULL,NULL,'Remote',NULL,'This is a general description for the Client: Company:201 and Project:802',10),(57,'Company:551','Project:576','A10',NULL,NULL,'Remote',NULL,'This is a general description for the Client: Company:551 and Project:576',10),(58,'Test Client','Test Project','A5',NULL,NULL,'Remote',NULL,'This is a test description for a project with lots of tags',10),(60,'Company:31','Project:588','A10','2023-05-30','2023-12-31','Client_Site','Test Address: 900','This is a general description for the Client: Company:31 and Project:588',10),(61,'Company:240','Project:575','A1','2023-05-30','2023-12-31','Remote',NULL,'This is a general description for the Client: Company:240 and Project:575',10),(62,'Company:29','Project:795','A10','2023-05-30','2023-12-31','Remote',NULL,'This is a general description for the Client: Company:29 and Project:795',10),(63,'Company:799','Project:300','A2',NULL,NULL,'Remote',NULL,'This is a general description for the Client: Company:799 and Project:300',10),(66,'Hybrid Client','Hybrid Project','A4','2023-06-07','2023-06-12','Hybrid','Wolverhampton','This is a Hybrid Project ',10),(67,'Long Client','Long Project','A5','2023-06-05','2023-06-26','Hybrid','This is an address','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut ullamcorper risus vitae lorem commodo, in efficitur nulla aliquet. Sed orci urna, placerat non ipsum non, pellentesque cursus odio. Cras nec gravida libero. Duis congue purus eu risus bibendum fringilla. Mauris id tortor mattis, dictum libero id, molestie tortor. Phasellus lorem tortor, cursus id tortor a, convallis varius velit. Pellentesque vehicula egestas purus id semper. Nulla mollis nisl vitae iaculis placerat. Aenean sit amet tincidunt augue.\r\n\r\nOrci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed semper ex sed lacus posuere, vel fringilla arcu porta. Praesent condimentum malesuada ante, ut interdum mi. Vestibulum vitae consectetur risus. Curabitur et quam ac justo ultrices mattis. Ut imperdiet ac urna eu cursus. Donec fermentum, lorem vel accumsan molestie, magna tortor luctus nunc, in faucibus nisl libero vel lorem. Sed enim justo, ullamcorper ut hendrerit vel, finibus sit amet diam. Donec venenatis, quam eu mollis rhoncus, sem ex fermentum massa, a pulvinar odio magna nec dui. Aliquam eget blandit nisi. Nunc efficitur dapibus vehicula. Integer laoreet imperdiet sapien ut efficitur. Nunc ac justo sit amet nisi venenatis bibendum. In tincidunt enim a risus congue ultricies. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\r\n\r\nAenean eu viverra ligula. Suspendisse potenti. Donec placerat eget nibh ac bibendum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed pellentesque, dolor quis ultrices feugiat, arcu justo varius mauris, at luctus quam orci at nisl. Pellentesque porta est in tellus gravida tincidunt. Nunc vitae blandit ligula. Etiam vestibulum, justo ac varius aliquam, diam felis consequat nulla, a varius felis urna ac nulla. Cras sed mauris sit amet leo posuere ultricies. Fusce pretium arcu quam, sit amet sagittis sem euismod sit amet. Aliquam feugiat ipsum id sem rhoncus, at porta arcu porta. Ut lacinia quis lacus ac ornare. Mauris cursus pharetra quam, vel maximus justo tristique gravida. Suspendisse in massa in mauris elementum porta in nec ante. Nam nec fermentum tortor. Aenean aliquet maximus neque, ac pellentesque eros.\r\n\r\nPellentesque odio nulla, commodo non iaculis id, rutrum non magna. Nunc a volutpat dolor. Donec imperdiet libero orci, vitae bibendum nibh sodales a. Nullam ut iaculis risus, nec lobortis velit. Nam eget urna sed lorem maximus faucibus eget molestie neque. Nullam tempor velit quam, ut tempus lorem dignissim nec. Mauris erat dolor, feugiat nec luctus at, iaculis vel nibh. Praesent malesuada maximus rutrum. Cras scelerisque leo ac eleifend auctor. Duis non aliquet turpis, id pellentesque nunc. Mauris volutpat tempor ullamcorper. Nunc feugiat lacinia elit at interdum. Nam quis placerat nisi, nec suscipit velit. Nullam in accumsan est, vel faucibus velit.\r\n\r\nVivamus bibendum tellus vitae magna rhoncus, quis ultrices ante placerat. Nam non enim non enim aliquet mollis. Aenean ac mi enim. Donec ullamcorper finibus arcu, non rutrum turpis convallis ac. Donec pretium rhoncus magna, ut porta lorem sodales ac. Phasellus vulputate semper nulla at sollicitudin. Vestibulum fringilla ante vehicula finibus iaculis. Aenean et porta metus. Nullam volutpat iaculis mi. Quisque gravida felis sed nibh aliquam, consequat tristique libero facilisis. Fusce ut dignissim ipsum, sed vestibulum mi. Proin sit amet varius erat. Vestibulum at lectus interdum, placerat diam et, elementum justo. Suspendisse potenti. Vivamus scelerisque ultrices varius.',10),(68,'Admin Client','Admin Project','A1','2023-06-07','2023-06-13','Remote',NULL,'A project created by the admin',6),(69,'Company:778','Project:275','A9','2023-05-30','2023-12-31','Client_Site','Test Address: 904','This is a general description for the Client: Company:778 and Project:275',10);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects-tools`
--

DROP TABLE IF EXISTS `projects-tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects-tools` (
  `project_id` int NOT NULL,
  `tool_id` int NOT NULL,
  PRIMARY KEY (`project_id`,`tool_id`),
  KEY `tool_id_idx` (`tool_id`),
  CONSTRAINT `project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`),
  CONSTRAINT `tool_id` FOREIGN KEY (`tool_id`) REFERENCES `tool` (`tool_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects-tools`
--

LOCK TABLES `projects-tools` WRITE;
/*!40000 ALTER TABLE `projects-tools` DISABLE KEYS */;
INSERT INTO `projects-tools` VALUES (51,1),(58,1),(61,1),(66,1),(67,1),(69,1),(52,2),(54,2),(58,2),(60,2),(61,2),(66,2),(67,2),(69,2),(50,3),(58,3),(60,3),(50,4),(53,4),(58,4),(61,4),(68,4),(51,5),(54,5),(58,5),(52,6),(53,6),(58,6),(66,6),(69,6),(54,7),(58,7),(50,8),(52,8),(53,8),(58,8),(51,9),(58,9),(60,9),(68,9);
/*!40000 ALTER TABLE `projects-tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool`
--

DROP TABLE IF EXISTS `tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tool` (
  `tool_id` int NOT NULL AUTO_INCREMENT,
  `tool_name` varchar(45) NOT NULL,
  PRIMARY KEY (`tool_id`),
  UNIQUE KEY `toolname_UNIQUE` (`tool_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool`
--

LOCK TABLES `tool` WRITE;
/*!40000 ALTER TABLE `tool` DISABLE KEYS */;
INSERT INTO `tool` VALUES (8,'Azure DevOps'),(4,'C++'),(9,'Cucumber'),(7,'Eggplant'),(5,'HPALM'),(1,'HTML'),(2,'JAVA'),(6,'Selenium'),(3,'SQL');
/*!40000 ALTER TABLE `tool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_first_name` varchar(45) NOT NULL,
  `user_last_name` varchar(45) NOT NULL,
  `user_email` varchar(45) NOT NULL,
  `user_login` varchar(45) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `role` varchar(45) NOT NULL,
  `grade` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_login_UNIQUE` (`user_login`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Harry','Edwards','harry.edwards@capgemini.com','haedward','$2a$10$gsvhUixbnVI5xRl3pSGCXOVliPr56rXrAQ4uwMl8PGWIyuDNhgyHO','ROLE_USER','A4'),(6,'admin','admin','admin@admin.com','admin','$2a$10$oN/BSg1o7nlfbbXZzIN/0.0cwPTKx/FD207E4pP17xyMe4GLXtqGq','ROLE_USER,ROLE_RM,ROLE_ADMIN','A10'),(10,'rm_user1','rm','rm_test_user@outlook.com','rm_user1','$2a$10$6mE.WtRLJgFZsKCbfRyeyOQCuZH0VUeho3kl2x5gFA31MveMPD4me','ROLE_USER,ROLE_RM','A6');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-28 16:10:46
