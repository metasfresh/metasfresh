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
public final class ALoginRes extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Connection" },
	{ "Defaults",           "Defaults" },
	{ "Login",              "Login" },
	{ "File",               "&File" },
	{ "Exit",               "Exit" },
	{ "Help",               "&Help" },
	{ "About",              "About" },
	{ "Host",               "&Server" },
	{ "Database",           "Database" },
	{ "User",               "&User ID" },
	{ "EnterUser",          "Enter Application User ID" },
	{ "Password",           "&Password" },
	{ "EnterPassword",      "Enter Application Password" },
	{ "Language",           "&Language" },
	{ "SelectLanguage",     "Select your language" },
	{ "Role",               "&Role" },
	{ "Client",             "&Client" },
	{ "Organization",       "&Organization" },
	{ "Date",               "&Date" },
	{ "Warehouse",          "&Warehouse" },
	{ "Printer",            "Prin&ter" },
	{ "Connected",          "Connected" },
	{ "NotConnected",       "Not Connected" },
	{ "DatabaseNotFound",   "Database not found" },
	{ "UserPwdError",       "User does not match password" },
	{ "RoleNotFound",       "Role not found/complete" },
	{ "Authorized",         "Authorized" },
	{ "Ok",                 "&Ok" },
	{ "Cancel",             "&Cancel" },
	{ "VersionConflict",    "Version Conflict:" },
	{ "VersionInfo",        "Server <> Client" },
	{ "PleaseUpgrade",      "Please download new Version from Server" }
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
