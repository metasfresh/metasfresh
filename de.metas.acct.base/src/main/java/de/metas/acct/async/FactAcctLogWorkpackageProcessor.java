package de.metas.acct.async;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import de.metas.acct.IFactAcctLogBL;
import de.metas.acct.IFactAcctLogDAO;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;

/*
 * #%L
 * de.metas.acct
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Processes {@link I_Fact_Acct_Log}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FactAcctLogWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	/**
	 * Schedules {@link I_Fact_Acct_Log} to be processed (async).
	 * 
	 * @param request
	 */
	public static final void schedule(final FactAcctLogProcessRequest request)
	{
		SCHEDULER.schedule(request);
	}

	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IFactAcctLogBL factAcctLogBL = Services.get(IFactAcctLogBL.class);

	private static final String SYSCONFIG_MaxLogsToProcess = "de.metas.acct.async.FactAcctLogWorkpackageProcessor.MaxLogsToProcess";
	private static final int DEFAULT_MaxLogsToProcess = 500;

	private static final FactAcctLogWorkpackageProcessorScheduler SCHEDULER = new FactAcctLogWorkpackageProcessorScheduler();

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workPackage);
		final int maxLogsToProcess = getMaxLogsToProcess();
		factAcctLogBL.processAll(ctx, maxLogsToProcess);

		if (Services.get(IFactAcctLogDAO.class).hasLogs(ctx, IFactAcctLogDAO.PROCESSINGTAG_NULL))
		{
			schedule(FactAcctLogProcessRequest.of(ctx));
		}

		return Result.SUCCESS;
	}

	private final int getMaxLogsToProcess()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_MaxLogsToProcess, DEFAULT_MaxLogsToProcess);
	}

}
