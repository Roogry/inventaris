<?php
    include 'model/koneksi.php';

    if(!isset($_POST['id'])){
        $peminjaman = $con->query("SELECT peminjaman.*, inventaris.nama, detail_pinjam.jumlah, peminjam.nama_peminjam FROM peminjaman, detail_pinjam, inventaris, peminjam WHERE detail_pinjam.id_peminjaman = peminjaman.id_peminjaman AND inventaris.id_inventaris = detail_pinjam.id_inventaris AND peminjam.id_peminjam = peminjaman.id_peminjam AND peminjaman.status_peminjaman = 1");
    }else{
        $idPeminjam = $_POST['id'];

        $peminjaman = $con->query("SELECT peminjaman.*, inventaris.nama, detail_pinjam.jumlah, peminjam.nama_peminjam FROM peminjaman, detail_pinjam, inventaris, peminjam WHERE detail_pinjam.id_peminjaman = peminjaman.id_peminjaman AND inventaris.id_inventaris = detail_pinjam.id_inventaris AND peminjam.id_peminjam = peminjaman.id_peminjam AND peminjaman.status_peminjaman = 1 AND peminjaman.id_peminjam = '$idPeminjam'");
    }

    $data = array();

    while($row = $peminjaman->fetch(PDO::FETCH_ASSOC)){
        $data["listItem"][] = $row;
    }

    echo json_encode($data);
?>