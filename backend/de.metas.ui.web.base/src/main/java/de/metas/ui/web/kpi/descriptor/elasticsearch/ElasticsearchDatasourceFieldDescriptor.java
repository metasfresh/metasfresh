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

import com.google.common.base.Splitter;
import de.metas.ui.web.kpi.data.KPIDataValue;
import de.metas.ui.web.kpi.descriptor.KPIFieldValueType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.InternalMultiBucketAggregation;
import org.elasticsearch.search.aggregations.InvalidAggregationPathException;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;

import java.util.List;

@Value
public class ElasticsearchDatasourceFieldDescriptor
{
	@FunctionalInterface
	public interface BucketValueExtractor
	{
		KPIDataValue extractValue(String containingAggName, MultiBucketsAggregation.Bucket bucket);
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

	private static BucketValueExtractor createBucketValueExtractor(@NonNull final List<String> path, @NonNull final KPIFieldValueType valueType)
	{
		if (path.size() == 1)
		{
			final String fieldName = path.get(0);
			if ("doc_count".equals(fieldName))
			{
				return (containingAggName, bucket) -> KPIDataValue.ofValueAndType(bucket.getDocCount(), valueType);
			}
			else if ("key".equals(fieldName))
			{
				return (containingAggName, bucket) -> KPIDataValue.ofValueAndType(bucket.getKeyAsString(), valueType);
			}
		}

		return (containingAggName, bucket) -> {
			final Object value;
			if (bucket instanceof InternalMultiBucketAggregation.InternalBucket)
			{
				final InternalMultiBucketAggregation.InternalBucket internalBucket = (InternalMultiBucketAggregation.InternalBucket)bucket;
				value = internalBucket.getProperty(containingAggName, path);
			}
			else
			{
				value = getProperty(bucket, containingAggName, path);
			}

			return KPIDataValue.ofValueAndType(value, valueType);
		};
	}

	private static Object getProperty(
			@NonNull final MultiBucketsAggregation.Bucket bucket,
			@NonNull final String containingAggName,
			@NonNull final List<String> path)
	{
		Check.assumeNotEmpty(path, "path shall not be empty");

		final Aggregations aggregations = bucket.getAggregations();
		final String aggName = path.get(0);
		final Aggregation aggregation = aggregations.get(aggName);
		if (aggregation == null)
		{
			throw new InvalidAggregationPathException("Cannot find an aggregation named [" + aggName + "] in [" + containingAggName + "]");
		}
		else if (aggregation instanceof InternalAggregation)
		{
			final InternalAggregation internalAggregation = (InternalAggregation)aggregation;
			return internalAggregation.getProperty(path.subList(1, path.size()));
		}
		else if (aggregation instanceof NumericMetricsAggregation.SingleValue)
		{
			final NumericMetricsAggregation.SingleValue singleValue = (NumericMetricsAggregation.SingleValue)aggregation;
			final List<String> subPath = path.subList(1, path.size());
			if (subPath.size() == 1 && "value".equals(subPath.get(0)))
			{
				return singleValue.value();
			}
			else
			{
				throw new AdempiereException("Cannot extract " + path + " from " + singleValue);
			}
		}
		else
		{
			throw new AdempiereException("Unknown aggregation type " + aggregation.getClass() + " for " + aggregation);
		}
	}

}
