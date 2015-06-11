<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="includes/js_css.jsp" %>

<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/moduleResources/blooddonationmanager/styles/common.css" />
<openmrs:require privilege="All Blood Bank" otherwise="/login.htm" redirect="/module/blooddonationmanager/main.form" />

<div style="border-bottom: 1px solid black;">
    <ul id="menu">
        <li class="first">
            <a href="home.form" style="font-size:large; font-weight:bold; text-decoration:none;">Blood Bank System</a>
        </li>
        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("home") %>'>class="active"</c:if>>
                <a href="home.form">Home</a>
            </li>
        </openmrs:hasPrivilege>
        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("queue") %>'>class="active"</c:if>>
                <a href="queue.form">Queue</a>
            </li>
        </openmrs:hasPrivilege>

        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("addOrUpdate") %>'>class="active"</c:if>>
                <a href="addOrUpdate.form">Add Donor</a>
            </li>
        </openmrs:hasPrivilege>

        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("viewBloodResults") %>'>class="active"</c:if>>
                <a href="bloodResults.form">View Blood Results</a>
            </li>
        </openmrs:hasPrivilege>

        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("ViewBloodInventory") %>'>class="active"</c:if>>
                <a href="Inventory.form">View Blood Inventory</a>
            </li>
        </openmrs:hasPrivilege>

        <openmrs:hasPrivilege privilege="Edit Encounters">
            <li <c:if test='<%= request.getRequestURI().contains("questionnaire") %>'>class="active"</c:if>>
                <a href="questionnaire.form">Donor Questionnaire</a>
            </li>
        </openmrs:hasPrivilege>
    </ul>
</div>