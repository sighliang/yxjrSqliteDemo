let prefix = "/yxjr/deviceInfo"
$(function () {
    load();
});

function load() {
    $('#deviceTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix, // 服务器数据的加载地址
                // showRefresh : true,
                // showToggle : true,
                // showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: true, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                // search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                queryParams: function (params) {
                    let devId = $('#devId').val();
                    let param = {
                        size: params.limit,
                        current: params.offset / params.limit + 1
                    }
                    if (devId != '' && devId != null) {
                        param.devId = devId
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
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'id', // 列字段名
                        title: '序号' // 列标题
                    },
                    {
                        field: 'devId',
                        title: '设备编号'
                    },
                    {
                        field: 'branchId',
                        title: '网点编号'
                    }, {
                        field: 'branchName',
                        title: '网点编号名'
                    }, {
                        field: 'ip',
                        title: '设备ip'
                    }, {
                        field: 'visitTime',
                        title: '最后一次访问时间'
                    }, {
                        field: 'createTime',
                        title: '注册时间'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            let d = '<a class="btn btn-primary btn-sm " href="#" title="提取所有日志"  mce_href="#" onclick="getAllLog(\'' + row.devId + '\',' + row.ip + ')"><i class="fa fa-upload"></i></a> ';
                            return  d ;
                        }
                    }
                ]
            });
}

function reLoad() {
    $('#deviceTable').bootstrapTable('refresh');
}

function upload() {
    let rows = $('#deviceTable').bootstrapTable('getSelections'); // 返回所选择的行，当没有选择的记录时，返回一个空数组
    let datetime = $('#datetime').val();
    if (rows == "") {
        layer.msg("请选择需要提取的设备");
        return;
    }
    if (datetime == null || datetime == "") {
        layer.msg("请选择需要提取的时间");
        return;
    }
    let row = rows[0];
    layer.confirm("确认要提取的设备号为‘" + row.devId + "'的日志吗吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        let index_wait = "";
        $.ajax({
            type: 'post',
            data: {
                "devId":row.devId,
                "ip":row.ip,
                "datetime":datetime
            },
            url: '/yxjr/logPackage/getLog',
            beforeSend: function(){
                index_wait = layer.load(0, {  //发送请求前调用load方法
                    shade: [0.5, '#fff'],  //0.5透明度的白色背景
                });
            },
            complete: function () {  //load默认不会关闭，请求完成需要在complete回调中关闭
                layer.close(index_wait);
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg("日志提取成功");
                    window.location.href = r.data;
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    });
}

function getAllLog(devId,ip) {
    layer.confirm("确认要提取的设备号为‘" + devId + "'的所有日志吗吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        let index_wait = "";
        $.ajax({
            type: 'post',
            data: {
                "devId":devId,
                "ip":ip,
            },
            url: '/yxjr/logPackage/getAllLog',
            beforeSend: function(){
                index_wait = layer.load(0, {  //发送请求前调用load方法
                    shade: [0.5, '#fff'],  //0.5透明度的白色背景
                });
            },
            complete: function () {  //load默认不会关闭，请求完成需要在complete回调中关闭
                layer.close(index_wait);
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg("日志提取成功");
                    window.location.href = r.data;
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    });
}