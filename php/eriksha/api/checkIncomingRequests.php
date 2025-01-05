<?php

include("connection.php");

$uid = $_POST['uid'];

//$uid = "1";

$sql = "SELECT * FROM driver_tbl WHERE id ='$uid' and driver_status ='active'";
$result = mysqli_query($con,$sql);

if (mysqli_num_rows($result) > 0) {

	$row = mysqli_fetch_assoc($result);
	$lat = $row['latitude'];
	$lon = $row['longitude'];

	$sql2 = "SELECT id, uid, start_location, dest_location, trip_date, start_lat, start_long, SQRT( POW(69.1 * (start_lat - '$lat'), 2) + POW(69.1 * ('$lon' - start_long) * COS(start_lat / 57.3), 2)) AS distance FROM request_tbl WHERE trip_status = 'waiting'  HAVING distance < 5 ORDER BY distance ASC ";

	//echo $sql2;

	$result2 = mysqli_query($con,$sql2);

	if (mysqli_num_rows($result2) > 0) {

		while($row2 = mysqli_fetch_assoc($result2)){

			$id = $row2['id'];
			$userId = $row2['uid'];
        
        	$sql3 = "SELECT * FROM user_tbl WHERE id = '$userId'";
        	$result3 = mysqli_query($con,$sql3);
			$row3 = mysqli_fetch_assoc($result3);

			//echo $sql3;

			$data["data"][] = array('id' => $row2['id'], 'name' => $row3['name'], 'phone' => $row3['number'], 'start_location' => $row2['start_location'], 'dest_location' => $row2['dest_location'], 'start_lat' => $row2['start_lat'], 'start_long' => $row2['start_long'], 'trip_date' => $row2['trip_date'],'distance'=>$row2['distance']); 

		}

		echo json_encode($data);
		


	}
	else{

		echo "failed";
	}

	
	
}else{

	echo "inactive";
}




?>