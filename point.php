<?php
$postpoint = $_POST['point'];


$mysql_server_name='localhost'; //改成自己的mysql資料庫伺服器
$mysql_username='root'; //改成自己的mysql資料庫使用者名稱
$mysql_password='fish91501'; //改成自己的mysql資料庫密碼
$mysql_database='point'; //改成自己的mysql資料庫名


$temp=0;

$conn =new mysqli($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); //連線資料庫

if(!$conn){  
  die('Could not connect: '.mysqli_connect_error());  
}  
echo 'Connected successfully<br/>';  

$id=1;  




$sql = "SELECT point FROM point";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
     
        while($row = $result->fetch_assoc()) {
           $temp=$row['point'];

        }
		
		}

$sql ="update point set  point=$postpoint+$temp where id=$id";



if(mysqli_query($conn, $sql)){  
 echo "Record updated successfully";  
}else{  
echo "Could not update record: ". mysqli_error($conn);  
}  

mysqli_close($conn);  







$mysql_database='state';
$conn =new mysqli($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); //連線資料庫

if(!$conn){  
  die('Could not connect: '.mysqli_connect_error());  
}  
echo 'Connected successfully<br/>';  

$id=1;  

$sql ="update state set  state=0 where id=$id";



if(mysqli_query($conn, $sql)){  
 echo "Record updated successfully";  
}else{  
echo "Could not update record: ". mysqli_error($conn);  
}  

mysqli_close($conn); 



?>

