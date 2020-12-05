<?php
    header('Content-Type: application/json; charset=utf-8');
    $input = json_decode(file_get_contents('php://input'));

    if ($_SERVER['REQUEST_METHOD'] != 'POST') {
        die(json_encode(array('code' => 4, 'message' => 'API này chỉ hỗ trợ POST')));
    }

    if (is_null($input)) {
        die(json_encode(array('code' => 2, 'message' => 'Chỉ nhận dữ liệu đầu vào là JSON Object')));
    }


    $title = $input->title;
    $image = $input->image;
    $address = $input->address;
    $area = $input->area;
    $size = $input->size;
    $rooms = $input->rooms;
    $descript = $input->descript;
    $username = $input->username;

    require_once('connection.php');

    $sql = 'insert into post(title, image, address, area, size, rooms, descript, username) values(?,?,?,?,?,?,?,?)';
    $stm = $conn->prepare($sql);

    $stm->bind_param('ssssssss', $title, $image, $address, $area, $size, $rooms, $descript, $username);

    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    sleep(2);

    die(json_encode(array('code' => 0, 'message' => 'Đã lưu', 'data' => $title)));
?>