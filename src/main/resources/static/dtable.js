/**
 * datatable简单封装
 * pengl
 * 2017.10.21
 */
(function ($, window, document) {
    window.dtable = window.dtable || {};

    var defaultOption = {
        "refer" : "tableid",
        "dom" : "rtip", //f:搜索框  l:每页X行
        "renderer" : 'bootstrap',
        "pagingType" : "full_numbers",
        "autoWidth" : true,
        "processing" : true, // 是否显示进度
        "serverSide" : false, // 是否服务端处理分页
        "stateSave"  : true,
        "destroy" : false,
        'paging' : true, // 是否分页
        'pageLength' : 10, //每页显示10条记录
        'searching' : true, // 是否使用内置的过滤功能
        "ajax" : "",  //ajax url
        "deferRender" : false, //控制Datatables的延迟渲染，可以提高初始化的速度
        "language" : {
            "lengthMenu" : "每页显示 _MENU_ 条记录",
            "zeroRecords" : "对不起，查询不到任何相关数据",
            "info" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "infoEmtpy" : "找不到相关数据",
            "infoFiltered" : "数据表中共为 _MAX_ 条记录",
            "processing" : "正在加载中...",
            "search" : "全局搜索",
            "url" : "", // 多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "paginate" : {
                "first" : "第一页",
                "previous" : " 上一页 ",
                "next" : " 下一页 ",
                "last" : " 最后一页 ",
                "jump" : "跳转"
            },
            "aria": {
                "sortAscending" :  ": 升序",
                "sortDescending" : ": 降序"
            }
        },
        "initComplete" : function (settings, json) {
            dtable.checkEvent();
        },
        "drawCallback" : function () {
            common.initForm();
            dtable.checkEvent();
        }
    };
    /**
     * 初始化表格
     */
    dtable.init = function (option) {
        option = $.extend(true,{}, defaultOption, option);
        var table = $('#' + option.refer).DataTable(option);
        //全局搜索
        localSearch("globalSearch" , option.refer, table);
        return table;
    };

    /**
     * 重新加载表格数据
     */
    dtable.reload = function (table) {
        table.ajax.reload(function () {
            dtable.checkEvent();
        });
    };

    /**
     * 获取datatables首列复选框的值
     */
    dtable.getCheckBoxVals = function (ckname) {
        var vals = '';
        $('[name=' + ckname + ']:checked').each(function () {
            vals += $(this).val() + ',';
        });
        return vals.substr(0,vals.length-1);
    };
    /**
     * 获取当前checkbox选择的行的行数据
     */
    dtable.getCheckRowData = function (tableid, idvalue) {
        var rowData = {};
        $("#"+ tableid + " tbody tr").each(function () {
            var $tr = $(this);
            var id = $tr.first('td').find('input').val();
            if(id == idvalue){
                rowData = $("#" + tableid).DataTable().row($tr[0]).data();
                return false;
            }
        });
        return rowData;
    };

    /**
     * 判断是否选中行，返回行的主键id值
     */
    dtable.check = function (ckname) {
        var ids = dtable.getCheckBoxVals(ckname);
        if(ids.length == 0){
            common.alertError('请选择要操作的行记录');
            return false;
        }
        ids = ids.split(',');
        return ids;
    };

    /**
     * datatable字段截取
     */
    dtable.subStr = function (data , length) {
        if(data && data.length > length){
            var _data = data.slice(0 ,length) + "...";
            return '<div title="' + data + '" data-placement="left"  data-toggle="tooltip">'+ _data +'</div>';
        }
        return (data && data != 'null') ? data : "";
    };

    function localSearch(searchid, tableid, table) {
        $("#" + searchid).on("keyup", function(){
            table.search($(this).val() , true ).draw(false);
        });
        var localTableVals = localStorage.getItem("DataTables_" + tableid + "_" + window.document.location.pathname);
        if(localTableVals){
            localTableVals = $.parseJSON( localTableVals );
            $("#" + searchid).val(localTableVals.search.search);
        }
    }

    dtable.checkEvent = function() {
        $("input[allcheck='true']").on('ifChecked', function(event){
            $(':checkbox').iCheck('check');
        });
        $("input[allcheck='true']").on('ifUnchecked', function(event){
            $(':checkbox').iCheck('uncheck');
        });
    }
})(jQuery, window, document);