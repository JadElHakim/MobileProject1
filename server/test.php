
<?php
date_default_timezone_set('Asia/Beirut');  

//storing variables this way because api requires to remove leading 0s and is of this format
$year = date("Y");  
$month=date("m");
$day=date("d"); 
$hour=date("H");

//link fetch json object from 
$today = (string)((int)($year)).(string)((int)($month)).(string)((int)($day)).(string)((int)($hour));


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
        $latest_value=end($decoded["omt"]);
        echo json_encode($latest_value[1]);


curl_close($ch);

?>
