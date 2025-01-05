<?php

include("connection.php");

$uid = $_POST['uid'];
$tripId = $_POST['tripId'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

$sql = "UPDATE request_tbl SET dest_lat ='$latitude',dest_long='$longitude' WHERE id='$tripId'";

if (mysqli_query($con,$sql)) {

	$sql2 = "SELECT * FROM request_tbl WHERE id='$tripId'";
	$result2 = mysqli_query($con,$sql2);
	$row2 = mysqli_fetch_array($result2);

	$startLat = $row2['start_lat'];
	$startLng = $row2['start_long'];
	$destLat = $row2['dest_lat'];
	$destLong = $row2['dest_long'];

	//echo $startLat." ".$startLng." ".$destLat." ".$destLong;
    //******* Finding Distance *******//
	$earthRadius = 6371;
    $lat1 = deg2rad($startLat);
    $lon1 = deg2rad($startLng);
    $lat2 = deg2rad($destLat);
    $lon2 = deg2rad($destLong);

    $dlat = $lat2 - $lat1;
    $dlon = $lon2 - $lon1;

    // Haversine formula
    $a = sin($dlat / 2) * sin($dlat / 2) + cos($lat1) * cos($lat2) * sin($dlon / 2) * sin($dlon / 2);
    $c = 2 * atan2(sqrt($a), sqrt(1 - $a));

    // Distance in kilometers
    $distance = $earthRadius * $c;

    //echo $distance;

    $tripAmount = round($distance * 8,2);

    //echo $tripAmount;

    $sql3 = "UPDATE request_tbl SET trip_amount ='$tripAmount',trip_status='completed' WHERE id='$tripId'";
    mysqli_query($con,$sql3);

    $sql4 = "UPDATE driver_tbl SET ride_status='waiting' WHERE id='$uid'";
    mysqli_query($con,$sql4);

    $sql5 = "SELECT * FROM user_tbl WHERE id='$row2[uid]'";
    $result5 = mysqli_query($con,$sql5);
    $row5 = mysqli_fetch_array($result5);

    $data["data"][] = array('name' => $row5['name'], 'number' => $row5['number'],'amount'=>$tripAmount,'trip_date'=>$row2['trip_date']);

    echo json_encode($data);
	
}else{

    echo "failed";


}


?>