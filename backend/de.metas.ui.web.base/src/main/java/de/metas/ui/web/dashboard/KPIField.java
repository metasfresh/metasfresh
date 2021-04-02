package de.metas.ui.web.dashboard;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.InternalMultiBucketAggregation;
import org.elasticsearch.search.aggregations.InvalidAggregationPathException;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

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
public class KPIField
{
	public static Builder builder()
	{
		return new Builder();
	}

	@FunctionalInterface
	public interface BucketValueExtractor
	{
		KPIDataValue extractValue(String containingAggName, MultiBucketsAggregation.Bucket bucket);
	}

	@Getter
	private final String fieldName;
	@Getter
	private final boolean groupBy;

	private final ITranslatableString caption;
	private final ITranslatableString offsetCaption;
	private final ITranslatableString description;
	@Getter
	private final String unit;
	@Getter
	private final KPIFieldValueType valueType;
	@Getter
	private final Integer numberPrecision;

	@Getter
	private final String color;

	private final String esPathStr;
	private final List<String> esPath;
	@Getter
	private final BucketValueExtractor bucketValueExtractor;

	private KPIField(final Builder builder)
	{
		Check.assumeNotEmpty(builder.fieldName, "builder.fieldName is not empty");
		Check.assumeNotNull(builder.caption, "Parameter builder.caption is not null");
		Check.assumeNotNull(builder.description, "Parameter builder.description is not null");
		Check.assumeNotNull(builder.valueType, "Parameter builder.valueType is not null");
		Check.assumeNotEmpty(builder.esPath, "builder.esPath is not empty");

		fieldName = builder.fieldName;
		groupBy = builder.groupBy;

		caption = builder.caption;
		offsetCaption = builder.offsetCaption;
		description = builder.description;
		unit = builder.unit;
		valueType = builder.valueType;
		numberPrecision = builder.numberPrecision;

		color = builder.color;

		esPathStr = builder.esPathStr;
		esPath = builder.esPath;
		bucketValueExtractor = createBucketValueExtractor(esPath, valueType);
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

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("groupBy", groupBy)
				.add("esPath", esPath)
				.add("valueType", valueType)
				.toString();
	}

	public String getOffsetFieldName()
	{
		return fieldName + "_offset";
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getOffsetCaption(final String adLanguage)
	{
		return offsetCaption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public String getESPathAsString()
	{
		return esPathStr;
	}

	public static final class Builder
	{
		private String fieldName;
		private boolean groupBy = false;
		private ITranslatableString caption = TranslatableStrings.empty();
		private ITranslatableString offsetCaption = TranslatableStrings.empty();
		private ITranslatableString description = TranslatableStrings.empty();
		private String unit;
		private KPIFieldValueType valueType;
		@Nullable private Integer numberPrecision;

		private String color;

		private String esPathStr;
		private List<String> esPath;

		private static final Splitter PATH_SPLITTER = Splitter.on('.').trimResults();

		private Builder()
		{
			super();
		}

		public KPIField build()
		{
			return new KPIField(this);
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		public Builder setGroupBy(final boolean groupBy)
		{
			this.groupBy = groupBy;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			Check.assumeNotNull(caption, "Parameter caption is not null");
			this.caption = caption;
			return this;
		}

		public Builder setOffsetCaption(final ITranslatableString offsetCaption)
		{
			Check.assumeNotNull(offsetCaption, "Parameter offsetCaption is not null");
			this.offsetCaption = offsetCaption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			Check.assumeNotNull(description, "Parameter description is not null");
			this.description = description;
			return this;
		}

		public Builder setUnit(final String unit)
		{
			this.unit = unit;
			return this;
		}

		public Builder setValueType(final KPIFieldValueType valueType)
		{
			this.valueType = valueType;
			return this;
		}

		public Builder setNumberPrecision(@Nullable final Integer numberPrecision)
		{
			this.numberPrecision = numberPrecision;
			return this;
		}

		public Builder setColor(final String color)
		{
			this.color = color;
			return this;
		}

		public Builder setESPath(final String esPathStr)
		{
			this.esPathStr = esPathStr.trim();
			esPath = PATH_SPLITTER.splitToList(this.esPathStr);
			return this;
		}
	}
}
