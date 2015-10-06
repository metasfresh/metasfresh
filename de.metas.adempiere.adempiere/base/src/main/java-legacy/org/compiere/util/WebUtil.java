/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ecs.AlignType;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.br;
import org.apache.ecs.xhtml.hr;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.label;
import org.apache.ecs.xhtml.option;
import org.apache.ecs.xhtml.p;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.small;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.tr;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MMailMsg;
import org.compiere.model.MRequest;
import org.compiere.model.MStore;
import org.compiere.model.MUserMail;

/**
 *  Servlet Utilities
 *
 *  @author Jorg Janke
 *  @version  $Id: WebUtil.java,v 1.7 2006/09/24 12:11:54 comdivision Exp $
 */
public final class WebUtil
{
	/**	Static Logger	*/
	private static CLogger		log	= CLogger.getCLogger (WebUtil.class);
	
	/**
	 *  Create Timeout Message
	 *
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @param message - optional message
	 *  @throws ServletException
	 *  @throws IOException
	 */
	public static void createTimeoutPage (HttpServletRequest request, HttpServletResponse response,
		HttpServlet servlet, String message) throws ServletException, IOException
	{
		log.info(message);
	  	WebSessionCtx wsc = WebSessionCtx.get(request);
		String windowTitle = "Timeout";
		if (wsc != null)
			windowTitle = Msg.getMsg(wsc.ctx, "Timeout");

		WebDoc doc = WebDoc.create (windowTitle);

		//	Body
		body body = doc.getBody();
		//  optional message
		if (message != null && message.length() > 0)
			body.addElement(new p(message, AlignType.CENTER));

		//  login button
		body.addElement(getLoginButton(wsc == null ? null : wsc.ctx));

		//
		body.addElement(new hr());
		body.addElement(new small(servlet.getClass().getName()));
		//	fini
		createResponse (request, response, servlet, null, doc, false);
	}   //  createTimeoutPage

	/**
	 *  Create Error Message
	 *
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @param message message
	 *  @throws ServletException
	 *  @throws IOException
	 */
	public static void createErrorPage (HttpServletRequest request, HttpServletResponse response,
		HttpServlet servlet, String message) 
		throws ServletException, IOException
	{
		log.info( message);
	  	WebSessionCtx wsc = WebSessionCtx.get(request);
		String windowTitle = "Error";
		if (wsc != null)
			windowTitle = Msg.getMsg(wsc.ctx, "Error");
		if (message != null)
			windowTitle += ": " + message;

		WebDoc doc = WebDoc.create (windowTitle);

		//	Body
		body b = doc.getBody();

		b.addElement(new p(servlet.getServletName(), AlignType.CENTER));
		b.addElement(new br());

		//	fini
		createResponse (request, response, servlet, null, doc, false);
	}   //  createErrorPage

	/**
	 *  Create Exit Page "Log-off".
	 *  <p>
	 *  - End Session
	 *  - Go to start page (e.g. /adempiere/index.html)
	 *
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @param ctx context
	 *  @param AD_Message messahe
	 *  @throws ServletException
	 *  @throws IOException
	 */
	public static void createLoginPage (HttpServletRequest request, HttpServletResponse response,
		HttpServlet servlet, Properties ctx, String AD_Message) throws ServletException, IOException
	{
		request.getSession().invalidate();
		String url = WebEnv.getBaseDirectory("index.html");
		//
		WebDoc doc = null;
		if (ctx != null && AD_Message != null && !AD_Message.equals(""))
			doc = WebDoc.create (Msg.getMsg(ctx, AD_Message));
		else if (AD_Message != null)
			doc = WebDoc.create (AD_Message);
		else
			doc = WebDoc.create (false);
		script script = new script("window.top.location.replace('" + url + "');");
		doc.getBody().addElement(script);
		//
		createResponse (request, response, servlet, null, doc, false);
	}   //  createLoginPage

