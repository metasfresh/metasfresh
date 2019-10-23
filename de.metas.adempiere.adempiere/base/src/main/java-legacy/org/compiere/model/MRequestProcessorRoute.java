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
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;


/**
 *	Request Processor Route
 *	
 *  @author Jorg Janke
 *  @version $Id: MRequestProcessorRoute.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRequestProcessorRoute extends X_R_RequestProcessor_Route
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8739358607059413339L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param R_RequestProcessor_Route_ID id
	 */
	public MRequestProcessorRoute (Properties ctx, int R_RequestProcessor_Route_ID, String trxName)
	{
		super (ctx, R_RequestProcessor_Route_ID, trxName);
	}	//	MRequestProcessorRoute

	/**
	 * 	Load Constructor
	 *	@param ctx context 
	 *	@param rs result set
	 */
	public MRequestProcessorRoute (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequestProcessorRoute

}	//	MRequestProcessorRoute
