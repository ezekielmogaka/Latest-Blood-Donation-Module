<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="../mainMenu.jsp"%>
<%@ include file="../includes/js_css.jsp" %>
<!-- include of header.jsp not working -->
<br/>
<openmrs:require privilege="Edit Encounters" otherwise="/login.htm" redirect="/module/blooddonationmanager/queue.form" />

<script type="text/javascript">

	testNo = 0;
	var validateRescheduleDateResult = false;

	function getContextPath() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}

	// get all tests
	function getTests(currentPage){
		var date = $("#date").val();
		var phrase = $("#phrase").val();
		$.ajax({
			type : "GET",
			url : getContextPath() + "/module/donationmanager/searchTest.form",
			data : ({
				date			: date,
				phrase			: phrase,
				currentPage		: currentPage
			}),
			success : function(data) {
				$("#tests").html(data);
				if(testNo>0){
					//				$("#myTable").tablesorter({sortList: [[0,0]]});
					tb_init("a.thickbox"); // init to show thickbox
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	// accept a test
	function acceptTest(orderId) {
		var scrollTop = $("#acceptBox_" + orderId).scrollTop();
		$.ajax({
			type : "POST",
			url : getContextPath() + "/module/blooddonationmanager/searchTest.form",
			data : ({
				orderId : orderId,
				//date	: $("#date").val()
			}),
			success : function(data) {
				//testId = parseInt(data);		
				//$("#acceptBox_" + orderId).scrollTop(scrollTop);
				//$("#acceptBox_" + orderId).html("<b>Accepted</b>");
				//$("#rescheduleBox_" + orderId).html("Reschedule");
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	// unaccept a test
	function unacceptTest(testId, orderId) {
		$.ajax({
			type : "GET",
			url : getContextPath() + "/module/radiology/ajax/unacceptTest.htm",
			data : ({
				testId : testId
			}),
			success : function(data) {
				if (data == 'success') {
					$("#acceptBox_" + orderId).html(
							"<a href='javascript:acceptTest(" + orderId
							+ ");'>Accept</a>");
				} else {
					alert(data);
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert("ERROR " + xhr);
			}
		});
	}

	// reschedule a test
	function rescheduleTest(orderId, rescheduledDate) {
		validateRescheduleDate(orderId, rescheduledDate);
	}

	// validate reschedule date
	function validateRescheduleDate(orderId, rescheduledDate){
		validateRescheduleDateResult = false;
		$.ajax({
			type : "GET",
			url : getContextPath() + "/module/radiology/ajax/validateRescheduleDate.htm",
			data : ({
				rescheduleDate : rescheduledDate
			}),
			success : function(data) {

				if (data == 'success') {
					$.ajax({
						type : "POST",
						url : getContextPath() + "/module/radiology/rescheduleTest.form",
						data : ({
							orderId : orderId,
							rescheduledDate : rescheduledDate
						}),
						success : function(data) {
							if (data == 'success') {
								getTests();
							} else {
								alert(data);
							}
						},
						error : function(xhr, ajaxOptions, thrownError) {
							alert("ERROR " + xhr);
						}
					});
					tb_remove();
				} else {
					alert('Invalid reschedule date! It must be after the current date');
				}
			},
			error : function(xhr, ajaxOptions, thrownError) {

			}
		});
	}
</script>


<div class="container" style="padding: 20px;">
	<div class="boxHeader" style="padding:20px;">
	<div class="box">
		Date:
		<input id="date" value="${currentDate}" onFocus="showCalendar(this);" style="text-align:right;"/>
		Patient ID/Name:
		<input id="phrase"/>
		<input type="button" value="Get Requests" onClick="getRequests(1);"/>
		<%--get Request function--%>
		<input type="button" value="Reset" onClick="alert('Reset');"/>
	</div>

<div id="tests">
</div>

	<div class="table-responsive">
		<table class="table">
			<caption style="text-align: center;"><h2>Blood Request Queue</h2></caption>
			<thead>
			<tr>
				<th>Patient Id</th>
				<th>Blood Type</th>
				<th>Rhesus Factor</th>
				<th>Request Date</th>
				<th>Status</th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>29/10/2013</td>
				<td>Pending</td>
				<td><button type="submit" class="btn btn-primary">Collected</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Edit</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Delete</button>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>10/11/2013</td>
				<td>Collected</td>
				<td><button type="submit" class="btn btn-primary">Collected</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Edit</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Delete</button>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>19/11/2013</td>
				<td>Collected</td>
				<td><button type="submit" class="btn btn-primary">Collected</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Edit</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Delete</button>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td>21/11/2013</td>
				<td>Pending</td>
				<td><button type="submit" class="btn btn-primary">Collected</button>&nbsp;&nbsp;
					
					<button type="submit" class="btn btn-primary">Edit</button>&nbsp;&nbsp;
					<button type="submit" class="btn btn-primary">Delete</button>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</div>
</div>


<%@ include file="/WEB-INF/template/footer.jsp" %>  