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
package org.compiere.db;

import java.util.ListResourceBundle;

/**
 * Connection Resource Strings
 * @author      Jorg Janke
 * @version     $Id: DBRes_ar.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class DBRes_ar extends ListResourceBundle
{
	/** Data        */
	static final Object[][] contents = new String[][]{
	{ "CConnectionDialog", 	"\u0631\u0628\u0637 \u0643\u0645\u0628\u064a\u0631" },
	{ "Name", 		"\u0627\u0644\u0627\u0650\u0633\u0645" },
	{ "AppsHost", 		"\u0645\u0636\u064a\u0641 \u0627\u0644\u062a\u0637\u0628\u064a\u0642\u0627\u062a" },
	{ "AppsPort", 		"\u0627\u0644\u062a\u0637\u0628\u064a\u0642\u0627\u062a \u0645\u0646\u0641\u062f" },
	{ "TestApps", 		"\u062c\u0631\u0628 \u0645\u0648\u0632\u0639 \u0627\u0644\u062a\u0637\u0628\u064a\u0642\u0627\u062a" },
	{ "DBHost", 		"\u0645\u0636\u064a\u0641 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a" },
	{ "DBPort", 		"\u0645\u0646\u0641\u0630 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a" },
	{ "DBName", 		"\u0627\u0650\u0633\u0645 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a" },
	{ "DBUidPwd", 		"\u0627\u0644\u0645\u0633\u062a\u0639\u0645\u0644\\u0643\u0644\u0645\u0629 \u0627\u0644\u0633\u0631" },
	{ "ViaFirewall", 	"\u0639\u0628\u0631 \u062c\u062f\u0627\u0631 \u0646\u0627\u0631\u064a" },
	{ "FWHost", 		"\u0645\u0636\u064a\u0641 \u0627\u0644\u062c\u062f\u0627\u0631 \u0627\u0644\u0646\u0627\u0631\u064a" },
	{ "FWPort", 		"\u0645\u0646\u0641\u0630 \u0627\u0644\u062c\u062f\u0627\u0631 \u0627\u0644\u0646\u0627\u0631\u064a" },
	{ "TestConnection", 	"\u0645\u0646\u0641\u0630 \u0628\u0646\u0643 \u0627\u0644\u0645\u0639\u0644\u0648\u0645\u0627\u062a" },
	{ "Type", 		"\u0646\u0648\u0639 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a" },
	{ "BequeathConnection", "\u062a\u0631\u0643\u0629 \u0627\u0644\u0631\u0651\u064e\u0628\u0637" },
	{ "Overwrite", 		"\u0627\u0633\u062d\u0642" },
	{ "RMIoverHTTP", 	"HTTP \u0645\u0631\u0651\u0631 \u0627\u0644\u0643\u0627\u0626\u0646\u0627\u062a \u0639\u0628\u0631 \u0646\u0641\u0642" },
	{ "ConnectionError", 	"\u062e\u0637\u0623 \u0623\u062b\u0646\u0627\u0621 \u0627\u0644\u0631\u0628\u0637" },
	{ "ServerNotActive", 	"\u0627\u0644\u0645\u0648\u0632\u0639 \u0644\u0627 \u064a\u0639\u0645\u0644" }
	};

	/**
	 * Get Contsnts
	 * @return  contents
	 * @uml.property  name="contents"
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  DBRes_ar_TN

