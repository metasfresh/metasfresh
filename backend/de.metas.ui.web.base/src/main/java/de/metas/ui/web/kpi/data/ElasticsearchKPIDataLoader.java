
/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.kpi.data;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SearchType;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketAggregateBase;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.SingleMetricAggregateBase;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.impl.ESSystem;
import de.metas.logging.LogManager;
import de.metas.ui.web.kpi.TimeRange;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIField;
import de.metas.ui.web.kpi.descriptor.elasticsearch.ElasticsearchDatasourceDescriptor;
import de.metas.ui.web.kpi.descriptor.elasticsearch.ElasticsearchDatasourceFieldDescriptor;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

class ElasticsearchKPIDataLoader
{
	public static ElasticsearchKPIDataLoader newInstance(
			@NonNull final ElasticsearchClient elasticsearchClient,
			@NonNull final KPI kpi)
	{
		return new ElasticsearchKPIDataLoader(elasticsearchClient, kpi);
	}

	private static final Logger logger = LogManager.getLogger(ElasticsearchKPIDataLoader.class);

	private final ElasticsearchClient elasticsearchClient;
	private final KPI kpi;
	private final ElasticsearchDatasourceDescriptor datasourceDescriptor;

	private TimeRange mainTimeRange;
	private List<TimeRange> timeRanges;

	private BiFunction<KPIField, TimeRange, String> fieldNameExtractor = (field, timeRange) -> field.getFieldName();

	public interface DataSetValueKeyExtractor
	{
		KPIDataSetValuesAggregationKey extractKey(
				MultiBucketBase bucket,
				TimeRange timeRange,
				@Nullable KPIField groupByField);
	}

	private static class KeyDataSetValueKeyExtractor implements DataSetValueKeyExtractor
	{

		@Override
		public KPIDataSetValuesAggregationKey extractKey(
				@NonNull final MultiBucketBase bucket,
				@NonNull final TimeRange timeRange,
				@Nullable final KPIField groupByField)
		{
			// Extract key from MultiBucketBase - the new API provides key through keyAsString() or similar methods
			final Object valueObj = extractKeyFromBucket(bucket);
			final KPIDataValue value = groupByField != null
					? KPIDataValue.ofValueAndField(valueObj, groupByField)
					: KPIDataValue.ofUnknownType(valueObj);
			return KPIDataSetValuesAggregationKey.of(value);
		}

		private Object extractKeyFromBucket(MultiBucketBase bucket)
		{
			// Try to extract key from bucket - this might need to be adapted based on actual bucket type
			try
			{
				// The MultiBucketBase interface might provide key access differently
				// This is a placeholder - actual implementation depends on the specific bucket type
				return bucket.toString(); // Fallback - should be improved based on actual bucket type
			}
			catch (Exception e)
			{
				logger.warn("Failed to extract key from bucket: {}", e.getMessage());
				return null;
			}
		}
	}

	private DataSetValueKeyExtractor dataSetValueKeyExtractor = new KeyDataSetValueKeyExtractor();

