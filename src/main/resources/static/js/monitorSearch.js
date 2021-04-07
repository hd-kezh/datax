var flag = "1";//1新增 2修改
$(function(){
    getTime();
    getunitCode();
    searchTableData();
});

function searchTableData(){
    if($(".dataTables_wrapper").length > 0 ){
        reload();
    }else{
        $(".search-btn").attr("disabled",false);
        $(".search-content").removeClass("hide");
        var state = $("#rwState").val();
        var type = $("#rwType").val();
        var rerunCount = $("#ifRun").val();
        var id = $("#goNumner").val();
        var url = httpUrl + "/datax/task/list/?orderBy=create_time&remark=1&state="+ state +"&type="+ type +"&rerunCount="+ rerunCount +"&id="+ id;
        tableInit("table",columns,url);
    }
}

function reload(refresh){
    var state = $("#rwState").val();
    var type = $("#rwType").val();
    var rerunCount = $("#ifRun").val();
    var id = $("#goNumner").val();
    var url = httpUrl + "/datax/task/list/?orderBy=create_time&remark=1&state="+ state +"&type="+ type +"&rerunCount="+ rerunCount +"&id="+ id;
    $('#table').DataTable().ajax.url(url);
    if(refresh){
        $('#table').DataTable().ajax.reload(null,false);//定时刷新页面时停留在当前分页
    }else{
        $('#table').DataTable().ajax.reload();
    }
}

function getPageStartInfo(resp){
    var json = resp.data;
    $store.set("getPageStartInfo",json);
    $.each(json, function (i, item) {
        for(var j=0;j<columns.length;j++){
            var value = columns[j].field;
            var state = item["state"];
            if(value == "state"){//任务状态:0.初始化，1.执行成功，2.执行失败，3.执行中
                var span = "";
                switch (item[value]){
                    case 0:
                        span = "<span class='bg-own-blue padding-icon'>初始化</span>";
                        break;
                    case 1:
                        span = "<span class='bg-own-green'>执行成功</span>";
                        break;
                    case 2:
                        span = "<span class='bg-own-red'>执行失败</span>";
                        break;
                    case 3:
                        span = "<span class='bg-own-yellow padding-icon'>执行中</span>";
                        break;
                    case 4:
                        span = "<span class='bg-own-deepred'>手工中止</span>";
                        break;
                }
                json[i][value] = span;
            }else if(value == "startTime" || value == "endTime"){
                if(item[value]){
                    var time = timestampToTime(item[value]);
                    json[i][value] = time;
                }
            }else if(value == "errorInfo"){
                var info = (item[value])? item[value] : "无";
                var test = "<span class='al-info-pointer' data-toggle='modal' data-target='.bs-example-modal-lg' onclick='showInfo(this)'>"+ info +"</span>";
                json[i][value] = test;
            }else if(value == "rsvStr3"){//修改
                var e = "启动";
                if(state.indexOf("yellow") != "-1"){
                    e = "停止";
                }
                var handleBtn = "<div><span class='hover-btn hover-btn-border' id='handle"+ item["id"] +"' onclick='reviseFunc(this.id)'>修改</span><span class='hover-btn' onclick='startBtn(this)'>"+ e +"</span></div>";
                json[i][value] = handleBtn;
            }else if(value == "rerunCount"){//是否重跑
                var rerunCount = (item[value] == 0)?"否" : "是";
                json[i][value] = rerunCount;
            }else if(value == "type"){//任务类型
                var type = "";
                if(item[value] == 1){
                    type = "批处理";
                }
                json[i][value] = type;
            }
            /*else{
                json[i][value] = item[value];
            }*/
        }
    });

    return json;
}

//自动刷新按钮
function refreshSwitch(){
    if($(".fa-refresh").hasClass("rotate-icon")){//关闭
        $(".fa-refresh").removeClass("rotate-icon");
        $(".refresh-test").html("启动刷新");
        window.clearInterval(interval);
    }else{//开启
        $(".fa-refresh").addClass("rotate-icon");
        $(".refresh-test").html("停止刷新");
        interval = setInterval( function () {
            reload(true);
        }, 5000 );
    }
}

//显示报错信息
function showInfo(ele){
    var text = $(ele).html();
    $(".bs-example-modal-lg .modal-body").html(text);
}

//默认启动
$('#defaultStart').on('ifChecked', function(){
    $("#datepickerDiv,#datepickerCycleDiv").addClass("hide");
});

