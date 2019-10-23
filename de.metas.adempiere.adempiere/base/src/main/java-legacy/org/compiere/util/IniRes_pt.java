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
package org.compiere.util;

import java.util.ListResourceBundle;

/**
 *  License Dialog Translation
 *
 *  @author     Jesse Jr
 *  @version    $Id: IniRes_pt.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class IniRes_pt extends ListResourceBundle
{
	/** Translation Content     */
	//Characters encoded to UTF8 Hex, so no more problems with svn commits
	//Fernando Lucktemberg - CenturuyOn Consultoria
	static final Object[][] contents = new String[][]
	{
	{ "Adempiere_License",   "Acordo de Licen\u00e7a" },
	{ "Do_you_accept",      "Voc\u00ea aceita os termos da Licen\u00e7a ?" },
	{ "No",                 "Licen\u00e7a" },
	{ "Yes_I_Understand",   "Sim, eu Entendi e Aceito" },
	{ "license_htm",        "org/adempiere/license.htm" },
	{ "License_rejected",   "Licen\u00e7a rejeitada ou expirada" }
	};

	/**
	 *  Get Content
	 *  @return Content
	 */
	public Object[][] getContents()
	{
		return contents;
	}   //  getContent
}   //  IniRes_pt
