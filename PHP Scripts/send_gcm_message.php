<?php
	$response = array();
	$account_no = $_GET['account_no'];
	$gcm_id = "aa";
	$conn = new mysqli("***", "***", "****", "****");
    $sql = "SELECT gcm_id FROM user_gcm_credentials WHERE account_no = '$account_no';";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    $gcm_id = array( $row["gcm_id"] );
	$conn->close();
	define( 'API_ACCESS_KEY', '*****' );
	$msg = array
	(
		'message' 	=> 'late',
		'vibrate'	=> 1,
		'sound'		=> 1,
		'largeIcon'	=> 'large_icon',
		'smallIcon'	=> 'small_icon'
	);
	$fields = array
	(
		'registration_ids' 	=> $gcm_id,
		'data'			=> $msg
	);
	$headers = array
	(
		'Authorization: key=' . API_ACCESS_KEY,
		'Content-Type: application/json'
	);
	$ch = curl_init();
	curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
	curl_setopt( $ch,CURLOPT_POST, true );
	curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
	curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
	curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
	curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
	$result = curl_exec($ch );
	curl_close( $ch );
	echo $result;
?>
