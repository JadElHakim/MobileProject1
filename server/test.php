
<?php
date_default_timezone_set('Asia/Beirut');  

//storing variables this way because api requires to remove leading 0s and is of this format
$year = date("Y");  
$month=date("m");
$day=date("d");
$hour=date("H");

//link fetch json object from 
$today = (string)((int)($year)).(string)((int)($month)).(string)((int)($day)).(string)((int)($hour));
echo $today;

?>
