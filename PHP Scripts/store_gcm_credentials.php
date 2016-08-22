<?php
	$response = array();
	$account_no = $_POST['account_no'];
	$gcm_id = $_POST['gcm_id'];
	$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
    if ($conn->connect_error)
    {
        $response["success"] = 0;
        $response["message"] = "Error : Could not connect to database";
        echo json_encode($response);
    } 
    else
    {
    	$sql1 = "SELECT gcm_id FROM user_gcm_credentials WHERE account_no = '$account_no';";
    	$result = $conn->query($sql1)
    	if($result->num_rows == 0)
    	{
	    	$sql2 = "INSERT INTO user_gcm_credentials (`account_no`, `gcm_id`) VALUES ('$account_no', '$gcm_id');";
	        if($conn->query($sql2))
			{
				$response["success"]=1;
				echo json_encode($response);	
			}

			else
			{
				$response["success"]=0;
				echo json_encode($response);	
			}
		}
		else
		{
			$response["success"]=1;
			echo json_encode($response);	
		}
		$conn->close();
    }
?>