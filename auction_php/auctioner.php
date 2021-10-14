<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['product']) && isset($_POST['description']) && isset($_POST['price']) && isset($_POST['isHighBid']) && isset($_POST['bidName'])) {
    if ($db->dbConnect()) {
        if ($db->auctioner("bid", $_POST['product'], $_POST['description'], $_POST['price'], $_POST['isHighBid'], $_POST['bidName'])) {
            echo "Added Successfully";
        } else echo "Added Unsuccessfull";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>