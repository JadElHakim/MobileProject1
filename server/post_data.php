<?php
include("DataBAseConfig.php");
$amount = $_POST['amount'];
$currency = $_POST['currency'];
$rate=$_POST['rate'];
$query= $mysqli->prepare("INSERT INTO amountconverted(currency,amount,rate) VALUES (?,?,?)");
$query->bind_param("sss", $amount, $currency, $rate);
$query->execute();

 
?>