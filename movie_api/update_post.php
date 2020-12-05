<?php

    header('Content-Type: application/json; charset=utf-8');
    $input = json_decode(file_get_contents('php://input'));

    if ($_SERVER['REQUEST_METHOD'] != 'POST') {
        die(json_encode(array('code' => 4, 'message' => 'API này chỉ hỗ trợ POST')));
    }

    if (is_null($input)) {
        die(json_encode(array('code' => 2, 'message' => 'Chỉ nhận dữ liệu đầu vào là JSON Object')));
    }

    if (!property_exists($input,'title')||
        !property_exists($input,'address')) {
        die(json_encode(array('code' => 1, 'message' => 'Thiếu thông tin đầu vào')));
    }

    if (empty($input->address)||empty($input->title)) {
        die(json_encode(array('code' => 1, 'message' => 'Thông tin đầu vào không hợp lệ')));
    }

    $title = $input->title;
    $image = $input->image;
    $address = $input->address;
    $area = $input->area;
    $size = $input->size;
    $rooms = $input->rooms;
    $descript = $input->descript;
    $postId = $input->postId;
    $username = $input->username;

    require_once('connection.php');

    $sql = 'select * from post where postId=?';
    $stm = $conn->prepare($sql);

    $stm->bind_param('i', $postId );
    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    $result = $stm->get_result();
    if ($result->num_rows > 0) {
        $sql = 'update post 
                set title=?, image=?, address=?, area=?, size=?, rooms=?, descript=?, username=? 
                where postId=?';
        $stm = $conn->prepare($sql);

        $stm->bind_param('ssssssssi', $title, $image, $address, $area, $size, $rooms, $descript, $username, $postId);
        if (!$stm->execute()) {
            die(json_encode(array('code' => 3, 'message' => $stm->error)));
        }

        echo json_encode(array('code' => 0, "message" => "Đăng nhập thành công!", "data" => "updated"));
    }
    else {
        echo json_encode(array('code' => 4,"message" => "Sai email hoặc mật khẩu"));
    }
?>