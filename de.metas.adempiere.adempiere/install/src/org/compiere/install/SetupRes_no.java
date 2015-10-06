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
 *	Norwegian Setup Resource Translation
 *
 * 	@author 	Olaf Slazak L�ken
 * 	@version 	$Id: SetupRes_no.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_no extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", "Adempiere Server Oppsett" },
	{ "Ok", 				"Ok" },
	{ "File", 				"Fil" },
	{ "Exit", 				"Avslutt" },
	{ "Help", 				"Hjelp" },
	{ "PleaseCheck", 		"Vennligst Sjekk" },
	{ "UnableToConnect",	"Umulig � hente hjelp fra Adempiere Web Side" },

	{ "AdempiereHomeInfo", 	"Adempiere Hjem er i hoved Mappen" },
	{ "AdempiereHome", 		"Adempiere Hjem" },
	{ "WebPortInfo", 		"Web (HTML) Port" },
	{ "WebPort", 			"Web Port" },
	{ "AppsServerInfo", 	"Applikasion Server Navn" },
	{ "AppsServer", 		"App. Server" },
	{ "DatabaseTypeInfo", 	"Database Type" },
	{ "DatabaseType", 		"Database Type" },
	{ "DatabaseNameInfo", 	"Database Navn " },
	{ "DatabaseName", 		"Database Navn (SID)" },
	{ "DatabasePortInfo", 	"Database Listener Port" },
	{ "DatabasePort", 		"Database Port" },
	{ "DatabaseUserInfo", 	"Database Adempiere Bruker ID" },
	{ "DatabaseUser", 		"Database Bruker" },
	{ "DatabasePasswordInfo", "Database Adempiere Bruker Passord" },
	{ "DatabasePassword", 	"Database Passord" },
	{ "TNSNameInfo", 		"TNS eller Global Database Navn" },
	{ "TNSName", 			"TNS Navn" },
	{ "SystemPasswordInfo", "System Bruker Passord" },
	{ "SystemPassword", 	"System Passord" },
	{ "MailServerInfo", 	"Epost Server" },
	{ "MailServer", 		"Epost Server" },
	{ "AdminEMailInfo", 	"Adempiere Administrator EPost" },
	{ "AdminEMail", 		"Admin EPost" },
	{ "DatabaseServerInfo", "Database Server Navn" },
	{ "DatabaseServer", 	"Database Server" },
	{ "JavaHomeInfo", 		"Java Hjem Katalog" },
	{ "JavaHome", 			"Java Hjem" },
	{ "JNPPortInfo", 		"Aplikasions Server JNP Port" },
	{ "JNPPort", 			"JNP Port" },
	{ "MailUserInfo", 		"Adempiere EPost User" },
	{ "MailUser", 			"EPost User" },
	{ "MailPasswordInfo", 	"Adempiere EPost Bruker Passord" },
	{ "MailPassword", 		"EPost Passord" },
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
	{ "TestInfo", 			"Test Oppsettet" },
	{ "Test", 				"Test" },
	{ "SaveInfo", 			"Lagre Oppsett" },
	{ "Save", 				"Lagre" },
	{ "HelpInfo", 			"Hent Hjelp" },

	{ "ServerError", 		"Server Oppsett Feil" },
	{ "ErrorJavaHome", 		"Feil Java Hjem" },
	{ "ErrorAdempiereHome", 	"Feil Adempiere Hjem" },
	{ "ErrorAppsServer", 	"Feil App. Server (ikke bruk localhost)" },
	{ "ErrorWebPort", 		"Feil Web Port" },
	{ "ErrorJNPPort", 		"Feil JNP Port" },
	{ "ErrorDatabaseServer", "Feil Database Server (ikke bruk localhost)" },
	{ "ErrorDatabasePort", 	"Feil Database Port" },
	{ "ErrorJDBC", 			"Feil ved JDBC Oppkobling" },
	{ "ErrorTNS", 			"Feil ved TNS Oppkobling" },
	{ "ErrorMailServer", 	"Feil EPost Server (ikke bruk localhost)" },
	{ "ErrorMail", 			"Feil EPost" },
	{ "ErrorSave", 			"Feil Sving Fil" },

	{ "EnvironmentSaved",	"Oppsett lagret\nDu trenger � restarte serveren." },
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
