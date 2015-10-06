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
 *  @author     Plamen Niikolov
 *  @version    $Id: DBRes_bg.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_bg extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog",  "\u0412\u0440\u044a\u0437\u043a\u0430" },
	{ "Name",               "\u0418\u043c\u0435" },
	{ "AppsHost",           "\u0421\u044a\u0440\u0432\u0435\u0440 \u043d\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u0442\u043e" },
	{ "AppsPort",           "\u041f\u043e\u0440\u0442 \u043d\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u0442\u043e" },
	{ "TestApps",           "\u0422\u0435\u0441\u0442 \u043d\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u0442\u043e" },
	{ "DBHost",             "\u0421\u044a\u0440\u0432\u0435\u0440 \u043d\u0430 \u0431\u0430\u0437\u0430\u0442\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "DBPort",             "\u041f\u043e\u0440\u0442 \u043d\u0430 \u0431\u0430\u0437\u0430\u0442\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "DBName",             "\u0418\u043c\u0435 \u043d\u0430 \u0431\u0430\u0437\u0430\u0442\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "DBUidPwd",           "\u041f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b / \u041f\u0430\u0440\u043e\u043b\u0430" },
	{ "ViaFirewall",        "\u0417\u0430\u0434 \u0437\u0430\u0449\u0438\u0442\u043d\u0430 \u0441\u0442\u0435\u043d\u0430" },
	{ "FWHost",             "\u0421\u044a\u0440\u0432\u0435\u0440 \u043d\u0430 \u0437\u0430\u0449\u0438\u0442\u043d\u0430\u0442\u0430 \u0441\u0442\u0435\u043d\u0430" },
	{ "FWPort",             "\u041f\u043e\u0440\u0442 \u043d\u0430 \u0437\u0430\u0449\u0438\u0442\u043d\u0430\u0442\u0430 \u0441\u0442\u0435\u043d\u0430" },
	{ "TestConnection",     "\u0422\u0435\u0441\u0442 \u043d\u0430 \u0431\u0430\u0437\u0430\u0442\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "Type",               "\u0412\u0438\u0434 \u043d\u0430 \u0431\u0430\u0437\u0430\u0442\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "BequeathConnection", "\u041b\u043e\u043a\u0430\u043b\u043d\u0430 \u0432\u0440\u044a\u0437\u043a\u0430" },
	{ "Overwrite",          "\u0417\u0430\u043c\u044f\u043d\u0430" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "\u0413\u0440\u0435\u0448\u043a\u0430 \u043f\u0440\u0438 \u0441\u0432\u044a\u0440\u0437\u0432\u0430\u043d\u0435" },
	{ "ServerNotActive",    "\u0421\u044a\u0440\u0432\u0435\u0440\u044a\u0442 \u043d\u0435 \u0435 \u0430\u043a\u0442\u0438\u0432\u0435\u043d" }
	};

	/**
	 * Get Contsnts
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}
}   //  Res
