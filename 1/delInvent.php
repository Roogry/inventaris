<?php
    include 'model/koneksi.php';

    $id = $_POST['id'];

    $del = $con->query("DELETE FROM inventaris WHERE id_inventaris ='$id'");

    if($del){
        echo json_encode("Berhasil Menghapus");
    }else{
        echo json_encode("Gagal Menghapus");
    }
?>