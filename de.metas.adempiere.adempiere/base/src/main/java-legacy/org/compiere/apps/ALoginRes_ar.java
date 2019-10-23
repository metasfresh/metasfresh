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
 *  Login Resource Strings (Arab)
 *
 *  @author     Jorg Janke
 *  @version    $Id: ALoginRes_ar.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ALoginRes_ar extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",             "\u0631\u0628\u0637" },
	{ "Defaults",               "\u0627\u0650\u0641\u062a\u0631\u0627\u0636\u064a" },
	{ "Login",                  "\u0639\u0628\u0648\u0631 \u0643\u0645\u0628\u064a\u0631" },
	{ "File",                   "\u0645\u0644\u0641" },
	{ "Exit",                   "\u062e\u0631\u0648\u062c" },
	{ "Help",                   "\u0645\u0633\u0627\u0639\u062f\u0629" },
	{ "About",                  "\u0646\u0628\u0630\u0629" },
	{ "Host",                   "\u0645\u0648\u0632\u0639" },
	{ "Database",               "\u0642\u0627\u0639\u062f\u0629 \u0628\u064a\u0627\u0646\u0627\u062a" },
	{ "User",                   "\u0645\u0639\u0631\u0651\u0641 \u0627\u0644\u0645\u0633\u062a\u0639\u0645\u0644" },
	{ "EnterUser",              "\u0623\u062f\u062e\u0644 \u0645\u0639\u0631\u0651\u0641 \u0645\u0633\u062a\u0639\u0645\u0644 \u0627\u0644\u062a\u0637\u0628\u064a\u0642" },
	{ "Password",               "\u0643\u0644\u0645\u0629 \u0627\u0644\u0633\u0631" },
	{ "EnterPassword",          "\u0623\u062f\u062e\u0644 \u0643\u0644\u0645\u0629 \u0633\u0631 \u0627\u0644\u062a\u0637\u0628\u064a\u0642" },
	{ "Language",               "\u0627\u0644\u0644\u0651\u064f\u063a\u0629" },
	{ "SelectLanguage",         "\u0627\u0650\u062e\u062a\u0631 \u0644\u063a\u062a\u0643" },
	{ "Role",                   "\u0627\u0644\u062f\u0651\u064e\u0648\u0631" },
	{ "Client",                 "\u0648\u0643\u064a\u0644" },
	{ "Organization",           "\u0627\u0644\u0645\u0624\u0633\u0651\u064e\u0633\u0629" },
	{ "Date",                   "\u0627\u0644\u062a\u0627\u0631\u064a\u062e" },
	{ "Warehouse",              "\u0627\u0644\u0645\u062e\u0632\u0646" },
	{ "Printer",                "\u0627\u0644\u0622\u0644\u0629 \u0627\u0644\u0637\u0651\u064e\u0627\u0628\u0639\u0629" },
	{ "Connected",              "\u0645\u0631\u062a\u0628\u0637" },
	{ "NotConnected",           "\u063a\u064a\u0631 \u0645\u0631\u062a\u0628\u0637" },
	{ "DatabaseNotFound",       "\u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a \u0645\u0641\u0642\u0648\u062f\u0629" },
	{ "UserPwdError",           "\u0627\u0644\u0645\u0633\u062a\u0639\u0645\u0644 \u0644\u0627 \u064a\u0648\u0627\u0626\u0645 \u0643\u0644\u0645\u0629 \u0627\u0644\u0633\u0631" },
	{ "RoleNotFound",           "\u0627\u0644\u062f\u0651\u064e\u0648\u0631 \u0645\u0641\u0642\u0648\u062f" },
	{ "Authorized",             "\u0645\u0633\u0645\u0648\u062d \u0644\u0647" },
	{ "Ok",                     "\u0646\u0639\u0645" },
	{ "Cancel",                 "\u0625\u0644\u063a\u0627\u0621" },
	{ "VersionConflict",        "\u062a\u0636\u0627\u0631\u0628 \u0635\u064a\u063a" },
	{ "VersionInfo",            "\u0645\u0648\u0632\u0639 <> \u062d\u0631\u064a\u0641" },
	{ "PleaseUpgrade",          "\u0627\u0644\u0631\u0651\u064e\u062c\u0627\u0621 \u0623\u062c\u0631 \u0628\u0631\u0646\u0627\u0645\u062c \u0627\u0644\u062a\u062d\u064a\u064a\u0646" }
	};

	/**
	 *  Get Contents
	 *  @return content
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes_ar_TN
