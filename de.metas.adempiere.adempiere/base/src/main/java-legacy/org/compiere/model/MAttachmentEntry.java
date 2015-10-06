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
package org.compiere.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.MimeType;


/**
 *	Individual Attachment Entry of MAttachment
 *	
 *  @author Jorg Janke
 *  @version $Id: MAttachmentEntry.java,v 1.2 2006/07/30 00:58:18 jjanke Exp $
 */
public class MAttachmentEntry
{
	/**
	 * 	Attachment Entry
	 * 	@param name name
	 * 	@param data binary data
	 * 	@param index optional index
	 */
	public MAttachmentEntry (String name, byte[] data, int index)
	{
		super ();
		setName (name);
		setData (data);
		if (index > 0)
			m_index = index;
		else
		{
			long now = System.currentTimeMillis();
			if (s_seed+3600000l < now)	//	older then 1 hour
			{
				s_seed = now;
				s_random = new Random(s_seed);
			}
			m_index = s_random.nextInt();
		}
	}	//	MAttachmentItem
	
	/**
	 * 	Attachment Entry
	 * 	@param name name
	 * 	@param data binary data
	 */
	public MAttachmentEntry (String name, byte[] data)
	{
		this (name, data, 0);
	}	//	MAttachmentItem
	
	/**	The Name				*/
	private String 	m_name = "?";
	/** The Data				*/
	private byte[] 	m_data = null;
	
	/** Random Seed			*/
	private static long		s_seed = System.currentTimeMillis(); 
	/** Random Number		*/
	private static Random	s_random = new Random(s_seed);
	/** Index				*/
	private int				m_index = 0; 

	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());
	
	
	/**
	 * @return Returns the data.
	 */
	public byte[] getData ()
	{
		return m_data;
	}
	/**
	 * @param data The data to set.
	 */
	public void setData (byte[] data)
	{
		m_data = data;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName ()
	{
		return m_name;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName (String name)
	{
		if (name != null)
			m_name = name;
		if (m_name == null)
			m_name = "?";
	}	//	setName
	
	/**
	 * 	Get Attachment Index
	 *	@return timestamp
	 */
	public int getIndex()
	{
		return m_index;
	}	//	getIndex
	
	/**
	 * 	To String
	 *	@return name
	 */
	public String toString ()
	{
		return m_name;
	}	//	toString

	/**
	 * 	To String Extended
	 *	@return name (length)
	 */
	public String toStringX ()
	{
		StringBuffer sb = new StringBuffer (m_name);
		if (m_data != null)
		{
			sb.append(" (");
			//
			float size = m_data.length;
			if (size <= 1024)
				sb.append(m_data.length).append(" B");
			else
			{
				size /= 1024;
				if (size > 1024)
				{
					size /= 1024;
					sb.append(size).append(" MB");
				}
				else
					sb.append(size).append(" kB");
			}
			//
			sb.append(")");
		}
		sb.append(" - ").append(getContentType());
		return sb.toString();
	}	//	toStringX

	
	/**
	 * 	Dump Data
	 */
	public void dump ()
	{
		String hdr = "----- " + getName() + " -----";
		System.out.println (hdr);
		if (m_data == null)
		{
			System.out.println ("----- no data -----");
			return;
		}
		//	raw data
		for (int i = 0; i < m_data.length; i++)
		{
			char data = (char)m_data[i];
			System.out.print(data);
		}
			
		System.out.println ();
		System.out.println (hdr);
		//	Count nulls at end
		int ii = m_data.length -1;
		int nullCount = 0;
		while (m_data[ii--] == 0)
			nullCount++;
		System.out.println("----- Length=" + m_data.length + ", EndNulls=" + nullCount 
			+ ", RealLength=" + (m_data.length-nullCount));
		/**
		//	Dump w/o nulls
		if (nullCount > 0)
		{
			for (int i = 0; i < m_data.length-nullCount; i++)
				System.out.print((char)m_data[i]);
			System.out.println ();
			System.out.println (hdr);
		}
		/** **/
	}	//	dump

	/**
	 * 	Get File with default name
	 *	@return File
	 */
	public File getFile ()
	{
		return getFile (getName());
	}	//	getFile

	/**
	 * 	Get File with name
	 *	@param fileName optional file name
	 *	@return file
	 */	
	public File getFile (String fileName)
	{
		if (fileName == null || fileName.length() == 0)
			fileName = getName();
		return getFile (new File(fileName));
	}	//	getFile

	/**
	 * 	Get File
	 *	@param file out file
	 *	@return file
	 */
	public File getFile (File file)
	{
		if (m_data == null || m_data.length == 0)
			return null;
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(m_data);
			fos.close();
		}
		catch (IOException ioe)
		{
			log.log(Level.SEVERE, "getFile", ioe);
		}
		return file;
	}	//	getFile

	/**
	 * 	Is attachment entry a PDF
	 *	@return true if PDF
	 */
	public boolean isPDF()
	{
		return m_name.toLowerCase().endsWith(".pdf");
	}	//	isPDF
	
	/**
	 * 	Is attachment entry a Graphic
	 *	@return true if *.gif, *.jpg, *.png
	 */
	public boolean isGraphic()
	{
		String m_lowname = m_name.toLowerCase();
		return m_lowname.endsWith(".gif") || m_lowname.endsWith(".jpg") || m_lowname.endsWith(".png");
	}	//	isGraphic
	
	
	/**
	 * 	Get Content (Mime) Type
	 *	@return content type
	 */
	public String getContentType()
	{
		return MimeType.getMimeType(m_name);
	}	//	getContentType
	
	/**
	 * 	Get Data as Input Stream
	 *	@return input stream
	 */
	public InputStream getInputStream()
	{
		if (m_data == null)
			return null;
		return new ByteArrayInputStream(m_data);
	}	//	getInputStream
	
}	//	MAttachmentItem
