<?php

include("connection.php");

$uid = $_POST['uid'];
$driverName = $_POST['driverName'];
$rating = $_POST['rating'];
$comments = $_POST['comments'];

$sql ="INSERT INTO feedback_tbl (user_id,driver_name,rating,feedback) VALUES ('$uid','$driverName','$rating','$comments')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}

?>