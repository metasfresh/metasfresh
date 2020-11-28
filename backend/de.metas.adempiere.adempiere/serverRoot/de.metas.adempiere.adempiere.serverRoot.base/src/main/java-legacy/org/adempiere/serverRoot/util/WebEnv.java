package org.adempiere.serverRoot.util;

import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.br;
import org.apache.ecs.xhtml.comment;
import org.apache.ecs.xhtml.h3;
import org.apache.ecs.xhtml.hr;
import org.apache.ecs.xhtml.p;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.tr;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WebEnv
{
	/** Add HTML Debug Info                     */
	public static boolean DEBUG                 = true;
	/**	Logging									*/
	private static final Logger log = LogManager.getLogger(WebEnv.class);

	/** Encoding (ISO-8859-1 - UTF-8) 		*/
	public static final String      ENCODING = "UTF-8";
	/** Cookie Name                             */
	public static final String      COOKIE_INFO = "adempiereInfo";

	/** Timeout - 15 Minutes                    */
	public static final int         TIMEOUT     = 15*60;

	/** Not Braking Space						*/
	public static String			NBSP = "&nbsp;";
	
	/**
	 * 	Get Cell Content
	 *	@param content optional content
	 *	@return string content or non breaking space
	 */
	public static String getCellContent (Object content)
	{
		if (content == null)
			return NBSP;
		String str = content.toString();
		if (str.length() == 0)
			return NBSP;
		return str;
	}	//	getCellContent

	/**
	 * 	Get Cell Content
	 *	@param content optional content
	 *	@return string content
	 */
	public static String getCellContent (int content)
	{
		return String.valueOf(content);
	}	//	getCellContent

	/**************************************************************************
	 *  Add Footer (with diagnostics)
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @param body - Body to add footer
	 */
	public static void addFooter(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet, body body)
	{
		body.addElement(new hr());
		body.addElement(new comment(" --- Footer Start --- "));
		//  Command Line
		p footer = new p();
		footer.addElement(org.compiere.Adempiere.getDateVersion() + ": ");
		footer.addElement(new a("javascript:diag_window();", "Window Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:parent.resizeFrame('5,*');", "Menu"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:diag_navigator();", "Browser Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:diag_request();", "Request Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:diag_document();", "Document Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:diag_form();", "Form Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:toggle('DEBUG');", "Servlet Info"));
		footer.addElement(" - ");
		footer.addElement(new a("javascript:diag_source();", "Show Source"));
		footer.addElement("\n");
		body.addElement(footer);

		//  Add ServletInfo
		body.addElement(new br());
		body.addElement(getServletInfo(request, response, servlet));
		body.addElement(new script("hide('DEBUG');"));
		body.addElement(new comment(" --- Footer End --- "));
	}   //  getFooter

	/**
	 *	Get Information and put it in a HTML table
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @return Table
	 */
	private static table getServletInfo (HttpServletRequest request, HttpServletResponse response, HttpServlet servlet)
	{
		table table = new table();
		table.setID("DEBUG");
		Enumeration<?> e;

		tr space = new tr().addElement(new td().addElement("."));
		//	Request Info
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Request Info")) ));
		table.addElement(new tr().addElement(new td().addElement("Method"))
									.addElement(new td().addElement(request.getMethod() )));
		table.addElement(new tr().addElement(new td().addElement("Protocol"))
									.addElement(new td().addElement(request.getProtocol() )));
		table.addElement(new tr().addElement(new td().addElement("URI"))
									.addElement(new td().addElement(request.getRequestURI() )));
		table.addElement(new tr().addElement(new td().addElement("Context Path"))
									.addElement(new td().addElement(request.getContextPath() )));
		table.addElement(new tr().addElement(new td().addElement("Servlet Path"))
									.addElement(new td().addElement(request.getServletPath() )));
		table.addElement(new tr().addElement(new td().addElement("Path Info"))
									.addElement(new td().addElement(request.getPathInfo() )));
		table.addElement(new tr().addElement(new td().addElement("Path Translated"))
									.addElement(new td().addElement(request.getPathTranslated() )));
		table.addElement(new tr().addElement(new td().addElement("Query String"))
									.addElement(new td().addElement(request.getQueryString() )));
		table.addElement(new tr().addElement(new td().addElement("Content Length"))
									.addElement(new td().addElement("" + request.getContentLength() )));
		table.addElement(new tr().addElement(new td().addElement("Content Type"))
									.addElement(new td().addElement(request.getContentType() )));
		table.addElement(new tr().addElement(new td().addElement("Character Encoding"))
									.addElement(new td().addElement(request.getCharacterEncoding() )));
		table.addElement(new tr().addElement(new td().addElement("Locale"))
									.addElement(new td().addElement(request.getLocale().toString() )));
		table.addElement(new tr().addElement(new td().addElement("Schema"))
									.addElement(new td().addElement(request.getScheme() )));
		table.addElement(new tr().addElement(new td().addElement("Server Name"))
									.addElement(new td().addElement(request.getServerName() )));
		table.addElement(new tr().addElement(new td().addElement("Server Port"))
									.addElement(new td().addElement("" + request.getServerPort() )));
		table.addElement(new tr().addElement(new td().addElement("Remote User"))
									.addElement(new td().addElement(request.getRemoteUser() )));
		table.addElement(new tr().addElement(new td().addElement("Remote Address"))
									.addElement(new td().addElement(request.getRemoteAddr() )));
		table.addElement(new tr().addElement(new td().addElement("Remote Host"))
									.addElement(new td().addElement(request.getRemoteHost() )));
		table.addElement(new tr().addElement(new td().addElement("Authorization Type"))
									.addElement(new td().addElement(request.getAuthType() )));
		table.addElement(new tr().addElement(new td().addElement("User Principal"))
									.addElement(new td().addElement(request.getUserPrincipal()==null ? "" : request.getUserPrincipal().toString())));
		table.addElement(new tr().addElement(new td().addElement("IsSecure"))
									.addElement(new td().addElement(request.isSecure() ? "true" : "false" )));

		//	Request Attributes
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Request Attributes")) ));
		e = request.getAttributeNames();
		while (e.hasMoreElements())
		{
			String name = e.nextElement().toString();
			String attrib = request.getAttribute(name).toString();
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(attrib)));
		}

		//	Request Parameter
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Req Parameters")) ));
		try
		{
			String enc = request.getCharacterEncoding();
			if (enc == null)
				request.setCharacterEncoding(WebEnv.ENCODING);
		}
		catch (Exception ee)
		{
			log.error("Set CharacterEncoding=" + WebEnv.ENCODING, ee);
		}
		e = request.getParameterNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			String para = WebUtil.getParameter (request, name);
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(para)));
		}

		//	Request Header
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Req Header")) ));
		e = request.getHeaderNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			if (!name.equals("Cockie"))
			{
				String hdr = request.getHeader(name);
				table.addElement(new tr().addElement(new td().addElement(name))
											.addElement(new td().addElement(hdr)));
			}
		}

		//  Request Cookies
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Req Cookies")) ));
		Cookie[] cc = request.getCookies();
		if (cc != null)
		{
			for (int i = 0; i < cc.length; i++)
			{
				//	Name and Comment
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName() ))
											.addElement(new td().addElement(cc[i].getValue()) ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Comment" ))
											.addElement(new td().addElement(cc[i].getComment()) ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Domain" ))
											.addElement(new td().addElement(cc[i].getDomain()) ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Max Age" ))
											.addElement(new td().addElement(""+ cc[i].getMaxAge()) ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Path" ))
											.addElement(new td().addElement(cc[i].getPath()) ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Is Secure" ))
											.addElement(new td().addElement(cc[i].getSecure() ? "true" : "false") ));
				table.addElement(new tr().addElement(new td().addElement(cc[i].getName()+": Version" ))
											.addElement(new td().addElement("" + cc[i].getVersion()) ));
			}
		}	//	Cookies

		//  Request Session Info
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Req Session")) ));
		HttpSession session = request.getSession(true);
		table.addElement(new tr().addElement(new td().addElement("Session ID"))
									.addElement(new td().addElement(session.getId() )));
		Timestamp ts = new Timestamp(session.getCreationTime());
		table.addElement(new tr().addElement(new td().addElement("Created"))
									.addElement(new td().addElement(ts.toString() )));
		ts = new Timestamp(session.getLastAccessedTime());
		table.addElement(new tr().addElement(new td().addElement("Accessed"))
									.addElement(new td().addElement(ts.toString() )));
		table.addElement(new tr().addElement(new td().addElement("Request Session ID"))
									.addElement(new td().addElement(request.getRequestedSessionId() )));
		table.addElement(new tr().addElement(new td().addElement(".. via Cookie"))
									.addElement(new td().addElement("" + request.isRequestedSessionIdFromCookie() )));
		table.addElement(new tr().addElement(new td().addElement(".. via URL"))
									.addElement(new td().addElement("" + request.isRequestedSessionIdFromURL() )));
		table.addElement(new tr().addElement(new td().addElement("Valid"))
									.addElement(new td().addElement("" + request.isRequestedSessionIdValid() )));

		//	Request Session Attributes
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Session Attributes")) ));
		e = session.getAttributeNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			String attrib = session.getAttribute(name).toString();
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(attrib)));
		}

		//	Response Info
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Response")) ));
		table.addElement(new tr().addElement(new td().addElement("Buffer Size"))
									.addElement(new td().addElement(String.valueOf(response.getBufferSize()) )));
		table.addElement(new tr().addElement(new td().addElement("Character Encoding"))
									.addElement(new td().addElement(response.getCharacterEncoding() )));
		table.addElement(new tr().addElement(new td().addElement("Locale"))
									.addElement(new td().addElement(response.getLocale()==null ? "null" : response.getLocale().toString())));

		//  Servlet
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Servlet")) ));
		table.addElement(new tr().addElement(new td().addElement("Name"))
										.addElement(new td().addElement(servlet.getServletName())));
		table.addElement(new tr().addElement(new td().addElement("Info"))
										.addElement(new td().addElement(servlet.getServletInfo())));

		//  Servlet Init
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Servlet Init Parameter")) ));
		e = servlet.getInitParameterNames();
		//  same as:  servlet.getServletConfig().getInitParameterNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			String para = servlet.getInitParameter(name);
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(para)));
		}

		//  Servlet Context
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Servlet Context")) ));
		ServletContext servCtx = servlet.getServletContext();
		e = servCtx.getAttributeNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			String attrib = servCtx.getAttribute(name).toString();
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(attrib)));
		}

		//  Servlet Context
		table.addElement(space);
		table.addElement(new tr().addElement(new td().addElement(new h3("Servlet Context Init Parameter")) ));
		e = servCtx.getInitParameterNames();
		while (e.hasMoreElements())
		{
			String name = (String)e.nextElement();
			String attrib = servCtx.getInitParameter(name).toString();
			table.addElement(new tr().addElement(new td().addElement(name))
										.addElement(new td().addElement(attrib)));
		}

		/*	*/
		return table;
	}	//	getServletInfo
}
