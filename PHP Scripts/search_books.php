<?php 
	$response = array();
	$search_string=$_POST['search_string'];
	$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
    if ($conn->connect_error)	
    {
        $response["success"] = 0;
        $response["message"] = "Error : Could not connect to database";
        echo json_encode($response);
    } 
	else
	{  
		$sql1 = "SELECT title, author, book_id FROM books WHERE title LIKE '%".$search_string."%';";
		$result1 = $conn->query($sql1);
		$response["success"] = 1;
		$response["no_of_books"]=$result1->num_rows;
		$response["books"] = array();
	    while($row1 = $result1->fetch_assoc())
        {
        	$book_id = $row1['book_id'];
        	$sql2 = "SELECT record_id FROM records WHERE issued_book_id = $book_id AND date_returned IS NULL;";
        	$result2 = $conn->query($sql2);
        	$is_issued = "false";
        	if($result2 -> num_rows == 1)
        		$is_issued = "true";
			$book_details = array();
			$book_details['title'] = $row1['title'];
			$book_details['author'] = $row1['author'];
			$book_details['book_id'] = $row1['book_id'];
			$book_details['is_issued'] = $is_issued;
			array_push($response["books"], $book_details);
		}			
		echo json_encode($response);
	} 
	$conn -> close();
?>