<?php
    $host = '127.0.0.1';
    $user = 'root';
    $pass = '';
    $db = 'database';

    $conn = new mysqli($host, $user, $pass, $db);
    if ($conn->connect_error) {
        die('Không thể kết nối database');
    }
?>
