/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
import java.util.TreeSet;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import de.metas.util.Services;
import it.sauronsoftware.cron4j.SchedulingPattern;


/**
 *	Scheduler Model
 *
 *  @author Jorg Janke
 *  @version $Id: MScheduler.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MScheduler extends X_AD_Scheduler
	implements AdempiereProcessor, AdempiereProcessor2
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6563650236096742870L;

	/**
	 * 	Get Active
	 *	@param ctx context
	 *	@return active processors
	 */
	public static MScheduler[] getActive (Properties ctx)
	{
		List<MScheduler> list = new Query(ctx, Table_Name, null, null)
		.setOnlyActiveRecords(true)
		.list(MScheduler.class);
		MScheduler[] retValue = new MScheduler[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getActive

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Scheduler_ID id
	 *	@param trxName transaction
	 */
	public MScheduler (Properties ctx, int AD_Scheduler_ID, String trxName)
	{
		super (ctx, AD_Scheduler_ID, trxName);
		if (AD_Scheduler_ID == 0)
		{
		//	setAD_Process_ID (0);
		//	setName (null);
			setScheduleType (SCHEDULETYPE_Frequency);	// F
			setFrequencyType (FREQUENCYTYPE_Day);
		//	setFrequency (1);
		//	setMonthDay(1);
		//	setWeekDay(WEEKDAY_Monday);
			//
			setKeepLogDays (7);
		//	setSupervisor_ID (0);
		}
	}	//	MScheduler

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MScheduler (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MScheduler

	/**	Process Parameter			*/
	private List<I_AD_Scheduler_Para> _parameters = null;
	/** Process Recipients			*/
	private MSchedulerRecipient[]	m_recipients = null;

	/**
	 * 	Get Server ID
	 *	@return id
	 */
	@Override
	public String getServerID ()
	{
		return "Scheduler" + get_ID();
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
		final String whereClause = MSchedulerLog.COLUMNNAME_AD_Scheduler_ID+"=?";
		List<MSchedulerLog> list = new Query(getCtx(), MSchedulerLog.Table_Name, whereClause, get_TrxName())
		.setParameters(new Object[]{getAD_Scheduler_ID()})
		.setOrderBy("Created DESC")
		.list(MSchedulerLog.class);
		MSchedulerLog[] retValue = new MSchedulerLog[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getLogs

	/**
	 * Delete old Request Log
	 *
	 * @param trxName
	 * @return number of records deleted
	 */
	public int deleteLog(final String trxName)
	{
		if (getKeepLogDays() < 1)
			return 0;
		String sql = "DELETE FROM AD_SchedulerLog "
			+ "WHERE AD_Scheduler_ID=" + getAD_Scheduler_ID()
			+ " AND (Created+" + getKeepLogDays() + ") < now()";
		int no = DB.executeUpdateEx(sql, trxName);
		return no;
	}	//	deleteLog

	/**
	 * 	Get Parameters
	 *	@param reload reload
	 *	@return parameter
	 */
	public List<I_AD_Scheduler_Para> getParameters (final boolean reload)
	{
		if (reload || _parameters == null)
		{
			synchronized (this)
			{
				if(reload || _parameters == null)
				{
					_parameters = Services.get(IQueryBL.class)
							.createQueryBuilder(I_AD_Scheduler_Para.class, getCtx(), get_TrxName())
							.addEqualsFilter(I_AD_Scheduler_Para.COLUMNNAME_AD_Scheduler_ID, getAD_Scheduler_ID())
							.addOnlyActiveRecordsFilter()
							.create()
							.listImmutable(I_AD_Scheduler_Para.class);
				}
			}
		}

		return _parameters;
	}	//	getParameter

	/**
	 * 	Get Recipients
	 *	@param reload reload
	 *	@return Recipients
	 */
	public MSchedulerRecipient[] getRecipients (boolean reload)
	{
		if (!reload && m_recipients != null)
			return m_recipients;
		//
		String whereClause = MSchedulerRecipient.COLUMNNAME_AD_Scheduler_ID+"=?";
		final List<MSchedulerRecipient> list = new Query(getCtx(), MSchedulerRecipient.Table_Name, whereClause, get_TrxName())
		.setParameters(new Object[]{getAD_Scheduler_ID()})
		.setOnlyActiveRecords(true)
		.list(MSchedulerRecipient.class);
		m_recipients = new MSchedulerRecipient[list.size()];
		list.toArray(m_recipients);
		return m_recipients;
	}	//	getRecipients

	/**
	 * 	Get Recipient AD_User_IDs
	 *	@return array of user IDs
	 */
	public Integer[] getRecipientAD_User_IDs()
	{
		TreeSet<Integer> list = new TreeSet<>();
		MSchedulerRecipient[] recipients = getRecipients(false);
		for (int i = 0; i < recipients.length; i++)
		{
			MSchedulerRecipient recipient = recipients[i];
			if (!recipient.isActive())
				continue;
			if (recipient.getAD_User_ID() > 0)
			{
				list.add(recipient.getAD_User_ID());
			}
			if (recipient.getAD_Role_ID() > 0)
			{
				final List<Integer> allRoleUserIds = Services.get(IRoleDAO.class).retrieveUserIdsForRoleId(recipient.getAD_Role_ID());
				list.addAll(allRoleUserIds);
			}
		}
		//	Add Updater
		if (list.size() == 0
				&& getUpdatedBy() > 0 // avoid sending mails/notfications to the "System" user
				)
		{
			list.add(getUpdatedBy());
		}
		//
		return list.toArray(new Integer[list.size()]);
	}	//	getRecipientAD_User_IDs

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//	Set Schedule Type & Frequencies
		if (SCHEDULETYPE_Frequency.equals(getScheduleType()))
		{
			if (getFrequencyType() == null)
				setFrequencyType(FREQUENCYTYPE_Day);
			if (getFrequency() < 1)
				setFrequency(1);
			setCronPattern(null);
		}
		else if (SCHEDULETYPE_CronSchedulingPattern.equals(getScheduleType()))
		{
			String pattern = getCronPattern();
			if (pattern != null && pattern.trim().length() > 0)
			{
				if (!SchedulingPattern.validate(pattern))
				{
					throw new AdempiereException("@InvalidCronPattern@");
				}
			}
		}
		return true;
	}	//	beforeSave

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MScheduler[");
		sb.append (get_ID ()).append ("-").append (getName()).append ("]");
		return sb.toString ();
	}	//	toString

	@Override
	public boolean saveOutOfTrx()
	{
		return save(ITrx.TRXNAME_None);
	}

}	//	MScheduler
