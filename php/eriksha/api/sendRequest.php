<?php

include("connection.php");

$uid = $_POST['uid'];
$start = $_POST['start'];
$destination = $_POST['destination'];
$startlat = $_POST['startlat'];
$startlon = $_POST['startlon'];

$date = date("d-m-Y");

$sql ="INSERT INTO request_tbl(uid,start_lat,start_long,start_location,dest_location,trip_date,trip_status)VALUES('$uid','$startlat','$startlon','$start','$destination','$date','waiting')";

if (mysqli_query($con,$sql)) {
	
	echo "success";
}else {

	echo "failed";
}



?>