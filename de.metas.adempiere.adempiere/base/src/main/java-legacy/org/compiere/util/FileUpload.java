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

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.xhtml.form;
import org.apache.ecs.xhtml.input;
import org.apache.ecs.xhtml.label;

/**
 *	Web File Upload utility.
 *	Based on code like:
 *	<code>
	  <form action="requestServlet" method="post" enctype="multipart/form-data" name="fileLoad" id="fileLoad">
	    <label for="file">File: </label>
        <input name="R_Request_ID" type="hidden" id="R_Request_ID" value="<c:out value='${request.r_Request_ID}'/>">
	    <input name="file" type="file" id="file" size="40">
        <input type="submit" name="Submit" value="Upload">
      </form>
 *	</code>
 *  @author Jorg Janke
 *  @version $Id: FileUpload.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class FileUpload
{
	/**
	 * 	Create Upload Form.
	 * 	You need to add the (hidden) parameter
	 * 	@param action form action
	 *	@return form
	 */
	public static form createForm (String action)
	{
		form upload = new form(action, form.METHOD_POST, form.ENC_UPLOAD);
		upload.addElement(new label ("File").setFor("file"));
		
		//Browse Button
		//upload.addElement(new input (input.TYPE_FILE, "file", "").setSize(40));
		String textbtn = "file";
		String text = "Browse";				
		input filebtn = new input(input.TYPE_FILE, textbtn, "  "+text);
		filebtn.setSize(40);
		filebtn.setID(text);
		filebtn.setClass("filebtn");
		upload.addElement(filebtn);
		
		//upload Button
		//upload.addElement(new input (input.TYPE_SUBMIT, "upload", "Upload"));		
		textbtn = "upload";
		text = "Upload";				
		input submitbtn = new input(input.TYPE_SUBMIT, textbtn, "  "+text);		
		submitbtn.setID(text);
		submitbtn.setClass("submitbtn");		
		upload.addElement(submitbtn);
		
		return upload;
	}	//	createForm

	
	/**************************************************************************
	 * 	Upload File from requesr
	 * 	@param request request
	 */
	public FileUpload (HttpServletRequest request)
	{
		super ();
		try
		{
			m_error = upload (request);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "FileUpload", e); 
			m_error = e.getLocalizedMessage();
			if (m_error == null || m_error.length() == 0)
				m_error = e.toString();
		}
	}	//	FileUpload
	
	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());
	/** File Name		*/
	private String	m_fileName = null;
	/* File Data		*/
	private byte[]	m_data = null;
	/** Error Message	*/
	private String	m_error = null;
	
	/** Request Info as Data String		*/
	private String	m_requestDataString = null; 
	
	/**
	 * @return Returns the data.
	 */
	public byte[] getData ()
	{
		return m_data;
	}	//	getData
	
	/**
	 * @return Returns the actual fileName (without path).
	 */
	public String getFileName ()
	{
		if (m_fileName != null)		//	eliminate path
		{
			int index = Math.max(m_fileName.lastIndexOf('/'), m_fileName.lastIndexOf('\\'));
			if (index > 0)
				return m_fileName.substring(index+1);
		}
		return m_fileName;
	}	//	getFileName
	
	/**
	 * @return Returns the error message or null.
	 */
	public String getError ()
	{
		return m_error;
	}	//	getError

	/**************************************************************************
	 * 	Upload File
	 *	@param request request
	 *	@throws ServletException
	 *	@throws IOException
	 *	@return error message or null
	 */
	private String upload (HttpServletRequest request)
		throws ServletException, IOException
	{
		final int MAX_KB = 250;		//	Max Upload Size in kB
		//
		int formDataLength = request.getContentLength();
		String contentType = request.getContentType();
		int index = contentType.lastIndexOf('=');
		String boundary = contentType.substring(index+1);
		log.fine(formDataLength + " - " + boundary);
		int sizeKB = formDataLength/1024; 
		if (sizeKB > MAX_KB)					//	250k
		{
			log.warning("File too large " + sizeKB);
			return "File too large = " + sizeKB 
				+ "kB - Allowed = " + MAX_KB + "kB";
		}

		DataInputStream in = new DataInputStream (request.getInputStream()); 
		byte[] data = new byte[formDataLength];
		int bytesRead = 0;
		int totalBytesRead = 0;
		while (totalBytesRead < formDataLength)
		{
			bytesRead = in.read(data, totalBytesRead, formDataLength);
			totalBytesRead += bytesRead;
		}
		
		//	Convert to String for easy manipulation
		m_requestDataString = new String (data, "ISO-8859-1");
		if (m_requestDataString.length() != data.length)
			return "Internal conversion Error";
			
		//	File Name:
		//	Content-Disposition: form-data; name="file"; filename="C:\Documents and Settings\jjanke\My Documents\desktop.ini"
		index = m_requestDataString.indexOf("filename=\"");
		m_fileName = m_requestDataString.substring(index+10);
		index = m_fileName.indexOf('"');
		if (index < 1)
			return "No File Name";
		m_fileName = m_fileName.substring(0, index);
		log.fine("upload - " + m_fileName); 
			
			
		//	Content:
		//	Content-Disposition: form-data; name="file"; filename="C:\Documents and Settings\jjanke\My Documents\desktop.ini"
		//	Content-Type: application/octet-stream
		//
		//	[DeleteOnCopy]
		//	Owner=jjanke
		//	Personalized=5
		//	PersonalizedName=My Documents
		//
		//	-----------------------------7d433475038e
		int posStart = m_requestDataString.indexOf("filename=\"");
		posStart = m_requestDataString.indexOf("\n",posStart)+1;	//	end of Context-Disposition
		posStart = m_requestDataString.indexOf("\n",posStart)+1;	//	end of Content-Type
		posStart = m_requestDataString.indexOf("\n",posStart)+1;	//	end of empty line
		int posEnd = m_requestDataString.indexOf(boundary, posStart)-4;
		int length = posEnd-posStart;
		//
		log.fine("uploadFile - Start=" + posStart + ", End=" + posEnd + ", Length=" + length);

		//	Final copy
		m_data = new byte[length]; 
		for (int i = 0; i < length; i++)
			m_data[i] = data[posStart+i];
		return null;
	}	//	uploadFile

	/**
	 * 	Get MultiPart Form Parameter.
	 * 	Assumes single line (no cr)
	 * 	<code>
		Request
		-----------------------------7d433475038e
		Content-Disposition: form-data; name="R_Request_ID"
	
		1000000
		-----------------------------7d433475038e--
		</code>
	 *	@param parameterName name of parameter
	 *	@return parameter or null of mot found
	 */
	public String getParameter (String parameterName)
	{
		if (m_requestDataString == null)
			return null;
		String retValue = null;
		String search = "name=\"" + parameterName + "\"";
		int index = m_requestDataString.indexOf(search); 
		if (index > 0)
		{
			retValue = m_requestDataString.substring(index);
			retValue = retValue.substring(retValue.indexOf("\n")+1);	//	eol
			retValue = retValue.substring(retValue.indexOf("\n")+1);	//	empty line
			retValue = retValue.substring(0,retValue.indexOf("\n"));	//	cr
			retValue = retValue.trim();
		}
		else
		{
			log.warning("getParameter Not found - " + parameterName);
			return null;
		}
		log.fine("getParameter = " + parameterName + "=" + retValue);
		return retValue;
	}	//	getMultiPartParameter

	/**
	 * 	Get Multi Part Parameter As Int
	 *	@param parameterName name
	 *	@return result or 0
	 */
	public int getParameterAsInt (String parameterName)
	{
		String result = getParameter (parameterName);
		try
		{
			if (result != null && result.length() > 0)
				return Integer.parseInt(result);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getParameterAsInt - " + parameterName + "=" + result, e);
		}
		return 0;
	}	//	getParameterAsInt
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("FileUpload[");
		if (m_fileName != null)
			sb.append(m_fileName);
		if (m_error != null)
			sb.append(";Error=").append(m_error);
		if (m_data != null)
			sb.append(";Length=").append(m_data.length);
		sb.append ("]");
		return sb.toString ();
	} //	toString
	
}	//	FileUpload