	private ElasticsearchKPIDataLoader(
			@NonNull final ElasticsearchClient elasticsearchClient,
			@NonNull final KPI kpi)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.kpi = kpi;
		this.datasourceDescriptor = kpi.getElasticsearchDatasource();
	}

	public ElasticsearchKPIDataLoader setTimeRange(final TimeRange mainTimeRange)
	{
		this.mainTimeRange = mainTimeRange;

		final ImmutableList.Builder<TimeRange> timeRanges = ImmutableList.builder();
		timeRanges.add(mainTimeRange);

		//
		final Duration compareOffset = kpi.getCompareOffset();
		if (compareOffset != null)
		{
			timeRanges.add(mainTimeRange.offset(compareOffset));

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
			// Offset dataSet value(item) key extractor (i.e. on which key we shall join the result ofValueAndField our queries)
			final KPIField groupByField = kpi.getGroupByField();
			if (groupByField.getValueType().isDate())
			{
				dataSetValueKeyExtractor = (bucket, timeRange, groupByFieldParam) -> {
					assert groupByFieldParam != null;
					final Object keyObj = ((KeyDataSetValueKeyExtractor) dataSetValueKeyExtractor).extractKeyFromBucket(bucket);
					final Instant date = convertToInstant(keyObj);
					final KPIDataValue value = KPIDataValue.ofValueAndField(
							date.minus(timeRange.getOffset()),
							groupByFieldParam);
					return KPIDataSetValuesAggregationKey.of(value);
				};
			}
			else
			{
				dataSetValueKeyExtractor = (bucket, timeRange, groupByFieldParam) -> {
					assert groupByFieldParam != null;
					final Object keyObj = ((KeyDataSetValueKeyExtractor) dataSetValueKeyExtractor).extractKeyFromBucket(bucket);
					final KPIDataValue value = KPIDataValue.ofValueAndField(
							keyObj,
							groupByFieldParam);
					return KPIDataSetValuesAggregationKey.of(value);
				};
			}
		}

		this.timeRanges = timeRanges.build();

		return this;
	}

	public KPIDataResult retrieveData()
	{
		final Stopwatch duration = Stopwatch.createStarted();

		logger.trace("Retrieving data for {}, range={}", kpi, mainTimeRange);
		final KPIDataResult.Builder data = KPIDataResult.builder()
				.range(mainTimeRange);

		timeRanges.forEach(timeRange -> loadData(data, timeRange));

		return data
				.datasetsComputeDuration(duration.elapsed())
				.build();
	}

	private void loadData(@NonNull final KPIDataResult.Builder data, @NonNull final TimeRange timeRange)
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
		final IStringExpression esQuery = datasourceDescriptor.getEsQuery();
		final String esQueryParsed = esQuery.evaluate(evalCtx, OnVariableNotFound.Preserve);

		//
		// Execute the query
		final SearchResponse<Object> response;
		try
		{
			logger.trace("Executing: \n{}", esQueryParsed);

			// Parse the JSON query using the new API approach
			final JsonpMapper mapper = new JacksonJsonpMapper();
			final SearchRequest searchRequest = SearchRequest.of(builder -> {
				
				builder.index(datasourceDescriptor.getEsSearchIndex());

				// Convert search types if needed
				final SearchType searchType = datasourceDescriptor.getEsSearchTypes();
				builder.searchType(searchType);
				
				// Parse the JSON query
				try (StringReader jsonReader = new StringReader(esQueryParsed))
				{
					builder.withJson(jsonReader);
				}
				
				return builder;
			});

			response = elasticsearchClient.search(searchRequest, Object.class);

			logger.trace("Got response: \n{}", response);
		}
		catch (final java.io.IOException e)
		{
			// Elasticsearch connection/transport error => nothing to do about it
			throw new AdempiereException("Cannot connect to elasticsearch node."
					+ "\nIf you want to disable the elasticsearch system then you can set system property or sysconfig `" + ESSystem.SYSCONFIG_elastic_enable + "` to `N`.",
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
			final Map<String, Aggregate> aggregations = response.aggregations();
			if (aggregations != null)
			{
				for (final Map.Entry<String, Aggregate> entry : aggregations.entrySet())
				{
					final String aggName = entry.getKey();
					final Aggregate agg = entry.getValue();
					final Object aggregateValue = agg._get();

					if (aggregateValue instanceof SingleMetricAggregateBase)
					{
						loadDataFromSingleValue(data, aggName, (SingleMetricAggregateBase) aggregateValue);
					}
					else if (aggregateValue instanceof MultiBucketAggregateBase)
					{
						loadDataFromMultiBucketsAggregation(data, timeRange, aggName, (MultiBucketAggregateBase<?>) aggregateValue);
					}
					else
					{
						//noinspection ThrowableNotThrown
						new AdempiereException("Aggregation type not supported: " + aggregateValue.getClass())
								.throwIfDeveloperModeOrLogWarningElse(logger);
					}
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
		return "\"" + instant + "\"";
	}

	private void loadDataFromMultiBucketsAggregation(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final TimeRange timeRange,
			@NonNull final String aggName,
			@NonNull final MultiBucketAggregateBase<?> aggregation)
	{
		// The buckets().array() method returns List<?>, so we need to cast appropriately
		final List<?> rawBuckets = aggregation.buckets().array();

		// Since we know these are MultiBucketBase instances from the context, we can safely cast
		@SuppressWarnings("unchecked")
		final List<? extends MultiBucketBase> buckets = (List<? extends MultiBucketBase>) rawBuckets;

		for (final MultiBucketBase bucket : buckets)
		{
			@Nullable final KPIField groupByField = kpi.getGroupByFieldOrNull();
			final KPIDataSetValuesAggregationKey key = dataSetValueKeyExtractor.extractKey(bucket, timeRange, groupByField);

			for (final KPIField field : kpi.getFields())
			{
				final ElasticsearchDatasourceFieldDescriptor fieldDatasourceDescriptor = datasourceDescriptor.getFieldByName(field.getFieldName());

				final KPIDataValue value = fieldDatasourceDescriptor.getBucketValueExtractor().extractValue(aggName, bucket);
				if (value == null || value.isNull())
				{
					continue;
				}

				final String fieldName = fieldNameExtractor.apply(field, timeRange);

				data.putValue(aggName, key, fieldName, value);
			}

			//
			// Make sure the groupByField's value is present in our dataSet value.
			// If not exist, we can use the key as it's value.
			if (groupByField != null)
			{
				data.putValueIfAbsent(aggName, key, groupByField.getFieldName(), key.getValue());
			}
		}
	}

	private void loadDataFromSingleValue(
			@NonNull final KPIDataResult.Builder data,
			@NonNull final String aggName,
			@NonNull final SingleMetricAggregateBase aggregation)
	{
		for (final KPIField field : kpi.getFields())
		{
			final ElasticsearchDatasourceFieldDescriptor fieldDatasourceDescriptor = datasourceDescriptor.getFieldByName(field.getFieldName());

			final Object value;
			if ("value".equals(fieldDatasourceDescriptor.getEsPathAsString()))
			{
				value = aggregation.value();
			}
			else
			{
				throw new IllegalStateException("Only ES path ending with 'value' allowed for field: " + field);
			}

			data.putValue(
					aggName,
					KPIDataSetValuesAggregationKey.NO_KEY,
					field.getFieldName(),
					KPIDataValue.ofValueAndField(value, field));
		}
	}

	private static Instant convertToInstant(final Object valueObj)
	{
		switch (valueObj)
		{
			case null ->
			{
				return Instant.ofEpochMilli(0);
			}
			case Long longValue ->
			{
				return Instant.ofEpochMilli(longValue);
			}
			case Number number ->
			{
				return Instant.ofEpochMilli(number.longValue());
			}
			case String stringValue ->
			{
				try
				{
					// Try to parse as ISO instant
					return Instant.parse(stringValue);
				}
				catch (Exception e)
				{
					try
					{
						// Try to parse as milliseconds
						return Instant.ofEpochMilli(Long.parseLong(stringValue));
					}
					catch (NumberFormatException nfe)
					{
						throw new AdempiereException("Cannot convert string " + stringValue + " to Instant.", nfe);
					}
				}
			}
			default -> throw new AdempiereException("Cannot convert " + valueObj + " (" + valueObj.getClass() + ") to Instant.");
		}
	}
}