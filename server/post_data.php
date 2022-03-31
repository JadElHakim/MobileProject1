<?php
include("DataBaseConfig.php");
$amount = $_POST['amount'];
$currency = $_POST['currency'];
$rate=$_POST['rate'];
$return_amout="";


if (strcmp($currency, "USD") == 0) {
    $temp_amount=floatval($amount)*floatval($rate);
    $return_amount=strval($temp_amount);
}

else if (strcmp($currency, "LBP") == 0) {
    //im going to leave this here another hour of decoding because i added this line of code

                            //echo "goodbye";

    /////////////////////////////////////////////////////////////////
    $temp_amount=floatval($amount)/floatval($rate);
    $return_amount=strval($temp_amount);
}
$query= $mysqli->prepare("INSERT INTO amountconverted(currency,amount,rate,return_amount) VALUES (?,?,?,?)");
$query->bind_param("ssss", $amount, $currency, $rate,$return_amount);
$query->execute();

$array=array("result"=>$return_amount);

echo json_encode($array);
 
?>