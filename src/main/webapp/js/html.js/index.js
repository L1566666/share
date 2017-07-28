//全局变量
var type = 0;	//存储当前分类,可动态修改	0代表主页,1代表软件,2代表视频,3代表随心分享
var pageNo = 1; //存储页码;
var search_str = "";	//搜索关键字
var totalPage = 0;	//总页数
$.ajax({
	type: "post",
	url: "isLogin",
	success: function(msg) {
		var resultCode = msg.resultCode;
		if(resultCode == 1) {
			loggedIn();
			console.log("已登录!");
		} else {
			noLogged();
			console.log("未登录!");
		}
	},
	error: function() {
		noLogged();
		console.log("网络连接异常");
	}
});
//已登录时调用此函数
function loggedIn() {
	var login_label = document.getElementById("login");
	var register_label = document.getElementById("register");
	var personal_label = document.getElementById("personal-info");
	var personal_info_email = document.getElementById("personal-info-email");
	var left_static = document.getElementById("left-static");
	login_label.style.cssText = "display: none;";
	register_label.style.cssText = "display: none;";
	personal_label.style.cssText = "display: ;";
	personal_info_email.innerText = getCookie1("email");
	left_static.style.cssText = "display: ;"

}
//未登录时调用此方法
function noLogged() {
	var login_label = document.getElementById("login");
	var register_label = document.getElementById("register");
	var personal_label = document.getElementById("personal-info");
	var personal_info_email = document.getElementById("personal-info-email");
	var left_static = document.getElementById("left-static");
	login_label.style.cssText = "display: ;";
	register_label.style.cssText = "display: ;";
	personal_label.style.cssText = "display: none;";
//	left_static.style.cssText = "display: none;"
}
//登出
function login_out() {
	$.ajax({
		type: "post",
		url: "loginOut",
		success: function(msg) {
			if(msg.resultCode == 1) {
				window.location.reload();
			} else {
				console.log("退出登录失败!");
			}
		},
		error: function() {

		}
	});

}

function setCookie(c_name, value, expiredays) {
	var exdate = new Date()
	exdate.setDate(exdate.getDate() + expiredays)
	document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())

}

function getCookie(c_name) {
	if(document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if(c_end == -1) c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}
/**
 *2017年7月13日08:54:13
 * lin
 * 为了去除tomcat存储cookie带有双引号
 */
function getCookie1(c_name) {
	if(document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if(c_end == -1) c_end = document.cookie.length;
			var c_value = document.cookie.substring(c_start, c_end);
			if("\"" === c_value.substring(0, 1) && "\"" === c_value.substring(c_value.length - 1, c_value.length)) {
				c_value = c_value.substring(1, c_value.length - 1);
			}
			return c_value;
		}
	}
	return "";
}

