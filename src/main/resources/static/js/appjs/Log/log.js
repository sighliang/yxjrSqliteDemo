let prefix="/yxjr/logPackage"
$(function() {
    load();
});

function load() {
    $('#exampleTable')
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
                    let logName=$('#logName').val();
                    let param={
                        size:params.limit,
                        current:params.offset/params.limit+1
                    }
                    if(devId !='' && devId !=null){
                        param.devId=devId
                    }
                    if(logName !='' && logName !=null){
                        param.logName=logName
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
                        field : 'logName',
                        title : '日志包名'
                    },
                    {
                        field : 'url',
                        title : '下载地址'
                    },{
                        field : 'uploadTime',
                        title : '上传时间'
                    },{
                        field : 'devId',
                        title : '所属设备'
                    },
                    {
                        title : '操作',
                        field : 'id',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var d = '<a class="btn btn-warning btn-sm " href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm " href='
                                + row.url
                                + '  title="下载" ><i class="fa fa-download"></i></a> ';
                            return  d + f;
                        }
                    } ],
                exportDataType:'all',//'basic':当前页的数据, 'all':全部的数据, 'selected':选中的数据
                showExport: true,  //是否显示导出按钮
                buttonsAlign:"left",  //按钮位置
                exportTypes:['excel'],//导出文件类型，[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf']
                exportOptions:{
                    ignoreColumn: [0,6],            //忽略某一列的索引
                    fileName: '日志包表',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: '日志包表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                }
            });
}
function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type : 2,
        title : '提取日志包',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '100%', '100%' ],
        content : prefix + '/add'
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : prefix,
            type : "delete",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    reLoad();
                }else{
                    layer.msg(r.msg);
                }
            }
        });
    })
}