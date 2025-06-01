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

package de.metas.fulltextsearch.indexer.queue;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.ESDocumentToIndexChunk;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.config.FTSConfigSourceTable;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexer;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexerRegistry;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Profile(Profiles.PROFILE_App)
public class ModelToIndexEnqueueProcessor
{
	private final Logger logger = LogManager.getLogger(getClass());
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final FTSModelIndexerRegistry indexersRegistry;
	private final FTSConfigService configService;
	private final ModelToIndexRepository queueRepository;

	private static final String SYSCONFIG_PollIntervalInSeconds = "fulltextsearch.indexer.queue.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	private static final String SYSCONFIG_RetrieveBatchSize = "de.metas.fulltextsearch.indexer.queue.ModelToIndexEnqueueProcessor.retrieveBatchSize";
	private static final int DEFAULT_RetrieveBatchSize = 1000;

	public ModelToIndexEnqueueProcessor(
			@NonNull final FTSModelIndexerRegistry indexersRegistry,
			@NonNull final FTSConfigService configService,
			@NonNull final ModelToIndexRepository queueRepository)
	{
		this.indexersRegistry = indexersRegistry;
		this.configService = configService;
		this.queueRepository = queueRepository;
	}

	@PostConstruct
	public void postConstruct()
	{
		final BooleanWithReason enabled = configService.getEnabled();
		if (enabled.isFalse())
		{
			logger.warn("Elasticsearch is disabled because: {}", enabled.getReasonAsString());
			return;
		}

		final Thread thread = new Thread(this::processInfinitely);
		thread.setDaemon(true);
		thread.setName("FTS-index-queue-processor");
		thread.start();
		logger.info("Started {}", thread);
	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}

	private int getRetrieveBatchSize()
	{
		final int batchSize = sysConfigBL.getIntValue(SYSCONFIG_RetrieveBatchSize, DEFAULT_RetrieveBatchSize);
		return batchSize > 0 ? batchSize : DEFAULT_RetrieveBatchSize;
	}

	private void processInfinitely()
	{
		Duration pollInterval = getPollInterval();
		while (true)
		{
			if (pollInterval.toMillis() > 0)
			{
				logger.debug("Sleeping {}", pollInterval);
				try
				{
					Thread.sleep(getPollInterval().toMillis());
				}
				catch (final InterruptedException ex)
				{
					logger.info("Got interrupt request. Exiting.");
					return;
				}
			}

			try
			{
				final boolean mightHaveMore = processNow();
				pollInterval = mightHaveMore ? Duration.ZERO : getPollInterval();
			}
			catch (final IOException ex)
			{
				logger.warn("Elasticsearch communication error", ex);
				pollInterval = Duration.ofMinutes(1);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
				pollInterval = getPollInterval();
			}
		}
	}

	/**
	 * @return true if there was at last one record successfully processed
	 */
	private boolean processNow() throws Exception
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String processingTag = queueRepository.newProcessingTag();
		final int batchSize = getRetrieveBatchSize();
		final ImmutableList<ModelToIndex> events = queueRepository.tagAndRetrieve(processingTag, batchSize);
		if (events.isEmpty())
		{
			return false;
		}

