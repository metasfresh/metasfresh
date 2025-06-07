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

package de.metas.ui.web.kpi.descriptor.elasticsearch;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketAggregateBase;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.SingleBucketAggregateBase;
import co.elastic.clients.elasticsearch._types.aggregations.SingleMetricAggregateBase;
import com.google.common.base.Splitter;
import de.metas.ui.web.kpi.data.KPIDataValue;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Map;

@Value
public class ElasticsearchDatasourceFieldDescriptor
{
	@FunctionalInterface
	public interface BucketValueExtractor
	{
		KPIDataValue extractValue(String containingAggName, MultiBucketBase bucket);
	}

	@NonNull String fieldName;
	@NonNull String esPathAsString;
	List<String> esPath;
	@NonNull BucketValueExtractor bucketValueExtractor;

	private static final Splitter PATH_SPLITTER = Splitter.on('.').trimResults();

	@Builder
	private ElasticsearchDatasourceFieldDescriptor(
			@NonNull final String fieldName,
			@NonNull final KPIFieldValueType valueType,
			@NonNull final String esPath)
	{
		this.fieldName = fieldName;
		this.esPathAsString = esPath.trim();
		this.esPath = PATH_SPLITTER.splitToList(this.esPathAsString);
		bucketValueExtractor = createBucketValueExtractor(this.esPath, valueType);
	}

	private static BucketValueExtractor createBucketValueExtractor(
			@NonNull final List<String> path, 
			@NonNull final KPIFieldValueType valueType)
	{
		if (path.size() == 1)
		{
			final String fieldName = path.getFirst();
			if ("doc_count".equals(fieldName))
			{
				return (containingAggName, bucket) -> KPIDataValue.ofValueAndType(bucket.docCount(), valueType);
			}
			else if ("key".equals(fieldName))
			{
				return (containingAggName, bucket) -> {
					// TODO clarify with Teo if this is OK
					// final JsonData key = bucket.key();
					// final String keyString = key.isString() ? key.to(String.class) : key.toString();
					final String keyString = bucket.toString();
					return KPIDataValue.ofValueAndType(keyString, valueType);
				};
			}
		}

		return (containingAggName, bucket) -> {
			final Object value = getProperty(bucket, containingAggName, path);
			return KPIDataValue.ofValueAndType(value, valueType);
		};
	}

	private static Object getProperty(
			@NonNull final MultiBucketBase bucket,
			@NonNull final String containingAggName,
			@NonNull final List<String> path)
	{
		Check.assumeNotEmpty(path, "path shall not be empty");

		final Map<String, Aggregate> aggregations = bucket.aggregations();
		final String aggName = path.getFirst();
		final Aggregate aggregate = aggregations.get(aggName);

		if (aggregate == null)
		{
			throw new AdempiereException("Cannot find an aggregation named [" + aggName + "] in [" + containingAggName + "]");
		}

		// Use _get() to extract the underlying aggregation object
		final Object aggregateValue = aggregate._get();

		// Handle single metric aggregations
		if (aggregateValue instanceof SingleMetricAggregateBase)
		{
			final List<String> subPath = path.subList(1, path.size());
			if (subPath.size() == 1 && "value".equals(subPath.getFirst()))
			{
				final SingleMetricAggregateBase singleMetric = (SingleMetricAggregateBase) aggregateValue;
				return singleMetric.value();
			}
			else
			{
				throw new AdempiereException("Cannot extract " + path + " from single metric aggregation");
			}
		}
		// Handle multi-bucket aggregations
		else if (aggregateValue instanceof MultiBucketAggregateBase)
		{
			// For multi-bucket aggregations, we need to navigate through the buckets
			throw new AdempiereException("Multi-bucket navigation not implemented for path " + path);
		}
		// Handle single bucket aggregations
		else if (aggregateValue instanceof SingleBucketAggregateBase)
		{
			// For single bucket aggregations, continue navigating through sub-aggregations
			final SingleBucketAggregateBase singleBucket = (SingleBucketAggregateBase) aggregateValue;
			final Map<String, Aggregate> subAggs = singleBucket.aggregations();
			if (path.size() > 1)
			{
				return getPropertyFromAggregations(subAggs, path.subList(1, path.size()));
			}
		}

		throw new AdempiereException("Unknown aggregation type for " + aggregate);
	}

	private static Object getPropertyFromAggregations(
			@NonNull final Map<String, Aggregate> aggregations,
			@NonNull final List<String> path)
	{
		if (path.isEmpty())
		{
			throw new AdempiereException("Path cannot be empty");
		}

		final String aggName = path.getFirst();
		final Aggregate aggregate = aggregations.get(aggName);

		if (aggregate == null)
		{
			throw new AdempiereException("Cannot find aggregation named [" + aggName + "]");
		}

		// Use _get() to extract the underlying aggregation object
		final Object aggregateValue = aggregate._get();

		if (path.size() == 1)
		{
			// Last element in path
			if (aggregateValue instanceof SingleMetricAggregateBase)
			{
				return ((SingleMetricAggregateBase) aggregateValue).value();
			}
			else
			{
				throw new AdempiereException("Expected single metric aggregation for " + aggName);
			}
		}
		else
		{
			// Navigate deeper
			if (aggregateValue instanceof SingleBucketAggregateBase)
			{
				final SingleBucketAggregateBase singleBucket = (SingleBucketAggregateBase) aggregateValue;
				return getPropertyFromAggregations(singleBucket.aggregations(), path.subList(1, path.size()));
			}
			else
			{
				throw new AdempiereException("Cannot navigate deeper into aggregation " + aggName);
			}
		}
	}
}