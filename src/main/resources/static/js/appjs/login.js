$(function() {
    $("#imgVerify").click();
});

function keyLogin(){
    if (event.keyCode===13)  //回车键的键值为13
        document.getElementById("loginBtn").click();
}

//获取验证码
function getVerify(obj) {
    obj.src = "/yxjr/getCode?" + Math.random();
    $("#verify").val("");
}

function reLoad() {
    $('#deviceTable').bootstrapTable('refresh');
}

function login() {
    let userName=$("#userName").val();
    let password=$("#password").val();
    let verify=$("#verify").val();
    if(userName==null || userName==""){
        parent.layer.msg("请输入用户名");
        return;
    }
    if(password==null || password==""){
        parent.layer.msg("请输入密码");
        return;
    }
    $.ajax({
        cache : true,
        type : "POST",
        url : "/yxjr/login",
        data : {
            "userName":userName,
            "password":password,
            "verify":verify
        },
        async : false,
        error : function(request) {
            parent.layer.alert("连接异常");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("登录成功");
                parent.location.href = 'index';
            } else {
                layer.msg(data.msg);
                $("#imgVerify").click();
                return;
            }

        }
    });

}