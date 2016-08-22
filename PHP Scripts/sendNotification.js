function sendNotification()
{
	var accountNo = document.getElementById("accountNoField").value;
	var xhttp = new XMLHttpRequest();
  	xhttp.onreadystatechange = function() 
  	{
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    {
	    	document.getElementById("sentStatus").innerHTML = "Message sent successfully";
	    	document.getElementById("serverResponse").innerHTML = "Server response : " + xhttp.responseText;
	    }
    };
    xhttp.open("GET", "send_gcm_message.php?account_no=" + accountNo, true);
    xhttp.send();
}