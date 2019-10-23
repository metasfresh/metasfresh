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

import java.util.ListResourceBundle;

/**
 *  License Dialog Translation
 *
 *  @author     Stefan Christians 
 *  @version    $Id: IniRes_ja.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class IniRes_ja extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",  "\u30e9\u30a4\u30bb\u30f3\u30b9" },
	{ "Do_you_accept",      "\u3053\u306e\u30e9\u30a4\u30bb\u30f3\u30b9\u306b\u540c\u610f\u3057\u307e\u3059\u304b\uff1f" },
	{ "No",                 "\u3044\u3044\u3048" },
	{ "Yes_I_Understand",   "\u306f\u3044" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "\u30e9\u30a4\u30bb\u30f3\u30b9\u306b\u540c\u610f\u3057\u307e\u305b\u3093\u3067\u3057\u305f" }
	};

	/**
	 *  Get Content
	 *  @return Content
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  IniRes
