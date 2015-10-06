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
 *  @author     Nikola Petkov
 *  @version    $Id: patch-java-trl-sr_RS.txt,v 1.1 2008/12/05 11:39:16 mgifos Exp $
 */
public class DBRes_sr extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog", 	"\u0410\u0434\u0435\u043C\u043F\u0438\u0435\u0440\u0435 \u0432\u0435\u0437\u0430" },
	{ "Name", 				"\u041D\u0430\u0437\u0438\u0432" },
	{ "AppsHost", 			"\u0425\u043E\u0441\u0442 \u0430\u043F\u043B\u0438\u043A\u0430\u0446\u0438\u0458\u0435" },
	{ "AppsPort", 			"\u041F\u043E\u0440\u0442 \u0430\u043F\u043B\u0438\u043A\u0430\u0446\u0438\u0458\u0435" },
	{ "TestApps", 			"\u0422\u0435\u0441\u0442\u0438\u0440\u0430\u045A\u0435 \u0441\u0435\u0440\u0432\u0435\u0440\u0430" },
	{ "DBHost", 			"\u0425\u043E\u0441\u0442 \u0431\u0430\u0437\u0435" },
	{ "DBPort", 			"\u041F\u043E\u0440\u0442 \u0431\u0430\u0437\u0435" },
	{ "DBName", 			"\u041D\u0430\u0437\u0438\u0432 \u0431\u0430\u0437\u0435" },
	{ "DBUidPwd", 			"\u041A\u043E\u0440\u0438\u0441\u043D\u0438\u043A / \u043B\u043E\u0437\u0438\u043D\u043A\u0430" },
	{ "ViaFirewall", 		"\u041F\u0443\u0442\u0435\u043C Firewall-a" },
	{ "FWHost", 			"Firewall \u0425\u043E\u0441\u0442" },
	{ "FWPort", 			"Firewall \u041F\u043E\u0440\u0442" },
	{ "TestConnection", 	"\u0422\u0435\u0441\u0442\u0438\u0440\u0430\u045A\u0435 \u0431\u0430\u0437\u0435" },
	{ "Type", 				"\u0422\u0438\u043F \u0431\u0430\u0437\u0435" },
	{ "BequeathConnection", "Bequeath \u0432\u0435\u0437\u0430" },
	{ "Overwrite", 			"\u041F\u0440\u0435\u0431\u0440\u0438\u0441\u0430\u0442\u0438" },
	{ "ConnectionProfile",	"\u0412\u0435\u0437\u0430" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal \u0441\u0435\u0440\u0432\u0435\u0440" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError", 	"\u0413\u0440\u0435\u0448\u043A\u0430 \u0443 \u0432\u0435\u0437\u0438" },
	{ "ServerNotActive", 	"\u0421\u0435\u0440\u0432\u0435\u0440 \u043D\u0438\u0458\u0435 \u0430\u043A\u0442\u0438\u0432\u0438\u0440\u0430\u043D" }
	};

	/**
	 * Get contents
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  Res

