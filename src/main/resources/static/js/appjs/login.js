
function keyLogin(){
    if (event.keyCode===13)  //回车键的键值为13
        document.getElementById("loginBtn").click();
}

function login() {
    let userName=$("#userName").val();
    let password=$("#password").val();
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
        url : "/login",
        data : {
            "userName":userName,
            "password":password
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
                alert(data.msg);
                return;
            }

        }
    });

}