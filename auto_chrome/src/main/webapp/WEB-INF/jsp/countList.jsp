<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="/WEB-INF/common/common.jsp" %>
<html lang="en">
<%@include file="/WEB-INF/common/head.jsp" %>

<!--TIPS-->
<!--You may remove all ID or Class names which contain "demo-", they are only used for demonstration. -->
<body>
<div id="container" class="effect aside-float aside-bright mainnav-lg">

    <!--NAVBAR-->
    <!--===================================================-->
    <%@include file="/WEB-INF/common/header.jsp" %>
    <!--===================================================-->
    <!--END NAVBAR-->
    <div class="boxed">
        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
            <!--Page Title-->
            <!-- 内容标题 -->
            <div id="page-title">
                <h1 class="page-header text-overflow">账户管理</h1>
                <!--Searchbox-->
                <div class="searchbox">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search..">
                            <span class="input-group-btn">
                                <button class="text-muted" type="button"><i class="demo-pli-magnifi-glass"></i></button>
                            </span>
                    </div>
                </div>
            </div>
            <!--End page title-->
            <!--Page content-->
            <!--页面内容->
            <!--===================================================-->
            <div id="page-content">

                <form class="form-inline" action="${ctx}/count/add">
                    <div class="form-group">
                        <input type="text" name="countName" placeholder="账户" id="demo-inline-inputmail"
                               class="form-control">
                    </div>
                    <div class="form-group">
                        <input type="password" name="countPass" placeholder="密码" id="demo-inline-inputpass"
                               class="form-control">
                    </div>

                    <button class="btn btn-primary" type="submit">添加</button>
                </form>

                <div class="form-inline">
                    <div class="form-group">
                        <input type="number" name="pushDay" placeholder="推送天数（默认三天）" id="pushDay"
                               class="form-control">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-danger" onclick="quickPush()">极速推送</button>
                        <button class="btn btn-pink" onclick="cancelPush()">取消所有推送</button>
                    </div>
                </div>


                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><input type="checkbox" onclick="selectAll()">全选</th>
                        <th>账户名</th>
                        <th>密码</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${counts}" var="count">
                        <tr>
                            <td><input type="checkbox" class="select_count" value="${count.countName}"></td>
                            <td class="">${count.countName}</td>
                            <td>${count.countPass}</td>
                            <td>
                                <button class="btn btn-primary" onclick="login('${count.countName}')">登录</button>
                                <button class="btn btn-success" onclick="aiPush('${count.countName}')">智能推送</button>
                                <!-- 赶集智能推送 -->
                                <button class="btn btn-warning" onclick="applyGanji('${count.countName}')">赶集智能推送</button>
                                <a class="btn btn-info" href="${ctx}/task/list?countName=${count.countName}">任务管理</a>
                                <a class="btn btn-default" href="${ctx}/houseInfo/list?countName=${count.countName}">房源管理</a>
                                <a class="btn btn-danger" href="${ctx}/count/del?countName=${count.countName}">删除账户</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!--End page content-->
        </div>
        <!--===================================================-->
        <!--END CONTENT CONTAINER-->
        <!--ASIDE-->
        <!--===================================================-->
        <%@include file="/WEB-INF/common/right.jsp" %>
        <!--===================================================-->
        <!--END ASIDE-->
        <!--MAIN NAVIGATION-->
        <!--===================================================-->
        <!--左侧页面-->
        <%@include file="/WEB-INF/common/left.jsp" %>
        <!--===================================================-->
        <!--END MAIN NAVIGATION-->
    </div>

    <!-- FOOTER -->
    <!-- 底部页面 -->
    <!--===================================================-->
    <%@include file="/WEB-INF/common/footer.jsp" %>
    <!--===================================================-->
    <!-- END FOOTER -->

    <!-- SCROLL PAGE BUTTON -->
    <!--===================================================-->
    <button class="scroll-top btn">
        <i class="pci-chevron chevron-up"></i>
    </button>
    <!--===================================================-->
</div>
<!--===================================================-->
<!-- END OF CONTAINER -->
<!-- SETTINGS - DEMO PURPOSE ONLY -->
<!-- 设置 -->
<!--===================================================-->
<%@include file="/WEB-INF/common/settings.jsp" %>
<!--===================================================-->
<!-- 临时处理对话框宽度问题，其他页面不建议使用 -->
<style>
    .modal-dialog {
        width: auto;
    }

