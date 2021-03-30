package de.metas.ui.web.dashboard;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.impl.ESSystem;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.LoggingDeprecationHandler;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class KPIDataLoader
{
	public static KPIDataLoader newInstance(
			@NonNull final RestHighLevelClient elasticsearchClient,
			@NonNull final KPI kpi,
			@NonNull final JSONOptions jsonOptions)
	{
		return new KPIDataLoader(elasticsearchClient, kpi, jsonOptions);
	}

	private static final Logger logger = LogManager.getLogger(KPIDataLoader.class);

	private final RestHighLevelClient elasticsearchClient;
	private final KPI kpi;
	private final JSONOptions jsonOptions;

	private TimeRange mainTimeRange;
	private List<TimeRange> timeRanges;

	private boolean formatValues = false;

	private BiFunction<KPIField, TimeRange, String> fieldNameExtractor = (field, timeRange) -> field.getFieldName();

	public interface DataSetValueKeyExtractor
	{
		Object extractKey(MultiBucketsAggregation.Bucket bucket, TimeRange timeRange);
	}

	private static class KeyDataSetValueKeyExtractor implements DataSetValueKeyExtractor
	{

		@Override
		public Object extractKey(@NonNull final MultiBucketsAggregation.Bucket bucket, @NonNull final TimeRange timeRange)
		{
			return bucket.getKey();
		}
	}

	private DataSetValueKeyExtractor dataSetValueKeyExtractor = new KeyDataSetValueKeyExtractor();

	private KPIDataLoader(
			@NonNull final RestHighLevelClient elasticsearchClient,
			@NonNull final KPI kpi,
			@NonNull final JSONOptions jsonOptions)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.kpi = kpi;
		this.jsonOptions = jsonOptions;
	}

	public KPIDataLoader setTimeRange(final TimeRange mainTimeRange)
	{
		this.mainTimeRange = mainTimeRange;

		final ImmutableList.Builder<TimeRange> timeRanges = ImmutableList.builder();
		timeRanges.add(mainTimeRange);

		//
		final Duration compareOffset = kpi.getCompareOffset();
		if (compareOffset != null)
		{
			timeRanges.add(TimeRange.offset(mainTimeRange, compareOffset));

			//
			// Offset fieldName extractor
			fieldNameExtractor = (field, timeRange) -> {
				if (timeRange.isMainTimeRange())
				{
					return field.getFieldName();
				}
				else if (field.isGroupBy())
				{
					return "_" + field.getOffsetFieldName();
				}
				else
				{
					return field.getOffsetFieldName();
				}
			};

			//
			// Offset dataSet value(item) key extractor (i.e. on which key we shall join the result of our queries)
			final KPIField groupByField = kpi.getGroupByField();
			if (groupByField.getValueType().isDate())
			{
				dataSetValueKeyExtractor = (bucket, timeRange) -> {
					final long millis = convertToMillis(bucket.getKey());
					return formatValue(groupByField, timeRange.subtractOffset(millis));
				};
			}
			else
			{
				dataSetValueKeyExtractor = (bucket, timeRange) -> formatValue(groupByField, bucket.getKey());
			}
		}

		this.timeRanges = timeRanges.build();

		return this;
	}

	/**
	 * @param formatValues true if the loader shall format the values and make them user friendly
	 */
	public KPIDataLoader setFormatValues(final boolean formatValues)
	{
		this.formatValues = formatValues;
		return this;
	}

	private boolean isFormatValues()
	{
		return formatValues;
	}

	public KPIDataLoader assertESIndexExists()
	{
		final String esSearchIndex = kpi.getEsSearchIndex();
		final GetIndexRequest request = new GetIndexRequest(esSearchIndex);
		try
		{
			final boolean indexExists = elasticsearchClient.indices().exists(request, RequestOptions.DEFAULT);
			if (!indexExists)
			{
				throw new AdempiereException("ES index '" + esSearchIndex + "' not found");
			}

			return this;
		}
		catch (final java.io.IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	public KPIDataResult retrieveData()
	{
		final Stopwatch duration = Stopwatch.createStarted();

		logger.trace("Retrieving data for {}, range={}", kpi, mainTimeRange);
		final KPIDataResult.Builder data = KPIDataResult.builder()
				.setRange(mainTimeRange);

		timeRanges.forEach(timeRange -> loadData(data, timeRange));

		return data
				.setTook(duration.stop())
				.build();
	}

	private void loadData(final KPIDataResult.Builder data, final TimeRange timeRange)
	{
		logger.trace("Loading data for {}", timeRange);

		//
		// Create query evaluation context
		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put("MainFromMillis", toESQueryString(data.getRange().getFrom()))
				.put("MainToMillis", toESQueryString(data.getRange().getTo()))
				.put("FromMillis", toESQueryString(timeRange.getFrom()))
				.put("ToMillis", toESQueryString(timeRange.getTo()))
				.build()
				// Fallback to user context
				.andComposeWith(Evaluatees.ofCtx(Env.getCtx()));

		//
		// Resolve esQuery's variables
		final IStringExpression esQuery = kpi.getEsQuery();
		final String esQueryParsed = esQuery.evaluate(evalCtx, OnVariableNotFound.Preserve);

		//
		// Execute the query
		final SearchResponse response;
		try
		{
			logger.trace("Executing: \n{}", esQueryParsed);

			final SearchRequest searchRequest = new SearchRequest()
					.indices(kpi.getEsSearchIndex())
					.searchType(kpi.getEsSearchTypes())
					.source(toSearchSourceBuilder(esQueryParsed));

			response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

			logger.trace("Got response: \n{}", response);
		}
		catch (final NoNodeAvailableException e)
		{
			// elastic search transport error => nothing to do about it
			throw new AdempiereException("Cannot connect to elasticsearch node."
					+ "\nIf you want to disable the elasticsearch system then you can set sysconfig `" + ESSystem.SYSCONFIG_PostKpiEvents + "` to `N`.",
					e);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed executing query for " + this + ": " + e.getLocalizedMessage()
					+ "\n KPI: " + kpi
					+ "\n Query: " + esQueryParsed,
					e);
		}

		//
		// Fetch data
		try
		{
			final List<Aggregation> aggregations = response.getAggregations().asList();

			for (final Aggregation agg : aggregations)
			{
				if (agg instanceof NumericMetricsAggregation.SingleValue)
				{
					loadDataFromSingleValue(data, (NumericMetricsAggregation.SingleValue)agg);
				}
				else if (agg instanceof MultiBucketsAggregation)
				{
					loadDataFromMultiBucketsAggregation(data, timeRange, (MultiBucketsAggregation)agg);
				}
				else
				{
					//noinspection ThrowableNotThrown
					new AdempiereException("Aggregation type not supported: " + agg.getClass())
							.throwIfDeveloperModeOrLogWarningElse(logger);
				}
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed fetching data from elasticsearch response"
					+ "\n KPI: " + kpi
					+ "\n Query: " + esQueryParsed
					+ "\n Response: " + response,
					e);

		}
	}

	private static String toESQueryString(@NonNull final Instant instant)
	{
		return "\"" + instant.toString() + "\"";
	}

	private void loadDataFromMultiBucketsAggregation(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final TimeRange timeRange,
			@NonNull final MultiBucketsAggregation aggregation)
	{
		final String aggName = aggregation.getName();
		for (final MultiBucketsAggregation.Bucket bucket : aggregation.getBuckets())
		{
			final Object key = dataSetValueKeyExtractor.extractKey(bucket, timeRange);

			for (final KPIField field : kpi.getFields())
			{
				final Object value = field.getBucketValueExtractor().extractValue(aggName, bucket);
				final Object jsonValue = formatValue(field, value);
				if (jsonValue == null)
				{
					continue;
				}

				final String fieldName = fieldNameExtractor.apply(field, timeRange);

				data.putValue(aggName, key, fieldName, jsonValue);
			}

			//
			// Make sure the groupByField's value is present in our dataSet value.
			// If not exist, we can use the key as it's value.
			final KPIField groupByField = kpi.getGroupByFieldOrNull();
			if (groupByField != null)
			{
				data.putValueIfAbsent(aggName, key, groupByField.getFieldName(), key);
			}
		}
	}

	private void loadDataFromSingleValue(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final NumericMetricsAggregation.SingleValue aggregation)
	{
		final String key = "NO_KEY"; // N/A

		for (final KPIField field : kpi.getFields())
		{
			final Object value;
			if ("value".equals(field.getESPathAsString()))
			{
				value = aggregation.value();
			}
			else
			{
				throw new IllegalStateException("Only ES path ending with 'value' allowed for field: " + field);
			}

			final Object jsonValue = field.convertValueToJson(value, jsonOptions);
			data.putValue(aggregation.getName(), key, field.getFieldName(), jsonValue);
		}
	}

	@NonNull
	private static SearchSourceBuilder toSearchSourceBuilder(final String json) throws IOException
	{
		final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		final SearchModule searchModule = new SearchModule(Settings.EMPTY, false, ImmutableList.of());
		try (final XContentParser parser = XContentFactory.xContent(XContentType.JSON)
				.createParser(
						new NamedXContentRegistry(searchModule.getNamedXContents()),
						LoggingDeprecationHandler.INSTANCE,
						json))
		{
			searchSourceBuilder.parseXContent(parser);
		}
		return searchSourceBuilder;
	}

	@Nullable
	private Object formatValue(final KPIField field, final Object value)
	{
		if (isFormatValues())
		{
			return field.convertValueToJsonUserFriendly(value, jsonOptions);
		}
		else
		{
			return field.convertValueToJson(value, jsonOptions);
		}
	}

	private static long convertToMillis(final Object valueObj)
	{
		if (valueObj == null)
		{
			return 0;
		}
		else if (valueObj instanceof org.joda.time.DateTime)
		{
			return ((org.joda.time.DateTime)valueObj).getMillis();
		}
		else if (valueObj instanceof Long)
		{
			return (Long)valueObj;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).longValue();
		}
		else
		{
			throw new AdempiereException("Cannot convert " + valueObj + " to millis.");
		}
	}
}