$().ready(function() {
	//静态区js代码
	$(".left-static-text").mouseover(function() {
		$(this).css("color", "blue");
	}).mouseout(function() {
		$(this).css("color", "black");
	});

	//保存分享按钮
	$("#shareSave").click(function() {
//		var share_subject_value = $("input[name=share_subject]").val(); //获取分享主题  暂时不使用
		var share_describe_value = $("input[name=share_describe]").val(); //获取分享描述
		var share_url_value = $("textarea[name=share_url]").val(); //获取分享链接内容
		var share_type_value = $("select[name=share_type]").val(); //获取分享分类类型
		//验证是否为链接
		var reg = /((http|ftp|https):\/\/)?[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;
		var result = reg.test(share_url_value);
		if(result == false) {
			alert("分享链接输入有错误,不是正确的分享链接!");
			return;
		}
		$.ajax({
			type: "post",
			url: "share/save",
			data: {
				"share_describe": share_describe_value,
				"share_url": share_url_value,
				"share_type": share_type_value
			},
			success: function(msg) {
				if(msg === null || ""===msg){
					alert("请登录后提交分享!");
					return ;
				}
				var json = eval(msg);
				if(json.resultCode === 1) {
					alert(json.message);
					console.log("保存成功!");
					window.location.reload();
				} else {
					alert(json.message);
					console.log("保存失败!请重试");
				}
			},
			error: function() {
				alert("失败");
				console.log("网络连接失败!");
			}
		});
	});
	//搜索按钮点击
	$("#search").click(function(){
		search_str = $("input[name=search_str]").val();
//		alert(search_str);
		getShareList();
	});
	//主页按钮被点击时
	$("#index-button").click(function(){
		$(this).attr("class","active");
		$("#software-button").attr("class","");
		$("#video-button").attr("class","");
		$("#casual-button").attr("class","");
		//设置select默认分类
		$("#type-select option[value=1]").removeAttr("selected");
		$("#type-select option[value=2]").removeAttr("selected");
		$("#type-select option[value=3]").removeAttr("selected");

		type = 0;
		pageNo = 1;
		getShareList();
	});
	//随心分享按钮被点击时
	$("#casual-button").click(function(){
		$(this).attr("class","active");
		$("#index-button").attr("class","");
		$("#software-button").attr("class","");
		$("#video-button").attr("class","");
		
		//设置select默认分类
		$("#type-select option[value=1]").attr("selected","true");
		$("#type-select option[value=2]").removeAttr("selected");
		$("#type-select option[value=3]").removeAttr("selected");
		
		type = 1;
		pageNo = 1;
		getShareList();
	});
	//软件按钮被点击时
	$("#software-button").click(function(){
		$(this).attr("class","active");
		$("#index-button").attr("class","");
		$("#video-button").attr("class","");
		$("#casual-button").attr("class","");
		//设置select默认分类
		$("#type-select option[value=2]").attr("selected","true");
		$("#type-select option[value=1]").removeAttr("selected");
		$("#type-select option[value=3]").removeAttr("selected");
		type = 2;
		pageNo = 1;
		getShareList();
	});
	//视频按钮被点击时
	$("#video-button").click(function(){
		$(this).attr("class","active");
		$("#index-button").attr("class","");
		$("#software-button").attr("class","");
		$("#casual-button").attr("class","");
		//设置select默认分类
		$("#type-select option[value=1]").removeAttr("selected");
		$("#type-select option[value=3]").attr("selected","true");
		$("#type-select option[value=2]").removeAttr("selected");
		type = 3;
		pageNo = 1;
		getShareList();
	});
	
	
	//上一页被点击
	$("#previous-page").click(function(){
		if(pageNo > 1){
			pageNo=parseInt(pageNo)-1;
			getShareList();	
		}else{
			$("#previous-page a").attr("href","JavaScript:return false;");
		}
	});
	$("#next-page").click(function(){
		if(pageNo < totalPage){
			pageNo=parseInt(pageNo)+1;
			getShareList();
		}else{
			$("#next-page a").attr("href","JavaScript:return false;");
		}
		
	});
	$("#jump-page").click(function(){
		var page_no = $("#page-no").val();
		if(page_no>=1 && page_no<=totalPage){
			$("#jump-page").attr("href","#");
			pageNo=$("#page-no").val();
			getShareList();
		}else{
			$("#jump-page").attr("href","JavaScript:return false;");
		}
		
	});
	
	//点赞按钮
	$(".support").click(function(){
		alert("bbb");
	});

});

//angular设定数据
var app = angular.module("my-app", []);
app.controller("my-controller", function($scope, $http) {
	
	$http({
		url: "share/list?share_type="+type+"&page_no="+pageNo+"&search_str=",
		mathod: "get",
		}
		).success(function(msg){
			$scope.shareList=msg.shareList;
			$scope.totalPage=msg.totalPage;
			$scope.pageNo = pageNo;
			totalPage = msg.totalPage;
	
		}).error(function(){
			alert("请求失败");
		})
	
});
//自定义 angular拦截器
app.filter('shareTypeFilter', function() { //可以注入依赖
    return function(type) {
        if(type===1){
        	return "随心分享";
        }else if(type===2){
        	return "软件";
        }else if(type === 3){
        	return "视频";
        }else{
        	return "未知分类";
        }
    }
});
//获取sharelist函数
function getShareList(){
	$.ajax({
		type:"get",
		url:"share/list?share_type="+type+"&page_no="+pageNo+"&search_str="+search_str,
		success:function(msg){
			var data = eval(msg);
			var shareList = data.shareList;
			
			var appElement = document.querySelector('[ng-controller=my-controller]');
			var $scope = angular.element(appElement).scope();
			$scope.shareList = shareList;
			totalPage = data.totalPage;

			$scope.$apply(function(){
				$scope.shareList=shareList;	
				$scope.totalPage=data.totalPage;
				$scope.pageNo = pageNo;

			});

		},
		error:function(){
			alert("请求失败!");
		}
	});
}
//点赞功能
function support(id){
	var share_id=id.substring(8);
	$.ajax({
		type:"get",
		url:"/supportOrStep/supportOrStep?support_or_step=1&share_id="+share_id,
		success:function(msg){
			if(msg == null || msg == ""){
				alert("未登录");
				return ;
			}
			if(msg.resultCode == 0){
				alert(msg.message);
			}else if(msg.resultCode == 1){
				var support_amount = document.getElementById("supportAmount_"+share_id);

				support_amount.innerText = parseInt(support_amount.innerText)+1;
			}
		},
		error:function(){
			alert("网络连接异常!");
		}
	});
}
function supportMove(id){
	var support = document.getElementById(id);
	support.style.cssText="color: #262626;cursor: pointer;";
}
function supportOut(id){
	var support = document.getElementById(id);
	support.style.cssText="cursor: pointer;";
}
//踩功能
function step(id){
	var share_id=id.substring(5);
	$.ajax({
		type:"get",
		url:"/supportOrStep/supportOrStep?support_or_step=2&share_id="+share_id,
		success:function(msg){
			if(msg == null || msg == ""){
				alert("未登录");
				return ;
			}
			if(msg.resultCode == 0){
				alert(msg.message);
			}else if(msg.resultCode == 1){
				var step_amount = document.getElementById("stepAmount_"+share_id);
				var value = step_amount.innerText;
				
				step_amount.innerText = parseInt(step_amount.innerText)+1;
			}
		},
		error:function(){
			alert("网络连接异常!");
		}
	});
}
function stepMove(id){
	var step = document.getElementById(id);
	step.style.cssText="color: #262626;cursor: pointer;";

}
function stepOut(id){
	var step = document.getElementById(id);
	step.style.cssText="cursor: pointer;";
}
