<?php
    $servername = "localhost";
    $username = "root";
    $password = "fish91501";
    $dbname = "state";
	$flag=0;
   
    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("connect fail: " . $conn->connect_error);
    }
    
    $sql = "SELECT state FROM state";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
     
        while($row = $result->fetch_assoc()) {
           
			$output[] = $row;
		   if ($row["state"]=='1'){
			  $flag=1;
        }
		
		}
		
	print((json_encode($output, JSON_UNESCAPED_UNICODE)));
	
		
    } else {
        echo "對方還沒到見你";
    }
	

	mysqli_close($conn);
?>