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
 *  @author     Jesse Jr
 *  @version    $Id: DBRes_pt.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_pt extends ListResourceBundle
{
	/** Data        */
	//Characters encoded to UTF8 Hex, so no more problems with svn commits
	//Fernando Lucktemberg - CenturuyOn Consultoria
	static final Object[][] contents = new String[][]
	{
	{ "CConnectionDialog",  "Conex\u00e3o" },
	{ "Name",               "Nome" },
	{ "AppsHost",           "Servidor de Aplica\u00e7\u00e3o" },
	{ "AppsPort",           "Porta TCP da Aplica\u00e7\u00e3o" },
	{ "TestApps",           "Testar Aplica\u00e7\u00e3o" },
	{ "DBHost",             "Servidor do Banco de Dado" },
	{ "DBPort",             "Porta TCP do Banco de Dados" },
	{ "DBName",             "Nome do Banco de Dados" },
	{ "DBUidPwd",           "Usu\u00e1rio / Senha" },
	{ "ViaFirewall",        "via Firewall" },
	{ "FWHost",             "Servidor de Firewall" },
	{ "FWPort",             "Porta TCP do Firewall" },
	{ "TestConnection",     "Testar Banco de Dados" },
	{ "Type",               "Tipo de Banco de Dados" },
	{ "BequeathConnection", "Conex\u00e3o Bequeath" },
	{ "Overwrite",          "Sobrescrever" },
	{ "ConnectionProfile",	"Connection" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError",    "Erro de Conex\u00e3o" },
	{ "ServerNotActive",    "Servidor n\u00e3o Ativo" }
	};

	/**
	 * Get Contsnts
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  Res
