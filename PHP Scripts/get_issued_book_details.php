<?php
	$response = array();
	$account_no = $_POST['account_no'];
	$conn = new mysqli("mysql8.000webhost.com","a8522340_rhari","hari1234","a8522340_library");
    if ($conn->connect_error)
    {
        $response["success"] = 0;
        $response["message"] = "Error : Could not connect to database";
        echo json_encode($response);
    } 
    else
    {
        $response["success"] = 1;
        $sql1 = "SELECT record_id, borrowed_book_id, DATE(date_issued) as issue_date, DATE(due_date) as ".
                "due_date, fine_amount, DATE(DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY)) as new_return_date FROM records WHERE borrower_id = $account_no AND date_returned IS NULL;";
        $result1 = $conn->query($sql1);
        if($result1->num_rows == 0)
        {
        	$response["no_of_books_issued"] = 0;
        	echo json_encode($response);
        }
        else if($result1->num_rows == 1)
        {
        	$response["no_of_books_issued"] = 1;
        	$row1 = $result1->fetch_assoc();
            $response["book_1_details"] = array();
        	$issued_book_id = $row1["borrowed_book_id"];
        	$sql2 = "SELECT title, author FROM books WHERE book_id=$issued_book_id;";
            $result2 = $conn->query($sql2);
            $row2 = $result2->fetch_assoc();
        	$book_details = array();
            $book_details["record_id"] = $row1["record_id"];
            $book_details["book_id"] = $row1["borrowed_book_id"];
        	$book_details["title"] = $row2["title"];
        	$book_details["author"] = $row2["author"];
        	$book_details["issue_date"] = $row1["issue_date"];
        	$book_details["due_date"] = $row1["due_date"];
            $book_details["new_return_date"] = $row1["new_return_date"];
        	$book_details["fine_amount"] = $row1["fine_amount"];
        	array_push($response["book_1_details"], $book_details);
        	echo json_encode($response);
        }
        else if($result1->num_rows == 2)
        {
        	$response["no_of_books_issued"] = 2;
        	$row1 = $result1->fetch_assoc();
            $response["book_1_details"] = array();
        	$issued_book_id = $row1["borrowed_book_id"];
        	$sql2 = "SELECT title, author FROM books WHERE book_id=$issued_book_id;";
        	$result2 = $conn->query($sql2);
        	$row2 = $result2->fetch_assoc();
        	$book_details = array();
            $book_details["record_id"] = $row1["record_id"];
            $book_details["book_id"] = $row1["borrowed_book_id"];
        	$book_details["title"] = $row2["title"];
        	$book_details["author"] = $row2["author"];
        	$book_details["issue_date"] = $row1["issue_date"];
        	$book_details["due_date"] = $row1["due_date"];
            $book_details["new_return_date"] = $row1["new_return_date"];
        	$book_details["fine_amount"] = $row1["fine_amount"];
        	array_push($response["book_1_details"], $book_details);
        	$response["book_2_details"] = array();
        	$row1 = $result1->fetch_assoc();
        	$issued_book_id = $row1["borrowed_book_id"];
        	$sql2 = "SELECT title, author FROM books WHERE book_id=$issued_book_id;";
        	$result2 = $conn->query($sql2);
        	$row2 = $result2->fetch_assoc();
        	$book_details = array();
            $book_details["record_id"] = $row1["record_id"];
            $book_details["book_id"] = $row1["borrowed_book_id"];
        	$book_details["title"] = $row2["title"];
        	$book_details["author"] = $row2["author"];
        	$book_details["issue_date"] = $row1["issue_date"];
        	$book_details["due_date"] = $row1["due_date"];
            $book_details["new_return_date"] = $row1["new_return_date"];
        	$book_details["fine_amount"] = $row1["fine_amount"];
        	array_push($response["book_2_details"], $book_details);
        	echo json_encode($response);
        }
        $conn->close();
    }
?>