package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation.Bucket;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class KPI
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(KPI.class);

	private final int id;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final KPIChartType chartType;
	private final Duration timeRange;
	private final List<KPIField> fields;

	private final String esSearchIndex;
	private final String esSearchTypes;
	private final IStringExpression esQuery;
	private final Client elasticsearchClient;
	
	private final int pollIntervalSec;

	private KPI(final Builder builder)
	{
		super();

		Check.assume(builder.id > 0, "id > 0");
		Check.assumeNotNull(builder.caption, "Parameter builder.caption is not null");
		Check.assumeNotNull(builder.description, "Parameter builder.description is not null");
		Check.assumeNotNull(builder.chartType, "Parameter builder.chartType is not null");
		Check.assumeNotEmpty(builder.fields, "builder.fields is not empty");
		Check.assumeNotEmpty(builder.esSearchIndex, "builder.esSearchIndex is not empty");
		Check.assumeNotEmpty(builder.esSearchTypes, "builder.esSearchTypes is not empty");
		Check.assumeNotEmpty(builder.esQuery, "builder.esQuery is not empty");
		Check.assumeNotNull(builder.elasticsearchClient, "Parameter builder.elasticsearchClient is not null");

		id = builder.id;

		caption = builder.caption;
		description = builder.description;
		chartType = builder.chartType;
		timeRange = builder.timeRange;

		fields = ImmutableList.copyOf(builder.fields);

		esSearchIndex = builder.esSearchIndex;
		esSearchTypes = builder.esSearchTypes;
		esQuery = StringExpressionCompiler.instance.compile(builder.esQuery);
		elasticsearchClient = builder.elasticsearchClient;
		
		pollIntervalSec = builder.pollIntervalSec;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("caption", caption.getDefaultValue())
				.toString();
	}

	public int getId()
	{
		return id;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public KPIChartType getChartType()
	{
		return chartType;
	}

	public List<KPIField> getFields()
	{
		return fields;
	}
	
	public int getPollIntervalSec()
	{
		return pollIntervalSec;
	}

	public KPIDataResult retrieveData(long fromMillis, long toMillis)
	{
		final Stopwatch duration = Stopwatch.createStarted();
		
		//
		// Create query evaluation context
		if (toMillis <= 0)
		{
			toMillis = SystemTime.millis();
		}

		if (fromMillis <= 0)
		{
			if (timeRange.isZero())
			{
				fromMillis = 0;
			}
			else
			{
				fromMillis = toMillis - timeRange.abs().toMillis();
			}
		}

		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put("FromMillis", fromMillis)
				.put("ToMillis", toMillis)
				.build()
				// Fallback to user context
				.andComposeWith(Evaluatees.ofCtx(Env.getCtx()));

		//
		// Resolve esQuery's variables
		final String esQueryParsed = esQuery.evaluate(evalCtx, OnVariableNotFound.Preserve);

		// TODO: remove it, For debugging
		// if(true)
		// {
		// try
		// {
		// esQueryParsed = Files.toString(new File("c:\\tmp\\es_query.json"), Charset.forName("UTF-8"));
		// }
		// catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		//
		// Execute the query
		final SearchResponse response;
		try
		{
			logger.trace("Executing: \n{}", esQueryParsed);
			
			response = elasticsearchClient.prepareSearch(esSearchIndex)
					.setTypes(esSearchTypes)
					.setSource(esQueryParsed)
					// .setExplain(true) // enable it only for debugging
					.get();
			
			logger.info("Got response: \n{}", response);
		}
		catch (final NoNodeAvailableException e)
		{
			// elastic search transport error => nothing to do about it
			throw e;
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

			final KPIDataResult.Builder data = KPIDataResult.builder()
					.setTimeRange(fromMillis, toMillis);

			for (final Aggregation agg : aggregations)
			{
				if (agg instanceof MultiBucketsAggregation)
				{
					final String aggName = agg.getName();
					final MultiBucketsAggregation multiBucketsAggregation = (MultiBucketsAggregation)agg;

					final ImmutableList.Builder<KPIDataSetValue> values = ImmutableList.builder();
					for (final Bucket bucket : multiBucketsAggregation.getBuckets())
					{
						final KPIDataSetValue.Builder dataSetValue = KPIDataSetValue.builder();
						for (final KPIField field : getFields())
						{
							final Object value = field.getBucketValueExtractor().extractValue(aggName, bucket);
							final Object jsonValue = field.convertValueToJson(value);
							if (jsonValue == null)
							{
								continue;
							}

							dataSetValue.add(field.getFieldName(), jsonValue);
						}

						values.add(dataSetValue.build());
					}

					data.addDataSet(new KPIDataSet(aggName, values.build()));
				}
				else if (agg instanceof NumericMetricsAggregation.SingleValue)
				{
					final NumericMetricsAggregation.SingleValue singleValueAggregation = (NumericMetricsAggregation.SingleValue)agg;

					final String key = null; // N/A

					final KPIDataSetValue.Builder dataSetValue = KPIDataSetValue.builder();
					for (final KPIField field : getFields())
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

						final Object jsonValue = field.convertValueToJson(value);
						dataSetValue.add(field.getFieldName(), jsonValue);
					}

					data.addDataSet(new KPIDataSet(key, ImmutableList.of(dataSetValue.build())));
				}
				else
				{
					new AdempiereException("Aggregation type not supported: " + agg.getClass())
							.throwIfDeveloperModeOrLogWarningElse(logger);
				}
			}

			return data
					.setTook(duration.stop())
					.build();
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage()
					+ "\n KPI: " + this
					+ "\n Query: " + esQueryParsed
					+ "\n Response: " + response, e);

		}
	}

	public static final class Builder
	{
		private int id;
		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private ITranslatableString description = ImmutableTranslatableString.empty();
		private KPIChartType chartType;
		private Duration timeRange = Duration.ZERO;
		private List<KPIField> fields;

		private String esSearchTypes;
		private String esSearchIndex;
		private String esQuery;
		private Client elasticsearchClient;
		private int pollIntervalSec;

		private Builder()
		{
			super();
		}

		public KPI build()
		{
			return new KPI(this);
		}

		public Builder setId(final int id)
		{
			this.id = id;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setChartType(final KPIChartType chartType)
		{
			this.chartType = chartType;
			return this;
		}

		public Builder setFields(final List<KPIField> fields)
		{
			this.fields = fields;
			return this;
		}

		public Builder setESSearchIndex(final String esSearchIndex)
		{
			this.esSearchIndex = esSearchIndex;
			return this;
		}

		public Builder setESSearchTypes(final String esSearchTypes)
		{
			this.esSearchTypes = esSearchTypes;
			return this;
		}

		public Builder setESQuery(final String query)
		{
			esQuery = query;
			return this;
		}

		public Builder setTimeRange(final Duration timeRange)
		{
			this.timeRange = timeRange == null ? Duration.ZERO : timeRange;
			return this;
		}

		public Builder setElasticsearchClient(final Client elasticsearchClient)
		{
			this.elasticsearchClient = elasticsearchClient;
			return this;
		}

		public Builder setPollIntervalSec(final int pollIntervalSec)
		{
			this.pollIntervalSec = pollIntervalSec;
			return this;
		}
	}
}
