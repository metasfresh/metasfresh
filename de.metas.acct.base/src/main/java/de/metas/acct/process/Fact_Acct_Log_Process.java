package de.metas.acct.process;

import java.util.Properties;

import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;

import de.metas.acct.IFactAcctLogBL;
import de.metas.acct.IFactAcctLogDAO;
import de.metas.acct.async.FactAcctLogProcessRequest;
import de.metas.acct.async.FactAcctLogWorkpackageProcessor;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Process pending {@link I_Fact_Acct_Log} records.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Fact_Acct_Log_Process extends JavaProcess
{
	private final transient IFactAcctLogBL factAcctLogBL = Services.get(IFactAcctLogBL.class);
	private final transient IFactAcctLogDAO factAcctLogDAO = Services.get(IFactAcctLogDAO.class);

	private final String PARAM_IsAsync = "IsAsync";
	private boolean p_IsAsync = true;

	private final int p_MaxRecordsToProcessSync = IQuery.NO_LIMIT;

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		if (params.hasParameter(PARAM_IsAsync))
		{
			p_IsAsync = params.getParameterAsBool(PARAM_IsAsync);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();

		//
		// Process async
		if (p_IsAsync)
		{
			if (factAcctLogDAO.hasLogs(ctx, IFactAcctLogDAO.PROCESSINGTAG_NULL))
			{
				final FactAcctLogProcessRequest request = FactAcctLogProcessRequest.of(ctx);
				FactAcctLogWorkpackageProcessor.schedule(request);
			}
			else
			{
				addLog("@NotFound@ @" + I_Fact_Acct_Log.Table_Name + "@");
			}
		}
		//
		// Process sync
		else
		{
			factAcctLogBL.processAll(ctx, p_MaxRecordsToProcessSync);
		}

		return MSG_OK;
	}
}
