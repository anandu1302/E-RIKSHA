<?php

include("connection.php");

$driverId = $_POST['uid'];

//$driverId = "1";

$sql = "SELECT * FROM request_tbl WHERE did='$driverId' and trip_status='accepted' or trip_status='onTrip'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM user_tbl WHERE id ='$row[uid]'";
		$result2 = mysqli_query($con,$sql2);
		$roww = mysqli_fetch_assoc($result2);

		$data["data"][] = array('id'=>$row['id'],'start_location'=>$row['start_location'],'dest_location' => $row['dest_location'],'start_latitude'=>$row['start_lat'],'start_longitude'=>$row['start_long'],'name' => $roww['name'],'number' => $roww['number'],'trip_status'=>$row['trip_status']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}


?>