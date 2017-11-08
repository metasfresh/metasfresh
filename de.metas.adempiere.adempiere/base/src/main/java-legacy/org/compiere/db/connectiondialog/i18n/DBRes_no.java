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
 *  Norwegian Connection Resource Strings
 *
 *  @author     Olaf Slazak L�ken
 *  @version    $Id: DBRes_no.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_no extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "Forbindelse" },
	{ "Name",               "Navn" },
	{ "AppsHost",           "Applikasjon  Maskine" },
	{ "AppsPort",           "Applikasjon  Port" },
	{ "TestApps",           "Test Applikasjon " },
	{ "DBHost",             "Database Maskin" },
	{ "DBPort",             "Database Port" },
	{ "DBName",             "Database Navn" },
	{ "DBUidPwd",           "Bruker /Passord" },
	{ "ViaFirewall",        "Gjennom Brannmur" },
	{ "FWHost",             "Brannmur Maskin" },
	{ "FWPort",             "Brannmur Port" },
	{ "TestConnection",     "Test Database" },
	{ "Type",               "Database Type" },
	{ "BequeathConnection", "Bequeath Forbindelse" },
	{ "Overwrite",          "Overskriv" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "Feil ved Oppkobling" },
	{ "ServerNotActive",    "Server Ikke Aktivert" }
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