	/**
	 *  Create Login Button - replace Window
	 *
	 *  @param ctx context
	 *  @return Button
	 */
	public static input getLoginButton (Properties ctx)
	{
		String text = "Login";
		if (ctx != null)
			text = Msg.getMsg (ctx, "Login");
		
		input button = new input("button", text, "  "+text);		
		button.setID(text);
		button.setClass("loginbtn");		
		StringBuffer cmd = new StringBuffer ("window.top.location.replace('");
		cmd.append(WebEnv.getBaseDirectory("index.html"));
		cmd.append("');");
		button.setOnClick(cmd.toString());
		return button;
	}   //  getLoginButton

	
	/**************************************************************************
	 *  Get Cookie Properties
	 *
	 *  @param request request
	 *  @return Properties
	 */
	public static Properties getCookieProprties(HttpServletRequest request)
	{
		//  Get Properties
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (int i = 0; i < cookies.length; i++)
			{
				if (cookies[i].getName().equals(WebEnv.COOKIE_INFO))
					return propertiesDecode(cookies[i].getValue());
			}
		}
		return new Properties();
	}   //  getProperties

	
	/**
	 *  Get String Parameter.
	 *
	 *  @param request request
	 *  @param parameter parameter
	 *  @return string or null
	 */
	public static String getParameter (HttpServletRequest request, String parameter)
	{
		if (request == null || parameter == null)
			return null;
		String enc = request.getCharacterEncoding();
		try
		{
			if (enc == null)
			{
				request.setCharacterEncoding(WebEnv.ENCODING);
				enc = request.getCharacterEncoding();
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Set CharacterEncoding=" + WebEnv.ENCODING, e);
			enc = request.getCharacterEncoding();
		}
		String data = request.getParameter(parameter);
		if (data == null || data.length() == 0)
			return data;
		
		//	Convert
		if (enc != null && !WebEnv.ENCODING.equals(enc))
		{
			try
			{
				String dataEnc = new String(data.getBytes(enc), WebEnv.ENCODING);
				log.log(Level.FINER, "Convert " + data + " (" + enc + ")-> " 
					+ dataEnc + " (" + WebEnv.ENCODING + ")");
				data = dataEnc;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "Convert " + data + " (" + enc + ")->" + WebEnv.ENCODING);
			}
		}
		
		//	Convert &#000; to character (JSTL input)
		String inStr = data;
		StringBuffer outStr = new StringBuffer();
		int i = inStr.indexOf("&#");
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to &#
			inStr = inStr.substring(i+2, inStr.length());	// from &#

			int j = inStr.indexOf(';');						// next ;
			if (j < 0)										// no second tag
			{
				inStr = "&#" + inStr;
				break;
			}

			String token = inStr.substring(0, j);
			try
			{
				int intToken = Integer.parseInt(token);
				outStr.append((char)intToken);				// replace context
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "Token=" + token, e);
				outStr.append("&#").append(token).append(";");
			}
			inStr = inStr.substring(j+1, inStr.length());	// from ;
			i = inStr.indexOf("&#");
		}

		outStr.append(inStr);           					//	add remainder
		String retValue = outStr.toString();
		/**
		StringBuffer debug = new StringBuffer();
		char[] cc = data.toCharArray();
		for (int j = 0; j < cc.length; j++)
		{
			debug.append(cc[j]);
			int iii = (int)cc[j];
			debug.append("[").append(iii).append("]");
		}
		log.finest(parameter + "=" + data + " -> " + retValue + " == " + debug);
		**/
		log.finest(parameter + "=" + data + " -> " + retValue);
		return retValue;
	}   //  getParameter

	/**
	 *  Get integer Parameter - 0 if not defined.
	 *
	 *  @param request request
	 *  @param parameter parameter
	 *  @return int result or 0
	 */
	public static int getParameterAsInt (HttpServletRequest request, String parameter)
	{
		if (request == null || parameter == null)
			return 0;
		String data = getParameter(request, parameter);
		if (data == null || data.length() == 0)
			return 0;
		try
		{
			return Integer.parseInt(data);
		}
		catch (Exception e)
		{
			log.warning (parameter + "=" + data + " - " + e);
		}
		return 0;
	}   //  getParameterAsInt

	/**
	 *  Get numeric Parameter - 0 if not defined
	 *
	 *  @param request request
	 *  @param parameter parameter
	 *  @return big decimal result or 0
	 */
	public static BigDecimal getParameterAsBD (HttpServletRequest request, String parameter)
	{
		if (request == null || parameter == null)
			return Env.ZERO;
		String data = getParameter(request, parameter);
		if (data == null || data.length() == 0)
			return Env.ZERO;
		try
		{
			return new BigDecimal (data);
		}
		catch (Exception e)
		{
		}
		try
		{
			DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Number);
			Object oo = format.parseObject(data);
			if (oo instanceof BigDecimal)
				return (BigDecimal)oo;
			else if (oo instanceof Number)
				return new BigDecimal (((Number)oo).doubleValue());
			return new BigDecimal (oo.toString());
		}
		catch (Exception e)
		{
			log.fine(parameter + "=" + data + " - " + e);
		}
		return Env.ZERO;
	}   //  getParameterAsBD

	/**
	 *  Get date Parameter - null if not defined.
	 *	Date portion only
	 *  @param request request
	 *  @param parameter parameter
	 *  @return timestamp result or null
	 */
	public static Timestamp getParameterAsDate (HttpServletRequest request, 
		String parameter)
	{
		return getParameterAsDate (request, parameter, null);
	}	//	getParameterAsDate
	
	/**
	 *  Get date Parameter - null if not defined.
	 *	Date portion only
	 *  @param request request
	 *  @param parameter parameter
	 *  @param language optional language
	 *  @return timestamp result or null
	 */
	public static Timestamp getParameterAsDate (HttpServletRequest request, 
		String parameter, Language language)
	{
		if (request == null || parameter == null)
			return null;
		String data = getParameter(request, parameter);
		if (data == null || data.length() == 0)
			return null;
		
		//	Language Date Format
		if (language != null)
		{
			try
			{
				DateFormat format = DisplayType.getDateFormat(DisplayType.Date, language);
				java.util.Date date = format.parse(data);
				if (date != null)
					return new Timestamp (date.getTime());
			}
			catch (Exception e)
			{
			}
		}
		
		//	Default Simple Date Format
		try
		{
			SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.Date);
			java.util.Date date = format.parse(data);
			if (date != null)
				return new Timestamp (date.getTime());
		}
		catch (Exception e)
		{
		}
		
		//	JDBC Format
		try 
		{
			return Timestamp.valueOf(data);
		}
		catch (Exception e) 
		{
		}
		
		log.warning(parameter + " - cannot parse: " + data);
		return null;
	}   //  getParameterAsDate

	/**
	 *  Get boolean Parameter.
	 *  @param request request
	 *  @param parameter parameter
	 *  @return true if found
	 */
	public static boolean getParameterAsBoolean (HttpServletRequest request, 
		String parameter)
	{
		return getParameterAsBoolean(request, parameter, null);
	}	//	getParameterAsBoolean
	
	/**
	 *  Get boolean Parameter.
	 *  @param request request
	 *  @param parameter parameter
	 *  @param expected optional expected value
	 *  @return true if found and if optional value matches
	 */
	public static boolean getParameterAsBoolean (HttpServletRequest request, 
		String parameter, String expected)
	{
		if (request == null || parameter == null)
			return false;
		String data = getParameter(request, parameter);
		if (data == null || data.length() == 0)
			return false;
		//	Ignore actual value
		if (expected == null)
			return true;
		//
		return expected.equalsIgnoreCase(data);
	}   //  getParameterAsBoolean
	
    /**
     * 	get Parameter or Null fi empty
     *	@param request request
     *	@param parameter parameter
     *	@return Request Value or null
     */
    public static String getParamOrNull (HttpServletRequest request, String parameter)
    {
        String value = WebUtil.getParameter(request, parameter);
        if(value == null) 
        	return value;
        if (value.length() == 0) 
        	return null;
        return value;
    }	//	getParamOrNull
    

    /**
     * 	reload
     *	@param logMessage
     *	@param jsp
     *	@param session
     *	@param request
     *	@param response
     *	@param thisContext
     *	@throws ServletException
     *	@throws IOException
     */
    public static void reload(String logMessage, String jsp, HttpSession session, HttpServletRequest request, HttpServletResponse response, ServletContext thisContext)
            throws ServletException, IOException
    {
        session.setAttribute(WebSessionCtx.HDR_MESSAGE, logMessage);
        log.warning(" - " + logMessage + " - update not confirmed");
        thisContext.getRequestDispatcher(jsp).forward(request, response);
    }

	
	/**************************************************************************
	 *  Create Standard Response Header with optional Cookie and print document.
	 *  D:\j2sdk1.4.0\docs\guide\intl\encoding.doc.html
	 *
	 *  @param request request
	 *  @param response response
	 *  @param servlet servlet
	 *  @param cookieProperties cookie properties
	 *  @param doc doc
	 *  @param debug debug
	 *  @throws IOException
	 */
	public static void createResponse (HttpServletRequest request, HttpServletResponse response,
		HttpServlet servlet, Properties cookieProperties, WebDoc doc, boolean debug) throws IOException
	{
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/html; charset=UTF-8");

		//
		//  Update Cookie - overwrite
		if (cookieProperties != null)
		{
			Cookie cookie = new Cookie (WebEnv.COOKIE_INFO, propertiesEncode(cookieProperties));
			cookie.setComment("(c) adempiere, Inc - Jorg Janke");
			cookie.setSecure(false);
			cookie.setPath("/");
			if (cookieProperties.size() == 0)
				cookie.setMaxAge(0);            //  delete cookie
			else
				cookie.setMaxAge(2592000);      //  30 days in seconds   60*60*24*30
			response.addCookie(cookie);
		}
		//  add diagnostics
		if (debug && WebEnv.DEBUG)
		{
		//	doc.output(System.out);
			WebEnv.addFooter(request, response, servlet, doc.getBody());
		//	doc.output(System.out);
		}
	//	String content = doc.toString();
	//  response.setContentLength(content.length());    //  causes problems at the end of the output

		//  print document
		PrintWriter out = response.getWriter();     //  with character encoding support
		doc.output(out);
		out.flush();
		if (out.checkError())
			log.log(Level.SEVERE, "error writing");
		//  binary output (is faster but does not do character set conversion)
	//	OutputStream out = response.getOutputStream();
	//	byte[] data = doc.toString().getBytes();
	//	response.setContentLength(data.length);
	//	out.write(doc.toString().getBytes());
		//
		out.close();
	}   //  createResponse

	
	/**************************************************************************
	 *  Create Java Script to clear Target frame
	 *
	 *  @param targetFrame target frame
	 *  @return Clear Frame Script
	 */
	public static script getClearFrame (String targetFrame)
	{
		StringBuffer cmd = new StringBuffer();
		cmd.append("// <!-- clear frame\n")
			.append("var d = parent.").append(targetFrame).append(".document;\n")
			.append("d.open();\n")
			.append("d.write('<link href=\"").append(WebEnv.getStylesheetURL()).append("\" type=\"text/css\" rel=\"stylesheet\">');\n")
			.append("d.write('<link href=\"/adempiere/css/window.css\" type=\"text/css\" rel=\"stylesheet\">');\n")
			.append("d.write('<br><br><br><br><br><br><br>');")
			.append("d.write('<div style=\"text-align: center;\"><img class=\"CenterImage\" style=\"vertical-align: middle; filter:alpha(opacity=50); -moz-opacity:0.5;\" src=\"Logo.gif\" /></div>');\n")
			.append("d.close();\n")
			.append("// -- clear frame -->");
		//
		return new script(cmd.toString());
	}   //  getClearFrame


	/**
	 * 	Return a link and script with new location.
	 * 	@param url forward url
	 * 	@param delaySec delay in seconds (default 3)
	 * 	@return html
	 */
	public static HtmlCode getForward (String url, int delaySec)
	{
		if (delaySec <= 0)
			delaySec = 3;
		HtmlCode retValue = new HtmlCode();
		//	Link
		a a = new a(url);
		a.addElement(url);
		retValue.addElement(a);
		//	Java Script	- document.location - 
		script script = new script("setTimeout(\"window.top.location.replace('" + url 
			+ "')\"," + (delaySec+1000) + ");");
		retValue.addElement(script);
		//
		return retValue;
	}	//	getForward

	/**
	 * 	Create Forward Page
	 * 	@param response response
	 * 	@param title page title
	 * 	@param forwardURL url
	 * 	@param delaySec delay in seconds (default 3)
	 * 	@throws ServletException
	 * 	@throws IOException
	 */
	public static void createForwardPage (HttpServletResponse response,
		String title, String forwardURL, int delaySec) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		WebDoc doc = WebDoc.create(title);
		body b = doc.getBody();
		b.addElement(getForward(forwardURL, delaySec));
		PrintWriter out = response.getWriter();
		doc.output(out);
		out.flush();
		if (out.checkError())
			log.log(Level.SEVERE, "Error writing");
		out.close();
		log.fine(forwardURL + " - " + title);
	}	//	createForwardPage


	/**
	 * 	Does Test exist
	 *	@param test string
	 *	@return true if String with data
	 */
	public static boolean exists (String test)
	{
		if (test == null)
			return false;
		return test.length() > 0;
	}	//	exists

	/**
	 * 	Does Parameter exist
	 * 	@param request request
	 *	@param parameter string
	 *	@return true if String with data
	 */
	public static boolean exists (HttpServletRequest request, String parameter)
	{
		if (request == null || parameter == null)
			return false;
		try
		{
			String enc = request.getCharacterEncoding();
			if (enc == null)
				request.setCharacterEncoding(WebEnv.ENCODING);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Set CharacterEncoding=" + WebEnv.ENCODING, e);
		}
		return exists (request.getParameter(parameter));
	}	//	exists


	/**
	 *	Is EMail address valid
	 * 	@param email mail address
	 * 	@return true if valid
	 */
	public static boolean isEmailValid (String email)
	{
		if (email == null || email.length () == 0)
			return false;
		try
		{
			InternetAddress ia = new InternetAddress (email, true);
			if (ia != null)
				return true;
		}
		catch (AddressException ex)
		{
			log.warning (email + " - "
				+ ex.getLocalizedMessage ());
		}
		return false;
	}	//	isEmailValid


	/**************************************************************************
	 *  Decode Properties into String (URL encoded)
	 *
	 *  @param pp properties
	 *  @return Encoded String
	 */
	public static String propertiesEncode (Properties pp)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try
		{
			pp.store(bos, "adempiere");   //  Header
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, "store", e);
		}
		String result = new String (bos.toByteArray());
	//	System.out.println("String=" + result);
		try
		{
			result = URLEncoder.encode(result, WebEnv.ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			log.log(Level.SEVERE, "encode" + WebEnv.ENCODING, e);
			String enc = System.getProperty("file.encoding");      //  Windows default is Cp1252
			try
			{
				result = URLEncoder.encode(result, enc);
				log.info("encode: " + enc);
			}
			catch (Exception ex)
			{
				log.log(Level.SEVERE, "encode", ex);
			}
		}
	//	System.out.println("String-Encoded=" + result);
		return result;
	}   //  propertiesEncode

	/**
	 *  Decode data String (URL encoded) into Properties
	 *
	 *  @param data data
	 *  @return Properties
	 */
	public static Properties propertiesDecode (String data)
	{
		String result = null;
	//	System.out.println("String=" + data);
		try
		{
			result = URLDecoder.decode(data, WebEnv.ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			log.log(Level.SEVERE, "decode" + WebEnv.ENCODING, e);
			String enc = System.getProperty("file.encoding");      //  Windows default is Cp1252
			try
			{
				result = URLEncoder.encode(data, enc);
				log.log(Level.SEVERE, "decode: " + enc);
			}
			catch (Exception ex)
			{
				log.log(Level.SEVERE, "decode", ex);
			}
		}
	//	System.out.println("String-Decoded=" + result);

		ByteArrayInputStream bis = new ByteArrayInputStream(result.getBytes());
		Properties pp = new Properties();
		try
		{
			pp.load(bis);
		}
		catch (IOException e)
		{
			log.log(Level.SEVERE, "load", e);
		}
		return pp;
	}   //  propertiesDecode

	
	/**************************************************************************
	 *  Convert Array of NamePair to HTTP Option Array.
	 *  <p>
	 *  If the ArrayList does not contain NamePairs, the String value is used
	 *  @see org.compiere.util.NamePair
	 *  @param  list    ArrayList containing NamePair values
	 *  @param  default_ID  Sets the default if the key/ID value is found.
	 *      If the value is null or empty, the first value is selected
	 *  @return Option Array
	 */
	public static option[] convertToOption (NamePair[] list, String default_ID)
	{		
		int size = list.length;
		option[] retValue = new option[size];	
		for (int i = 0; i < size; i++)
		{			
			boolean selected = false;
			//  select first entry
			if (i == 0 && (default_ID == null || default_ID.length() == 0))
				selected = true;

			//  Create option
			String name = Util.maskHTML(list[i].getName());
			retValue[i] = new option(list[i].getID()).addElement(name);

			//  Select if ID/Key is same as default ID
			if (default_ID != null && default_ID.equals(list[i].getID()))
				selected = true;
			retValue[i].setSelected(selected);
		}		
		return retValue;
	}   //  convertToOption

	
	/**
	 *  Create label/field table row
	 *
	 *  @param line - null for new line (table row)
	 *  @param FORMNAME form name
	 *  @param PARAMETER parameter name
	 *  @param labelText label
	 *  @param inputType HTML input type
	 *  @param value data value
	 *  @param sizeDisplay display size
	 *  @param size data size
	 *  @param longField field spanning two columns
	 *  @param mandatory mark as mandatory
	 *  @param onChange onChange call
	 *  @param script script
	 *  @return tr table row
	 */
	static public tr createField (tr line, String FORMNAME, String PARAMETER,
		String labelText, String inputType, Object value,
		int sizeDisplay, int size, boolean longField, 
		boolean mandatory, String onChange, StringBuffer script)
	{
		if (line == null)
			line = new tr();
		String labelInfo = labelText;
		if (mandatory)
		{
			labelInfo += "&nbsp;<font color=\"red\">*</font>";
			String fName = "document." + FORMNAME + "." + PARAMETER;
			script.append(fName).append(".required=true; ");
		}

		label llabel = new label().setFor(PARAMETER).addElement(labelInfo);
		llabel.setID("ID_" + PARAMETER + "_Label");
	//	label.setTitle(description);
		line.addElement(new td().addElement(llabel).setAlign(AlignType.RIGHT));
		input iinput = new input(inputType, PARAMETER, value == null ? "" : value.toString());
		iinput.setSize(sizeDisplay).setMaxlength(size);
		iinput.setID("ID_" + PARAMETER);
		if (onChange != null && onChange.length() > 0)
			iinput.setOnChange(onChange);
		iinput.setTitle(labelText);
		td field = new td().addElement(iinput).setAlign(AlignType.LEFT);
		if (longField)
			field.setColSpan(3);
		line.addElement(field);
		return line;
	}   //  addField

	/**
	 * 	Get Close PopUp Buton
	 *	@return button
	 */
	public static input createClosePopupButton(Properties ctx)
	{
		String text = "Close";
		if (ctx != null)
			text = Msg.getMsg (ctx, "Close");
		
		input close = new input("button", text, "  "+text);		
		close.setID(text);
		close.setClass("closebtn");		
		close.setTitle ("Close PopUp");	//	Help
		//close.setOnClick ("closePopup();return false;");
		close.setOnClick ("self.close();return false;");
		return close;
	}	//	getClosePopupButton
	
	
	/**
	 * 	Stream Attachment Entry
	 *	@param response response
	 *	@param attachment attachment
	 *	@param attachmentIndex logical index
	 *	@return error message or null
	 */
	public static String streamAttachment (HttpServletResponse response, 
		MAttachment attachment, int attachmentIndex)
	{
		if (attachment == null)
			return "No Attachment";
		
		int realIndex = -1;
		MAttachmentEntry[] entries = attachment.getEntries();
		for (int i = 0; i < entries.length; i++)
		{
			MAttachmentEntry entry = entries[i];
			if (entry.getIndex() == attachmentIndex)
			{
				realIndex = i;
				break;
			}
		}
		if (realIndex < 0)
		{
			log.fine("No Attachment Entry for Index=" 
				+ attachmentIndex + " - " + attachment);
			return "Attachment Entry not found";
		}
		
		MAttachmentEntry entry = entries[realIndex];
		if (entry.getData() == null)
		{
			log.fine("Empty Attachment Entry for Index=" 
				+ attachmentIndex + " - " + attachment);
			return "Attachment Entry empty";
		}
		
		//	Stream Attachment Entry
		try
		{
			int bufferSize = 2048; //	2k Buffer
			int fileLength = entry.getData().length;
			//
			response.setContentType(entry.getContentType());
			response.setBufferSize(bufferSize);
			response.setContentLength(fileLength);
			//
			log.fine(entry.toString());
			long time = System.currentTimeMillis();		//	timer start
			//
			ServletOutputStream out = response.getOutputStream ();
			out.write (entry.getData());
			out.flush();
			out.close();
			//
			time = System.currentTimeMillis() - time;
			double speed = (fileLength/1024) / ((double)time/1000);
			log.info("Length=" 
				+ fileLength + " - " 
				+ time + " ms - " 
				+ speed + " kB/sec - " + entry.getContentType());
		}
		catch (IOException ex)
		{
			log.log(Level.SEVERE, ex.toString());
			return "Streaming error - " + ex;
		}
		return null;
	}	//	streamAttachment

	
	/**
	 * 	Stream File
	 *	@param response response
	 *	@param file file to stream
	 *	@return error message or null
	 */
	public static String streamFile (HttpServletResponse response, File file)
	{
		if (file == null)
			return "No File";
		if (!file.exists())
			return "File not found: " + file.getAbsolutePath();
		
		MimeType mimeType = MimeType.get(file.getAbsolutePath());
		//	Stream File
		try
		{
			int bufferSize = 2048; //	2k Buffer
			int fileLength = (int)file.length();
			//
			response.setContentType(mimeType.getMimeType());
			response.setBufferSize(bufferSize);
			response.setContentLength(fileLength);
			//
			log.fine(file.toString());
			long time = System.currentTimeMillis();		//	timer start
			//	Get Data
			FileInputStream in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream ();
			int c = 0;
			while ((c = in.read()) != -1)
				out.write(c);
			//
			out.flush();
			out.close();
			in.close();
			//
			time = System.currentTimeMillis() - time;
			double speed = (fileLength/1024) / ((double)time/1000);
			log.info("Length=" 
				+ fileLength + " - " 
				+ time + " ms - " 
				+ speed + " kB/sec - " + mimeType);
		}
		catch (IOException ex)
		{
			log.log(Level.SEVERE, ex.toString());
			return "Streaming error - " + ex;
		}
		return null;
	}	//	streamFile
	
	
	/**
	 * 	Remove Cookie with web user by setting user to _
	 * 	@param request request (for context path)
	 * 	@param response response to add cookie
	 */
	public static void deleteCookieWebUser (HttpServletRequest request, HttpServletResponse response, String COOKIE_NAME)
	{
		Cookie cookie = new Cookie(COOKIE_NAME, " ");
		cookie.setComment("adempiere Web User");
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(1);      //  second
		response.addCookie(cookie);
	}	//	deleteCookieWebUser
	
	/**************************************************************************
	 * 	Send EMail
	 *	@param request request
	 *	@param to web user
	 *	@param msgType see MMailMsg.MAILMSGTYPE_*
	 *	@param parameter object array with parameters
	 * 	@return mail EMail.SENT_OK or error message 
	 */
	public static String sendEMail (HttpServletRequest request, WebUser to,
		String msgType, Object[] parameter)
	{
		WebSessionCtx wsc = WebSessionCtx.get(request);
		MStore wStore = wsc.wstore;
		MMailMsg mailMsg = wStore.getMailMsg(msgType);
		//
		StringBuffer subject = new StringBuffer(mailMsg.getSubject());
		if (parameter.length > 0 && parameter[0] != null)
			subject.append(parameter[0]);
		//
		StringBuffer message = new StringBuffer();
		String hdr = wStore.getEMailFooter();
		if (hdr != null && hdr.length() > 0)
			message.append(hdr).append("\n");
		message.append(mailMsg.getMessage());
		if (parameter.length > 1 && parameter[1] != null)
			message.append(parameter[1]);
		if (mailMsg.getMessage2() != null)
		{
			message.append("\n")
				.append(mailMsg.getMessage2());
			if (parameter.length > 2 && parameter[2] != null)
				message.append(parameter[2]);
		}
		if (mailMsg.getMessage3() != null)
		{
			message.append("\n")
				.append(mailMsg.getMessage3());
			if (parameter.length > 3 && parameter[3] != null)
				message.append(parameter[3]);
		}
		message.append(MRequest.SEPARATOR)
			.append("http://").append(request.getServerName()).append(request.getContextPath())
			.append("/ - ").append(wStore.getName())
			.append("\n").append("Request from: ").append(getFrom(request))
			.append("\n");
		String ftr = wStore.getEMailFooter();
		if (ftr != null && ftr.length() > 0)
			message.append(ftr);
		
		//	Create Mail
		EMail email = wStore.createEMail(to.getEmail(), 
			subject.toString(), message.toString());
		//	CC Order
		if (msgType.equals(MMailMsg.MAILMSGTYPE_OrderAcknowledgement))
		{
			String orderEMail = wStore.getWebOrderEMail();
			String storeEMail = wStore.getWStoreEMail();
			if (orderEMail != null && orderEMail.length() > 0
				&& !orderEMail.equals(storeEMail))	//	already Bcc
				email.addBcc(orderEMail);
		}

		//	Send
		String retValue = email.send();
		//	Log
		MUserMail um = new MUserMail(mailMsg, to.getAD_User_ID(), email);
		um.save();
		//
		return retValue;
	}	//	sendEMail
	
	/**
	 * 	Get Remote From info
	 * 	@param request request
	 * 	@return remore info
	 */
	public static String getFrom (HttpServletRequest request)
	{
		String host = request.getRemoteHost();
		if (!host.equals(request.getRemoteAddr()))
			host += " (" + request.getRemoteAddr() + ")";
		return host;
	}	//	getFrom

	/**
	 * 	Add Cookie with web user
	 * 	@param request request (for context path)
	 * 	@param response response to add cookie
	 * 	@param webUser email address
	 */
	public static void addCookieWebUser (HttpServletRequest request, HttpServletResponse response, String webUser, String COOKIE_NAME)
	{
		Cookie cookie = new Cookie(COOKIE_NAME, webUser);
		cookie.setComment("adempiere Web User");
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(2592000);      //  30 days in seconds   60*60*24*30
		response.addCookie(cookie);
	}	//	setCookieWebUser

	/**
	 * 	Resend Validation Code
	 * 	@param request request
	 *	@param wu user
	 */
	public static void resendCode(HttpServletRequest request, WebUser wu)
	{
		String msg = sendEMail(request, wu, 
			MMailMsg.MAILMSGTYPE_UserVerification,
			new Object[]{
				request.getServerName(),
				wu.getName(),
				wu.getEMailVerifyCode()});
		if (EMail.SENT_OK.equals(msg))
			wu.setPasswordMessage ("EMail sent");
		else
			wu.setPasswordMessage ("Problem sending EMail: " + msg);
	}	//	resendCode
	

	/**
	 * 	Update Web User
	 * 	@param request request
	 * 	@param wu user
	 * 	@param updateEMailPwd if true, change email/password
	 * 	@return true if saved
	 */
	public static boolean updateFields (HttpServletRequest request, WebUser wu, boolean updateEMailPwd)
	{
		if (updateEMailPwd)
		{
			String s = WebUtil.getParameter (request, "PasswordNew");
			wu.setPasswordMessage (null);
			wu.setPassword (s);
			if (wu.getPasswordMessage () != null)
            {
                return false;
            }
			//
			s = WebUtil.getParameter (request, "EMail");
			if (!WebUtil.isEmailValid (s))
			{
				wu.setPasswordMessage ("EMail Invalid");
				return false;
			}
			wu.setEmail (s.trim());
		}
		//
		StringBuffer mandatory = new StringBuffer();
		String s = WebUtil.getParameter (request, "Name");
		if (s != null && s.length() != 0)
			wu.setName(s.trim());
		else
			mandatory.append(" - Name");
		s = WebUtil.getParameter (request, "Company");
		if (s != null && s.length() != 0)
			wu.setCompany(s);
		s = WebUtil.getParameter (request, "Title");
		if (s != null && s.length() != 0)
			wu.setTitle(s);
		//
		s = WebUtil.getParameter (request, "Address");
		if (s != null && s.length() != 0)
			wu.setAddress(s);
		else
			mandatory.append(" - Address");
		s = WebUtil.getParameter (request, "Address2");
		if (s != null && s.length() != 0)
			wu.setAddress2(s);
		//
		s = WebUtil.getParameter (request, "City");
		if (s != null && s.length() != 0)
			wu.setCity(s);
		else
			mandatory.append(" - City");
		s = WebUtil.getParameter (request, "Postal");
		if (s != null && s.length() != 0)
			wu.setPostal(s);
		else
			mandatory.append(" - Postal");
		//	Set Country before Region for validation
		s = WebUtil.getParameter (request, "C_Country_ID");
		if (s != null && s.length() != 0)
			wu.setC_Country_ID(s);
		s = WebUtil.getParameter (request, "C_Region_ID");
		if (s != null && s.length() != 0)
			wu.setC_Region_ID(s);
		s = WebUtil.getParameter (request, "RegionName");
		if (s != null && s.length() != 0)
			wu.setRegionName(s);
		//
		s = WebUtil.getParameter (request, "Phone");
		if (s != null && s.length() != 0)
			wu.setPhone(s);
		s = WebUtil.getParameter (request, "Phone2");
		if (s != null && s.length() != 0)
			wu.setPhone2(s);
		s = WebUtil.getParameter (request, "C_BP_Group_ID");
		if (s != null && s.length() != 0)
			wu.setC_BP_Group_ID (s);
		s = WebUtil.getParameter (request, "Fax");
		if (s != null && s.length() != 0)
			wu.setFax(s);
		//
		if (mandatory.length() > 0)
		{
			mandatory.insert(0, "Enter Mandatory");
			wu.setSaveErrorMessage(mandatory.toString());
			return false;
		}
		return wu.save();
	}	//	updateFields
}   //  WUtil
