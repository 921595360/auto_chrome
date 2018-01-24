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
    <!-- 图表相关开始 -->
    <!--Flot Pie Chart [ OPTIONAL ]-->
    <script src="js/jquery.flot.min.js"></script>
    <script src="js/jquery.flot.pie.min.js"></script>
    <script src="js/jquery.flot.resize.min.js"></script>
    <!--Gauge js [ OPTIONAL ]-->
    <script src="js/gauge.min.js"></script>
    <!--Easy Pie Chart [ OPTIONAL ]-->
    <script src="js/jquery.easypiechart.min.js"></script>
    <!-- 图表相关结束 -->


    <!--END NAVBAR-->
    <div class="boxed">
        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
            <div id="page-title">
                <h1 class="page-header text-overflow">控制台</h1>

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

            <div id="page-content">
                <div class="row">
                <div class="eq-height">


                    <div class="col-sm-6 col-lg-4 eq-box-lg">
                        <div class="panel">
                            <div class="panel-heading">
                                    <h3 class="panel-title">成功率</h3>
                            </div>
                            <div class="panel-body text-center">

                                <!--Easy Pie Chart-->
                                <!--===================================================-->
                                <div id="demo-pie-1" class="pie-title-center mar-rgt" data-percent="${success}">
                                    <span class="pie-value text-thin"></span>
                                </div>
                                <!--===================================================-->
                                <!-- End Easy Pie Chart -->

                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-4 eq-box-lg">
                        <div class="panel">
                            <div class="panel-heading">
                                <h3 class="panel-title">待执行</h3>
                            </div>
                            <div class="panel-body text-center">

                                <!--Easy Pie Chart-->
                                <!--===================================================-->
                                <div id="demo-pie-0" class="pie-title-center mar-rgt" data-percent="${started}">
                                    <span class="pie-value text-thin"></span>
                                </div>
                                <!--===================================================-->
                                <!-- End Easy Pie Chart -->

                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-lg-4 eq-box-lg">
                        <div class="panel">
                            <div class="panel-heading">
                                <h3 class="panel-title">失败率</h3>
                            </div>
                            <div class="panel-body text-center">

                                <!--Easy Pie Chart-->
                                <!--===================================================-->
                                <div id="demo-pie-2" class="pie-title-center" data-percent="${failed}">
                                    <span class="pie-value text-thin"></span>
                                </div>
                                <!--===================================================-->
                                <!-- End Easy Pie Chart -->

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
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
    $(function(){

        $('#demo-pie-0').easyPieChart({
            barColor :'#68b828',
            scaleColor: false,
            trackColor : '#eee',
            lineCap : 'round',
            lineWidth :8,
            onStep: function(from, to, percent) {
                $(this.el).find('.pie-value').text(Math.round(percent) + '%');
            }
        });

        $('#demo-pie-1').easyPieChart({
            barColor :'#68b828',
            scaleColor: false,
            trackColor : '#eee',
            lineCap : 'round',
            lineWidth :8,
            onStep: function(from, to, percent) {
                $(this.el).find('.pie-value').text(Math.round(percent) + '%');
            }
        });

        $('#demo-pie-2').easyPieChart({
            barColor :'#E61A1A',
            scaleColor:false,
            trackColor : '#eee',
            lineCap : 'butt',
            lineWidth :8,
            onStep: function(from, to, percent) {
                $(this.el).find('.pie-value').text(Math.round(percent) + '%');
            }
        });


    });




</script>
</body>
</html>
