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
 * 	@author 	Nikola Petkov
 * 	@version 	$Id: patch-java-trl-sr_RS.txt,v 1.1 2008/12/05 11:39:16 mgifos Exp $
 */
public class SetupRes_sr extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", 	"\u041F\u043E\u0441\u0442\u0430\u0432\u043A\u0435 Adempiere \u0441\u0435\u0440\u0432\u0435\u0440\u0430" },
	{ "Ok", 					"\u0423 \u0440\u0435\u0434\u0443" },
	{ "File", 					"\u0414\u0430\u0442\u043E\u0442\u0435\u043A\u0430" },
	{ "Exit", 					"\u0418\u0437\u043B\u0430\u0437" },
	{ "Help", 					"\u041F\u043E\u043C\u043E\u045B" },
	{ "PleaseCheck", 			"\u041C\u043E\u043B\u0438\u043C\u043E \u043F\u0440\u043E\u0432\u0435\u0440\u0438\u0442\u0435" },
	{ "UnableToConnect", 		"\u0413\u0440\u0435\u0448\u043A\u0430 \u0443 Adempiere \u043F\u043E\u0432\u0435\u0437\u0438\u0432\u0430\u045A\u0443 \u043D\u0430 web" },
	//
	{ "AdempiereHomeInfo", 		"Adempiere Home \u0458\u0435 \u0433\u043B\u0430\u0432\u043D\u0438 \u0434\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u0458\u0443\u043C" },
	{ "AdempiereHome", 			"Adempiere Home" },
	{ "WebPortInfo", 			"Web (HTML) Port" },
	{ "WebPort", 				"Web Port" },
	{ "AppsServerInfo", 		"\u041D\u0430\u0437\u0438\u0432 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0430\u043F\u043B\u0438\u043A\u0430\u0446\u0438\u0458\u0435" },
	{ "AppsServer", 			"\u0421\u0435\u0440\u0432\u0435\u0440 \u0430\u043F\u043B\u0438\u043A\u0430\u0446\u0438\u0458\u0435" },
	{ "DatabaseTypeInfo", 		"\u0422\u0438\u043F \u0431\u0430\u0437\u0435 \u043F\u043E\u0434\u0430\u0442\u0430\u043A\u0430" },
	{ "DatabaseType", 			"\u0422\u0438\u043F \u0431\u0430\u0437\u0435 \u043F\u043E\u0434\u0430\u0442\u0430\u043A\u0430" },
	{ "DatabaseNameInfo", 		"\u041D\u0430\u0437\u0438\u0432 \u0431\u0430\u0437\u0435" },
	{ "DatabaseName", 			"\u041D\u0430\u0437\u0438\u0432 \u0431\u0430\u0437\u0435 (SID)" },
	{ "DatabasePortInfo", 		"Port \u0431\u0430\u0437\u0435" },
	{ "DatabasePort", 			"Port \u0431\u0430\u0437\u0435" },
	{ "DatabaseUserInfo", 		"Adempiere \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A \u0431\u0430\u0437\u0435" },
	{ "DatabaseUser", 			"\u041A\u043E\u0440\u0438\u0441\u043D\u0438\u043A \u0431\u0430\u0437\u0435" },
	{ "DatabasePasswordInfo", 	"\u041B\u043E\u0437\u0438\u043D\u043A\u0430 \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A\u0430 \u0431\u0430\u0437\u0435" },
	{ "DatabasePassword", 		"\u041B\u043E\u0437\u0438\u043D\u043A\u0430 \u0431\u0430\u0437\u0435" },
	{ "TNSNameInfo", 			"TNS \u0438\u043B\u0438 \u0433\u043B\u043E\u0431\u0430\u043B\u043D\u0438 \u043D\u0430\u0437\u0438\u0432 \u0431\u0430\u0437\u0435" },
	{ "TNSName", 				"TNS \u043D\u0430\u0437\u0438\u0432" },
	{ "SystemPasswordInfo", 	"\u041B\u043E\u0437\u0438\u043D\u043A\u0430 System \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A\u0430" },
	{ "SystemPassword", 		"System \u043B\u043E\u0437\u0438\u043D\u043A\u0430" },
	{ "MailServerInfo", 		"Mail \u0441\u0435\u0440\u0432\u0435\u0440" },
	{ "MailServer", 			"Mail \u0441\u0435\u0440\u0432\u0435\u0440" },
	{ "AdminEMailInfo", 		"Email Adempiere \u0430\u0434\u043C\u0438\u043D\u0438\u0441\u0442\u0440\u0430\u0442\u043E\u0440\u0430" },
	{ "AdminEMail", 			"EMail \u0430\u0434\u043C\u0438\u043D\u0438\u0441\u0442\u0440\u0430\u0442\u043E\u0440\u0430" },
	{ "DatabaseServerInfo", 	"\u041D\u0430\u0437\u0438\u0432 \u0441\u0435\u0440\u0432\u0435\u0440\u0430 \u0431\u0430\u0437\u0435" },
	{ "DatabaseServer", 		"\u0421\u0435\u0440\u0432\u0435\u0440 \u0431\u0430\u0437\u0435" },
	{ "JavaHomeInfo", 			"Java Home \u0434\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u0458\u0443\u043C" },
	{ "JavaHome", 				"Java Home" },
	{ "JNPPortInfo", 			"\u0421\u0435\u0440\u0432\u0435\u0440 \u0430\u043F\u043B\u0438\u043A\u0430\u0446\u0438\u0458\u0435 JNP Port" },
	{ "JNPPort", 				"JNP Port" },
	{ "MailUserInfo", 			"Adempiere Mail \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A" },
	{ "MailUser", 				"Mail \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A" },
	{ "MailPasswordInfo", 		"Adempiere Mail \u043B\u043E\u0437\u0438\u043D\u043A\u0430 \u043A\u043E\u0440\u0438\u0441\u043D\u0438\u043A\u0430" },
	{ "MailPassword", 			"Mail \u043B\u043E\u0437\u0438\u043D\u043A\u0430" },
	{ "KeyStorePassword",		"Key Store \u043B\u043E\u0437\u0438\u043D\u043A\u0430" },
	{ "KeyStorePasswordInfo",	"\u041B\u043E\u0437\u0438\u043D\u043A\u0430 од SSL Key Store" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Java VM Vendor"},
	{ "AppsType",				"\u0422\u0438\u043F \u0441\u0435\u0440\u0432\u0435\u0440\u0430"},
	{ "AppsTypeInfo",			"\u0422\u0438\u043F JEE \u0430\u043F\u043B\u0438\u043A\u0430\u0442\u0438\u0432\u043D\u043E\u0433 \u0441\u0435\u0440\u0432\u0435\u0440\u0430"},
	{ "DeployDir",				"Deployment"},
	{ "DeployDirInfo",			"J2EE Deployment \u0434\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u0458\u0443\u043C"},
	{ "ErrorDeployDir",			"Error Deployment \u0434\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u0458\u0443\u043C"},
	//
	{ "TestInfo", 				"\u0422\u0435\u0441\u0442 \u043F\u043E\u0441\u0442\u0430\u0432\u043A\u0438" },
	{ "Test", 					"\u0422\u0435\u0441\u0442" },
	{ "SaveInfo", 				"\u0421\u043D\u0438\u043C\u0438 \u043F\u043E\u0441\u0442\u0430\u0432\u043A\u0435" },
	{ "Save", 					"\u0421\u043D\u0438\u043C\u0438" },
	{ "HelpInfo", 				"\u041F\u043E\u043C\u043E\u045B" },
	//
	{ "ServerError", 			"\u0413\u0440\u0435\u0448\u043A\u0430 \u043F\u0440\u0438\u043B\u0438\u043A\u043E\u043C \u043F\u043E\u0434\u0435\u0448\u0430\u0432\u0430\u045A\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0430" },
	{ "ErrorJavaHome", 			"\u0413\u0440\u0435\u0448\u043A\u0430 Java Home" },
	{ "ErrorAdempiereHome", 	"\u0413\u0440\u0435\u0448\u043A\u0430 Adempiere Home" },
	{ "ErrorAppsServer", 		"\u0413\u0440\u0435\u0448\u043A\u0430  \u0410\u043F\u043B\u0438\u043A\u0430\u0442\u0438\u0432\u043D\u0438 \u0441\u0435\u0440\u0432\u0435\u0440 (\u043D\u0435 \u043A\u043E\u0440\u0438\u0441\u0442\u0438 localhost)" },
	{ "ErrorWebPort", 			"\u0413\u0440\u0435\u0448\u043A\u0430  Web Port" },
	{ "ErrorJNPPort", 			"\u0413\u0440\u0435\u0448\u043A\u0430  JNP Port" },
	{ "ErrorDatabaseServer", 	"\u0413\u0440\u0435\u0448\u043A\u0430  \u0421\u0435\u0440\u0432\u0435\u0440 \u0431\u0430\u0437\u0435 (\u043D\u0435 \u043A\u043E\u0440\u0438\u0441\u0442\u0438 localhost)" },
	{ "ErrorDatabasePort", 		"\u0413\u0440\u0435\u0448\u043A\u0430  Port \u0431\u0430\u0437\u0435" },
	{ "ErrorJDBC", 				"\u0413\u0440\u0435\u0448\u043A\u0430  JDBC веза" },
	{ "ErrorTNS", 				"\u0413\u0440\u0435\u0448\u043A\u0430  TNS веза" },
	{ "ErrorMailServer", 		"\u0413\u0440\u0435\u0448\u043A\u0430  Mail \u0441\u0435\u0440\u0432\u0435\u0440 (\u043D\u0435 \u043A\u043E\u0440\u0438\u0441\u0442\u0438 localhost)" },
	{ "ErrorMail", 				"\u0413\u0440\u0435\u0448\u043A\u0430  Mail" },
	{ "ErrorSave", 				"\u0413\u0440\u0435\u0448\u043A\u0430 \u043F\u0440\u0438\u043B\u0438\u043A\u043E\u043C \u0441\u043D\u0438\u043C\u0430\u045A\u0430 \u0444\u0430\u0458\u043B\u0430" },

	{ "EnvironmentSaved", 		"\u041E\u043A\u0440\u0443\u0436\u0435\u045A\u0435 \u0458\u0435 \u0441\u043D\u0438\u043C\u0459\u0435\u043D\u043E\n\u0421\u0430\u0434\u0430 \u043C\u043E\u0436\u0435\u0442\u0435 \u043F\u043E\u043A\u0440\u0435\u043D\u0443\u0442\u0438 \u0430\u043F\u043B\u0438\u043A\u0430\u0442\u0438\u0432\u043D\u0438 \u0441\u0435\u0440\u0432\u0435\u0440." }

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
