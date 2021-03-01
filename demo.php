<?php
$postlat = $_POST['lat'];
$postlng = $_POST['lng'];
$postname = $_POST['name'];
$help = $_POST['help'];
$postphone_number = $_POST['phone_number'];


$mysql_server_name='localhost'; //改成自己的mysql資料庫伺服器
$mysql_username='root'; //改成自己的mysql資料庫使用者名稱
$mysql_password='fish91501'; //改成自己的mysql資料庫密碼
$mysql_database='location'; //改成自己的mysql資料庫名




$conn =new mysqli($mysql_server_name,$mysql_username,$mysql_password,$mysql_database); //連線資料庫

if ($conn->connect_errno) {
    printf("Connect failed: %s\n", $conn->connect_error);
    exit();
}
$sql = "INSERT INTO location (lat,lng,name,help,phone_number) VALUES ('$postlat','$postlng','$postname','$help','$postphone_number')";
if ($conn->query($sql) === TRUE) {
  echo "成功";
} else {
echo "Error: " . $sql . "<br>" . $conn->error;

}
$conn->close();


    $file = fopen("test.txt","a+"); //開啟檔案
    fwrite($file,$postlat);
    fclose($file);
?>