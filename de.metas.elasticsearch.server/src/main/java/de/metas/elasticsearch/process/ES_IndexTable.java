package de.metas.elasticsearch.process;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;

import com.google.common.base.Stopwatch;

import de.metas.elasticsearch.indexer.IESIndexerResult;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.indexer.IESModelIndexersRegistry;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.Process;

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

/**
 * SysAdmin process used to completely index a given table using all registered model indexers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Process
public class ES_IndexTable extends JavaProcess
{
	// services
	private final transient IESModelIndexersRegistry modelIndexingService = Services.get(IESModelIndexersRegistry.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private String p_ModelTableName;

	@Param(parameterName = "WhereClause")
	private String p_WhereClause = null;

	@Param(parameterName = "OrderByClause")
	private String p_OrderByClause = null;

	@Param(parameterName = "Limit")
	private int p_Limit = -1;

	//
	// Statistics
	private int countAll = 0;
	private int countErrors = 0;

	@Override
	protected void prepare()
	{
		final I_AD_Table adTable = getRecord(I_AD_Table.class);
		p_ModelTableName = adTable.getTableName();
		Check.assumeNotEmpty(p_ModelTableName, "p_ModelTableName is not empty");
	}

	@Override
	protected String doIt() throws Exception
	{
		final String modelTableName = getModelTableName();
		final Collection<IESModelIndexer> modelIndexers = modelIndexingService.getModelIndexersByTableName(modelTableName);
		if (modelIndexers.isEmpty())
		{
			throw new AdempiereException("No model indexers were defined for " + modelTableName);
		}

		final Stopwatch duration = Stopwatch.createStarted();

		modelIndexers
				.stream()
				.forEach(modelIndexer -> indexModelsFor(modelIndexer));

		return "Indexed " + countAll + " documents, " + countErrors + " errors, took " + duration;
	}

	private final String getModelTableName()
	{
		return p_ModelTableName;
	}

	private void indexModelsFor(final IESModelIndexer modelIndexer)
	{
		// Make sure the index exists and has the right config & mappings
		modelIndexer.createUpdateIndex();

		final Iterator<Object> modelsToIndex = retrieveModelsToIndex(modelIndexer);

		final IESIndexerResult result = modelIndexer.addToIndex(modelsToIndex);

		countAll += result.getTotalCount();
		countErrors += result.getFailuresCount();

		addLog("{} - {}", modelIndexer, result.getSummary());
	}

	private Iterator<Object> retrieveModelsToIndex(final IESModelIndexer modelIndexer)
	{
		final String modelTableName = modelIndexer.getModelTableName();
		final List<IESModelIndexerTrigger> triggers = modelIndexer.getTriggers();
		if (triggers.isEmpty())
		{
			addLog("Warning: skip {} because there are no triggers defined for it", modelIndexer);
			return Collections.emptyIterator();
		}

		final ICompositeQueryFilter<Object> triggerFilters = queryBL.createCompositeQueryFilter(modelTableName)
				.setJoinOr();
		for (final IESModelIndexerTrigger trigger : triggers)
		{
			final IQueryFilter<Object> filter = trigger.getMatchingModelsFilter();
			triggerFilters.addFilter(filter);
		}

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(modelTableName, this)
				.filter(triggerFilters);

		if (!Check.isEmpty(p_WhereClause, true))
		{
			queryBuilder.filter(new TypedSqlQueryFilter<>(p_WhereClause));
		}

		if (p_Limit > 0)
		{
			queryBuilder.setLimit(p_Limit);
		}

		final IQuery<Object> query = queryBuilder.create();

		if (!Check.isEmpty(p_OrderByClause, true))
		{
			query.setOrderBy(queryBL.createSqlQueryOrderBy(p_OrderByClause));
		}

		//
		// Execute query
		return query.iterate(Object.class);
	}
}