//启动时间
$('#timeStart').on('ifChecked', function(){
    $("#datepickerDiv").removeClass("hide");
    $("#datepickerCycleDiv").addClass("hide");
});

//启动周期
$('#cycleTimeStart').on('ifChecked', function(){
    $("#datepickerCycleDiv").removeClass("hide");
    $("#datepickerDiv").addClass("hide");
});

//发布信息
function setData(){
    var taskName = $("#taskName").val();
    if(!taskName){
        alert("任务名不能为空");
        return;
    }
    var unitCode = $("#unitCode option:selected").val();
    if(unitCode == '00'){
        alert("请选择配置");
        return;
    }
    var startType = $("input[name='timeStart']:checked").val();//启动方式：1.默认启动，2.定时启动，3.周期启动
    var unitCode = $("#unitCode option:selected").val();
    var label = $("#unitCode option:selected").html();
    if(flag == "1"){
        var url = httpUrl + "/datax/task/insert/";
        var data = {
            "taskName": taskName,
            "unitCode": unitCode,
            "label" : label,
            "startType":startType,//启动方式
            "remark":"1"
        };
        if(startType == "2"){
            data.delayTime = $("#datepicker").val();//定时时间
        }else if(startType == "3"){
            var hour = parseInt($("#hour").val())*3600;
            var minute = parseInt($("#minute").val())*60;
            var second = parseInt($("#second").val());
            var time = hour + minute + second;
            data.cycle = time;//周期时间
        }

        $.ajaxFunc(url,JSON.stringify(data),"POST",function(resp){
            reload();
            $(".close").click();
        })
    }else{
        reviseData(taskName,unitCode,label);
    }
}

//修改接口
function reviseData(taskName,unitCode,label){
    var url = httpUrl + "/datax/task/update/";
    var data = {
        "id": $store.get("index"),
        "taskName": taskName,
        "unitCode": unitCode,
        "label" : label,
        "remark":"1"
    };
    $.ajaxFunc(url,JSON.stringify(data),"POST",function(resp){
        reload();
        $(".close").click();
        //alert("已修改");
    })
}

//新增按钮
function addWindowBtn(){
    flag = "1";
    $(".addWindow").modal();
    $("input[name='timeStart']").attr("disabled",false).iCheck('uncheck');
    $("#datepicker,#hour,#minute,#second").attr("disabled",false);
    $('#defaultStart').iCheck('check');
    $('#taskName,#datepicker').val("");
    $("#hour,#minute,#second").val("00");
    $(".addWindow .modal-title").html("添加新数据");
}

//修改
function reviseFunc(index){
    flag = "2";
    $(".addWindow").modal();
    $("input[name='timeStart']").attr("disabled",true).iCheck('uncheck');
    $("#datepicker,#hour,#minute,#second").attr("disabled",true);
    $(".addWindow .modal-title").html("修改数据");

    index = index.replace("handle","");
    $store.set("index",index);
    var resp = $store.get("getPageStartInfo");
    for(var i=0;i<resp.length;i++){
        if(resp[i].id == index){
            var taskName = resp[i].taskName;
            $("#taskName").val(taskName);
            //下拉框
            var unitCode = resp[i].unitCode;
            $("#unitCode").val(unitCode).trigger("change");

            var startType = resp[i].startType;
            if(startType == "1"){//默认
                $('#defaultStart').iCheck('check');
                $("#datepickerDiv,#datepickerCycleDiv").addClass("hide");
            }else if(startType == "2"){//定时
                $('#timeStart').iCheck('check');
                var delayTime = resp[i].delayTime;
                $("#datepicker").val(timestampToTime(delayTime));
                $("#datepickerCycleDiv").addClass("hide");
                $("#datepickerDiv").removeClass("hide");
            }else{//周期
                $('#cycleTimeStart').iCheck('check');
                var cycle = parseInt(resp[i].cycle);
                var hourVal = parseInt(cycle/3600);
                if(hourVal < 10) hourVal = "0"+ hourVal;
                var minVal = parseInt(cycle%3600/60);
                var secVal = parseInt(cycle%3600 - minVal*60);
                if(minVal < 10) minVal = "0"+ minVal;
                if(secVal < 10) secVal = "0"+ secVal;

                $("#hour").val(hourVal);
                $("#minute").val(minVal);
                $("#second").val(secVal);
                $("#datepickerDiv").addClass("hide");
                $("#datepickerCycleDiv").removeClass("hide");
            }
        }
    }
}

