<?php
    include 'model/koneksi.php';

    $dFrom = $_POST['dFrom'];
	$dTo = $_POST['dTo'];

    $report = $con->query("CALL getReport('$dFrom', '$dTo')");

    $data = array();
    if($report){
        while($row = $report->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }
    }

    echo json_encode($data);
    
?>