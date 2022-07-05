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
        url : "/yxjr/deviceInfo",
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
            branchId : {
                required : true
            },
            devId : {
                required : true
            }
        },
        messages : {
            branchId : {
                required : icon + "请输入网点编号"
            },
            devId : {
                required : icon + "请输入设备编号"
            }
        }
    })
}