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
 * 	@author 	Marek Mosiewicz <marek.mosiewicz@jotel.com.pl>
 * 	@version 	$Id: SetupRes_pl.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_pl extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", "Konfiguracja serwera Adempiere" },
	{ "Ok", 				"Ok" },
	{ "File", 				"Plik" },
	{ "Exit", 				"Wyj\u015bcie" },
	{ "Help", 				"Pomoc" },
	{ "PleaseCheck", 		"Prosz\u0119 sprawdzi\u0107" },
	{ "UnableToConnect",	"Nie mo\u017cna po\u0142\u0105czy\u0107 si\u0119 ze stron\u0105 Adempiere w celu uzyskania pomocy" },

	{ "AdempiereHomeInfo", 	"Folder Adempiere jest folderem g\u0142\u00f3wnym" },
	{ "AdempiereHome", 		"Folder Adempiere" },
	{ "WebPortInfo", 		"Web (HTML) Port" },
	{ "WebPort", 			"Web Port" },
	{ "AppsServerInfo", 	"Nazwa serwera aplikacji" },
	{ "AppsServer", 		"Serwer bazy danych" },
	{ "DatabaseTypeInfo", 	"Typ bazy danych" },
	{ "DatabaseType", 		"Typ bazy danych" },
	{ "DatabaseNameInfo", 	"Nazwa bazy danych " },
	{ "DatabaseName", 		"Nazwa bazy danych (SID)" },
	{ "DatabasePortInfo", 	"Port listenera bazy danych" },
	{ "DatabasePort", 		"Port bazy danych" },
	{ "DatabaseUserInfo", 	"U\u017cytkownik Adempiere w bazie danych" },
	{ "DatabaseUser", 		"U\u017cytkownik bazy" },
	{ "DatabasePasswordInfo", "Has\u0142o u\u017cytkownika Adempiere" },
	{ "DatabasePassword", 	"Has\u0142o u\u017cytkownika" },
	{ "TNSNameInfo", 		"TNS lub Globalna Nazwa Bazy (dla Oracle)" },
	{ "TNSName", 			"Nazwa TNS" },
	{ "SystemPasswordInfo", "Has\u0142o dla u\u017cytkownika System w bazie danych" },
	{ "SystemPassword", 	"Has\u0142o System" },
	{ "MailServerInfo", 	"Serwer pocztowy" },
	{ "MailServer", 		"Serwer pocztowy" },
	{ "AdminEMailInfo", 	"Adres email administartora Adempiere" },
	{ "AdminEMail", 		"EMail administ." },
	{ "DatabaseServerInfo", "Nazwa serwera bazy danych" },
	{ "DatabaseServer", 	"Serwer bazy danych" },
	{ "JavaHomeInfo", 		"Folder Javy" },
	{ "JavaHome", 			"Folder Javy" },
	{ "JNPPortInfo", 		"Application Server JNP Port" },
	{ "JNPPort", 			"JNP Port" },
	{ "MailUserInfo", 		"U\u017cytkownik poczty dla cel\u00f3w administracyjnych Adempiere" },
	{ "MailUser", 			"U\u017cytkownik poczty" },
	{ "MailPasswordInfo", 	"Has\u0142o dla konta pocztowego Adempiere" },
	{ "MailPassword", 		"Has\u0142o poczty" },
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
	{ "TestInfo", 			"Sprawd\u017a ustawienia" },
	{ "Test", 				"Testuj" },
	{ "SaveInfo", 			"Zapisz ustawienia" },
	{ "Save", 				"Zapisz" },
	{ "HelpInfo", 			"Pomoc" },

	{ "ServerError", 		"B\u0142\u0119dne ustawienia" },
	{ "ErrorJavaHome", 		"Niepoprawny folder Javy" },
	{ "ErrorAdempiereHome", 	"Nie stwierdzono zainstalowanego systemu Adempiere w miescu wskazanym jako Folder Adempiere" },
	{ "ErrorAppsServer", 	"Niepoprawny serwer aplikacji (nie mo\u017ce by\u0107 localhost)" },
	{ "ErrorWebPort", 		"Niepoprawny port WWW (by\u0107 mo\u017ce inna aplikacja u\u017cywa ju\u017c tego portu)" },
	{ "ErrorJNPPort", 		"Niepoprawny port JNP (by\u0107 mo\u017ce inna aplikacja u\u017cywa ju\u017c tego portu)" },
	{ "ErrorDatabaseServer", "Niepoprawny serwer bazy (nie mo\u017ce by\u0107 localhost)" },
	{ "ErrorDatabasePort", 	"Niepoprawny port serwer bazy" },
	{ "ErrorJDBC", 			"Wyst\u0105pi\u0142 b\u0142\u0105d przy pr\u00f3bie po\u0142\u0105cznia si\u0119 z baz\u0105 danych" },
	{ "ErrorTNS", 			"Wyst\u0105pi\u0142 b\u0142\u0105d przy pr\u00f3bie po\u0142\u0105cznia si\u0119 z baz\u0105 danych poprzez TNS" },
	{ "ErrorMailServer", 	"Niepoprawny serwer pocztowy (nie mo\u017ce by\u0107 localhost)" },
	{ "ErrorMail", 			"B\u0142\u0105d poczty" },
	{ "ErrorSave", 			"B\u0142\u0105d przy zapisywaniu konfiguracji" },

	{ "EnvironmentSaved",	"Ustawienia zapisany\nMusisz ponownie uruchomi\u0107 serwer." },
	{ "RMIoverHTTP", 		"Tunelowanie RMI over HTTP" },
	{ "RMIoverHTTPInfo", 	"Tunelowanie RMI over HTTP pozwala u\u017cywa\u0107 Adempiere przez firewall" }
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