		boolean markRecords = false;
		AdIssueId adIssueId = null;
		try
		{
			for (final ConfigAndEvents configAndEvents : toConfigAndEvents(events))
			{
				processNow_ConfigAndEvents(configAndEvents);
			}

			markRecords = true;

			return true;
		}
		catch (final IOException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			markRecords = true;
			final AdempiereException metasfreshEx = AdempiereException.wrapIfNeeded(ex);
			adIssueId = errorManager.createIssue(metasfreshEx);
			throw metasfreshEx;
		}
		finally
		{
			final String processedResolution;
			if (markRecords)
			{
				if (adIssueId == null)
				{
					queueRepository.markAsProcessed(processingTag);
					processedResolution = "SUCCESS";
				}
				else
				{
					queueRepository.markAsError(processingTag, adIssueId);
					processedResolution = "ERROR";
				}
			}
			else
			{
				queueRepository.untag(processingTag);
				processedResolution = "TEMPORARY ERROR";
			}

			logger.info("{} - Processed {} events in {} (processing tag: {})", processedResolution, events.size(), stopwatch.stop(), processingTag);
		}

	}

	private Collection<ConfigAndEvents> toConfigAndEvents(final ImmutableList<ModelToIndex> events)
	{
		if (events.isEmpty())
		{
			return ImmutableList.of();
		}

		final LinkedHashMap<FTSConfigId, ConfigAndEvents> configAndEventsMap = new LinkedHashMap<>();

		for (final ModelToIndex event : events)
		{
			configAndEventsMap.computeIfAbsent(event.getFtsConfigId(), this::newConfigAndEvents)
					.addEvent(event);
		}

		return configAndEventsMap.values();
	}

	private ConfigAndEvents newConfigAndEvents(@NonNull final FTSConfigId ftsConfigId)
	{
		final FTSConfig config = configService.getConfigById(ftsConfigId);

		final ImmutableSet<TableName> sourceTableNames = configService.getSourceTables().getByConfigId(ftsConfigId)
				.stream()
				.map(FTSConfigSourceTable::getTableName)
				.collect(ImmutableSet.toImmutableSet());

		return new ConfigAndEvents(config, sourceTableNames);
	}

	private void processNow_ConfigAndEvents(final ConfigAndEvents configAndEvents) throws IOException
	{
		final ImmutableList<ModelToIndex> events = configAndEvents.getEvents();
		if (events.isEmpty())
		{
			return;
		}

		final FTSConfig config = configAndEvents.getConfig();

		final List<FTSModelIndexer> indexers = getModelIndexers(configAndEvents);
		if (indexers.isEmpty())
		{
			logger.warn("No indexers found found for {}. Discard {} events", config, events.size());
			return;
		}

		final ImmutableList<ESDocumentToIndexChunk> chunks = indexers.stream()
				.flatMap(indexer -> indexer.createDocumentsToIndex(events, config).stream())
				.collect(ImmutableList.toImmutableList());

		if (chunks.isEmpty())
		{
			logger.debug("No documents to index for {}. Discard {} events.", config, events.size());
			return;
		}

		if (!isESIndexExists(config))
		{
			createESIndex(config);
		}

		addDocumentsToIndex(config, chunks);
	}

	private List<FTSModelIndexer> getModelIndexers(final ConfigAndEvents configAndEvents)
	{
		return indexersRegistry.getBySourceTableNames(configAndEvents.getSourceTableNames());
	}

	private boolean isESIndexExists(final FTSConfig config) throws IOException
	{
		return configService.elasticsearchClient()
				.indices()
				.exists(ExistsRequest.of(e -> e.index(config.getEsIndexName())))
				.value();
	}

	private void createESIndex(final FTSConfig config) throws IOException
	{
		// Parse the JSON settings and pass as InputStream
		final String indexSettingsJson = config.getCreateIndexCommand().getAsString();
		final java.io.ByteArrayInputStream settingsStream = new java.io.ByteArrayInputStream(indexSettingsJson.getBytes());

		configService.elasticsearchClient()
				.indices()
				.create(CreateIndexRequest.of(c -> c
						.index(config.getEsIndexName())
						.withJson(settingsStream)));
	}

	private void addDocumentsToIndex(
			@NonNull final FTSConfig config,
			@NonNull final List<ESDocumentToIndexChunk> chunks) throws IOException
	{
		for (final ESDocumentToIndexChunk chunk : chunks)
		{
			addDocumentsToIndex(config, chunk);
		}
	}

	private void addDocumentsToIndex(
			@NonNull final FTSConfig config,
			@NonNull final ESDocumentToIndexChunk chunk) throws IOException
	{
		final ElasticsearchClient elasticsearchClient = configService.elasticsearchClient();
		final String esIndexName = config.getEsIndexName();

		final List<BulkOperation> bulkOperations = new ArrayList<>();

		// Add delete operations
		for (final String documentIdToDelete : chunk.getDocumentIdsToDelete())
		{
			bulkOperations.add(BulkOperation.of(b -> b
					.delete(DeleteOperation.of(d -> d
							.index(esIndexName)
							.id(documentIdToDelete)))));
		}

		// Add index operations
		final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		for (final ESDocumentToIndex documentToIndex : chunk.getDocumentsToIndex())
		{
			final JsonNode documentJson = objectMapper.readTree(documentToIndex.getJson());
			bulkOperations.add(BulkOperation.of(b -> b
					.index(IndexOperation.of(i -> i
							.index(esIndexName)
							.id(documentToIndex.getDocumentId())
							.document(documentJson)))));
		}

		if (!bulkOperations.isEmpty())
		{
			final BulkRequest bulkRequest = BulkRequest.of(b -> b.operations(bulkOperations));
			final BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest);
			
			if (bulkResponse.errors())
			{
				logger.warn("Some bulk operations failed. Check individual item responses.");
			}
		}
	}

	@ToString
	private static class ConfigAndEvents
	{
		@Getter
		private final FTSConfig config;

		@Getter
		private final ImmutableSet<TableName> sourceTableNames;

		private final ArrayList<ModelToIndex> events = new ArrayList<>();

		private ConfigAndEvents(
				@NonNull final FTSConfig config,
				@NonNull final ImmutableSet<TableName> sourceTableNames)
		{
			this.config = config;
			this.sourceTableNames = sourceTableNames;
		}

		public void addEvent(final ModelToIndex event)
		{
			if (!events.contains(event))
			{
				events.add(event);
			}
		}

		public ImmutableList<ModelToIndex> getEvents()
		{
			return ImmutableList.copyOf(events);
		}
	}
}
