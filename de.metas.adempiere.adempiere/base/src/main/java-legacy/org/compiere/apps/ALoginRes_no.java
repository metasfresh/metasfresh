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
 *  Norwegian Base Resource Bundle Translation
 *
 * 	@author 	Olaf Slazak L�ken
 * 	@version 	$Id: ALoginRes_no.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_no extends ListResourceBundle
{
	// TODO Run native2ascii to convert to plain ASCII !! 
	
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Forbindelse" },
	{ "Defaults",           "Vanlige" },
	{ "Login",              "ADempiere Loginn" },
	{ "File",               "Fil" },
	{ "Exit",               "Avslutt" },
	{ "Help",               "Hjelp" },
	{ "About",              "Om" },
	{ "Host",               "Maskin" },
	{ "Database",           "Database" },
	{ "User",               "Bruker ID" },
	{ "EnterUser",          "Skriv  Applikasjon Bruker ID" },
	{ "Password",           "Passord" },
	{ "EnterPassword",      "Skriv Applikasjon Passordet" },
	{ "Language",           "Spr�k" },
	{ "SelectLanguage",     "Velg �nsket Spr�k" },
	{ "Role",               "Rolle" },
	{ "Client",             "Klient" },
	{ "Organization",       "Organisasjon" },
	{ "Date",               "Dato" },
	{ "Warehouse",          "Varehus" },
	{ "Printer",            "Skriver" },
	{ "Connected",          "Oppkoblett" },
	{ "NotConnected",       "Ikke Oppkoblet" },
	{ "DatabaseNotFound",   "Database ikke funnet" },
	{ "UserPwdError",       "Bruker passer ikke til passordet" },
	{ "RoleNotFound",       "Role not found/complete" },
	{ "Authorized",         "Autorisert" },
	{ "Ok",                 "Ok" },
	{ "Cancel",             "Avbryt" },
	{ "VersionConflict",    "Versions Konflikt:" },
	{ "VersionInfo",        "Server <> Klient" },
	{ "PleaseUpgrade",      "Vennligst kj�r oppdaterings programet" }
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
