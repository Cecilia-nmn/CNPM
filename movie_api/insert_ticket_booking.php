<?php
    header('Content-Type: application/json; charset=utf-8');
    $input = json_decode(file_get_contents('php://input'));

    if ($_SERVER['REQUEST_METHOD'] != 'POST') {
        die(json_encode(array('code' => 4, 'message' => 'API này chỉ hỗ trợ POST')));
    }

    if (is_null($input)) {
        die(json_encode(array('code' => 2, 'message' => 'Chỉ nhận dữ liệu đầu vào là JSON Object')));
    }

    if (empty($input->id_rap) ||
        empty($input->email)||
        empty($input->id_phim)||
        empty($input->suat_phim)||
        empty($input->ma_ghe)||
        empty($input->ngay_xem)) {
        die(json_encode(array('code' => 1, 'message' => 'Thông tin đầu vào không hợp lệ')));
    }

    $cinemaId = $input->id_rap;
    $email = $input->email;
    $movieId = $input->id_phim;
    $timeStart = $input->suat_phim;
    $seatId = $input->ma_ghe;
    $date = $input->ngay_xem;

    require_once('connection.php');

    $sql = 'insert into dat_ve(id_rap, email, id_phim, suat_phim, ma_ghe, ngay_xem) values(?,?,?,?,?,?)';
    $stm = $conn->prepare($sql);

    $stm->bind_param('isisss', $cinemaId, $email, $movieId, $timeStart, $seatId, $date);

    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    sleep(2);

    die(json_encode(array('code' => 0, 'message' => 'Đã lưu', 'data' => $seatId)));
?>