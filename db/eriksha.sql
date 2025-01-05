-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 23, 2024 at 12:07 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `eriksha`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_tbl`
--

CREATE TABLE IF NOT EXISTS `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `accname` varchar(100) NOT NULL,
  `accountno` varchar(100) NOT NULL,
  `bank` varchar(100) NOT NULL,
  `pin` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `account_tbl`
--

INSERT INTO `account_tbl` (`id`, `uid`, `accname`, `accountno`, `bank`, `pin`, `amount`) VALUES
(1, '1', 'anandu', '12345', 'State Bank Of India', '1234', '248.11'),
(2, '1', 'Anandu Ajith', '1234567890', 'Federal Bank', '3456', '896.92'),
(3, '2', 'Abc', '775823998874', 'KGB', '7129', '2000000'),
(4, '2', 'Kallyani S  Nair', '123589644', 'SBI', '7692', '50000'),
(5, '5', 'carmel', '11223344555', 'gggggg', '688525', '9750'),
(6, '5', 'sbi', '255555', 'sbi', '688538', '99.98'),
(7, '6', 'gdhdhh', '6566658585', 'hdhdhdh', '9494994', '199.98');

-- --------------------------------------------------------

--
-- Table structure for table `driver_tbl`
--

CREATE TABLE IF NOT EXISTS `driver_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `vehicle_no` varchar(100) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `driver_status` varchar(100) NOT NULL,
  `ride_status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `driver_tbl`
--

INSERT INTO `driver_tbl` (`id`, `name`, `number`, `email`, `image`, `vehicle_no`, `latitude`, `longitude`, `password`, `driver_status`, `ride_status`) VALUES
(1, 'Amal', '7560855091', 'amal@gmail.com', 'amal.png', 'KL03W108', '10.0058871', '76.305147', '123', 'active', 'waiting'),
(2, 'anu', '7560855091', 'anu@gmail.com', 'amal.png', 'KL03W108', '10.0058829', '76.305136', '123', 'active', 'waiting'),
(3, 'Alan', '8787787878', 'josephmachambi@gmail.com', 'ion.jpg', 'kA16J234', '10.0054555', '76.3061645', '14', 'active', 'waiting');

-- --------------------------------------------------------

--
-- Table structure for table `feedback_tbl`
--

CREATE TABLE IF NOT EXISTS `feedback_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `driver_name` varchar(100) NOT NULL,
  `rating` varchar(100) NOT NULL,
  `feedback` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `feedback_tbl`
--

INSERT INTO `feedback_tbl` (`id`, `user_id`, `driver_name`, `rating`, `feedback`) VALUES
(1, '1', 'Amal', '5.0', 'good experience '),
(2, '1', 'Amal', '4.0', 'average'),
(3, '1', 'Amal', '5.0', 'good '),
(4, '1', 'Amal', '4.0', 'good'),
(5, '5', 'Amal', '5.0', 'great\n'),
(6, '5', 'Amal', '5.0', 'great\n');

-- --------------------------------------------------------

--
-- Table structure for table `payment_tbl`
--

CREATE TABLE IF NOT EXISTS `payment_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL,
  `driver_id` varchar(100) NOT NULL,
  `booking_id` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `card_name` varchar(100) NOT NULL,
  `acc_number` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `payment_tbl`
--

