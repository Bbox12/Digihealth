<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');
 session_start();

require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);



if (isset($_GET['id'])){


$id=0;
$User= $_SESSION["email"];
$IP= $_SERVER['REMOTE_ADDR'];
$User= test_input($User);
$IP= test_input($IP);

    $id=$_GET["id"];
    $id= test_input($id);

     $user = $db->SiteDeleteSymptoms($id,$User,$IP);
        if ($user) {  
          $_SESSION["error"]=1;
        header('Location: http://139.59.38.160/Corona/Dashboard/AddSymtomps.php');
    } else  {
            $_SESSION["error"]=2;
          header('Location: http://139.59.38.160/Corona/Dashboard/AddSymtomps.php');
    }
 
    



} else {
     $_SESSION["error"]=2;
    $response['error'] = true;
        header('Location: http://139.59.38.160/Corona/Dashboard/AddSymtomps.php');
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>