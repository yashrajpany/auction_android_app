<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $fullname, $email, $username, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
    
    function auctioner($table, $product, $description, $price, $isHighBid, $bidName)
    {
        $product = $this->prepareData($product);
        $description = $this->prepareData($description);
        $price = $this->prepareData($price);
        $isHighBid = $this->prepareData($isHighBid);
        $bidName = $this->prepareData($bidName);
        $this->sql =
            "INSERT INTO " . $table . " (product, description, price, isHighBid, bidName) VALUES ('" . $product . "','" . $description . "','" . $price . "','" . $isHighBid . "','" . $bidName . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
    
    function productBid($table, $product, $description, $price, $isHighBid, $bidName, $id)
    {
        $price = $this->prepareData($price);
        $isHighBid = $this->prepareData($isHighBid);
        $bidName = $this->prepareData($bidName);
        $product = $this->prepareData($product);
        $description= $this->prepareData($description);
        $id = $this->prepareData($id);

        $this->sql = "select * from " . $table . " where id = '" . $id . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbprice = $row['price'];
            $dbisHighBid = $row['isHighBid'];
            if ($dbprice < $price ) {
                $this->sql = "UPDATE bid SET product ='$product', description = '$description', price = $price, isHighBid = '2', bidName = '$bidName' WHERE id = '$id'";
                mysqli_query($this->connect, $this->sql);
                $bid = true;
            } else $bid = false;
        } else $bid = false;

        return $bid;
    }
}

?>