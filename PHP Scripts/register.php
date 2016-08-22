<?php
	$response = array();
	if (isset($_GET['name']) && isset($_GET['email']) && isset($_GET['password']) && isset($_GET['address']) 
	&& isset($_GET['phone_number']) && isset($_GET['dob']) && isset($_GET['gender']) && isset($_GET['class']) 
    && isset($_GET['year']))
	{
		$name = $_GET['name'];
		$email = $_GET['email'];
		$password = $_GET['password'];
		$address = $_GET['address'];
		$phone_number = $_GET['phone_number'];
		$dob = $_GET['dob'];
		$gender = $_GET['gender'];
		$class = $_GET['class'];
		$year = $_GET['year'];
		$conn = new mysqli("localhost","Hari","hari1234","library");
		if ($conn->connect_error)
        {
        	die("Connection failed: " . $conn->connect_error);
        } 
        $sql = "INSERT INTO users (name, email, password, address, phone_number, dob, gender, class, year) VALUES".
        "('$name', '$email', '$password', '$address', '$phone_number', '$dob', '$gender', '$class', '$year');";
        if($conn->query($sql)) 
        {
        	$response["success"] = 1;
            $response["message"] = "User successfully created.";
            echo json_encode($response);
        }
        else
        {
         	$response["success"] = 0;
            $response["message"] = "Oops! An error occurred.";
            echo json_encode($response);       	
        }
        $conn->close();
	}
	else
	{
    	$response["success"] = 0;
    	$response["message"] = "Required field(s) is missing";
    	echo json_encode($response);
	}
?>