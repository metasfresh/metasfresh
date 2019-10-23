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
 * 	@author 	kirinlin
 * 	@version 	$Id: ALoginRes_zh.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_zh extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "\u9023\u7dda" },
	{ "Defaults",           "\u9810\u8a2d\u503c" },
	{ "Login",              "ADempiere \u767b\u5165" },
	{ "File",               "\u6a94\u6848" },
	{ "Exit",               "\u96e2\u958b" },
	{ "Help",               "\u8aaa\u660e" },
	{ "About",              "\u95dc\u65bc" },
	{ "Host",               "\u4e3b\u6a5f" },
	{ "Database",           "\u8cc7\u6599\u5eab" },
	{ "User",               "\u5e33\u865f" },
	{ "EnterUser",          "\u8acb\u9375\u5165\u5e33\u865f" },
	{ "Password",           "\u5bc6\u78bc" },
	{ "EnterPassword",      "\u8acb\u9375\u5165\u5bc6\u78bc" },
	{ "Language",           "\u8a9e\u8a00" },
	{ "SelectLanguage",     "\u9078\u64c7\u8a9e\u8a00" },
	{ "Role",               "\u89d2\u8272" },
	{ "Client",             "\u5BE6\u9AD4" },	// \u5ba2\u6236\u7aef
	{ "Organization",       "\u7d44\u7e54" },
	{ "Date",               "\u65e5\u671f" },
	{ "Warehouse",          "\u5009\u5eab" },
	{ "Printer",            "\u5370\u8868\u6a5f" },
	{ "Connected",          "\u5df2\u9023\u7dda" },
	{ "NotConnected",       "\u672a\u9023\u7dda" },
	{ "DatabaseNotFound",   "\u627e\u4e0d\u5230\u8cc7\u6599\u5eab" },
	{ "UserPwdError",       "\u5e33\u865f\u5bc6\u78bc\u4e0d\u6b63\u78ba" },
	{ "RoleNotFound",       "\u6c92\u6709\u9019\u89d2\u8272" },
	{ "Authorized",         "\u5df2\u6388\u6b0a" },
	{ "Ok",                 "\u78ba\u5b9a" },
	{ "Cancel",             "\u53d6\u6d88" },
	{ "VersionConflict",    "\u7248\u672c\u885d\u7a81:" },
	{ "VersionInfo",        "\u4f3a\u670d\u7aef <> \u5ba2\u6236\u7aef" },
	{ "PleaseUpgrade",      "\u8acb\u57f7\u884c\u5347\u7d1a\u7a0b\u5f0f" }
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
