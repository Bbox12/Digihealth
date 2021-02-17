<?php
 header('Content-Type: application/json');


require_once 'DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['mobile'])){




    $mobile=$_POST['mobile'];
    $mobile=test_input($mobile);

    $risk=$_POST['risk'];
    $risk=test_input($risk);
   
    $time=$_POST['time'];
    $time=test_input($time);

    $pmode=$_POST['pmode'];
    $pmode=test_input($pmode);

    $date=$_POST['date'];
    $date=test_input($date);

            $res = $db->Store_Consultation($mobile,$risk,$time,$pmode,$date);

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