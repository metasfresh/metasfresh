package de.metas.elasticsearch.process;

import java.util.Iterator;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkResponse;

import com.google.common.base.Stopwatch;

import de.metas.elasticsearch.IESModelIndexingService;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.business
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

public class ES_IndexTable extends SvrProcess
{
	@Param(parameterName = "WhereClause")
	private final String p_WhereClause = null;

	@Param(parameterName = "OrderByClause")
	private final String p_OrderByClause = null;

	@Param(parameterName = "Limit")
	private final int p_Limit = -1;

	@Override
	protected String doIt() throws Exception
	{
		final Stopwatch duration = Stopwatch.createStarted();

		final BulkResponse response = Services.get(IESModelIndexingService.class)
				.addToIndexes(retrieveRecordsToIndex())
				.actionGet();

		duration.stop();

		if (response.hasFailures())
		{
			final String failureMessage = response.buildFailureMessage();
			throw new ElasticsearchException(failureMessage);
		}

		return "Indexed " + response.getItems().length + " documents in " + duration;
	}

	private Iterator<Object> retrieveRecordsToIndex()
	{
		final I_AD_Table adTable = getRecord(I_AD_Table.class);
		final String tableName = adTable.getTableName();

		final TypedSqlQuery<Object> query = new Query(getCtx(), tableName, p_WhereClause, ITrx.TRXNAME_None)
				.setOrderBy(p_OrderByClause)
				.setLimit(p_Limit);
		final Iterator<Object> recordsIterator = query.iterate();
		return recordsIterator;
	}

}
