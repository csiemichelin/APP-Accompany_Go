<?php
    $servername = "localhost";
    $username = "root";
    $password = "fish91501";
    $dbname = "fd";
	$postname = $_POST['name'];

   
    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("connect fail: " . $conn->connect_error);
    }
    
    $sql = "SELECT SD_name, OLD_name,OLD_num,SD_num FROM fd";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
     
        while($row = $result->fetch_assoc()) {
           
		  if($row["OLD_name"]==$postname ){
			$output[] = $row;
		  }
        }
		
		
	print((json_encode($output, JSON_UNESCAPED_UNICODE)));
	
		
    } else {
        echo "暫時沒有任務資訊";
    }
	
	
    $conn->close();
?>