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
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript">
$(function(){
	$.ajax({
		url: '${pageContext.request.contextPath}/saleChance/findById.do',
		type: 'post',
		data: {id: '${param.saleChanceId}'},
		dataType: 'json',
		success: function(result){
			$("#customerName").val(result.customerName);
			$("#chanceSource").val(result.chanceSource);
			$("#linkMan").val(result.linkMan);
			$("#linkPhone").val(result.linkPhone);
			$("#cgjl").val(result.cgjl);
			$("#overView").val(result.overView);
			$("#description").val(result.description);
			$("#createMan").val(result.createMan);
			$("#createTime").val(result.createTime);
			$("#assignMan").val(result.assignMan);
			$("#assignTime").val(result.assignTime);
		}
	});
	$("#dg").edatagrid({
		url:'${pageContext.request.contextPath}/cusDevPlan/list.do?saleChanceId=${param.saleChanceId}',
		saveUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		updateUrl:'${pageContext.request.contextPath}/cusDevPlan/save.do?saleChance.id=${param.saleChanceId}',
		destroyUrl:'${pageContext.request.contextPath}/cusDevPlan/delete.do'
	 });
});
	function updateSaleChanceDevResult(devResult){
		var url = '${pageContext.request.contextPath}/cusDevPlan/updateSaleChanceDevResult.do';
		$.post(url,{id:'${param.saleChanceId}',devResult:devResult},function(r){
			if(r.success){
				$.messager.alert("系统提示","执行成功！");
			}else{
				$.messager.alert("系统提示","执行失败！");
			}
		},'json');
	}
</script>
</head>
<body style="margin: 10px;">
<div id="p" class="easyui-panel" title="销售机会信息" style="width: 700px; height: 400px; padding: 10px;">
	<table cellpadding="8px;">
		<tr>
			<td>客户名称：</td>
			<td><input readonly="readonly" type="text" id="customerName" name="customerName"></td>
			<td>&nbsp;</td>
			<td>机会来源：</td>
			<td><input readonly="readonly" type="text" id="chanceSource" name="chanceSource"></td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td><input readonly="readonly" type="text" id="linkMan" name="linkMan"></td>
			<td>&nbsp;</td>
			<td>联系电话：</td>
			<td><input readonly="readonly" type="text" id="linkPhone" name="linkPhone"></td>
		</tr>
		<tr>
			<td>成功几率（%）：</td>
			<td>
				<input readonly="readonly" type="text" id="cgjl" name="cgjl">
			</td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td>概要：</td>
			<td colspan="4">
				<input readonly="readonly" type="text" id="overView" name="overView" style="width: 450px">
			</td>
		</tr>
		<tr>
			<td>机会描述：</td>
			<td colspan="4">
				<textarea readonly="readonly" id="description" name="description" rows="5" cols="62"></textarea>
			</td>
		</tr>
		<tr>
			<td>创建人：</td>
			<td><input  readonly="readonly" type="text" id="createMan" name="createMan"></td>
			<td>&nbsp;</td>
			<td>创建时间：</td>
			<td><input  readonly="readonly" type="text" id="createTime" name="createTime"></td>
		</tr>
		<tr>
			<td>指派给：</td>
			<td>
			<input readonly="readonly" type="text" id="assignMan" name="assignMan"></td>
			<td>&nbsp;</td>
			<td>指派时间：</td>
			<td>
			<input readonly="readonly" type="text" id="assignTime" name="assignTime"></td>
		</tr>
	</table>
</div>
<br>
<table id="dg" title="开发计划项" style="width: 700px; height: 250px;" toolbar="#tb" idField="id" rownumbers="true" fitColumns="true" singleSelect="true">
	<thead>
		<tr>
		<th field="id" width="50">编号</th>
		<th field="planDate" width="100" editor="{type:'datebox',options:{required:true}}">日期</th>
		<th field="planItem" width="100" editor="{type:'validatebox',options:{required:true}}">计划内容</th>
		<th field="exeAffect" width="100" editor="{type:'validatebox',options:{required:true}}">执行效果</th>
		</tr>
	</thead>
</table>
<div id="tb">
	<c:if test="${param.show != 'true' }">
	<a href="javascript: void(0)" onclick="javascript: $('#dg').edatagrid('addRow')" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
	<a href="javascript: void(0)" onclick="javascript: $('#dg').edatagrid('destroyRow')" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	<a href="javascript: void(0)" onclick="javascript: $('#dg').edatagrid('saveRow');$('#dg').edatagrid('reload')" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
	<a href="javascript: void(0)" onclick="javascript: $('#dg').edatagrid('cancelRow')" class="easyui-linkbutton" iconCls="icon-undo" plain="true">撤销行</a>
	<a href="javascript: void(0)" onclick="javascript: updateSaleChanceDevResult(2)" class="easyui-linkbutton" iconCls="icon-kfcg" plain="true">开发成功</a>
	<a href="javascript: void(0)" onclick="javascript: updateSaleChanceDevResult(3)" class="easyui-linkbutton" iconCls="icon-zzkf" plain="true">终止开发</a>
	</c:if>
</div>
</body>
</html>