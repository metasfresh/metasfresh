package org.adempiere.server.rpl.trx.api;

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


import java.util.Map;

import org.adempiere.process.rpl.model.I_EXP_ReplicationTrx;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueSolver;

import de.metas.util.ISingletonService;

/**
 * Business logic that helps solving replication issues. To use it, implement you specific solver and pass it to the {@link #solveReplicationIssues(IReplicationIssueSolver, I_EXP_ReplicationTrx)}
 * method.
 *
 * @author ts
 *
 */
public interface IReplicationIssueSolverBL extends ISingletonService
{
	IReplicationIssueSolverParams createParams(Map<String, Object> params);

	IReplicationTrxLinesProcessorResult solveReplicationIssues(I_EXP_ReplicationTrx rplTrx, Class<? extends IReplicationIssueAware> issueAwareType,
			IReplicationIssueSolver<? extends IReplicationIssueAware> issueSolver, IReplicationIssueSolverParams params);

}
