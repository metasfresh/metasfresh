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
package org.compiere.db.connectiondialog.i18n;

import java.util.ListResourceBundle;

/**
 *  Connection Resource Strings
 *
 *  @author     Erwin Cortes
 *  @author     Jordi Luna
 *  @version    $Id: DBRes_es.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_es extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog",  "Conexi\u00f3n Server" },
	{ "Name",               "Nombre" },
	{ "AppsHost",           "Servidor de Aplicaciones" },
	{ "AppsPort",           "Puerto de Aplicaci\u00f3n" },
	{ "TestApps",           "Prueba Servidor de Aplicaciones" },
	{ "DBHost",             "Servidor de Base de Datos" },
	{ "DBPort",             "Puerto de Base de Datos" },
	{ "DBName",             "Nombre de Base de datos" },
	{ "DBUidPwd",           "Usuario / Contrase\u00f1a" },
	{ "ViaFirewall",        "v\u00eda Firewall" },
	{ "FWHost",             "Servidor de Firewall" },
	{ "FWPort",             "Puerto de Firewall" },
	{ "TestConnection",     "Prueba de Base de Datos" },
	{ "Type",               "Tipo de Base de Datos" },
	{ "BequeathConnection", "Conexi\u00f3n Delegada" },
	{ "Overwrite",          "Sobreescribir" },
	{ "ConnectionProfile",	"Conexi\u00f3n" },
	{ "LAN",		 		"Red de Area Local" },
	{ "TerminalServer",		"Servidor de Terminales" },
	{ "VPN",		 		"Red Privada Virtual" },
	{ "WAN", 				"Red de Area Amplia" },
	{ "ConnectionError",    "Error de Conexi\u00f3n" },
	{ "ServerNotActive",    "Servidor no Activo" }
	};

	/**
	 * Get Contsnts
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}
}   //  Res