INSERT INTO `payment_tbl` (`id`, `user_id`, `driver_id`, `booking_id`, `date`, `amount`, `card_name`, `acc_number`) VALUES
(1, '1', '1', '8', '26-07-2024', '0.14', 'Anandu Ajith', '7736582458963333'),
(2, '1', '1', '8', '26-07-2024', '0.14', 'Anandu Ajith', '7736582458963333'),
(3, '2', '1', '8', '16-08-2024', '680.16', 'Kalliyani', '7555555555555555'),
(4, '1', '1', '9', '17-08-2024', '0.03', 'Anandu Ajith', '6523392107271294'),
(5, '1', '1', '10', '17-08-2024', '0.1', 'Anandu Ajith', '6523392107271294'),
(6, '3', '3', '13', '17-08-2024', '0.06', 'uffjfuf', '1124245446424643'),
(7, '1', '1', '7', '22-08-2024', '0.13', 'anandu', '12345'),
(8, '1', '1', '7', '22-08-2024', '0.13', 'anandu', '12345'),
(9, '1', '1', '7', '22-08-2024', '0.13', 'Anandu Ajith', '1234567890'),
(10, '2', '2', '15', '23-08-2024', '0.09', 'Abc', '775823998874'),
(11, '1', '1', '7', '23-08-2024', '100', 'Anandu Ajith', '1234567890'),
(12, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(13, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(14, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(15, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(16, '1', '1', '7', '23-08-2024', '100', 'Anandu Ajith', '1234567890'),
(17, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(18, '1', '1', '7', '23-08-2024', '100', 'anandu', '12345'),
(19, '1', '1', '7', '23-08-2024', '100', 'Anandu Ajith', '1234567890'),
(20, '1', '2', '5', '23-08-2024', '100', 'Anandu Ajith', '1234567890'),
(21, '5', '1', '17', '23-08-2024', '0.45', 'anandu', '12345'),
(22, '5', '1', '17', '23-08-2024', '0.45', 'anandu', '12345'),
(23, '5', '1', '16', '23-08-2024', '0.99', 'anandu', '12345'),
(24, '5', '1', '17', '23-08-2024', '250', 'anandu', '12345'),
(25, '5', '1', '17', '23-08-2024', '250', 'carmel', '11223344555'),
(26, '1', '1', '6', '23-08-2024', '3.08', 'Anandu Ajith', '1234567890'),
(27, '5', '1', '20', '23-08-2024', '0.02', 'sbi', '255555'),
(28, '6', '1', '21', '23-08-2024', '0.02', 'gdhdhh', '6566658585');

-- --------------------------------------------------------

--
-- Table structure for table `request_tbl`
--

CREATE TABLE IF NOT EXISTS `request_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(100) NOT NULL,
  `did` varchar(100) NOT NULL,
  `start_lat` varchar(200) NOT NULL,
  `start_long` varchar(200) NOT NULL,
  `dest_lat` varchar(200) NOT NULL,
  `dest_long` varchar(200) NOT NULL,
  `start_location` varchar(100) NOT NULL,
  `dest_location` varchar(100) NOT NULL,
  `trip_amount` varchar(100) NOT NULL,
  `trip_date` varchar(100) NOT NULL,
  `trip_status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `request_tbl`
--

INSERT INTO `request_tbl` (`id`, `uid`, `did`, `start_lat`, `start_long`, `dest_lat`, `dest_long`, `start_location`, `dest_location`, `trip_amount`, `trip_date`, `trip_status`) VALUES
(2, '1', '1', '10.0058879', '76.3051501', '10.0058899', '76.3051465', 'Palarivattom', 'Kaloor', '0', '05-07-2024', 'completed'),
(3, '1', '1', '9.9890971', '76.3088536', '9.9890958', '76.3088528', 'Thammanam', 'Palarivattom', '16.09', '05-07-2024', 'completed'),
(4, '1', '1', '9.9890971', '76.3088536', '9.9891005', '76.3088532', 'Thammanam', 'Edapally', '0', '05-07-2024', 'completed'),
(5, '1', '2', '10.0068217', '76.3044018', '10.006976666666667', '76.30435666666668', 'Palarivattom', 'Thammanam', '100', '06-07-2024', 'paid'),
(6, '1', '1', '10.0093926', '76.3051449', '10.005931666666667', '76.30501666666667', 'Edappally', 'edapally', '3.08', '19-07-2024', 'paid'),
(7, '1', '1', '10.0068716', '76.3043819', '10.007006666666665', '76.30433666666666', 'Palarivattom', 'Vyttila ', '100', '20-07-2024', 'paid'),
(8, '2', '2', '10.0068276', '76.3043938', '', '', 'Palarivattom', 'Edapally ', '68066.16', '20-07-2024', 'completed'),
(9, '1', '1', '10.0058914', '76.3051425', '10.0058952', '76.3051731', 'Palarivattom', 'Kaloor', '0.03', '17-08-2024', 'completed'),
(10, '1', '1', '10.0060269', '76.3049281', '10.005923333333334', '76.30496666666666', 'Palarivattom', 'Vyttila ', '0.1', '17-08-2024', 'paid'),
(11, '3', '1', '10.005898', '76.3051415', '10.0058914', '76.3051455', 'Palarivattom', 'Alappuzha', '0.01', '17-08-2024', 'completed'),
(12, '4', '1', '10.0058887', '76.3051467', '10.0058914', '76.3051455', 'Palarivattom', 'aluva', '0', '17-08-2024', 'completed'),
(13, '3', '3', '10.0058374', '76.3050961', '10.0058914', '76.3051455', 'Palarivattom', 'cherthala', '0.06', '17-08-2024', 'completed'),
(15, '2', '2', '10.0058852', '76.3051447', '10.005976371467113', '76.3050929736346', 'Palarivattom', 'pallinada', '0.09', '23-08-2024', 'paid'),
(16, '5', '1', '10.0058903', '76.3051424', '10.006796666666666', '76.30448833333334', 'Palarivattom', 'kaloor', '0.99', '23-08-2024', 'paid'),
(17, '5', '1', '10.0059669', '76.3050113', '10.006053333333334', '76.30551999999999', 'Palarivattom', 'kaloor', '250', '23-08-2024', 'paid'),
(18, '5', '1', '10.0058868', '76.3051466', '10.0058869', '76.3051464', 'Palarivattom', 'kaloor', '0', '23-08-2024', 'completed'),
(19, '1', '1', '10.0058864', '76.3051423', '10.006008333333334', '76.30515666666666', 'Palarivattom', 'aluva', '0.11', '23-08-2024', 'completed'),
(20, '5', '1', '10.005884', '76.3051348', '10.0058843', '76.305155', 'Palarivattom', 'kaloor', '0.02', '23-08-2024', 'accpeted'),
(21, '6', '1', '10.0058691', '76.3051477', '10.0058869', '76.3051467', 'Palarivattom', 'vaikom', '0.02', '23-08-2024', 'paid');

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE IF NOT EXISTS `user_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `number` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`id`, `name`, `email`, `number`, `username`, `password`) VALUES
(1, 'anandu', 'ananduajithkumar123@gmail.com', '8943409211', 'anandu', '555'),
(2, 'Kalliyani', 'kalliyani@gmail.com', '7736473857', 'kalliyani', '123'),
(3, 'ATHUL', 'athul@gmail.com', '7306201861', 'athulc', 'Athul123'),
(4, 'Alan', 'Alanddf@gmail.com', '5681638410', 'Alanj', '10987'),
(5, 'carmel', 'ggggg@gnail.com', '7306201861', 'Carmel ', 'Carmel'),
(6, 'devika', 'athulacsa@gmail.com', '7306201861', 'Devika', '12345678');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
