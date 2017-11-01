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
 *  Connection Resource Strings (German)
 *
 *  @author     Jorg Janke
 *  @version    $Id: DBRes_de.java,v 1.3 2006/08/30 20:30:44 comdivision Exp $
 */
public class DBRes_de extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "Verbindung" },
	{ "Name",               "Name" },
	{ "AppsHost",           "Applikations-Server" },
	{ "AppsPort",           "Applikations-Port" },
	{ "TestApps",           "Teste Verbindung zur Applikation" },
	{ "DBHost",             "Datenbank-Server" },
	{ "DBPort",             "Datenbank-Port" },
	{ "DBName",             "Datenbank-Name" },
	{ "DBUidPwd",           "Nutzer / Passwort" },
	{ "ViaFirewall",        "\u00fcber Firewall" },
	{ "FWHost",             "Firewall-Server" },
	{ "FWPort",             "Firewall-Port" },
	{ "TestConnection",     "Teste Verbindung zur Datenbank" },
	{ "Type",               "Datenbank-Typ" },
	{ "BequeathConnection", "Bequeath-Verbindung" },
	{ "Overwrite",          "\u00fcberschreiben" },
	{ "ConnectionProfile",	"Verbindungsprofil" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "Verbindungsfehler" },
	{ "ServerNotActive",    "Server nicht erreichbar" }
	};

	/**
	 * Get Contents
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  Res_de
