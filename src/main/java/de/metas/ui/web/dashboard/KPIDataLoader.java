package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation.Bucket;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.impl.ESSystem;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;

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
	public static final KPIDataLoader newInstance(
			@NonNull final Client elasticsearchClient,
			@NonNull final KPI kpi,
			@NonNull final JSONOptions jsonOptions)
	{
		return new KPIDataLoader(elasticsearchClient, kpi, jsonOptions);
	}

	private static final Logger logger = LogManager.getLogger(KPIDataLoader.class);

	private final Client elasticsearchClient;
	private final KPI kpi;
	private final JSONOptions jsonOptions;

	private TimeRange mainTimeRange;
	private List<TimeRange> timeRanges;

	private boolean formatValues = false;

	private BiFunction<KPIField, TimeRange, String> fieldNameExtractor = (field, timeRange) -> field.getFieldName();
	private BiFunction<Bucket, TimeRange, Object> dataSetValueKeyExtractor = (bucket, timeRange) -> bucket.getKey();

	private KPIDataLoader(
			@NonNull final Client elasticsearchClient,
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

	/**
	 * Checks if KPI's elasticsearch Index and Type exists
	 */
	public KPIDataLoader assertESTypesExists()
	{
		final IndicesAdminClient admin = elasticsearchClient.admin()
				.indices();

		//
		// Check index exists
		final String esSearchIndex = kpi.getESSearchIndex();
		final GetIndexResponse indexResponse = admin.prepareGetIndex()
				.addIndices(esSearchIndex)
				.get();
		final List<String> indexesFound = Arrays.asList(indexResponse.getIndices());
		if (!indexesFound.contains(esSearchIndex))
		{
			throw new AdempiereException("ES index '" + esSearchIndex + "' not found in " + indexesFound);
		}
		logger.debug("Indexes found: {}", indexesFound);

		//
		// Check type exists
		final String esTypes = kpi.getESSearchTypes();
		final boolean esTypesExists = admin.prepareTypesExists(esSearchIndex)
				.setTypes(kpi.getESSearchTypes())
				.get()
				.isExists();
		if (!esTypesExists)
		{
			throw new AdempiereException("Elasticseatch types " + esTypes + " does not exist");
		}

		// All good
		return this;
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
				.put("MainFromMillis", data.getRange().getFromMillis())
				.put("MainToMillis", data.getRange().getToMillis())
				.put("FromMillis", timeRange.getFromMillis())
				.put("ToMillis", timeRange.getToMillis())
				.build()
				// Fallback to user context
				.andComposeWith(Evaluatees.ofCtx(Env.getCtx()));

		//
		// Resolve esQuery's variables
		final IStringExpression esQuery = kpi.getESQuery();
		final String esQueryParsed = esQuery.evaluate(evalCtx, OnVariableNotFound.Preserve);

		//
		// Execute the query
		final SearchResponse response;
		try
		{
			logger.trace("Executing: \n{}", esQueryParsed);

			response = elasticsearchClient.prepareSearch(kpi.getESSearchIndex())
					.setTypes(kpi.getESSearchTypes())
					.setSource(esQueryParsed)
					// .setExplain(true) // enable it only for debugging
					.get();

			logger.trace("Got response: \n{}", response);
		}
		catch (final NoNodeAvailableException e)
		{
			// elastic search transport error => nothing to do about it
			throw new AdempiereException("" + e.getLocalizedMessage() + "."
					+ "\nIf you want to disable the elasticsearch system then you can set `" + ESSystem.SYSCONFIG_PostKpiEvents + "` to `N`.", e);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed executing query for " + this + ": " + e.getLocalizedMessage()
					+ "\nQuery: " + esQueryParsed, e);
		}

		//
		// Fetch data
		try
		{
			final List<Aggregation> aggregations = response.getAggregations().asList();

			for (final Aggregation agg : aggregations)
			{
				if (agg instanceof MultiBucketsAggregation)
				{
					final String aggName = agg.getName();
					final MultiBucketsAggregation multiBucketsAggregation = (MultiBucketsAggregation)agg;

					for (final Bucket bucket : multiBucketsAggregation.getBuckets())
					{
						final Object key = dataSetValueKeyExtractor.apply(bucket, timeRange);

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
				else if (agg instanceof NumericMetricsAggregation.SingleValue)
				{
					final NumericMetricsAggregation.SingleValue singleValueAggregation = (NumericMetricsAggregation.SingleValue)agg;

					final String key = "NO_KEY"; // N/A

					for (final KPIField field : kpi.getFields())
					{
						final Object value;
						if ("value".equals(field.getESPathAsString()))
						{
							value = singleValueAggregation.value();
						}
						else
						{
							throw new IllegalStateException("Only ES path ending with 'value' allowed for field: " + field);
						}

						final Object jsonValue = field.convertValueToJson(value, jsonOptions);
						data.putValue(agg.getName(), key, field.getFieldName(), jsonValue);
					}
				}
				else
				{
					new AdempiereException("Aggregation type not supported: " + agg.getClass())
							.throwIfDeveloperModeOrLogWarningElse(logger);
				}
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage()
					+ "\n KPI: " + this
					+ "\n Query: " + esQueryParsed
					+ "\n Response: " + response, e);

		}
	}

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

	private static final long convertToMillis(final Object valueObj)
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
			return ((Long)valueObj).longValue();
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
