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
 *  @author     Nikola Petkov
 *  @version    $Id: patch-java-trl-sr_RS.txt,v 1.1 2008/12/05 11:39:16 mgifos Exp $
 */
public class IniRes_sr extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",  "\u0421\u043F\u043E\u0440\u0430\u0437\u0443\u043C \u043E \u043B\u0438\u0446\u0435\u043D\u0446\u0438" },
	{ "Do_you_accept",      "\u0414\u0430 \u043B\u0438 \u043F\u0440\u0438\u0445\u0432\u0430\u0442\u0430\u0442\u0435 \u043B\u0438\u0446\u0435\u043D\u0446\u0443" },
	{ "No",                 "\u041D\u0435" },
	{ "Yes_I_Understand",   "\u0414\u0430, \u0440\u0430\u0437\u0443\u043C\u0435\u043C \u0438 \u043F\u0440\u0438\u0445\u0432\u0430\u0442\u0430\u043C" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "\u041B\u0438\u0446\u0435\u043D\u0446\u0430 \u0458\u0435 \u043E\u0434\u0431\u0438\u0458\u0435\u043D\u0430 \u0438\u043B\u0438 \u0438\u0441\u0442\u0435\u043A\u043B\u0430" }
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

