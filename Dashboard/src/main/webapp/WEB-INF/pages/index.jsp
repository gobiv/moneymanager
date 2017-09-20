<html>
<head>
<head>
<title>Chubb - Dashboard</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-paged-scroll.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<!-- Theme style -->
<link href="${pageContext.request.contextPath}/resources/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap/js/jsapi.js"></script>

<style>
input {
	display: block;
	visibility: hidden;
	width: 0;
	height: 0;
}
 #back-to-top {
    position: fixed;
    bottom: 40px;
    right: 40px;
    z-index: 9999;
    width: 32px;
    height: 32px;
    text-align: center;
    line-height: 30px;
    background: #f48282;
    color: #444;
    cursor: pointer;
    border: 0;
    border-radius: 2px;
    text-decoration: none;
    transition: opacity 0.2s ease-out;
    opacity: 0;
}
#back-to-top:hover {
    background: #e9ebec;
}
#back-to-top.show {
    opacity: 1;
}
#content {
    height: 2000px;
}
.loadedcontent {min-height: 1200px; }

 
</style>
<script type="text/javascript">
/* Charting Section Starting*/

google.load("visualization", "1", {packages:["gauge","corechart"]});

function drawChartAndGauge(data){
     drawChart(data);
     drawGauge();
 }
function drawChart(data) {
    var obj = JSON.parse(data);

  	// set Area chart options
    var options1 = {
        title: 'Company Performance',
        hAxis: {title: 'Month',  titleTextStyle: {color: '#333'}},		          
        vAxis: {minValue: 0}
     };

     // create the data table for area chart      
     var data1 = new google.visualization.DataTable();
	 data1.addColumn('string', 'Month');
	 data1.addColumn('number', 'Billed_Hours');
	 data1.addColumn('number', 'Non_Billed_Hours');
	 var len = obj.root[1].AreaChart.length;
	 for(i =0; i<len ; i++){
		month = obj.root[1].AreaChart[i].Month_year;
		billedhr = obj.root[1].AreaChart[i].Billed_Hours;
		non_billedhr = obj.root[1].AreaChart[i].Non_billed_hours;
	    data1.addRows([
	        [month,billedhr,non_billedhr]
	      ]);
	 }

	 // Set pie chart options
        var options2 = {'title':'Pie Chart',
                       'width':400,
                       'height':300};
            
     // Create the data table for pie chart
     var data2 = new google.visualization.DataTable();
     data2.addColumn('string', 'Technology');
     data2.addColumn('number', 'memberCount');
	 var len = obj.root[2].PieChart.length;
	 for(i =0; i<len ; i++){
		java = obj.root[2].PieChart[i].Java;
		dotNet = obj.root[2].PieChart[i].DotNet;
		mainframe = obj.root[2].PieChart[i].Mainframe;
		testing = obj.root[2].PieChart[i].Testing;
        data2.addRows([
              ['Java', java],
              ['DotNet', dotNet],
              ['Mainframe', mainframe],
              ['Testing', testing]
         ]);
	 }

	 var chart1 = new google.visualization.AreaChart(document.getElementById('chart_div'));
     chart1.draw(data1, options1);
     var chart2 = new google.visualization.PieChart(document.getElementById('chart_div2'));
     chart2.draw(data2, options2);
				
   }
   function drawGauge() {
       var data = google.visualization.arrayToDataTable([
          ['Label', 'Value'],
          ['Billable', 100],
          ['Non-Billable', 80]
              ]);
        var options = {
          width: 400, height: 120,
          redFrom: 90, redTo: 100,
          yellowFrom:75, yellowTo: 90,
          minorTicks: 5        };
        var chart = new google.visualization.Gauge(document.getElementById('chart_div4'));
        chart.draw(data, options);
    }
    /* Charting Section End*/
    function convert2JSON() {
      	//$('#leftSection').html('');
      console.log("Convert2JSON called");
		$('#result').html('');
        $("#form2").ajaxForm({
        	success:function(data) {
            	if(data != -1){ 
	        		// drawLeft(data);
	        		//drawChartAndGauge(data);
	        		 var p = JSON.parse(data)
	        		 
	        		 if(p.root[4].message ==='upload'){
	        			 console.log("Clearning the HTML Area");
	        			 $('ul.timeline').html('');
	        		 }
	        			 
	        	  	getDetails(1,10,true);	
            	}else{
            		var txt1="";
            		txt1="<div class='small-box bg-red'>";
            		$('#result').html('Please upload a file using upload option');
                }	        	    	        	          
        	},
       		dataType:"text"
        }).submit();
    }
    function drawLeft(data){						
		var obj = JSON.parse(data);
		var len = obj.root[0].Left.length;
		var count = 1;
		for(i =0; i<len ; i++){
			category = obj.root[0].Left[i].Category;
			desc = obj.root[0].Left[i].Description;
			status = obj.root[0].Left[i].Status;
			date = obj.root[0].Left[i].Date;
			/*leftSection*/
    		var txt1="";
    		if(status === 'Approved'){
    			txt1="<div class='small-box bg-green'>"; 	 
    		}else if (status === 'In Progress'){
    			txt1="<div class='small-box bg-yellow'>";
    		}else if(status ==='Rejected'){
    			txt1="<div class='small-box bg-red'>";
    		}else if(status ==='Submitted'){
    			txt1="<div class='small-box bg-aqua'>";
    		}
    		var txt2= "<div class='inner'>";
    		var txt3= "<h3>"+category+"</h3>";
    		var txt4= "<p>"+status+"</p>";
    		var txt5 = "</div>";
    		var txt6 = "<div class='icon'>";
    		var txt7="<span class='glyphicon glyphicon-home'></span>";
    		var txt8 ="</div>";
    		var txt9= "<a href='#' class='small-box-footer'>More info</a>";
    		var txt10="</div>";

    		jQuery('<div/>', {
    			id: 'leftSection'+count,
    			html: txt1+txt2+txt3+txt4+txt5+txt6+txt7+txt8+txt9+txt10
    		}).appendTo('#leftSection');
    		count = count+1;
		}
	}

		          
		          
		          function getDetails(startVal,endVal,init) {
		        	  console.log("Get Details Called");
		              $.ajax({
		                  url : 'getDetails.html',
		                  data: { start: startVal, end : endVal,init:init },
		                  success : function(data) {
		                	//   $('ul.timeline').html("");
		                	
		                
		                	if(data ==="No records found")
		                		return false;
		                	
		                      var parsed = $.parseJSON(data)
		                     // console.log("Parsed Value: "+parsed);
		                      //Setting next start and end values
		                      var cList = $('ul.timeline');
		                      $.each(parsed, function(i, obj) {
		                    	  
		                    	  
		                    	  var txt1 = "<p>Text.</p>";              // Create text with HTML
		                    	  var txt2 = $("<p></p>").text("Text.");  // Create text with jQuery
		                    	  var txt3 = document.createElement("p");
		                    	 	  txt3.innerHTML = "Text.";               // Create text with DOM
		                    	 $("body").append(txt1, txt2, txt3);     // Append new elements
		                    	  
		                    	  
		                    	  
		                    	  
		                    	  var txt1 ="<li class='time-label'><span class='bg-red'>"+obj.uploadedDateStr+"</span></li>";
		                    	  //var txt1 =$("<li></li>").text(obj.uploadedDateStr).style("time-label").html("<span class='bg-red'></span>");
		                    	  
		                    	  var txt2 ="<li><span class='glyphicon glyphicon-home'></span>";
	                    		  var txt3="<div class='panel panel-default timeline-item'>";
		                    	  var txt4="<div class='panel-heading'>"+obj.itemHeader+"</div>";
		                    	  var txt5= "<div class='panel-body'>"+obj.description+"</div></div></li>";
		                    	  
		                    	  var html =  txt2+txt3+txt4+txt5;
		                    	  if (typeof obj.start === "undefined") {
		                    	  jQuery('<li/>', {		        					    
		        					    html: txt1
		        					}).appendTo(cList);


		                    	  jQuery('<li/>', {		        					    
		        					    html: html
		        					}).appendTo(cList);
		                    	  } 
		                    	  
		                    	  if(typeof obj.start != "undefined"){
		                    		  console.log("Next start value to fetch: "+obj.start);
				                      console.log("Next end value to fetch: "+obj.end); 
				                      $("input[id=start]").val(obj.start+"")
				                      $("input[id=end]").val(obj.end+"")
		                    	  }
		                    	  
		                    	  if (typeof obj.numberOfPages != "undefined") {
		                    		  console.log("Number of Pages: "+ obj.numberOfPages);
		                    		  $("input[id=numberOfPages]").val(obj.numberOfPages+"");
		                    	  }
		                    	});
		                  }
		              });
		          }
		          
		           
      </script>
     
