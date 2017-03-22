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


import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.util.Loggables;

/**
 * This implementation does nothing with the given {@link IReplicationIssueAware}.
 *
 * @author ts
 *
 * @param <T>
 */
public class NoOpIssueSolver<T extends IReplicationIssueAware> implements IReplicationIssueSolver<T>
{
	@Override
	public void solveIssues(final IReplicationIssueAware recordWithIssues, final IReplicationIssueSolverParams params)
	{
		Loggables.get().addLog("NoOpIssueSolver is called with IReplicationIssueAware={}", recordWithIssues);
		// nothing to change; we just want to clear the record for further processing.
	}

}
