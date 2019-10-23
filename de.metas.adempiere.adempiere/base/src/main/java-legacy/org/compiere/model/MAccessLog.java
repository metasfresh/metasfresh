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
 *	Access Log Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MAccessLog.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MAccessLog extends X_AD_AccessLog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7169782622717772940L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_AccessLog_ID id
	 *	@param trxName transaction
	 */
	public MAccessLog (Properties ctx, int AD_AccessLog_ID, String trxName)
	{
		super (ctx, AD_AccessLog_ID, trxName);
	}	//	MAccessLog

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAccessLog (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAccessLog

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param Remote_Host host
	 *	@param Remote_Addr address
	 *	@param TextMsg text message
	 *	@param trxName transaction
	 */
	public MAccessLog (Properties ctx, String Remote_Host, String Remote_Addr, 
		String TextMsg, String trxName)
	{
		this (ctx, 0, trxName);
		setRemote_Addr(Remote_Addr);
		setRemote_Host(Remote_Host);
		setTextMsg(TextMsg);
	}	//	MAccessLog

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param AD_Table_ID table
	 *	@param AD_Column_ID column
	 *	@param Record_ID record
	 *	@param trxName transaction
	 */
	public MAccessLog (Properties ctx, int AD_Table_ID, int AD_Column_ID, int Record_ID, String trxName)
	{
		this (ctx, 0, trxName);
		setAD_Table_ID(AD_Table_ID);
		setAD_Column_ID(AD_Column_ID);
		setRecord_ID(Record_ID);
	}	//	MAccessLog
	
}	//	MAccessLog
