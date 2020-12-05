<?php
    header('Content-Type: application/json; charset=utf-8');
    $input = json_decode(file_get_contents('php://input'));

    if ($_SERVER['REQUEST_METHOD'] != 'POST') {
        die(json_encode(array('code' => 4, 'message' => 'API này chỉ hỗ trợ POST')));
    }

    if (is_null($input)) {
        die(json_encode(array('code' => 2, 'message' => 'Chỉ nhận dữ liệu đầu vào là JSON Object')));
    }

    if (empty($input->email) ||
        empty($input->content)) {
        die(json_encode(array('code' => 1, 'message' => 'Thông tin đầu vào không hợp lệ')));
    }

    $email = $input->email;
    $content = $input->content;

    require_once('connection.php');

    $sql = 'insert into report(email, content) values(?,?)';
    $stm = $conn->prepare($sql);

    $stm->bind_param('ss', $email, $content);

    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    sleep(2);

    die(json_encode(array('code' => 0, 'message' => 'Đã lưu', 'data' => $email)));
?>