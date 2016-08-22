<?php
	$response = array();
	$book_id = $_POST["book_id"];
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
        $sql1 = "SELECT title, author, edition, average_rating FROM books WHERE book_id = $book_id;";
        $result1 = $conn->query($sql1);
        $row1 = $result1->fetch_assoc();
        $response["title"] = $row1["title"];
        $response["author"] = $row1["author"];
        $response["edition"] = $row1["edition"];
        $response["average_rating"] = $row1["average_rating"];
        $response["comments"] = array();
        $sql2 = "SELECT account_no, comment_body, posted_at FROM user_comments WHERE book_id = $book_id;";
        $result2 = $conn->query($sql2);
        while($row2 = $result2->fetch_assoc())
        {
        	$comment = array();
        	$account_no = $row2["account_no"];
        	$sql3 = "SELECT name FROM users WHERE account_no = $account_no;";
        	$result3 = $conn->query($sql3);
        	$row3 = $result3->fetch_assoc();
        	$comment["commenter_name"] = $row3["name"];
        	$comment["comment_body"] = $row2["comment_body"];
        	$comment["posted_at"] = $row2["posted_at"];
        	array_push($response["comments"], $comment);
        }
        echo json_encode($response);
        $conn->close();
    }
?>