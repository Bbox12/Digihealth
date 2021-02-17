<?php
// Report runtime errors
error_reporting(E_ERROR | E_WARNING | E_PARSE);

// Report all errors
error_reporting(E_ALL);



header('Content-Type: application/json');
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $conn = $db->connect();

 
if(!$conn){
   echo "Could not connect to DBMS"; 
    }else { 



 
    $mobile= $_POST['_mId'];
    
   
    $mobile=test_input($mobile);




if (isset($_POST['country'])){
    $country= $_POST['country'];
    
    $country = str_replace(' ', '', $country);
   
    $country=test_input($country);
       $links=$conn->query( "SELECT `ID`, `Country`, `Link` FROM `govt` WHERE `Country`='$country'");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                        
                             "Links"=>$user1["Link"],
                      
 );

array_push($response["govt"], $jsonRow_201);
  
} 
  }
  
    $date=date("Y-m-d");


try{
$server_ip="139.59.38.160";
  $response = array("symtoms"=>array(),"login"=>array(),"category"=>array(),"links1"=>array(),"links2"=>array(),"govt"=>array(),"news"=>array(),"videos"=>array(),"satistics"=>array(),"askwho"=>array(),"questions"=>array(),"brand"=>array(),"pro"=>array(),"request"=>array(),"questionss"=>array(),"general"=>array(),"sampletype"=>array(),"indication"=>array(),"covid"=>array(),"maxid"=>array(),"firstlevel"=>array(),"secondlevel"=>array()
,"risk"=>array(),"timeslot"=>array());
  $uID=0;


 $result =$conn->query("SELECT ID FROM  user_details WHERE PhoneNo=$mobile");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
          $uID=$row["ID"];
        }
         }


 $timeslot=$conn->query( "SELECT `ID`, `Slot`, `ActualTime`, `Date`, `IDSalon` FROM `timeslot`");

          while ($user1 = mysqli_fetch_assoc($timeslot)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                    
                                                      "Slot"=>$user1["Slot"],
                                 
 );

array_push($response["timeslot"], $jsonRow_201);
  
}

         $risk=$conn->query( "SELECT `ID`, `Factors`, `Date`, `Time` FROM `risk_factors`");

          while ($user1 = mysqli_fetch_assoc($risk)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                    
                                                      "Factors"=>$user1["Factors"],
                                 
 );

array_push($response["risk"], $jsonRow_201);
  
}


$firstlevel=$conn->query( "SELECT `ID`, `Question`, `isActive`, `Date`, `Time` FROM `firstlevelquestions` WHERE `isActive`=1");

          while ($user1 = mysqli_fetch_assoc($firstlevel)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                    
                                                      "Question"=>$user1["Question"],
                                 
 );

array_push($response["firstlevel"], $jsonRow_201);
  
}

$secondlevel=$conn->query( "SELECT `ID`, `Question`, `isActive`, `Date`, `Time` FROM `secondlevelquestions` WHERE `isActive`=1");

          while ($user1 = mysqli_fetch_assoc($secondlevel)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                    
                                                      "Question"=>$user1["Question"],
                                 
 );

array_push($response["secondlevel"], $jsonRow_201);
  
}


         $covid=$conn->query( "SELECT `ID`, `UserID`, `Address`, `Sars`, `Sampletype`, `Indication`, `Country1`, `Ddate1`, `Rdate1`, `Country2`, `Ddate2`, `Rdate2`,Status, `Date`, `Time` FROM `covidtest` WHERE `UserID`='$uID' ORDER BY ID DESC LIMIT 1");

          while ($user1 = mysqli_fetch_assoc($covid)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Address"=>$user1["Address"],
                                                      "Sars"=>$user1["Sars"],
                                                             "Sampletype"=>$user1["Sampletype"],
                                                                 "Indication"=>$user1["Indication"],
                                                 "Country1"=>$user1["Country1"],
                                                      "Ddate1"=>$user1["Ddate1"],
                                                             "Rdate1"=>$user1["Rdate1"],
                                                             "Country2"=>$user1["Country2"],
                                                      "Ddate2"=>$user1["Ddate2"],
                                                             "Rdate2"=>$user1["Rdate2"],
                                                                "Status"=>$user1["Status"],
                                                              "Date"=>$user1["Date"],
                                                             "Time"=>$user1["Time"],
                                              
 );

