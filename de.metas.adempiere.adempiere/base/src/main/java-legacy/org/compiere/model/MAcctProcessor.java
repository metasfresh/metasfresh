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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.i18n.IMsgBL;


/**
 *	Accounting Processor Model
 *	
 *  @author Jorg Janke
 *  @author     victor.perez@e-evolution.com, www.e-evolution.com
 *    			<li>RF [ 2214883 ] Remove SQL code and Replace for Query http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335 
 *  @version $Id: MAcctProcessor.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MAcctProcessor extends X_C_AcctProcessor
	implements AdempiereProcessor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6558688522646469260L;

	/**
	 * 	Get Active
	 *	@param ctx context
	 *	@return active processors
	 */
	public static MAcctProcessor[] getActive (final Properties ctx)
	{
		List<MAcctProcessor> list = new Query(ctx, MAcctProcessor.Table_Name, null, ITrx.TRXNAME_None)
										.setOnlyActiveRecords(true)
										.list(MAcctProcessor.class);
		return list.toArray(new MAcctProcessor[list.size()]);		
	}	//	getActive
	
	/**
	 * 	Standard Construvtor
	 *	@param ctx context
	 *	@param C_AcctProcessor_ID id
	 *	@param trxName transaction
	 */
	public MAcctProcessor (Properties ctx, int C_AcctProcessor_ID, String trxName)
	{
		super (ctx, C_AcctProcessor_ID, trxName);
		if (C_AcctProcessor_ID == 0)
		{
		//	setName (null);
		//	setSupervisor_ID (0);
			setFrequencyType (FREQUENCYTYPE_Hour);
			setFrequency (1);
			setKeepLogDays (7);	// 7
		}	
	}	//	MAcctProcessor

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAcctProcessor (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAcctProcessor

	/**
	 * 	Parent Constructor
	 *	@param client parent
	 *	@param Supervisor_ID admin
	 */
	public MAcctProcessor (MClient client, int Supervisor_ID)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setClientOrg(client);
		setName (client.getName() + " - "  + Services.get(IMsgBL.class).translate(getCtx(), I_C_AcctProcessor.COLUMNNAME_C_AcctProcessor_ID));
		setSupervisor_ID (Supervisor_ID);
	}	//	MAcctProcessor
	
	
	
	/**
	 * 	Get Server ID
	 *	@return id
	 */
	@Override
	public String getServerID ()
	{
		return "AcctProcessor" + get_ID();
	}	//	getServerID

	/**
	 * 	Get Date Next Run
	 *	@param requery requery
	 *	@return date next run
	 */
	@Override
	public Timestamp getDateNextRun (boolean requery)
	{
		if (requery)
			load(get_TrxName());
		return getDateNextRun();
	}	//	getDateNextRun

	/**
	 * 	Get Logs
	 *	@return logs
	 */
	@Override
	public AdempiereProcessorLog[] getLogs ()
	{
		String whereClause = "C_AcctProcessor_ID=? ";
		List<MAcctProcessorLog> list = new Query(getCtx(), MAcctProcessorLog.Table_Name,whereClause,get_TrxName())
		.setParameters(new Object[]{getC_AcctProcessor_ID()})
		.setOrderBy("Created DESC")
		.list(MAcctProcessorLog.class);
		return list.toArray(new MAcctProcessorLog[list.size()]);		
	}	//	getLogs

	/**
	 * 	Delete old Request Log
	 *	@return number of records
	 */
	public int deleteLog()
	{
		if (getKeepLogDays() < 1)
			return 0;
		String sql = "DELETE FROM C_AcctProcessorLog "
			+ "WHERE C_AcctProcessor_ID=" + getC_AcctProcessor_ID() 
			+ " AND (Created+" + getKeepLogDays() + ") < now()";
		int no = DB.executeUpdate(sql, get_TrxName());
		return no;
	}	//	deleteLog

}	//	MAcctProcessor
