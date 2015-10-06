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
package org.compiere.install;

import java.util.ListResourceBundle;

/**
 *	Setup Resources
 *
 * 	@author 	kirinlin
 * 	@version 	$Id: SetupRes_zh.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_zh extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", "Adempiere \u4f3a\u670d\u5668\u8a2d\u5b9a" },
	{ "Ok", 				"\u78ba\u5b9a" },
	{ "File", 				"\u6a94\u6848" },
	{ "Exit", 				"\u96e2\u958b" },
	{ "Help", 				"\u8aaa\u660e" },
	{ "PleaseCheck", 		"\u8acb\u6aa2\u67e5" },
	{ "UnableToConnect",	"\u7121\u6cd5\u81ea Adempiere \u7db2\u7ad9\u5f97\u5230\u8aaa\u660e" },

	{ "AdempiereHomeInfo", 	"Adempiere \u4e3b\u76ee\u9304" },
	{ "AdempiereHome", 		"Adempiere Home" },
	{ "WebPortInfo", 		"\u7db2\u9801\u4f3a\u670d\u5668\u9023\u63a5\u57e0" },
	{ "WebPort", 			"Web Port" },
	{ "AppsServerInfo", 	"\u61c9\u7528\u4f3a\u670d\u5668\u540d\u7a31" },
	{ "AppsServer", 		"Apps Server" },
	{ "DatabaseTypeInfo", 	"\u8cc7\u6599\u5eab\u7a2e\u985e" },
	{ "DatabaseType", 		"Database Type" },
	{ "DatabaseNameInfo", 	"\u8cc7\u6599\u5eab\u540d\u7a31 " },
	{ "DatabaseName", 		"Database Name (SID)" },
	{ "DatabasePortInfo", 	"\u8cc7\u6599\u5eab\u9023\u63a5\u57e0" },
	{ "DatabasePort", 		"Database Port" },
	{ "DatabaseUserInfo", 	"Adempiere \u4f7f\u7528\u8cc7\u6599\u5eab\u7684\u5e33\u865f" },
	{ "DatabaseUser", 		"Database User" },
	{ "DatabasePasswordInfo", "Adempiere \u4f7f\u7528\u8cc7\u6599\u5eab\u7684\u5bc6\u78bc" },
	{ "DatabasePassword", 	"Database Password" },
	{ "TNSNameInfo", 		"TNS or Global Database Name" },
	{ "TNSName", 			"TNS Name" },
	{ "SystemPasswordInfo", "\u7cfb\u7d71\u5bc6\u78bc" },
	{ "SystemPassword", 	"System Password" },
	{ "MailServerInfo", 	"\u90f5\u4ef6\u4f3a\u670d\u5668" },
	{ "MailServer", 		"Mail Server" },
	{ "AdminEMailInfo", 	"Adempiere \u7ba1\u7406\u8005 EMail" },
	{ "AdminEMail", 		"Admin EMail" },
	{ "DatabaseServerInfo", "\u8cc7\u6599\u5eab\u540d\u7a31" },
	{ "DatabaseServer", 	"Database Server" },
	{ "JavaHomeInfo", 		"Java \u4e3b\u76ee\u9304" },
	{ "JavaHome", 			"Java Home" },
	{ "JNPPortInfo", 		"\u61c9\u7528\u4f3a\u670d\u5668\u7684 JNP \u9023\u63a5\u57e0" },
	{ "JNPPort", 			"JNP Port" },
	{ "MailUserInfo", 		"Adempiere Mail \u5e33\u865f" },
	{ "MailUser", 			"Mail User" },
	{ "MailPasswordInfo", 	"Adempiere Mail \u5e33\u865f\u7684\u5bc6\u78bc" },
	{ "MailPassword", 		"Mail Password" },
	{ "KeyStorePassword",		"Key Store Password" },
	{ "KeyStorePasswordInfo",	"Password for SSL Key Store" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Java VM Vendor"},
	{ "AppsType",				"Server Type"},
	{ "AppsTypeInfo",			"J2EE Application Server Type"},
	{ "DeployDir",				"Deployment"},
	{ "DeployDirInfo",			"J2EE Deployment Directory"},
	{ "ErrorDeployDir",			"Error Deployment Directory"},
	//
	{ "TestInfo", 			"\u8a2d\u5b9a\u6e2c\u8a66" },
	{ "Test", 				"Test" },
	{ "SaveInfo", 			"\u5132\u5b58\u8a2d\u5b9a" },
	{ "Save", 				"Save" },
	{ "HelpInfo", 			"\u53d6\u5f97\u8aaa\u660e" },

	{ "ServerError", 		"\u4f3a\u670d\u5668\u8a2d\u5b9a\u932f\u8aa4" },
	{ "ErrorJavaHome", 		"Java \u4e3b\u76ee\u9304\u932f\u8aa4" },
	{ "ErrorAdempiereHome", 	"Adempiere \u4e3b\u76ee\u9304\u932f\u8aa4" },
	{ "ErrorAppsServer", 	"\u61c9\u7528\u4f3a\u670d\u5668\u932f\u8aa4 (do not use localhost)" },
	{ "ErrorWebPort", 		"\u7db2\u9801\u4f3a\u670d\u5668\u9023\u63a5\u57e0\u932f\u8aa4" },
	{ "ErrorJNPPort", 		"JNP \u9023\u63a5\u57e0\u932f\u8aa4" },
	{ "ErrorDatabaseServer", "\u8cc7\u6599\u5eab\u932f\u8aa4 (do not use localhost)" },
	{ "ErrorDatabasePort", 	"\u8cc7\u6599\u5eab Port \u932f\u8aa4" },
	{ "ErrorJDBC", 			"JDBC \u9023\u63a5\u932f\u8aa4" },
	{ "ErrorTNS", 			"TNS \u9023\u63a5\u932f\u8aa4" },
	{ "ErrorMailServer", 	"\u90f5\u4ef6\u4f3a\u670d\u5668\u932f\u8aa4 (do not use localhost)" },
	{ "ErrorMail", 			"\u90f5\u4ef6\u932f\u8aa4" },
	{ "ErrorSave", 			"\u5b58\u6a94\u932f\u8aa4" },

	{ "EnvironmentSaved",	"\u74b0\u5883\u8a2d\u5b9a\u5132\u5b58\u6210\u529f\n\u8acb\u5c07\u4f3a\u670d\u5668\u91cd\u65b0\u555f\u52d5." },
	{ "RMIoverHTTP", 		"Tunnel Objects via HTTP" },
	{ "RMIoverHTTPInfo", 	"RMI over HTTP allows to go through firewalls" }
	};

	/**
	 * 	Get Contents
	 * 	@return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}	//	getContents

}	//	SerupRes
