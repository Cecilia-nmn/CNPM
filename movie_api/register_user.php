<?php
    header('Content-Type: application/json; charset=utf-8');
    $input = json_decode(file_get_contents('php://input'));

    if ($_SERVER['REQUEST_METHOD'] != 'POST') {
        die(json_encode(array('code' => 4, 'message' => 'API này chỉ hỗ trợ POST')));
    }

    if (is_null($input)) {
        die(json_encode(array('code' => 2, 'message' => 'Chỉ nhận dữ liệu đầu vào là JSON Object')));
    }


if (!property_exists($input,'email') ||
    !property_exists($input,'username') ||
    !property_exists($input,'password')||
    !property_exists($input,'phone')||
    !property_exists($input,'role')
) {
    die(json_encode(array('code' => 1, 'message' => 'Thiếu thông tin đầu vào')));
}

if (empty($input->email) ||
    empty($input->username) ||
    empty($input->password)||
    empty($input->phone)||
    empty($input->role)) {
    die(json_encode(array('code' => 1, 'message' => 'Thông tin đầu vào không hợp lệ')));
}

    $email = $input->email;
    $username = $input->username;
    $password = $input->password;
    $role = $input->role;
    $phone = $input->phone;

    require_once('connection.php');

    $sql = 'select * from user where email=?';
    $stm = $conn->prepare($sql);

    $stm->bind_param('s', $email);
    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    $result = $stm->get_result();
    if ($result->num_rows > 0) {
        echo json_encode(array("code" => 4, 'message' => 'Email tồn tại!'));
    }

    $sql = 'select * from user where username=?';
    $stm = $conn->prepare($sql);

    $stm->bind_param('s', $username);
    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    $result = $stm->get_result();
    if ($result->num_rows > 0) {
        echo json_encode(array("code" => 4, 'message' => 'Tên người dùng tồn tại!'));
    }
    else {
        $sql = 'insert into user(username, password, email, phone, role) values(?,?,?,?,?)';
        $stm = $conn->prepare($sql);

        $stm->bind_param('ssssi', $username, $password, $email, $phone, $role);

        if (!$stm->execute()) {
            die(json_encode(array('code' => 3, 'message' => $stm->error)));
        }

        sleep(2);

        die(json_encode(array('code' => 0, 'message' => 'Đăng ký thành công', 'data' => $email)));
    }
?>