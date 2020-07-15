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
 *  @author     Adam Bodurka
 *  @version    $Id: DBRes_pl.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_pl extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "Po\u0142\u0105czenie z Server" },
	{ "Name",               "Nazwa" },
	{ "AppsHost",           "Host Aplikacji" },
	{ "AppsPort",           "Port Aplikacji" },
	{ "TestApps",           "Test Aplikacji" },
	{ "DBHost",             "Host Bazy Danych" },
	{ "DBPort",             "Port Bazy Danych" },
	{ "DBName",             "Nazwa Bazy Danych" },
	{ "DBUidPwd",           "U\u017cytkownik / Has\u0142o" },
	{ "ViaFirewall",        "via Firewall" },
	{ "FWHost",             "Host Firewall-a" },
	{ "FWPort",             "Port Firewall-a" },
	{ "TestConnection",     "Test Bazy Danych" },
	{ "Type",               "Typ Bazy Danych" },
	{ "BequeathConnection", "Zapisuj Po\u0142\u0105czenie" },
	{ "Overwrite",          "Nadpisuj" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "B\u0142\u0105d po\u0142\u0105czenia" },
	{ "ServerNotActive",    "Serwer nie jest aktywny" }
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
