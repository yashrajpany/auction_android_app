<?php
define('DB_HOST','localhost');
define('DB_USER','root');
define('DB_PASS','');
define('DB_NAME','auction');
$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

if(mysqli_connect_errno()){
	die('Unable to connect to database' . mysqli_connect_error());
}

$stmt = $conn->prepare("SELECT id, product, description, price, bidName FROM bid;");

$stmt ->execute();
$stmt -> bind_result($id, $product, $description, $price, $bidName);

$bid = array();

while($stmt ->fetch()){

    $temp = array();
	$temp['id'] = $id;
	$temp['product'] = $product;
	$temp['description'] = $description;
	$temp['price'] = $price;
	$temp['bidName'] = $bidName;
	
	
	array_push($bid,$temp);
	}

	echo json_encode($bid);

?>