<#macro commonStyle>

	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${request.contextPath}/adminlte/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
    <link rel="stylesheet" href="${request.contextPath}/plugins/font-awesome-4.3.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <!-- <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"> -->
    <link rel="stylesheet" href="${request.contextPath}/plugins/ionicons-2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${request.contextPath}/adminlte/dist/css/AdminLTE-local.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${request.contextPath}/adminlte/dist/css/skins/_all-skins.min.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="${request.contextPath}/adminlte/plugins/iCheck/square/_all.css"/>
	<link rel="stylesheet" href="${request.contextPath}/adminlte/plugins/select2/select2.min.css"/>
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  
	<!-- scrollup -->
	<link rel="stylesheet" href="${request.contextPath}/plugins/scrollup/image.css">
	<!-- pace -->
	<link rel="stylesheet" href="${request.contextPath}/plugins/pace/themes/pace-theme-flash.css">

</#macro>

<#macro commonScript>
	<!-- jQuery 2.1.4 -->
	<script src="${request.contextPath}/adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.5 -->
	<script src="${request.contextPath}/adminlte/bootstrap/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="${request.contextPath}/adminlte/plugins/fastclick/fastclick.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${request.contextPath}/adminlte/dist/js/app.min.js"></script>

    <!-- scrollup -->
    <script src="${request.contextPath}/plugins/scrollup/jquery.scrollUp.min.js"></script>
    <!-- pace -->
    <script src="${request.contextPath}/plugins/pace/pace.min.js"></script>
	<#-- jquery.cookie -->
    <script src="${request.contextPath}/plugins/jquery/jquery.cookie.js"></script>
    <#-- jquery.validate -->
    <script src="${request.contextPath}/plugins/jquery/jquery.validate.min.js"></script>
	 <!-- iCheck 1.0.1 -->
    <script src="${request.contextPath}/adminlte/plugins/iCheck/icheck.min.js"></script>
	<#-- layer -->
	<script src="${request.contextPath}/plugins/layer/layer.js"></script>
	 <!-- Select2 -->
    <script src="${request.contextPath}/adminlte/plugins/select2/select2.full.min.js"></script>
	<script>var base_url = '${request.contextPath}';</script>
	<script src="${request.contextPath}/common.js"></script>
	<script src="${request.contextPath}/dtable.js"></script>
</#macro>

<#macro commonHeader>
	<header class="main-header">
		<a href="${request.contextPath}/" class="logo">
			<span class="logo-mini"><b>同步</b></span>
			<span class="logo-lg"><b>数据同步</b></span>
		</a>
		<nav class="navbar navbar-static-top" role="navigation">
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"><span class="sr-only">切换导航</span></a>
          	<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">

					<#-- login user -->
                    <li class="dropdown">
                        <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							<#if Session["user"]?exists>${Session["user"].name}</#if>，欢迎您！
							<span class="caret"></span>
						</a>
                        <ul class="dropdown-menu" role="menu">
                            <li id="logoutBtn"><a href="javascript:logout();">退出登录</a></li>
                        </ul>
                    </li>


				</ul>
			</div>
		</nav>
	</header>

	<!-- 修改密码.模态框 -->
	<div class="modal fade" id="updatePwdModal" tabindex="-1" role="dialog"  aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" >修改密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal form" role="form" >
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">新密码<font color="red">*</font></label>
							<div class="col-sm-10"><input type="text" class="form-control" name="password" placeholder="请输入新密码" maxlength="100" ></div>
						</div>
						<hr>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-6">
								<button type="submit" class="btn btn-primary"  >保存</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal" role="dialog" tabindex="-1" aria-labelledby="myModalLabel">
		<div class="modal-dialog" aria-hidden="true" style="top:10%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-right" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

</#macro>

<#macro commonLeft pageName >
	<!-- Left side column. contains the logo and sidebar -->
	<aside class="main-sidebar">
		<!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">
			<!-- sidebar menu: : style can be found in sidebar.less -->
			<ul class="sidebar-menu">
				<li class="header">主导航</li>
				<li class="nav-click <#if pageName == "task">active</#if>" ><a href="${request.contextPath}/task"><i class="fa fa-tasks text-aqua"></i><span>任务管理</span></a></li>
				<li class="nav-click <#if pageName == "dts">active</#if>" ><a href="${request.contextPath}/dts"><i class="fa fa-database text-green"></i><span>数据源管理</span></a></li>
				<li class="nav-click <#if pageName == "node">active</#if>" ><a href="${request.contextPath}/node"><i class="fa fa-paper-plane text-yellow"></i><span>Datax节点管理</span></a></li>
				<#--<li class="nav-click <#if pageName == "env">active</#if>" ><a href="${request.contextPath}/env"><i class="fa fa-bank text-blue"></i><span>环境管理</span></a></li>-->
			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>
</#macro>

<#macro commonFooter >

</#macro>