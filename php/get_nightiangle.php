<?php session_start();


header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();


if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 

if (isset($_POST["id"])){
 
    $id= $_POST["id"];       
    $id=test_input($id);

   
     
try{
$server_ip="139.59.38.160";
    $response = array();

   
 $result =$conn->query("SELECT u.ID,u.Name,u.PhoneNo,u.Gender,u.DOB,u.Email,c.WAQF, c.Option,c.IDSecondtLevel,c.Date,c.Time,cc.Age,(SELECT  `Question` FROM `secondlevelquestions` WHERE ID=cc.IDSecondtLevel) AS IDSecondtLevel ,(SELECT `Factors` FROM `risk_factors` WHERE `ID`=cc.RiskFactor) AS RiskFactor,y.Category AS symtoms,s.symID, s.status FROM `customer_questionlevel` c INNER JOIN user_details u ON u.ID=c.IDUser INNER JOIN customer_consultation cc ON cc.IDUser=c.IDUser INNER JOIN `usersymptoms` s ON s.UserID=c.IDUser INNER JOIN symptoms y ON y.ID=s.symID WHERE `Option`!=0 AND c.IDFirstLevel=1 ORDER BY ID ASC WHERE c.IDUser='$id'");
         






while ($user1 = mysqli_fetch_assoc($result)) {

$jsonRow_201=array(
    "ID"=>$user1["ID"],
       "OTP"=>$user1["OTP"],
    "Name"=>$user1["Name"],
    "Email"=>$user1["Email"],
    "Gender"=>$user1["Gender"],
    "PhoneNo"=>$user1["PhoneNo"],
        "symID"=>$user1["symID"],    
             "status"=>$user1["status"],     
                    "Date"=>$user1["Date"],    
             "Time"=>$user1["Time"],
                       "statusdate"=>$user1["statusdate"],    
             "statustime"=>$user1["statustime"],  
                 "feedback"=>$user1["feedback"],    
 );

array_push($response, $jsonRow_201);
  
}

   echo json_encode($response);    

} catch(Error $e) {
    $trace = $e->getTrace();
    echo $e->getMessage().' in '.$e->getFile().' on line '.$e->getLine().' called from '.$trace[0]['file'].' on line '.$trace[0]['line'];
}

}
}
 
    
 
function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>