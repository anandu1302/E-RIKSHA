<?php

include("connection.php");

$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$driverId = $_POST['uid'];

$sql = "UPDATE driver_tbl SET latitude = '$latitude', longitude = '$longitude'  WHERE id = '$driverId' ";
if(mysqli_query($con,$sql))
{
	echo "updated";
}
else
{
	echo "failed";
}

?>