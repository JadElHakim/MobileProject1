<?php

//Code overview this api is written to fetch the data from another api
//cURL is a PHP extension, that allows us to receive and send information via the URL syntax
//the format of the url is as follows https://lirarate.org/wp-json/lirarate/v2/omt?currency=LBP&_ver=tYearMonthDayHour
//you need to remove the leading 0s and time is based on a 24 hour system


//Get current timezone
date_default_timezone_set('Asia/Beirut');  

//storing variables this way because api requires to remove leading 0s and is of this format
$year = date("Y");  
$month=date("m");
$day=date("d"); 
$hour=date("H");
$today = (string)((int)($year)).(string)((int)($month)).(string)((int)($day)).(string)((int)($hour));

//initializing curl
$ch=curl_init();

$url="https://lirarate.org/wp-json/lirarate/v2/omt?currency=LBP&_ver=t".$today;

curl_setopt($ch,CURLOPT_URL,$url);
curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
$response=curl_exec($ch);
if($e=curl_error($ch)){
    echo $e;
}else{
    $decoded=json_decode($response,true);
}
        //storing required result in JSONObject
        $latest_value=end($decoded["omt"]);
        echo json_encode($latest_value[1]);


//closing curl
curl_close($ch);

?>
