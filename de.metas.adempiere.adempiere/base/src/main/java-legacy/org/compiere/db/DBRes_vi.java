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
 *  @author     Bui Chi Trung
 *  @version    $Id: DBRes_vi.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_vi extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "K\u1EBFt n\u1ED1i" },
	{ "Name",               "T�n" },
	{ "AppsHost",           "M�y ch\u1EE7 \u1EE9ng d\u1EE5ng" },
	{ "AppsPort",           "C\u1ED5ng \u1EE9ng d\u1EE5ng" },
	{ "TestApps",           "Th\u1EED nghi\u1EC7m \u1EE9ng d\u1EE5ng" },
	{ "DBHost",             "M�y ch\u1EE7 CSDL" },
	{ "DBPort",             "C\u1ED5ng CSDL" },
	{ "DBName",             "T�n CSDL" },
	{ "DBUidPwd",           "Ng\u01B0\u1EDDi d�ng / M\u1EADt kh\u1EA9u" },
	{ "ViaFirewall",        "Qua b\u1EE9c t\u01B0\u1EDDng l\u1EEDa" },
	{ "FWHost",             "M�y ch\u1EE7 b\u1EE9c t\u01B0\u1EDDng l\u1EEDa" },
	{ "FWPort",             "C\u1ED5ng v�o b\u1EE9c t\u01B0\u1EDDng l\u1EEDa" },
	{ "TestConnection",     "Ki\u1EC3m tra CSDL" },
	{ "Type",               "Lo\u1EA1i CSDL" },
	{ "BequeathConnection", "Truy\u1EC1n l\u1EA1i k\u1EBFt n\u1ED1i" },
	{ "Overwrite",          "Ghi \u0111�" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "L\u1ED7i k\u1EBFt n\u1ED1i" },
	{ "ServerNotActive",    "M�y ch\u1EE7 hi\u1EC7n kh�ng ho\u1EA1t \u0111\u1ED9ng" }
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
