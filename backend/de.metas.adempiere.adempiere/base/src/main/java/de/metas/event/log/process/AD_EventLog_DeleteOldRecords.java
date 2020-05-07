package de.metas.event.log.process;

import java.sql.Timestamp;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.event.model.I_AD_EventLog;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class AD_EventLog_DeleteOldRecords extends JavaProcess
{

	@Param(mandatory = true, parameterName = "LastUpdatedDaysBack")
	private int p_lastUpdatedDaysBack;

	@Override
	protected String doIt() throws Exception
	{
		final Timestamp maxUpdated = TimeUtil.addDays(Env.getDate(Env.getCtx()), -p_lastUpdatedDaysBack);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_AD_EventLog> noErrorFilter = queryBL.createCompositeQueryFilter(I_AD_EventLog.class)
				.setJoinOr()
				.addEqualsFilter(I_AD_EventLog.COLUMN_IsError, false)
				.addEqualsFilter(I_AD_EventLog.COLUMN_IsErrorAcknowledged, true);

		final int deleted = queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addCompareFilter(I_AD_EventLog.COLUMN_Updated, Operator.LESS_OR_EQUAL, maxUpdated)
				.filter(noErrorFilter)
				.create()
				.deleteDirectly(); // the log entries are deleted by the DB, via an FK constraint

		addLog("Deleted {} records with a Updated <= {}", deleted, maxUpdated);
		return MSG_OK;
	}

}
