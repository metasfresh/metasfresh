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

import de.metas.util.Check;


/**
 *  Web Environment and debugging
 *
 *  @author Jorg Janke
 *  @version $Id: WebEnv.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WebEnv
{
	/**
	 * Base Directory links e.g. <b>http://localhost:8080/</b>
	 * 
	 * NOTE: to configure this, check the "serverRoot" project
	 */
	private static final String DIR_BASE = "";
	/** Image Sub-Directory under BASE */
	private static final String DIR_IMAGE = "images";
	/** Stylesheet Name */
	private static final String STYLE_STD = "css/standard.css";
	
	public static final String IMAGE_PARAM_Width = "width";
	/** Small Logo. */
	public static final String LOGO = "logo.png"; // put ONLY the filename here
	
	/**************************************************************************
	 *  Get Base Directory entry.
	 *  <br>
	 *  /adempiere/
	 *  @param entry file entry or path
	 *  @return url to entry in base directory
	 */
	static String getBaseDirectory (final String... entries)
	{
		final StringBuilder sb = new StringBuilder (DIR_BASE);
		
		for (final String entry : entries)
		{
			if (Check.isEmpty(entry, true))
			{
				continue;
			}
			if (sb.length() > 0 && !entry.startsWith("/"))
			{
				sb.append("/");
			}
			sb.append(entry);
		}
		return sb.toString();
	}   //  getBaseDirectory
	
	/**
	 * Get Logo Path.
	 *
	 * @param width optional image width
	 * @return url to logo
	 */
	public static String getLogoURL(final int width)
	{
		String logoURL = getBaseDirectory(DIR_IMAGE, LOGO);
		return logoURL + "?" + IMAGE_PARAM_Width + "=" + width;
	}   //  getLogoPath

	/**
	 *  Get Stylesheet Path.
	 *  <p>
	 *  /adempiere/standard.css
	 *  @return url of Stylesheet
	 */
	public static String getStylesheetURL()
	{
		return getBaseDirectory(STYLE_STD);
	}   //  getStylesheetURL

}   //  WEnv