array_push($response["covid"], $jsonRow_201);
  
}


$symtoms=$conn->query( "SELECT `ID`, `Category`, `Photo`, `Description`, `isActive`, `Date`, `Time`, `User`,(SELECT status FROM usersymptoms WHERE symID=s.ID AND UserID='$uID') AS Client,(SELECT feedback FROM usersymptoms WHERE symID=s.ID AND UserID='$uID') AS feedback FROM `symptoms` s   WHERE `isActive`=1");

          while ($user1 = mysqli_fetch_assoc($symtoms)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Name"=>$user1["Category"],
                                                      "Client"=>$user1["Client"],
                                                             "feedback"=>$user1["feedback"],
                                              
 );

array_push($response["symtoms"], $jsonRow_201);
  
}

$sampletype=$conn->query( "SELECT `ID`, `Name` FROM `sampletype` ");

          while ($user1 = mysqli_fetch_assoc($sampletype)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Name"=>$user1["Name"],
                                                     
 );

array_push($response["sampletype"], $jsonRow_201);
  
}

$indication=$conn->query( "SELECT `ID`, `Name` FROM `indication`  ");

          while ($user1 = mysqli_fetch_assoc($indication)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Name"=>$user1["Name"],
                                                     
 );

array_push($response["indication"], $jsonRow_201);
  
}


 $general=$conn->query( "SELECT `ID`, `Title`, `Description`, `Photo`, `Date`, `Time` FROM `generalinfo`");

          while ($user1 = mysqli_fetch_assoc($general)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Title"=>$user1["Title"],
                                                      "Description"=>$user1["Description"],
                                                 "Photo"=>$user1["Photo"],
 );

array_push($response["general"], $jsonRow_201);
  
}







 $questionss=$conn->query( "SELECT `ID`, `Category`, `Question`, `Ans`, `User`, `Date`, `Time` FROM `questions` WHERE `ID`>3");

          while ($user1 = mysqli_fetch_assoc($questionss)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Category"=>$user1["Category"],
                                                      "Question"=>$user1["Question"],
                                                 "Ans"=>$user1["Ans"],
 );

