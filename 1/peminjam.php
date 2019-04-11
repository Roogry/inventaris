<?php
    include 'model/koneksi.php';

    if($_SERVER['REQUEST_METHOD']=='GET'){
        $peminjam = $con->query("SELECT * FROM peminjam");

        $data = array();
        while($row = $peminjam->fetch(PDO::FETCH_ASSOC)){
            $data["listItem"][] = $row;
        }

        echo json_encode($data);
    }else if(isset($_POST['id']) && isset($_POST['nama'])){
        $id = $_POST['id'];
        $nama = $_POST['nama'];
        $nip = $_POST['nip'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $alamat = $_POST['alamat'];
        $status = $_POST['status'];

        $peminjam = $con->query("UPDATE peminjam SET nama_peminjam = '$nama', nip_nis = '$nip', username = '$username', password = '$password', alamat='$alamat', status_peminjam = '$status' WHERE id_peminjam = '$id'");

        if($peminjam){
            echo json_encode("Berhasil Mengubah");
        }else{
            echo json_encode("Gagal Mengubah");
        }

    }else if(isset($_POST['id'])){
        $id = $_POST['id'];

        $peminjam = $con->query("DELETE FROM peminjam WHERE id_peminjam ='$id'");

        if($peminjam){
            echo json_encode("Berhasil Menghapus");
        }else{
            echo json_encode("Gagal Menghapus");
        }
    }else{
        $nama = $_POST['nama'];
        $nip = $_POST['nip'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        $alamat = $_POST['alamat'];
        $status = $_POST['status'];

        $peminjam = $con->query("INSERT INTO peminjam (nama_peminjam, nip_nis, username, password, alamat, status_peminjam) VALUES ('$nama', '$nip', '$username', '$password', '$alamat', '$status')");   

        if($peminjam){
            echo json_encode("Berhasil Menambahkan");
        }else{
            echo json_encode("Gagal Menambahkan");
        }
    }
?>