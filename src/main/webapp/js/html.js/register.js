var emailResult=0;
var passwordResult=0;
var repasswordResult=0;

function emailValidate(){
	var email=document.getElementById("email").value;
	var isEmail=/^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/;
	var emailError=document.getElementById("email_error");
	if(!isEmail.test(email)){
		emailError.innerText="邮箱格式不正确!";
		emailError.style.cssText="color: red;"
		document.getElementById("email_frame").className="form-group has-error";
		emailResult=0;
	}else{
		emailError.innerText="正确!";
		emailError.style.cssText="color: green;"
		document.getElementById("email_frame").className="form-group has-success";
		emailResult=1;
	}
}
function passwordValidate(){
	var password=document.getElementById("password").value;
	var passwordError=document.getElementById("password_error");
	passwordError.innerText=password;
	var passwordLenth=password.length;
	if(passwordLenth<6||passwordLenth>16){
		passwordError.innerText="密码长度不能小于6位且不能大于16位!";
		passwordError.style.cssText="color: red;"
		document.getElementById("password_frame").className="form-group has-error";
		passwordResult=0;
	}else{
		passwordError.innerText="正确!";
		passwordError.style.cssText="color: green;"
		document.getElementById("password_frame").className="form-group has-success";
		passwordResult=1;
	}
}
function repasswordValidate(){
	var password = document.getElementById("password").value;
	var repassword=document.getElementById("repassword").value;
	var repasswordeError=document.getElementById("repassword_error");
	if(repassword!=password){
		repasswordeError.innerText="两次输入密码必须一致!,且密码长度不能小于6位且不能大于16位!";
		repasswordeError.style.cssText="color:red";
		document.getElementById("repassword_frame").className="form-group has-error";
		repasswordResult=0;
	}else if(repassword.length>=6&&repassword.length<=16){
		repasswordeError.innerText="正确!";
		repasswordeError.style.cssText="color:green";
		document.getElementById("repassword_frame").className="form-group has-success";
		repasswordResult=1;
	}
	
}
function register(){
	if(emailResult==1&&passwordResult==1&&repasswordResult==1){
		var email=document.getElementById("email").value;
		var password = document.getElementById("password").value;
		var resultAlert = document.getElementById("resultAlert");
        var resultText = document.getElementById("resultText");
		 $.ajax({
            type:'post',
            url:"/register",
            data:{"email" : email,"password" : password},
            success:function(msg){
                var json = eval(msg);
                if(json.resultCode==1){
                	resultAlert.className="alert-success";
                	resultText.innerText=json.message+"正在跳转...";
                	resultAlert.cssText="display:;";
                	setInterval("skip()", 3000);
                }else{
                	resultAlert.style.cssText="display:;";
                	resultAlert.className="alert-warning";
                	resultText.innerText="注册失败:";
                	resultText.innerText=json.message;
                }
            },
            error:function(){
            	resultAlert.className="alert-warning";
            	resultAlert.style.cssText="display:;";
            	resultText.innerText="网络连接错误!请重新尝试";
            }
        });
	}else{
		alert("填写的信息不正确!请重新填写.");
	}
}
function skip() { 
	window.location.href = "login.html"; 
	clearInterval(intervalid);
}
