<?php
	$response = array();
	salil
	$account_no = $_POST['account_no'];
	$book_id = $_POST['book_id'];
	$comment_body = $_POST['comment_body'];
	$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
    if ($conn->connect_error)
    {
        $response["success"] = 0;
        $response["message"] = "Error : Could not connect to database";
        echo json_encode($response);
    } 
    else
    {
        $sql = "INSERT INTO `user_comments` (`comment_id`, `account_no`, `book_id`, `comment_body`, `posted_at`) VALUES (NULL, '$account_no', '$book_id', '$comment_body', CURRENT_TIMESTAMP);";
        if($conn->query($sql))
		{
			$response["success"]=1;
			echo json_encode($response);	
		}

		else
		{
			$response["success"]=0;
			echo json_encode($response);	
		}
		mysqli_close($conn);
    }
?>