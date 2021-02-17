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
    <title>Sicknote</title>
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
 xmlhttp.open("POST","get_nightiangle.php",true);
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
        alert("Error");
    }else{
        for(i = 0; i < arr.length; i++) {
        	   document.getElementById("ID").innerHTML=arr[i].OTP;
         document.getElementById("name").innerHTML=arr[i].Name;
           document.getElementById("email").innerHTML=arr[i].Email;
               document.getElementById("phone").innerHTML=arr[i].PhoneNo;
                  document.getElementById("date").innerHTML=arr[i].Date;
                         document.getElementById("gender").innerHTML=arr[i].Gender;
                    document.getElementById("SalonID").value=arr[i].status;
                       
  document.getElementById("Details").value=arr[i].feedback;

  
  }
       }
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
                <a class="navbar-brand" href="./"><img src="images/logo.png" alt="Logo"></a>
                <a class="navbar-brand hidden" href="./"><img src="images/logo.png" alt="Logo"></a>
            </div>

            <div id="main-menu" class="main-menu collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="admin.php"> <i class="menu-icon fa fa-dashboard"></i>Dashboard </a>
                    </li>


                            <h3 class="menu-title">Questionnaire</h3>
                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Questions</a>
                        <ul class="sub-menu children dropdown-menu">
                               <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddRequests.php">Add  request list</a></li>
                                    <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddSymtomps.php">Add symptoms</a></li>
                           <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddPrimaryService.php">Add health Questions</a></li>
                          <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddGeneralInfo.php">Add General Info</a></li>
                        </ul>
                    </li>



                      <h3 class="menu-title">Main Page</h3>
                     <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-th"></i>Add </a>
                        <ul class="sub-menu children dropdown-menu">
                           <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="WorkingSites.php">Add Hot spot</a></li>
                     <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="Quaratinezone.php">Display Hot spot</a></li>
                                
                        </ul>
                    </li>



                          <li class="menu-item-has-children dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="menu-icon fa fa-list"></i>Policy</a>
                        <ul class="sub-menu children dropdown-menu">
                            <li><i class="menu-icon fa fa-th"></i><a href="http://139.59.38.160/comingsoon/index.html">New</a></li>
                            <li><i class="menu-icon fa fa-th"></i><a href="http://139.59.38.160/comingsoon/index.html">Update</a></li>
                        </ul>
                    </li>
 
  <h3 class="menu-title">Extras</h3><!-- /.menu-title -->
                    <li class="menu ">
                        <a href="page-login.php" ><i class="menu-icon fa fa-sign-out"></i> LOGOUT</a>
                       
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

     <div class="card" style="padding:40px;" id="dataTable1">
   <div id="printableArea" >

    
      <h1 >SICK NOTE</h1>
  
  
      <div id="project">
          <div><span style="color: #5D6975;">ID: </span><span id="ID" ></span></div>
        <div><span style="color: #5D6975;">Name: </span><span id="name" ></span></div>
        <div><span style="color: #5D6975;">Email: </span><span id="email" font size="7"></span></div>
        <div><span style="color: #5D6975;">Phone No:</span><span id="phone" font size="7"></span></div>
               <div><span style="color: #5D6975;">Gender: </span><span id="gender" font size="7"></span></div>
        <div><span style="color: #5D6975;">Date:</span><span id="date" font size="7"></span></div>
      </div>

    <main>
             <table id="bootstrap-data-table-export" class="table table-striped table-bordered" >
                                    <thead>
                                       <tr>
                                                     <th>Sl No</th>
                                                           <th>Symptoms</th>  
                                                              <th>Status</th>  
                                                         <th>Status date</th>  
                                                                   <th>Status time</th>  
                                        </tr>
                                    </thead>
                                    <tbody>
                                         <?php
                      require_once 'DB_Connect.php';
                
                      $db = new Db_Connect();
                      $conn = $db->connect();
                      $id=$_GET["id"];
                         $date=date("Y-m-d");
                         $server_ip="139.59.38.160";
                       $users =$conn->query("SELECT s.ID,s.OTP,s.statusdate,s.statustime, u.Name, u.Email,u.Gender,u.PhoneNo,(SELECT Category FROM symptoms WHERE ID=s.symID) AS Category, s.status, s.Date, s.Time FROM `usersymptoms` s INNER JOIN user_details u ON u.ID=s.UserID INNER JOIN symptoms y ON y.ID=s.symID WHERE s.UserID='$id'");      
                      
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;
                    ?>
                    <tr>
                
                          <td><?php echo $count; ?></td>
                           <td><?php echo $user['Category']; ?></td>
                       <td>
                  <?php if( $user['status']==1){
                                              echo "Pending";
                                            }else if( $user['status']==2){
                                              echo "Accepted";
                                            }else if( $user['status']==3){
                                              echo "Rejected";
                                            }; ?>
                                              
                            </td>

                                                                               <td><?php echo $user['statusdate']; ?></td>
                           <td><?php echo $user['statustime']; ?></td>

                    </tr>

                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No data found......</td></tr>
                    <?php endif; ?>

          <tr>
              
          </tr>
                                    </tbody>
                           </table>
                           
    
    </main>
                            
      </div>
      </div>

        <div class="content mt-3">
            <div class="animated fadeIn">


                <div class="row">
          
                                                  

                  
                                              <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Feedback</strong>
                                                    </div>
                                                    <div class="card-body card-block">
                                                      
                                              
                                       

                                                    <div class="card-body card-block">
                                                    
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Status</label></div>
                             <div class="col-8 col-md-8"><select  id="SalonID" name="SalonID"  class="input-sm form-control-sm form-control"  >
                          
                            <option >--Select--</option>
                             <option value="3">--Reject--</option>
                              <option value="2">--Accept--</option>
                


                                                                        </select></div>
                         </div>
                           
                                                            <div class="row input-group">
                                                                    
                              <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Feedback</label></div>
                            <div class="col-8 col-md-8"><textarea type="text" lines=4 id="Details" name="Details"  class="input-sm form-control-sm form-control" required=""></textarea></div>
                           
                            
                                                            </div>
                                                     
                                                          
                                                                    
                                                                      
                                                                    
                                                      </div>



                                               
                                                        <button  class="btn btn-primary btn-sm" onclick="changedata()" >
                                                            <i class="fa fa-dot-circle-o" ></i> Submit
                                                        </button>
                                                        <button type="reset" class="btn btn-danger btn-sm">
                                                            <i class="fa fa-ban"></i> Reset
                                                        </button>
                                                  
                                                    
                                                            </div>
                                                </div>
                                             </div>
                                                          
                                        </div><!-- .animated -->
                                    </div><!-- .content -->
                                </div>
    
 <script type="text/javascript">
function changedata() {

      var id='<?php session_start();echo $_GET["id"]; ?>';

var value= document.getElementById("SalonID").value;

var feedback= document.getElementById("Details").value;

 var id='id='+ id+'&value='+value+'&feedback='+feedback;

 

var request = new XMLHttpRequest();

request.onreadystatechange = function(response) {
  if (request.readyState === 4) {
    if (request.status === 200) {
  
  alert("Success");
    
        
    $( "#dataTable1" ).load(window.location.href + " #dataTable1" );

    } 
}
  }
request.open('POST', 'verifysicknote.php', true);
request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
request.send(id); 

};
 </script>


    
    <script src="vendors/jquery/dist/jquery.min.js"></script>
    <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>
 

    <script src="vendors/chart.js/dist/Chart.bundle.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script src="assets/js/widgets.js"></script>


</body>

</html>
