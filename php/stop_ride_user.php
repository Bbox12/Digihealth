<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['unique_id'])){
 
    // receiving the post params
    $driver_mobile =isset($_POST['driver_mobile']) ? $_POST['driver_mobile'] : '';
    $unique_id =isset($_POST['unique_id']) ? $_POST['unique_id'] : '';
    $user_mobile = isset($_POST['user_mobile']) ? $_POST['user_mobile'] : '';
    $user_rating=isset($_POST['user_rating']) ? $_POST['user_rating'] : '';

    $review=isset($_POST['review']) ? $_POST['review'] : '';
    $driver_mobile=test_input($driver_mobile);
    $user_mobile=test_input($user_mobile);
    $Unique_id=test_input($unique_id);
     $user_rating=test_input($user_rating);
     $review=test_input($review);

  
        $user = $db->stop_ride_driver($driver_mobile,$user_mobile,$unique_id,$user_rating,$review);
     

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
?>