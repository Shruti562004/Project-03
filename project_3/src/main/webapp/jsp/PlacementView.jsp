<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.PlacementCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Placement View</title>

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
	</div>

	<jsp:useBean id="dto"
		class="in.co.rays.project_3.dto.PlacementDTO"
		scope="request">
	</jsp:useBean>

	<%
		long id = DataUtility.getLong(request.getParameter("id"));
	%>

	<main>

	<form action="<%=ORSView.PLACEMENT_CTL%>" method="post">

		<input type="hidden" name="id" value="<%=dto.getId()%>">

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
							if (dto.getId() != null && dto.getId() > 0) {
						%>

						<h3 class="text-center text-primary">
							Update Placement
						</h3>

						<%
							} else {
						%>

						<h3 class="text-center text-primary">
							Add Placement
						</h3>

						<%
							}
						%>

						<hr>

						<!-- Success Message -->

						<%
							if (!ServletUtility.getSuccessMessage(request).equals("")) {
						%>

						<div class="alert alert-success alert-dismissible">

							<button type="button" class="close"
								data-dismiss="alert">&times;</button>

							<%=ServletUtility.getSuccessMessage(request)%>

						</div>

						<%
							}
						%>

						<!-- Error Message -->

						<%
							if (!ServletUtility.getErrorMessage(request).equals("")) {
						%>

						<div class="alert alert-danger alert-dismissible">

							<button type="button" class="close"
								data-dismiss="alert">&times;</button>

							<%=ServletUtility.getErrorMessage(request)%>

						</div>

						<%
							}
						%>

						<!-- Name -->

						<div class="form-group">

							<label>
								<b>Name</b>
								<span style="color: red;">*</span>
							</label>

							<div class="input-group">

								<div class="input-group-prepend">

									<div class="input-group-text">

										<i class="fa fa-user"></i>

									</div>

								</div>

								<input type="text"
									class="form-control"
									name="name"
									placeholder="Enter Name"
									value="<%=DataUtility.getStringData(dto.getName())%>">

							</div>

							<font color="red">

								<%=ServletUtility.getErrorMessage("name", request)%>

							</font>

						</div>

						<!-- Coordinates -->

						<div class="form-group">

							<label>
								<b>Coordinates</b>
								<span style="color: red;">*</span>
							</label>

							<div class="input-group">

								<div class="input-group-prepend">

									<div class="input-group-text">

										<i class="fa fa-map-marker"></i>

									</div>

								</div>

								<input type="text"
									class="form-control"
									name="coordinates"
									placeholder="Enter Coordinates"
									value="<%=DataUtility.getStringData(dto.getCoordinates())%>">

							</div>

							<font color="red">

								<%=ServletUtility.getErrorMessage("coordinates", request)%>

							</font>

						</div>

						<!-- Scale -->

						<div class="form-group">

							<label>
								<b>Scale</b>
								<span style="color: red;">*</span>
							</label>

							<div class="input-group">

								<div class="input-group-prepend">

									<div class="input-group-text">

										<i class="fa fa-expand"></i>

									</div>

								</div>

								<input type="text"
									class="form-control"
									name="scale"
									placeholder="Enter Scale"
									value="<%=DataUtility.getStringData(dto.getScale())%>">

							</div>

							<font color="red">

								<%=ServletUtility.getErrorMessage("scale", request)%>

							</font>

						</div>

						<!-- Rotation -->

						<div class="form-group">

							<label>
								<b>Rotation</b>
								<span style="color: red;">*</span>
							</label>

							<div class="input-group">

								<div class="input-group-prepend">

									<div class="input-group-text">

										<i class="fa fa-book"></i>

									</div>

								</div>

								<input type="text"
									class="form-control"
									name="rotation"
									placeholder="Enter Rotation"
									value="<%=DataUtility.getStringData(dto.getRotation())%>">

							</div>

							<font color="red">

								<%=ServletUtility.getErrorMessage("rotation", request)%>

							</font>

						</div>

						<!-- Buttons -->

						<div class="text-center">

							<%
							if (dto != null && dto.getId() > 0) {
							%>

							<input type="submit"
								name="operation"
								class="btn btn-success btn-md"
								value="<%=PlacementCtl.OP_UPDATE%>">

							<input type="submit"
								name="operation"
								class="btn btn-warning btn-md"
								value="<%=PlacementCtl.OP_CANCEL%>">

							<%
								} else {
							%>

							<input type="submit"
								name="operation"
								class="btn btn-success btn-md"
								value="<%=PlacementCtl.OP_SAVE%>">

							<input type="submit"
								name="operation"
								class="btn btn-warning btn-md"
								value="<%=PlacementCtl.OP_RESET%>">

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

	</main>

</body>

<%@include file="FooterView.jsp"%>

</html>