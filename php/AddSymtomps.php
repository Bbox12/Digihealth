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
    <title>Add symptoms</title>
    <meta name="description" content="livestock">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">


    <link rel="stylesheet" href="vendors/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="vendors/themify-icons/css/themify-icons.css">
    <link rel="stylesheet" href="vendors/flag-icon-css/css/flag-icon.min.css">
    <link rel="stylesheet" href="vendors/selectFX/css/cs-skin-elastic.css">
  
    <link rel="stylesheet" href="main.css">
    <link rel="stylesheet" href="assets/css/style.css">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800' rel='stylesheet' type='text/css'>

 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>


    <link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
    <style>
      #locationField, #controls {
        position: relative;
        width: 200px;
      }
      #autocomplete {
        position: absolute;
        top: 0px;
        left: 0px;
        width: 99%;
      }
      .label {
        text-align: right;
        font-weight: bold;
        width: 100px;
        color: #303030;
        font-family: "Roboto";
      }
      #address {
        border: 1px solid #000090;
        background-color: #f0f9ff;
        width: 480px;
        padding-right: 2px;
      }
      #address td {
        font-size: 10pt;
      }
      .field {
        width: 99%;
      }
      .slimField {
        width: 80px;
      }
      .wideField {
        width: 200px;
      }
      #locationField {
        height: 20px;
        margin-bottom: 2px;
      }
    </style>

<script type="text/javascript">
function myFunction() {
  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
    var error = '<?php session_start();echo $_SESSION["error"] ?>';
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Corona/Dashboard/page-login.php");
  }else{
     if(error!=''){
      if(error==1){
alert("Successfully stored information.");
  }else{
    if(error==2){
alert("Error in Storing information");
  }else{
    alert("Please check the information");
  }
  }
}



};
}

</script>
</head>

<body onload="myFunction()">
<?php session_start();
       $_SESSION["error"]='';?>

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
                          <li><i class="menu-icon fa fa-pencil-square-o"></i><a href="AddRequests.php">Add mandatory request list</a></li>
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
                    <div class="user-area dropdown float-right">
                           <a href="page-login.php" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php  session_start(); echo $_SESSION["email"]."|".$_SESSION["name"];?></a>
                        </a>
                    </div>
                </div>
          </div>

        </header>




        <div class="breadcrumbs">
            <div class="col-sm-4">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1>Dashboard</h1>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="page-header float-right">
                    <div class="page-title">
                        <ol class="breadcrumb text-right">
                            <li><a href="index.php">Dashboard</a></li>
                            <li><a href="#">Questions</a></li>
                            <li class="active">Add symptoms</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>


                <div class="content mt-3">
            <div class="animated fadeIn">


                <div class="row">
          
                                                  

                  
                                              <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Symptoms</strong>
                                                    </div>
                                                    <div class="card-body card-block">
                                                          <form action="addsymptoms.php" method="post" enctype="multipart/form-data" target="_self"  class="form-horizontal">
                                                            
                                              
                                       

                                                    <div class="card-body card-block">

                                                           <div class="row form-group">
                             <div class="col col-md-3"><label for="text-input" class=" form-control-label">Symptoms</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="symptoms" name="symptoms" placeholder="Enter the name  (sick note, medical script, covid tests, x rays etc)" class="input-sm form-control-sm form-control"  required></div></div>                                          
                  
                                                            
                                      
               <div class="row form-group">
                             <div class="col col-md-3"><label for="text-input" class=" form-control-label">Details</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="details" name="details" placeholder="Enter the details" class="input-sm form-control-sm form-control"  required></div></div>
                         
                   
                          
                      
                                              
                                                          </div>
                                                     

                                               
                                                        <button type="submit" class="btn btn-primary btn-sm">
                                                            <i class="fa fa-dot-circle-o"></i> Submit
                                                        </button>
                                                        <button type="reset" class="btn btn-danger btn-sm">
                                                            <i class="fa fa-ban"></i> Reset
                                                        </button>
                                                  
                                                          </form>
                                                            </div>
                                                </div>
                                             </div>
                                           </div>
                           
                                      
                                    </div><!-- .content -->
                                </div><!-- /#right-panel -->
                                <!-- Right Panel -->


                                                  <div class="content mt-3">
            <div class="animated fadeIn">


                <div class="row">
                                                            <div class="col-lg-6" >
                        <div class="card">
                                               
                            <div class="card-header">
                                <strong class="card-title">Existing Primary Category</strong>
                            </div>
                              
                                             
                                                     
                                                    <div class="card-body card-block">
                            
                                <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Sl/No</th>
                                                <th>Requests</th>
                                                                  
                                                 <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                                  <?php
                      require_once 'DB_Connect.php';
              
                      $db = new Db_Connect();
                      $conn = $db->connect();
                        $date=date("Y-m-d");
                         $server_ip="139.59.38.160";
                       $users =$conn->query("SELECT `ID`, `Category`, `Photo`, `Description`, `isActive`, `Date`, `Time`, `User` FROM `symptoms` WHERE `isActive`=1");
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;
                    ?>
                    <tr>
                        <td><?php echo $count; ?></td>
           <td><?php echo $user['Category']; ?></td>
                       <td><?php echo $user['Description']; ?></td>     

                          
                        <td><a href="DeleteSymptoms.php?id=<?php echo $user['ID']; ?>" style="color: red;"><?php echo "DEL"; ?></td>
              
                    </tr>
                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No user(s) found......</td></tr>
                    <?php endif; ?>
                                    </tbody>
                                </table>                              
                                                
                        </div>
               

</div>
</div>
                        <div class="col-lg-6" >
                      <div class="card">
                                               
                            <div class="card-header">
                                <strong class="card-title">Not Active Primary Category</strong>
                            </div>
                              
                                             
                                                     
                                                    <div class="card-body card-block">
                            
                                <table id="bootstrap-data-table-export" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Sl/No</th>
                                                <th>Name</th>
                                                       
                                                 <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                                  <?php
                      require_once 'DB_Connect.php';
              
                      $db = new Db_Connect();
                      $conn = $db->connect();
                        $date=date("Y-m-d");
                         $server_ip="139.59.38.160";
                         $users =$conn->query("SELECT `ID`, `Category`, `Photo`, `Description`, `isActive`, `Date`, `Time`, `User` FROM `symptoms` WHERE `isActive`=0");
                        if(!empty($users)): $count = 0; foreach($users as $user): $count++;
                    ?>
                    <tr>
                        <td><?php echo $count; ?></td>
                        <td><?php echo $user['Category']; ?></td>
                      

                        <td><a href="ActiveSymptoms.php?id=<?php echo $user['ID']; ?>" style="color: green;"><?php echo "Activate"; ?></td>
              
                    </tr>
                    <?php endforeach; else: ?>
                    <tr><td colspan="5">No user(s) found......</td></tr>
                    <?php endif; ?>
                                    </tbody>
                                </table>                              
                                                
                        </div>
                    </div>

</div>
                                      
                                    </div><!-- .content -->
                                </div><!-- /#right-panel -->
                               </div>
  </div><!-- /#right-panel -->
                               </div>

          
                            <script src="vendors/jquery/dist/jquery.min.js"></script>
                            <script src="vendors/popper.js/dist/umd/popper.min.js"></script>
                            <script src="vendors/jquery-validation/dist/jquery.validate.min.js"></script>
                            <script src="vendors/jquery-validation-unobtrusive/dist/jquery.validate.unobtrusive.min.js"></script>
                            <script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>
                            <script src="assets/js/main.js"></script>
                            <script src="main.js"></script>
</body>
</html>
