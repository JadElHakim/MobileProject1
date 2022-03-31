<?php
//database host name
$db_host="localhost";
//database username
$db_user="root";
//database password
$db_pass= "123";
//database name
$db_name="currency_converter";
//connect to databse
$mysqli = new mysqli($db_host,$db_user,$db_pass,$db_name);
//if error die ( thats what they all say :( )
if(mysqli_connect_errno()){
    die("Connection Failed" .$mysqli->connect_error);
}



?>