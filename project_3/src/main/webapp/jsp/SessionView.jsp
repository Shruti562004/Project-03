<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.SessionCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.SessionDTO" scope="request"></jsp:useBean>

<%
	long id = (dto.getId() == null) ? 0 : dto.getId();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Session View</title>

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>

	<form action="<%=ORSView.SESSION_CTL%>" method="post">

		<input type="hidden" name="id" value="<%=id%>">

		<input type="hidden" name="createdBy"
			value="<%=dto.getCreatedBy()%>">

		<input type="hidden" name="modifiedBy"
			value="<%=dto.getModifiedBy()%>">

		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

		<div class="row pt-3 pb-3">

			<div class="col-md-4"></div>

			<div class="col-md-4">

				<div class="card">

					<div class="card-body">

						<%
						if (dto != null && dto.getId() > 0) {
						%>

						<h3 class="text-center text-primary">Update Session</h3>

						<%
							} else {
						%>

						<h3 class="text-center text-primary">Add Session</h3>

						<%
							}
						%>

						<h4 align="center">

							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>

							<div class="alert alert-success">
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>

							<%
								}
							%>

						</h4>

						<h4 align="center">

							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>

							<div class="alert alert-danger">
								<%=ServletUtility.getErrorMessage(request)%>
							</div>

							<%
								}
							%>

						</h4>

						<!-- CODE -->

						<label><b>Code</b></label>

						<input type="text" class="form-control" name="code"
							placeholder="Enter Code"
							value="<%=DataUtility.getStringData(dto.getCode())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("code", request)%>
						</font>

						<br>

						<!-- NAME -->

						<label><b>Name</b></label>

						<input type="text" class="form-control" name="name"
							placeholder="Enter Name"
							value="<%=DataUtility.getStringData(dto.getName())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("name", request)%>
						</font>

						<br>

						<!-- LOGIN TIME -->

						<label><b>Login Time</b></label>

						<input type="text" id="datepicker2" name="loginTime"
							class="form-control" 
							value="<%=DataUtility.getDateString(dto.getLoginTime())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("loginTime", request)%>
						</font>

						<br>

						<!-- STATUS -->

						<label><b>Status</b></label>

						<textarea name="status" class="form-control" rows="5"><%=DataUtility.getStringData(dto.getStatus())%></textarea>

						<font color="red">
							<%=ServletUtility.getErrorMessage("status", request)%>
						</font>

						<br>

						<div class="text-center">

							<%
							if (dto != null && dto.getId() > 0) {
							%>

							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=SessionCtl.OP_UPDATE%>">

							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=SessionCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit" name="operation"
								class="btn btn-success"
								value="<%=SessionCtl.OP_SAVE%>">

							<input type="submit" name="operation"
								class="btn btn-warning"
								value="<%=SessionCtl.OP_RESET%>">

							<%
								}
							%>

						</div>

					</div>

				</div>

			</div>

			<div class="col-md-4"></div>

		</div>

	</form>

</body>

<%@include file="FooterView.jsp"%>

</html>