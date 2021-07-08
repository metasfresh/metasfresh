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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.process.rpl.model.I_C_OLCand;

/**
 * Resets the {@link I_C_OLCand#isGroupingError()}} for the currently selected candidates.
 **/
public class C_OLCand_ClearGroupingError extends JavaProcess
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected void prepare()
	{
		// Display process logs only if the process failed.
		// NOTE: we do that because this process is called from window Gear and the user shall not see a popoup, only the status line.
		setShowProcessLogs(ProcessExecutionResult.ShowProcessLogs.OnError);
	}

	@Override
	protected String doIt() throws Exception
	{
		final ICompositeQueryUpdater<I_C_OLCand> updater = queryBL.createCompositeQueryUpdater(I_C_OLCand.class)
				.addSetColumnValue(I_C_OLCand.COLUMNNAME_IsGroupingError, false)
				.addSetColumnValue(I_C_OLCand.COLUMNNAME_GroupingErrorMessage, null);

		return "@Updated@ #" + createOLCandQueryBuilder().create().update(updater);
	}

	protected IQueryBuilder<I_C_OLCand> createOLCandQueryBuilder()
	{
		return queryBL.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_IsGroupingError, true)
				.filter(getProcessInfo().getQueryFilterOrElseFalse());
	}

}
