<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.HospitalCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hospital view</title>
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;

	/* background-size: 100%; */
}
</style>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HospitalDTO"
			scope="request"></jsp:useBean>

		<main>
		<form action="<%=ORSView.HOSPITAL_CTL%>" method="post">

			<div class="row pt-3 pb-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

							if (dto != null && dto.getId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Hospital</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add
								Hospital</h3>
							<%
								}
							%>
							<!--Body-->
							<div>


								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>
							<div class="md-form">
							
							<span class="pl-sm-5"><b>Name</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-list grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="name"
											placeholder="Enter code"
											value="<%=DataUtility.getStringData(dto.getName())%>">

									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("name", request)%></font></br>
								<span class="pl-sm-5"><b>Disease</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-book grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="disease"
											placeholder="Enter disease"
											value="<%=DataUtility.getStringData(dto.getDisease())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("disease", request)%></font></br>
								<span class="pl-sm-5"><b>DocterName</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-book grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text"  name="docName"
											class="form-control" placeholder="docName"
											
											value="<%=DataUtility.getStringData(dto.getDocName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("docName", request)%></font></br>


								<span class="pl-sm-5"><b>Bill</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-list grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text"  name="bill"
											class="form-control" placeholder="bill"
											
											value="<%=dto.getBill()==0 ? "":dto.getBill()%>">

									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bill", request)%></font></br>
								</div>
								</br>
								<%
								if (dto != null && dto.getId() > 0) {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=HospitalCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=HospitalCtl.OP_CANCEL%>">
								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=HospitalCtl.OP_SAVE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=HospitalCtl.OP_RESET%>">
								</div>
								<%
									}
								%>
							</div>
						</div>
					</div>
					<div class="col-md-4 mb-4"></div>
				</div>
		</form>
		</main>


	</div>

</body>
<%@include file="FooterView.jsp"%>
</html>