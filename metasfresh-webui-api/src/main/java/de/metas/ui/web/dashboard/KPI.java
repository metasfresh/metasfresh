package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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

public class KPI
{
	public static final Builder builder()
	{
		return new Builder();
	}

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

	private KPI(final Builder builder)
	{
		super();
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

	public KPIData retrieveData(final long fromMillis, final long toMillis)
	{
		//
		// Resolve esQuery's variables
		final Evaluatee evalCtx = createEvalCtx(fromMillis, toMillis);
		final String esQueryParsed = esQuery.evaluate(evalCtx, OnVariableNotFound.Preserve);

		//
		// Execute the query
		final SearchResponse response;
		try
		{
			response = elasticsearchClient.prepareSearch(esSearchIndex)
					.setTypes(esSearchTypes)
					.setSource(esQueryParsed)
					.get();
		}
		catch (final NoNodeAvailableException e)
		{
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
			final List<Aggregation> aggs = response.getAggregations().asList();
			if (aggs.size() != 1)
			{
				throw new AdempiereException("One and only one aggregation is allowed but we got " + aggs.size() + " for " + this
						+ "\nQuery: " + esQueryParsed
						+ "\nResponse: " + response);
			}
			final Aggregation agg = aggs.get(0);

			final KPIData.Builder data = KPIData.builder();
			if (agg instanceof MultiBucketsAggregation)
			{
				final String aggName = agg.getName();
				final MultiBucketsAggregation multiBucketsAggregation = (MultiBucketsAggregation)agg;

				for (final Bucket bucket : multiBucketsAggregation.getBuckets())
				{
					final String key = bucket.getKeyAsString();

					final ImmutableList.Builder<Object> values = ImmutableList.builder();
					for (final KPIField field : getFields())
					{
						final Object value = field.getValueExtractor().extractValue(aggName, bucket);
						final Object jsonValue = field.convertValueToJson(value);
						values.add(jsonValue == null ? Optional.empty() : jsonValue);
					}

					data.addValue(new KPIValue(key, values.build()));
				}
			}
			else
			{
				throw new IllegalStateException("Aggregation type not supported: " + agg.getClass());
			}

			return data.build();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed retrieving data for " + this
					+ "\nQuery: " + esQueryParsed
					+ "\nResponse: " + response, e);

		}
	}

	private Evaluatee createEvalCtx(long fromMillis, long toMillis)
	{
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

		return Evaluatees.mapBuilder()
				.put("FromMillis", fromMillis)
				.put("ToMillis", toMillis)
				.build()
				// Fallback to user context
				.andComposeWith(Evaluatees.ofCtx(Env.getCtx()));

	}

	public static final class Builder
	{
		private int id;
		private ITranslatableString caption;
		private ITranslatableString description = ImmutableTranslatableString.empty();
		private KPIChartType chartType;
		private Duration timeRange = Duration.ZERO;
		private List<KPIField> fields;

		private String esSearchTypes;
		private String esSearchIndex;
		private String esQuery;
		private Client elasticsearchClient;

		private Builder()
		{
			super();
		}

		public KPI build()
		{
			Check.assume(id > 0, "id > 0");
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
	}
}
