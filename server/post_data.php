<?php

$HostName = "localhost";

$HostUser = "root";

$HostPass = "";

$DatabaseName = "currency_converter";
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $amount = $_POST['amount'];
 $currency = $_POST['currency'];
 $rate = $_POST['rate'];
 $sttmnt= $con->prepare("INSERT INTO `amountconverted`(currency,amount) VALUES (?,?)");
 $sttmnt->bind_param("ss",$currency,$amount);
 if($sttmnt->execute()==TRUE){
 $total = $amount * $rate;
echo json_encode($total);
}
else{

echo json_encode('Try Again');
 
 }
 mysqli_close($con);
?>