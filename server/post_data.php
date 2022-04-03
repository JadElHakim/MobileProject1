<?php
//include database info+connection
include("DataBaseConfig.php");

//--------------------------Variable Assignment-------------------------------------------//
//assigining variables that are sent from the front to the backend for propper assignment 

//amount to be converted
$amount = $_POST['amount'];
//conversion currency
$currency = $_POST['currency'];
//conversion rate
$rate=$_POST['rate'];
//return_ammount === converted value to be displayed 
$return_amout="";
//-----------------------------------------------------------------------------------//

//if USD
if (strcmp($currency, "USD") == 0) {

    $temp_amount=floatval($amount)*floatval($rate);

    $return_amount=strval($temp_amount);
}

//if LBP
else if (strcmp($currency, "LBP") == 0) {
    //im going to leave this here another hour of decoding because i added this line of code

                            //echo "goodbye";

    /////////////////////////////////////////////////////////////////
    $temp_amount=floatval($amount)/floatval($rate);

    $return_amount=strval($temp_amount);
}

//insert values to Database
$query= $mysqli->prepare("INSERT INTO conversions(currency,amount,rate,return_amount) VALUES (?,?,?,?)");
$query->bind_param("ssss", $amount, $currency, $rate,$return_amount);
$query->execute();

//return converted amount to be displayed on the front-end
$array=array("result"=>$return_amount);

echo json_encode($array);
 
?>