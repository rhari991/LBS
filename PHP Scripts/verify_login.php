<?php
	$response = array();
	if (isset($_GET['account_no']) && isset($_GET['password']))
	{
		$account_no = $_POST['account_no'];
		$password = $_POST['password'];
		$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
		if ($conn->connect_error)
        {
        	$response["success"] = -2;
            $response["message"] = "Error : Could not connect to database";
            echo json_encode($response);
        } 
        else
        {
            $sql = "SELECT account_no FROM users WHERE account_no = '$account_no' AND password = '$password';";
            $result = $conn->query($sql);
            if($result == false)
            {
                $response["success"] = -1;
                $response["message"] = "Error : Could not execute SQL statement : $sql";
                echo json_encode($response);
            }
            else if ($result->num_rows > 0)
            {
            	$row = $result->fetch_assoc();
            	$response["success"] = 1;
            	$response["message"] = "Login successful";
            	$response["account_no"] = $row["account_no"];
            	echo json_encode($response);
            }
            else
            {
            	$response["success"] = 0;
            	$response["message"] = "Invalid credentials";
            	echo json_encode($response);
            }
            $conn->close();
        }
	}
	else
	{
		$response["success"] = -1;
   	    $response["message"] = "Required field(s) is missing";
   	    echo json_encode($response);
	} 
?>