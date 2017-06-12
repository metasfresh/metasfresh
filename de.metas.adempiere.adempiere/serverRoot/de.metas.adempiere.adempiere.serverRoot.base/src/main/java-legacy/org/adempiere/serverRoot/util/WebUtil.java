package org.adempiere.serverRoot.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static final transient Logger log = LogManager.getLogger(WebUtil.class);
	
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
	 *  Decode Properties into String (URL encoded)
	 *
	 *  @param pp properties
	 *  @return Encoded String
	 */
	private static String propertiesEncode (Properties pp)
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
	private static Properties propertiesDecode (String data)
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
}
