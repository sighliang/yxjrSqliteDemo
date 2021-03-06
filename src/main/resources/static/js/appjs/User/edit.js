$().ready(function() {
    validateRule();
});

$.validator.setDefaults({
    submitHandler : function() {
        update();
    }
});
function update() {
    $.ajax({
        cache : true,
        type : "PUT",
        url : "/yxjr/user",
        data : $('#form-info-edit').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }

        }
    });

}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#form-info-edit").validate({
        rules : {
            userName : {
                required : true
            },
            password : {
                required : true
            }
        },
        messages : {
            userName : {
                required : icon + "请输入账号"
            },
            password : {
                required : icon + "请输入密码"
            }
        }
    })
}