package org.adempiere.server.rpl.trx.process;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_C_OLCand;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverBL;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.server.rpl.trx.api.IReplicationTrxLinesProcessorResult;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;
import org.adempiere.server.rpl.trx.spi.NoOpIssueSolver;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;

import de.metas.ordercandidate.api.IOLCandValdiatorBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;

/**
 * Uses {@link IOLCandValdiatorBL} to check the prices and other aspects of all {@link I_C_OLCand} for a certain {@link I_EXP_ReplicationTrx#COLUMNNAME_EXP_ReplicationTrx_ID EXP_ReplicationTrx_ID}. <br>
 * Then, if all prices are OK, uses {@link NoOpIssueSolver} to flag the {@link I_C_OLCand}s of that trx-ID as solved.
 * Finally, the process performs an update to set <code>C_OLCand.IsImportedWithIssues='N'</code> to all olcands.
 */
public class OLCandIssueSolverProcess extends JavaProcess
{

	private int p_EXP_ReplicationTrx_ID;
	private final Map<String, Object> params = new HashMap<String, Object>();

	//
	// services
	private final IReplicationIssueSolverBL replicationIssueSolverBL = Services.get(IReplicationIssueSolverBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and the user shall not see a popoup, only the status line.
		setShowProcessLogs(ShowProcessLogs.OnError);

		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				// do nothing
			}
			else if (I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID.equals(name))
			{
				p_EXP_ReplicationTrx_ID = para[i].getParameterAsInt();
			}
			else
			{
				final Object value = para[i].getParameter();
				params.put(name, value);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Class<? extends IReplicationIssueAware> issueAwareType = I_C_OLCand.class;
		final IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver = new NoOpIssueSolver<I_C_OLCand>();

		final I_EXP_ReplicationTrx rplTrx = InterfaceWrapperHelper.create(getCtx(), p_EXP_ReplicationTrx_ID, I_EXP_ReplicationTrx.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(rplTrx, "@EXP_ReplicationTrx_ID@ exists for ID={}", p_EXP_ReplicationTrx_ID);

		//
		// task 08072: verify that all candidates have proper pricing
		final Mutable<Integer> candidatesUpdatedDuringValidation = new Mutable<Integer>(0);
		if (!validateReplicationTrx(candidatesUpdatedDuringValidation))
		{
			// In case one of the candidates with the given EXP_ReplicationTrx_ID, do not solve issues
			return " @Updated@ # " + candidatesUpdatedDuringValidation.getValue();
		}

		// the validation was OK

		final IReplicationIssueSolverParams replicationIssueSolverParams = replicationIssueSolverBL.createParams(params);
		final IReplicationTrxLinesProcessorResult result = replicationIssueSolverBL.solveReplicationIssues(rplTrx, issueAwareType, issueSolver, replicationIssueSolverParams);

		// task 08770: each individual line was solved. Now we can flag all OLCands as cleared, so they can all be processed at once.
		final IQueryBuilder<I_C_OLCand> queryBuilder = createOLCandQueryBuilder();
		queryBuilder
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_OLCand.COLUMNNAME_IsImportedWithIssues, false)
				.execute();

		return "@Updated@ #" + result.getReplicationIssueAwareCount();
	}

	private boolean validateReplicationTrx(final Mutable<Integer> candidatesUpdated)
	{
		final IQueryBuilder<I_C_OLCand> queryBuilder = createOLCandQueryBuilder();

		final Iterator<I_C_OLCand> selectedCands = queryBuilder
				.create()
				.iterate(I_C_OLCand.class);

		final IOLCandValdiatorBL olCandValdiatorBL = Services.get(IOLCandValdiatorBL.class);
		int candidatesWithErorr = 0;

		olCandValdiatorBL.setValidationProcessInProgress(true); // avoid the InterfaceWrapperHelper.save to trigger another validation from a MV.
		try
		{
			for (final I_C_OLCand cand : IteratorUtils.asIterable(selectedCands))
			{
				olCandValdiatorBL.validate(cand);
				if (InterfaceWrapperHelper.hasChanges(cand))
				{
					candidatesUpdated.setValue(candidatesUpdated.getValue() + 1);
					InterfaceWrapperHelper.save(cand);
				}
				if (cand.isError())
				{
					candidatesWithErorr++;
				}
			}

			if (candidatesWithErorr != 0)
			{
				addLog(msgBL.getMsg(getCtx(), IOLCandValdiatorBL.MSG_ERRORS_FOUND, new Object[] { candidatesWithErorr }));
			}

			return (candidatesWithErorr == 0); // return true if there were no errors
		}
		finally
		{
			olCandValdiatorBL.setValidationProcessInProgress(false);
		}
	}

	private IQueryBuilder<I_C_OLCand> createOLCandQueryBuilder()
	{
		final IQueryBuilder<I_C_OLCand> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_EXP_ReplicationTrx_ID, p_EXP_ReplicationTrx_ID);

		return queryBuilder;
	}
}
