/*
 * #%L
 * de.metas.elasticsearch.server
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.elasticsearch.IESSystem;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.config.FTSConfigId;
import de.metas.fulltextsearch.config.FTSConfigService;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexer;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexerRegistry;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
	private static final Logger logger = LogManager.getLogger(ModelToIndexEnqueueProcessor.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IESSystem elasticsearchSystem = Services.get(IESSystem.class);
	private final FTSModelIndexerRegistry indexersRegistry;
	private final FTSConfigService configService;
	private final ModelToIndexRepository queueRepository;

	private static final String SYSCONFIG_PollIntervalInSeconds = "fulltextsearch.indexer.queue.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	private static final int RETRIEVE_BATCH_SIZE = 500;

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
		final BooleanWithReason enabled = elasticsearchSystem.getEnabled();
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

	private void processInfinitely()
	{
		Duration pollIntervalOverride = null;
		while (true)
		{
			final Duration pollInterval = pollIntervalOverride != null ? pollIntervalOverride : getPollInterval();
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

			pollIntervalOverride = null;
			try
			{
				processNow();
			}
			catch (final IOException ex)
			{
				logger.warn("Elasticsearch communication error", ex);
				pollIntervalOverride = Duration.ofMinutes(1);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
			}
		}
	}

	private void processNow() throws Exception
	{
		final String processingTag = queueRepository.newProcessingTag();
		final ImmutableList<ModelToIndex> events = queueRepository.tagAndRetrieve(processingTag, RETRIEVE_BATCH_SIZE);

		boolean markRecords = false;
		AdIssueId adIssueId = null;
		try
		{
			for (final ConfigAndEvents configAndEvents : toConfigAndEvents(events))
			{
				processNow_ConfigAndEvents(configAndEvents);
			}

			markRecords = true;
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
			if (markRecords)
			{
				if (adIssueId == null)
				{
					queueRepository.markAsProcessed(processingTag);
				}
				else
				{
					queueRepository.markAsError(processingTag, adIssueId);
				}
			}
			else
			{
				queueRepository.untag(processingTag);
			}
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
			final String sourceTableName = event.getSourceModelRef().getTableName();

			for (final FTSConfig config : configService.getBySourceTableName(sourceTableName))
			{
				final ConfigAndEvents configAndEvents = configAndEventsMap.computeIfAbsent(
						config.getId(),
						configId -> new ConfigAndEvents(config));

				configAndEvents.addEvent(event);
			}
		}

		return configAndEventsMap.values();
	}

	private void processNow_ConfigAndEvents(final ConfigAndEvents configAndEvents) throws IOException
	{
		final ImmutableList<ModelToIndex> events = ImmutableList.copyOf(configAndEvents.getEvents());
		if (events.isEmpty())
		{
			return;
		}

		final FTSConfig config = configAndEvents.getConfig();

		final ImmutableSet<String> sourceTableNames = config.getSourceTableNames();
		final List<FTSModelIndexer> indexers = indexersRegistry.getBySourceTableNames(sourceTableNames);
		if (indexers.isEmpty())
		{
			logger.warn("No indexers found found for {}. Discard {} events", sourceTableNames, events.size());
			return;
		}

		final ImmutableList<ESDocumentToIndex> esDocumentsToIndex = indexers.stream()
				.flatMap(indexer -> indexer.createDocumentsToIndex(events, config).stream())
				.collect(ImmutableList.toImmutableList());

		if (esDocumentsToIndex.isEmpty())
		{
			logger.debug("No documents to index for {}. Discard {} events.", config, events.size());
			return;
		}

		if (!isESIndexExists(config))
		{
			createESIndex(config);
		}

		addDocumentsToIndex(config, esDocumentsToIndex);
	}

	private boolean isESIndexExists(final FTSConfig config) throws IOException
	{
		return elasticsearchSystem.elasticsearchClient()
				.indices()
				.exists(new GetIndexRequest(config.getEsIndexName()), RequestOptions.DEFAULT);
	}

	private void createESIndex(final FTSConfig config) throws IOException
	{
		final CreateIndexRequest esCreateRequest = new CreateIndexRequest(config.getEsIndexName())
				.source(config.getCreateIndexCommand().getAsString(), XContentType.JSON);

		elasticsearchSystem.elasticsearchClient()
				.indices()
				.create(esCreateRequest, RequestOptions.DEFAULT);
	}

	private void addDocumentsToIndex(
			@NonNull final FTSConfig config,
			@NonNull final List<ESDocumentToIndex> documentsToIndex) throws IOException
	{
		final BulkRequest bulkRequest = new BulkRequest();
		final String esIndexName = config.getEsIndexName();

		//
		// Documents to add
		documentsToIndex.stream()
				.filter(ESDocumentToIndex::isAddOrUpdate)
				.map(documentToIndex -> new IndexRequest(esIndexName)
						.id(documentToIndex.getDocumentId())
						.source(documentToIndex.getJson(), XContentType.JSON))
				.forEach(bulkRequest::add);

		//
		// Documents to remove
		documentsToIndex.stream()
				.filter(ESDocumentToIndex::isRemove)
				.map(documentToIndex -> new DeleteRequest(esIndexName)
						.id(documentToIndex.getDocumentId()))
				.forEach(bulkRequest::add);

		elasticsearchSystem.elasticsearchClient().bulk(bulkRequest, RequestOptions.DEFAULT);
	}

	@Value
	private static class ConfigAndEvents
	{
		FTSConfig config;
		ArrayList<ModelToIndex> events = new ArrayList<>();

		private ConfigAndEvents(@NonNull final FTSConfig config)
		{
			this.config = config;
		}

		public void addEvent(final ModelToIndex event)
		{
			if (!events.contains(event))
			{
				events.add(event);
			}
		}
	}
}
