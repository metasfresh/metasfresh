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
 * 	@author 	Jorg Janke
 * 	@version 	$Id: SetupRes_ro.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_ro extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", 	"Configurarea serverului Adempiere" },
	{ "Ok", 					"OK" },
	{ "File", 					"Aplica\u0163ie" },
	{ "Exit", 					"Ie\u015fire" },
	{ "Help", 					"Ajutor" },
	{ "PleaseCheck", 			"Consulta\u0163i" },
	{ "UnableToConnect", 		"Nu s-a putut ob\u0163ine ajutor de pe site-ul web al Adempiere" },
	//
	{ "AdempiereHomeInfo", 		"Loca\u0163ia Adempiere reprezint\u0103 directorul s\u0103u de instalare" },
	{ "AdempiereHome", 			"Loca\u0163ie Adempiere" },
	{ "WebPortInfo", 			"Portul de web (HTML)" },
	{ "WebPort", 				"Port de web" },
	{ "AppsServerInfo", 		"Numele serverului de aplica\u0163ie" },
	{ "AppsServer", 			"Server de aplica\u0163ie" },
	{ "DatabaseTypeInfo", 		"Tipul bazei de date" },
	{ "DatabaseType", 			"Tip de baz\u0103 de date" },
	{ "DatabaseNameInfo", 		"Numele (serviciului) bazei de date" },
	{ "DatabaseName", 			"Nume de baz\u0103 de date" },
	{ "DatabasePortInfo", 		"Portul rezevat serviciului bazei de date" },
	{ "DatabasePort", 			"Port de baz\u0103 de date" },
	{ "DatabaseUserInfo", 		"Utilizatorul Adempiere pentru baza de date" },
	{ "DatabaseUser", 			"Utilizator de baz\u0103 de date" },
	{ "DatabasePasswordInfo", 	"Parola utilizatorului Adempiere pentru baza de date" },
	{ "DatabasePassword", 		"Parola pentru baza de date" },
	{ "TNSNameInfo", 			"Baze de date g\u0103site" },
	{ "TNSName", 				"C\u0103utare de baze de date" },
	{ "SystemPasswordInfo", 	"Parola utilizatorului System" },
	{ "SystemPassword", 		"Parol\u0103 pentru System" },
	{ "MailServerInfo", 		"Server de po\u015ft\u0103 electronic\u0103" },
	{ "MailServer", 			"Server de po\u015ft\u0103 electronic\u0103" },
	{ "AdminEMailInfo", 		"Adresa de po\u015ft\u0103 electronic\u0103 a administratorului Adempiere" },
	{ "AdminEMail", 			"Adres\u0103 de e-mail a administratorului" },
	{ "DatabaseServerInfo", 	"Numele serverului de baz\u0103 de date" },
	{ "DatabaseServer", 		"Server de baz\u0103 de date" },
	{ "JavaHomeInfo", 			"Loca\u0163ia de instalare a Java" },
	{ "JavaHome", 				"Loca\u0163ie Java" },
	{ "JNPPortInfo", 			"Portul JNP al serverului de aplica\u0163ie" },
	{ "JNPPort", 				"Port JNP" },
	{ "MailUserInfo", 			"Utilizatorul Adempiere pentru po\u015fta electronic\u0103" },
	{ "MailUser", 				"Utilizator de po\u015ft\u0103 electronic\u0103" },
	{ "MailPasswordInfo", 		"Parola utilizatorului Adempiere pentru po\u015fta electronic\u0103" },
	{ "MailPassword", 			"Parol\u0103 de po\u015ft\u0103 electronic\u0103" },
	{ "KeyStorePassword",		"Parol\u0103 de keystore" },
	{ "KeyStorePasswordInfo",	"Parola de pentru arhiva de chei SSL" },
	//
	{ "JavaType",				"Ma\u015fina virtual\u0103 Java"},
	{ "JavaTypeInfo",			"Furnizorul ma\u015finii virtuale Java"},
	{ "AppsType",				"Tip de server"},
	{ "AppsTypeInfo",			"Tipul serverului de aplica\u0163ie J2EE"},
	{ "DeployDir",				"Director de instalare"},
	{ "DeployDirInfo",			"Directorul J2EE de instalare"},
	{ "ErrorDeployDir",			"Director de instalare incorect"},
	//
	{ "TestInfo", 				"Testarea configur\u0103rii" },
	{ "Test", 					"Testare" },
	{ "SaveInfo", 				"Salvarea configur\u0103rii" },
	{ "Save", 					"Salvare" },
	{ "HelpInfo", 				"Ob\u0163inere de ajutor" },
	//
	{ "ServerError", 			"Eroare de configurare a serverului" },
	{ "ErrorJavaHome", 			"Eroare de loca\u0163ie Java" },
	{ "ErrorAdempiereHome", 		"Eroare de loca\u0163ie Adempiere" },
	{ "ErrorAppsServer", 		"Eroare de server de aplica\u0163ie (nu folosi\u0163i 'localhost')" },
	{ "ErrorWebPort", 			"Eroare de port de web" },
	{ "ErrorJNPPort", 			"Eroare de port JNP" },
	{ "ErrorDatabaseServer", 	"Eroare de server de baz\u0103 de date (nu folosi\u0163i 'localhost')" },
	{ "ErrorDatabasePort", 		"Eroare de port de baz\u0103 de date" },
	{ "ErrorJDBC", 				"Eroare de conexiune JDBC" },
	{ "ErrorTNS", 				"Eroare de conexiune TNS" },
	{ "ErrorMailServer", 		"Eroare de server de po\u015ft\u0103 electronic\u0103 (nu folosi\u0163i 'localhost')" },
	{ "ErrorMail", 				"Eroare de po\u015ft\u0103 electronic\u0103" },
	{ "ErrorSave", 				"Eroare la salvarea fi\u015fierului" },

	{ "EnvironmentSaved", 		"Configurarea a fost salvat\u0103... se \u00eencepe instalarea.\n"
		+ "Pute\u0163i (re)porni serverul de aplica\u0163ie dup\u0103 terminarea programului curent.\n"
		+ "Verifica\u0163i apoi dac\u0103 apar erori \u00een jurnal." }

	};

	/**
	 * 	Get Contents
	 * 	@return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}	//	getContents

}	//	SetupRes
