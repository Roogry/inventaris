<?php
    include 'model/koneksi.php';

    $id = $_POST['id'];
    $nama = $_POST['nama'];
    $kondisi = $_POST['kondisi'];
    $ket = $_POST['keterangan'];
    $jumlah = $_POST['jumlah'];
    $idJenis = $_POST['idJenis'];
    $idRuang = $_POST['idRuang'];

    $setInvent = $con->query("UPDATE inventaris SET nama = '$nama', kondisi = '$kondisi', keterangan = '$ket', jumlah = '$jumlah', id_jenis = '$idJenis', id_ruang = '$idRuang' WHERE id_inventaris ='$id'");

    if($setInvent){
        echo json_encode("Berhasil Mengubah");
    }else{
        echo json_encode("Gagal Mengubah");
    }
?>