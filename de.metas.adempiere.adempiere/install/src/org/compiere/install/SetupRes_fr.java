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
 * under the terms version 2 of the GNU General Public License as published   *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.install;

import java.util.*;

/**
 *	Setup Resources (French Translation)
 *
 * 	@author 	Development team of Alamanga www.alamanga.com
 * 	@version 	$Id: SetupRes.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_fr extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup", 	"Configuration du serveur Adempiere" },
	{ "Ok", 					"Ok" },
	{ "File", 					"Fichier" },
	{ "Exit", 					"Sortir" },
	{ "Help", 					"Aide" },
	{ "PleaseCheck", 			"Verifiez" },
	{ "UnableToConnect", 		"Impossible d'obtenir de l'aide depuis le site web Adempiere" },
	//
	{ "AdempiereHomeInfo", 		"R\u00E9pertoire racine d'Adempiere est le repertoire principale" },
	{ "AdempiereHome", 			"R\u00E9pertoire racine d'Adempiere" },
	{ "WebPortInfo", 			"Port Web (HTML)" },
	{ "WebPort", 				"Port Web" },
	{ "AppsServerInfo", 		"Nom du serveur d'application" },
	{ "AppsServer", 			"Serveur d'application" },
	{ "DatabaseTypeInfo", 		"Type de base de donn\u00E9es" },
	{ "DatabaseType", 			"Type de base de donn\u00E9es" },
	{ "DatabaseNameInfo", 		"Nom de la base de donn\u00E9e (Service)" },
	{ "DatabaseName", 			"Nom de la base de donn\u00E9es" },
	{ "DatabasePortInfo", 		"Port d'\u00E9coute de la base de donn\u00E9es" },
	{ "DatabasePort", 			"Port de la base de donn\u00E9es" },
	{ "DatabaseUserInfo", 		"ID de l'utilisateur de la base de donn\u00E9es Adempiere" },
	{ "DatabaseUser", 			"Utilisateur de la base de donn\u00E9es" },
	{ "DatabasePasswordInfo", 	"Mot de passe de la base de donn\u00E9es Adempiere" },
	{ "DatabasePassword", 		"Mot de passe de la base de donn\u00E9es" },
	{ "TNSNameInfo", 			"D\u00E9couverte de bases de donn\u00E9ees (TNS)" },
	{ "TNSName", 				"Recherche de bases de donn\u00E9ees" },
	{ "SystemPasswordInfo", 	"Mot de passe de l'utilisateur System de la bases de donn\u00E9ees" },
	{ "SystemPassword", 		"Mot de passe utilisateur System" },
	{ "MailServerInfo", 		"Serveur de courrier" },
	{ "MailServer", 			"Serveur de courrier" },
	{ "AdminEMailInfo", 		"Courriel de l'administrateur Adempiere" },
	{ "AdminEMail", 			"Courriel de l'administreur" },
	{ "DatabaseServerInfo", 	"Nom du serveur de base de donn\u00E9es" },
	{ "DatabaseServer", 		"Serveur de base de donn\u00E9es" },
	{ "JavaHomeInfo", 			"R\u00E9pertoire installation de Java" },
	{ "JavaHome", 				"R\u00E9pertoire Java" },
	{ "JNPPortInfo", 			"Port JPN du serveur d'application" },
	{ "JNPPort", 				"Port JNP" },
	{ "MailUserInfo", 			"Utilisateur courriel Adempiere" },
	{ "MailUser", 				"Utilisateur courriel" },
	{ "MailPasswordInfo", 		"Mot de passe de l'utilisateur Adempiere" },
	{ "MailPassword", 			"Mot de passe courriel" },
	{ "KeyStorePassword",		"Mot de passe KeyStore" },
	{ "KeyStorePasswordInfo",	"Mot de passe pour le KeyStore SSL" },
	//
	{ "JavaType",				"VM Java"},
	{ "JavaTypeInfo",			"Vendeur de VM Java"},
	{ "AppsType",				"Type de serveur"},
	{ "AppsTypeInfo",			"Type de serveur d'application J2EE"},
	{ "DeployDir",				"D\u00E9ploiement"},
	{ "DeployDirInfo",			"R\u00E9pertoire de d\u00E9ploiement J2EE"},
	{ "ErrorDeployDir",			"Erreur sur le r\u00E9pertoire de d\u00E9ploiement"},
	//
	{ "TestInfo", 				"Test de la configuration" },
	{ "Test", 					"Test" },
	{ "SaveInfo", 				"Sauvegarder la configuration" },
	{ "Save", 					"Sauvegarder" },
	{ "HelpInfo", 				"Obtenir de l'aide" },
	//
	{ "ServerError", 			"Erreur de la configuration du serveur" },
	{ "ErrorJavaHome", 			"Erreur sur le r\u00E9pertoire Java" },
	{ "ErrorAdempiereHome", 	"Erreur sur le r\u00E9pertoire Adempiere" },
	{ "ErrorAppsServer", 		"Erreur Serveur Apps (ne pas utiliser localhost)" },
	{ "ErrorWebPort", 			"Erreur port Web" },
	{ "ErrorJNPPort", 			"Erreur port JNP" },
	{ "ErrorDatabaseServer", 	"Erreur serveur de base de donn\u00E9es (ne pas utiliser localhost)" },
	{ "ErrorDatabasePort", 		"Erreur port de la base de donn\u00E9es" },
	{ "ErrorJDBC", 				"Erreur sur la connection JDBC" },
	{ "ErrorTNS", 				"Erreur sur la connection TNS" },
	{ "ErrorMailServer", 		"Erreur serveur de Courriel (ne pas utiliser localhost)" },
	{ "ErrorMail", 				"Erreur Mail" },
	{ "ErrorSave", 				"Erreur lors de la sauvegarde du fichier" },

	{ "EnvironmentSaved", 		"Fichier sauvegard\u00E9 .... d\u00E9marrage du d\u00E9ploiement\n"
		+ "Vous pouvez re d\u00E9marrer le serveur d'application une fois le programme execut\u00E9.\n"
		+ "V\u00E9rifiez les erreurs dans les fichiers de traces.\n" }

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