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
 * 	@translator 	Jaume Teixi
 * 	@author 	Jordi Luna
 * 	@version 	$Id: SetupRes_ca.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_ca extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup",	"Configuraci\u00f3 Servidor Adempiere" },
	{ "Ok", 					"Acceptar" },
	{ "File", 					"Fitxer" },
	{ "Exit", 					"Sortir" },
	{ "Help", 					"Ajuda" },
	{ "PleaseCheck", 			"Sisplau Comproveu" },
	{ "UnableToConnect",		"No s'ha pogut obtenir ajuda de la web d'Adempiere" },
	//
	{ "AdempiereHomeInfo", 		"Adempiere Home \u00e9s la Carpeta Principal" },
	{ "AdempiereHome", 			"Adempiere Home" },
	{ "WebPortInfo", 			"Port Web (HTML)" },
	{ "WebPort", 				"Port Web" },
	{ "AppsServerInfo", 		"Nom Servidor Aplicaci\u00f3" },
	{ "AppsServer", 			"Servidor Aplicaci\u00f3" },
	{ "DatabaseTypeInfo", 		"Tipus Base de Dades" },
	{ "DatabaseType", 			"Tipus Base de Dades" },
	{ "DatabaseNameInfo", 		"Nom Base de Dades (Serve\u00ef)" },
	{ "DatabaseName", 			"Nom Base de Dades" },
	{ "DatabasePortInfo", 		"Port Escolta Base de Dades" },
	{ "DatabasePort", 			"Port Base de Dades" },
	{ "DatabaseUserInfo", 		"ID Usuari Base de Dades Adempiere" },
	{ "DatabaseUser", 			"Usuari Base de Dades" },
	{ "DatabasePasswordInfo", 	"Contrasenya Usuari Base de Dades Adempiere" },
	{ "DatabasePassword", 		"Contrasenya Base de Dades" },
	{ "TNSNameInfo", 			"Bases de Dades Trobades" },
	{ "TNSName", 				"Buscar Bases de Dades" },
	{ "SystemPasswordInfo", 	"Contrasenya Usuari System" },
	{ "SystemPassword", 		"Contrasenya System" },
	{ "MailServerInfo", 		"Servidor Correu" },
	{ "MailServer", 			"Servidor Correu" },
	{ "AdminEMailInfo", 		"Email Administrador Adempiere" },
	{ "AdminEMail", 			"Email Admin" },
	{ "DatabaseServerInfo", 	"Nom Servidor Base de Dades" },
	{ "DatabaseServer", 		"Servidor Base de Dades" },
	{ "JavaHomeInfo", 			"Carpeta Java Home" },
	{ "JavaHome", 				"Java Home" },
	{ "JNPPortInfo", 			"Port JNP Servidor Aplicaci\u00f3" },
	{ "JNPPort", 				"Port JNP" },
	{ "MailUserInfo", 			"Usuari Correu Adempiere" },
	{ "MailUser", 				"Usuari Correu" },
	{ "MailPasswordInfo", 		"Contrasenya Usuari Correu Adempiere" },
	{ "MailPassword", 			"Contrasenya Correu" },
	{ "KeyStorePassword",		"Contrasenya Key Store" },
	{ "KeyStorePasswordInfo",	"Contrasenya per SSL Key Store" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Prove\u00efdor Java VM"},
	{ "AppsType",				"Tipus Servidor"},
	{ "AppsTypeInfo",			"Tipus Servidor Aplicacions J2EE"},
	{ "DeployDir",				"Desplegament"},
	{ "DeployDirInfo",			"Directori Desplegament J2EE"},
	{ "ErrorDeployDir",			"Error Directori Desplegament"},
	//
	{ "TestInfo", 				"Provar Configuraci\u00f3" },
	{ "Test", 					"Provar" },
	{ "SaveInfo", 				"Guardar Configuraci\u00f3" },
	{ "Save", 					"Guardar" },
	{ "HelpInfo", 				"Obtenir Ajuda" },
	//
	{ "ServerError", 			"Error Configuraci\u00f3 Servidor" },
	{ "ErrorJavaHome", 			"Error Java Home" },
	{ "ErrorAdempiereHome", 	"Error Adempiere Home" },
	{ "ErrorAppsServer", 		"Error Servidor Aplicaci\u00f3 (no utilitzar localhost)" },
	{ "ErrorWebPort", 			"Error Port Web" },
	{ "ErrorJNPPort", 			"Error Port JNP" },
	{ "ErrorDatabaseServer",	"Error Servidor Base de Dades (no utilitzar localhost)" },
	{ "ErrorDatabasePort", 		"Error Port Base de Dades" },
	{ "ErrorJDBC", 				"Error Connexi\u00f3 JDBC" },
	{ "ErrorTNS", 				"Error Connexi\u00f3 TNS" },
	{ "ErrorMailServer", 		"Error Servidor Correu (no utilitzar localhost)" },
	{ "ErrorMail", 				"Error Correu" },
	{ "ErrorSave", 				"Error Guardant Fitxer" },

	{ "EnvironmentSaved", 		"Arxiu d'Entorn guardat .... comen\u00e7ant Desplegament\n"
		+ "Pot tornar a engegar el Servidor de l'Aplicaci\u00f3 quan el programa finalitzi.\n"
		+ "Si us plau comprobi l'arxiu d'errors\n" }
	
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
