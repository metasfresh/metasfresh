/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

/**
 * Individual Attachment Entry of MAttachment
 * 
 * @author Jorg Janke
 * @version $Id: MAttachmentEntry.java,v 1.2 2006/07/30 00:58:18 jjanke Exp $
 */
public final class MAttachmentEntry
{
	/**
	 * Attachment Entry
	 * 
	 * @param name name
	 * @param data binary data
	 * @param index optional index
	 */
	MAttachmentEntry(String name, byte[] data, int id)
	{
		this.id = id;
		m_name = name == null ? "?" : name;
		m_data = data;
	}

	private final int id;
	private final String m_name;
	private byte[] m_data;

	/**
	 * @return Returns the data.
	 */
	public byte[] getData()
	{
		return m_data;
	}

	/**
	 * @param data The data to set.
	 */
	void setData(final byte[] data)
	{
		m_data = data;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return m_name;
	}

	/**
	 * Get Attachment Id
	 * 
	 * @return index
	 */
	public int getId()
	{
		return id;
	}	// getIndex

	/**
	 * To String
	 * 
	 * @return name
	 */
	@Override
	public String toString()
	{
		return m_name;
	}	// toString

	/**
	 * To String Extended
	 * 
	 * @return name (length)
	 */
	public String toStringX()
	{
		StringBuffer sb = new StringBuffer(m_name);
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
	}	// toStringX

	/**
	 * Get File with default name
	 * 
	 * @return File
	 */
	public File saveToFile()
	{
		return saveToFile(getName());
	}	// getFile

	/**
	 * Get File with name
	 * 
	 * @param fileName optional file name
	 * @return file
	 */
	public File saveToFile(String fileName)
	{
		if (fileName == null || fileName.length() == 0)
			fileName = getName();
		final File file = new File(fileName);
		saveToFile(file);
		return file;
	}	// getFile

	/**
	 * Save to file.
	 * 
	 * @param file out file
	 * @return file
	 */
	void saveToFile(final File file)
	{
		// tolerate empty files
		//Check.assume(m_data != null && m_data.length > 0, "entry has data: {}", this);
		try(FileOutputStream fos = new FileOutputStream(file))
		{
			if(m_data != null && m_data.length > 0)
			{
				fos.write(m_data);
			}
			
			fos.close();
			//System.out.println("Saved to "+file);
		}
		catch (IOException ioe)
		{
			throw new AdempiereException("Failed saving "+file);
		}
	}	// getFile

	/**
	 * Is attachment entry a PDF
	 * 
	 * @return true if PDF
	 */
	public boolean isPDF()
	{
		return m_name.toLowerCase().endsWith(".pdf");
	}	// isPDF

	/**
	 * Is attachment entry a Graphic
	 * 
	 * @return true if *.gif, *.jpg, *.png
	 */
	public boolean isGraphic()
	{
		String m_lowname = m_name.toLowerCase();
		return m_lowname.endsWith(".gif") || m_lowname.endsWith(".jpg") || m_lowname.endsWith(".png");
	}	// isGraphic

	/**
	 * Get Content (Mime) Type
	 * 
	 * @return content type
	 */
	public String getContentType()
	{
		return MimeType.getMimeType(m_name);
	}	// getContentType

	/**
	 * Get Data as Input Stream
	 * 
	 * @return input stream
	 */
	public InputStream getInputStream()
	{
		if (m_data == null)
			return null;
		return new ByteArrayInputStream(m_data);
	}	// getInputStream

	public String getFilename()
	{
		final String name = getName();
		if (name == null)
		{
			return null;
		}
		return new File(name).getName();
	}
	
	public File getFile()
	{
		return new File(getName());
	}

}	// MAttachmentItem