array_push($response["questionss"], $jsonRow_201);
  
}








 $request=$conn->query( "SELECT `ID`, `Name`, `User`, `Date`, `Time`, `isActive` FROM `requests` WHERE `isActive`=1");

          while ($user1 = mysqli_fetch_assoc($request)) {

$jsonRow_201=array(
             
                                         "ID"=>$user1["ID"],
                                                 "Name"=>$user1["Name"],
 );

array_push($response["request"], $jsonRow_201);
  
}

 $category=$conn->query( "SELECT `ID`, `Photo` FROM `protect`");

          while ($user1 = mysqli_fetch_assoc($category)) {

$jsonRow_201=array(
             
                
                             "Photo"=>$user1["Photo"],
                      
 );

array_push($response["pro"], $jsonRow_201);
  
}

 $category=$conn->query( "SELECT `ID`, `Image` FROM `askwho`");

          while ($user1 = mysqli_fetch_assoc($category)) {

$jsonRow_201=array(
             
                
                             "Photo"=>$user1["Image"],
                      
 );

array_push($response["brand"], $jsonRow_201);
  
}

  

          $login=$conn->query( "SELECT `ID`, `Name`, `Email`, `Photo`, `PhoneNo`,`Gender`,`DOB`,IDPatient,`Emergencyname`, `Emergencyno`,`Passport`,`Latitude`, `Longitude`, `Is_Blocked`, `User_Referrence_Code`, `FirebaseToken`, `Date`, `Time`, `User`, `IP` FROM `user_details` WHERE PhoneNo='$mobile'");

          while ($user1 = mysqli_fetch_assoc($login)) {

$jsonRow_201=array(
             
                             "Name"=>$user1["Name"],
                             "PhoneNo"=>$user1["PhoneNo"],
                         "Photo"=>'http://' . $server_ip . '/' . 'Corona' . '/' .'App'.'/'.'user'.'/'.$user1["Photo"],
                          "Gender"=>$user1["Gender"],
                               "Email"=>$user1["Email"],
                                "Date_of_Birth"=>$user1["DOB"],
               "IDPatient"=>$user1["IDPatient"],
                                 "Emergencyname"=>$user1["Emergencyname"],
               "Emergencyno"=>$user1["Emergencyno"],
                                "Passport"=>$user1["Passport"],
                                 "date"=>$date,

                                 
 );

array_push($response["login"], $jsonRow_201);
  
}


 $maxid=$conn->query( "SELECT MAX(`ID`) AS Max FROM `user_details`");

          while ($user1 = mysqli_fetch_assoc($maxid)) {

$jsonRow_201=array(
             
                             "ID"=>"DD".str_pad($user1["Max"]+1, 4, "0", STR_PAD_LEFT),
                                                      
 );

array_push($response["maxid"], $jsonRow_201);
  
}


          $category=$conn->query( "SELECT `ID`, `Category`, `Photo`, `Date` FROM `symptoms` LIMIT 4");

          while ($user1 = mysqli_fetch_assoc($category)) {

$jsonRow_201=array(
             
                             "Category"=>$user1["Category"],
                             "Photo"=>$user1["Photo"],
                      
 );

array_push($response["category"], $jsonRow_201);
  
}

         $links=$conn->query( "SELECT `ID`, `Subject`, `Links` FROM `links` WHERE ID=1");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "Subject"=>$user1["Subject"],
                             "Links"=>$user1["Links"],
                      
 );

array_push($response["links1"], $jsonRow_201);
  
}
$links=$conn->query( "SELECT `ID`, `Subject`, `Links` FROM `links` WHERE ID=2");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "Subject"=>$user1["Subject"],
                             "Links"=>$user1["Links"],
                      
 );

array_push($response["links2"], $jsonRow_201);
  
}


   $links=$conn->query( "SELECT `ID`, `Subject`, `Links` FROM `links` WHERE ID=4");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "Subject"=>$user1["Subject"],
                             "Links"=>$user1["Links"],
                      
 );

array_push($response["videos"], $jsonRow_201);
  
}  
   $links=$conn->query( "SELECT `ID`, `Subject`, `Links` FROM `links` WHERE ID=5");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "Subject"=>$user1["Subject"],
                             "Links"=>$user1["Links"],
                      
 );

array_push($response["satistics"], $jsonRow_201);
  
}  
   $links=$conn->query( "SELECT `ID`, `Subject`, `Links` FROM `links` WHERE ID=6");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "Subject"=>$user1["Subject"],
                             "Links"=>$user1["Links"],
                      
 );

array_push($response["askwho"], $jsonRow_201);
  
}    

   $links=$conn->query( "SELECT `ID`, `picture`, `fromurl`, `subject`, `message`, `Links` FROM `news`");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
             
                             "picture"=>$user1["picture"],
                             "fromurl"=>$user1["fromurl"],
                                "subject"=>$user1["subject"],
                             "message"=>$user1["message"],
                                     "Links"=>$user1["Links"],
                      
 );

array_push($response["news"], $jsonRow_201);
  
}  

   $links=$conn->query( "SELECT  `ID` ,  `Category` ,  `Question` ,  `Ans` 
FROM  `questions`");

          while ($user1 = mysqli_fetch_assoc($links)) {

$jsonRow_201=array(
                   "Category"=>$user1["Category"],
                             "Question"=>test_input($user1["Question"]),
                          
 );

array_push($response["questions"], $jsonRow_201);
  
}  

   echo json_encode($response);    

} catch(Error $e) {
    $trace = $e->getTrace();
    echo $e->getMessage().' in '.$e->getFile().' on line '.$e->getLine().' called from '.$trace[0]['file'].' on line '.$trace[0]['line'];
}


}
 
    
 
function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}
?>