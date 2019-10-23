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
package org.compiere.apps;

import java.util.ListResourceBundle;

/**
 *  Swedish Base Resource Bundle Translation
 *
 * 	@author 	Thomas Dilts
 * 	@version 	$Id: ALoginRes_sv.java, 2007/04/15 09:38:27 usrdno Exp $
 */
public final class ALoginRes_sv extends ListResourceBundle
{
	// TODO Run native2ascii to convert to plain ASCII !! 
	
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Anslutning" },
	{ "Defaults",           "Standardinst\u00e4llningar" },
	{ "Login",              "ADempiere inloggning" },
	{ "File",               "Fil" },
	{ "Exit",               "Avsluta" },
	{ "Help",               "Hj\u00e4lp" },
	{ "About",              "Om" },
	{ "Host",               "V\u00e4rddator" },
	{ "Database",           "Databas" },
	{ "User",               "Anv\u00e4ndarnamn" },
	{ "EnterUser",          "Ange anv\u00e4ndarnamn" },
	{ "Password",           "L\u00f6senord" },
	{ "EnterPassword",      "Ange l\u00f6senord" },
	{ "Language",           "Spr\u00e5k" },
	{ "SelectLanguage",     "V\u00e4lj spr\u00e5k" },
	{ "Role",               "Roll" },
	{ "Client",             "Klient" },
	{ "Organization",       "Organisation" },
	{ "Date",               "Datum" },
	{ "Warehouse",          "Lager" },
	{ "Printer",            "Skrivare" },
	{ "Connected",          "Ansluten" },
	{ "NotConnected",       "Ej ansluten" },
	{ "DatabaseNotFound",   "Hittade inte databasen" },
	{ "UserPwdError",       "Felaktig anv\u00e4ndare/l\u00f6senord" },
	{ "RoleNotFound",       "Hittade inte rollen" },
	{ "Authorized",         "Auktoriserad" },
	{ "Ok",                 "Ok" },
	{ "Cancel",             "Avbryt" },
	{ "VersionConflict",    "Versionskonflikt:" },
	{ "VersionInfo",        "Server <> Klient" },
	{ "PleaseUpgrade",      "Uppgradering n\u00f6dv\u00e4ndig" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes
