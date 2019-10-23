package org.adempiere.server.rpl.trx.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.process.rpl.model.X_EXP_ReplicationTrxLine;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverDAO;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.server.rpl.trx.api.IReplicationTrxLinesProcessorResult;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;

import de.metas.util.Services;
import lombok.NonNull;

public class ReplicationTrxLinesProcessor implements ITrxItemChunkProcessor<I_EXP_ReplicationTrxLine, IReplicationTrxLinesProcessorResult>
{
	private final ReplicationTrxLinesProcessorResult result = new ReplicationTrxLinesProcessorResult();

	private IReplicationIssueSolverParams params = null;
	private IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver;

	private List<I_EXP_ReplicationTrxLine> currentTrxLines;

	public void setParams(@NonNull final IReplicationIssueSolverParams params)
	{
		this.params = params;
	}

	public void setReplicationIssueSolver(final IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver)
	{
		this.issueSolver = issueSolver;
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		// this.processorCtx = processorCtx;
	}

	@Override
	public ReplicationTrxLinesProcessorResult getResult()
	{
		return result;
	}

	@Override
	public boolean isSameChunk(final I_EXP_ReplicationTrxLine item)
	{
		return false;
	}

	@Override
	public void newChunk(final I_EXP_ReplicationTrxLine item)
	{
		currentTrxLines = new ArrayList<I_EXP_ReplicationTrxLine>();
	}

	@Override
	public void process(final I_EXP_ReplicationTrxLine item) throws Exception
	{
		final IReplicationIssueAware issueAware = Services.get(IReplicationIssueSolverDAO.class).retrieveReplicationIssueAware(item);
		issueSolver.solveIssues(issueAware, params);

		currentTrxLines.add(item);
		result.addReplicationIssueAware(issueAware);
	}

	@Override
	public void completeChunk()
	{
		for (final I_EXP_ReplicationTrxLine line : currentTrxLines)
		{
			line.setReplicationTrxStatus(X_EXP_ReplicationTrxLine.REPLICATIONTRXSTATUS_Vollstaendig);
			InterfaceWrapperHelper.save(line);
		}
	}

	@Override
	public void cancelChunk()
	{
		currentTrxLines = null;
	}

}