</style>
<!-- END SETTINGS -->
<script>
    function login(countName) {
        $.get('${ctx}/count/login?countName=' + countName);
    }

    function selectAll() {
        if (window.selectAllCount) {
            //未知原因只能遍历设置checked属性才生效
            $('.select_count').each(function (i, v) {
                v.checked = false
            });
            window.selectAllCount = undefined;
        } else {
            $('.select_count').each(function (i, v) {
                v.checked = true
            });
            window.selectAllCount = true;
        }
    }


    function selectAllPush() {
        var isCheck= $('.select_push')[0].checked;
        if (isCheck) {
            $('.select_push').each(function (i, v) {
                v.checked = false;
            });
        } else {
            $('.select_push').each(function (i, v) {
                v.checked = true;
            });
        }
    }

    function quickPush() {
        var countNames = '';
        var pushDay = $('#pushDay').val();
        bootbox.confirm("确定采用极速推送?", function (result) {
            if (result) {

                $('.select_count:checked').each(function (i, v) {
                    countNames += ',' + v.value;
                });

                $.post('${ctx}/task/quickPush', {countNames: countNames, pushDay: pushDay}, function () {
                    $.niftyNoty({
                        type: 'success',
                        message: '<strong>开始执行!</strong> ',
                        container: 'floating',
                        timer: 500
                    });
                });
            }
        });
    }

    function cancelPush() {
        var countNames = '';
        bootbox.confirm("确定取消所有推送?", function (result) {
            if (result) {

                $('.select_count:checked').each(function (i, v) {
                    countNames += ',' + v.value;
                });

                $.post('${ctx}/task/cancelPush', {countNames: countNames}, function () {
                    $.niftyNoty({
                        type: 'success',
                        message: '<strong>开始执行!</strong> ',
                        container: 'floating',
                        timer: 500
                    });
                });
            }
        });
    }

    function aiPush(countName) {
        bootbox.prompt("获取前多少页房源（默认8页）?", function (result) {
            if (!result) result = 8;
            $.post('${ctx}/task/aiPush', {pages: result,countName:countName}, function (data) {
                if (data.success && data.result) {
                    var tableHtml = "<div class=\"btn-group\">\n" +
                            "	<div class=\" form-inline\">\n" +
                            "<input type=\"number\" name=\"pushDay1\" placeholder=\"推送天数（默认三天）\" id=\"pushDay1\" class=\"form-control\">" +
                            "<button onclick=\"push('"+countName+"')\" class=\"btn btn-info\">批量推送</button>\n" +
                            "	</div>\n" +
                            "</div>"+
                            "<table  class=\"table table-bordered\">\n" +
                            "		<thead>\n" +
                            "               <tr>\n" +
                            "	                    <th><input type=\"checkbox\" onclick=\"selectAllPush()\">全选</th>\n" +
                            "	                    <th class=\"text-center\">序号</th>\n" +
                            "						<th>标题</th>\n" +
                            "						<th>详情</th>\n" +
                            "						<th>发布、更新时间</th>\n" +
                            "						<th>昨日浏览</th>\n" +
                            "						<th>三月总浏览</th>\n" +
                            "						<th>操作</th>\n" +
                            "				</tr>\n" +
                            "		</thead>\n" +
                            "		<tbody>\n";

                    $(data.result).each(function (i, v) {
                        tableHtml += "				<tr>\n" +
                                "						<td><input type=\"checkbox\" id='"+v.houseNum+"' class=\"select_push\"></td>" +
                                "						<td>" + (i + 1) + "</td>\n" +
                                "						<td>" + v.title + "</td>\n" +
                                "					    <td>" + v.detail + "</td>\n" +
                                "						<td>" + v.pushAndUpdateDate + "</td>\n" +
                                "						<td>" + v.yesDayView + "</td>\n" +
                                "						<td>" + v.totalView + "</td>\n" +
                                "						<td><button class=\"btn btn-pink\">查看详情</button></td>\n" +
                                "				</tr>\n";

                    });


                    tableHtml += "		</tbody>\n" +
                            "</table>";


                    bootbox.dialog({
                        message: tableHtml,
                        callback: function (result) {
                            //Callback function here


                        },
                        animateIn: 'bounceIn',
                        animateOut: 'bounceOut'
                    });


                }
            });
        });
    }

    /**
     *
     */
    function push(countName){
        bootbox.confirm("确定批量推送?", function (result) {
            if (result) {
                var pushDay=$('#pushDay1').val();
                var houseIds='';
                $('.select_push:checked').each(function (i, v) {
                    houseIds += ',' + v.id;

                });

                $.post('${ctx}/task/push', {countName:countName,houseIds: houseIds, pushDay: pushDay}, function () {
                    $.niftyNoty({
                        type: 'success',
                        message: '<strong>开始执行!</strong> ',
                        container: 'floating',
                        timer: 500
                    });
                });
            }
        });

    }

    // 赶集智能推广
    function applyGanji(countName) {
        $.get('${ctx}/count/applyGanji?countName='+countName);
    }


    $(function () {

        /*bootbox.confirm({
         message : "<table clas></table>",
         callback : function(result) {
         //Callback function here
         },
         animateIn: 'bounceIn',
         animateOut : 'bounceOut'
         });*/


    });
</script>
</body>
</html>
