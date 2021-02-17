<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['mobile'])){
 
    // receiving the post params
      $mobile =$_POST['mobile'];
      $mobile=test_input($mobile);


       $address =$_POST['address'];
      $address=test_input($address);



       $sars =$_POST['sars'];
      $sars=test_input($sars);




       $sample =$_POST['sample'];
      $sample=test_input($sample);



       $indication =$_POST['indication'];
      $indication=test_input($indication);



       $country1 =$_POST['country1'];
      $country1=test_input($country1);


      $ddate1 =$_POST['ddate1'];
      $ddate1=test_input($ddate1);



       $rdate1 =$_POST['rdate1'];
      $rdate1=test_input($rdate1);



       $country2 =$_POST['country2'];
      $country2=test_input($country2);

   
 $ddate2 =$_POST['ddate2'];
      $ddate2=test_input($ddate2);



       $rdate2 =$_POST['rdate2'];
      $rdate2=test_input($rdate2);



  
        $user = $db->stop_covid_test($mobile,$address,$sars,$sample,$indication,$country1,$ddate1,$rdate1,$country2,$ddate2,$rdate2);
     

        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters missing!";
    echo json_encode($response);
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

function generateRandomString($length = 6) {
    $characters = '0123456789';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}


?>