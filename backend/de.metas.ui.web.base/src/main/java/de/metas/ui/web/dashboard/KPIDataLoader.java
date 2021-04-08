package de.metas.ui.web.dashboard;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.impl.ESSystem;
import de.metas.logging.LogManager;
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
import org.joda.time.DateTime;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiFunction;

public class KPIDataLoader
{
	public static KPIDataLoader newInstance(
			@NonNull final RestHighLevelClient elasticsearchClient,
			@NonNull final KPI kpi)
	{
		return new KPIDataLoader(elasticsearchClient, kpi);
	}

	private static final Logger logger = LogManager.getLogger(KPIDataLoader.class);

	private final RestHighLevelClient elasticsearchClient;
	private final KPI kpi;

	private TimeRange mainTimeRange;
	private List<TimeRange> timeRanges;

	private BiFunction<KPIField, TimeRange, String> fieldNameExtractor = (field, timeRange) -> field.getFieldName();

	public interface DataSetValueKeyExtractor
	{
		KPIDataSetValuesAggregationKey extractKey(
				MultiBucketsAggregation.Bucket bucket,
				TimeRange timeRange,
				@Nullable KPIField groupByField);
	}

	private static class KeyDataSetValueKeyExtractor implements DataSetValueKeyExtractor
	{

		@Override
		public KPIDataSetValuesAggregationKey extractKey(
				@NonNull final MultiBucketsAggregation.Bucket bucket,
				@NonNull final TimeRange timeRange,
				@Nullable final KPIField groupByField)
		{
			final Object valueObj = bucket.getKey();
			final KPIDataValue value = groupByField != null
					? KPIDataValue.ofValueAndField(valueObj, groupByField)
					: KPIDataValue.ofUnknownType(valueObj);
			return KPIDataSetValuesAggregationKey.of(value);
		}
	}

	private DataSetValueKeyExtractor dataSetValueKeyExtractor = new KeyDataSetValueKeyExtractor();

	private KPIDataLoader(
			@NonNull final RestHighLevelClient elasticsearchClient,
			@NonNull final KPI kpi)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.kpi = kpi;
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
					final Instant date = convertToInstant(bucket.getKey());
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
					final KPIDataValue value = KPIDataValue.ofValueAndField(
							bucket.getKey(),
							groupByFieldParam);
					return KPIDataSetValuesAggregationKey.of(value);
				};
			}
		}

		this.timeRanges = timeRanges.build();

		return this;
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
			@Nullable final KPIField groupByField = kpi.getGroupByFieldOrNull();
			final KPIDataSetValuesAggregationKey key = dataSetValueKeyExtractor.extractKey(bucket, timeRange, groupByField);

			for (final KPIField field : kpi.getFields())
			{
				final KPIDataValue value = field.getBucketValueExtractor().extractValue(aggName, bucket);
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
			@NonNull final NumericMetricsAggregation.SingleValue aggregation)
	{
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

			data.putValue(
					aggregation.getName(),
					KPIDataSetValuesAggregationKey.NO_KEY,
					field.getFieldName(),
					KPIDataValue.ofValueAndField(value, field));
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

	private static Instant convertToInstant(final Object valueObj)
	{
		if (valueObj == null)
		{
			return Instant.ofEpochMilli(0);
		}
		else if (valueObj instanceof org.joda.time.DateTime)
		{
			final long millis = ((DateTime)valueObj).getMillis();
			return Instant.ofEpochMilli(millis);
		}
		else if (valueObj instanceof Long)
		{
			return Instant.ofEpochMilli((Long)valueObj);
		}
		else if (valueObj instanceof Number)
		{
			return Instant.ofEpochMilli(((Number)valueObj).longValue());
		}
		else
		{
			throw new AdempiereException("Cannot convert " + valueObj + " to Instant.");
		}
	}
}
