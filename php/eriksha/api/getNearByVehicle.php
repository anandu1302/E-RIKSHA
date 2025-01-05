<?php

include("connection.php");
 
$lat = $_POST['latitude'];
$lon = $_POST['longitude'];
 
$sql = "SELECT id, name, latitude, longitude, SQRT( POW(69.1 * (latitude - '$lat'), 2) + POW(69.1 * ('$lon' - longitude) * COS(latitude / 57.3), 2)) AS distance FROM driver_tbl WHERE driver_status='active' HAVING distance < 5 ORDER BY distance DESC ";

$res = mysqli_query($con,$sql);
if(mysqli_num_rows($res) > 0)
{
	while($row = mysqli_fetch_assoc($res))
	{
		$data["data"][] = $row;
	}

	echo json_encode($data);
	
}
else
{
	echo "failed";
}

?>