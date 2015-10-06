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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import javax.activation.DataSource;

/**
 *	A DataSource based on the Java Mail Example.
 *  This class implements a DataSource from:
 * 		an InputStream
 *		a byte array
 * 		a String
 * 	@author John Mani
 * 	@author Bill Shannon
 * 	@author Max Spivak
 */
public class ByteArrayDataSource
	implements DataSource
{
	/**
	 *  Create a DataSource from an input stream
	 * 	@param is stream
	 * 	@param type optional MIME type e.g. text/html
	 */
	public ByteArrayDataSource (InputStream is, String type)
	{
		try
		{
			ByteArrayOutputStream os = new ByteArrayOutputStream ();
			int ch;

			while ((ch = is.read ()) != -1)
			{
				// XXX - must be made more efficient by
				// doing buffered reads, rather than one byte reads
				os.write (ch);
			}
			m_data = os.toByteArray ();
		}
		catch (IOException ioex)
		{
			log.log(Level.WARNING, "", ioex);
		}
		if (type != null && type.length() > 0)
			m_type = type;
	}	//	ByteArrayDataSource

	/**
	 * 	Create a DataSource from a byte array
	 * 	@param data	data
	 * 	@param type type e.g. text/html
	 */
	public ByteArrayDataSource (byte[] data, String type)
	{
		m_data = data;
		if (type != null && type.length() > 0)
			m_type = type;
	}	//	ByteArrayDataSource

	/**
	 * Create a DataSource from a String
	 * @param stringData content
	 * @param charSetName optional if null/empty uses UTF-8
	 * @param type optional MIME type e.g. text/html
	 */
	public ByteArrayDataSource (String stringData, String charSetName, String type)
	{
		if (charSetName == null || charSetName.length() == 0)
			charSetName = "UTF-8";	// WebEnv.ENCODING - alternatibe iso-8859-1	
		try	
		{
			m_data = stringData.getBytes (charSetName);
		}
		catch (UnsupportedEncodingException uex)
		{
			log.log(Level.WARNING, "CharSetName=" + charSetName, uex);
		}
		if (type != null && type.length() > 0)
			m_type = type;
	}	//	ByteArrayDataSource

	/**	Data			**/
	private byte[] 		m_data = null;
	/** Content Type	**/
	private String 		m_type = "text/plain";
	/**	Name			**/
	private String		m_name = null;

	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (ByteArrayDataSource.class);
	
	/**
	 * 	Return an InputStream for the data.
	 * 	@return inputstream
	 * 	@throws IOException
	 */
	public InputStream getInputStream ()
		throws IOException
	{
		if (m_data == null)
			throw new IOException ("no data");
		//	a new stream must be returned each time.
		return new ByteArrayInputStream (m_data);
	}	//	getInputStream

	/**
	 * Throws exception
	 * @return null
	 * @throws IOException
	 */
	public OutputStream getOutputStream ()
		throws IOException
	{
		throw new IOException ("cannot do this");
	}	//	getOutputStream

	/**
	 * 	Get Content Type
	 * 	@return MIME type e.g. text/html
	 */
	public String getContentType ()
	{
		return m_type;
	}	//	getContentType

	/**
	 * 	Set Name
	 * 	@param name name
	 * 	@return this
	 */
	public ByteArrayDataSource setName(String name)
	{
		m_name = name;
		return this;
	}	//	setName

	/**
	 * 	Return Name or Class Name & Content Type
	 * 	@return dummy
	 */
	public String getName ()
	{
		if (m_name != null)
			return m_name;
		return "ByteArrayDataStream " + m_type;
	}	//	getName

}	//	ByteArrayDataStream
