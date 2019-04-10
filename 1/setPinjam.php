<?php
    include 'model/koneksi.php';

    $idInvent = $_POST['id_invent'];
    $jumlah = $_POST['jumlah'];
    $idpeminjam = $_POST['id_peminjam'];

    $string = substr(str_shuffle("0123456789"), 0, 8);
    $kode = "PN".$string;

    $setPinjam = $con->query("INSERT INTO peminjaman (id_peminjam, kode_peminjaman) VALUES ('$idpeminjam', '$kode' )");   

    if($setPinjam){
        $id = $con->lastInsertId();

        $setDetailPinjam = $con->query("INSERT INTO detail_pinjam (id_inventaris, id_peminjaman, jumlah) VALUES ('$idInvent', '$id', '$jumlah')");

        if($setDetailPinjam){
            echo json_encode("Berhasil Meminjam");
        }else{
            echo json_encode("Gagal Meminjam");
        }

    }else{
        echo json_encode("Gagal Meminjam");
    }

    

?>