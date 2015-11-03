<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String productName = org.compiere.Adempiere.getName();
	if (productName == null || productName.isEmpty())
	{
		productName = "metas Fresh";
	}
	String productURL = org.compiere.Adempiere.getURL();
	if(productURL == null || productURL.isEmpty())
	{
		productURL = "http://www.metas-fresh.com";
	}
	
	boolean isZkWebUIServerEnabled = org.compiere.Adempiere.isZkWebUIServerEnabled();	
%>
<html>
<head>
	<title><%=productName%> Application Home</title>
	<meta name="description" content="metas Fresh Application Home">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="css/standard.css" rel="stylesheet" type="text/css">
	<link rel="shortcut icon" href="favicon_32.png" />
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>

				<td background="header_banner.png" height="60">
					<table border="0" cellpadding="5" cellspacing="0" width="100%">

						<tbody>
							<tr>
								<td align="left" valign="middle" width="477"></td>
								<td align="left" valign="bottom"><a href="<%=productURL%>" target="_blank"><img src="logo_web_metasfresh_color.gif" alt="logo" align="right" border="0"></a></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td background="header_bar.jpg" height="60">
					<table border="0" cellpadding="5" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td align="left" valign="middle"><font color="#76B828" face="Arial, Helvetica, sans-serif" size="4">Welcome to the <%=productName%> Home Page!</font> <br></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>

			<tr>
				<td height="77">
					<table border="0" cellpadding="5" cellspacing="0" width="100%">

						<!--DWLayoutTable-->
						<tbody>
							<tr>
								<td bgcolor="#BBED80">
									<h2>
										<font color="#000000">Portable Desktop Client</font>
									</h2>
								</td>

								<td bgcolor="#fbf8f1"><a href="/binaries/metas-fresh-client.zip"><img src="zip.gif" alt="<%=productName%> Client zip" align="right" border="0" height="32" width="32"></a>Runs under windows and Linux.<br>Instructions: Install a Java
									Runtime Environment (Java 7 or later); Download and extract the desktop client <a href="/binaries/metas-fresh-client.zip" title="Download <%=productName%> Client">zip</a> file on your computer.</td>
							</tr>
							<!--
short story: commented out as of now because we can't incorporate fonts into an uber-jar
							<tr>
								<td bgcolor="#BBED80">
									<h2>
										<font color="#000000">Local Install (exe)</font>
									</h2>
								</td>

								<td bgcolor="#fbf8f1"><a href="/binaries/metas-fresh-client.exe"><img
										src="zip.gif" alt="metas-fresh Client exe" align="right"
										border="0" height="32" width="32"></a> Install a Java
									Runtime Environment (Java 7 or later); Download and extract the
									<%=productName%> Client <a href="/binaries/metas-fresh-client.exe"
									title="Download <%=productName%> Client">exe</a> file on your
									Client.</td>
							</tr>
-->
							<c:choose>
							<c:when test="${isZkWebUIServerEnabled}">
							<tr>
								<td bgcolor="#BBED80" valign="top">
									<h2>
										<font color="#000000">Web Application</font>
									</h2>
								</td>
								<td bgcolor="#fbf8f1"><a href="/webui">webUI</a> - contributed by Posterita</td>

							</tr>
							</c:when>
							</c:choose>

							<tr>
								<td bgcolor="#BBED80" valign="top">
									<h2>
										<font color="#000000">Server Admin</font>
									</h2>
								</td>
								<td bgcolor="#fbf8f1">
									<a href="serverMonitor">Server Management</a>
								</td>
							</tr>
							<tr>
								<td bgcolor="#BBED80" valign="top">
									<h2>
										<font color="#000000">Additional Binaries</font>
									</h2>
								</td>
								<td bgcolor="#fbf8f1">
									Binaries required for advanced usage scenarios 
									<ul>
										<li><a href="/binaries/de.metas.edi.esb.camel.jar">EDI ESB bundle</a></li>
										<li><a href="/binaries/de.metas.document.archive.esb.camel.jar">Archive ESB bundle</a></li>
										<li><a href="/binaries/de.metas.document.archive.esb.camel.jar">Printing ESB bundle</a></li>
										<li><a href="/binaries/de.metas.printing.esb.client-jar-with-dependencies.jar">Printing standalone client</a></li>
									</ul>
								</td>
							</tr>

						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>