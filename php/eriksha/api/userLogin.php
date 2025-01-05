<?php

include("connection.php");

$username = $_POST['username'];
$password = $_POST['password'];

$sql ="SELECT * FROM user_tbl where username='$username' and password='$password'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result)>0){
	
	$row=mysqli_fetch_assoc($result);	
	$data[]=array('id'=> $row['id'],'name'=> $row['name']);  

	echo json_encode($data);

}
else{
	echo "failed";
}


?>