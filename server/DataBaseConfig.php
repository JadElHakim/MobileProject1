<?php

$db_host="localhost";
$db_user="root";
$db_pass= "123";
$db_name="currency_converter";

$mysqli = new mysqli($db_host,$db_user,$db_pass,$db_name);

if(mysqli_connect_errno()){
    die("Connection Failed" .$mysqli->connect_error);
}



?>