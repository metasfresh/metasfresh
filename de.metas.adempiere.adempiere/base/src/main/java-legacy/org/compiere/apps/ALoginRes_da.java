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
 *  Base Resource Bundle
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ALoginRes_da.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_da extends ListResourceBundle
{
	// TODO Run native2ascii to convert to plain ASCII !! 
	
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Forbindelse" },
	{ "Defaults",           "Basis" },
	{ "Login",              "ADempiere: Log på" },
	{ "File",               "Fil" },
	{ "Exit",               "Afslut" },
	{ "Help",               "Hjælp" },
	{ "About",              "Om" },
	{ "Host",               "Vært" },
	{ "Database",           "Database" },
	{ "User",               "Bruger-ID" },
	{ "EnterUser",          "Angiv bruger-ID til program" },
	{ "Password",           "Adgangskode" },
	{ "EnterPassword",      "Angiv adgangskode til program" },
	{ "Language",           "Sprog" },
	{ "SelectLanguage",     "Vælg sprog" },
	{ "Role",               "Rolle" },
	{ "Client",             "Firma" },
	{ "Organization",       "Organisation" },
	{ "Date",               "Dato" },
	{ "Warehouse",          "Lager" },
	{ "Printer",            "Printer" },
	{ "Connected",          "Forbindelse OK" },
	{ "NotConnected",       "Ingen forbindelse" },
	{ "DatabaseNotFound",   "Database blev ikke fundet" },
	{ "UserPwdError",       "Forkert bruger til adgangskode" },
	{ "RoleNotFound",       "Rolle blev ikke fundet/afsluttet" },
	{ "Authorized",         "Tilladelse OK" },
	{ "Ok",                 "OK" },
	{ "Cancel",             "Annullér" },
	{ "VersionConflict",    "Konflikt:" },
	{ "VersionInfo",        "Server <> Klient" },
	{ "PleaseUpgrade",      "Kør opdateringsprogram" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes_da
 //  ALoginRes-da
