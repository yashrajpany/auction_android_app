<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['product']) && isset($_POST['description']) && isset($_POST['price']) && isset($_POST['isHighBid']) && isset($_POST['bidName'])&& isset($_POST['id'])) {
    if ($db->dbConnect()) {
        if ($db->productBid("bid", $_POST['product'], $_POST['description'], $_POST['price'], $_POST['isHighBid'], $_POST['bidName'], $_POST['id'])) {
            echo "Bid Successful";
        } else echo "Bid Unsuccessfull";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>