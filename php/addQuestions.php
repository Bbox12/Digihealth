<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
 header('Content-Type: application/json');
 session_start();

require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

var_dump($_POST);

if (isset($_POST['first'])){



 
 $ID=$_POST["ID"];
$ID=test_input($ID);
$first=$_POST["first"];
$first=test_input($first);
$second=$_POST["second"];
$second=test_input($second);
$role=$_POST["role"];
$role=test_input($role);
$User= $_SESSION["email"];
$IP= $_SERVER['REMOTE_ADDR'];
$User= test_input($User);
$IP= test_input($IP);



     $user = $db->addQuestions($ID,$first,$second,$role,$User,$IP);
             if ($user) {  
          $_SESSION["error"]=1;
               $_SESSION["lat"]=$Latitude;
                    $_SESSION["long"]=$Longitude;
        $response["error"] = false;
      header('Location: http://139.59.38.160/Corona/Dashboard/AddPrimaryService.php');
    } else  {
            $_SESSION["error"]=2;
        $response["error"] = true;
          header('Location: http://139.59.38.160/Corona/Dashboard/AddPrimaryService.php');
    }

 
 

    



} else {
     $_SESSION["error"]=2;
    $response['error'] = true;
        header('Location: http://139.59.38.160/Corona/Dashboard/AddPrimaryService.php');
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>