<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="refresh" content="text/html; charset=ISO-8859-1">
<link href="<%= request.getContextPath() %>/jsp/style.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="jsp/validation.js" type="text/javascript"></script>
<script type="text/javascript">
function validateCreateAssignment(form){
	var task = form.Task.value;
	var title = form.Title.value;
	var userID = form.UserID.value;
	var errors = [];
	if (!checkLength(task)) {
		errors.push("You must enter a task description");
	}
	if (task.length>200) {
		errors.push("You can't have more than 200 characters");
	}
	if (!checkLength(title)) {
		errors.push("You must enter in a title");
	}
	if (title.length>20) {
		errors.push("You can't have more than 20 characters");
	}

	if (userID.length==null) {

		errors.push("You must enter in a UserID");
	}
	if (errors.length > 0) {
		reportErrors(errors);
		return false;
	}
	return true;
}

function validateUpdateAssignment(form){
	var aid = form.AssignmentID.value;
	var errors = [];
	if (!checkLength(aid)) {
		errors.push("You must put in assignment ID (AID)");
	}
	
	if (errors.length > 0) {
		reportErrors(errors);
		return false;
	}
	return true;
}
 
var href = document.location.href;
if(href.indexOf("?param=refresh") != -1)
{
     href = href.substring(0,href.indexOf("?param=refresh"));  
    /* the line above strips off the parameter so refresh only occurs
        once otherwise the page would refresh over and over */
     document.location = href;
}
 
 

</script>
<title>Project Results</title>
</head>
<body onunload="">
  	<c:if test="${not loginResult}">
	    <p>Login Unsuccessful: ${Message}</p>
	    
  	</c:if>
	<c:if test="${loginResult}">
		<div>
			<h1 style="text-align:center;color:#FFF;font-family:Tahoma, Geneva, sans-serif;font-size:315%;">Title:${ProjectName}</h1>
			<h1 style="text-align:center;color:#FFF;font-family:Tahoma, Geneva, sans-serif;font-size:315%;">ID: ${ProjectID} </h1>
		</div>
		<button type="button" name="back" class="goBackButton" onclick="history.back()" style="
    position:relative;
    top:50%; 
    left:45%;" >Go back to project page</button>
		<h3 style="text-align:center;color:#FFF;font-family:Tahoma, Geneva, sans-serif;font-size:200%;">Assignment List</h3>
		
		<table class="CSSTableGenerator">
    	<thead>
      	<tr>
	        <th>Aid</th>
        	<th>Title</th>
        	<th>Task</th>
        	<th>ID of User Responsible</th>
       </tr>
    	</thead>
    	<tbody>
    	
    	
    	<c:forEach items="${assignmentList}" var="item">
			      <tr>
	        	<td>${item.assignmentID}</td>
        		<td>${item.title}</td>
        		<td>${item.task}</td>
        		<td> ${item.userID}</td>
        	
      		</tr>
      	</c:forEach>
    	
	
	    </tbody>
  	</table>
		
  	
  	<div class="divLeftA">	
  	<form action="/ClassCoor/MakeAssignment" method="get" class="elegant-aero">
  	<h3>Make a New Assignment</h3>
  	<label>
		<span>Title:</span> <input type="text" name="Title" placeholder="Title">
	</label>	
		<br/>
		<br/>
	<label>
		<span>Task:</span> <input type="text" name="Task" placeholder="Task"><br/>
	</label>
		<br/>
	<label>
		<span>ProjectID:</span> <input type="text" name="ProjID" value= "${ProjectID}" readonly><br/>
		<br/>
	</label>
	<label>
		<span>UserID who's responsible:</span> <input type="text" name="UserID" placeholder="UserID"><br/>
		<br/>
	</label>
		<input type="submit" value="Add Assignment!">
	</form>
	</div>
	
	
	<div class="divRightA">	
  	<form action="/ClassCoor/UpdateAssignment" method="get" class="elegant-aero" onsubmit="return validateUpdateAssignment(this);">
  	<h3>Update Assignment</h3>
  	<label>
		<span>AID for assignment you want to update: </span> <input type="text" name="AssignmentID" placeholder="AID">
	</label>	
		<br/>
		<br/>
	<label>
		<span>Update task to: </span> <input type="text" name="Task" maxlength="200" placeholder="Task"><br/>
	</label>
		<br/>
	<label>
		<span>ProjectID: </span> <input type="text" name="ProjID" value= "${ProjectID}" readonly><br/>
		<br/>
	</label>
	<label>
		<span>Your ID: </span> <input type="text" name="UserID" value= "${UserID}" placeholder="UserID" readonly><br/>
		<br/>
	</label>
		<input type="submit" value="Update">
	</form>
	</div>
		
		
		
		
	</c:if>

</body>
</html>