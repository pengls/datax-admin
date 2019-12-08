<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>数据同步管理</title>

<#import "../common/common.macro.ftl" as netCommon>
<@netCommon.commonStyle />
<link rel="stylesheet" href="${request.contextPath}/adminlte/plugins/datatables/dataTables.bootstrap.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		
		<@netCommon.commonHeader />

		<@netCommon.commonLeft "task" />

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    任务管理
                    <small>任务列表</small>
                </h1>
            </section>
            <section class="content container-fluid">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box box-widget">
                            <div class="box-header with-border">
                                <div class="user-block">
                                    <label class="form-inline"><input type="text" class="form-control" id="globalSearch" placeholder="全局搜索(支持正则匹配)" /></label>
                                    <button type='button' class='btn btn-default' onclick="task.add();"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;新增</button>
                                    <button type='button' class='btn btn-default' onclick="task.edit();"><i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;&nbsp;编辑</button>
                                    <button type='button' class='btn btn-default' onclick="task.del();"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;删除</button>
                                    <button type='button' class='btn btn-default' onclick="task.excuteshow();"><i class="fa fa-bolt"></i>&nbsp;&nbsp;&nbsp;&nbsp;执行一次</button>
                                    <button type='button' class='btn btn-default' onclick="task.push();"><i class="fa fa-cogs"></i>&nbsp;&nbsp;&nbsp;&nbsp;任务推送</button>
                                </div>
                                <!-- /.box-tools -->
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="taskTable" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" data-flag="icheck" class="square-blue" allcheck="true" /></th>
                                        <th>名称</th>
                                        <th>描述</th>
                                        <th>源库</th>
                                        <th>目标库</th>
                                        <th>内容</th>
                                        <th>状态</th>
                                        <th>更新时间</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- / .col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

		<@netCommon.commonFooter />

	</div>
	<!-- ./wrapper -->

    <div id="taskWin" class="modal fade in" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <li class="fa fa-remove"></li>
                    </button>
                    <h5 class="modal-title" id="taskInfoTitle"></h5>
                </div>
                <div class="modal-body">
                    <form id="task-form" name="dts-form" class="form-horizontal bv-form" novalidate="novalidate">
                        <input type="hidden" name="primKey" id="primKey" />
                        <div class="box-body">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>任务名称</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="taskName" name="taskName" placeholder="任务名称" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Reader插件</label>
                                    <select class="form-control" style="width: 100%;" id="reader">
                                        <option value="mysqlreader">mysqlreader</option>
                                        <option value="oraclereader">oraclereader</option>
                                        <option value="sqlserverreader">sqlserverreader</option>
                                        <option value="postgresqlreader">postgresqlreader</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>源数据库</label>
                                    <select class="form-control" style="width: 100%;" id="readerDsId">
                                        <option value="-1">选择数据源</option>
                                        <#list dtslist as item>
                                            <option value="${item.dsId}">${item.dsDesc}(${item.dsType})</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group has-feedback">
                                    <label>配置模式</label>
                                    <div class="col-sm-12">
                                        <input type="radio" name="confModel" data-flag="icheck" class="square-yellow" checked value="1"/>
                                        点选
                                    </div>
                                </div>
                                <div class="form-group" id="readerTableNameDiv">
                                    <label>表</label>
                                    <select class="form-control" style="width: 100%;" id="readerTableName">
                                        <option value="-1">选择表</option>
                                    </select>
                                </div>
                                <div class="form-group" id="readerColumnsBigDiv">
                                    <label>字段(拖拽排序)</label>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:task.checkAllFeilds('readerColumn');"><span class="label label-warning">全选</span></a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:task.uncheckAllFeilds('readerColumn');"><span class="label label-danger">反选</span></a>
                                    <div class="col-sm-12" id="readerColumnsDiv"></div>
                                </div>
                                <div class="form-group" id="whereDiv">
                                    <label>条件语句</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="where" name="where" placeholder="where语句" />
                                    </div>
                                </div>
                                <div class="form-group" id="querySqlDiv" style="display: none;">
                                    <label>查询SQL</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" name="querySql" id="querySql" class="form-control" placeholder="查询SQL">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>写入前执行</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="preSql" name="preSql" placeholder="preSql" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>写入后执行</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="postSql" name="postSql" placeholder="postSql" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label>推送节点</label>
                                    <div class="col-sm-12">
                                        <#list nodelist as item>
                                            <input type="checkbox" name="pushNodes" data-flag="icheck" class="square-blue" value="${item.nodeId}"/>
                                            ${item.nodeIp}
                                        </#list>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>保存文件名称</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="taskSaveName" name="taskSaveName" placeholder="文件名称" />
                                        <span class="input-group-addon">.json</span>
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label>写入模式</label>
                                    <div class="col-sm-12">
                                        <input type="radio" name="writeMode" data-flag="icheck" class="square-blue" value="update" checked/>
                                        update
                                        <input type="radio" name="writeMode" data-flag="icheck" class="square-blue" value="insert"/>
                                        insert
                                        <input type="radio" name="writeMode" data-flag="icheck" class="square-blue" value="replace"/>
                                        replace
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>并发数</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="channel" name="channel" placeholder="并发数" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label>状态</label>
                                    <div class="col-sm-12">
                                        <input type="radio" name="status" data-flag="icheck" class="square-blue" value="1" checked/>
                                        有效
                                        <input type="radio" name="status" data-flag="icheck" class="square-blue" value="-1"/>
                                        无效
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>任务描述</label>
                                    <div class="col-sm-12 input-group">
                                        <input type="text" class="form-control" id="taskDesc" name="taskDesc" placeholder="任务描述" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Writer插件</label>
                                    <select class="form-control" style="width: 100%;" id="writer">
                                        <option value="mysqlwriter">mysqlwriter</option>
                                        <option value="oraclewriter">oraclewriter</option>
                                        <option value="sqlserverwriter">sqlserverwriter</option>
                                        <option value="postgresqlwriter">postgresqlwriter</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>目标数据库</label>
                                    <select class="form-control" style="width: 100%;" id="writerDsId">
                                        <option value="-1">选择数据源</option>
                                        <#list dtslist as item>
                                            <option value="${item.dsId}">${item.dsDesc}(${item.dsType})</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group has-feedback">
                                    <label>配置模式</label>
                                    <div class="col-sm-12">
                                        <input type="radio" name="confModel" data-flag="icheck" class="square-yellow" value="2"/>
                                        自编SQL
                                    </div>
                                </div>
                                <div class="form-group" id="writerTableNameDiv">
                                    <label>表</label>
                                    <select class="form-control" style="width: 100%;" id="writerTableName">
                                        <option value="-1">选择表</option>
                                    </select>
                                </div>
                                <div class="form-group" id="writerColumnsBigDiv">
                                    <label>字段(拖拽排序)</label>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:task.checkAllFeilds('writerColumn');"><span class="label label-warning">全选</span></a>
                                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:task.uncheckAllFeilds('writerColumn');"><span class="label label-danger">反选</span></a>
                                    <div class="col-sm-12" id="writerColumnsDiv"></div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-right">
                            <button type="button" class="btn btn-default" data-btn-type="cancel" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-btn-type="save" onclick="task.save();">提交</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div id="excuteWin" class="modal fade in bs-example-modal-sm" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-sm" style="top:20%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <li class="fa fa-remove"></li>
                    </button>
                    <h5 class="modal-title">执行节点</h5>
                </div>
                <div class="modal-body">
                    <form id="task-form" name="dts-form" class="form-horizontal bv-form" novalidate="novalidate">
                        <input type="hidden" name="primKey" id="primKey" />
                        <div class="box-body">
                            <div class="col-md-6">
                                <div class="form-group has-feedback">
                                    <label>执行节点</label>
                                    <div class="col-sm-12">
                                        <#list nodelist as item>
                                            <input type="radio" name="excuteNodes" data-flag="icheck" class="square-blue" value="${item.nodeId}"/>
                                            ${item.nodeIp}<br/>
                                        </#list>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-right">
                            <button type="button" class="btn btn-default" data-btn-type="cancel" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-btn-type="save" onclick="task.excute();">提交</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

	<@netCommon.commonScript/>

    <script src="${request.contextPath}/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/daterangepicker/moment.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/jQuery/jquery-ui.js"></script>
    <script src="${request.contextPath}/datax_admin/js/task.js"></script>
</body>
</html>
