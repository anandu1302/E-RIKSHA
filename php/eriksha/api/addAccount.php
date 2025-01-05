<?php

include("connection.php");

$uid = $_POST['uid'];
$accName = $_POST['accName'];
$accNumber = $_POST['accNumber'];
$bank = $_POST['bank'];
$pin = $_POST['pin'];
$amount = $_POST['amount'];


$sql ="INSERT INTO account_tbl (uid,accname,accountno,bank,pin,amount) VALUES ('$uid','$accName','$accNumber','$bank','$pin','$amount')";

if(mysqli_query($con,$sql)){
	

	echo "success";
}
else{
	
	echo"failed";
}


?>