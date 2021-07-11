-- phpMyAdmin SQL Dump
-- version 4.0.10deb1ubuntu0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 11, 2021 at 07:42 PM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `corona`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_login_data`
--

CREATE TABLE IF NOT EXISTS `admin_login_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(200) NOT NULL,
  `LastName` varchar(200) NOT NULL,
  `Role` int(11) NOT NULL,
  `StaffID` varchar(200) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL DEFAULT 'logo.png',
  `PhoneNo` varchar(200) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `loginDate` date NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `isStaff` tinyint(1) NOT NULL DEFAULT '0',
  `isOffice` tinyint(1) NOT NULL DEFAULT '0',
  `isVerified` tinyint(1) NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `D_Date` date NOT NULL,
  `D_Time` time NOT NULL,
  `D_User` varchar(200) NOT NULL,
  `D_IP` varchar(200) NOT NULL,
  `Reference_Code` varchar(200) DEFAULT NULL,
  `AppInstallation_Date` date NOT NULL,
  `AppInstallation_Time` time NOT NULL,
  `FirebaseToken` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `User` varchar(200) NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `admin_login_data`
--

INSERT INTO `admin_login_data` (`ID`, `FirstName`, `LastName`, `Role`, `StaffID`, `Email`, `Photo`, `PhoneNo`, `Password`, `loginDate`, `isAdmin`, `isStaff`, `isOffice`, `isVerified`, `isDeleted`, `D_Date`, `D_Time`, `D_User`, `D_IP`, `Reference_Code`, `AppInstallation_Date`, `AppInstallation_Time`, `FirebaseToken`, `Date`, `User`, `Time`, `IP`) VALUES
(2, 'Admin', '123', 0, 'ADMIN123', 'admin@hmail.com', 'logo.png', '9999999999', '123456', '2019-10-26', 1, 0, 0, 1, 0, '0000-00-00', '00:00:00', '', '', NULL, '0000-00-00', '00:00:00', '', '0000-00-00', '', '00:00:00', ''),
(7, 'Parag', 'Deka', 2, 'Parag241', 'parag.moni44@gmsil.com', 'artboard.png', '7002608241', '123456', '0000-00-00', 0, 0, 1, 0, 0, '0000-00-00', '00:00:00', '', '', NULL, '0000-00-00', '00:00:00', '', '2020-02-18', 'ADMIN123', '17:11:41', '47.29.140.135');

-- --------------------------------------------------------

--
-- Table structure for table `askwho`
--

CREATE TABLE IF NOT EXISTS `askwho` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Image` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `askwho`
--

INSERT INTO `askwho` (`ID`, `Image`) VALUES
(1, 'http://139.59.38.160/Corona/who/safe-greetings.png'),
(2, 'http://139.59.38.160/Corona/who/handshaking-3.png'),
(3, 'http://139.59.38.160/Corona/who/wearing-gloves.png');

-- --------------------------------------------------------

--
-- Table structure for table `comobility`
--

CREATE TABLE IF NOT EXISTS `comobility` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) NOT NULL,
  `Amount` float(10,2) NOT NULL,
  `DigiCategory` int(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `comobility`
--

INSERT INTO `comobility` (`ID`, `Category`, `Amount`, `DigiCategory`, `Date`, `Time`) VALUES
(1, 'If age < 50 and no morbility', 99.00, 1, '0000-00-00', '00:00:00'),
(2, 'If age < 50 and morbility', 199.00, 1, '0000-00-00', '00:00:00'),
(3, 'If age > 60  and no co morb', 149.00, 2, '0000-00-00', '00:00:00'),
(4, 'If  age is 50-59 and co mob) ', 299.00, 3, '0000-00-00', '00:00:00'),
(5, 'If age > 60 and co mob', 499.00, 3, '0000-00-00', '00:00:00'),
(6, 'If age is 50-59 and co mob', 199.00, 4, '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `covidtest`
--

CREATE TABLE IF NOT EXISTS `covidtest` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OTP` int(11) NOT NULL,
  `IDrequests` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `Address` varchar(200) NOT NULL,
  `Sars` tinyint(4) NOT NULL DEFAULT '0',
  `Sampletype` varchar(200) NOT NULL,
  `Indication` varchar(200) NOT NULL,
  `Country1` varchar(200) NOT NULL,
  `Ddate1` date NOT NULL,
  `Rdate1` date NOT NULL,
  `Country2` varchar(200) NOT NULL,
  `Ddate2` date NOT NULL,
  `Rdate2` date NOT NULL,
  `Signature` varchar(200) NOT NULL,
  `Document` varchar(200) NOT NULL,
  `Status` int(11) NOT NULL DEFAULT '1',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `covidtest`
--

