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
 *  @author     Plamen Niikolov
 *  @version    $Id: IniRes_bg.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class IniRes_bg extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",   "\u041b\u0438\u0446\u0435\u043d\u0437\u043d\u043e \u0441\u043f\u043e\u0440\u0430\u0437\u0443\u043c\u0435\u043d\u0438\u0435" },
	{ "Do_you_accept",      "\u0421\u044a\u0433\u043b\u0430\u0441\u043d\u0438 \u043b\u0438 \u0441\u0442\u0435 \u0441 \u043b\u0438\u0446\u0435\u043d\u0437\u043d\u043e\u0442\u043e \u0441\u043f\u043e\u0440\u0430\u0437\u0443\u043c\u0435\u043d\u0438\u0435 ?" },
	{ "No",                 "\u041d\u0435" },
	{ "Yes_I_Understand",   "\u0414\u0430, \u0440\u0430\u0431\u0438\u0440\u0430\u043c \u0433\u043e \u0438 \u0433\u043e \u043f\u0440\u0438\u0435\u043c\u0430\u043c" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "\u041b\u0438\u0446\u0435\u043d\u0437\u0430 \u0435 \u043e\u0442\u043a\u0430\u0437\u0430\u043d \u0438\u043b\u0438 \u0441\u0432\u044a\u0440\u0448\u0438\u043b" }
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
