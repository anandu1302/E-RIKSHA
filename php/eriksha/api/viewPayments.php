<?php

include("connection.php");

$did = $_REQUEST['uid'];

$sql = "SELECT * FROM payment_tbl WHERE driver_id='$did'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM user_tbl WHERE id ='$row[user_id]'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_assoc($result2);

		$sql3 = "SELECT SUM(amount) as totalAmount FROM payment_tbl WHERE driver_id='$did'";
		$result3 = mysqli_query($con,$sql3);
		$row3 = mysqli_fetch_assoc($result3);

		$totalAmount = round($row3['totalAmount'],2);


		$data["data"][] = array('id'=>$row['id'],'amount'=>$row['amount'],'date'=>$row['date'],'card_number' => $row['acc_number'],'name'=>$row2['name'],'number'=>$row2['number'],'totalAmount'=>$totalAmount);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}
?>