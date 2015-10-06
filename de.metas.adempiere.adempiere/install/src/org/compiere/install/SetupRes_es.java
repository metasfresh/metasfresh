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
 * 	@author 	Jordi Luna
 * 	@version 	$Id: SetupRes_es.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class SetupRes_es extends ListResourceBundle
{
	/**	Translation Info	*/
	static final Object[][] contents = new String[][]{
	{ "AdempiereServerSetup",	"Configuraci\u00f3n Servidor Adempiere" },
	{ "Ok", 					"Aceptar" },
	{ "File", 					"Fichero" },
	{ "Exit", 					"Salir" },
	{ "Help", 					"Ayuda" },
	{ "PleaseCheck", 			"Por favor Compruebe" },
	{ "UnableToConnect",		"No se ha podido obtener ayuda de la web de Adempiere" },
	//
	{ "AdempiereHomeInfo", 		"Adempiere Home es la Carpeta Principal" },
	{ "AdempiereHome", 			"Adempiere Home" },
	{ "WebPortInfo", 			"Puerto Web (HTML)" },
	{ "WebPort", 				"Puerto Web" },
	{ "AppsServerInfo", 		"Nombre Servidor Aplicaci\u00f3n" },
	{ "AppsServer", 			"Servidor Aplicaci\u00f3n" },
	{ "DatabaseTypeInfo", 		"Tipo Base de Datos" },
	{ "DatabaseType", 			"Tipo Base de Datos" },
	{ "DatabaseNameInfo", 		"Nombre Base de Datos (Servicio)" },
	{ "DatabaseName", 			"Nombre Base de Datos" },
	{ "DatabasePortInfo", 		"Puerto Escucha Base de Datos" },
	{ "DatabasePort", 			"Puerto Base de Datos" },
	{ "DatabaseUserInfo", 		"ID Usuario Base de Datos Adempiere" },
	{ "DatabaseUser", 			"Usuario Base de Datos" },
	{ "DatabasePasswordInfo", 	"Contrase\u00f1a Usuario Base de Datos Adempiere" },
	{ "DatabasePassword", 		"Contrase\u00f1a Base de Datos" },
	{ "TNSNameInfo", 			"Bases de Datos Encontradas" },
	{ "TNSName", 				"Buscar Bases de Datos" },
	{ "SystemPasswordInfo", 	"Contrase\u00f1a Usuario Administrador de Base de Datos" },
	{ "SystemPassword", 		"Contrase\u00f1a Admin BD" },
	{ "MailServerInfo", 		"Servidor Correo" },
	{ "MailServer", 			"Servidor Correo" },
	{ "AdminEMailInfo", 		"Email Administrador Adempiere" },
	{ "AdminEMail", 			"Email Admin" },
	{ "DatabaseServerInfo", 	"Nombre Servidor Base de Datos" },
	{ "DatabaseServer", 		"Servidor Base de Datos" },
	{ "JavaHomeInfo", 			"Carpeta Java Home" },
	{ "JavaHome", 				"Java Home" },
	{ "JNPPortInfo", 			"Puerto JNP Servidor Aplicaci\u00f3n" },
	{ "JNPPort", 				"Puerto JNP" },
	{ "MailUserInfo", 			"Usuario Correo Adempiere" },
	{ "MailUser", 				"Usuario Correo" },
	{ "MailPasswordInfo", 		"Contrase\u00f1a Usuario Correo Adempiere" },
	{ "MailPassword", 			"Contrase\u00f1a Correo" },
	{ "KeyStorePassword",		"Contrase\u00f1a Key Store" },
	{ "KeyStorePasswordInfo",	"Contrase\u00f1a para SSL Key Store" },
	//
	{ "JavaType",				"Java VM"},
	{ "JavaTypeInfo",			"Proveedor Java VM"},
	{ "AppsType",				"Tipo Servidor"},
	{ "AppsTypeInfo",			"Tipo Servidor Aplicaciones J2EE"},
	{ "DeployDir",				"Despliegue"},
	{ "DeployDirInfo",			"Directorio Despliegue J2EE"},
	{ "ErrorDeployDir",			"Error Directorio Despliegue"},
	//
	{ "TestInfo", 				"Probar Configuraci\u00f3n" },
	{ "Test", 					"Probar" },
	{ "SaveInfo", 				"Guardar Configuraci\u00f3n" },
	{ "Save", 					"Guardar" },
	{ "HelpInfo", 				"Obtener Ayuda" },
	//
	{ "ServerError", 			"Error Configuraci\u00f3n Servidor" },
	{ "ErrorJavaHome", 			"Error Java Home" },
	{ "ErrorAdempiereHome", 	"Error Adempiere Home" },
	{ "ErrorAppsServer", 		"Error Servidor Aplicaci\u00f3n (no utilizar localhost)" },
	{ "ErrorWebPort", 			"Error Puerto Web" },
	{ "ErrorJNPPort", 			"Error Puerto JNP" },
	{ "ErrorDatabaseServer",	"Error Servidor Base de Datos (no utilizar localhost)" },
	{ "ErrorDatabasePort", 		"Error Puerto Base de Datos" },
	{ "ErrorJDBC", 				"Error Connexi\u00f3n JDBC" },
	{ "ErrorTNS", 				"Error Connexi\u00f3n TNS" },
	{ "ErrorMailServer", 		"Error Servidor Correo (no utilizar localhost)" },
	{ "ErrorMail", 				"Error Correo" },
	{ "ErrorSave", 				"Error Guardando Ficheros" },

	{ "EnvironmentSaved", 		"Archivo de Entorno guardado .... empezando Despliegue\n"
		+ "Puede volver a arrancar el Servidor de la Aplicaci\u00f3n cuando el programa finalice.\n"
		+ "Por favor compruebe el archivo de errores.\n" }

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
