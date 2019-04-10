<?php
    include 'model/koneksi.php';

    $kode = $_POST['kode'];

    $kembalikan = $con->query("UPDATE peminjaman SET tanggal_kembali = NOW(), status_peminjaman = '2' WHERE kode_peminjaman = '$kode' AND tanggal_kembali = '0'");

    if($kembalikan->rowCount() == 1){
        echo json_encode("Berhasil Dikembalikan");
    }else{
        echo json_encode("Kode tidak Valid");
    }
?>