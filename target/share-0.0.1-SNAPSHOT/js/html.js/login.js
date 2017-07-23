
function login(){
	var email=document.getElementById("email").value;
	var password=document.getElementById("password").value;
	var remember=document.getElementsByName("remember")[0];
	var resultText=document.getElementById("resultText");
	resultText.innerText="正在登录.请稍等...";
	resultText.style.cssText="color:green";
	$.ajax({
		type:"post",
		url:"/login",
		data:{"email":email,"password":password,"remember":remember.checked},
		success:function(msg){
			var json=eval(msg);
			if(json.resultCode==1){
				resultText.innerText="登录成功,正在跳转到主页...";
				resultText.style.cssText="color: blue;";
				var interval_1=setInterval("skip()",1000);
				
			}else{
				resultText.innerText=json.message+"";
				resultText.style.cssText="color: red;";
			}
		},
		error:function(){
			resultText.innerText="网络连接错误,请重新尝试!";
				resultText.style.cssText="color: red;";
		}
	});
}
function skip(){
	window.location.href="../../index.html";
	clearInterval();
}
