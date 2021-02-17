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

   
 $result =$conn->query("SELECT `ID`, OTP,(SELECT IDPatient FROM user_details WHERE ID=c.UserID) AS `IDPatient `,(SELECT Gender FROM user_details WHERE ID=c.UserID) AS `Gender`,(SELECT DOB FROM user_details WHERE ID=c.UserID) AS `DOB`,(SELECT Email FROM user_details WHERE ID=c.UserID) AS `Email`,(SELECT Name FROM user_details WHERE ID=c.UserID) AS `Name`,(SELECT PhoneNo FROM user_details WHERE ID=c.UserID) AS `mobile`, `Address`, `Sars`, `Sampletype`, `Indication`, `Country1`, `Ddate1`, `Rdate1`, `Country2`, `Ddate2`, `Rdate2`, `Status`, `Date`, `Time`, (SELECT COUNT(ID) FROM usersymptoms WHERE UserID=c.UserID AND Status=2) AS `Symtoms` FROM `covidtest` c WHERE ID='$id'");
         






while ($user1 = mysqli_fetch_assoc($result)) {

$jsonRow_201=array(
    "ID"=>$user1["ID"],
       "OTP"=>$user1["OTP"],
    "Name"=>$user1["Name"],
    "Email"=>$user1["Email"],
    "Gender"=>$user1["Gender"],
    "PhoneNo"=>$user1["PhoneNo"],
        "IDPatient"=>$user1["IDPatient"],    
             "DOB"=>$user1["DOB"],     
                    "Sampletype"=>$user1["Sampletype"],    
             "Indication"=>$user1["Indication"],
                       "Address"=>$user1["Address"],    
             "Country1"=>$user1["Country1"],  
                 "Sars"=>$user1["Sars"],    

                  "Ddate1"=>$user1["Ddate1"],    
             "Rdate1"=>$user1["Rdate1"],
                       "Ddate2"=>$user1["Ddate2"],    
             "Rdate2"=>$user1["Rdate2"],  
                 "Status"=>$user1["Status"],    
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