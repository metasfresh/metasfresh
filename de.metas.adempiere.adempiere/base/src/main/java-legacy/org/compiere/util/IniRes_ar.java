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
 * License Dialog Translation
 * @author      Jorg Janke
 * @version     $Id: IniRes_ar.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */

public class IniRes_ar extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",   "\u0631\u062e\u0635\u0629 \u0643\u0645\u0628\u064a\u0631" },
	{ "Do_you_accept",      "\u0647\u0644 \u0623\u0646\u062a \u0645\u0648\u0627\u0641\u0642\u061f" },
	{ "No",                 "\u0644\u0627" },
	{ "Yes_I_Understand",   "\u0646\u0639\u0645\u060c \u0623\u0641\u0647\u0645 \u0648 \u0623\u0648\u0627\u0641\u0642" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "\u0627\u0644\u0631\u0651\u064f\u062e\u0635\u0629 \u0645\u0631\u0641\u0648\u0636\u0629 \u0623\u0648 \u0645\u0646\u062a\u0647\u064a\u0629" }
	};

	/**
	 * Get Content
	 * @return  Content
	 * @uml.property  name="contents"
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  IniRes_ar_TN

