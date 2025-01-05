<?php

include("connection.php");

$driverId = $_REQUEST['did'];

$sql = "SELECT * FROM request_tbl WHERE did='$driverId' ORDER BY id DESC";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM user_tbl WHERE id = '$row[uid]'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_array($result2);


		$data["data"][] = array('id' => $row['id'],'start_location' => $row['start_location'], 'dest_location' => $row['dest_location'],'trip_amount'=>$row['trip_amount'],'trip_date'=>$row['trip_date'],'status'=>$row['trip_status'],'userId'=>$row['uid'],'userName'=>$row2['name'],'userNumber'=>$row2['number']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>