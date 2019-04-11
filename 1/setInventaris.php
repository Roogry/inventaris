<?php
    include 'model/koneksi.php';

    $nama = $_POST['nama'];
    $kondisi = $_POST['kondisi'];
    $ket = $_POST['keterangan'];
    $jumlah = $_POST['jumlah'];
    $idJenis = $_POST['idJenis'];
    $idRuang = $_POST['idRuang'];
    $idPetugas = $_POST['idPetugas'];

    $lastId = $con->query("SELECT MAX(id_inventaris) FROM inventaris")->fetch();

    $kode = "IS";
    $id = ++$lastId[0];
    for($i=0; $i<4-strlen($id); $i++){
        $kode .= "0";
    }
    $kode .= $id;

    $setInvent = $con->query("INSERT INTO inventaris (nama, kondisi, keterangan, jumlah, id_jenis, id_ruang, kode_inventaris, id_petugas) VALUES ('$nama', '$kondisi', '$ket', '$jumlah', '$idJenis', '$idRuang', '$kode', '$idPetugas') ");

    if($setInvent){
        echo json_encode("Berhasil Menambahkan");
    }else{
        echo json_encode("Gagal Menambahkan");
    }
?>