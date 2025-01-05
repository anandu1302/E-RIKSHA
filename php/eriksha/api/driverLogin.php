<?php

include("connection.php");

$email = $_POST['email'];
$password = $_POST['password'];

$sql ="SELECT * FROM driver_tbl where email='$email' and password='$password'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result)>0){
	
	$row=mysqli_fetch_assoc($result);	
	$data[]=array('id'=> $row['id'],'name'=> $row['name'],'ride_status'=> $row['ride_status'],'driver_status' =>$row['driver_status']);  

	echo json_encode($data);

}
else{
	echo "failed";
}


?>