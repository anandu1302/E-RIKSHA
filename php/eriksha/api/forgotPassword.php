<?php 
include('connection.php');

	
	$email=$_POST['email'];

	$sel="select * from user_tbl where email='$_POST[email]'";
	$result = mysqli_query($con,$sel) or die(mysql_error());
	$row=mysqli_fetch_array($result);

	if($result > 0){

		$subject="Welcome To Eriksha";
	    $title="Your Password";
	    $msg="Greetings from Eriksha. \n Your Username id is: $row[username] \n Your password is: $row[password]";
	    include('mail.php');

	    echo"";
	}else{
		echo "failed";
	}
	
	


?>