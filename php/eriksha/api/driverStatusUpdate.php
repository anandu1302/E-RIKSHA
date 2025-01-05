<?php

include("connection.php");

$uid = $_POST['uid'];
$status = $_POST['status'];

$sql = "UPDATE driver_tbl SET driver_status='$status' WHERE id='$uid'";
		
if(mysqli_query($con,$sql))
	echo "You Are ".$status;
else
	echo "Unanble to process the request";
	
?>