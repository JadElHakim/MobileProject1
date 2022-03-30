<?php
include 'DataBaseConfig.php';
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $amount = $_POST['amount'];
 $currency = $_POST['currency'];
 $sttmnt= $con->prepare("insert into ammountconverted (amount,currency) values ('$amount','$currency')");
 $sttmnt->bind_param("dss",$amount,$currency);
 if($sttmnt->execute()==TRUE){
 
 echo 'Data Submit Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>