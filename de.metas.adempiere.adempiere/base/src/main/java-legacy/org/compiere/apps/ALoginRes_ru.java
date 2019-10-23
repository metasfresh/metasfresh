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
 * 	@author 	Vyacheslav Pedak
 * 	@version 	$Id: ALoginRes_ru.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_ru extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "\u0421\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u0438\u0435" },
	{ "Defaults",           "\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u044f \u043f\u043e \u0443\u043c\u043e\u043b\u0447\u0430\u043d\u0438\u044e" },
	{ "Login",              "\u0412\u0445\u043e\u0434 \u0432 ADempiere" },
	{ "File",               "\u0424\u0430\u0439\u043b" },
	{ "Exit",               "\u0412\u044b\u0445\u043e\u0434" },
	{ "Help",               "\u041f\u043e\u043c\u043e\u0449\u044c" },
	{ "About",              "\u041e \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0435" },
	{ "Host",               "\u0425\u043e\u0441\u0442" },
	{ "Database",           "\u0411\u0430\u0437\u0430 \u0434\u0430\u043d\u043d\u044b\u0445" },
	{ "User",               "\u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c" },
	{ "EnterUser",          "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f" },
	{ "Password",           "\u041f\u0430\u0440\u043e\u043b\u044c" },
	{ "EnterPassword",      "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u044c" },
	{ "Language",           "\u042f\u0437\u044b\u043a" },
	{ "SelectLanguage",     "\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u0432\u0430\u0448 \u044f\u0437\u044b\u043a" },
	{ "Role",               "\u0420\u043e\u043b\u044c" },
	{ "Client",             "\u041a\u043b\u0438\u0435\u043d\u0442" },
	{ "Organization",       "\u041e\u0440\u0433\u0430\u043d\u0438\u0437\u0430\u0446\u0438\u044f" },
	{ "Date",               "\u0414\u0430\u0442\u0430" },
	{ "Warehouse",          "\u0421\u043a\u043b\u0430\u0434" },
	{ "Printer",            "\u041f\u0440\u0438\u043d\u0442\u0435\u0440" },
	{ "Connected",          "\u0421\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u043e" },
	{ "NotConnected",       "\u041d\u0435 \u0441\u043e\u0435\u0434\u0438\u043d\u0435\u043d\u043e" },
	{ "DatabaseNotFound",   "\u0411\u0430\u0437\u0430 \u0434\u0430\u043d\u043d\u044b\u0445 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430" },
	{ "UserPwdError",       "\u041d\u0435 \u0432\u0435\u0440\u043d\u044b\u0439 \u043f\u0430\u0440\u043e\u043b\u044c" },
	{ "RoleNotFound",       "\u041d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430 \u0440\u043e\u043b\u044c" },
	{ "Authorized",         "\u0410\u0432\u0442\u043e\u0440\u0438\u0437\u043e\u0432\u0430\u043d" },
	{ "Ok",                 "\u0414\u0430" },
	{ "Cancel",             "\u041e\u0442\u043c\u0435\u043d\u0430" },
	{ "VersionConflict",    "\u041a\u043e\u043d\u0444\u043b\u0438\u043a\u0442 \u0432\u0435\u0440\u0441\u0438\u0439:" },
	{ "VersionInfo",        "\u0421\u0435\u0440\u0432\u0435\u0440 <> \u041a\u043b\u0438\u0435\u043d\u0442" },
	{ "PleaseUpgrade",      "\u041f\u043e\u0436\u0430\u043b\u0443\u0439\u0441\u0442\u0430 \u0437\u0430\u043f\u0443\u0441\u0442\u0438\u0442\u0435 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0443 \u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u044f" }
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
