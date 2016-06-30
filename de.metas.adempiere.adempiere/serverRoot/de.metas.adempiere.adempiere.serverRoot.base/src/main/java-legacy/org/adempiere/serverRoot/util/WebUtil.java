package org.adempiere.serverRoot.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ecs.AlignType;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.label;
import org.apache.ecs.xhtml.option;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.tr;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.NamePair;
import org.compiere.util.Util;
import org.compiere.util.WebDoc;
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

public class WebUtil
{
	/**	Static Logger	*/
	private static Logger		log	= LogManager.getLogger(WebUtil.class);
	
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
			log.error("Set CharacterEncoding=" + WebEnv.ENCODING, e);
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
				log.trace("Convert " + data + " (" + enc + ")-> " 
					+ dataEnc + " (" + WebEnv.ENCODING + ")");
				data = dataEnc;
			}
			catch (Exception e)
			{
				log.error("Convert " + data + " (" + enc + ")->" + WebEnv.ENCODING);
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
				log.error("Token=" + token, e);
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
		log.trace(parameter + "=" + data + " -> " + retValue + " == " + debug);
		**/
		log.trace(parameter + "=" + data + " -> " + retValue);
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
			log.warn(parameter + "=" + data + " - " + e);
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
			log.debug(parameter + "=" + data + " - " + e);
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
		
		log.warn(parameter + " - cannot parse: " + data);
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
        String value = getParameter(request, parameter);
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
        log.warn(" - " + logMessage + " - update not confirmed");
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
			log.error("error writing");
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
			log.error("Set CharacterEncoding=" + WebEnv.ENCODING, e);
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
			log.warn(email + " - "
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
			log.error("store", e);
		}
		String result = new String (bos.toByteArray());
	//	System.out.println("String=" + result);
		try
		{
			result = URLEncoder.encode(result, WebEnv.ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("encode" + WebEnv.ENCODING, e);
			String enc = System.getProperty("file.encoding");      //  Windows default is Cp1252
			try
			{
				result = URLEncoder.encode(result, enc);
				log.info("encode: " + enc);
			}
			catch (Exception ex)
			{
				log.error("encode", ex);
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
			log.error("decode" + WebEnv.ENCODING, e);
			String enc = System.getProperty("file.encoding");      //  Windows default is Cp1252
			try
			{
				result = URLEncoder.encode(data, enc);
				log.error("decode: " + enc);
			}
			catch (Exception ex)
			{
				log.error("decode", ex);
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
			log.error("load", e);
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
			String s = getParameter (request, "PasswordNew");
			wu.setPasswordMessage (null);
			wu.setPassword (s);
			if (wu.getPasswordMessage () != null)
            {
                return false;
            }
			//
			s = getParameter (request, "EMail");
			if (!isEmailValid (s))
			{
				wu.setPasswordMessage ("EMail Invalid");
				return false;
			}
			wu.setEmail (s.trim());
		}
		//
		StringBuffer mandatory = new StringBuffer();
		String s = getParameter (request, "Name");
		if (s != null && s.length() != 0)
			wu.setName(s.trim());
		else
			mandatory.append(" - Name");
		s = getParameter (request, "Company");
		if (s != null && s.length() != 0)
			wu.setCompany(s);
		s = getParameter (request, "Title");
		if (s != null && s.length() != 0)
			wu.setTitle(s);
		//
		s = getParameter (request, "Address");
		if (s != null && s.length() != 0)
			wu.setAddress(s);
		else
			mandatory.append(" - Address");
		s = getParameter (request, "Address2");
		if (s != null && s.length() != 0)
			wu.setAddress2(s);
		//
		s = getParameter (request, "City");
		if (s != null && s.length() != 0)
			wu.setCity(s);
		else
			mandatory.append(" - City");
		s = getParameter (request, "Postal");
		if (s != null && s.length() != 0)
			wu.setPostal(s);
		else
			mandatory.append(" - Postal");
		//	Set Country before Region for validation
		s = getParameter (request, "C_Country_ID");
		if (s != null && s.length() != 0)
			wu.setC_Country_ID(s);
		s = getParameter (request, "C_Region_ID");
		if (s != null && s.length() != 0)
			wu.setC_Region_ID(s);
		s = getParameter (request, "RegionName");
		if (s != null && s.length() != 0)
			wu.setRegionName(s);
		//
		s = getParameter (request, "Phone");
		if (s != null && s.length() != 0)
			wu.setPhone(s);
		s = getParameter (request, "Phone2");
		if (s != null && s.length() != 0)
			wu.setPhone2(s);
		s = getParameter (request, "C_BP_Group_ID");
		if (s != null && s.length() != 0)
			wu.setC_BP_Group_ID (s);
		s = getParameter (request, "Fax");
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

}
