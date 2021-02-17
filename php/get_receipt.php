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

   
 $result =$conn->query("SELECT ID FROM  superdistributor_stocks WHERE Invoice_No='$id'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
              $sid=$row["ID"];
        }
         }

         if($sid==0){
           $result =$conn->query("SELECT ID FROM  distributor_stocks WHERE Invoice_No='$id'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
              $sid=$row["ID"];
        }
         }
         $ride=$conn->query( "SELECT s.ID,s.Invoice_No, m.Name,m.Mobile,m.Address, s.Date, s.User,(SELECT SUM(Price_per_item) FROM distributor_stocks
 WHERE Invoice_No=s.Invoice_No) AS total FROM `distributor_stocks` s INNER JOIN member_table m ON s.DistributorID=m.ID  WHERE s.ID='$sid' ORDER BY s.ID ASC");
         }else{
          $ride=$conn->query( "SELECT s.ID,s.Invoice_No, m.Name,m.Mobile,m.Address, s.Date, s.User,(SELECT SUM(Price_per_item) FROM superdistributor_stocks
 WHERE Invoice_No=s.Invoice_No) AS total FROM `superdistributor_stocks` s INNER JOIN member_table m ON s.DistributorID=m.ID  WHERE s.ID='$sid' ORDER BY s.ID ASC");
         }






while ($user1 = mysqli_fetch_assoc($ride)) {

$jsonRow_201=array(
    "Invoice_No"=>$user1["Invoice_No"],
    "Name"=>$user1["Name"],
    "Address"=>$user1["Address"],
    "Date"=>$user1["Date"],
    "total"=>$user1["total"],
        "Mobile"=>$user1["Mobile"],    
             
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