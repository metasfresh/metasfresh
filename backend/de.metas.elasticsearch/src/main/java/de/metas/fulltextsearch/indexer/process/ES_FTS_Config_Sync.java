/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.process;

import de.metas.elasticsearch.IESSystem;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSConfigSourceTablesMap;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEnqueueRequest;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEventType;
import de.metas.fulltextsearch.indexer.queue.ModelsToIndexQueueService;
import de.metas.fulltextsearch.indexer.queue.model_interceptor.EnqueueSourceModelInterceptor;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.TableName;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class ES_FTS_Config_Sync extends JavaProcess
{
	private final FTSConfigService ftsConfigService = SpringContextHolder.instance.getBean(FTSConfigService.class);
	private final ModelsToIndexQueueService modelsToIndexQueueService = SpringContextHolder.instance.getBean(ModelsToIndexQueueService.class);
	private final IESSystem elasticsearchSystem = Services.get(IESSystem.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = "ES_DeleteIndex")
	private boolean p_esDropIndex;

	@Override
	protected String doIt() throws Exception
	{
		final FTSConfigId ftsConfigId = FTSConfigId.ofRepoId(getRecord_ID());
		final FTSConfig ftsConfig = ftsConfigService.getConfigById(ftsConfigId);

		deleteESIndexIfRequired(ftsConfig);
		enqueueModels(ftsConfig);

		return MSG_OK;
	}

	public void deleteESIndexIfRequired(final FTSConfig config) throws IOException
	{
		if (p_esDropIndex)
		{
			final String esIndexName = config.getEsIndexName();

			final IndicesClient indicesClient = elasticsearchSystem.elasticsearchClient().indices();
			if (indicesClient.exists(new GetIndexRequest(esIndexName), RequestOptions.DEFAULT))
			{
				indicesClient.delete(new DeleteIndexRequest(esIndexName), RequestOptions.DEFAULT);
				addLog("Elasticsearch index dropped: {}", esIndexName);
			}
			else
			{
				addLog("Elasticsearch index missing, skip deleting it: {}", esIndexName);
			}

		}
	}

	private void enqueueModels(final FTSConfig ftsConfig)
	{
		final FTSConfigSourceTablesMap sourceTables = FTSConfigSourceTablesMap.ofList(ftsConfigService.getSourceTables().getByConfigId(ftsConfig.getId()));
		for (final TableName sourceTableName : sourceTables.getTableNames())
		{
			enqueueModels(sourceTableName, sourceTables);
		}
	}

	private void enqueueModels(
			@NonNull final TableName sourceTableName,
			@NonNull final FTSConfigSourceTablesMap sourceTablesMap)
	{
		final Stream<ModelToIndexEnqueueRequest> requestsStream = queryBL.createQueryBuilder(sourceTableName.getAsString())
				.addOnlyActiveRecordsFilter()
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterateAndStream()
				.flatMap(model -> extractRequests(model, sourceTablesMap).stream());

		GuavaCollectors.batchAndStream(requestsStream, 500)
				.forEach(requests -> {
					modelsToIndexQueueService.enqueueNow(requests);
					addLog("Enqueued {} records from {} table", requests.size(), sourceTableName);
				});
	}

	private List<ModelToIndexEnqueueRequest> extractRequests(
			@NonNull final Object model,
			@NonNull final FTSConfigSourceTablesMap sourceTables)
	{
		return EnqueueSourceModelInterceptor.extractRequests(
				model,
				ModelToIndexEventType.MODEL_CREATED_OR_UPDATED,
				sourceTables);
	}
}
