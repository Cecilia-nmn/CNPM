-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 05, 2020 lúc 05:42 AM
-- Phiên bản máy phục vụ: 10.4.14-MariaDB
-- Phiên bản PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `database`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `contact_request`
--

CREATE TABLE `contact_request` (
  `crId` int(11) NOT NULL,
  `senderId` varchar(100) NOT NULL,
  `receiverId` varchar(100) NOT NULL,
  `contactInf` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `contact_request`
--

INSERT INTO `contact_request` (`crId`, `senderId`, `receiverId`, `contactInf`) VALUES
(1, 'user2', 'user1', '0123456789');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `post`
--

CREATE TABLE `post` (
  `postId` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `area` int(11) NOT NULL,
  `size` varchar(50) NOT NULL,
  `rooms` varchar(50) NOT NULL,
  `descript` text NOT NULL,
  `username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
INSERT INTO `post` (`postId`, `title`, `image`, `address`, `area`, `size`, `rooms`, `descript`, `username`) VALUES
(1, 'Mùa táo rụng', 'hinhanh1.jpg', '11 Hà Nội', 10, '40', 'A305', 'Gút chóp, a mây zing', 'user1'),
(2, 'Mùa nho chín', 'hinhanh2.jpg', '11 TP HCM', 11, '20', 'A321', 'Gút chóp, a mây zing', 'user2'),
(3, 'Mùa xoài thơm', 'hinhanh3.jpg', '19 Trần Hưng Đạo', 01, '10', 'A325', 'Gút chóp, a mây zing', 'user3');

--
-- Cấu trúc bảng cho bảng `report`
--

CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `email` varchar(200) NOT NULL,
  `content` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `report`
--

INSERT INTO `report` (`id`, `email`, `content`) VALUES
(1, 'user@gmail.com', 'sth i wanna report'),
(4, 'stranger3@gmail.com', 'm new content'),
(5, 'stranger3@gmail.com', 'it\'s me again'),
(7, 'stranger1@gmail.com', 'my new content'),
(8, 'stranger5@gmail.com', 'content content');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`username`, `password`, `email`, `phone`, `role`) VALUES
('admin', '123123', 'admin@gmail.com', '0123456798', '0'),
('user1', '123123', 'user1@gmail.com', NULL, '1'),
('user2', '123123', 'user2@gmail.com', NULL, '2'),
('user3', '123123', 'user3@gmail.com', NULL, '2'),
('user4', '123123', 'user4@gmail.com', NULL, '2'),
('user5', '123123', 'user5@gmail.com', NULL, '2'),
('user6', '123123', 'user6@gmail.com', NULL, '2'),
('user7', '123123', 'user7@gmail.com', NULL, '2'),
('user8', '123123', 'user8@gmail.com', NULL, '2'),
('user9', '123123', 'user9@gmail.com', NULL, '2'),
('user10', '123123', 'user10@gmail.com', NULL, '2'),
('user11', '123123', 'user11@gmail.com', NULL, '2'),
('user12', '123123', 'user12@gmail.com', NULL, '2'),
('user13', '123123', 'user13@gmail.com', NULL, '2');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `contact_request`
--
ALTER TABLE `contact_request`
  ADD PRIMARY KEY (`crId`),
  ADD KEY `receiverId` (`receiverId`),
  ADD KEY `senderId` (`senderId`);

--
-- Chỉ mục cho bảng `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`postId`),
  ADD KEY `username` (`username`);

--
-- Chỉ mục cho bảng `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `contact_request`
--
ALTER TABLE `contact_request`
  MODIFY `crId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `post`
--
ALTER TABLE `post`
  MODIFY `postId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `report`
--
ALTER TABLE `report`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `contact_request`
--
ALTER TABLE `contact_request`
  ADD CONSTRAINT `contact_request_ibfk_1` FOREIGN KEY (`receiverId`) REFERENCES `user` (`username`),
  ADD CONSTRAINT `contact_request_ibfk_2` FOREIGN KEY (`senderId`) REFERENCES `user` (`username`);

--
-- Các ràng buộc cho bảng `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
