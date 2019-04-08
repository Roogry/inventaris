<?php
    include 'model/koneksi.php';

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $petugas = $con->query("SELECT * FROM petugas WHERE id_level != 1");

        $data = array();
        while($row = $petugas->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }

        echo json_encode($data);
    }else if(isset($_POST['id'])){
        $id = $_POST['id'];
        $nama = $_POST['nama'];
        $username = $_POST['username'];
        $password = $_POST['password'];

        $petugas = $con->query("UPDATE petugas SET nama_petugas = '$nama', username = '$username', password = '$password' WHERE id_petugas = '$id'");

        if($petugas){
            echo json_encode("Berhasil Mengubah");
        }else{
            echo json_encode("Gagal Mengubah");
        }

    }else{
        $nama = $_POST['nama'];
        $username = $_POST['username'];
        $password = $_POST['password'];

        $petugas = $con->query("INSERT INTO petugas (nama_petugas, username, password, id_level) VALUES ('$nama', '$username', '$password', '2')");   

        if($petugas){
            echo json_encode("Berhasil Menambahkan");
        }else{
            echo json_encode("Gagal Menambahkan");
        }
    }
?>