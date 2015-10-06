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
 *  @version    $Id: DBRes_ml.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_ms extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "Kotak Sambungan" },
	{ "Name",               "Nama" },
	{ "AppsHost",           "Aplikasi Induk" },
	{ "AppsPort",           "Port Aplikasi" },
	{ "TestApps",           "Uji Aplikasi" },
	{ "DBHost",             "Hos Pengkalan Data" },
	{ "DBPort",             "Port Pengkalan Data" },
	{ "DBName",             "Nama Pengkalan Data" },
	{ "DBUidPwd",           "Pengguna / Kata-laluan" },
	{ "ViaFirewall",        "menerusi Pendinding" },
	{ "FWHost",             "Hos Pendinding" },
	{ "FWPort",             "Port Pendinding" },
	{ "TestConnection",     "Uji Pengkalan Data" },
	{ "Type",               "Jenis Pengkalan Data" },
	{ "BequeathConnection", "Memberi Sambungan" },
	{ "Overwrite",          "Mengatasi" },
	{ "ConnectionProfile",	"Sambungan" },
	{ "LAN",		 		"Rangkaian Dalaman" },
	{ "TerminalServer",		"Pelayan Terminal" },
	{ "VPN",		 		"Rangkaian Maya" },
	{ "WAN", 				"Rangkaian Luar" },
	{ "ConnectionError",    "Kesilapan Sambungan" },
	{ "ServerNotActive",    "Pelayan Tidak Aktif" }
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
