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
    
   
    
    <script src='//cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js'></script>
    
    <link href="${contextPath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    
    <link href='${contextPath}/packages/core/main.css' rel='stylesheet' />
	<link href='${contextPath}/packages/daygrid/main.css' rel='stylesheet' />
	<script src='${contextPath}/packages/core/main.js'></script>
	<script src='${contextPath}/packages/interaction/main.js'></script>
	<script src='${contextPath}/packages/daygrid/main.js'></script>
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
    
    
     
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	
	<script>
	var calendar;
	
  document.addEventListener('DOMContentLoaded', function() {
	  $("#modal-btn-yes").on("click", function(){
		    removeEventActual(parseInt($('body').find('[id*=removeID]').html()));
		    $("#confirm-modal").modal('hide');
		  });
		  
		  $("#modal-btn-no").on("click", function(){
		    $("#confirm-modal").modal('hide');
		  });
	  
	  
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
      defaultDate: new Date,
      disableDragging: true,
      editable: false,
      eventLimit: true, // allow "more" link when too many events
      header: {
      	center: 'addEventButton logoutBtn'
      },
      customButtons: {
        addEventButton: {
          text: 'add event...',
          click: function() {
          	$("#eventModal").find("#eventDate").val("");
          	$("#eventModal").find("#eventTitle").val("");
          	$("#eventModal").find("[id*=eventUsers]").val("");
          	$("#eventModal").find("[id*=eventDescription]").val("");
          	
          	$("#eventModal").find("[id*=eventUsers]").parent().removeClass("has-error");
          	$("#datetimepicker1").removeClass("has-error");
          	$("#eventModal").find("#eventTitle").parent().removeClass("has-error");
          
          	$("#eventModal").modal();
          }
        },
        logoutBtn: {
      		text: "logout",
      		click: function() {
      			document.forms['logoutForm'].submit();
      		}
      	  }
      },
      eventRender: function(info) {   
    	 var $el = $(info.el);
    	 
	     $el.popover({
		     title: info.event.title,
		     content: info.event.extendedProps.description,
		     trigger: 'hover',
		     placement: 'top',
		     container: 'body'
		  });
	     
	     if (info.event.extendedProps.type != 0){
	    	 $el.css('background-color', 'red');
	     }
	     
	     if (info.event.extendedProps.single == true){
	    	 $el.append('<a style="color: black" onclick="javascript:removeEvent('+info.event.id+')"><div class="glyphicon glyphicon-trash"></div></a>');
	     }
	     
	     console.log(info);
	     
	     $el.attr('event-id', (typeof info.event.id == 'undefined' ? '' : info.event.id));

	  },
			
      events: currentEvents
    });

    calendar.render();
    
  });
  
    function removeEventActual(eventId){
    	var allEvents = calendar.getEvents();
  		calendar.removeAllEvents();
  		
  		for (var i = 0; i < allEvents.length; i++){
  			if (allEvents[i].id != eventId)
  				calendar.addEvent(allEvents[i]);
  			else{
  				$('.popover').hide();
  				$("[event-id *= "+eventId+"]").popover('destroy');
  				
  				
  				$.ajax({
  				    type: "GET",
  				    contentType: "application/json",
  				    url: "/removeEvent",
  				    data: {id: eventId},
  				    dataType: 'json',
  				    timeout: 600000,
  				    success: function (data) { 
  				    },
  				     error: function (e) {
  				    	 if (e.responseText = "Event removed"){
  				    		 $("[id*=emptyAlert]").hide();
  				    		 $("[id*=eventRemoved]").show();
  				    		 setTimeout(function(){$("[id*=eventRemoved]").hide(); $("[id*=emptyAlert]").show();},2500);
  				    	 }else{
  				     		 alert(e.responseText);
  				    	 }
  				     }
  					});
  			}	
  		}
    }
  
  	function removeEvent(eventId){
  		$('[id *=removeID]').remove();
  		$('body').append("<div id='#removeID'>"+ eventId +"</div>");
  		$("#confirm-modal").modal('show');
  	}
  
    function doAddEvent(){
		var dateStr = $("#eventModal").find("#eventDate").val();
        var title = $("#eventModal").find("#eventTitle").val();
        var users = $("[id*=eventUsers]").val();
        var description = $("[id*=eventDescription]").val();
        var type = $("[id*=eventType]").val();
          	
        var isError = false;
          	
        if (dateStr == ""){
        	$("#datetimepicker1").addClass("has-error");
        	isError = true;
        }
        if (title == ""){
        	$("#eventModal").find("#eventTitle").parent().addClass("has-error");
        	isError = true;
        }
        
        var tmpDate = new Date(dateStr);
        for (var i = 0; i < calendar.getEvents().length; i++){
        	var eventDate = new Date(calendar.getEvents()[i].start);
        	if (+tmpDate === +eventDate){
        		alert("Event already exist at given time");
        		$("#datetimepicker1").addClass("has-error");
            	isError = true;
        	}
        }
        
        //Check if users exist
        if (users != ""){
	        $.ajax({
			    type: "GET",
			    contentType: "application/json",
			    url: "/getAllUsers?usernames="+users,
			    dataType: 'json',
			    async: false,
			    success: function (e) { 
			    	if (e.responseText == "not found"){
			    		$("[id*=eventUsers]").parent().addClass("has-error");
			    		$("[id*=eventUsers]").val("One or more users not found");
			    		isError = true;
			    	}
			    },
			     error: function (e) {
			    	 if (e.responseText == "not found"){
				    	$("[id*=eventUsers]").parent().addClass("has-error");
				    	$("[id*=eventUsers]").val("One or more users not found");
				    	isError = true;
				    }
			     }
			});
        }
        //-------------------
        
       if (!isError){
       	$("#eventModal").modal('toggle');
       	 var single = true;
       	 if (users != ""){
       		 single = false;
       	 }
       	 var date = new Date(dateStr); // will be in local time

         $.ajax({
		    type: "GET",
		    contentType: "application/json",
		    url: "/addEvent",
		    data: {title:title, start: dateStr, users:users, eventDescription:description, type:type},
		    dataType: 'json',
		    timeout: 600000,
		    success: function (data) {
		    	var id = parseInt(data);
	    		var jsonData = {id: id, title: title, start: date, description: description, type: type, single: single};
	           	calendar.addEvent(jsonData);
	    		 
	    		$("[id*=emptyAlert]").hide();
	    		$("[id*=eventAdded]").show();
	    		setTimeout(function(){$("[id*=eventAdded]").hide(); $("[id*=emptyAlert]").show();},2500);
		    },
		     error: function (e) {
		    	 console.log(e.responseText);
		    	 if (e.responseCode == '200'){
		    		 var id = parseInt(e.responseText);
		    		 var jsonData = {id: id, title: title, start: date, description: description, type: type, single: single};
		           	 calendar.addEvent(jsonData);
		    		 
		    		 $("[id*=emptyAlert]").hide();
		    		 $("[id*=eventAdded]").show();
		    		 setTimeout(function(){$("[id*=eventAdded]").hide(); $("[id*=emptyAlert]").show();},2500);
		    	 }else{
		     		 alert(e.responseText);
		    	 }
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

<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="confirm-modal">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Remove event?</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="modal-btn-yes">Yes</button>
        <button type="button" class="btn btn-primary" id="modal-btn-no">No</button>
      </div>
    </div>
  </div>
</div>

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
         Event Title:
         <div class="row">
         	 <div class='col-sm-6'><input id="eventTitle" type='text' class="form-control" /></div>
         </div>
         Event type:
         <div class="row">
         	 <div class='col-sm-6'>
	         	 <select name="cars" id="eventType" class="form-control">
				  <option value="0">Regular</option>
				  <option value="1">Special</option>
				</select>
         	 </div>
         </div>
         Description for event:
         <div class="row">
         	 <div class='col-sm-6'><input id="eventDescription" type='text' class="form-control" /></div>
         </div>
         People invited (usernames seperated by a comma):
         <div class="row">
         	 <div class='col-sm-6'><input id="eventUsers" type='text' class="form-control" /></div>
         </div>
       </div>

      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="javascript:doAddEvent()">Create</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div id="eventAdded" class="alert alert-success " role="alert" style="display: none;">
  Event succesfully added to calendar
</div>
<div id="eventRemoved" class="alert alert-success " role="alert" style="display: none;">
  Event succesfully removed from calendar
</div>
<div id="emptyAlert" class="alert " role="alert" style="">
  
</div>

  <div id='calendar'></div>

<form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

<div style='clear:both'></div>

</body>
</html>

