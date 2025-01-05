<?php

include("connection.php");

$name = $_POST['name'];
$number = $_POST['number'];
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];


$sql ="INSERT INTO user_tbl (name,number,email,username,password) VALUES ('$name','$number','$email','$username','$password')";

if(mysqli_query($con,$sql)){
	
	echo "success";
}
else{
	
	echo"failed";
}


?>