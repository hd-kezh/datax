//var httpUrl = "http://localhost:8080";
var httpUrl = location.protocol+ "//" +location.host;

$(function(){
    $.fn.dataTable.ext.errMode = "none";
    //关闭弹出窗口
    $(document).on("click",".modal .close",function(){
        $(this).parents(".modal").hide();
    });

    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
});

function loadPage(id){
    var url = id + ".html";
    $.ajaxSetup({cache: false });
    $(".content-wrapper .content").load(url,function(result){

    })
}

function creatScript(url){
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    document.body.appendChild(script);
}

//ajax方法
$.extend({
    ajaxFunc : function(url, param, type, successFunc, errorFunc) {
        $.ajax({
            url:  url,
            type: type,
            timeout: 60000,
            dataType: "json",
            data: param,
            async: false,
            contentType: "application/json",
            success: function (data) {
                successFunc(data);
            },
            error: function (data) {
                if (errorFunc) {
                    errorFunc(data);
                }
            }
        });
    }
});

//本地存储
var $store = {
    set : function(key , value){
        if(value != null){
            localStorage.setItem(key , JSON.stringify(value));
        }
    },
    get : function(key){
        return JSON.parse(localStorage.getItem(key));
    },
    remove : function(key){
        localStorage.removeItem(key);
    },
    clear : function(){
        localStorage.clear();
    }
};

/*
1.table的id
2.columnsData 定义table表头
3.dataObject 接收数据
4.第一列为checkbox取消第一列排序
*/
function tableInit(id,columnsData,url,checkbox){
    var aoColumnDefs = [];
    var aaSorting = [];
    if(checkbox){
        aoColumnDefs = [ { "bSortable": false, "aTargets": [ 0 ] }];
        aaSorting = [[1, "asc"]];
    }
    var wageSummaryTable = $("#" +id).DataTable({
        destroy:true,
        retrieve:true,
        ordering: true, //开启列排序
        autoWidth: false,  //自动设置宽度
        columns:columnsData,
        aoColumnDefs: aoColumnDefs,//第一列为checkbox时取消第一列排序
        aaSorting: aaSorting,//第一列为checkbox时取消第一列排序
        ajax : {
            // 使用POST请求的方式（注意：多个字段必须在后面加上逗号）
            type : 'GET',
            dataType : 'json',
            cache : false,
            async : false,
            contentType : "application/json;charset=UTF-8",
            // 请求的URL地址（填写服务端的接口地址即可，这里我就模拟一个本地的数据）
            url : url,
            dataSrc: function(json){//处理返回数据！！！！！！！！！！！
                return getPageStartInfo(json);
            }
        },
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        /*fnDrawCallback: function( oSettings ) {
            $("#"+ id +" thead tbody th:first").removeClass("sorting_asc");//移除checkbox列的排序箭头
        }*/
    });

    $(".dataTable td").attr("onmouseover","this.title=this.innerText");
}

//table返回数据格式化
//1.resp返回data 数组格式
//2.btn增删改查按钮 数组格式[$creatBtn.del("funcName()"),$creatBtn.add("funcName()"),$creatBtn.revise("funcName()")]
function creatTableData(list,btn){
    var arrayData = [];
    var checkboxField = '<input type="checkbox" class="icheckbox_minimal">';
    $.each(list, function (i, item) {
        var array = [];
        for(var i=0;i<columns.length;i++){
            var value = columns[i].field;
            if(value == "checkboxField"){
                array.push(checkboxField);
            }else if(value == "btnField"){
                var allBtn = "";
                if(btn){
                    $.each(btn,function(index,$this){
                        allBtn += $this;
                    });
                }
                array.push(allBtn);
            }else{
                array.push(item[value]);
            }
        }
        arrayData.push(array);
    });
    return arrayData;
}

//生成botton 参数为button添加的方法
var $creatBtn = {
    "add":function(func,className){
        func = (func) ? "onclick=" + func : "";
        className = (className) ? className : "";
        var button = "<button class='btn btn-success btn-sm mb-2 mt-2 "+ className +"' "+ func +"><i class='fa fa-fw fa-plus'></i>增加</button>";
        return button;
    },
    "del":function(func,className){
        func = (func) ? "onclick=" + func : "";
        className = (className) ? className : "";
        var button = "<button class='btn btn-danger btn-sm mb-2 mt-2 "+ className +"' "+ func +"><i class='fa fa-fw fa-trash-o'></i>删除</button>";
        return button;
    },
    "revise":function(func,className){
        func = (func) ? "onclick=" + func : "";
        className = (className) ? className : "";
        var button = "<button class='btn btn-warning btn-sm mb-2 mt-2 "+ className +"' "+ func +"><i class='fa fa-fw fa-pencil'></i>修改</button>";
        return button;
    }
};

function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = (date.getHours() < 10)? '0' + date.getHours() + ':' : date.getHours() + ':';
    var m = (date.getMinutes() < 10)? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
    var s = (date.getSeconds() < 10)? '0' + date.getSeconds() : date.getSeconds();
    return Y+M+D+h+m+s;
}

function hourTimes(all){
    var hourVal = parseInt(all/3600);
    if(hourVal < 10) hourVal = "0"+ hourVal;
    var minVal = parseInt(all%3600/60);
    var secVal = parseInt(all%3600 - minVal*60);
    if(minVal < 10) minVal = "0"+ minVal;
    if(secVal < 10) secVal = "0"+ secVal;
    var times = {
        "hourVal" : hourVal,
        "minVal" : minVal,
        "secVal" : secVal
    };
    return times;
}