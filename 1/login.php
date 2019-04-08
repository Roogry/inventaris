<?php
    include 'model/koneksi.php';

    $username = $_POST['username'];
    $password = $_POST['password'];

    $petugas = $con->query("SELECT * FROM petugas WHERE username ='$username' AND password='$password'");
    $peminjam = $con->query("SELECT * FROM peminjam WHERE username ='$username' AND password='$password'");

    $data = array();

    $data["listItem"]["alamat"] = "";
    $data["listItem"]["status_peminjam"] = "";
    $data["listItem"]["id_level"] = "3";

    if($peminjam){
        while($row = $peminjam->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"]["id"] = $row['id_peminjam'];
            $data["listItem"]["nama"] = $row['nama_peminjam'];
            $data["listItem"]["username"] = $row['username'];
            $data["listItem"]["password"] = $row['password'];
            $data["listItem"]["alamat"] = $row['alamat'];
            $data["listItem"]["status_peminjam"] = $row['status_peminjam'];
        }
    } 
    
    if ($petugas){
        while($row = $petugas->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"]["id"] = $row['id_petugas'];
            $data["listItem"]["nama"] = $row['nama_petugas'];
            $data["listItem"]["username"] = $row['username'];
            $data["listItem"]["password"] = $row['password'];
            $data["listItem"]["id_level"] = $row['id_level'];
        }
    }else{
        echo 'Username atau Password salah';
    }

    echo json_encode($data);
?>