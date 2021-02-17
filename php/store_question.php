<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['mobile'])){




    $mobile=$_POST['mobile'];
    $mobile=test_input($mobile);

    $first=$_POST['first'];
    $first=test_input($first);
   
    $second=$_POST['second'];
    $second=test_input($second);

    $pmode=$_POST['pmode'];
    $pmode=test_input($pmode);

            $res = $db->Store_Question($mobile,$first,$second,$pmode);

         if ($res) {
       
        $response["error"] = false;
      
    
    } else  {
        $response["error"] = true;
    
    }
 echo json_encode($response);
} else {
    // File parameter is missing
    $response['error'] = true;
  
}
     echo json_encode($response); 
       


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>