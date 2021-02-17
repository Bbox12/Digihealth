<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TAX INVOICE</title>
    <meta name="description" content="Sufee Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="vendors/jqvmap/dist/jqvmap.min.css">
    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="style.css" media="all" />
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

   <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
 
 <script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
  
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Garima/Dashboard/page-login.html");
  }else{
    var getV='<?php session_start();echo $_GET["id"]; ?>';

    var dataString = 'id='+getV;
    if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
  } else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 xmlhttp.onreadystatechange=function() {
    

    if (this.readyState==4 && this.status==200) {

         myTable(this.responseText);
      
  }
}
 xmlhttp.open("POST","get_receipt.php",true);
 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xmlhttp.send(dataString);  
  }
};
</script>
<script type="text/javascript">
function myTable(response) {
    var arr = JSON.parse(response);
    var i;
 
    if(arr.length==0){
        alert("Please check Invoice  no");
    }else{
        for(i = 0; i < arr.length; i++) {
        	
         document.getElementById("name").innerHTML=arr[i].Name;
              document.getElementById("address").innerHTML=arr[i].Address;
               document.getElementById("phone").innerHTML=arr[i].Mobile;
                  document.getElementById("date").innerHTML=arr[i].Date;
                        document.getElementById("invoice").innerHTML=arr[i].Invoice_No;
                             document.getElementById("total").innerHTML=arr[i].total;
  
  }
       }
     };
   </script>
    <script type="text/javascript">
function printDiv(divName) {
     var printContents = document.getElementById(divName).innerHTML;
     var originalContents = document.body.innerHTML;
     document.body.innerHTML = printContents;
     window.print();
     document.body.innerHTML = originalContents;
};
</script>
</head>

