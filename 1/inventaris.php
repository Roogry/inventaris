<?php
    include 'model/koneksi.php';

    if(isset($_POST['keyword'])){
        $keyword = $_POST['keyword'];
        $invent = $con->query("SELECT * FROM inventaris WHERE jumlah != 0 AND nama LIKE '%$keyword%'");

    }else if(isset($_POST['id'])){
        $id = $_POST['id'];
        $invent = $con->query("SELECT * FROM inventaris WHERE id_inventaris = '$id'");

    }else{
        $invent = $con->query("SELECT * FROM inventaris WHERE jumlah != 0");
    }

    $data = array();

    if($invent){
        while($row = $invent->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }

        echo json_encode($data);
    }
?>