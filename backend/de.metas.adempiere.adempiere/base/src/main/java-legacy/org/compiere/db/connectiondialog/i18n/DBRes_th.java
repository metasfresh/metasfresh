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
 *  Connection Resource Strings (Thai)
 *
 *  @author     Sureeraya Limpaibul
 *  @version    $Id: DBRes_th.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_th extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog",  "Connection" },
	{ "Name",               "\u0e0a\u0e37\u0e48\u0e2d" },
	{ "AppsHost",           "\u0e41\u0e2d\u0e47\u0e1e\u0e1e\u0e25\u0e34\u0e40\u0e04\u0e0a\u0e31\u0e48\u0e19 \u0e42\u0e2e\u0e2a" },
	{ "AppsPort",           "\u0e41\u0e2d\u0e47\u0e1e\u0e1e\u0e25\u0e34\u0e40\u0e04\u0e0a\u0e31\u0e48\u0e19 \u0e1e\u0e2d\u0e23\u0e4c\u0e15" },
	{ "TestApps",           "\u0e17\u0e14\u0e2a\u0e2d\u0e1a\u0e41\u0e2d\u0e47\u0e1e\u0e1e\u0e25\u0e34\u0e40\u0e04\u0e0a\u0e31\u0e48\u0e19" },
	{ "DBHost",             "\u0e42\u0e2e\u0e2a\u0e02\u0e2d\u0e07\u0e10\u0e32\u0e19\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25" },
	{ "DBPort",             "\u0e1e\u0e2d\u0e23\u0e4c\u0e15\u0e02\u0e2d\u0e07\u0e10\u0e32\u0e19\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25" },
	{ "DBName",             "\u0e0a\u0e37\u0e48\u0e2d\u0e10\u0e32\u0e19\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25" },
	{ "DBUidPwd",           "\u0e0a\u0e37\u0e48\u0e2d\u0e1c\u0e39\u0e49\u0e43\u0e0a\u0e49 / \u0e23\u0e2b\u0e31\u0e2a\u0e1c\u0e48\u0e32\u0e19" },
	{ "ViaFirewall",        "\u0e1c\u0e48\u0e32\u0e19\u0e44\u0e1f\u0e23\u0e27\u0e2d\u0e25" },
	{ "FWHost",             "\u0e44\u0e1f\u0e23\u0e27\u0e2d\u0e25 \u0e42\u0e2e\u0e2a" },
	{ "FWPort",             "\u0e1e\u0e2d\u0e23\u0e4c\u0e15\u0e44\u0e1f\u0e23\u0e27\u0e2d\u0e25" },
	{ "TestConnection",     "\u0e17\u0e14\u0e2a\u0e2d\u0e1a\u0e10\u0e32\u0e19\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25" },
	{ "Type",               "\u0e1b\u0e23\u0e30\u0e40\u0e20\u0e17\u0e10\u0e32\u0e19\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25" },
	{ "BequeathConnection", "Bequeath Connection" },
	{ "Overwrite",          "\u0e1a\u0e31\u0e19\u0e17\u0e36\u0e01\u0e17\u0e31\u0e1a" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "\u0e01\u0e32\u0e23\u0e40\u0e0a\u0e37\u0e48\u0e2d\u0e21\u0e15\u0e48\u0e2d\u0e1c\u0e34\u0e14\u0e1e\u0e25\u0e32\u0e14" },
	{ "ServerNotActive",    "\u0e40\u0e0a\u0e34\u0e23\u0e4c\u0e1f\u0e40\u0e27\u0e2d\u0e23\u0e4c\u0e44\u0e21\u0e48\u0e41\u0e2d\u0e47\u0e04\u0e17\u0e35\u0e1f" }};

	/**
	 * Get Contsnts
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}
}   //  Res
