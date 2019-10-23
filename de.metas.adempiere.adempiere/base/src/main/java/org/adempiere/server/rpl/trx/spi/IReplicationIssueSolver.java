package org.adempiere.server.rpl.trx.spi;

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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverBL;
import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;

/**
 * Implementors of this interface can be passed to
 * {@link IReplicationIssueSolverBL#solveReplicationIssues(org.adempiere.process.rpl.model.I_EXP_ReplicationTrx, Class, IReplicationIssueSolver, IReplicationIssueSolverParams)}.
 * 
 * TODO: add error handling. Suggestion: introduce a ReplicationIssueSolverException that can be thrown by {@link #solveIssues(IReplicationIssueAware, IReplicationIssueSolverParams)} and
 * that is handled by the issue-solver-processor.
 *
 * @param <T> the class of records to be solved. It is assumed that {@link InterfaceWrapperHelper#getTableName(Class)} is able to return something for the class
 * 
 */
public interface IReplicationIssueSolver<T extends IReplicationIssueAware>
{
	/**
	 * Solve the issue. Throw an exception if there is a problem.
	 *
	 * Saving database changes is the responsibility of implementors.
	 *
	 * @param recordWithIssues
	 * @param params
	 */
	void solveIssues(IReplicationIssueAware recordWithIssues, IReplicationIssueSolverParams params);
}
