function validateForm() {
    var x = document.forms["myForm"]["t1"].value;
    var y=document.forms["myForm"]["t2"].value;
    
    if (x==null || x=="") {
        alert("Required Field Must Enter Email id ");
        return false;
    }
    
    if (y==null || y=="") {
        alert("Required Field Must Enter  PASSWORD");
        return false;
    }
}


function checkEmail() {

    var email = document.getElementById('txtEmail');
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    if (!filter.test(email.value)) {
    alert('Please provide a valid email address');
    email.focus;
    return false;
 }
}

function isEmpty(){  
     var data=document.getElementById("name").value;  
     if(data.length==0){  
       alert("Name is required");  
       return false;  
     }  
     return true;  
  }