</head>
<body  >
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header" align="middle">
				<a class="navbar-brand" href="#"><img src="${pageContext.request.contextPath}/resources/img/Chubb2.png"
					alt="Chubb Logo"></a>
			</div>
			<form id="form2" method="post" action="cont/upload" enctype="multipart/form-data">
			<div>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="#"><span
							class="glyphicon glyphicon-home"></span> Home</a></li>
					<li><a href="#"><span class="glyphicon glyphicon-upload"></span>
							
  					<!-- File input -->    
  					<label for="file">Upload</label> <input type="file" id="file"
							name="file" class="hidden" onchange="convert2JSON()" /> </a></li>
					
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Welcome <%= request.getUserPrincipal().getName() %> </a></li>
					<li><a href="${pageContext.request.contextPath}/logoff" > <span class="glyphicon glyphicon-log-out"/>Logout</a></li>
					
				</ul>
			</div>			
			</form>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3" style="min-height: 100%">
				<!-- <INPUT TYPE="file" ID="XMLFilePath" value="">
				<button onclick="convert2JSON()">convert2JSON</button> -->
				<div id="leftSection"></div>
			</div>
			<div class="col-md-6">
			  <div id="result"></div>
				<ul class="timeline">
				</ul>
				<a href="#" id="back-to-top" title="Back to top">&uarr;</a>
			</div>
			<div class="col-md-3">
				<div id="chart_div4"></div>
				<div id="chart_div"></div>
				<div id="chart_div2"></div>
				<div id="chart_div3"></div>
			</div>
		</div>
	</div>
	<input type="hidden" id="start" value=""/>
	<input type="hidden" id="end" value=""/>
	<input type="hidden" id="numberOfPages" value=""/>
	 <script type="text/javascript">
	//<![CDATA[ 
	 $(window).load(function(){
	 if ($('#back-to-top').length) {
	     var scrollTrigger = 100, // px
	         backToTop = function () {
	             var scrollTop = $(window).scrollTop();
	             if (scrollTop > scrollTrigger) {
	                 $('#back-to-top').addClass('show');
	             } else {
	                 $('#back-to-top').removeClass('show');
	             }
	         };
	     backToTop();
	     $(window).on('scroll', function () {
	         backToTop();
	     });
	     $('#back-to-top').on('click', function (e) {
	         e.preventDefault();
	         $('html,body').animate({
	             scrollTop: 0
	         }, 700);
	     });
	 }
	 });//]]>   
	 
	 
      $(document).ready(function (e) {
    	  	//getDetails(1,10,true);
    		convert2JSON();
      });
         var p;
       $(window).paged_scroll({
          handleScroll:function (page,container,doneCallback) {
              setTimeout(function () {
                 // console.log("Page is:", page);
                  getDetails($("#start").val(),$("#end").val(),false)
//                  doneCallback();
                  //console.log("$$$$ "+$("#numberOfPages").val())
                  
              }, 1000);

              return true;
          },
          // Uncomment to enable infinite scroll
          //pagesToScroll : 5,
          triggerFromBottom:'5%',
          loader:'<div class="loader"></div>',
          pagesToScroll:$("#numberOfPages").val(),
          targetElement:$("ul.timeline"),
          debug:false
      });  
      
</script>
</body>
</html>

