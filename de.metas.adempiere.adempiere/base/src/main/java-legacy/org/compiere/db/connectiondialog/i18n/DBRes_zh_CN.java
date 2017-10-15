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
 *  @author     ZhaoXing Meng
 *  @version    $Id: DBRes_zh_CN.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_zh_CN extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "\u8fde\u673a" },
	{ "Name",               "\u540d\u79f0" },
	{ "AppsHost",           "\u5e94\u7528\u670d\u52a1\u5668\u4e3b\u673a" },
	{ "AppsPort",           "\u5e94\u7528\u670d\u52a1\u5668\u7aef\u53e3" },
	{ "TestApps",           "\u6d4b\u8bd5\u5e94\u7528\u670d\u52a1\u5668" },
	{ "DBHost",             "\u6570\u636e\u5e93\u4e3b\u673a" },
	{ "DBPort",             "\u6570\u636e\u5e93\u7aef\u53e3" },
	{ "DBName",             "\u6570\u636e\u5e93\u540d" },
	{ "DBUidPwd",           "\u7528\u6237\u53f7 / \u53e3\u4ee4" },
	{ "ViaFirewall",        "\u901a\u8fc7\u9632\u706b\u5899" },
	{ "FWHost",             "\u9632\u706b\u5899\u4e3b\u673a" },
	{ "FWPort",             "\u9632\u706b\u5899\u7aef\u53e3" },
	{ "TestConnection",     "\u6d4b\u8bd5\u6570\u636e\u5e93" },
	{ "Type",               "\u6570\u636e\u5e93\u7c7b\u578b" },
	{ "BequeathConnection", "\u9057\u7559\u8fde\u7ebf" },
	{ "Overwrite",          "\u8986\u5199" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "\u8fde\u673a\u9519\u8bef" },
	{ "ServerNotActive",    "\u670d\u52a1\u5668\u6ca1\u53cd\u5e94" }
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
