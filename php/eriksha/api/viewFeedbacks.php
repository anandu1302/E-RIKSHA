<?php

include("connection.php");

$driverId = $_REQUEST['uid'];

$sql = "SELECT * FROM driver_tbl WHERE id = '$driverId'";
$result = mysqli_query($con,$sql);
$row = mysqli_fetch_array($result);

$sql2 = "SELECT * FROM feedback_tbl WHERE driver_name ='$row[name]' ORDER BY id DESC";
$result2 = mysqli_query($con,$sql2);

if(mysqli_num_rows($result2) > 0){
	
	while($row2 = mysqli_fetch_assoc($result2)){

		$sql3 = "SELECT * FROM user_tbl WHERE id ='$row2[user_id]'";
		$result3 = mysqli_query($con,$sql3);
		$roww = mysqli_fetch_assoc($result3);

		$data["data"][] = array('id'=>$row2['id'],'rating'=>$row2['rating'],'feedback'=>$row2['feedback'],'name'=>$roww['name'],'number' => $roww['number']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>