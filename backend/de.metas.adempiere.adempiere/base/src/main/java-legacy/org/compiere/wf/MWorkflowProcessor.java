/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.wf;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.Query;
import org.compiere.model.X_AD_WorkflowProcessor;
import org.compiere.util.DB;

/**
 * Workflow Processor Model
 *
 * @author Jorg Janke
 * @version $Id: MWorkflowProcessor.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MWorkflowProcessor extends X_AD_WorkflowProcessor
		implements AdempiereProcessor
{
	/**
	 *
	 */
	private static final long serialVersionUID = 9164558879064747427L;

	/**
	 * Get Active
	 *
	 * @param ctx context
	 * @return active processors
	 */
	public static MWorkflowProcessor[] getActive(Properties ctx)
	{
		List<MWorkflowProcessor> list = new Query(ctx, Table_Name, null, null)
				.setOnlyActiveRecords(true)
				.list(MWorkflowProcessor.class);
		MWorkflowProcessor[] retValue = new MWorkflowProcessor[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getActive

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_WorkflowProcessor_ID id
	 * @param trxName transaction
	 */
	public MWorkflowProcessor(Properties ctx, int AD_WorkflowProcessor_ID, String trxName)
	{
		super(ctx, AD_WorkflowProcessor_ID, trxName);
	}	// MWorkflowProcessor

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MWorkflowProcessor(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MWorkflowProcessor

	/**
	 * Get Server ID
	 *
	 * @return id
	 */
	@Override
	public String getServerID()
	{
		return "WorkflowProcessor" + get_ID();
	}	// getServerID

	/**
	 * Get Date Next Run
	 *
	 * @param requery requery
	 * @return date next run
	 */
	@Override
	public Timestamp getDateNextRun(boolean requery)
	{
		if (requery)
			load(get_TrxName());
		return getDateNextRun();
	}	// getDateNextRun

	/**
	 * Get Logs
	 *
	 * @return logs
	 */
	@Override
	public AdempiereProcessorLog[] getLogs()
	{
		List<MWorkflowProcessorLog> list = new Query(getCtx(), MWorkflowProcessorLog.Table_Name, "AD_WorkflowProcessor_ID=?", get_TrxName())
				.setParameters(new Object[] { getAD_WorkflowProcessor_ID() })
				.setOrderBy("Created DESC")
				.list(MWorkflowProcessorLog.class);
		MWorkflowProcessorLog[] retValue = new MWorkflowProcessorLog[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getLogs

	/**
	 * Delete old Request Log
	 *
	 * @return number of records
	 */
	public int deleteLog()
	{
		if (getKeepLogDays() < 1)
			return 0;
		String sql = "DELETE FROM AD_WorkflowProcessorLog "
				+ "WHERE AD_WorkflowProcessor_ID=" + getAD_WorkflowProcessor_ID()
				+ " AND (Created+" + getKeepLogDays() + ") < now()";
		int no = DB.executeUpdate(sql, get_TrxName());
		return no;
	}	// deleteLog

	@Override
	public boolean saveOutOfTrx()
	{
		return save(ITrx.TRXNAME_None);
	}

}	// MWorkflowProcessor
