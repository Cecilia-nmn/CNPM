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
        empty($input->id_phim)||
        empty($input->suat_phim)||
        empty($input->ngay_xem)) {
        die(json_encode(array('code' => 1, 'message' => 'Thông tin đầu vào không hợp lệ')));
    }

    $cinemaId = $input->id_rap;
    $movieId = $input->id_phim;
    $timeStart = $input->suat_phim;
    $date = $input->ngay_xem;

    require_once('connection.php');

    $sql = 'select * from dat_ve where id_rap=? and id_phim=? and suat_phim=? and ngay_xem=?';
    $stm = $conn->prepare($sql);

    $stm->bind_param('iiss', $cinemaId, $movieId, $timeStart, $date);
    if (!$stm->execute()) {
        die(json_encode(array('code' => 3, 'message' => $stm->error)));
    }

    $result = $stm->get_result();
    if ($result->num_rows > 0) {
        $sql = 'select * from dat_ve where id_rap=? and id_phim=? and suat_phim=? and ngay_xem=?';
        $stm = $conn->prepare($sql);

        $stm->bind_param('iiss', $cinemaId, $movieId, $timeStart, $date);
        if (!$stm->execute()) {
            die(json_encode(array('code' => 3, 'message' => $stm->error)));
        }

        $array = array();
        $result = $stm->get_result();
        if ($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $array[] = $row;
            }
        }
        echo json_encode(array('code' => 0, "message" => "Thành công!", "data" => $array));
    }
    else {
        echo json_encode(array('code' => 4,"message" => "Chưa có dữ liệu"));
    }
?>