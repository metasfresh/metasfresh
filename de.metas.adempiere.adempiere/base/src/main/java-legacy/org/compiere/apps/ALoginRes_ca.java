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
 * 	@translator  	Jaume Teixi	
 *  @translator Jordi Luna
 * 	@version 	$Id: ALoginRes_ca.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_ca extends ListResourceBundle
{
	// TODO Run native2ascii to convert to plain ASCII !! 
	
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",         "Connexi\u00f3" },
	{ "Defaults",           "Valors Predeterminats" },
	{ "Login",              "Acc\u00e9s ADempiere" },
	{ "File",               "Fitxer" },
	{ "Exit",               "Sortir" },
	{ "Help",               "Ajuda" },
	{ "About",              "Referent" },
	{ "Host",               "Servidor" },
	{ "Database",           "Base de Dades" },
	{ "User",               "Usuari" },
	{ "EnterUser",          "Introdu\u00efr Usuari Aplicaci\u00f3" },
	{ "Password",           "Contrasenya" },
	{ "EnterPassword",      "Entrar Contrasenya Usuari Aplicaci\u00f3" },
	{ "Language",           "Idioma" },
	{ "SelectLanguage",     "Seleccioni Idioma" },
	{ "Role",               "Rol" },
	{ "Client",             "Entitat" },
	{ "Organization",       "Organitzaci\u00f3" },
	{ "Date",               "Data" },
	{ "Warehouse",          "Magatzem" },
	{ "Printer",            "Impressora" },
	{ "Connected",          "Connectat" },
	{ "NotConnected",       "No Connectat" },
	{ "DatabaseNotFound",   "No s'ha trobat la Base de Dades" },
	{ "UserPwdError",       "No coincideix l'Usuari i la Contrasenya" },
	{ "RoleNotFound",       "Rol no trobat/completat" },
	{ "Authorized",         "Autoritzat" },
	{ "Ok",                 "Acceptar" },
	{ "Cancel",             "Cancel.lar" },
	{ "VersionConflict",    "Conflicte Versions:" },
	{ "VersionInfo",        "Servidor <> Client" },
	{ "PleaseUpgrade",      "Descarregui una nova versi\u00f3 del Programa" }
	};

	/**
	 *  Get Contents
	 *  @return context
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes_ca