INSERT INTO `covidtest` (`ID`, `OTP`, `IDrequests`, `UserID`, `Address`, `Sars`, `Sampletype`, `Indication`, `Country1`, `Ddate1`, `Rdate1`, `Country2`, `Ddate2`, `Rdate2`, `Signature`, `Document`, `Status`, `Date`, `Time`, `IP`) VALUES
(1, 231635, 0, 96, '49 PANDAY RD, SYDENHAM, BEREA, 4091, SOUTH AFRICA', 0, '', '', '', '0000-00-00', '0000-00-00', '', '0000-00-00', '0000-00-00', '', 'Screenshot_20200802-104731_WhatsApp.jpg', 1, '2020-08-02', '22:03:48', 'FE80::2418:1DFF:FEF7:6311%P2P0'),
(2, 0, 2, 112, '8, LATAKATA RD, GANESH NAGAR, LATAKATA, GUWAHATI, MEGHALAYA 781029, INDIA', 0, '', '', '', '0000-00-00', '0000-00-00', '', '0000-00-00', '0000-00-00', '', 'IMG-20200728-WA0010.jpg', 1, '2020-08-05', '20:53:52', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0'),
(3, 975458, 3, 114, '8, LATAKATA RD, GANESH NAGAR, LATAKATA, GUWAHATI, MEGHALAYA 781029, INDIA', 0, '', '', '', '0000-00-00', '0000-00-00', '', '0000-00-00', '0000-00-00', '', 'IMG-20200806-WA0009.jpg', 1, '2020-08-06', '19:53:03', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0');

-- --------------------------------------------------------

--
-- Table structure for table `customer_consultation`
--

CREATE TABLE IF NOT EXISTS `customer_consultation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OTP` int(11) NOT NULL DEFAULT '0',
  `IDrequests` int(11) NOT NULL,
  `IDUser` int(11) NOT NULL,
  `Age` int(11) NOT NULL,
  `IDSymtoms` int(11) NOT NULL,
  `IDSecondtLevel` int(11) NOT NULL,
  `RiskFactor` int(11) NOT NULL,
  `OtherRiskFactor` varchar(200) NOT NULL,
  `BookDate` date NOT NULL,
  `TimeSlot` int(11) NOT NULL,
  `Amount` float(10,2) NOT NULL,
  `PaymentMode` int(11) NOT NULL,
  `IDDoc` int(11) NOT NULL,
  `DigiCategory` int(11) NOT NULL,
  `isComplete` int(11) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `customer_consultation`
--

INSERT INTO `customer_consultation` (`ID`, `OTP`, `IDrequests`, `IDUser`, `Age`, `IDSymtoms`, `IDSecondtLevel`, `RiskFactor`, `OtherRiskFactor`, `BookDate`, `TimeSlot`, `Amount`, `PaymentMode`, `IDDoc`, `DigiCategory`, `isComplete`, `Date`, `Time`, `User`) VALUES
(1, 0, 0, 78, 62, 294411, 5, 8, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-10', '18:19:46', ''),
(2, 0, 0, 124, 53, 35626, 1, 8, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-11', '11:58:40', ''),
(3, 0, 0, 76, 42, 815030, 5, 9, '', '0000-00-00', 0, 0.00, 0, 0, 1, 0, '2020-08-10', '19:50:18', ''),
(4, 0, 0, 65, 26, 74917, 4, 9, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-10', '22:01:44', ''),
(5, 0, 0, 49, 2019, 215807, 4, 1, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-10', '22:20:13', ''),
(6, 0, 0, 126, 25, 229979, 5, 8, '', '0000-00-00', 0, 0.00, 0, 0, 1, 0, '2020-08-18', '13:19:47', ''),
(7, 0, 0, 91, 120, 685112, 4, 9, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-30', '19:20:20', ''),
(8, 0, 0, 129, 53, 221036, 2, 1, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2020-08-22', '19:02:39', ''),
(9, 0, 0, 39, 47, 545222, 2, 3, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2021-01-25', '19:03:17', ''),
(10, 0, 0, 127, 30, 438839, 4, 1, '', '0000-00-00', 0, 0.00, 0, 0, 1, 0, '2020-08-26', '22:07:29', ''),
(11, 0, 0, 47, 47, 326759, 5, 3, '', '0000-00-00', 0, 0.00, 0, 0, 1, 0, '2020-08-29', '13:56:03', ''),
(12, 0, 0, 28, 86, 151042, 0, 3, '', '0000-00-00', 0, 0.00, 0, 111, 4, 0, '2020-09-11', '13:39:35', ''),
(13, 0, 0, 42, 53, 313960, 1, 1, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2021-01-11', '07:09:13', ''),
(14, 0, 0, 130, 44, 232323, 4, 8, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2021-01-13', '12:02:39', ''),
(15, 0, 0, 96, 22, 39693, 4, 2, '', '0000-00-00', 0, 0.00, 0, 0, 4, 0, '2021-02-06', '06:04:55', '');

-- --------------------------------------------------------

--
-- Table structure for table `customer_questionlevel`
--

CREATE TABLE IF NOT EXISTS `customer_questionlevel` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `IDFirstLevel` int(11) NOT NULL,
  `IDDoc` int(11) NOT NULL,
  `Patientfile` varchar(200) NOT NULL,
  `WAQF` varchar(200) NOT NULL,
  `Option` int(11) NOT NULL,
  `PaymentMode` int(11) NOT NULL,
  `IDSecondtLevel` int(11) NOT NULL,
  `isComplete` int(11) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=59 ;

--
-- Dumping data for table `customer_questionlevel`
--

INSERT INTO `customer_questionlevel` (`ID`, `IDUser`, `IDFirstLevel`, `IDDoc`, `Patientfile`, `WAQF`, `Option`, `PaymentMode`, `IDSecondtLevel`, `isComplete`, `Date`, `Time`) VALUES
(13, 36, 1, 4, '', 'WAQF-0036', 7, 0, 4, 1, '2020-07-28', '21:55:56'),
(18, 52, 1, 4, '', 'WAQF-0052', 1, 0, 2, 1, '2020-07-28', '11:27:11'),
(21, 37, 1, 4, '', 'WAQF-0037', 6, 0, 4, 1, '2020-07-28', '22:36:42'),
(22, 54, 1, 0, '', 'WAQF-0054', 2, 0, 3, 0, '2020-07-28', '23:02:23'),
(23, 30, 1, 4, '', 'WAQF-0030', 6, 0, 3, 1, '2020-07-28', '23:05:52'),
(24, 81, 1, 0, '', 'WAQF-0081', 4, 0, 5, 0, '2020-07-30', '21:49:46'),
(25, 60, 1, 0, '', 'WAQF-0060', 6, 0, 2, 0, '2020-07-31', '22:02:54'),
(26, 62, 1, 0, '', 'WAQF-0062', 6, 0, 3, 0, '2020-08-01', '08:17:03'),
(27, 92, 1, 0, '', 'WAQF-0092', 8, 0, 2, 0, '2020-08-02', '13:09:18'),
(28, 96, 1, 0, '', 'WAQF-0096', 3, 0, 2, 0, '2021-02-06', '06:04:16'),
(29, 75, 1, 0, '', 'WAQF-0077', 4, 0, 5, 0, '2020-08-02', '19:54:24'),
(30, 94, 1, 0, '', 'WAQF-0094', 4, 0, 1, 0, '2020-08-02', '18:24:50'),
(31, 76, 1, 0, '', 'WAQF-0076', 4, 0, 5, 0, '2020-08-02', '18:29:27'),
(32, 97, 1, 0, '', 'WAQF-0097', 3, 0, 4, 0, '2020-08-02', '18:54:31'),
(33, 78, 1, 0, '', 'WAQF-0078', 4, 0, 5, 0, '2020-08-10', '18:19:33'),
(34, 102, 1, 0, '', 'WAQF-0102', 3, 0, 1, 0, '2020-08-02', '20:28:35'),
(35, 10, 1, 0, '', 'WAQF-0010', 6, 0, 3, 0, '2020-08-02', '22:36:18'),
(36, 112, 1, 113, '', 'WAQF-0112', 1, 0, 3, 1, '2020-08-05', '23:26:09'),
(37, 117, 1, 116, '', 'WAQF-0117', 8, 0, 4, 1, '2020-08-06', '21:06:54'),
(38, 121, 1, 113, '', 'WAQF-0121', 1, 0, 2, 0, '2020-08-06', '16:07:29'),
(39, 118, 1, 113, '', 'WAQF-0118', 8, 0, 1, 1, '2020-08-06', '16:09:49'),
(40, 114, 1, 0, '', 'WAQF-0118', 1, 0, 3, 0, '2020-08-06', '17:16:43'),
(41, 119, 1, 0, '', 'WAQF-0119', 1, 0, 4, 0, '2020-08-06', '20:47:44'),
(42, 120, 1, 0, '', 'WAQF-0120', 5, 0, 5, 0, '2020-08-06', '20:55:00'),
(43, 122, 1, 0, '', 'WAQF-0122', 2, 0, 4, 0, '2020-08-06', '21:53:00'),
(44, 123, 1, 116, '', 'WAQF-0123', 2, 0, 4, 1, '2020-08-06', '22:44:31'),
(45, 77, 1, 0, '', 'WAQF-0077', 4, 0, 5, 0, '2020-08-07', '19:44:18'),
(46, 124, 1, 0, '', 'WAQF-0124', 6, 0, 4, 0, '2020-08-11', '11:58:23'),
(47, 65, 1, 0, '', 'WAQF-0065', 8, 0, 4, 0, '2020-08-10', '22:01:16'),
(48, 49, 1, 0, '', 'WAQF-0049', 3, 0, 4, 0, '2020-08-10', '22:19:16'),
(49, 126, 1, 0, '', 'WAQF-0126', 3, 0, 5, 0, '2020-08-10', '22:38:06'),
(50, 91, 1, 0, '', 'WAQF-0091', 6, 0, 4, 0, '2020-08-30', '19:20:11'),
(51, 29, 1, 0, '', 'WAQF-0029', 5, 0, 2, 0, '2020-08-22', '19:58:00'),
(52, 127, 1, 0, '', 'WAQF-0127', 6, 0, 4, 0, '2020-08-22', '18:54:19'),
(53, 129, 1, 0, '', 'WAQF-0129', 6, 0, 2, 0, '2020-08-22', '19:01:59'),
(54, 39, 1, 0, '', 'WAQF-0039', 1, 0, 3, 0, '2021-01-25', '19:02:53'),
(55, 47, 1, 0, '', 'WAQF-0047', 5, 0, 5, 0, '2020-08-27', '21:51:18'),
(56, 28, 1, 111, '', 'WAQF-0028', 5, 0, 5, 0, '2020-09-11', '13:39:19'),
(57, 42, 1, 0, '', 'WAQF-0042', 1, 0, 1, 0, '2021-01-11', '07:08:50'),
(58, 130, 1, 0, '', 'WAQF-0130', 3, 0, 4, 0, '2021-01-13', '12:02:05');

-- --------------------------------------------------------

--
-- Table structure for table `digicategory`
--

CREATE TABLE IF NOT EXISTS `digicategory` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `digicategory`
--

INSERT INTO `digicategory` (`ID`, `Category`, `Date`, `Time`) VALUES
(1, 'Digisurgery', '0000-00-00', '00:00:00'),
(2, 'Digiclinic', '0000-00-00', '00:00:00'),
(3, 'Digihospital', '0000-00-00', '00:00:00'),
(4, 'DigiICU', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `docdetails`
--

CREATE TABLE IF NOT EXISTS `docdetails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Address` mediumtext NOT NULL,
  `practisenumber` varchar(200) NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `firstlevelquestions`
--

CREATE TABLE IF NOT EXISTS `firstlevelquestions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(200) NOT NULL,
  `Question` mediumtext NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Color` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `firstlevelquestions`
--

INSERT INTO `firstlevelquestions` (`ID`, `Title`, `Question`, `isActive`, `Color`, `Date`, `Time`) VALUES
(1, 'Nightingale beneficiary', 'A targeted Nightingale beneficiary who wishes to join DigiDoc', 1, '#2196f3', '0000-00-00', '00:00:00'),
(2, 'Referred another doctor', 'A patient referred to DigiDoc by another doctor or a patient of another doctor that wishes to use the service', 0, '#ff5722', '0000-00-00', '00:00:00'),
(3, 'Existing patient of Ekuphileni', 'An Existing patient of Ekuphileni who wants to join DigiDoc', 0, '#2e7d32', '0000-00-00', '00:00:00'),
(4, 'New Patient', 'A New individual who wants to join DigiDoc', 0, '#9c27b0', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `generalinfo`
--

CREATE TABLE IF NOT EXISTS `generalinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDHeading` int(11) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `Description` mediumtext NOT NULL,
  `isVideo` int(11) NOT NULL DEFAULT '0',
  `Photo` varchar(200) NOT NULL,
  `Uploadedby` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `generalinfo`
--

INSERT INTO `generalinfo` (`ID`, `IDHeading`, `Title`, `Description`, `isVideo`, `Photo`, `Uploadedby`, `Date`, `Time`, `IP`) VALUES
(1, 6, 'THE MEDICAL PERSPECTIVE OF COV-19', 'MEDICAL PERSPECTIVE OF COV-19', 0, 'http://139.59.38.160/Corona/news/Notes_200809_233312_e70.pdf', 4, '2020-08-09', '23:40:09', 'FE80::B849:45E:6624:D7C7%WLAN0'),
(2, 6, 'DIGI RANKING', 'DIAGRAMMATIC ILLUSTRATION OF THE DIGI MODEL FOR PATIENT CLASSIFICATION', 0, 'http://139.59.38.160/Corona/news/20200722_011753_0000.png', 4, '2020-08-12', '21:50:56', 'FE80::BD7E:CBDF:BE50:DE40%WLAN0');

-- --------------------------------------------------------

--
-- Table structure for table `govt`
--

CREATE TABLE IF NOT EXISTS `govt` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Country` varchar(200) NOT NULL,
  `Link` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `govt`
--

INSERT INTO `govt` (`ID`, `Country`, `Link`) VALUES
(1, 'India', 'https://www.mohfw.gov.in'),
(2, 'SouthAfrica', 'http://www.health.gov.za'),
(3, 'Brazil', 'http://www.health.gov.za');

-- --------------------------------------------------------

--
-- Table structure for table `heading`
--

CREATE TABLE IF NOT EXISTS `heading` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `heading`
--

INSERT INTO `heading` (`ID`, `Name`, `Date`, `Time`) VALUES
(1, 'Clinical', '0000-00-00', '00:00:00'),
(2, 'Medical', '0000-00-00', '00:00:00'),
(3, 'Pharmaceutical', '0000-00-00', '00:00:00'),
(4, 'Nursing', '0000-00-00', '00:00:00'),
(5, 'Paramedical', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `healthinfo`
--

CREATE TABLE IF NOT EXISTS `healthinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PatientID` int(11) NOT NULL,
  `ANS` tinyint(1) NOT NULL DEFAULT '0',
  `Description` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `indication`
--

CREATE TABLE IF NOT EXISTS `indication` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `indication`
--

INSERT INTO `indication` (`ID`, `Name`, `Date`, `Time`) VALUES
(1, 'Recent travel', '0000-00-00', '00:00:00'),
(2, 'Contact with a known positive case', '0000-00-00', '00:00:00'),
(3, 'Healthcare worker', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `links`
--

CREATE TABLE IF NOT EXISTS `links` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Subject` varchar(200) NOT NULL,
  `Links` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `links`
--

INSERT INTO `links` (`ID`, `Subject`, `Links`) VALUES
(1, 'How to Protect Yourself', 'https://www.cdc.gov/coronavirus/2019-ncov/prepare/prevention.html'),
(2, 'What To Do if You Are Sick', 'https://www.cdc.gov/coronavirus/2019-ncov/if-you-are-sick/steps-when-sick.html'),
(3, 'Govt', 'https://www.mohfw.gov.in'),
(4, 'Videos', 'https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public/videos'),
(5, 'Statistics', 'https://www.bing.com/covid?vert=graph'),
(6, 'ask who', 'https://www.who.int/emergencies/diseases/novel-coronavirus-2019');

-- --------------------------------------------------------

--
-- Table structure for table `medical`
--

CREATE TABLE IF NOT EXISTS `medical` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `OTP` int(11) NOT NULL,
  `LDH` float(10,4) NOT NULL,
  `CRP` float(10,4) NOT NULL,
  `Ferritin` float(10,4) NOT NULL,
  `Lymphocytes` float(10,4) NOT NULL,
  `Count` float(10,2) NOT NULL,
  `DDimer` float(10,4) NOT NULL,
  `Interleukin` float(10,4) NOT NULL,
  `PCT` float(10,4) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `isComplete` tinyint(4) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `medical`
--

INSERT INTO `medical` (`ID`, `IDUser`, `OTP`, `LDH`, `CRP`, `Ferritin`, `Lymphocytes`, `Count`, `DDimer`, `Interleukin`, `PCT`, `Photo`, `isComplete`, `Date`, `Time`) VALUES
(1, 78, 275845, 0.0000, 0.0000, 0.0000, 0.0000, 0.00, 0.0000, 0.0000, 0.0000, '', 0, '2020-08-07', '19:55:36'),
(2, 124, 35626, 0.0000, 0.0000, 0.0000, 0.0000, 0.00, 0.0000, 0.0000, 0.0000, '', 0, '2020-08-07', '20:40:06'),
(3, 78, 294411, 0.0000, 1.4000, 139.0000, 0.0000, 0.00, 0.2700, 0.0000, 0.0000, '', 0, '2020-08-08', '10:50:41'),
(4, 78, 294411, 0.0000, 0.0000, 157.0000, 0.0000, 0.00, 0.2000, 2.0000, 0.0000, '', 0, '2020-08-10', '18:21:20'),
(5, 124, 35626, 0.0000, 0.0000, 0.0000, 0.0000, 0.00, 0.0000, 0.0000, 0.0000, '', 0, '2020-08-11', '12:08:58'),
(6, 91, 685112, 0.0000, 0.0000, 0.0000, 0.0000, 0.00, 0.0000, 0.0000, 0.0000, '', 0, '2020-08-20', '10:32:20'),
(7, 28, 151042, 0.0000, 0.0000, 0.0000, 0.0000, 0.00, 0.0000, 0.0000, 0.0000, 'http://139.59.38.160/Corona/Medical/Mr_Khan__Yakoob__Bloods CumulativeReport_pdf-1.pdf', 0, '2020-09-11', '13:41:53'),
(8, 42, 313960, 1.0000, 2.0000, 3.0000, 6.0000, 66.00, 9.0000, 6.0000, 4.0000, '', 0, '2021-01-11', '07:10:55'),
(9, 96, 39693, 32.0000, 65.0000, 58.0000, 69.0000, 3.00, 63.0000, 69.0000, 66.0000, '', 0, '2021-02-06', '06:05:47');

-- --------------------------------------------------------

--
-- Table structure for table `medical_aid`
--

CREATE TABLE IF NOT EXISTS `medical_aid` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Detail` mediumtext NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `medical_aid`
--

INSERT INTO `medical_aid` (`ID`, `Detail`, `Date`, `Time`) VALUES
(1, 'I confirm my medical aid details are correct.', '0000-00-00', '00:00:00'),
(2, 'I agree to my medical aid details being provided.\n', '0000-00-00', '00:00:00'),
(3, 'I understand that I am liable for any amount due in the event that my medical aid does not cover.', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `picture` varchar(200) NOT NULL,
  `fromurl` varchar(200) NOT NULL,
  `subject` varchar(200) NOT NULL,
  `message` varchar(200) NOT NULL,
  `Links` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`ID`, `picture`, `fromurl`, `subject`, `message`, `Links`) VALUES
(1, 'http://139.59.38.160/Corona/news/images-5.jpeg', 'Coronavirus cases in Africa pass 2400 amid fears for health ...', 'The Guardian-2 hours ago', 'The country''s health minister, Zweli Mkhize, said on Wednesday that the number of coronavirus cases had reached 709, up from 554 a day ...', 'https://www.theguardian.com/world/2020/mar/25/coronavirus-cases-africa-pass-amid-fears-health-services-south-who'),
(2, 'http://139.59.38.160/Corona/news/images-6.jpeg', 'Senate Reaches Historic Deal On $2 Trillion Coronavirus ...', 'NPR-1 hour ago', 'Senate leaders have struck a historic deal to inject the U.S. economy with about $2 trillion in aid in response to the coronavirus pandemic.', 'https://www.npr.org/2020/03/25/818881845/senate-reaches-historic-deal-on-2t-coronavirus-economic-rescue-package');

-- --------------------------------------------------------

--
-- Table structure for table `nitingleoptions`
--

CREATE TABLE IF NOT EXISTS `nitingleoptions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Options` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `nitingleoptions`
--

INSERT INTO `nitingleoptions` (`ID`, `Options`, `Date`, `Time`) VALUES
(1, 'JHB', '0000-00-00', '00:00:00'),
(2, 'Westville', '0000-00-00', '00:00:00'),
(3, 'Sherwood', '0000-00-00', '00:00:00'),
(4, ' Mount Edgecombe', '0000-00-00', '00:00:00'),
(5, 'Berea', '0000-00-00', '00:00:00'),
(6, 'Durban', '0000-00-00', '00:00:00'),
(7, 'Mobile', '0000-00-00', '00:00:00'),
(8, 'Overport', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDSender` int(11) NOT NULL,
  `TOGroup` int(11) NOT NULL,
  `Title` varchar(200) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`ID`, `IDSender`, `TOGroup`, `Title`, `Description`, `Photo`, `Date`, `Time`) VALUES
(1, 4, 1, 'TEST 18072020 1608', 'THIS IS A TEST', '', '2020-07-18', '16:08:26'),
(2, 4, 1, 'COV-19 PATHWAY TO CARE', 'ASSALAMUALAIKUM ALL NGHTNGLES\n\nIâ€™VE HAD A QUICK LOOK AT OUR  DIGIHEALTH PATHWAYS. NO TIME FOR  DEEP ANALYSIS .WE''LL NEED TO REVISIT THEM  TO BENCHMARK PROPERLY  AGAINST INTERNATIONAL BEST PRACTICE. ( JAZAKALLAH TO DR FATHIMA - UK FOR THEIR GUIDES TOO) \n\nATTACHED BELOW, LATEST BUILD WITH MIMINUM VIABLE PRODUCT( MVP) STANDARDS. \n\n\nPATHS TO HOPE\n09 AUGUST\n10PM, SUNDAY\n\n *DIGIHEALTH EMERGENCY* FLOW\n\nPERSONALLY, I WOULD PREFER 082 911 AS YOUR EMERGENCY NUMBER TO CALL , WITH A PROVISO, SOME ONE ALSO ATTEMPTS TO CALL ME, SO I CAN CO ORDINATE IF AIR MED SERVICE MAY NEED TO  BE ACTIVATED. \n\n *URGENT PATHWAY IS SLIGHTLY LESS LIFE AND DEATH BREATHING RATE.* \n\nTHE NEXT TWO PATHWAYS, YOUR FAMILY MEDIC IS YOUR FIRST POINT OF CALL. DIGIHEALTH CANNOT TAKE SOLE RESPONSIBILITY. IF WE DO,  THATâ€™S A HEAVY BURDEN FOR WHICH WE NOT CAPACIATED FOR. WE ARE PURELY VIRTUAL. MEANING WE TOTALLY DIGITAL , HOUSING OURSELVES IN THE CLOUD. SO FOR ALL INTENTS AND PURPOSES, WE CAN ONLY , AT MOST, BE AT YOUR VIRTUAL &QUOT;BEDSIDE&QUOT;.\n\nSAYING THAT I COME FROM 30 OR SO  YEARS  BACKGROUND ! \n\nA PARAMEDIC WILL CHECK YOU OUT AND IF  NO IMMEDIATE DANGER, LEAVE YOU AT HOME.  BUT IF YOU ARE  NOT SURE ABOUT YOUR HEALTH,  OR IF YOUR FAMILY NEEDS A SECOND OPINION OR YOUR FAMILY MEDIC IS NOT AVAILABLE, THEN PLEASE DONT HESITATE TO USE THIS PERSONALISED HUMANITARIAN SERVICE, SET UP PRECISELY ON THE WORST CASE GRAVELY PREDICTIONS AROUND SARS-COV-2 ( DTOA  - VI- 2) \n\n\n\nPROMPT ICU LEVEL , PRE - HOSPITAL  SERVICE IN NON- EMERGENCY  WITH DIGIHEALTH APP  AND FREE '' VIRTUAL '' C *U* RE ! INSHA  ALLLAH.\n\nI DONT KNOW HPCSA TELEHEALTH LATEST GUIDES. THATS WHAT GUIDES ARE. GUIDES.\n\nITS WHAT WE DO NOW THAT COUNTS IN THE HEREAFTER.\n\nWE DONâ€™T WORK NIGHTS,  SO OUR SAFETY NETTING TO ALL PATIENTS IS :\n\n CALL AN AMBULANCE. \n\n IF ITâ€™S OUT OF HOURS, OR AN *URGENCY*  AND YOU CANâ€™T GET HOLD OF A GP ! \n\n EMERGENCIES A LITTLE DIFFERENT TO URGENCY.\n\nMOST WHAT WE SAY IS GOOD FOR EMERGENCIES,  BUT HEART RATE  WITH  110 BEATS PER MIN ON YOUR OXIMETER, WIT OTHER SYMPTOMS, LIKE HIGH  TEMPERATURE, WITH BREATHLESSNESS OR DIZZINNES MAY JUST BE MORE THAN PLAIN OLD ANXIETY ! OR\n\n *SALBUTAMOL ( WHEEZING ) INHALER OVERUSE* \n\n *ALL READINGS OUT OF RANGE ON OXIMETER, CALL FOR HELP AND RECHECK ,  BEFORE PANICKING FURTHER* \n\nðŸ‘†ðŸ¼JUST PRACTICE POINTERS TO NOT LOSE THE PATIENT. ULTIMATELY , ALLAH IS THE PROXIMAL AND DISTAL CAUSE OF DTOA - VI-2\n\nTHINGS MAY BE DIFFERENT IN YOUR HOME !\n\n*DIGIHEALTH EMERGENCY PATHWAY* \n\nTHERE IS NO VIRTUAL PATHWAY IN AN EMERGENCY.\n\nIN THE FIRST INSTANCE, CALL ANY OF THESE NUMBERS BELOW: \n\n082 911   NETCARE OR\n084 124  ER 24 \nOR\n0800 333 911 CITIMED\n\nTOGETHER WITH THE ABOVE, GET SOMEONE TO ALSO CALL DIGI@DOC ON \n0828 09 09 09. \n\nWHAT CONSTITUTES AN EMERGENCY ? \n\nANY PERCEPTION OF BEING IN AN EMERGENCY,  REAL OR NOT.\n\nAN OXIMETER READING BELOW 92 % ON ROOM AIR. \n\nRESPIRATORY RATE GREATER THAN 25 BREATHS PER MIN\n\nSPEECH INCOHERENT OR DIFFICULTLY IN COMPLETING A SENTENCE IN ONE BREATH.\n\nPULSE GREATER THAN 110 BEATS PER MIN.\n\n\n*DIGIHEALTH URGENT INFORMATIC TO ENTER VIRTUAL HOSPITAL WAITING ROOM* \n\nCRITERIA FOR URGENCY \n1. OXYGEN SATURATION DROP BY MORE THAN 2% FROM BASE.EG 97 TO 95 OR 99 TO 97.\n\n2. ANY OXYGEN SATURATION BELOW 95 % \n\n3. ANY ANXIETY ON ANYTHING COVID-19 AT ANY OXYGEN SATURATION LEVEL\n\n4. ANY SHORTNESS OF BREATH OR COUGH OT ANY SYMPTOM  THAT HAS DETERIORATED FROM A PREVIOUS DIGI@DOC CONVERSATION OR ANY SYMPTOM SINCE LAST DIGI CONVERSATION\n\nTHE PATH IS AS FOR NON URGENT DIGI@PATIENTS, EXCEPT WE HAVE A TIME ZONE  COMPRESSION AS FOLLOWS: \n\nSIGN IN TO THE DIGIHEALTH APP. IF THE APP IS DOWN, WHATS APP ME.\n\n\nIF I DO NOT RESPOND WITHIN 10 MINUTES EITHER THROUGH THE APP OR WHATS APP, THEN :\n\nSEND ME A VOICE NOTE.\n\nIF I DONT REAPOND WITHIN 5 MINUTES\n\nTELEVIDEO WHATS APP CALL ME \n\nIF I DONT TAKE CALL ON 3 RINGS,  \n\nTHEN TELECALL ME ( NOT WHATS APP CALL) \n\nIF I STILL DONT ANSWER WITHIN HALF A RING, THEN BE REST ASSURED\n\nI AM &QUOT;GLOVED&QUOT; IN VIRTUAL ICU CONSULTATION .CALL YOUR  POD OFFICER IMMEDIATELY.\n\nINSHALLAH I WILL TRY TO RETURM YOUR CALL WHEN DONE, AND IF YOU STILL &QUOT;ALIVE'' , WE CONTINUE CHATTING TO CURE YOU OF COVID-19ðŸ¤£\n\n\n*DIGIHEALTH NON URGENT PATHWAY TO THE DIGIDOC VIRTUAL WAITING ROOM* \n\n\nSIGN IN TO THE DIGIHEALTH APP AND FOLLOW THE PROMPTS.\n\nIF THE APP IS DOWN, OR THE VIRTUAL WAITING ROOM IS TOO BUSY,  PLSE FEEL FREE DIRECT  WHATS APP  ME.\n\nTHEN IF I DONT RESPOND ( VIA THE DIGI APP OR VIA IR WHATS APP TO ME) WITHIN 30 MINS\n\nTHEN :\nSEND ME A  VOICE NOTE...\n\nIF I THEN,  DONT RESPOND WITHIN 15 MINS  ,\n\n GIVE ME A TELEVIDEO CALL....\n\nIF I DONT TAKE TELEVIDEO  CALL ON 5 RINGS, \n\nTHEN TELECALL ME...\n\nIF I STILL DONT ANSWER WITHIN ONE RING...THEN BE REST ASSURED THAT I AM IN  THE VICU...START THE PROCESS OUTLINED ABOVE AGAIN, IF NON URGENT.   \n\nIF URGENT, LOOK AT OUR DIGIHEALTH URGENT PATHWAY BELOW.\n\nIF AN EMERGENCY, CALL 082 911 OR 084 124 OR 0800 333 911\n\nDO NOT CALL \n0828 09 08 09', '', '2020-08-10', '09:28:31');

-- --------------------------------------------------------

--
-- Table structure for table `oxygen`
--

CREATE TABLE IF NOT EXISTS `oxygen` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `OTP` int(11) NOT NULL,
  `Oxygen` float(10,2) NOT NULL,
  `Breaths` int(11) NOT NULL,
  `temperature` float(10,2) NOT NULL,
  `pulse` int(11) NOT NULL,
  `Issues` int(4) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `oxygen`
--

INSERT INTO `oxygen` (`ID`, `UserID`, `OTP`, `Oxygen`, `Breaths`, `temperature`, `pulse`, `Issues`, `Date`, `Time`) VALUES
(1, 78, 275845, 99.00, 68, 34.00, 0, 0, '2020-08-07', '19:53:00'),
(2, 124, 35626, 98.00, 18, 36.00, 65, 0, '2020-08-07', '20:38:00'),
(3, 78, 294411, 99.00, 20, 34.00, 72, 0, '2020-08-08', '10:45:00'),
(4, 78, 294411, 98.00, 20, 34.00, 67, 0, '2020-08-10', '18:20:00'),
(5, 65, 74917, 97.00, 100, 36.00, 80, 0, '2020-08-10', '22:02:00'),
(6, 49, 215807, 90.00, 25, 37.00, 102, 0, '2020-08-10', '22:22:00'),
(7, 124, 35626, 98.00, 25, 38.00, 120, 1, '2020-08-11', '12:00:00'),
(8, 91, 685112, 95.00, 15, 36.00, 90, 0, '2020-08-20', '10:31:00'),
(9, 129, 221036, 99.00, 10, 365.00, 70, 0, '2020-08-22', '19:05:00'),
(10, 39, 545222, 98.00, 10, 36.00, 85, 0, '2020-08-22', '21:05:00'),
(11, 28, 151042, 97.00, 0, 36.00, 73, 0, '2020-08-30', '18:37:00'),
(12, 28, 151042, 97.00, 50, 36.00, 68, 0, '2020-09-11', '13:41:00'),
(13, 42, 313960, 96.00, 55, 53.00, 66, 0, '2021-01-11', '07:10:00'),
(14, 96, 39693, 36.00, 69, 66.00, 96, 1, '2021-02-06', '06:05:00');

-- --------------------------------------------------------

--
-- Table structure for table `protect`
--

CREATE TABLE IF NOT EXISTS `protect` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Photo` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `protect`
--

INSERT INTO `protect` (`ID`, `Photo`) VALUES
(1, 'http://139.59.38.160/Corona/who/blue-1.png'),
(2, 'http://139.59.38.160/Corona/who/blue-2.png'),
(3, 'http://139.59.38.160/Corona/who/blue-3.png'),
(4, 'http://139.59.38.160/Corona/who/blue-4.png');

-- --------------------------------------------------------

--
-- Table structure for table `questionans`
--

CREATE TABLE IF NOT EXISTS `questionans` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `IDQuestion` int(11) NOT NULL,
  `ANS` int(11) NOT NULL,
  `Desc` mediumtext NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) NOT NULL,
  `Question` mediumtext NOT NULL,
  `Ans` tinyint(1) NOT NULL,
  `User` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`ID`, `Category`, `Question`, `Ans`, `User`, `Date`, `Time`) VALUES
(1, 'Travel', ' Have you recently traveled to an area with known local spread of COVID-19?', 0, '', '0000-00-00', '00:00:00'),
(2, 'Contact', 'Have you come into close contact i.e. within 6 feet with someone who has a laboratory confirmed COVID-19 diagnosis in the past 14 days?', 0, '', '0000-00-00', '00:00:00'),
(3, 'Fever', 'Are you having body temperature more than 36 degree', 0, 'ADMIN123', '2020-06-22', '12:56:43'),
(4, 'What is your current respiratory level?', 'Enter the number of breaths per minute', 2, 'admin123', '2020-06-22', '22:38:39'),
(5, 'This will be some question', 'Enter a value', 1, 'admin123', '2020-06-29', '21:20:31'),
(6, 'Have you had any recent infections?', 'Many forms of infections can cause musculoskeletal pain, whether they are located in musculoskeletal tissues or not.', 1, '', '0000-00-00', '00:00:00'),
(7, 'Have you had previous vascular, spinal or joint replacement surgery?', 'Scars from these procedures are often less than obvious especially in the practice where gowning is not performed for routine visits. Surgeries such as enarterectomies, aneurysm repair, spinal fusions and hip replacements can all affect the type of treatment that can be rendered.', 1, '', '0000-00-00', '00:00:00'),
(8, 'Some test question', 'This is some test', 1, 'admin123', '2020-06-22', '22:05:46');

-- --------------------------------------------------------

--
-- Table structure for table `relationships`
--

CREATE TABLE IF NOT EXISTS `relationships` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Relation` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `relationships`
--

INSERT INTO `relationships` (`ID`, `Relation`, `Date`, `Time`) VALUES
(1, 'Father', '0000-00-00', '00:00:00'),
(2, 'Mother', '0000-00-00', '00:00:00'),
(3, 'Brother', '0000-00-00', '00:00:00'),
(4, 'Sister', '0000-00-00', '00:00:00'),
(5, 'Son', '0000-00-00', '00:00:00'),
(6, 'Daughter', '0000-00-00', '00:00:00'),
(7, 'Spouse', '0000-00-00', '00:00:00'),
(8, 'Other', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE IF NOT EXISTS `requests` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `User` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `isActive` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`ID`, `Name`, `User`, `Date`, `Time`, `isActive`) VALUES
(1, 'Sick Note', 'ADMIN123', '2020-06-22', '11:45:11', 0),
(2, 'Biomarker and clotting test form', 'ADMIN123', '2020-06-22', '11:46:04', 1),
(3, 'Swab form', 'ADMIN123', '2020-06-22', '11:46:15', 1),
(4, 'X rays', 'ADMIN123', '2020-06-22', '11:46:27', 1),
(5, 'Antibody test', '', '0000-00-00', '00:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `risk_factors`
--

CREATE TABLE IF NOT EXISTS `risk_factors` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Factors` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `risk_factors`
--

INSERT INTO `risk_factors` (`ID`, `Factors`, `Date`, `Time`) VALUES
(1, 'Hypertension', '0000-00-00', '00:00:00'),
(2, 'Heart Disease', '0000-00-00', '00:00:00'),
(3, 'Lung disease (like asthma)', '0000-00-00', '00:00:00'),
(4, 'Cancer', '0000-00-00', '00:00:00'),
(5, 'Obesity', '0000-00-00', '00:00:00'),
(6, 'HIV', '0000-00-00', '00:00:00'),
(7, 'Other', '0000-00-00', '00:00:00'),
(8, 'Unknown : I am unaware of any risk factors', '0000-00-00', '00:00:00'),
(9, 'None', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `sampletype`
--

CREATE TABLE IF NOT EXISTS `sampletype` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `sampletype`
--

INSERT INTO `sampletype` (`ID`, `Name`, `Date`, `Time`) VALUES
(1, 'Sputum', '0000-00-00', '00:00:00'),
(2, 'Nasopharyngeal Swab', '0000-00-00', '00:00:00'),
(3, 'Throat Swab', '0000-00-00', '00:00:00'),
(4, 'Nasal Swab', '0000-00-00', '00:00:00'),
(5, 'Tracheal Aspirate', '0000-00-00', '00:00:00'),
(6, 'Broncho Alveolar Lavage', '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `secondlevelquestions`
--

CREATE TABLE IF NOT EXISTS `secondlevelquestions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Question` mediumtext NOT NULL,
  `isActive` tinyint(4) NOT NULL DEFAULT '1',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `secondlevelquestions`
--

INSERT INTO `secondlevelquestions` (`ID`, `Question`, `isActive`, `Date`, `Time`) VALUES
(1, 'I am already a positively diagnosed COVID-19 patient and wish to virtually consult with the doctor ', 1, '0000-00-00', '00:00:00'),
(2, 'I came into contact with a positive COVID-19 patient and wish to virtually consult with a doctor to determine if I should do a COVID-19 test', 1, '0000-00-00', '00:00:00'),
(3, 'I do not want to consult the doctor, all I want is the form to take to the lab to get a COVID-19 test done', 1, '0000-00-00', '00:00:00'),
(4, 'I am experiencing some symptoms and want to know if it is COVID-19', 1, '0000-00-00', '00:00:00'),
(5, 'I have already consulted DigiDoc through this medium, but wish to seek another consult with DigiDoc', 1, '0000-00-00', '00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `sessiondatafrompatient`
--

CREATE TABLE IF NOT EXISTS `sessiondatafrompatient` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDSession` int(11) NOT NULL,
  `IDPatient` int(11) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Files` varchar(200) NOT NULL,
  `isComplete` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `startsession`
--

CREATE TABLE IF NOT EXISTS `startsession` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OTP` int(11) NOT NULL,
  `PatientID` int(11) NOT NULL,
  `IDDoc` int(11) NOT NULL,
  `Description` mediumtext NOT NULL,
  `Files` varchar(200) NOT NULL,
  `NextDate` date NOT NULL,
  `NextTime` time NOT NULL,
  `isComplete` tinyint(4) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `IP` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Dumping data for table `startsession`
--

INSERT INTO `startsession` (`ID`, `OTP`, `PatientID`, `IDDoc`, `Description`, `Files`, `NextDate`, `NextTime`, `isComplete`, `Date`, `Time`, `IP`) VALUES
(1, 0, 36, 4, 'GYFHCJVJGUG\n\n\n\nIBIBIVUVU\n\nYVUV', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '20:36:16', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(2, 0, 36, 4, 'HESJDJHDHDD', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:05:12', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(3, 0, 37, 4, 'SHSHDHDHDBDDND\n\nDNDNDN\n\n\n\nSHDNDNDJDJJD', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:05:36', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(4, 0, 52, 4, 'DHSHDHDHXBDJAKSKAKA', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:05:54', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(5, 0, 36, 4, 'HXUFUCCUCJXHCXN BV', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:14:51', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(6, 0, 36, 4, '5FTFYCJCJCVJKVJF', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:23:45', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(7, 0, 36, 4, 'HFUFHJCU\n\nBZGXHXHCJGI\n\nHXCJCJI', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:32:31', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(8, 0, 52, 4, 'DBJDJDHD', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:41:33', 'FE80::CFCC:B2C4:19F2:4693%WLAN0'),
(9, 0, 36, 4, 'CHFCJC', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:51:49', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(10, 0, 36, 4, 'THFT', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '21:55:00', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(11, 0, 30, 4, 'DHCJFJFJCJCJC', '', '0000-00-00', '00:00:00', 1, '2020-07-28', '23:07:54', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(12, 0, 112, 113, 'START OF SESSION', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '12:12:36', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(13, 0, 112, 113, 'TESTING', '', '2020-08-13', '15:51:00', 1, '2020-08-05', '12:20:25', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(14, 0, 112, 113, 'END', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '13:42:39', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(15, 0, 112, 113, 'FINISH', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '13:47:38', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(16, 0, 112, 113, 'FINISH', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '13:54:48', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(17, 0, 117, 116, 'MEDICATION TO TAKE\n\n1 X PANADO\n2 X LAUGHTER', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '20:25:30', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(18, 0, 117, 116, 'NEW SESSION', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '20:29:42', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(19, 0, 112, 113, 'TEST SESSION ENDS', '', '0000-00-00', '00:00:00', 1, '2020-08-05', '23:01:23', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(20, 0, 118, 113, 'START', '', '2020-08-12', '22:38:00', 1, '2020-08-06', '19:04:36', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(21, 0, 118, 113, 'END', '', '0000-00-00', '00:00:00', 1, '2020-08-06', '19:37:10', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(22, 0, 121, 113, 'START', '', '2020-08-17', '21:07:00', 0, '2020-08-06', '19:37:38', '2409:4065:D86:CC4B:BA64:91FF:FEE1:7A7E%38'),
(23, 0, 117, 116, 'TEST 1', '', '0000-00-00', '00:00:00', 1, '2020-08-06', '21:13:34', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(24, 0, 123, 116, 'GYVTVYVGVYYV', '', '0000-00-00', '00:00:00', 1, '2020-08-06', '22:49:12', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(25, 0, 123, 116, 'GYVTVYVGVYYV', '', '0000-00-00', '00:00:00', 1, '2020-08-06', '22:51:43', 'FE80::EC1F:72FF:FE16:3605%P2P0'),
(26, 0, 28, 111, 'GOOD NOW', '', '2021-03-08', '10:49:00', 0, '2021-02-06', '06:19:29', 'FE80::54B8:2FF:FE0D:AEEB%WLAN0');

-- --------------------------------------------------------

--
-- Table structure for table `symptoms`
--

CREATE TABLE IF NOT EXISTS `symptoms` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Category` varchar(200) NOT NULL,
  `Photo` varchar(200) NOT NULL,
  `Description` mediumtext NOT NULL,
  `DigiCategory` int(11) NOT NULL,
  `isActive` int(11) NOT NULL DEFAULT '1',
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `symptoms`
--

INSERT INTO `symptoms` (`ID`, `Category`, `Photo`, `Description`, `DigiCategory`, `isActive`, `Date`, `Time`, `User`) VALUES
(1, 'Fever', 'http://139.59.38.160/Corona/symptoms/c1.png', '', 4, 1, '2020-06-22 18:30:00', '14:21:47', 'ADMIN123'),
(2, 'Cough', 'http://139.59.38.160/Corona/symptoms/c2.png', '', 4, 1, '2020-03-25 10:27:37', '00:00:00', ''),
(3, 'Shortness of breath/Difficulty to breathe', 'http://139.59.38.160/Corona/symptoms/c3.png', '', 4, 1, '2020-03-25 10:29:21', '00:00:00', ''),
(4, 'Chills', 'http://139.59.38.160/Corona/symptoms/diff.jpg', '', 3, 1, '2020-03-29 12:31:27', '00:00:00', ''),
(6, 'Diarrhoea', '', '', 3, 1, '2020-06-25 12:52:28', '00:00:00', ''),
(7, 'Nausea / Vomiting', '', '', 3, 1, '2020-06-25 12:52:28', '00:00:00', ''),
(8, 'Sore Throat', '', '', 2, 1, '2020-06-25 12:52:44', '00:00:00', ''),
(9, 'Other', '', '', 2, 1, '2020-06-25 12:52:44', '00:00:00', ''),
(10, 'None', '', '', 0, 1, '2020-07-12 18:30:00', '18:17:31', 'admin123'),
(11, 'Weakness/Difficulty in moving', '', '', 4, 1, '2020-07-13 18:30:00', '20:32:44', 'admin123'),
(13, 'Body aches', '', '', 2, 1, '2020-07-13 18:30:00', '20:33:14', 'admin123'),
(14, 'Loss of Taste', '', '', 3, 1, '2020-07-13 18:30:00', '20:33:34', 'admin123'),
(15, 'Loss of Smell', '', '', 3, 1, '2020-07-13 18:30:00', '20:33:46', 'admin123'),
(16, 'Stuffy Nose', '', '', 2, 1, '2020-07-13 18:30:00', '20:34:07', 'admin123'),
(17, 'Headache', '', '', 2, 1, '2020-07-13 18:30:00', '20:34:24', 'admin123'),
(18, 'Diabetes', '', '', 4, 1, '2020-07-27 18:30:00', '20:44:32', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `timeslot`
--

CREATE TABLE IF NOT EXISTS `timeslot` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Slot` varchar(200) NOT NULL,
  `ActualTime` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDSalon` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `timeslot`
--

INSERT INTO `timeslot` (`ID`, `Slot`, `ActualTime`, `Date`, `IDSalon`) VALUES
(1, '9:30AM', 9, '2020-02-28 09:20:48', 1),
(2, '10:00AM', 9, '2020-02-28 09:20:48', 1),
(3, '10:30AM', 10, '2020-02-28 09:21:09', 1),
(4, '11:00AM', 11, '2020-02-28 09:21:09', 1),
(5, '11:30AM', 11, '2020-02-28 09:21:20', 1),
(6, '12:00PM', 12, '2020-02-28 09:21:20', 1),
(7, '12:30PM', 12, '2020-02-28 09:21:53', 1),
(8, '1:00PM', 13, '2020-02-28 09:21:53', 1),
(9, '1:30PM', 13, '2020-02-28 09:22:04', 1),
(10, '2:00PM', 14, '2020-02-28 09:22:04', 1),
(11, '2:30PM', 14, '2020-02-28 09:22:16', 1),
(12, '3:00PM', 15, '2020-02-28 09:22:16', 1),
(13, '3:30PM', 15, '2020-02-28 09:22:30', 1),
(14, '4:00PM', 16, '2020-02-28 09:22:30', 1),
(15, '4:30PM', 16, '2020-02-28 09:22:47', 1),
(16, '5:00PM', 17, '2020-02-28 09:22:47', 1);

-- --------------------------------------------------------

--
-- Table structure for table `travel`
--

CREATE TABLE IF NOT EXISTS `travel` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Country` varchar(200) NOT NULL,
  `Date_of_Departure` date NOT NULL,
  `Date_of_Return` date NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `travelquestions`
--

CREATE TABLE IF NOT EXISTS `travelquestions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IDUser` int(11) NOT NULL,
  `Country` varchar(200) NOT NULL,
  `Region` varchar(200) NOT NULL,
  `isFlight` tinyint(4) NOT NULL DEFAULT '0',
  `FlightNumber` int(11) NOT NULL,
  `Seat` int(11) NOT NULL,
  `ArrivalDate` date NOT NULL,
  `DepartureDate` date NOT NULL,
  `Accommodation` int(11) NOT NULL,
  `OtherDetails` mediumtext NOT NULL,
  `OtherTravel` tinyint(4) NOT NULL DEFAULT '0',
  `Date1` date NOT NULL,
  `Carrier` mediumtext NOT NULL,
  `Seat2` date NOT NULL,
  `DepartedFrom` mediumtext NOT NULL,
  `ArriveI` date NOT NULL,
  `Details2` mediumtext NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `usersymptoms`
--

CREATE TABLE IF NOT EXISTS `usersymptoms` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `OTP` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `symID` int(11) NOT NULL,
  `otherreason` varchar(200) NOT NULL,
  `oxygen` float(10,2) NOT NULL,
  `breaths` int(11) NOT NULL,
  `issue` varchar(200) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `feedback` mediumtext NOT NULL,
  `statusdate` date NOT NULL,
  `statustime` time NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Dumping data for table `usersymptoms`
--

INSERT INTO `usersymptoms` (`ID`, `OTP`, `UserID`, `symID`, `otherreason`, `oxygen`, `breaths`, `issue`, `status`, `feedback`, `statusdate`, `statustime`, `Date`, `Time`, `User`) VALUES
(1, 275845, 78, 14, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-07', '19:52:00', ''),
(2, 275845, 78, 15, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-07', '19:52:00', ''),
(3, 294411, 78, 14, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-07', '20:04:00', ''),
(4, 294411, 78, 15, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-07', '20:04:00', ''),
(5, 35626, 124, 2, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-07', '00:00:00', ''),
(6, 294411, 78, 14, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-08', '08:16:00', ''),
(7, 294411, 78, 15, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-08', '08:16:00', ''),
(8, 294411, 78, 14, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-08', '09:11:00', ''),
(9, 294411, 78, 15, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-08', '09:11:00', ''),
(10, 294411, 78, 0, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '18:20:00', ''),
(11, 74917, 65, 13, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:02:00', ''),
(12, 74917, 65, 16, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:02:00', ''),
(13, 215807, 49, 2, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(14, 215807, 49, 4, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(15, 215807, 49, 3, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(16, 215807, 49, 14, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(17, 215807, 49, 16, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(18, 215807, 49, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-10', '22:20:00', ''),
(19, 35626, 124, 10, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-11', '11:59:00', ''),
(20, 685112, 91, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-30', '00:00:00', ''),
(21, 221036, 129, 18, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-22', '00:00:00', ''),
(22, 545222, 39, 17, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-22', '21:00:00', ''),
(23, 545222, 39, 13, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-22', '21:00:00', ''),
(24, 545222, 39, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-22', '21:00:00', ''),
(25, 326759, 47, 10, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-29', '00:00:00', ''),
(26, 151042, 28, 10, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-08-30', '18:32:00', ''),
(27, 151042, 28, 3, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '12:22:00', ''),
(28, 151042, 28, 11, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '12:22:00', ''),
(29, 151042, 28, 3, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '13:39:00', ''),
(30, 151042, 28, 11, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '13:39:00', ''),
(31, 151042, 28, 3, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '13:42:00', ''),
(32, 151042, 28, 10, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '13:42:00', ''),
(33, 151042, 28, 11, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2020-09-11', '13:42:00', ''),
(34, 313960, 42, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-11', '07:09:00', ''),
(35, 313960, 42, 4, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-11', '07:09:00', ''),
(36, 313960, 42, 2, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-11', '07:09:00', ''),
(37, 232323, 130, 3, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-13', '12:03:00', ''),
(38, 232323, 130, 2, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-13', '12:03:00', ''),
(39, 232323, 130, 17, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-13', '12:03:00', ''),
(40, 545222, 39, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-25', '19:03:00', ''),
(41, 545222, 39, 13, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-25', '19:03:00', ''),
(42, 545222, 39, 17, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-01-25', '19:03:00', ''),
(43, 39693, 96, 10, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-02-02', '18:47:00', ''),
(44, 39693, 96, 1, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-02-06', '06:05:00', ''),
(45, 39693, 96, 13, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-02-06', '06:05:00', ''),
(46, 39693, 96, 15, '', 0.00, 0, '', 1, '', '0000-00-00', '00:00:00', '2021-02-06', '06:05:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `users_emergency_contacts`
--

CREATE TABLE IF NOT EXISTS `users_emergency_contacts` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) NOT NULL,
  `Contact_Name` varchar(255) NOT NULL,
  `Contact_Phone_No` varchar(200) NOT NULL,
  `isRequested` tinyint(4) NOT NULL DEFAULT '0',
  `isAccepted` tinyint(4) NOT NULL DEFAULT '0',
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `User` varchar(255) NOT NULL,
  `IP` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `users_emergency_contacts`
--

INSERT INTO `users_emergency_contacts` (`ID`, `User_ID`, `Contact_Name`, `Contact_Phone_No`, `isRequested`, `isAccepted`, `Date`, `Time`, `User`, `IP`) VALUES
(1, 54, 'Coll Unknown', '9085719129', 0, 0, '2020-06-28', '21:39:00', '', 'fe80::a5e:88a5%rmnet0');

-- --------------------------------------------------------

--
-- Table structure for table `user_details`
--

CREATE TABLE IF NOT EXISTS `user_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Photo` varchar(255) DEFAULT 'profile_image.png',
  `PhoneNo` varchar(200) NOT NULL,
  `Password` varchar(200) NOT NULL,
  `Gender` varchar(11) NOT NULL,
  `DOB` date NOT NULL,
  `Role` int(11) NOT NULL DEFAULT '1',
  `IDPatient` varchar(200) NOT NULL,
  `UniqueID` mediumtext NOT NULL,
  `Emergencyname` varchar(200) NOT NULL,
  `Emergencyno` varchar(200) NOT NULL,
  `Passport` varchar(200) NOT NULL,
  `PodOwner` int(11) NOT NULL,
  `Relation` int(11) NOT NULL,
  `Latitude` float(10,6) DEFAULT NULL,
  `Longitude` float(10,6) DEFAULT NULL,
  `Rating` float(10,2) NOT NULL,
  `TotalRating` int(11) NOT NULL,
  `Is_Blocked` tinyint(1) NOT NULL DEFAULT '0',
  `User_Referrence_Code` varchar(20) DEFAULT NULL,
  `FirebaseToken` varchar(255) DEFAULT NULL,
  `Travel` tinyint(4) NOT NULL DEFAULT '0',
  `Contact` tinyint(4) NOT NULL DEFAULT '0',
  `Fever` tinyint(4) NOT NULL DEFAULT '0',
  `symDate` date NOT NULL,
  `Test` tinyint(4) NOT NULL DEFAULT '0',
  `testDate` date NOT NULL,
  `Date` date DEFAULT NULL,
  `Time` time DEFAULT NULL,
  `User` varchar(255) DEFAULT NULL,
  `IP` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=131 ;

--
-- Dumping data for table `user_details`
--

INSERT INTO `user_details` (`ID`, `Name`, `Email`, `Photo`, `PhoneNo`, `Password`, `Gender`, `DOB`, `Role`, `IDPatient`, `UniqueID`, `Emergencyname`, `Emergencyno`, `Passport`, `PodOwner`, `Relation`, `Latitude`, `Longitude`, `Rating`, `TotalRating`, `Is_Blocked`, `User_Referrence_Code`, `FirebaseToken`, `Travel`, `Contact`, `Fever`, `symDate`, `Test`, `testDate`, `Date`, `Time`, `User`, `IP`) VALUES
(102, 'A Alli', NULL, 'profile_image.png', '0825727463', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '9605220326083', '', '', '', 49, 6, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(103, 'A Alli', NULL, 'profile_image.png', '0649001669', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-05-22', 1, '', '0008110179081', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(17, 'TestMo', NULL, 'Profile.png', '0827865662', '7c4a8d09ca3762af61e59520943dc26494f8941b', 'Male', '0000-00-00', 1, '', 'MO123', '', '', '', 128, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-15', '20:15:00', 'admin123', '102.182.107.72'),
(4, 'testDoctor', '', '', '0747865662', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1997-02-14', 2, 'DD0004', '', 'Hoosain', '27 83 786 8025', '9702145662088', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-07-19', '2020-08-09', '23:20:45', 'testDoctor', 'fe80::b849:45e:6624:d7c7%wlan0'),
(8, 'ASHRAF ABDOOLA', NULL, 'Profile.png', '0829012786', 'c6618e109dc4ad8471170e759c5aba449c4e30e4', '', '0000-00-00', 1, 'DD0008', '', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-13', '23:03:00', NULL, NULL),
(9, 'YAHYA MOOLA', NULL, 'Profile.png', '0682217061', '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', 2, 'DD0009', '', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-14', '15:49:00', NULL, NULL),
(104, 'Imraan Noorbhai', NULL, 'profile_image.png', '0834080201', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '6904025136083', '', '', '', 49, 4, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(105, 'Faheema Noorbhai', NULL, 'profile_image.png', '7004130157089', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-05-22', 1, '', '7004130157089', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(49, 'Nazeem Alli', '', '', '0832277786', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1995-02-08', 3, 'DD0049', '6802175144081', 'Dad', '0829012786', '6802175144081', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-10', '22:20:13', 'Nazeem Alli', '192.143.30.233'),
(50, 'MAHMOOD', NULL, 'profile_image.png', '0832844548', '7c222fb2927d828af22f592134e8932480637c0d', '', '2000-06-29', 3, 'DD0050', '9906285023080', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-29', '21:54:00', NULL, NULL),
(28, 'YAKOOB KHAN', '', '', '', 'eeb0c030be769d01ae303f28de596db3547dece5', 'Male', '1934-10-10', 1, 'DD0028', '3410105098089', 'Riaz Khan', '27847860809', '3410105098089', 42, 1, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-09-11', '13:39:34', 'YAKOOB KHAN', 'fe80::88a8:43ff:fe07:13e8%dummy0'),
(29, 'MARIAM', NULL, 'profile_image.png', '0822234312', '67c69dc7e718b222e46095578132b4d7ec3c6a5a', 'Female', '1941-09-19', 1, 'DD0029', '4109190110085', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-10', '11:33:00', 'admin123', '102.182.107.248'),
(90, 'PatientTest', NULL, 'profile_image.png', '0747865662', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1980-01-01', 1, '', '8001011234567', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(96, 'testingPatient', '', '', '1234567890', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1999-01-01', 1, 'DD0096', '9901012345678', 'Ahmad Kajee Kadwa', '0837864340', '9901012345678', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-02', '2021-02-06', '06:04:54', 'testingPatient', 'fe80::54b8:2ff:fe0d:aeeb%wlan0'),
(97, 'AYESHA', NULL, 'profile_image.png', '0718181302', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, 'DD0097', '0205220212083', '', '', '', 0, 6, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-10', '17:23:00', 'admin123', '102.182.107.248'),
(98, 'M.S Alli', NULL, 'profile_image.png', '3811115055086', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-05-22', 1, '', '3811115055086', '', '', '', 49, 1, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(99, 'F.K Alli', NULL, 'profile_image.png', '479150082081', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '479150082081', '', '', '', 49, 2, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(100, 'MN Alli', NULL, 'profile_image.png', '6802175144081', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '6802175144081', '', '', '', 49, 7, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(39, 'ADILAH KHAN', '', '', '0646828518', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1973-01-31', 1, 'DD0039', '7301310164087', 'Riaz dixon', '0714859521', '7301310164087', 42, 7, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2021-01-25', '19:03:07', 'ADILAH KHAN', 'fe80::54b8:2ff:fe0d:aeeb%wlan0'),
(40, 'Nabeelah Khan', NULL, 'profile_image.png', '0827185432', '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', 1, '', '9701090142088', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, NULL, NULL),
(41, 'Haamid Khan', NULL, 'profile_image.png', '0827409167', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-02-08', 1, '', '0202085477083', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(42, 'RIAZ KHAN', '', '', '0847860809', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1967-10-07', 3, 'DD0042', '6710075210086', 'Adilah Dear', '27646828518', '6710075210086', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2021-01-11', '07:09:12', 'RIAZ KHAN', 'fe80::4a89:6201%rmnet2'),
(43, 'Thauheed Khan', NULL, 'profile_image.png', '0815918569', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1998-11-28', 1, '', '9811285099082', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(44, 'Zaheer Khan', NULL, 'profile_image.png', '0828022903', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1969-09-07', 1, '', '6909075124087', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(45, 'Amaarah Modan', NULL, 'profile_image.png', '0711871095', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1999-02-24', 1, '', '9902240452084', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(46, 'Zaakir Modan', NULL, 'profile_image.png', '0715313492', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1995-11-22', 1, '', '9511225300083', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(47, 'ANISA KHAN', '', '', '0827838430', 'acf4e2dca23be2e64d5b9533cd97e52b28975fc6', 'Female', '1973-05-06', 1, 'DD0047', '7305060168081', 'Zaakir Modan', '0715313492', '7305060168081', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-29', '13:56:01', 'ANISA KHAN', 'fe80::88a8:43ff:fe07:13e8%dummy0'),
(101, 'F Alli', NULL, 'profile_image.png', '0726986786', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '6903280121087', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(91, 'testPatient', '', '', '0812345678', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1900-01-01', 1, 'DD0091', '1234567890123', 'Hoosain', '27 83 786 8025', '1234567890123', 89, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-30', '19:20:19', 'testPatient', 'fe80::62f:8271:a140:235%wlan0'),
(92, 'Fahmida Hoosen', '', '', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1970-05-02', 1, 'DD0092', '7410225249089', '1', '1', '7005020655082', 55, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-02', '2020-08-02', '13:11:28', 'Fahmida Hoosen', 'fe80::e566:b060:f74b:5c5f%wlan0'),
(93, 'Afsana Sayed', NULL, 'profile_image.png', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1975-11-25', 1, '', '7511250199089', '', '', '', 55, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(94, 'Muhammad Azhar Sayed', '', '', '0796598267', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1996-12-24', 1, 'DD0094', '9612245683084', 'Mum', '0798450538', '9612245683084', 55, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-03', '2020-08-03', '18:19:28', 'Muhammad Azhar Sayed', 'fe80::ae5f:3eff:fe35:c9bc%wlan0'),
(55, 'MAHOMED KHAN', '', '', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1966-08-18', 3, 'DD0055', '6608185152083', 'Ashraf Khan', '0745286818', '6608185152083', 55, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-07-31', '2020-08-10', '19:04:00', 'Mahomed Khan', 'fe80::aadb:3ff:fe59:fed6%wlan0'),
(56, 'Dr Moola Locum', NULL, 'profile_image.png', '068 221 7061', '7c222fb2927d828af22f592134e8932480637c0d', '', '0000-00-00', 2, '', '12345678912', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, NULL, NULL),
(95, 'Aaliyah Sayed', NULL, 'profile_image.png', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1998-06-15', 1, '', '9806150519081', '', '', '', 55, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(58, 'AHMAD', NULL, 'profile_image.png', '0837211233', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1993-12-11', 1, 'DD0058', '9311045256080', '', '', '', 50, 3, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-31', '18:52:00', 'admin123', '102.182.107.248'),
(59, 'HUSNA', NULL, 'profile_image.png', '0640530761', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2000-12-23', 1, 'DD0059', '0012230124088', '', '', '', 50, 4, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-07-31', '19:59:00', 'admin123', '102.182.107.248'),
(60, 'MUHAMMAD MOTALA', '', '', '0746016089', 'f7c3bc1d808e04732adf679965ccc34ca7ae3441', 'Male', '1995-05-03', 1, 'DD0060', '9503055552081', 'Ismail Omar', '082 809 0909', '9503055552081', 50, 3, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-07-31', '2020-07-31', '22:03:48', 'MUHAMMAD MOTALA', 'fe80::4c66:41ff:fe6e:61ad%p2p0'),
(61, 'Aisha', NULL, 'profile_image.png', '0717000331', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1997-09-04', 1, '', '9709040518083', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(62, 'MUHAMMED OMAR', '', '', '0798090909', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1992-08-19', 1, 'DD0062', '920819514087', 'Ismail Omar', '082 809 0909', '920819514087', 50, 3, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-01', '2020-08-01', '08:17:26', 'MUHAMMED OMAR', 'fe80::4c66:41ff:fe6e:61ad%p2p0'),
(63, 'Fathima', NULL, 'profile_image.png', '0832844638', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1996-07-01', 1, '', '9607010496084', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(64, 'Adamjee', NULL, 'profile_image.png', '0810205231', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1995-09-20', 1, '', '9509205409080', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(65, 'EBRAHIM', '', '', '0842577619', 'e08ba8ff72769e31898b224e42a2ac3ffd412025', 'Male', '1994-12-05', 1, 'DD0065', '9412055931081', 'Papa', '082 809 0909', '9412055931081', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-10', '22:01:44', 'EBRAHIM', '100.105.179.17'),
(66, 'Fathima G', NULL, 'profile_image.png', '0847867619', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1993-09-02', 1, '', '9309020539082', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(67, 'Maryam', NULL, 'profile_image.png', '0842577619', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2020-02-13', 1, '', '2002130944089', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(68, 'Zakiyah', NULL, 'profile_image.png', '0832844628', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1974-07-15', 1, '', '7407150196087', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(69, 'Farida', NULL, 'profile_image.png', '0823797867', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1948-08-24', 1, '', '4808240083081', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(70, 'Khalid Hassen', NULL, 'profile_image.png', '0762665011', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2016-04-20', 1, '', '1604206428086', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(71, 'Unaisa Hassen', NULL, 'profile_image.png', '0762665011', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2012-03-31', 1, '', '1203315520089', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(72, 'Urwah Hassen', NULL, 'profile_image.png', '0762665011', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2013-08-31', 1, '', '1308310668088', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(73, 'Zainul Abedeen Hassen', NULL, 'profile_image.png', '0762665011', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1984-02-02', 1, '', '8402025094087', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(74, 'Shaakira Hassen', NULL, 'profile_image.png', '0721446125', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1988-11-20', 1, '', '8811200128082', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(125, 'MAHOMED REHAAN AMOD', NULL, 'profile_image.png', '0828778655', 'c971859f9881b1abc69029583057eef75228d21f', 'Male', '1998-08-14', 3, 'DD0125', '9808145367089', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-10', '19:07:00', 'admin123', '102.182.107.248'),
(76, 'TASNEIM', '', '', '0717016940', '966e039396206f363ca0598d4e6ce8be2ff25cec', 'Female', '1978-03-15', 1, 'DD0076', '7803150204089', 'Anwar Sayed', '083 761 9166', '7803150204089', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-02', '2020-09-14', '13:18:00', 'TASNEIM SAYED', 'fe80::e850:8bff:fef8:3363%p2p0'),
(77, 'AMMAARAH', NULL, 'profile_image.png', '0725283817', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2007-11-14', 1, 'DD0077', '0711140499080', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-02', '19:53:00', 'admin123', '102.182.107.248'),
(78, 'FAZILA', '', '', '0828680716', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1958-12-19', 1, 'DD0078', '5812190016082', 'Taahira Sayed', '072 528 3817', '5812190016082', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-08-02', '2020-08-10', '18:19:45', 'FAZILA', 'fe80::a425:eeff:fe64:121e%dummy0'),
(79, 'Ashraf Ali Khan', NULL, 'profile_image.png', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1997-02-14', 1, '', '9702145662088', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(80, 'Fahmida Hoosen', NULL, 'profile_image.png', '0829390907', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1970-05-02', 1, '', '7005020655082', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(81, 'SABERA BEE KHAN', '', '', '0795286828', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1945-12-13', 1, 'DD0081', '4512130467087', 'mahomed Khan', '082 939 0907', '4512130467087', 55, 2, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '2020-07-30', '2020-07-30', '21:51:12', 'SABERA BEE KHAN', 'fe80::aadb:3ff:fe59:fed6%wlan0'),
(82, 'YAHYA', NULL, 'profile_image.png', '0682217061', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1995-07-16', 2, 'DD0082', '9507165212080', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-12', '09:14:00', 'admin123', '102.182.107.248'),
(84, 'Sumaiya Modan', NULL, 'profile_image.png', '0823075960', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '0000-00-00', 1, '', '7307300150085', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(85, 'Zia Modan', NULL, 'profile_image.png', '0844445666', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1986-01-31', 1, '', '8601315217080', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(86, 'Fathima Modan', NULL, 'profile_image.png', '0760720680', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '1946-03-28', 1, '', '4603280053080', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(87, 'Imran Hussain', NULL, 'profile_image.png', '0765895541', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-10-13', 1, '', '0210135055080', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(88, 'Aaliyah Hussain', NULL, 'profile_image.png', '0642764214', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2001-06-27', 1, '', '0106271022084', '', '', '', 42, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(106, 'Muzzamil Noorbhai', NULL, 'profile_image.png', '9612165057087', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '2002-05-22', 1, '', '9612165057087', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(107, 'Mohammed Salmaan Noorbhai', NULL, 'profile_image.png', '0311095118080', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '0311095118080', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(108, 'Juwairiyah Noorbhai', NULL, 'profile_image.png', '0708280903087', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '0708280903087', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(109, 'Muaza Noorbhai', NULL, 'profile_image.png', '0007030290089', '7c222fb2927d828af22f592134e8932480637c0d', 'Female', '2002-05-22', 1, '', '0007030290089', '', '', '', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(110, 'Raheem Haroon', NULL, 'profile_image.png', '0837940726', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1971-06-08', 3, '', '7106085522082', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', NULL, NULL, 'admin123', '102.182.107.248'),
(111, 'ISMAIL OMAR', NULL, 'profile_image.png', '0828090909', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1967-02-23', 2, 'DD0111', '6702235166050', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-07', '00:22:00', 'admin123', '102.182.107.248'),
(124, 'ISMAIL OMAR', '', '', '', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1967-02-23', 1, 'DD0124', '1111111111111', 'Ahmad Kajee Kadwa', '0837864340', '1111111111111', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-11', '11:58:39', 'ISMAIL OMAR', '10.75.136.142'),
(126, 'SUHAIL ABDOOLA', '', '', '0739162541', '92b13f01ddee3622abf47ee1a20b14ed9e37d985', 'Male', '1995-02-08', 1, 'DD0126', '9502085121081', 'Dad', '0829012786', '9502085121081', 49, 8, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-18', '13:19:46', 'SUHAIL ABDOOLA', 'fe80::8eb8:4aff:fe42:9fb0%wlan0'),
(127, 'TESTYMPATIENT', '', '', '1234567890', '7c222fb2927d828af22f592134e8932480637c0d', 'Male', '1990-01-01', 1, 'DD0127', '2222222222222', 'Mom', '27 73 561 5100', '2222222222222', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-26', '22:07:28', 'TESTYMPATIENT', 'fe80::144c:ccff:fea0:414e%dummy0'),
(128, 'TESTPODOFFICER', NULL, 'profile_image.png', '1234567890', '01b307acba4f54f55aafc33bb06bbbf6ca803e9a', 'Male', '1984-07-12', 3, 'DD0128', '3333333333333', '', '', '', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-20', '10:20:00', 'admin123', '102.182.107.248'),
(129, 'SALEEM MOOLA', '', '', '0827512451', 'efb7be0de67436da19912804069ab091f5de678e', 'Male', '1967-11-08', 1, 'DD0129', '6711085237085', 'ICE Rafiah wife Moola', '27735615100', '6711085237085', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2020-08-22', '19:04:01', 'SALEEM MOOLA', 'fe80::443:d3ff:fe6f:c568%dummy0'),
(130, 'SHAKIRA', '', '', '0678284720', '23264aa6268488c2909ef81ead49e09e248d5d91', 'Female', '1977-08-21', 1, 'DD0130', '7708210140084', 'Raj', '27680113112', '7708210140084', 0, 0, NULL, NULL, 0.00, 0, 0, NULL, NULL, 0, 0, 0, '0000-00-00', 0, '0000-00-00', '2021-01-13', '12:02:39', 'SHAKIRA', '192.168.1.100');

-- --------------------------------------------------------

--
-- Table structure for table `zone`
--

CREATE TABLE IF NOT EXISTS `zone` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Token` varchar(200) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Address` varchar(200) NOT NULL,
  `Latitude` float(10,6) NOT NULL,
  `Longitude` float(10,6) NOT NULL,
  `User` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `zone`
--

INSERT INTO `zone` (`ID`, `Token`, `Name`, `Address`, `Latitude`, `Longitude`, `User`, `Date`, `Time`) VALUES
(1, '1922725378', 'TEST1', 'Basistha Mandir Road, Ganesh Nagar, Basistha, Guwahati, Assam, India', 26.096584, 91.790108, 'ADMIN123', '2020-04-03', '16:29:38'),
(3, '380321906', 'TEST3', 'Lakhimandir Path, Sarboday Nagar, Bhetapara, Guwahati, Assam, India', 26.112705, 91.785027, 'ADMIN123', '2020-04-03', '16:32:14'),
(10, '964121128', 'TEST4', 'Beltola Tiniali, Guwahati, Assam, India', 26.125536, 91.797104, 'ADMIN123', '2020-04-03', '16:57:51'),
(11, '243197734', 'TEST5', 'Wireless, Basisthpur, Guwahati, Assam, India', 26.134472, 91.791016, 'ADMIN123', '2020-04-03', '16:59:20'),
(12, '2065799624', 'Test6', 'Six Mile, Guwahati, Assam, India', 26.131351, 91.804413, 'ADMIN123', '2020-04-03', '17:00:07'),
(13, '1145963750', 'TEWST7', 'Chandmari, Guwahati, Assam, India', 26.189508, 91.772369, 'ADMIN123', '2020-04-03', '17:54:47'),
(14, '1761156950', 'TEST9', 'Zoo Road, GMC Ward Number 44, Dispur, Ganeshguri, Guwahati, Assam, India', 26.150513, 91.785049, 'ADMIN123', '2020-04-04', '12:03:45'),
(15, '729474239', 'TEST10', 'Nalbaripolicestation', 26.446795, 91.417236, 'ADMIN123', '2020-04-04', '12:10:02'),
(16, '1625394606', 'TEST11', 'NALBARI DC COURT', 26.446241, 91.431915, 'ADMIN123', '2020-04-04', '12:16:01'),
(20, '869771771', 'TEST10', 'Rio de Janeiro, State of Rio de Janeiro, Brazil', -22.906847, -43.172897, 'ADMIN123', '2020-04-06', '22:12:49'),
(21, '993098506', 'Test11', 'Santa Teresa, Rio de Janeiro - State of Rio de Janeiro, Brazil', -22.916265, -43.191635, 'ADMIN123', '2020-04-06', '22:14:11'),
(22, '852913240', 'Ganesh Nagar', 'Beltola Chariali, Guwahati, Assam, India', 26.115276, 91.798248, 'ADMIN123', '2020-04-07', '01:04:25'),
(23, '1690440185', 'Latakata', 'Latakata Road, Ganesh Nagar, Basistha, Guwahati, Assam, India', 26.098518, 91.796219, 'ADMIN123', '2020-04-07', '01:04:56'),
(25, '1918341440', 'DL1', 'test', 25.611452, 25.609442, 'ADMIN123', '2020-04-24', '21:38:08'),
(26, '1652502066', 'Test19', 'Police Bazar, Shillong, Meghalaya, India', 25.577921, 91.883698, 'ADMIN123', '2020-04-26', '18:31:58'),
(27, '1542698203', 'TEST20', 'Umsning, Meghalaya, India', 25.746809, 91.889168, 'ADMIN123', '2020-04-26', '18:38:44'),
(28, '1888348738', 'TEST21', 'North Eastern Hill University, Shillong, Meghalaya, India', 25.612099, 91.897713, 'ADMIN123', '2020-04-26', '18:46:51'),
(29, '1728919747', 'Nyclone san', 'Test', 26.093138, 91.796448, 'ADMIN123', '2020-05-08', '16:20:22');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
