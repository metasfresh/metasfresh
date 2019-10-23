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
 *  Login Resource Strings (French)
 *
 *  @author     Jean-Luc SCHEIDEGGER
 *  @version    $Id: ALoginRes_fr.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ALoginRes_fr extends ListResourceBundle
{
	// TODO Run native2ascii to convert to plain ASCII !! 
	
	/** Translation Content     */
	static final Object[][] contents = new String[][]
	{
	{ "Connection",             "Connexion" },
	{ "Defaults",               "D\u00E9fauts" },
	{ "Login",                  "Login ADempiere" },
	{ "File",                   "Fichier" },
	{ "Exit",                   "Sortir" },
	{ "Help",                   "Aide" },
	{ "About",                  "A propos de" },
	{ "Host",                   "Serveur" },
	{ "Database",               "Base de donn\u00E9es" },
	{ "User",                   "Utilisateur" },
	{ "EnterUser",              "Entrer votre code utilisateur" },
	{ "Password",               "Mot de passe" },
	{ "EnterPassword",          "Entrer le mot de passe" },
	{ "Language",               "Langue" },
	{ "SelectLanguage",         "S\u00E9lectionnez votre langue" },
	{ "Role",                   "R\u00F4le" },
	{ "Client",                 "Soci\u00E9t\u00E9" },
	{ "Organization",           "D\u00E9partement" },
	{ "Date",                   "Date" },
	{ "Warehouse",              "Stock" },
	{ "Printer",                "Imprimante" },
	{ "Connected",              "Connect\u00E9" },
	{ "NotConnected",           "Non Connect\u00E9" },
	{ "DatabaseNotFound",       "Base de donn\u00E9es non trouv\u00E9e" },
	{ "UserPwdError",           "L'utilisateur n'a pas entr\u00E9 de mot de passe" },
	{ "RoleNotFound",           "R\u00F4le non trouv\u00E9" },
	{ "Authorized",             "Autoris\u00E9" },
	{ "Ok",                     "Ok" },
	{ "Cancel",                 "Annuler" },
	{ "VersionConflict",        "Conflit de Version:" },
	{ "VersionInfo",            "Serveur <> Client" },
	{ "PleaseUpgrade",          "SVP, mettez \u00E0 jour le programme" }
	};

	/**
	 *  Get Contents
	 *  @return data
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContents
}   //  ALoginRes_fr
