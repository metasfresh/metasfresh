/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
 *  @author     Kyriakos Ioannidis - kioa@openway.gr
 *  @version    $Id: IniRes.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class IniRes_el extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",  "\u03a3\u03c5\u03bc\u03c6\u03c9\u03bd\u03af\u03b1 \u0386\u03b4\u03b5\u03b9\u03b1\u03c2 \u03a7\u03c1\u03ae\u03c3\u03b7\u03c2" },
	{ "Do_you_accept",      "\u0391\u03c0\u03bf\u03b4\u03ad\u03c7\u03b5\u03c3\u03c4\u03b5 \u03c4\u03b7\u03bd \u0386\u03b4\u03b5\u03b9\u03b1 \u03a7\u03c1\u03ae\u03c3\u03b7\u03c2 ?" },
	{ "No",                 "\u038c\u03c7\u03b9" },
	{ "Yes_I_Understand",   "\u039d\u03b1\u03b9, \u03c4\u03b7\u03bd \u03ba\u03b1\u03c4\u03ac\u03bb\u03b1\u03b2\u03b1 \u03ba\u03b1\u03b9 \u03c4\u03b7\u03bd \u0391\u03c0\u03bf\u03b4\u03ad\u03c7\u03bf\u03bc\u03b1\u03b9" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "\u0397 \u0386\u03b4\u03b5\u03b9\u03b1 \u03a7\u03c1\u03ae\u03c3\u03b7\u03c2 \u03b1\u03c0\u03bf\u03c1\u03c1\u03af\u03c6\u03b8\u03b7\u03ba\u03b5 \u03ae \u03ad\u03bb\u03b7\u03be\u03b5" }
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
