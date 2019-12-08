/**
 * 数据源管理
 * pengl
 * 2018/11/22
 */
(function ($, window, document) {
    window.task = window.task || {};
    var table = {};
    var rootPath = common.root();
    var querySqlStr = '';
    /**
     * 页面初始化
     */
    task.init = function () {
        initTable();
        dtsListens();
        tableListens();
    };
    /**
     * 任务编辑
     * @returns {boolean}
     */
    task.edit = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        if(checkResult.length > 1){
            common.alertError('只能选择一条记录进行操作');
            return false;
        }
        var taskId = checkResult[0];
        var rowData = dtable.getCheckRowData('taskTable', taskId);
        var tkContent = JSON.parse(rowData.taskContent);
        $("#taskInfoTitle").html("编辑任务【"+taskId+"】");
        fixFormData(rowData, tkContent);
        lockForm();
        $("#taskWin").modal('show');
    };
    /**
     * 任务保存
     */
    task.save = function() {
        var formData = getFormData();
        if(verifyFormData(formData)){
            $.ajax({
                type: "post",
                url: rootPath + "/task/save",
                data: JSON.stringify(formData) ,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    if(data.flag){
                        var sucMsg = new StringBuffer("任务保存成功，成功推送：");
                        var nods = data.data;
                        if(nods){
                            var lgh = nods.length;
                            sucMsg.append(lgh + "个节点：<br>");
                            for(var i = 0; i<lgh; i++){
                                sucMsg.append(nods[i] + "<br/>");
                            }
                        }else{
                            sucMsg.append('0个节点');
                        }
                        layer.alert(sucMsg.toString(), {icon: 1}, function () {
                            $("#taskWin").modal('hide');
                            dtable.reload(table);
                        });
                    }else{
                        common.alertError(data.msg);
                    }

                }, error: function () {
                    common.alertError('后台出现异常！');
                }
            })
        }
    }
    /**
     * 任务删除
     */
    task.del = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        //询问框
        layer.confirm('是否删除选中的数据源？', {
            icon: 3
        }, function(){
            $.post(rootPath + "/task/del",{taskIds : checkResult.join(',')}, function (data) {
                if(data.flag){
                    common.alertSuc('成功删除：'+ data.msg + '个任务', function () {
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(data.msg);
                }
            }, "json");
        }, function(){

        });
    };

    task.add = function () {
        clearForm();
        $("#taskInfoTitle").html("新增任务");
        $("#taskWin").modal('show');
    };

    task.excuteshow = function(){
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        $("#excuteWin").modal('show');
    }

    task.push = function(){
        common.alertError('功能未开放');
    }

    task.checkAllFeilds = function(name){
        $("input[name='" + name + "']").iCheck('check');
    }

    task.uncheckAllFeilds = function(name){
        $("input[name='" + name + "']").iCheck('uncheck');
    }

    /**
     * 任务执行
     */
    task.excute = function () {
        var nodeId = $(":radio[name='excuteNodes']:checked").val();
        if(!nodeId){
            common.alertError('请选择执行节点');
            return;
        }
        $.ajax({
            url:rootPath + "/task/excute",
            type:"POST",
            data:{taskIds : dtable.check('ckbox').join(','), nodeId : nodeId},
            timeout:600000,
            dataType:"json",
            success:function(data){
                if(data.flag){
                    var sucMsg = new StringBuffer("成功执行：");
                    var nods = data.data;
                    if(nods){
                        var lgh = nods.length;
                        sucMsg.append(lgh + "个任务：<br>");
                        for(var i = 0; i<lgh; i++){
                            sucMsg.append(nods[i] + "<br/>");
                        }
                    }else{
                        sucMsg.append('0个任务');
                    }
                    layer.alert(sucMsg.toString(), {icon: 1}, function (index) {
                        $("#excuteWin").modal('hide');
                        layer.close(index);
                    });
                }else{
                    common.alertError(data.msg);
                }
            },
            error:function(){
                common.alertError('后台出现异常！');
            }
        });
    }

    function initTable() {
        table = dtable.init({
            "refer" : "taskTable",
            "ajax": {
                url : rootPath + "/task/list",
                type :"POST"
            },
            "columns": [
                { "data": "taskId"},
                { "data": "taskName"},
                { "data": "taskDesc"},
                { "data": "readerDsName"},
                { "data": "writerDsName"},
                { "data": "taskContent"},
                { "data": "status"},
                { "data": "updateTime"}
            ],
            "columnDefs": [
                {
                    "orderable": false,
                    "render": function ( data, type, row ) {
                        return '<input type ="checkbox" name="ckbox" data-flag="icheck" class="square-blue" value="' + data + '" />';
                    },
                    "targets":  0
                },
                {
                    "render": function ( data, type, row ) {
                        return '<span class="label label-warning" onclick="task.showTaskContent(\'' + row.taskId + '\')">点击查看</span>';
                    },
                    "targets":  5
                },
                {
                    "render": function ( data, type, row ) {
                        return data == 1 ? '<span class="label label-success">有效</span>' : '<span class="label label-danger">无效</span>';
                    },
                    "targets":  6
                },
                {
                    "render": function ( data, type, row ) {
                        var _date = new Date();
                        _date.setTime(data);
                        return _date.format("yyyy-MM-dd hh:mm:ss");
                    },
                    "targets":  7
                }
            ],
            "order": [[7, 'desc']],
            "drawCallback" : function () {
                common.initForm();
                configModelListen();
                dtable.checkEvent();
            }
        });
    }
    //清除表单数据
    function clearForm() {
        $("#task-form").find(':input[name]:not(:radio,:checkbox)').val('');
        $("#reader").val('mysqlreader');
        $("#writer").val('mysqlwriter');
        $("#readerDsId, #writerDsId").val('-1');
        $(":radio[name='confModel'][value='1']").iCheck('check');
        $(":radio[name='writeMode'][value='update']").iCheck('check');
        $(":checkbox[name='pushNodes']").iCheck('uncheck');
        $("#readerTableName, #writerTableName").html('').append("<option value='-1'>选择表</option>");
        $("#readerColumnsDiv, #writerColumnsDiv").html('');
    }
    //锁定表格字段
    function lockForm() {

    }
    //填充表单
    function fixFormData(data, tkContent) {
        var cot = tkContent.job.content[0];
        $("#primKey").val(data.taskId);
        $("#taskName").val(data.taskName);
        $("#taskDesc").val(data.taskDesc);
        $("#reader").val(cot.reader.name);
        $("#writer").val(cot.writer.name);
        $("#readerDsId").val(data.readerDsId);
        $("#writerDsId").val(data.writerDsId);
        dtsChange($("#readerDsId"), 'readerTableName', function(){});
        dtsChange($("#writerDsId"), 'writerTableName', function(){
            $("#writerTableName").val(cot.writer.parameter.connection[0].table[0]);
            tableChange($("#writerTableName"), $("#writerDsId").val(), 'writerColumnsDiv', "writerColumn", function(){
                var columns = cot.writer.parameter.column;
                if(columns && columns.length > 0){
                    for(var k=0; k<columns.length; k++){
                        $(":checkbox[name='writerColumn'][value='"+columns[k]+"']").iCheck('check');
                    }
                }
            })
        });
        $(":radio[name='confModel'][value='2']").iCheck('check');
        querySqlStr = cot.reader.parameter.connection[0].querySql[0];
        $("#querySql").val(querySqlStr);
        $("#preSql").val(cot.writer.parameter.preSql[0]);
        $("#postSql").val(cot.writer.parameter.postSql[0]);
        $(":radio[name='writeMode'][value='"+cot.writer.parameter.writeMode+"']").iCheck('check');
        $(":radio[name='status'][value='"+data.status+"']").iCheck('check');
        $("#channel").val(tkContent.job.setting.speed.channel);
        $("#taskSaveName").val(data.taskSaveName);
    }
    //获取表单数据
    function getFormData() {
        return {
            taskId : $("#primKey").val(),
            taskDesc : $("#taskDesc").val(),
            taskName : $("#taskName").val(),
            reader : $("#reader").val(),
            writer : $("#writer").val(),
            readerDsId : $("#readerDsId").val(),
            writerDsId : $("#writerDsId").val(),
            readerTableName : $("#readerTableName").val(),
            writerTableName : $("#writerTableName").val(),
            readerColumns : task.getIcheckValues('readerColumn'),
            writerColumns : task.getIcheckValues('writerColumn'),
            where : $("#where").val(),
            preSql : $("#preSql").val(),
            postSql : $("#postSql").val(),
            writeMode : $(":radio[name='writeMode']:checked").val(),
            channel : $("#channel").val(),
            pushNodes : task.getIcheckValues('pushNodes'),
            querySql : $("#querySql").val(),
            status : $(":radio[name='status']:checked").val(),
            taskSaveName : $("#taskSaveName").val()
        }
    }
    //表单验证
    function verifyFormData(formData) {
        if(formData.taskName.trim().length == 0){
            common.alertError('请输入任务名称！');
            return false;
        }
        if(formData.readerDsId == '-1'){
            common.alertError('请选择源数据库！');
            return false;
        }
        if(formData.writerDsId == '-1'){
            common.alertError('请选择目标数据库！');
            return false;
        }
        if(formData.writerTableName == '-1'){
            common.alertError('请选择目标数据库表！');
            return false;
        }
        if(formData.writerColumns.length == 0){
            common.alertError('请选择目标数据库的表字段！');
            return false;
        }
        var configModel = $(":radio[name='confModel']:checked").val();
        if(configModel == '1'){
            if(formData.readerTableName == '-1'){
                common.alertError('请选择源数据库表！');
                return false;
            }
            if(formData.readerColumns.length == 0){
                common.alertError('请选择源数据库表字段！');
                return false;
            }
        }else{
            if(formData.querySql.trim().length == 0){
                common.alertError('请填写查询SQL语句！');
                return false;
            }
        }
        if(formData.taskSaveName.length == 0){
            common.alertError('请输入保存的文件名称！');
            return false;
        }
        return true;
    }

    /**
     * 展示任务详情 TODO 待格式化
     * @param taskId
     */
    task.showTaskContent = function (taskId){
        var rowData = dtable.getCheckRowData('taskTable', taskId);
        common.model('任务详情', rowData.taskContent, 'default');
    }

    /**
     * 根据数据源加载所有的表
     * @param dsId
     */
    function loadTables(dsId, suc){
        $.post(rootPath + "/dts/tables",{dsId : dsId}, function (data) {
            if(data.flag){
                suc(data.data);
            }else{
                common.alertError(data.msg);
            }
        }, "json");
    }

    /**
     * 根据数据源和表名加载所有的列
     * @param dsId
     */
    function loadColumns(dsId, tableName, suc){
        $.post(rootPath + "/dts/columns",{dsId : dsId, tableName : tableName}, function (data) {
            if(data.flag){
                suc(data.data);
            }else{
                common.alertError(data.msg);
            }
        }, "json");
    }

    /**
     * 数据源切换
     * @param obj
     * @param selId
     */
    function dtsChange(obj, selId, suc){
        var dsId = obj.val();
        if(dsId == '-1'){
            $("#" + selId).html('').append("<option value='-1'>选择表</option>");
            suc();
            return;
        }
        loadTables(dsId, function (data) {
            $("#" + selId).html('').append("<option value='-1'>选择表</option>").append(appendTableNameOptions(data));
            suc();
        });
    }

    /**
     * 表名切换
     * @param obj
     * @param dsId
     * @param selId
     */
    function tableChange(obj, dsId, selId, name, suc){
        var tableName = obj.val();
        if(tableName == '-1'){
            $("#" + selId).html('');
            suc();
            return;
        }
        loadColumns(dsId, tableName, function (data) {
            $("#" + selId).html(appendColumnsCheckbox(data, name));
            common.initIcheck();
            $("#" + selId).sortable().disableSelection();
            configModelListen();
            suc();
        });
    }

    /**
     * 给数据源添加监听事件
     */
    function dtsListens(){
        $("#readerDsId").change(function () {
            dtsChange($(this), 'readerTableName', function(){})
        })
        $("#writerDsId").change(function () {
            dtsChange($(this), 'writerTableName', function(){})
        })
    }

    /**
     * 给表名添加监听事件
     */
    function tableListens(){
        $("#readerTableName").change(function () {
            tableChange($(this), $("#readerDsId").val(), 'readerColumnsDiv', "readerColumn", function(){})
        })
        $("#writerTableName").change(function () {
            tableChange($(this), $("#writerDsId").val(), 'writerColumnsDiv', "writerColumn", function(){})
        })
    }

    /**
     * 配置模式点击事件
     */
    function configModelListen(){
        $("input:radio[name='confModel']").on('ifChecked', function(event){
            var _v = $(this).val();
            if(_v == '1'){
                $("#querySql").val('');
                $("#querySqlDiv").hide();
                $("#readerTableNameDiv, #readerColumnsBigDiv, #whereDiv").show();
            }else{
                $("#readerTableNameDiv, #readerColumnsBigDiv, #whereDiv").hide();
                $("#querySql").val(querySqlStr);
                $("#querySqlDiv").show();
            }
        });
    }

    /**
     * icheck获取复选框值
     * @param name
     * @returns {Array}
     */
    task.getIcheckValues = function(name){
        var checkBoxArr = [];
        $("input[name='" + name + "']:checked").each(function () {
            checkBoxArr.push($(this).val());
        });
        return checkBoxArr;
    }

    /**
     * 拼接option
     * @param list
     * @returns {*}
     */
    function appendTableNameOptions(list){
        var _html = new StringBuffer();
        if(list && list.length > 0){
            for(var i = 0; i<list.length; i++){
                _html.append("<option value='" + list[i] + "'>" + list[i] + "</option>")
            }
        }
        return _html.toString();
    }

    /**
     * 拼接字段列表
     * @param list
     * @returns {*}
     */
    function appendColumnsCheckbox(list, name){
        var _html = new StringBuffer();
        if(list && list.length > 0){
            for(var i = 0; i<list.length; i++){
                _html.append("<div class=\"input-group\">");
                _html.append("<input type='checkbox' name='" + name + "' data-flag='icheck' class='square-orange' value='"+list[i]+"'/>" + list[i])
                _html.append("</div>")
            }
        }
        return _html.toString();
    }

    task.init();
})(jQuery, window, document);