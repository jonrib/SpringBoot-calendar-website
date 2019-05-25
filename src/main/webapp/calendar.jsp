<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Your personal calendar</title>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js" type="text/javascript"></script>
    
    <script type="text/javascript" src="${contextPath}/resources/js/moment.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/bootstrap.min.js"></script>
     <script type="text/javascript" src="${contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
     
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link href="${contextPath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    
    <link href='${contextPath}/packages/core/main.css' rel='stylesheet' />
	<link href='${contextPath}/packages/daygrid/main.css' rel='stylesheet' />
	<script src='${contextPath}/packages/core/main.js'></script>
	<script src='${contextPath}/packages/interaction/main.js'></script>
	<script src='${contextPath}/packages/daygrid/main.js'></script>
	
	<script>
	var calendar;
	
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var currentEvents;
    
     $.ajax({
		    type: "GET",
		    contentType: "application/json",
		    url: "/getCalendar",
		    dataType: 'json',
		    async: false,
		    success: function (data) { 
		    	currentEvents = data.events;
		    },
		     error: function (e) {
		     	alert(e.responseText);
		     }
	});

    calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid' ],
      defaultDate: '2019-04-12',
      editable: true,
      eventLimit: true, // allow "more" link when too many events
      header: {
      	center: 'addEventButton'
      },
      customButtons: {
        addEventButton: {
          text: 'add event...',
          click: function() {
          	$("#eventModal").find("#eventDate").val("");
          	$("#eventModal").find("#eventTitle").val("");
          	$("#datetimepicker1").removeClass("has-error");
          	$("#eventModal").find("#eventTitle").removeClass("has-error");
          
          	$("#eventModal").modal();
          	
          }
        }
      },
      events: currentEvents
    });

    calendar.render();
    
  });
  
    function doAddEvent(){
		var dateStr = $("#eventModal").find("#eventDate").val();
        var title = $("#eventModal").find("#eventTitle").val();
          	
        var isError = false;
          	
        if (dateStr == ""){
        	$("#datetimepicker1").addClass("has-error");
        	isError = true;
        }
        if (title == ""){
        	$("#eventModal").find("#eventTitle").addClass("has-error");
        	isError = true;
        }
        
       if (!isError){
       	$("#eventModal").modal('toggle');
       	 var date = new Date(dateStr); // will be in local time
       	 
       	 var jsonData = {title: title, start: date};
       	 
       	 calendar.addEvent(jsonData);
         
         $.ajax({
		    type: "GET",
		    contentType: "application/json",
		    url: "/addEvent",
		    data: {title:title, start: dateStr},
		    dataType: 'json',
		    timeout: 600000,
		    success: function (data) { 
		    },
		     error: function (e) {
		     	alert(e.responseText);
		     }
			});
       }
	}

</script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }

</style>
</head>
<body>

<div class="modal" tabindex="-1" role="dialog" id="eventModal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">New event</h5>
      </div>
    
       
      <div class="modal-body">
        <div class="container">
   		 <div class="row">
     	   <div class='col-sm-6'>
            <div class="form-group">
                <div class='input-group date' id='datetimepicker1'>
                    <input id="eventDate" type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
         </div>
         <script type="text/javascript">
            $(function () {
                $('#datetimepicker1').datetimepicker({format: 'yyyy-mm-dd hh:ii'});
            });
         </script>
       </div>
       </div>
      
       <input id="eventTitle" type='text' class="form-control" />

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="javascript:doAddEvent()">Create</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

  <div id='calendar'></div>

<form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
<button class="btn btn-lg btn-primary btn-block" onclick="document.forms['logoutForm'].submit()">Logout</button>

<div style='clear:both'></div>
</div>
</body>
</html>

