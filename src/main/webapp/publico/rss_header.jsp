<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html:xhtml/>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<logic:present name="siteView">
	<logic:present name="siteView" property="commonComponent.executionCourse.nome">
		<% final String appContext = pt.ist.bennu.core.util.ConfigurationManager.getProperty("app.context"); %>
		<% final String context = (appContext != null && appContext.length() > 0) ? "/" + appContext : ""; %>

		<bean:define id="executionCourseName" name="siteView" property="commonComponent.executionCourse.nome" />

		<bean:define id="linkRSSa" type="java.lang.String"><%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%><%=context%><%="/publico/announcementsRSS.do?id=" + pageContext.findAttribute("executionCourseCode")%></bean:define>
		<link rel="alternate" type="application/rss+xml" title="<%= executionCourseName %>" href="<%= linkRSSa %>"/>

		<bean:define id="linkRSSs" type="java.lang.String"><%=request.getScheme()%>://<%=request.getServerName()%>:<%=request.getServerPort()%><%=context%><%="/publico/summariesRSS.do?id=" + pageContext.findAttribute("executionCourseCode")%></bean:define>
		<link rel="alternate" type="application/rss+xml" title="<%= executionCourseName %>" href="<%= linkRSSs %>"/>

	</logic:present>
</logic:present>