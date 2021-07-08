package de.metas.async.api.impl;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Date;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.TimeUtil;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * Identify and deactivates stale {@link I_C_Queue_WorkPackage}s.
 * 
 * An {@link I_C_Queue_WorkPackage} is considered stale when:
 * <ul>
 * <li>it's not processed
 * <li>it's not flagged as ready for processing
 * <li>it's active
 * <li>it was last updated before a given stale date
 * </ul>
 * 
 * @author tsa
 *
 */
public class WorkpackageCleanupStaleEntries
{
	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_StaleDays = "de.metas.async.api.impl.WorkpackageCleanupStaleEntries.StaleDays";
	private static final int DEFAULT_StaleDays = 1;
	private static final String SYSCONFIG_StaleErrorMsg = "de.metas.async.api.impl.WorkpackageCleanupStaleEntries.StaleErrorMsg";
	private static final String DEFAULT_StaleErrorMsg = "Deactivated because it was stale";

	// Parameters
	private final Date now = SystemTime.asDate();
	private Properties _ctx;
	private Date _staleDateFrom;
	private String _staleErrorMsg;
	private PInstanceId _adPInstanceId;
	private ILoggable logger = Loggables.nop();

	// Status
	private Integer staleWorkpackagesCount;

	public WorkpackageCleanupStaleEntries()
	{
		super();
	}

	public void run()
	{
		final Date staleDateFrom = getStaleDateFrom();
		logger.addLog("Stale DateFrom: " + staleDateFrom);
		
		final String staleErrorMsg = buildFullStaleErrorMsg();
		

		this.staleWorkpackagesCount = Services.get(IQueryBL.class)
				//
				// Select stale workpackages
				.createQueryBuilder(I_C_Queue_WorkPackage.class, getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMN_Processed, false)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMN_IsReadyForProcessing, false)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_Queue_WorkPackage.COLUMN_Updated, Operator.LESS_OR_EQUAL, staleDateFrom)
				.create()
				//
				// Mass-update them: deactivate, flag them as IsError, set error message
				.updateDirectly()
				.addSetColumnValue(I_C_Queue_WorkPackage.COLUMNNAME_IsActive, false) // de-activate it
				.addSetColumnValue(I_C_Queue_WorkPackage.COLUMNNAME_ErrorMsg, staleErrorMsg)
				.addSetColumnValue(I_C_Queue_WorkPackage.COLUMNNAME_IsError, true)
				.execute();

		logger.addLog("" + staleWorkpackagesCount + " workpackage(s) were staled and deactivated");
	}

	/**
	 * Execute it and then return how many workpackages were deactivated because they were stale.
	 */
	public int runAndGetUpdatedCount()
	{
		run();
		return getStaleWorkpackagesCount();
	}

	public int getStaleWorkpackagesCount()
	{
		Check.assumeNotNull(staleWorkpackagesCount, "staleWorkpackagesCount not null");
		return staleWorkpackagesCount;
	}

	public WorkpackageCleanupStaleEntries setContext(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	/**
	 * Sets Stale Date.
	 * 
	 * Only "stale workpackages" which were last updated before this date will be considered.
	 * 
	 * @param staleDateFrom
	 * @return
	 */
	public WorkpackageCleanupStaleEntries setStaleDateFrom(final Date staleDateFrom)
	{
		this._staleDateFrom = staleDateFrom;
		return this;
	}

	private Date getStaleDateFrom()
	{
		if (_staleDateFrom != null)
		{
			return _staleDateFrom;
		}

		//
		// Get default stale date
		final int staleDays = sysConfigBL.getIntValue(SYSCONFIG_StaleDays, DEFAULT_StaleDays);
		if (staleDays <= 0)
		{
			throw new AdempiereException("Invalid stale days sysconfig value: " + SYSCONFIG_StaleDays);
		}
		final Date staleDateFromDefault = TimeUtil.addDays(now, -staleDays);
		return staleDateFromDefault;
	}

	public WorkpackageCleanupStaleEntries setStaleErrorMsg(final String staleErrorMsg)
	{
		this._staleErrorMsg = staleErrorMsg;
		return this;
	}

	private String buildFullStaleErrorMsg()
	{
		final StringBuilder errorMsg = new StringBuilder();
		final String baseErrorMsg = getStaleErrorMsg();
		errorMsg.append(baseErrorMsg);

		final PInstanceId adPInstanceId = getPinstanceId();
		if (adPInstanceId != null)
		{
			errorMsg.append("; Updated by AD_PInstance_ID=").append(adPInstanceId.getRepoId());
		}

		return errorMsg.toString();
	}

	private String getStaleErrorMsg()
	{
		if (!Check.isEmpty(_staleErrorMsg, true))
		{
			return _staleErrorMsg;
		}

		// Get the default
		return sysConfigBL.getValue(SYSCONFIG_StaleErrorMsg, DEFAULT_StaleErrorMsg);
	}

	/**
	 * Sets AD_PInstance_ID to be used and tag all those workpackages which were deactivated.
	 * 
	 * This is optional.
	 * 
	 * @param adPInstanceId
	 */
	public WorkpackageCleanupStaleEntries setAD_PInstance_ID(final PInstanceId adPInstanceId)
	{
		this._adPInstanceId = adPInstanceId;
		return this;
	}

	private PInstanceId getPinstanceId()
	{
		return _adPInstanceId;
	}

	public WorkpackageCleanupStaleEntries setLogger(final ILoggable logger)
	{
		Check.assumeNotNull(logger, "logger not null");
		this.logger = logger;
		return this;
	}

}
