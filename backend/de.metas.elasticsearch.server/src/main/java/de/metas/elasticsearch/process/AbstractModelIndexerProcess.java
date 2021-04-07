package de.metas.elasticsearch.process;

import com.google.common.base.Stopwatch;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.elasticsearch.indexer.engine.IESIndexerResult;
import de.metas.elasticsearch.indexer.registry.ESModelIndexersRegistry;
import de.metas.elasticsearch.indexer.source.ESModelIndexerDataSources;
import de.metas.elasticsearch.indexer.source.SqlESModelIndexerDataSource;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * de.metas.elasticsearch.server
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

abstract class AbstractModelIndexerProcess extends JavaProcess implements IProcessPrecondition
{
	// services
    /** This is null if elastic search is switched off */
	protected final transient ESModelIndexersRegistry modelIndexingService = SpringContextHolder.instance.getBeanOr(ESModelIndexersRegistry.class, null);

	@Param(parameterName = "WhereClause")
	private String p_WhereClause = null;

	@Param(parameterName = "OrderByClause")
	private String p_OrderByClause = null;

	@Param(parameterName = "Limit")
	private int p_Limit = -1;

	@Param(parameterName = "ES_DeleteIndex")
	private boolean p_DeleteIndex = false;

	//
	// Statistics
	private int countAll = 0;
	private int countErrors = 0;

	protected abstract Collection<ESModelIndexer> getModelIndexers();

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
        if (modelIndexingService == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ESSystem is not active");
		}
		return ProcessPreconditionsResolution.accept();
	}
	
	@Override
	protected final String doIt()
	{
		final Collection<ESModelIndexer> modelIndexers = getModelIndexers();
		if (modelIndexers.isEmpty())
		{
			throw new AdempiereException("No model indexers were defined");
		}

		final Stopwatch duration = Stopwatch.createStarted();

		modelIndexers.forEach(this::indexModelsFor);

		return "Indexed " + countAll + " documents, " + countErrors + " errors, took " + duration;
	}

	private void indexModelsFor(final ESModelIndexer modelIndexer)
	{
		if (p_DeleteIndex)
		{
			modelIndexer.deleteIndex();
			addLog("{} - Index deleted", modelIndexer.getId());
		}

		// Make sure the index exists and has the right config & mappings
		if (modelIndexer.createUpdateIndex())
		{
			addLog("{} - Index created now", modelIndexer.getId());
		}

		final List<IESModelIndexerTrigger> triggers = modelIndexer.getTriggers();
		if (triggers.isEmpty())
		{
			addLog("Warning: skip {} because there are no triggers defined for it", modelIndexer);
			return;
		}

		final SqlESModelIndexerDataSource modelsToIndex = ESModelIndexerDataSources.newSqlESModelIndexerDataSource()
				.modelTableName(modelIndexer.getModelTableName())
				.triggers(triggers)
				.sqlWhereClause(p_WhereClause)
				.sqlOrderByClause(p_OrderByClause)
				.limit(p_Limit)
				.build();
		final IESIndexerResult result = modelIndexer.addToIndex(modelsToIndex);

		countAll += result.getTotalCount();
		countErrors += result.getFailuresCount();

		addLog("{} - Indexed: {}", modelIndexer.getId(), result.getSummary());
	}
}
