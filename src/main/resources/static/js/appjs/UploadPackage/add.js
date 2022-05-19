let prefix="/deviceInfo"
$(function() {
    load();
});

function load() {
    $('#deviceTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix , // 服务器数据的加载地址
                // showRefresh : true,
                // showToggle : true,
                // showColumns : true,
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                queryParams : function(params) {
                    let devId=$('#devId').val();
                    let param={
                        size:params.limit,
                        current:params.offset/params.limit+1
                    }
                    if(devId !='' && devId !=null){
                        param.devId=devId
                    }
                    return param;
                },
                responseHandler: function (res) {
                    // 对返回参数进行处理
                    return {
                        "total": res.data.total,
                        "rows": res.data.records
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns : [
                    {
                        checkbox : true
                    },
                    {
                        field : 'id', // 列字段名
                        title : '序号' // 列标题
                    },
                    {
                        field : 'devId',
                        title : '设备编号'
                    },
                    {
                        field : 'branchId',
                        title : '网点编号'
                    },{
                        field : 'branchName',
                        title : '网点编号名'
                    },{
                        field : 'ip',
                        title : '设备ip'
                    },{
                        field : 'visitTime',
                        title : '最后一次访问时间'
                    },{
                        field : 'createTime',
                        title : '注册时间'
                    }
                    ]
            });
}

function reLoad() {
    $('#deviceTable').bootstrapTable('refresh');
}

function upload() {
    var rows = $('#deviceTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    let isAll =$('#isAll').val();
    let file = document.getElementById("file").files[0]; // js 获取文件对象
    let version =$('#version').val();
    if(isAll==0){
        if (rows.length == 0) {
            layer.msg("请选择需要单量更新的设备（可多选）");
            return;
        }
    }
    if(file==null){
        layer.msg("请选择需要上传的文件");
        return;
    }
    layer.confirm("确认要为选中的'" + rows.length + "'个设备上传更新包吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        var devIds = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function(i, row) {
            devIds[i] = row['devId'];
        });
        let formData = new FormData();
        formData.append("isAll",isAll);
        formData.append("file",file);
        formData.append("devIds",devIds);
        formData.append("version",version);
        $.ajax({
            type : 'post',
            dataType: "json",
            data : formData,
            url :  '/uploadPackage',
            processData: false,//不去处理发送的数据
            contentType: false,//不去设置Content-Type请求头
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {

    });
}