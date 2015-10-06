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
package org.compiere.db;

import java.util.ListResourceBundle;

/**
 *  Connection Resource Strings
 *
 *  @author     .
 *  @version    $Id: DBRes_fa.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_fa extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog", 	"\u0627\u062a\u0635\u0627\u0644 \u0628\u0647 \u06a9\u0627\u0645\u067e\u064a\u0631\u0647" },
	{ "Name", 				"\u0646\u0627\u0645" },
	{ "AppsHost", 			"\u0633\u064a\u0633\u062a\u0645 \u0645\u064a\u0632\u0628\u0627\u0646 \u06a9\u0627\u0631\u0628\u0631\u062f" },
	{ "AppsPort", 			"\u062f\u0631\u06af\u0627\u0647 \u06a9\u0627\u0631\u0628\u0631\u062f" },
	{ "TestApps", 			"\u0633\u0631\u0648\u0631 \u06a9\u0627\u0631\u0628\u0631\u062f \u0622\u0632\u0645\u0627\u064a\u0634\u06cc" },
	{ "DBHost", 			"\u0645\u064a\u0632\u0628\u0627\u0646 \u0628\u0627\u0646\u06a9 \u0627\u0637\u0644\u0627\u0639\u0627\u062a\u06cc" },
	{ "DBPort", 			"\u062f\u0631\u06af\u0627\u0647 \u0628\u0627\u0646\u06a9 \u0627\u0637\u0644\u0627\u0639\u0627\u062a\u06cc" },
	{ "DBName", 			"\u0646\u0627\u0645 \u0628\u0627\u0646\u06a9 \u0627\u0637\u0644\u0627\u0639\u0627\u062a" },
	{ "DBUidPwd", 			"\u0645\u0634\u062e\u0635\u0647 \u0627\u0633\u062a\u0641\u0627\u062f\u0647 \u06a9\u0646\u0646\u062f\u0647 \u0648 \u06a9\u0644\u0645\u0647 \u0639\u0628\u0648\u0631" },
	{ "ViaFirewall", 		"\u0627\u0632 \u0637\u0631\u0650\u064a\u0642 \u0641\u0627\u064a\u0631\u0648\u0627\u0644" },
	{ "FWHost", 			"\u0645\u064a\u0632\u0628\u0627\u0646 \u0641\u0627\u064a\u0631\u0648\u0627\u0644" },
	{ "FWPort", 			"\u062f\u0631\u06af\u0627\u0647 \u0641\u0627\u064a\u0631\u0648\u0627\u0644" },
	{ "TestConnection", 	"\u0628\u0627\u0646\u06a9 \u0627\u0637\u0644\u0627\u0639\u0627\u062a \u0622\u0632\u0645\u0627\u064a\u0634" },
	{ "Type", 				"\u0646\u0648\u0639 \u0628\u0627\u0646\u06a9 \u0627\u0637\u0644\u0627\u0639\u0627\u062a" },
	{ "BequeathConnection", "\u0627\u062a\u0635\u0627\u0644 \u062a\u062e\u0635\u064a\u0635 \u062f\u0627\u062f\u0647 \u0634\u062f\u0647" },
	{ "Overwrite", 			"\u0628\u0627\u0632\u0646\u0648\u064a\u0633\u06cc" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError", 	"\u062e\u0637\u0627 \u062f\u0631 \u0627\u062a\u0635\u0627\u0644" },
	{ "ServerNotActive", 	"\u0633\u0631\u0648\u0631 \u0641\u0639\u0627\u0644 \u0646\u064a\u0633\u062a" }
	};

	/**
	 * Get Contsnts
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  Res
