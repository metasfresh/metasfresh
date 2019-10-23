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
 * 	@author 	ZhaoXing Meng
 * 	@version 	$Id: ALoginRes_zh_CN.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_zh_CN extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "\u8fde\u673a" },
	{ "Defaults",           "\u9ed8\u8ba4\u8bbe\u7f6e" },
	{ "Login",              "ADempiere \u767b\u5f55" },
	{ "File",               "\u6587\u4ef6" },
	{ "Exit",               "\u9000\u51fa" },
	{ "Help",               "\u5e2e\u52a9" },
	{ "About",              "\u5173\u4e8e" },
	{ "Host",               "\u4e3b\u673a" },
	{ "Database",           "\u6570\u636e\u5e93" },
	{ "User",               "\u7528\u6237\u53f7" },
	{ "EnterUser",          "\u8bf7\u8f93\u5165\u7528\u6237\u53f7" },
	{ "Password",           "\u53e3\u4ee4" },
	{ "EnterPassword",      "\u8bf7\u8f93\u5165\u53e3\u4ee4" },
	{ "Language",           "\u8bed\u8a00" },
	{ "SelectLanguage",     "\u9009\u62e9\u8bed\u8a00" },
	{ "Role",               "\u89d2\u8272" },
	{ "Client",             "\u5b9e\u4f53" },
	{ "Organization",       "\u7EC4\u7EC7" },		// \u673a\u6784
	{ "Date",               "\u65e5\u671f" },
	{ "Warehouse",          "\u4ed3\u5e93" },
	{ "Printer",            "\u6253\u5370\u673a" },
	{ "Connected",          "\u5df2\u5728\u7ebf" },
	{ "NotConnected",       "\u672a\u5728\u7ebf" },
	{ "DatabaseNotFound",   "\u672a\u627e\u5230\u6570\u636e\u5e93" },
	{ "UserPwdError",       "\u7528\u6237\u53f7\u53e3\u4ee4\u4e0d\u6b63\u786e" },
	{ "RoleNotFound",       "\u6ca1\u6709\u6b64\u89d2\u8272" },
	{ "Authorized",         "\u5df2\u6388\u6743" },
	{ "Ok",                 "\u786e\u5b9a" },
	{ "Cancel",             "\u64a4\u6d88" },
	{ "VersionConflict",    "\u7248\u672c\u51b2\u7a81\uff1a" },
	{ "VersionInfo",        "\u670d\u52a1\u5668\u7aef <> \u5ba2\u6237\u7aef" },
	{ "PleaseUpgrade",      "\u8bf7\u5347\u7ea7\u7a0b\u5e8f" }
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
