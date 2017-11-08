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
 *  @author     Kyriakos Ioannidis - kioa@openway.gr
 *  @version    $Id: DBRes.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_el extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog", 	"Server - \u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7" },
	{ "Name", 				"\u038c\u03bd\u03bf\u03bc\u03b1" },
	{ "AppsHost", 			"\u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2 \u0395\u03c6\u03b1\u03c1\u03bc\u03bf\u03b3\u03ae\u03c2" },
	{ "AppsPort", 			"\u03a0\u03cc\u03c1\u03c4\u03b1 \u0395\u03c6\u03b1\u03c1\u03bc\u03bf\u03b3\u03ae\u03c2" },
	{ "TestApps", 			"\u0394\u03bf\u03ba\u03b9\u03bc\u03ae \u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae \u0395\u03c6\u03b1\u03c1\u03bc\u03bf\u03b3\u03ae\u03c2" },
	{ "DBHost", 			"\u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2 \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "DBPort", 			"\u03a0\u03cc\u03c1\u03c4\u03b1 \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "DBName", 			"\u038c\u03bd\u03bf\u03bc\u03b1 \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "DBUidPwd", 			"\u03a7\u03c1\u03ae\u03c3\u03c4\u03b7\u03c2/\u039a\u03c9\u03b4\u03b9\u03ba\u03cc\u03c2 \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "ViaFirewall", 		"\u039c\u03ad\u03c3\u03c9 \u03a4\u03b5\u03af\u03c7\u03bf\u03c5\u03c2 \u03a0\u03c1\u03bf\u03c3\u03c4\u03b1\u03c3\u03af\u03b1\u03c2" },
	{ "FWHost", 			"\u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2 \u03a4\u03b5\u03af\u03c7\u03bf\u03c5\u03c2 \u03a0\u03c1\u03bf\u03c3\u03c4\u03b1\u03c3\u03af\u03b1\u03c2" },
	{ "FWPort", 			"\u03a0\u03cc\u03c1\u03c4\u03b1 \u03a4\u03b5\u03af\u03c7\u03bf\u03c5\u03c2 \u03a0\u03c1\u03bf\u03c3\u03c4\u03b1\u03c3\u03af\u03b1\u03c2" },
	{ "TestConnection", 	"\u0394\u03bf\u03ba\u03b9\u03bc\u03ae \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "Type", 				"\u03a4\u03cd\u03c0\u03bf\u03c2 \u0392\u03ac\u03c3\u03b7\u03c2 \u0394\u03b5\u03b4\u03bf\u03bc\u03ad\u03bd\u03c9\u03bd" },
	{ "BequeathConnection", "Bequeath \u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7" },
	{ "Overwrite", 			"\u0395\u03c0\u03b9\u03ba\u03ac\u03bb\u03c5\u03c8\u03b7" },
	{ "ConnectionProfile",	"\u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7" },
	{ "LAN",		 		"LAN" },
	{ "TerminalServer",		"Terminal Server" },
	{ "VPN",		 		"VPN" },
	{ "WAN", 				"WAN" },
	{ "ConnectionError", 	"\u03a3\u03c6\u03ac\u03bb\u03bc\u03b1 \u03a3\u03cd\u03bd\u03b4\u03b5\u03c3\u03b7\u03c2" },
	{ "ServerNotActive", 	"\u0391\u03bd\u03b5\u03bd\u03b5\u03c1\u03b3\u03cc\u03c2 \u0394\u03b9\u03b1\u03ba\u03bf\u03bc\u03b9\u03c3\u03c4\u03ae\u03c2" }
	};

	/**
	 * Get Contents
	 * @return contents
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  Res
