<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>DigiDocs Dashboard</title>
    <meta name="description" content="Corona">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="vendors/jqvmap/dist/jqvmap.min.css">


    <link rel="stylesheet" href="assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="https://cdn.anychart.com/js/latest/anychart.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <style type="text/css">
        #graph {
  width: 100%;
  height: 128px;
}
    </style>
 <script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
  
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Corona/Dashboard/page-login.php");
  }
};
</script>
</head>

<body onload="myFunction()">


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
                        <button class="search-trigger"><i class="fa fa-search"></i></button>
                        <div class="form-inline">
                            <form class="search-form">
                                <input class="form-control mr-sm-2" type="text" placeholder="Search ..." aria-label="Search">
                                <button class="search-close" type="submit"><i class="fa fa-close"></i></button>
                            </form>
                        </div>
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
                    <div class="user-area dropdown float-right">
                           <a href="page-login.php" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php  session_start(); echo $_SESSION["email"]."|".$_SESSION["name"];?></a>
                        </a>
                    </div>
                </div>
          </div>

        </header>


                       <div class="content mt-3" id="dataTable1">
            <div class="animated fadeIn">
                <div class="row">

                    <div class="col-md-12">
                        <div class="card" >
                         <div class="card-header">
                                <strong class="card-title">Products</strong>
                            </div>
                            <div class="card-body" >
                                <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                                     <th>Sl No</th>
                                                       <th>ID</th>
                                            <th>Name</th>
                                      
                                               <th>Phone No</th>
                                             <th>Address</th>
                                               <th>C290</th>
                                                 <th>Sample Type</th>
                                             <th>Indication</th>
                                               <th>Travel History</th>
                            
                                            <th>Symtoms</th>
                                                 <th>Status</th>
                                                     <th>Date</th>
        <th>Time</th>
             <th>Edit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                               <?php
                      require_once 'DB_Connect.php';
                  
                      $db = new Db_Connect();
                      $conn = $db->connect();
                        $date=date("Y-m-d");
                         $server_ip="139.59.38.160";

                     
                               $users =$conn->query("SELECT `ID`, OTP,(SELECT Name FROM user_details WHERE ID=c.UserID) AS `Name`,(SELECT PhoneNo FROM user_details WHERE ID=c.UserID) AS `mobile`, `Address`, `Sars`, `Sampletype`, `Indication`, `Country1`, `Ddate1`, `Rdate1`, `Country2`, `Ddate2`, `Rdate2`, `Status`, `Date`, `Time`, (SELECT COUNT(ID) FROM usersymptoms WHERE UserID=c.UserID AND Status=2) AS `Symtoms` FROM `covidtest` c");
                      
                  
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;
                    ?>
                    <tr>
                
                         <td><?php echo $count; ?></td>
                       <td><?php echo $user['OTP']; ?></td>
                    
                        <td><?php echo $user['Name']; ?></td>
                            <td><?php echo $user['mobile']; ?></td>
                          <td><?php echo $user['Address']; ?></td>
                                     <td><?php if( $user['Sars']==1){
                                              echo "Yes";
                                            }else {
                                              echo "No";
                                            }; ?></td>
                                     <td><?php echo $user['Sampletype']; ?></td>
                          <td><?php echo $user['Indication']; ?></td>
                          <td><?php if( $user['Country1']==''){
                                              echo "NO";
                                            }else {
                                              echo "YES";
                                            }; ?></td>
                                               <td><?php echo $user['Symtoms']; ?></td>
                                             <td><?php if( $user['Status']==1){
                                              echo "Pending";
                                            }else if( $user['Status']==2){
                                              echo "Accept";
                                            }else if( $user['Status']==3){
                                              echo "Reject";
                                            }; ?></td>
                  <td><?php echo $user['Date']; ?></td>
                          <td><?php echo $user['Time']; ?></td>
                          <td><a href="http://139.59.38.160/comingsoon/index.html" style="display:block;color: blue;"><u>Edit</u></a></td>
                    </tr>
                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No user(s) found......</td></tr>
                    <?php endif; ?>
                                    </tbody>
                                </table>
                            </div>
                       
                        </div>
                    </div>


                </div>
            </div><!-- .animated -->
        </div>

       
    
 <script type="text/javascript">
function changedata(id,value) {



 var id='id='+ id+'&value='+value;

 

var request = new XMLHttpRequest();

request.onreadystatechange = function(response) {
  if (request.readyState === 4) {
    if (request.status === 200) {
  
  alert("Success");
    
        
    $( "#dataTable1" ).load(window.location.href + " #dataTable1" );

    } 
}
  }
request.open('POST', 'verifyadmin.php', true);
request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
request.send(id); 

};
 </script>


    



     <script src="vendors/jquery/dist/jquery.min.js"></script>
    <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
    <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/jquery.morphbutton.js"></script>

    <script src="vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="vendors/datatables.net-bs4/js/dataTables.bootstrap4.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="vendors/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js"></script>
      <script src="vendors/jszip/dist/jszip.min.js"></script>
    <script src="vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="vendors/datatables.net-buttons/js/buttons.colVis.min.js"></script>
    <script src="assets/js/init-scripts/data-table/datatables-init.js"></script>
    <script src="vendors/chart.js/dist/Chart.bundle.min.js"></script>
    <script src="assets/js/dashboard.js"></script>
    <script src="assets/js/widgets.js"></script>
    <script src="vendors/jqvmap/dist/jquery.vmap.min.js"></script>
    <script src="vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <script src="vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
     <script src="vendors/chart.js/dist/Chart.bundle.min.js"></script>


</body>

</html>
