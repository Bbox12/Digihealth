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
    <title>Add General info</title>
    <meta name="description" content="Covid-19">
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
    document.getElementById('first').style.display = "block";
       document.getElementById('second').style.display = "none";
          document.getElementById('third').style.display = "none";
             document.getElementById('fourth').style.display = "none";
                document.getElementById('fifth').style.display = "none";

     document.getElementById('ID1').readOnly = "true";
          document.getElementById('ID2').readOnly = "true";
             document.getElementById('ID3').readOnly = "true";
                document.getElementById('ID4').readOnly = "true";
                document.getElementById('ID5').readOnly = "true";


  var myVarFromPhp = '<?php session_start();echo $_SESSION["email"] ?>';
    var error = '<?php session_start();echo $_SESSION["error"] ?>';
  if(myVarFromPhp==''){
window.location.replace("http://139.59.38.160/Corona/Dashboard/page-login.php");
  }else{
     if(error!=''){
      if(error==1){
alert("Suucessfully stored information.");
  }else{
    if(error==2){
alert("Error in Storing information");
  }else{
    alert("Please check the information");
  }
  }
}

 var xmlhttp;
    if (window.XMLHttpRequest) {
    xmlhttp=new XMLHttpRequest();
  } else { 
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 xmlhttp.onreadystatechange=function() {
 
    if (this.readyState==4 && this.status==200) {
          
         var arr = JSON.parse(this.responseText);
          //alert(this.responseText);

             if(arr.length>0){
                  document.getElementById('second').style.display = "block";
     
                       var i=0;
  document.getElementById('firsttitle').value=arr[i].Category;
                                  document.getElementById('firstdescription').value=arr[i].Question;
                        
             }
              if(arr.length>1){
                       document.getElementById('third').style.display = "block";
                       var i=1;
  document.getElementById('secondtitle').value=arr[i].Category;
                                  document.getElementById('seconddescription').value=arr[i].Question;
                    
             }
             if(arr.length>2){
                     document.getElementById('fourth').style.display = "block";
                       var i=2;
  document.getElementById('thirdtitle').value=arr[i].Category;
                                  document.getElementById('thirddescription').value=arr[i].Question;
                          
             }
              if(arr.length>3){
                       document.getElementById('fifth').style.display = "block";
                       var i=3;
  document.getElementById('fourthtitle').value=arr[i].Category;
                                  document.getElementById('fourthdescription').value=arr[i].Question;
                       
             }
                 if(arr.length>4){
                         //document.getElementById('fifth').style.display = "block";
                       var i=4;
  document.getElementById('fifthttitle').value=arr[i].Category;
                                  document.getElementById('fifthdescription').value=arr[i].Question;
                             
             }
       
  }
}
 xmlhttp.open("POST","getProductsToEdit.php",true);
 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
 xmlhttp.send("id");

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
                            <li class="active">Add General Info</li>
                        </ol>
                    </div>
                </div>
            </div>
          </div>
 

        <div class="content mt-3" id="first">
            <div class="animated fadeIn">
                <div class="row">
          
                                                  

                  
                                              <div class="col-lg-12" >
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>First General Info</strong>
                                                    </div>
                                                    <div class="card-body card-block">
                                                        <form action="addQuestions.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                
                                            <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID1" name="ID"  class="input-sm form-control-sm form-control"  value="1" ></div>
                          
                         </div>
                                                    
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="firsttitle" name="title" placeholder="Enter the Title" class="input-sm form-control-sm form-control"  required></div>
                          
                         
                         </div>
                      <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Description</label></div>
                             <div class="col-9 col-md-9"><textarea  lines="4" type="text" id="firstdescription" name="description" placeholder="Enter the Question" class="input-sm form-control-sm form-control"  required></textarea></div>
                          
                         
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

                                                </div>

                                             </div>

                    <div class="content mt-3" id="second">
            <div class="animated fadeIn">
                <div class="row">
                                              <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Second General Info</strong>
                                                    </div>
                                                                 <div class="card-body card-block">
                                                        <form action="addQuestions.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
             
                                                                           <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID2" name="ID"  class="input-sm form-control-sm form-control"  value="2" ></div>
                          
                         </div>
                                                             
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="secondtitle" name="title" placeholder="Enter the Title" class="input-sm form-control-sm form-control"  required></div>
                          
                         
                         </div>
                      <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Description</label></div>
                             <div class="col-9 col-md-9"><textarea  lines="4" type="text" id="seconddescription" name="description" placeholder="Enter the Question" class="input-sm form-control-sm form-control"  required></textarea></div>
                          
                         
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

                                                </div>

                                             </div>

                        <div class="content mt-3" id="third">
            <div class="animated fadeIn">
                <div class="row">
                  <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Third General Info</strong>
                                                    </div>
                                                                              <div class="card-body card-block">
                                                        <form action="addQuestions.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                
                                                       

                                                                           <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID3" name="ID"  class="input-sm form-control-sm form-control"  value="3" ></div>
                          
                         </div>
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="thirdtitle" name="title" placeholder="Enter the Title" class="input-sm form-control-sm form-control"  required></div>
                          
                         
                         </div>
                      <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Description</label></div>
                             <div class="col-9 col-md-9"><textarea  lines="4" type="text" id="thirddescription" name="description" placeholder="Enter the Question" class="input-sm form-control-sm form-control"  required></textarea></div>
                          
                         
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

                                                </div>

                                             </div>

                          <div class="content mt-3" id="fourth">
            <div class="animated fadeIn">
                <div class="row">
                                                               <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Fourth General Info</strong>
                                                    </div>
                                                                    <div class="card-body card-block">
                                                        <form action="addQuestions.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                
                                                                       
                                                                           <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID4" name="ID"  class="input-sm form-control-sm form-control"  value="4" ></div>
                          
                         </div>       
                                                             
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="fourthtitle" name="title" placeholder="Enter the Title" class="input-sm form-control-sm form-control"  required></div>
                          
                         
                         </div>
                      <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Description</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="fourthdescription" name="description" placeholder="Enter the Question" class="input-sm form-control-sm form-control"  required></div>
                          
                         
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

                                                </div>

                                             </div>

                   <div class="content mt-3" id="fifth">
            <div class="animated fadeIn">
                <div class="row">
                                                               <div class="col-lg-12">
                                                 <div class="card">
                                                    <div class="card-header">
                                                        <strong>Fifth General Info</strong>
                                                    </div>
                                                                 <div class="card-body card-block">
                                                        <form action="addQuestions.php" method="post" enctype="multipart/form-data" target="_self" class="form-horizontal">
                                
                                                    
                                                                           <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">ID</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="ID5" name="ID"  class="input-sm form-control-sm form-control"  value="5" ></div>
                          
                         </div>
                                                                 
                                                 <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Title</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="fifthttitle" name="title" placeholder="Enter the Title" class="input-sm form-control-sm form-control"  required></div>
                          
                         
                         </div>
                      <div class="row input-group">
                                                                    
                             <div class="col col-md-3"><label for="text-input" class=" input-sm form-control-sm form-control-label">Description</label></div>
                             <div class="col-9 col-md-9"><input type="text" id="fifthdescription" name="description" placeholder="Enter the Question" class="input-sm form-control-sm form-control"  required></div>
                          
                         
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

                                                </div>
                                           
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
