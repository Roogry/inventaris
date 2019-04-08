<?php

    include 'model/koneksi.php';

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $jenis = $con->query("SELECT * FROM jenis");

        $data = array();
        while($row = $jenis->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }
        
        echo json_encode($data);
    }else if(isset($_POST['id']) && isset($_POST['nama'])){
        $id = $_POST['id'];
        $nama = $_POST['nama'];
        $kode = $_POST['kode'];
        $keterangan = $_POST['keterangan'];
        $jenis = $con->query("UPDATE jenis SET nama_jenis = '$nama', kode_jenis = '$kode', keterangan = '$keterangan' WHERE id_jenis ='$id'");

        if($jenis){
            echo json_encode("Berhasil Mengubah");
        }else{
            echo json_encode("Gagal Mengubah");
        }
    }else if(isset($_POST['id']) ){
        $id = $_POST['id'];
        $jenis = $con->query("DELETE FROM jenis WHERE id_jenis ='$id'");

        if($jenis){
            echo json_encode("Berhasil Menghapus");
        }else{
            echo json_encode("Gagal Menghapus");
        }
    }else{
        $nama = $_POST['nama'];
        $kode = $_POST['kode'];
        $keterangan = $_POST['keterangan'];
        $jenis = $con->query("INSERT INTO jenis (nama_jenis, kode_jenis, keterangan) VALUES ('$nama', '$kode', '$keterangan')");

        if($jenis){
            echo json_encode("Berhasil Menambah");
        }else{
            echo json_encode("Gagal Menambah");
        }
    }

?>