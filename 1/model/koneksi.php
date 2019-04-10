<?php
    $host = "localhost";
    $username = "root";
    $password = "";
    $dbname = "inventa";

    try{
        $con = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    }catch(PDOExeption $e){
        echo $e->getMessage();
    }
?>