<?php
    require_once('connection.php');
    header('Content-Type: application/json; charset=utf-8');

    $sql = 'select * from report';
    $result = $conn->query($sql);
    if (!$result) {
        echo json_encode(array('code' => 1, 'message' => $conn->error));
    }

    $items = array();
    while ($item = $result->fetch_assoc()) {
        array_push($items, $item);
    }

    echo json_encode(array('code' => 0, 'message' => 'Đọc thành công', 'data' => $items));
?>
