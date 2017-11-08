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
package org.compiere.db.connectiondialog.i18n;

import java.util.ListResourceBundle;

/**
 *  Connection Resource Strings
 *
 *  @author     kirinlin
 *  @version    $Id: DBRes_zh.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_zh extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "\u9023\u7dda" },
	{ "Name",               "\u540d\u7a31" },
	{ "AppsHost",           "\u61c9\u7528\u7a0b\u5f0f\u4e3b\u6a5f" },
	{ "AppsPort",           "\u61c9\u7528\u7a0b\u5f0f\u57e0" },
	{ "TestApps",           "\u6e2c\u8a66" },
	{ "DBHost",             "\u8cc7\u6599\u5eab\u4e3b\u6a5f" },
	{ "DBPort",             "\u8cc7\u6599\u5eab\u9023\u63a5\u57e0" },
	{ "DBName",             "\u8cc7\u6599\u5eab\u540d\u7a31" },
	{ "DBUidPwd",           "\u5e33\u865f / \u5bc6\u78bc" },
	{ "ViaFirewall",        "\u7d93\u904e\u9632\u706b\u7246" },
	{ "FWHost",             "\u9632\u706b\u7246\u4e3b\u6a5f" },
	{ "FWPort",             "\u9632\u706b\u7246\u57e0" },
	{ "TestConnection",     "\u6e2c\u8a66\u8cc7\u6599\u5eab" },
	{ "Type",               "\u8cc7\u6599\u5eab\u7a2e\u985e" },
	{ "BequeathConnection", "\u907a\u7559\u9023\u7dda" },
	{ "Overwrite",          "\u8986\u5beb" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "\u9023\u7dda\u932f\u8aa4" },
	{ "ServerNotActive",    "\u4f3a\u670d\u5668\u672a\u52d5\u4f5c" }
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
