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

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileFilter;


/**
 *	Extension File Chooser
 *
 *  @author Jorg Janke
 *  @version  $Id: ExtensionFileFilter.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class ExtensionFileFilter extends FileFilter
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1653311024184813302L;


	/**
	 *	Constructor
	 */
	public ExtensionFileFilter()
	{
		this ("","");
	}	//	ExtensionFileFilter

	/**
	 *	Constructor
	 * 	@param extension extension
	 * 	@param description description
	 */
	public ExtensionFileFilter(String extension, String description)
	{
		setDescription (description);
		setExtension (extension);
	}	//	ExtensionFileFilter

	/**	Extension			*/
	private String 		m_extension;
	//
	private String 		m_description;


	/**
	 *	Description
	 *  @return description
	 */
	public String getDescription()
	{
		return m_description;
	}
	/**
	 * 	Set Description
	 *	@param newDescription description
	 */
	public void setDescription(String newDescription)
	{
		m_description = newDescription;
	}	//	setDescription

	/**
	 *	Extension
	 *  @param newExtension ext
	 */
	public void setExtension(String newExtension)
	{
		m_extension = newExtension;
	}
	/**
	 * 	Get Extension
	 *	@return extension
	 */
	public String getExtension()
	{
		return m_extension;
	}

	/**
	 *	Accept File
	 *  @param file file to be tested
	 *  @return true if OK
	 */
	public boolean accept(File file)
	{
		//	Need to accept directories
		if (file.isDirectory())
			return true;

		String ext = file.getName();
		int pos = ext.lastIndexOf('.');

		//	No extension
		if (pos == -1)
			return false;

		ext = ext.substring(pos+1);

		if (m_extension.equalsIgnoreCase(ext))
			return true;

		return false;
	}	//	accept


	/**
	 *	Verify file name with filer
	 *  @param file file
	 *  @param filter filter
	 *  @return file name
	 */
	public static String getFileName(File file, FileFilter filter)
	{
		return getFile(file, filter).getAbsolutePath();
	}	//	getFileName

	/**
	 *	Verify file with filter
	 *  @param file file
	 *  @param filter filter
	 *  @return file
	 */
	public static File getFile(File file, FileFilter filter)
	{
		String fName = file.getAbsolutePath();
		if (fName == null || fName.equals(""))
			fName = "Adempiere";
		//
		ExtensionFileFilter eff = null;
		if (filter instanceof ExtensionFileFilter)
			eff = (ExtensionFileFilter)filter;
		else
			return file;
		//
		int pos = fName.lastIndexOf('.');

		//	No extension
		if (pos == -1)
		{
			fName += '.' + eff.getExtension();
			return new File(fName);
		}

		String ext = fName.substring(pos+1);

		//	correct extension
		if (ext.equalsIgnoreCase(eff.getExtension()))
			return file;

		fName += '.' + eff.getExtension();
		return new File(fName);
	}	//	getFile

}	//	ExtensionFileFilter