<body style="width: 100%;" onload="myFunction()">


    <!-- Left Panel -->

    <aside id="left-panel" class="left-panel">
        <nav class="navbar navbar-expand-sm navbar-default">

            <div class="navbar-header">
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="./"><img src="assets/images/logo.png" alt="Logo"></a>
                <a class="navbar-brand hidden" href="./"><img src="assets/images/logo.png" alt="Logo" ></a>
            </div>

            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="index.php"> <i class="menu-icon fa fa-dashboard"></i>Dashboard </a>
                    </li>
                <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Add</a>
                        <ul class="sub-menu children dropdown-menu">
                    <li><i class="menu-icon fa fa-th"></i><a href="member_registration.php">Member</a></li>
                          <li><i class="menu-icon fa fa-th"></i><a href="delete.php">Remove Member</a></li>
                        </ul>

                    </li>
                       
                       
                 
                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Product</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i><a href="add_products.php"> Add Products </a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="">Edit Products</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="">Delecte Products</a></li>
                           
                        </ul>

                    </li>
                     
                     
                

                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-arrow-circle-o-left"></i>Login</a>
                        <ul class="sub-menu children dropdown-menu">
                        
                            <li><i class="menu-icon fa fa-sign-in"></i><a href="page-register.html">Register</a></li>
                            <li><i class="menu-icon fa fa-arrow-right"></i><a href="pages-forget.html">Forget Password</a></li>
                             <li><i class="menu-icon fa fa-arrow-circle-o-left"></i><a href="front_page.php">Logout</a></li>
                        </ul>
                    </li>
                    
                </ul>
            </div>
        </nav>
    </aside>

    <div id="right-panel" class="right-panel">

        <!-- Header-->
        <header id="header" class="header">

            <div class="header-menu">

                <div class="col-sm-7">
                    <a id="menuToggle" class="menutoggle pull-left"><i class="fa fa fa-tasks"></i></a>
                    <div class="header-left">
                    
   <div class="dropdown for-notification">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="notification" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fa fa-bell"></i>
                            
                            </button>
                           
                        </div>

                        <div class="dropdown for-message">
                            <button class="btn btn-secondary dropdown-toggle" type="button"
                                id="message"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="ti-email"></i>
                            
                            </button>
                            <div class="dropdown-menu" aria-labelledby="message">
                                <p class="red">You have no Mails</p>
                               
                           
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-sm-5">
                    <div class="user-area dropdown float-left">
                       
                           <a class="nav-link" ><?php  session_start(); echo $_SESSION["email"];?></a>

                    

                        <div class="user-area dropdown-menu float-right">
                            <a class="nav-link" href="#"><i class="fa fa-user"></i> My Profile</a>

                           
                            <a class="nav-link" href="#"><i class="fa fa-cog"></i> Settings</a>

                            <a class="nav-link" href="#"><i class="fa fa-power-off"></i> Logout</a>
                        </div>
                    </div>

                   

                </div>
            </div>

        </header><!-- /header -->
        <!-- Header-->

     <div class="card" style="padding:40px;">
   <div id="printableArea" >

    
      <h1 id="invoice"></h1>
      <div id="company" style="padding: 40px;">
         <div width='128' height='128' align="top" style="background: green;color: black;font-size: 22px; font-weight: bold;">RENAISSANCE KINA BESs samabaya Ltd.</div>
           <div>Regd. Office: Lakhinagar,R.G.Baruah Rd,Kamrup(M),781005</div>
      </div>

  
      <div id="project">
        <div><span style="color: #5D6975;">Name: </span><span id="name" ></span></div>
        <div><span style="color: #5D6975;">Address: </span><span id="address" font size="7"></span></div>
        <div><span style="color: #5D6975;">Phone No:</span><span id="phone" font size="7"></span></div>
        <div><span style="color: #5D6975;">Date:</span><span id="date" font size="7"></span></div>
      </div>

    <main>
             <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                    <thead>
                                       <tr>
                                                     <th>Sl No</th>
                                                           <th>Products</th>  
                                                      <th>Quantity</th>  
                                                           <th>Unit</th>  
                                            <th>Price</th>
                                               
                                        </tr>
                                    </thead>
                                    <tbody>
                                         <?php
                      require_once 'DB_Connect.php';
                
                      $db = new Db_Connect();
                      $conn = $db->connect();
                      session_start();
                      $invoice=$_SESSION["INVOICE"];
                         $date=date("Y-m-d");
                         $server_ip="139.59.38.160";
                       $result =$conn->query("SELECT ID FROM  superdistributor_stocks WHERE Invoice_No='$invoice'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
              $sid=$row["ID"];
        }
         }

         if($sid==0){
           $result =$conn->query("SELECT ID FROM  distributor_stocks WHERE Invoice_No='$invoice'");
          
         if ($result->num_rows > 0) {
          while($row = $result->fetch_assoc()) {
              $sid=$row["ID"];
        }
         }
          $users =$conn->query("SELECT s.ID,s.Invoice_No,p.Name AS Product,s.Stcock,s.Unit,s.Price_per_item, s.Date, s.User FROM `distributor_stocks` s  INNER JOIN products i ON i.ID=s.ProductID INNER JOIN items p ON p.ID=i.ProductID  WHERE s.Invoice_No='$invoice'");
         }else{
        $users =$conn->query("SELECT s.ID,s.Invoice_No,p.Name AS Product,s.Stcock,s.Unit,s.Price_per_item, s.Date, s.User FROM `superdistributor_stocks` s  INNER JOIN products i ON i.ID=s.ProductID INNER JOIN items p ON p.ID=i.ProductID  WHERE s.Invoice_No='$invoice'");
         }
                     
                      
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;
                    ?>
                    <tr>
                
                          <td><?php echo $count; ?></td>
                           <td><?php echo $user['Product']; ?></td>
                          <td><?php echo $user['Stcock']; ?></td>
                        <td><?php echo $user['Unit']; ?></td>
                        <td><?php echo $user['Price_per_item']; ?></td>
                        
                

                    </tr>

                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No user(s) found......</td></tr>
                    <?php endif; ?>

          <tr>
            <td  class="grand total" colspan="4" style="text-align: right;">GRAND TOTAL</td>
            <td class="grand total" id="total" style="text-align: right;"></td>
          </tr>
                                    </tbody>
                           </table>
                           
    
    </main>
 
      </div>

        <input type="button" onclick="printDiv('printableArea')" value="print" />
      </div>

    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>
 

    <script src="vendors/chart.js/dist/Chart.bundle.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script src="assets/js/widgets.js"></script>


</body>

</html>
