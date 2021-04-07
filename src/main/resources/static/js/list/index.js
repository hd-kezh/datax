var myChart = echarts.init(document.querySelector('#chart-demand-all'))
var yMax = 30;
var dataLabel = ['完成任务数', '成功任务数', '失败任务数', '延迟任务数', '手工重跑数', '正在运行任务数']
/*var dataNum = [1, 2, 5, 1, 1, 2]*/
var dataNum = [0, 0, 0, 0, 0, 0]
var dataShadow = [];
for (var i = 0; i < dataLabel.length; i++) {
    dataShadow.push(yMax);
}

var colorList = function (params) {
    var colorList = ['#33a0ff', '#10cfe9', '#ff4858', '#ffc600', '#fa7d21', '#0ad00a'];
    return colorList[params.dataIndex]
}
var option = {
    // color  : colorList,
    tooltip: {
        trigger    : 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid   : {
        left        : '0%',
        right       : '0%',
        bottom      : '3%',
        containLabel: true
    },
    xAxis  : [
        {
            type    : 'category',
            data    : dataLabel,
            axisTick: {
                show:false,
                // inside:true,
                alignWithLabel: true
            },
            axisLine:{
                // show:false,
            }
        }
    ],
    yAxis  : [
        {
            type: 'value',
            axisTick: {
                // inside:true,
            },
            max: function(value) {
                var max = parseInt(value.max * 1.2)
                return max;
            },
            // boundaryGap: ['20%', '20%']
            // min:-1,
            // max:30,
            // minInterval:5
        }
    ],
    series : [
        {
            name    : '直接访问',
            type    : 'bar',
            itemStyle: {
                normal: {
                    color: colorList,
                },
                shadowColor:'#ff4858',
            },
            barWidth: '50%',
            data    : dataNum
        }
    ]
};
myChart.setOption(option)

window.onresize = function () {
    myChart.resize()
    
    myChart.setOption(option)
}
var socket = new SockJS('/websocket');
stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    // 注册推送时间回调
    stompClient.subscribe('/monitor/taskAgg', function(r) {
        var res = JSON.parse(r.body)
        var {SUCCESS_COUNT, COMPLETE_COUNT, FAIL_COUNT, DELAY_COUNT, RETRY_COUNT, RUNNING_COUNT, INIT_COUNT} = res
        var dataArray = [SUCCESS_COUNT, COMPLETE_COUNT, FAIL_COUNT, DELAY_COUNT, RETRY_COUNT, RUNNING_COUNT]
        // 更新文字统计
        var qs = $('.work-num')
        $.each(qs, function (index, value) {
            qs.eq(index).text(dataArray[index])
        })
        // 更新 echarts 柱状图
        option.series[0].data = dataArray
        myChart.setOption(option)
    });
    
})