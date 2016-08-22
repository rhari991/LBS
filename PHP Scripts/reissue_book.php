<?php
	$response = array();
	$record_id = $_POST["record_id"];
	$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
	if ($conn->connect_error)
    {
    	die("Connection failed: " . $conn->connect_error);
    } 
    $sql = "UPDATE records SET due_date = DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY) WHERE record_id = $record_id;";
    if($conn->query($sql)) 
    {
    	$response["success"] = 1;
        $response["message"] = "The book has been reissued successfully.";
        echo json_encode($response);
    }
    else
    {
     	$response["success"] = 0;
        $response["message"] = "Oops! An error has occurred.";
        echo json_encode($response);       	
    }
    $conn->close();
?>