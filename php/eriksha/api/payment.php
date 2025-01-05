<?php

include("connection.php");

$uid = $_REQUEST['userId'];
$driverId = $_REQUEST['driverId'];
$bookingId = $_REQUEST['bookingId'];
$amount = $_REQUEST['amount'];
$accountNumber = $_REQUEST['accNumber'];

$date = date('d-m-Y');

$sql3 = "SELECT * FROM account_tbl WHERE id='$accountNumber'";
$result3 = mysqli_query($con,$sql3);
$row3 = mysqli_fetch_assoc($result3);

$amnt=$row3["amount"];

if ($amnt >= $amount) {

$sql ="INSERT INTO payment_tbl (user_id,driver_id,booking_id,amount,date,card_name,acc_number) VALUES ('$uid','$driverId','$bookingId','$amount','$date','$row3[accname]','$row3[accountno]')";

if(mysqli_query($con,$sql)){




    	$a=$amnt-$amount;

    	$sql2 = "UPDATE account_tbl set amount='$a' WHERE accountno='$row3[accountno]'";
		mysqli_query($con,$sql2);

		$sql4 ="UPDATE request_tbl SET trip_status='paid' WHERE id='$bookingId'";
	    mysqli_query($con,$sql4);

	    echo "success";
    	
    }else{
	
		echo"failed";
	} 
	
}
else {

	echo "accerror";
}




?>