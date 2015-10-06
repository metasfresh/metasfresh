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
 *	Swedish Setup Resource Translation
 *
 * 	@author 	Thomas Dilts
 * 	@version 	$Id: SetupRes_sv.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_sv extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", "Adempiere server installationsprogram" },
	{ "Ok", 				"Ok" },
	{ "File", 				"Fil" },
	{ "Exit", 				"Avsluta" },
	{ "Help", 				"Hj\u00c3\u00a4lp" },
	{ "PleaseCheck", 		"Kolla" },
	{ "UnableToConnect",	"Kan inte f\u00c3\u00a5 hj\u00c3\u00a4lp fr\u00c3\u00a5n Adempiere Web Site" },

	{ "AdempiereHomeInfo", 	"Adempiere hem \u00c3\u00a4r huvudkatalog" },
	{ "AdempiereHome", 		"Adempiere hem" },
	{ "WebPortInfo", 		"Web (HTML) port" },
	{ "WebPort", 			"Web port" },
	{ "AppsServerInfo", 	"Program server name" },
	{ "AppsServer", 		"Program server" },
	{ "DatabaseTypeInfo", 	"Databastyp" },
	{ "DatabaseType", 		"Databastyp" },
	{ "DatabaseNameInfo", 	"Databas namn " },
	{ "DatabaseName", 		"Databas namn (SID)" },
	{ "DatabasePortInfo", 	"Databas avlyssningsport" },
	{ "DatabasePort", 		"Databas port" },
	{ "DatabaseUserInfo", 	"Databas Adempiere anv\u00c3\u00a4ndarnamn" },
	{ "DatabaseUser", 		"Databas anv\u00c3\u00a4ndarnamn" },
	{ "DatabasePasswordInfo", "Databas Adempiere anv\u00c3\u00a4ndare l\u00c3\u00b6senord" },
	{ "DatabasePassword", 	"Databas l\u00c3\u00b6senord" },
	{ "TNSNameInfo", 		"TNS eller global databas namn" },
	{ "TNSName", 			"TNS namn" },
	{ "SystemPasswordInfo", "System anv\u00c3\u00a4ndare l\u00c3\u00b6senord" },
	{ "SystemPassword", 	"System l\u00c3\u00b6senord" },
	{ "MailServerInfo", 	"Post server" },
	{ "MailServer", 		"Post server" },
	{ "AdminEMailInfo", 	"Adempiere administrat\u00c3\u00b6r e-post" },
	{ "AdminEMail", 		"Admin e-post" },
	{ "DatabaseServerInfo", "Databas server namn" },
	{ "DatabaseServer", 	"Databas server" },
	{ "JavaHomeInfo", 		"Java hemkatalog" },
	{ "JavaHome", 			"Java hem" },
	{ "JNPPortInfo", 		"Program server JNP port" },
	{ "JNPPort", 			"JNP port" },
	{ "MailUserInfo", 		"Adempiere post anv\u00c3\u00a4ndare" },
	{ "MailUser", 			"Post anv\u00c3\u00a4ndare" },
	{ "MailPasswordInfo", 	"Adempiere post anv\u00c3\u00a4ndare l\u00c3\u00b6senord" },
	{ "MailPassword", 		"Post l\u00c3\u00b6senord" },
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
	{ "TestInfo", 			"Testa inst\u00c3\u00a4llningar" },
	{ "Test", 				"Testa" },
	{ "SaveInfo", 			"Spara inst\u00c3\u00a4llningar" },
	{ "Save", 				"Spara" },
	{ "HelpInfo", 			"Hj\u00c3\u00a4lp" },

	{ "ServerError", 		"Server inst\u00c3\u00a4llningsfel" },
	{ "ErrorJavaHome", 		"Fel Java hem" },
	{ "ErrorAdempiereHome", 	"Fel Adempiere hem" },
	{ "ErrorAppsServer", 	"Fel applikationsserver (anv\u00c3\u00a4nd ej localhost)" },
	{ "ErrorWebPort", 		"Fel web port" },
	{ "ErrorJNPPort", 		"Fel JNP port" },
	{ "ErrorDatabaseServer", "Fel databas server (anv\u00c3\u00a4nd ej localhost)" },
	{ "ErrorDatabasePort", 	"Fel databas port" },
	{ "ErrorJDBC", 			"Fel JDBC anslutning" },
	{ "ErrorTNS", 			"Fel TNS anslutning" },
	{ "ErrorMailServer", 	"Fel post server (anv\u00c3\u00a4nd ej localhost)" },
	{ "ErrorMail", 			"Fel post" },
	{ "ErrorSave", 			"Fel swing fil" },

	{ "EnvironmentSaved",	"Inst\u00c3\u00a4llningar sparade\nDu m\u00c3\u00a5ste starta om servern." },
	{ "RMIoverHTTP", 		"Tunnla objects via HTTP" },
	{ "RMIoverHTTPInfo", 	"RMI via HTTP allows to go through firewalls" }
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
