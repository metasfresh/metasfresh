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
 * 	@author 	Gabriele Vivinetto - gabriele.mailing@rvmgroup.it
 * 	@version 	$Id: ALoginRes_it.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALoginRes_it extends ListResourceBundle
{
	static final Object[][] contents = new String[][]{
	//{ "Connection",       "Connection" },
	  { "Connection",       "Connessione" },
	//{ "Defaults",         "Defaults" },
	  { "Defaults",         "Defaults" }, //Need to be checked
	//{ "Login",            "ADempiere Login" },
	  { "Login",            "ADempiere Login" },
	//{ "File",             "File" },
	  { "File",             "File" },
	//{ "Exit",             "Exit" },
	  { "Exit",             "Esci" },
	//{ "Help",             "Help" },
	  { "Help",             "Aiuto" },
	//{ "About",            "About" },
	  { "About",            "Informazioni" },
	//{ "Host",             "Host" },
	  { "Host",             "Host" },
	//{ "Database",         "Database" },
	  { "Database",         "Database" },
	//{ "User",             "User ID" }, //Need to be checked. Leave "User ID" ?
	  { "User",             "Identificativo Utente" },
	//{ "EnterUser",        "Enter Application User ID" },
	  { "EnterUser",        "Identificativo Utente Applicazione" },
	//{ "Password",         "Password" },
	  { "Password",         "Password" },
	//{ "EnterPassword",    "Enter Application password" },
	  { "EnterPassword",    "Inserimento password Applicazione" },
	//{ "Language",         "Language" },
	  { "Language",         "Linguaggio" },
	//{ "SelectLanguage",   "Select your language" },
	  { "SelectLanguage",   "Selezionate il vostro linguaggio" },
	//{ "Role",             "Role" },
	  { "Role",             "Ruolo" },
	//{ "Client",           "Client" }, //Need to be checked. Everybody agree with the SAP translation ?
	  { "Client",           "Mandante" },
	//{ "Organization",     "Organization" },
	  { "Organization",     "Organizzazione" },
	//{ "Date",             "Date" },
	  { "Date",             "Data" },
	//{ "Warehouse",        "Warehouse" },
	  { "Warehouse",        "Magazzino" },
	//{ "Printer",          "Printer" },
	  { "Printer",          "Stampante" },
	//{ "Connected",        "Connected" },
	  { "Connected",        "Connesso" },
	//{ "NotConnected",     "Not Connected" },
	  { "NotConnected",     "Non Connesso" },
	//{ "DatabaseNotFound", "Database not found" },
	  { "DatabaseNotFound", "Database non trovato" },
	//{ "UserPwdError",     "User does not match password" },
	  { "UserPwdError",     "L'Utente non corrisponde alla password" },
	//{ "RoleNotFound",     "Role not found" },
	  { "RoleNotFound",     "Ruolo non trovato" },
	//{ "Authorized",       "Authorized" },
	  { "Authorized",       "Authorizzato" },
	//{ "Ok",               "Ok" },
	  { "Ok",               "Ok" },
	//{ "Cancel",           "Cancel" },
	  { "Cancel",           "Cancella" },
	//{ "VersionConflict",  "Version Conflict:" },
	  { "VersionConflict",  "Conflitto di Versione:" },
	//{ "VersionInfo",      "Server <> Client" },
	  { "VersionInfo",      "Server <> Client" },
	//{ "PleaseUpgrade",    "Please run the update program" }
	  { "PleaseUpgrade",    "Prego lanciare il programma di update" }
	};
	public Object[][] getContents()
	{
		return contents;
	}
}
