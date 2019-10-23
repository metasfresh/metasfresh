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
 * 	@author 	Stefan Christians 
 * 	@version 	$Id: ALoginRes_ja.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_ja extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "\u63a5\u7d9a" },
	{ "Defaults",           "\u30c7\u30d5\u30a9\u30eb\u30c8" },
	{ "Login",              "\u30a2\u30c7\u30f3\u30d4\u30a8\u30fc\u30ec \u30ed\u30b0\u30a4\u30f3" },
	{ "File",               "\u30d5\u30a1\u30a4\u30eb" },
	{ "Exit",               "\u7d42\u4e86" },
	{ "Help",               "\u30d8\u30eb\u30d7" },
	{ "About",              "\u60c5\u5831" },
	{ "Host",               "\u30b5\u30fc\u30d0\u30fc" },
	{ "Database",           "\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9" },
	{ "User",               "\u30e6\u30fc\u30b6\u30fc" },
	{ "EnterUser",          "\u30a2\u30d7\u30ea\u30b1\u30fc\u30b7\u30e7\u30f3\u306e\u30e6\u30fc\u30b6\u30fc\u540d\u3092\u5165\u529b\u3057\u3066\u4e0b\u3055\u3044" },
	{ "Password",           "\u30d1\u30b9\u30ef\u30fc\u30c9" },
	{ "EnterPassword",      "\u30a2\u30d7\u30ea\u30b1\u30fc\u30b7\u30e7\u30f3\u306e\u30d1\u30b9\u30ef\u30fc\u30c9\u3092\u5165\u529b\u3057\u3066\u4e0b\u3055\u3044" },
	{ "Language",           "\u8a00\u8a9e" },
	{ "SelectLanguage",     "\u8a00\u8a9e\u3092\u9078\u629e\u3057\u3066\u4e0b\u3055\u3044" },
	{ "Role",               "\u5f79\u5272" },
	{ "Client",             "\u4f1a\u793e" },
	{ "Organization",       "\u90e8\u8ab2" },
	{ "Date",               "\u65e5\u4ed8" },
	{ "Warehouse",          "\u5009\u5eab" },
	{ "Printer",            "\u30d7\u30ea\u30f3\u30bf" },
	{ "Connected",          "\u63a5\u7d9a\u6e08\u307f" },
	{ "NotConnected",       "\u672a\u63a5\u7d9a" },
	{ "DatabaseNotFound",   "\u30c7\u30fc\u30bf\u30d9\u30fc\u30b9\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093" },
	{ "UserPwdError",       "\u30e6\u30fc\u30b6\u30fc\u540d\u3068\u30d1\u30b9\u30ef\u30fc\u30c9\u304c\u4e00\u81f4\u3057\u307e\u305b\u3093" },
	{ "RoleNotFound",       "\u5f79\u5272\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093" },
	{ "Authorized",         "\u8a8d\u8a3c\u6e08\u307f" },
	{ "Ok",                 "OK" },
	{ "Cancel",             "\u30ad\u30e3\u30f3\u30bb\u30eb" },
	{ "VersionConflict",    "\u30d0\u30fc\u30b8\u30e7\u30f3\u304c\u5408\u3044\u307e\u305b\u3093:" },
	{ "VersionInfo",        "\u30b5\u30fc\u30d0 <> \u30af\u30e9\u30a4\u30a2\u30f3\u30c8" },
	{ "PleaseUpgrade",      "\u30d0\u30fc\u30b8\u30e7\u30f3\u30a2\u30c3\u30d7\u3057\u3066\u4e0b\u3055\u3044" }
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
