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

import java.util.*;

/**
 *	Setup Resources it
 *
 * 	@author 	Angelo Martinelli Genied s.p.a.
 * 	@version 	$Id: SetupRes_it.java,v 1.0 2008/01/03 17:57:42 AMartinelli Exp $
 */
public class SetupRes_it extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup",	"Configurazione Server Adempiere" },
	{ "Ok", 					"Ok" },
	{ "File", 					"File" },
	{ "Exit", 					"Uscire" },
	{ "Help", 					"Help" },
	{ "PleaseCheck", 			"Controllare" },
	{ "UnableToConnect",		"Impossibile Connettersi" },
	//
	{ "AdempiereHomeInfo", 		"Adempiere Home:variabile di ambiente di Adempiere" },
	{ "AdempiereHome", 			"Adempiere Home" },
	{ "WebPortInfo", 			"Porta Web (HTML): Se esiste Apache server utilizzare la porta 8080 o 8088" },
	{ "WebPort", 				"Porta Web" },
	{ "AppsServerInfo", 		"Application Server: Nome dell'Host sul quale si installa Adempiere" },
	{ "AppsServer", 			"Application Server" },
	{ "DatabaseTypeInfo", 		"Tipo DataBase" },
	{ "DatabaseType", 			"Tipo DataBase" },
	{ "DatabaseNameInfo", 		"Nome DataBase: Servizio del database" },
	{ "DatabaseName", 			"Nome DataBase" },
	{ "DatabasePortInfo", 		"Porta DataBase: Oracle standard 1521 " },
	{ "DatabasePort", 			"Porta DataBase" },
	{ "DatabaseUserInfo", 		"Utente del DataBase: Nome di creazione dello schema Adempiere" },
	{ "DatabaseUser", 			"Utente del DataBase" },
	{ "DatabasePasswordInfo", 	"Password del Database: dello schema Adempiere" },
	{ "DatabasePassword", 		"Password del Database" },
	{ "TNSNameInfo", 			"SID DataBase: o nome del servizio di ricerca dello schema dati" },
	{ "TNSName", 				"SID DataBase" },
	{ "SystemPasswordInfo", 	"System Password del Database" },
	{ "SystemPassword", 		"System Password" },
	{ "MailServerInfo", 		"Server della posta: host del server di posta dove è l'SMTP" },
	{ "MailServer", 			"Server della posta" },
	{ "AdminEMailInfo", 		"Email Amministratore Adempiere" },
	{ "AdminEMail", 			"Email Amministratore" },
	{ "DatabaseServerInfo", 	"Server DataBase:host della macchina dove si trova la basedati" },
	{ "DatabaseServer", 		"Server DataBase" },
	{ "JavaHomeInfo", 			"Java Home" },
	{ "JavaHome", 				"Java Home" },
	{ "JNPPortInfo", 			"Porta JNP dell'Application Server: solitamente 1099" },
	{ "JNPPort", 				"Porta JNP" },
	{ "MailUserInfo", 			"Utente EMail: Responsabile dell'installazione" },
	{ "MailUser", 				"Email Utente" },
	{ "MailPasswordInfo", 		"Password della casella email dell'utente responsabile" },
	{ "MailPassword", 			"Password della casella email" },
	{ "KeyStorePassword",		"KeyStorePassword" },
	{ "KeyStorePasswordInfo",	"KeyStorePassword per SSL" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Installazione della Java VM"},
	{ "AppsType",				"Tipo Server"},
	{ "AppsTypeInfo",			"Tipo Server dell'Aplicazione J2EE"},
	{ "DeployDir",				"Cartella Deploy"},
	{ "DeployDirInfo",			"Directory Deploy dell'applicazione J2EE"},
	{ "ErrorDeployDir",			"Errore directory di Deploy: potrebbe non esistere controllare"},
	//
	{ "TestInfo", 				"Testare la Configurazione Corrente" },
	{ "Test", 					"Test Configurazione" },
	{ "SaveInfo", 				"Salva la configurazione Corrente sul file properties" },
	{ "Save", 					"Salva" },
	{ "HelpInfo", 				"Help: contattare Fornitore" },
	//
	{ "ServerError", 			"Errore Configurazione Server" },
	{ "ErrorJavaHome", 			"Errore Java Home" },
	{ "ErrorAdempiereHome", 	"Errore Adempiere Home" },
	{ "ErrorAppsServer", 		"Errore Servidor Aplicaci\u00f3n (no utilizar localhost)" },
	{ "ErrorWebPort", 			"Errore Porta Web: potrebbe essere occupata dall'apache server o dal server Adempiere utilizzare 8080" },
	{ "ErrorJNPPort", 			"Errore Porta JNP potrebbe essere occupata da un'altra istanza del server adempiere" },
	{ "ErrorDatabaseServer",	"Errore Server DataBase (non utilizzare localhost)" },
	{ "ErrorDatabasePort", 		"Errore Porta DataBase" },
	{ "ErrorJDBC", 				"Errore connessione JDBC" },
	{ "ErrorTNS", 				"Errore Connessione al TNS" },
	{ "ErrorMailServer", 		"Errore Connessione server Posta: Deve essere un server che risponde a chiamate SMTP sulla porta 25 (non utilizzare localhost)" },
	{ "ErrorMail", 				"Errore Email" },
	{ "ErrorSave", 				"Errore Salvataggio" },

	{ "EnvironmentSaved", 		"Archivio delle proprietà salvato .... avvio del deploy\n"
		+ "Potete riavviare il Server dell'applicazione al termine del programma\n"
		+ "Controllare l'archivio degli errori.\n" }

	};

	/**
	 * 	Get Content
	 * 	@return content array
	 */
	public Object[][] getContents()
	{
		return contents;
	}	//	getContents

}	//	SetupRes
