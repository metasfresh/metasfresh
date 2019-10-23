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
package org.compiere.apps;

import java.util.ListResourceBundle;

/**
 *  Base Resource Bundle
 *
 * 	@author 	Erwin Cortes
 *  @translator Jordi Luna
 * 	@version 	$Id: ALoginRes_es.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_es extends ListResourceBundle
{
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection", 		"Conexi\u00f3n" },
	{ "Defaults", 			"Predeterminados" },
	{ "Login", 				"Autenticaci\u00f3n ADempiere" },
	{ "File", 				"Archivo" },
	{ "Exit", 				"Salir" },
	{ "Help", 				"Ayuda" },
	{ "About", 				"Acerca de" },
	{ "Host", 				"&Servidor" },
	{ "Database", 			"Base de datos" },
	{ "User", 				"&Usuario" },
	{ "EnterUser", 			"Introduzca Usuario Aplicaci\u00f3n" },
	{ "Password", 			"&Contrase\u00f1a" },
	{ "EnterPassword", 		"Introduzca Contrase\u00f1a Aplicaci\u00f3n" },
	{ "Language", 			"&Idioma" },
	{ "SelectLanguage", 	"Seleccione su idioma" },
	{ "Role", 				"Perfil" },
	{ "Client", 			"Entidad" },
	{ "Organization", 		"Organizaci\u00f3n" },
	{ "Date", 				"Fecha" },
	{ "Warehouse", 			"Almac\u00e9n" },
	{ "Printer", 			"Impresora" },
	{ "Connected", 			"Conectado" },
	{ "NotConnected", 		"No Conectado" },
	{ "DatabaseNotFound", 	"Base de datos no encontrada" },
	{ "UserPwdError", 		"Usuario-Contrase\u00f1a no coinciden" },
	{ "RoleNotFound", 		"Perfil no encontrado/incompleto" },
	{ "Authorized", 		"Autorizado" },
	{ "Ok", 				"Aceptar" },
	{ "Cancel", 			"Cancelar" },
	{ "VersionConflict", 	"Conflicto de versi\u00f3n:" },
	{ "VersionInfo", 		"Servidor <> Cliente" },
	{ "PleaseUpgrade", 		"Favor descargar una nueva versi\u00f3n desde el servidor" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}	//	ALoginRes_es
