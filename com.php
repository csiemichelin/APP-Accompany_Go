<?php
$poststate = $_POST['state'];
$postname = $_POST['name'];
$postphone_number = $_POST['phone_number'];
$postSD_name = $_POST['SD_name'];
$postSDphone_number = $_POST['SD_phone_number'];
$flag=0;

$mysql_server_name='localhost'; //改成自己的mysql資料庫伺服器
$mysql_username='root'; //改成自己的mysql資料庫使用者名稱
$mysql_password='fish91501'; //改成自己的mysql資料庫密碼
$mysql_database='state'; //改成自己的mysql資料庫名




$conn =new mysqli($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); //連線資料庫   state  database

if(!$conn){  
  die('Could not connect: '.mysqli_connect_error());  
}  
echo 'Connected successfully<br/>';  

$id=1;  

$sql ="update state set  state=$poststate where id=$id";



if(mysqli_query($conn, $sql)){  
 echo "Record updated successfully";  
}else{  
echo "Could not update record: ". mysqli_error($conn);  
}  

mysqli_close($conn);  





$mysql_database='fd'; //改成自己的mysql資料庫名
$conn =new mysqli($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); // fd database

if ($conn->connect_errno) {
    printf("Connect failed: %s\n", $conn->connect_error);
    exit();
}



   $sql = "SELECT SD_name, OLD_name FROM fd";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
     
        while($row = $result->fetch_assoc()) {
           
			$output[] = $row;
				   if ($row["SD_name"]==$postSD_name and $row["OLD_name"]==$postname){
			  $flag=1;
        }
        }
	} else {
echo "Error: " . $sql . "<br>" . $conn->error;

}

if($flag==0){
$sql = "INSERT INTO fd (SD_name,SD_num,OLD_name,OLD_num) VALUES ('$postSD_name','$postSDphone_number','$postname','$postphone_number')";

if ($conn->query($sql) === TRUE) {
  echo "成功新增朋友";
} else {
echo "Error: " . $sql . "<br>" . $conn->error;

}
}
elseif($flag==1) {
  echo "你已加過朋友了";
}


$conn->close();



?>

