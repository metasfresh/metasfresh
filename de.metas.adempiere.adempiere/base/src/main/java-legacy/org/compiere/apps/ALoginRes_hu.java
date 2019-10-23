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
 *  Base Resource Bundle.
 *  If you translate it, make sure that you convert the file to ASCII via
 *  native2ascii 
 *  http://java.sun.com/j2se/1.5.0/docs/tooldocs/windows/native2ascii.html
 *  The non ASCII characters need to be converted to unicode - e.g. \u00ab
 *  This makes it less readable in the source, but viewable for everyone
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ALoginRes.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_hu extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Kapcsolat" },
	{ "Defaults",           "Alapértelmezés" },
	{ "Login",              "ADempiere Belépés" },
	{ "File",               "Fájl" },
	{ "Exit",               "Kilépés" },
	{ "Help",               "Súgó" },
	{ "About",              "Névjegy" },
	{ "Host",               "Szerver" },
	{ "Database",           "Adatbázis" },
	{ "User",               "Felhasználó ID" },
	{ "EnterUser",          "Írja be a felhasználó ID-t" },
	{ "Password",           "Jelszó" },
	{ "EnterPassword",      "Írja be a jelszavát" },
	{ "Language",           "Nyelv" },
	{ "SelectLanguage",     "Válasszon nyelvet" },
	{ "Role",               "Szerepkör" },
	{ "Client",             "Vállalat" },
	{ "Organization",       "Szervezet" },
	{ "Date",               "Dátum" },
	{ "Warehouse",          "Raktár" },
	{ "Printer",            "Nyomtató" },
	{ "Connected",          "Csatlakoztatva" },
	{ "NotConnected",       "Nincs csatlakoztatva" },
	{ "DatabaseNotFound",   "Az adatbázis nem található" },
	{ "UserPwdError",       "A felhasználó vagy jelszó hibás" },
	{ "RoleNotFound",       "A szerepkör nem található" },
	{ "Authorized",         "Jogosultság ellenőrizve" },
	{ "Ok",                 "Ok" },
	{ "Cancel",             "Mégsem" },
	{ "VersionConflict",    "Verzió ütközés:" },
	{ "VersionInfo",        "Szerver <> Kliens" },
	{ "PleaseUpgrade",      "Töltse le a program új verzióját a szerverről" }
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
