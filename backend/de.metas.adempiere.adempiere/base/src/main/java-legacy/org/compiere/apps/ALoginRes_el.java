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
 * 	@author 	Kyriakos Ioannidis - kioa@openway.gr
 * 	@version 	$Id: ALoginRes.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_el extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
    {
	{ "Connection",         "\u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7" },
	{ "Defaults",           "\u0395\u03c0\u03b9\u03bb\u03bf\u03b3\u03ad\u03c2" },
	{ "Login",              "ADempiere - \u0395\u03af\u03c3\u03bf\u03b4\u03bf\u03c2" },
	{ "File",               "\u0391\u03c1\u03c7\u03b5\u03af\u03bf" },
	{ "Exit",               "\u0388\u03be\u03bf\u03b4\u03bf\u03c2" },
	{ "Help",               "\u0392\u03bf\u03ae\u03b8\u03b5\u03b9\u03b1" },
	{ "About",              "\u03a3\u03c7\u03b5\u03c4\u03b9\u03ba\u03ac" },
	{ "Host",               "\u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2" },
	{ "Database",           "\u0392\u03ac\u03c3\u03b7 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "User",               "\u03a7\u03c1\u03ae\u03c3\u03c4\u03b7\u03c2" },
	{ "EnterUser",          "\u039a\u03b1\u03c4\u03b1\u03c7\u03c9\u03c1\u03ae\u03c3\u03c4\u03b5 \u03c4\u03bf \u038c\u03bd\u03bf\u03bc\u03b1 \u03c4\u03bf\u03c5 \u03a7\u03c1\u03ae\u03c3\u03c4\u03b7" },
	{ "Password",           "\u039a\u03c9\u03b4\u03b9\u03ba\u03cc\u03c2" },
	{ "EnterPassword",      "\u039a\u03b1\u03c4\u03b1\u03c7\u03c9\u03c1\u03ae\u03c3\u03c4\u03b5 \u03c4\u03bf\u03bd \u039a\u03c9\u03b4\u03b9\u03ba\u03cc \u03a0\u03c1\u03cc\u03c3\u03b2\u03b1\u03c3\u03b7\u03c2" },
	{ "Language",           "\u0393\u03bb\u03ce\u03c3\u03c3\u03b1" },
	{ "SelectLanguage",     "\u0395\u03c0\u03b9\u03bb\u03ad\u03be\u03c4\u03b5 \u03c4\u03b7\u03bd \u0393\u03bb\u03ce\u03c3\u03c3\u03b1" },
	{ "Role",               "\u03a1\u03cc\u03bb\u03bf\u03c2" },
	{ "Client",             "\u0395\u03c4\u03b1\u03b9\u03c1\u03af\u03b1" },
	{ "Organization",       "\u03a4\u03bc\u03ae\u03bc\u03b1" },
	{ "Date",               "\u0397\u03bc\u03b5\u03c1\u03bf\u03bc\u03b7\u03bd\u03af\u03b1" },
	{ "Warehouse",          "\u0391\u03c0\u03bf\u03b8\u03ae\u03ba\u03b7" },
	{ "Printer",            "\u0395\u03ba\u03c4\u03c5\u03c0\u03c9\u03c4\u03ae\u03c2" },
	{ "Connected",          "\u03a3\u03c5\u03bd\u03b4\u03ad\u03b8\u03b7\u03ba\u03b5" },
	{ "NotConnected",       "\u03a7\u03c9\u03c1\u03af\u03c2 \u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7" },
	{ "DatabaseNotFound",   "\u0394\u03b5\u03bd \u03b2\u03c1\u03ad\u03b8\u03b7\u03ba\u03b5 \u0392\u03ac\u03c3\u03b7 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "UserPwdError",       "\u03a3\u03c6\u03ac\u03bb\u03bc\u03b1 \u03c3\u03c4\u03bf \u038c\u03bd\u03bf\u03bc\u03b1 \u03a7\u03c1\u03ae\u03c3\u03c4\u03b7 / \u039a\u03c9\u03b4\u03b9\u03ba\u03cc \u03a0\u03c1\u03cc\u03c3\u03b2\u03b1\u03c3\u03b7\u03c2" },
	{ "RoleNotFound",       "\u0394\u03b5\u03bd \u03b2\u03c1\u03ad\u03b8\u03b7\u03ba\u03b5 \u03bf \u03a1\u03cc\u03bb\u03bf\u03c2" },
	{ "Authorized",         "\u0395\u03be\u03bf\u03c5\u03c3\u03b9\u03bf\u03b4\u03bf\u03c4\u03ae\u03b8\u03b7\u03ba\u03b5" },
	{ "Ok",                 "Ok" },
	{ "Cancel",             "\u0391\u03ba\u03cd\u03c1\u03c9\u03c3\u03b7" },
	{ "VersionConflict",    "\u039b\u03b1\u03bd\u03b8\u03b1\u03c3\u03bc\u03ad\u03bd\u03b7 \u0388\u03ba\u03b4\u03bf\u03c3\u03b7:" },
	{ "VersionInfo",        "\u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2 <> \u03a5\u03c0\u03bf\u03bb\u03bf\u03b3\u03b9\u03c3\u03c4\u03ae\u03c2" },
	{ "PleaseUpgrade",      "\u03a0\u03b1\u03c1\u03b1\u03ba\u03b1\u03bb\u03ce \u03bd\u03b1 \u03b1\u03bd\u03b1\u03b2\u03b1\u03b8\u03bc\u03af\u03c3\u03b5\u03c4\u03b5\u2026" }
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
