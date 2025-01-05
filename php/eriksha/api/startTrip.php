<?php

include("connection.php");

$tripId = $_POST['tripId'];


$sql = "UPDATE request_tbl SET trip_status='onTrip' WHERE id='$tripId'";

if (mysqli_query($con,$sql)) {
	
	echo "success";
}else{

	echo "failed";
}

?>