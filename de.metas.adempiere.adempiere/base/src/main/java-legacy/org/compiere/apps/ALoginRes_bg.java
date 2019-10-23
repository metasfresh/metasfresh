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
 *  Login Resource Bundle (Bulgarian).
 *  native2ascii -encoding Cp1251 ALoginRes_bg.java ALoginRes_bg.java.txt
 *
 * 	@author 	Plamen Niikolov
 * 	@version 	$Id: ALoginRes_bg.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_bg extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",     "\u0412\u0440\u044a\u0437\u043a\u0430" },
	{ "Defaults",       "\u041f\u043e \u043f\u043e\u0434\u0440\u0430\u0437\u0431\u0438\u0440\u0430\u043d\u0435" },
	{ "Login",          "\u0421\u0432\u044a\u0440\u0437\u0432\u0430\u043d\u0435 \u0438 \u043e\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044f" },
	{ "File",           "\u0424\u0430\u0439\u043b" },
	{ "Exit",           "\u0418\u0437\u0445\u043e\u0434" },
	{ "Help",           "\u041f\u043e\u043c\u043e\u0449" },
	{ "About",          "\u0417\u0430 .." },
	{ "Host",           "\u0421\u044a\u0440\u0432\u0435\u0440" },
	{ "Database",       "\u0411\u0430\u0437\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "User",           "\u041f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b" },
	{ "EnterUser",      "\u0412\u044a\u0432\u0435\u0434\u0435\u0442\u0435 \u043f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b \u043d\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u0442\u043e" },
	{ "Password",       "\u041f\u0430\u0440\u043e\u043b\u0430" },
	{ "EnterPassword",  "\u0412\u044a\u0432\u0435\u0434\u0435\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u0430 \u0437\u0430 \u043f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b\u044f \u043d\u0430 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435\u0442\u043e" },
	{ "Language",       "\u0415\u0437\u0438\u043a" },
	{ "SelectLanguage", "\u0418\u0437\u0431\u0435\u0440\u0435\u0442\u0435 \u0412\u0430\u0448\u0438\u044f \u0435\u0437\u0438\u043a" },
	{ "Role",           "\u041f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u0438" },
	{ "Client",         "\u041a\u043b\u0438\u0435\u043d\u0442(\u0445\u043e\u043b\u0434\u0438\u043d\u0433)" },
	{ "Organization",   "\u041e\u0440\u0433\u0430\u043d\u0438\u0437\u0430\u0446\u0438\u044f(\u0444\u0438\u0440\u043c\u0430)" },
	{ "Date",           "\u0414\u0430\u0442\u0430" },
	{ "Warehouse",      "\u0421\u043a\u043b\u0430\u0434" },
	{ "Printer",        "\u041f\u0440\u0438\u043d\u0442\u0435\u0440" },
	{ "Connected",      "\u0412\u0440\u044a\u0437\u043a\u0430\u0442\u0430 \u0435 \u043e\u0441\u044a\u0449\u0435\u0441\u0442\u0432\u0435\u043d\u0430" },
	{ "NotConnected",   "\u0412\u0440\u044a\u0437\u043a\u0430\u0442\u0430 \u043d\u0435 \u0435 \u043e\u0441\u044a\u0449\u0435\u0441\u0442\u0432\u0435\u043d\u0430" },
	{ "DatabaseNotFound", "\u041b\u0438\u043f\u0441\u0432\u0430 \u0431\u0430\u0437\u0430 \u0434\u0430\u043d\u043d\u0438" },
	{ "UserPwdError",   "\u041f\u043e\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043b\u044f \u0438/\u0438\u043b\u0438 \u043f\u0430\u0440\u043e\u043b\u0430\u0442\u0430 \u0441\u0430 \u0433\u0440\u0435\u0448\u043d\u0438" },
	{ "RoleNotFound",   "\u041b\u0438\u043f\u0441\u0432\u0430\u0449\u0438 \u043f\u0440\u0438\u0432\u0438\u043b\u0435\u0433\u0438\u0438" },
	{ "Authorized",     "\u041e\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044f\u0442\u0430 \u0435 \u0443\u0441\u043f\u0435\u0448\u043d\u0430" },
	{ "Ok",             "\u0414\u0430" },
	{ "Cancel",         "\u041e\u0442\u043a\u0430\u0437" },
	{ "VersionConflict", "\u041a\u043e\u043d\u0444\u043b\u0438\u043a\u0442 \u043d\u0430 \u0432\u0435\u0440\u0441\u0438\u0438\u0442\u0435:" },
	{ "VersionInfo",    "\u0421\u044a\u0440\u0432\u0435\u0440 <> \u041a\u043b\u0438\u0435\u043d\u0442" },
	{ "PleaseUpgrade",  "\u041c\u043e\u043b\u044f \u0441\u0442\u0430\u0440\u0442\u0438\u0440\u0430\u0439\u0442\u0435 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u0430 \u0437\u0430 \u043e\u0431\u043d\u043e\u0432\u044f\u0432\u0430\u043d\u0435 \u043d\u0430 \u0432\u0435\u0440\u0441\u0438\u044f\u0442\u0430" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes_bg
