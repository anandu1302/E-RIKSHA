<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "SELECT * FROM request_tbl WHERE uid='$uid' ORDER BY id DESC";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM driver_tbl WHERE id = '$row[did]'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_array($result2);


		$data["data"][] = array('id' => $row['id'],'start_location' => $row['start_location'], 'dest_location' => $row['dest_location'],'trip_amount'=>$row['trip_amount'],'trip_date'=>$row['trip_date'],'status'=>$row['trip_status'],'driverId'=>$row['did'],'driverName'=>$row2['name']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>