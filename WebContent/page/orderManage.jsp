<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户关系管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$.ajax({
		url: '${pageContext.request.contextPath}/customer/findById.do',
		type: 'post',
		data: {id: '${param.cusId}'},
		dataType: 'json',
		success: function(result){
			$("#khno").val(result.khno);
			$("#name").val(result.name);
		}
	});
});
	function formatState(value, row, index){
		if(value==1){
			return "已回款";
		}else{
			return "未回款";
		}
	}
	function formatAction(value, row, index){
		return "<a href='javascript:openOrderDetailsDialog("+row.id+")'>查看订单明细</a>";
	}
	function openOrderDetailsDialog(orderId){
		$.post('${pageContext.request.contextPath}/order/findById.do', {id : orderId}, function(result){
			$("#fm").form("load",result);
			if(result.state==0){
				$("#state").val("未回款");
			}else{
				$("#state").val("已回款");
			}
		},'json');
		$.post('${pageContext.request.contextPath}/orderDetails/getTotalPrice.do', {orderId : orderId}, function(result){
			$("#totalMoney").val(result.totalMoney);
		},'json');
		$("#dg2").datagrid("options").url="${pageContext.request.contextPath }/orderDetails/list.do";
		$("#dg2").datagrid("reload",{'orderId':orderId});
		$("#dlg").dialog("open");
	}
</script>
</head>
<body style="margin: 10px;">
<div id="p" class="easyui-panel" title="客户基本信息" style="width: 900px; height: 100px; padding: 10px;">
	<table cellpadding="8px;">
		<tr>
			<td>客户编号：</td>
			<td><input readonly="readonly" type="text" id="khno" name="khno"></td>
			<td>&nbsp;</td>
			<td>客户名称：</td>
			<td><input readonly="readonly" type="text" id="name" name="name"></td>
		</tr>
	</table>
</div>
<br>
<table id="dg" title="客户历史订单" class="easyui-datagrid" data-options="fitColumns:true,pagination:true,rownumbers:true,
	url:'${pageContext.request.contextPath }/order/list.do?cusId=${param.cusId }'" style="width: 900px; height: 400px;">
	<thead>
		<tr>
		<th field="id" width="50" align="center">编号</th>
		<th field="orderNo" width="100" align="center">订单号</th>
		<th field="orderDate" width="100" align="center">订购日期</th>
		<th field="address" width="200" align="center">送货地址</th>
		<th field="state" width="100" formatter="formatState" align="center">状态</th>
		<th field="action" width="100" formatter="formatAction" align="center">操作</th>
		</tr>
	</thead>	
</table>
<div id="dlg" class="easyui-dialog" title="订单明细" style="width: 700px; height:450px; padding: 10px 20px;" closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
	<table cellpadding="8px;">
		<tr>
			<td>订单号：</td>
			<td><input type="text" id="orderNo" name="orderNo" readonly></td>
			<td>&nbsp;</td>
			<td>订购日期：</td>
			<td><input type="text" id="orderDate" name="orderDate" readonly></td>
		</tr>
		<tr>
			<td>送货地址：</td>
			<td><input type="text" id="address" name="address" readonly></td>
			<td>&nbsp;</td>
			<td>总金额：</td>
			<td><input type="text" id="totalMoney" name="totalMoney" readonly></td>
		</tr>
		<tr>
			<td>状态：</td>
			<td><input type="text" id="state" name="state" readonly></td>
			<td colspan="3">&nbsp;</td>
		</tr>
	</table>
	</form>
	<table id="dg2" title="订单订购详情" class="easyui-datagrid" data-options="fitColumns:true,pagination:true,rownumbers:true,
		url:''" style="width: 600px; height: 220px;">
		<thead>
			<tr>
			<th field="id" width="50" align="center">编号</th>
			<th field="goodsName" width="160" align="center">商品名称</th>
			<th field="goodsNum" width="100" align="center">订购数量</th>
			<th field="unit" width="100" align="center">单位</th>
			<th field="price" width="100" align="center">价格（元）</th>
			<th field="sum" width="100" align="center">金额（元）</th>
			</tr>
		</thead>	
	</table>
</div>
<div id="dlg-buttons">
	<a href="javascript: $('#dlg').dialog('close')" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>