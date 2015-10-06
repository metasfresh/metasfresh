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
 *	Scheduler Log
 *	
 *  @author Jorg Janke
 *  @version $Id: MSchedulerLog.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MSchedulerLog extends X_AD_SchedulerLog
	implements AdempiereProcessorLog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8105976307507562851L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_SchedulerLog_ID id
	 *	@param trxName transaction
	 */
	public MSchedulerLog (Properties ctx, int AD_SchedulerLog_ID, String trxName)
	{
		super (ctx, AD_SchedulerLog_ID, trxName);
		if (AD_SchedulerLog_ID == 0)
			setIsError(false);
	}	//	MSchedulerLog

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSchedulerLog (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MSchedulerLog

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param summary summary
	 */
	public MSchedulerLog (MScheduler parent, String summary)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_Scheduler_ID(parent.getAD_Scheduler_ID());
		setSummary(summary);
	}	//	MSchedulerLog

}	//	MSchedulerLog