//启动
function startBtn(e){
    var id = $(e).prev().attr("id").replace("handle","");
    var data = {
        "TASK_ID": id
    };

    if($(e).html() == "启动"){//启动
        var url = httpUrl + "/datax/manage/schedule";
    }else{//停止
        var url = httpUrl + "/datax/manage/stop";
    }
    $.ajaxFunc(url,data,"GET",function(resp){
        if($(e).html() == "启动"){
            reload();
            alert("启动成功")
        }else{
            reload();
            alert("已停止")
        }
    })
}

function getTime(){
    var option = "";
    var hourOption = "";
    for(var i=0;i<61;i++){
        if(i<10) i="0"+i;
        if(i < 25){
            hourOption += '<option value="'+ i +'">'+ i +'</option>';
        }
        option += '<option value="'+ i +'">'+ i +'</option>';
    }
    $("#hour").html(hourOption);
    $("#second,#minute").html(option);
}

//获取配置下拉框
function getunitCode(){
    var url = httpUrl + "/datax/stashunit/list/";
    var data = {};
    $.ajaxFunc(url,data,"GET",function(resp){
        var option = "<option value='00'>-- 请选择 --</option>";
        $.each(resp,function(i,item){
            option += '<option value="'+ item["unitCode"] +'">'+ item["label"] +'</option>';
        });
        $("#unitCode").html(option);
    })
}

var columns = [
    {"field":"id","title":"任务号","data":"id"},
    {"field":"taskName","title":"任务名","data":"taskName"},
    {"field":"startTime","title":"任务启动时间","width":"120px","data":"startTime"},
    {"field":"endTime","title":"任务结束时间","width":"120px","data":"endTime"},
    {"field":"state","title":"任务状态","data":"state"},
    {"field":"errorInfo","title":"任务报错信息","width":"100px","data":"errorInfo"},
    {"field":"dealNum","title":"任务处理数据条数","width":"130px","data":"dealNum"},
    {"field":"type","title":"任务类型","data":"type"},
    {"field":"rerunCount","title":"是否重跑","data":"rerunCount"},
    {"field":"rsvStr3","title":"操作","data":"rsvStr3"}
];

var data = [
    [
        "nbsp",
        "2018-08-08 19:08:08",
        "<span class='bg-own-green'>执行成功</span>",
        "<span class='al-info-pointer' data-toggle='modal' data-target='.bs-example-modal-lg' onclick='showInfo(this)'>lskjdofns;bd;s;dfh;asfdkl;nsdfknas;dfnnsdfknasdfnnsdfknas</span>",
        "100",
        "<div><span class='hover-btn hover-btn-border'>修改</span><span class='hover-btn'>启动</span></div>"
    ],
    [
        "nbsp",
        "2018-08-08 19:08:08",
        "<span class='bg-own-red'>执行失败</span>",
        "<span class='al-info-pointer' data-toggle='modal' data-target='.bs-example-modal-lg' onclick='showInfo(this)'>lskjdofns;bd;s;dfh;asfdkl;nsdfknas;dfnnsdfknasdfnnsdfknas</span>",
        "100",
        "<div><span class='hover-btn hover-btn-border'>修改</span><span class='hover-btn'>停止</span></div>"
    ],
    [
        "nbsp",
        "2018-08-08 19:08:08",
        "<span class='bg-own-blue padding-icon'>初始化</span>",
        "<span class='al-info-pointer' data-toggle='modal' data-target='.bs-example-modal-lg' onclick='showInfo(this)'>lskjdofns;bd;s;dfh;asfdkl;nsdfknas;dfnnsdfknasdfnnsdfknas</span>",
        "100",
        "<div><span class='hover-btn hover-btn-border'>修改</span><span class='hover-btn'>启动</span></div>"
    ],
    [
        "nbsp",
        "2018-08-08 19:08:08",
        "<span class='bg-own-yellow padding-icon'>执行中</span>",
        "<span class='al-info-pointer' data-toggle='modal' data-target='.bs-example-modal-lg' onclick='showInfo(this)'>lskjdofns;bd;s;dfh;asfdkl;nsdfknas;dfnnsdfknasdfnnsdfknas</span>",
        "100",
        "<div><span class='hover-btn hover-btn-border'>修改</span><span class='hover-btn'>启动</span></div>"
    ]
];