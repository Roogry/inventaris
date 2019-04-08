<?php
    include 'model/koneksi.php';

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $peminjam = $con->query("SELECT * FROM peminjam");

        $data = array();
        while($row = $peminjam->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }

        echo json_encode($data);
    }else if(isset($_POST['id'])){
        $id = $_POST['id'];
        $nama = $_POST['nama'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $status = $_POST['status'];

        $peminjam = $con->query("UPDATE peminjam SET nama_peminjam = '$nama', username = '$username', password = '$password', status_peminjam = '$status' WHERE id_peminjam = '$id'");

        if($peminjam){
            echo json_encode("Berhasil Mengubah");
        }else{
            echo json_encode("Gagal Mengubah");
        }

    }else{
        $nama = $_POST['nama'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $status = $_POST['status'];

        $peminjam = $con->query("INSERT INTO peminjam (nama_peminjam, username, password, status_peminjam) VALUES ('$nama', '$username', '$password', '$status')");   

        if($peminjam){
            echo json_encode("Berhasil Menambahkan");
        }else{
            echo json_encode("Gagal Menambahkan");
        }
    }
?>