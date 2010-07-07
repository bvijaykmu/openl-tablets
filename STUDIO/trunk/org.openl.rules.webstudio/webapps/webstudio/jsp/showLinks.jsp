<%@ page import="org.openl.rules.webstudio.web.util.WebStudioUtils" %>
<%@page import="org.openl.rules.table.xls.XlsUrlParser"%>
<%@page import="org.openl.rules.webstudio.util.ExcelLauncher"%>
<%@page import="org.openl.rules.webstudio.util.WordLauncher"%>
<%@ page import="java.io.File" %>
<%@page import="org.openl.commons.web.util.WebTool"%>

<%
	String excelScriptPath = pageContext.getServletContext().getRealPath("scripts/LaunchExcel.vbs");
	String wordScriptPath = pageContext.getServletContext().getRealPath("scripts/LaunchWord.vbs");
	
    boolean local = WebTool.isLocalRequest(request);
    boolean wantURI = request.getParameter("uri") != null;
    if (local) {
        if (wantURI) {
            XlsUrlParser parser = new XlsUrlParser();
            parser.parse(request.getParameter("uri"));
            ExcelLauncher.launch(excelScriptPath,
            	parser.wbPath,
                parser.wbName, 
                parser.wsName, 
                parser.range);
            return;
        }

        String wbName = request.getParameter("wbName");
        if (wbName != null)
        	
            ExcelLauncher.launch(excelScriptPath,
                    request.getParameter("wbPath"),
                    wbName,
                    request.getParameter("wsName"),
                    request.getParameter("range")

            );

        String wdName = request.getParameter("wdName");
        if (wdName != null)
            WordLauncher.launch(
                    wordScriptPath,
                    request.getParameter("wdPath"),
                    wdName,
                    request.getParameter("wdParStart"),
                    request.getParameter("wdParEnd")

            );
    } else if (wantURI) {
%>
<script type="text/javascript">alert("This action is available only from the machine server runs at.")</script>
<% } else {
    String filename = request.getParameter("wbName");
    String path = request.getParameter("wbPath");
    if (filename == null) {
        filename = request.getParameter("wdName");
        path = request.getParameter("wdPath");
    }
    pageContext.setAttribute("filename", new File(path, filename).getAbsolutePath());
%>
<jsp:forward page="/action/download"><jsp:param name="filename" value="${filename}" /></jsp:forward>
<%}%>
