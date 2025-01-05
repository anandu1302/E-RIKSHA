<?php

include("connection.php");

$requestId = $_POST['requestId'];
$uid = $_POST['uid'];

$sql = "UPDATE request_tbl SET trip_status='accepted',did='$uid' WHERE id='$requestId'";
 
if(mysqli_query($con,$sql)){

	$sql2 = "UPDATE driver_tbl SET ride_status='accepted' WHERE id='$uid'";
	$result2 = mysqli_query($con,$sql2);

	echo "success";

}else{

	echo "failed";
}




?>