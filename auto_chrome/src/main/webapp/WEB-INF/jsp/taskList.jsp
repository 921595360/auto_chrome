<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="/WEB-INF/common/common.jsp" %>
<html lang="en">
<%@include file="/WEB-INF/common/head.jsp"%>

<!--TIPS-->
<!--You may remove all ID or Class names which contain "demo-", they are only used for demonstration. -->
<body>
<div id="container" class="effect aside-float aside-bright mainnav-lg">

    <!--NAVBAR-->
    <!--===================================================-->
   <%@include file="/WEB-INF/common/header.jsp"%>
    <!--===================================================-->
    <!--END NAVBAR-->
    <div class="boxed">
        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
            <!--Page Title-->
            <!-- 内容标题 -->
            <div id="page-title">
                <h1 class="page-header text-overflow">任务管理</h1>
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

                <div class="btn-group">
                    <button onclick="changeStatus(-1)" class="btn btn-primary">已创建</button>
                    <button onclick="changeStatus(0)" class="btn btn-info">执行中</button>
                    <button onclick="changeStatus(2)" class="btn btn-danger">已失败</button>
                    <button onclick="changeStatus(1)" class="btn btn-success">已成功</button>
                </div>

                <div class="btn-group">
                    <div class=" form-inline">
                        <input type="number" class="form-control" id="time" placeholder="预约时间（小时）">
                        <button onclick="runTask()" class="btn btn-info">开始执行</button>
                        <button onclick="delTask()" class="btn btn-danger">全部删除</button>
                    </div>

                </div>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th><input type="checkbox" onclick="selectAll()">全选</th>
                        <th>账户</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>任务编号</th>
                        <th>房源编号</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${tasks}" var="task" varStatus="status">
                        <tr>
                            <td>${status.index+1}</td>
                            <td><input type="checkbox" class="select_task" value="${task.taskId}"></td>
                            <td>${task.countName}</td>
                            <td>
                                <c:if test="${task.status eq '-1'}">已创建</c:if>
                                <c:if test="${task.status eq '0'}">已启动</c:if>
                                <c:if test="${task.status eq '1'}">已成功</c:if>
                                <c:if test="${task.status eq '2'}">已失败</c:if>
                            </td>
                            <td>${task.createTime}</td>
                            <td>${task.beginTime}</td>
                            <td>${task.endTime}</td>
                            <td>${task.taskId}</td>
                            <td>${task.houseInfoId}</td>

                            <td>
                                <button data-target="#${task.taskId}-lg-modal" data-toggle="modal" class="btn btn-warning">错误信息</button>

                                <div id="${task.taskId}-lg-modal" class="modal fade" tabindex="-1">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                                                <h4 class="modal-title" id="myLargeModalLabel">错误信息</h4>
                                            </div>
                                            <div class="modal-body">
                                                <p>${task.errMsg}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

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
        <%@include file="/WEB-INF/common/right.jsp"%>
        <!--===================================================-->
        <!--END ASIDE-->
        <!--MAIN NAVIGATION-->
        <!--===================================================-->
        <!--左侧页面-->
        <%@include file="/WEB-INF/common/left.jsp"%>
        <!--===================================================-->
        <!--END MAIN NAVIGATION-->
    </div>

    <!-- FOOTER -->
    <!-- 底部页面 -->
    <!--===================================================-->
    <%@include file="/WEB-INF/common/footer.jsp"%>
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
<%@include file="/WEB-INF/common/settings.jsp"%>
<!--===================================================-->
<!-- END SETTINGS -->
<script>
    function changeStatus(status){
        var url='${ctx}/task/list?status='+status;
        <c:if test="${not(empty param.countName)}">
            url+='&countName=${param.countName}';
        </c:if>
        location.href=url;

    }

    function selectAll(){
        if(window.selectAllTask){
            //未知原因只能遍历设置checked属性才生效
            $('.select_task').each(function(i,v){v.checked=false});
            //$('.select_task').attr('checked',false);
            window.selectAllTask=undefined;
        }else{
            //$('.select_task').attr('checked',true);
            $('.select_task').each(function(i,v){v.checked=true});
            window.selectAllTask=true;
        }
    }

    /**
     * 开始执行任务
     */
    function runTask(){
        var taskIds='';
        $('.select_task:checked').each(function(i,v){
            taskIds+=','+v.value;
        });
        taskIds=taskIds.substring(1);

        $.post('${ctx}/task/run',{taskIds:taskIds,time:$('#time').val()},function(){
            $.niftyNoty({
                type: 'success',
                message : '<strong>操作成功!</strong> ',
                container : 'floating',
                timer : 500
            });

            location.href='${ctx}/task/list?status=0';
        });

    }


    /**
     * 删除所有任务
     */
    function delTask(){
        bootbox.confirm("确定删除?", function(result) {
            if (result) {
                location.href='${ctx}/task/delAll';
            }
        });
    }
</script>
</body>
</html>
