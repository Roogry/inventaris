<?php

    include 'model/koneksi.php';

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $ruang = $con->query("SELECT * FROM ruang");

        $data = array();
        while($row = $ruang->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }
        
        echo json_encode($data);
    }else if(isset($_POST['id']) && isset($_POST['nama'])){
        $id = $_POST['id'];
        $nama = $_POST['nama'];
        $kode = $_POST['kode'];
        $keterangan = $_POST['keterangan'];
        $ruang = $con->query("UPDATE ruang SET nama_ruang = '$nama', kode_ruang = '$kode', keterangan = '$keterangan' WHERE id_ruang ='$id'");

        if($ruang){
            echo json_encode("Berhasil Mengubah");
        }else{
            echo json_encode("Gagal Mengubah");
        }
    }else if(isset($_POST['id']) ){
        $id = $_POST['id'];
        $ruang = $con->query("DELETE FROM ruang WHERE id_ruang ='$id'");

        if($ruang){
            echo json_encode("Berhasil Menghapus");
        }else{
            echo json_encode("Gagal Menghapus");
        }
    }else{
        $nama = $_POST['nama'];
        $kode = $_POST['kode'];
        $keterangan = $_POST['keterangan'];
        $ruang = $con->query("INSERT INTO ruang (nama_ruang, kode_ruang, keterangan) VALUES ('$nama', '$kode', '$keterangan')");

        if($ruang){
            echo json_encode("Berhasil Menambah");
        }else{
            echo json_encode("Gagal Menambah");
        }
    }

?>