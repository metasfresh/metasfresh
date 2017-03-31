package de.metas.ui.web.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

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
	public static final Builder builder()
	{
		return new Builder();
	}

	@FunctionalInterface
	public static interface BucketValueExtractor
	{
		Object extractValue(final String containingAggName, final MultiBucketsAggregation.Bucket bucket);
	}

	private static final Logger logger = LogManager.getLogger(KPIField.class);

	private final String fieldName;
	private final boolean groupBy;

	private final ITranslatableString caption;
	private final ITranslatableString offsetCaption;
	private final ITranslatableString description;
	private final String unit;
	private final KPIFieldValueType valueType;
	private final Integer numberPrecision;

	private final String color;

	private final String esPathStr;
	private final List<String> esPath;
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
		bucketValueExtractor = createBucketValueExtractor(esPath);
	}

	private static BucketValueExtractor createBucketValueExtractor(final List<String> path)
	{
		if (path.size() == 1)
		{
			final String fieldName = path.get(0);
			if ("doc_count".equals(fieldName))
			{
				return (containingAggName, bucket) -> bucket.getDocCount();
			}
			else if ("key".equals(fieldName))
			{
				return (containingAggName, bucket) -> bucket.getKeyAsString();
			}
		}

		return (containingAggName, bucket) -> bucket.getProperty(containingAggName, path);
	}

	public final Object convertValueToJson(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			switch (valueType)
			{
				case Date:
				{
					if (value instanceof String)
					{
						final Date date = JSONDate.fromJson(value.toString(), DocumentFieldWidgetType.Date);
						return JSONDate.toJson(date);
					}
					else if (value instanceof Date)
					{
						return JSONDate.toJson((Date)value);
					}
					else if (value instanceof Number)
					{
						final long millis = ((Number)value).longValue();
						return JSONDate.toJson(millis);
					}
					else
					{
						return value;
					}
				}
				case DateTime:
				{
					if (value instanceof String)
					{
						final Date date = JSONDate.fromJson(value.toString(), DocumentFieldWidgetType.DateTime);
						return JSONDate.toJson(date);
					}
					else if (value instanceof Date)
					{
						return JSONDate.toJson((Date)value);
					}
					else if (value instanceof Number)
					{
						final long millis = ((Number)value).longValue();
						return JSONDate.toJson(millis);
					}
					else
					{
						return value;
					}
				}
				case Number:
				{
					if (value instanceof String)
					{
						final BigDecimal bd = new BigDecimal(value.toString());
						return roundToPrecision(bd);
					}
					else if (value instanceof Double)
					{
						final BigDecimal bd = BigDecimal.valueOf(((Double)value).doubleValue());
						return roundToPrecision(bd);
					}
					else if (value instanceof Number)
					{
						final BigDecimal bd = BigDecimal.valueOf(((Number)value).intValue());
						return roundToPrecision(bd);
					}
					else if (value instanceof Integer)
					{
						return value;
					}
					else
					{
						return value;
					}
				}
				case String:
				{
					return value.toString();
				}
				default:
				{
					throw new IllegalStateException("valueType not supported: " + valueType);
				}
			}
		}
		catch (Exception ex)
		{
			logger.warn("Failed converting {} for field {}", value, this, ex);
			return value.toString();
		}
	}

	private final BigDecimal roundToPrecision(final BigDecimal bd)
	{
		if (numberPrecision == null)
		{
			return bd;
		}
		else
		{
			return bd.setScale(numberPrecision, RoundingMode.HALF_UP);
		}
	}

	public final Object convertValueToJsonUserFriendly(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			if (valueType == KPIFieldValueType.Date)
			{
				if (value instanceof String)
				{
					final Date date = JSONDate.fromJson(value.toString(), DocumentFieldWidgetType.Date);
					return DisplayType.getDateFormat(DisplayType.Date)
							.format(date);
				}
				else if (value instanceof Date)
				{
					final Date date = (Date)value;
					return DisplayType.getDateFormat(DisplayType.Date)
							.format(date);
				}
				else if (value instanceof Number)
				{
					final long millis = ((Number)value).longValue();
					final Date date = new Date(millis);
					return DisplayType.getDateFormat(DisplayType.Date)
							.format(date);
				}
				else
				{
					return value.toString();
				}
			}
			else if (valueType == KPIFieldValueType.DateTime)
			{
				if (value instanceof String)
				{
					final Date date = JSONDate.fromJson(value.toString(), DocumentFieldWidgetType.DateTime);
					return DisplayType.getDateFormat(DisplayType.DateTime)
							.format(date);
				}
				else if (value instanceof Date)
				{
					final Date date = (Date)value;
					return DisplayType.getDateFormat(DisplayType.DateTime)
							.format(date);
				}
				else if (value instanceof Number)
				{
					final long millis = ((Number)value).longValue();
					final Date date = new Date(millis);
					return DisplayType.getDateFormat(DisplayType.DateTime)
							.format(date);
				}
				else
				{
					return value.toString();
				}
			}
			else
			{
				return convertValueToJson(value);
			}
		}
		catch (Exception ex)
		{
			logger.warn("Failed converting {} for field {}", value, this, ex);
			return value.toString();
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

	public String getFieldName()
	{
		return fieldName;
	}

	public String getOffsetFieldName()
	{
		return fieldName + "_offset";
	}

	public boolean isGroupBy()
	{
		return groupBy;
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

	public KPIFieldValueType getValueType()
	{
		return valueType;
	}

	public String getUnit()
	{
		return unit;
	}

	public List<String> getESPath()
	{
		return esPath;
	}

	public String getESPathAsString()
	{
		return esPathStr;
	}

	public String getColor()
	{
		return color;
	}

	public BucketValueExtractor getBucketValueExtractor()
	{
		return bucketValueExtractor;
	}

	public static final class Builder
	{
		private String fieldName;
		private boolean groupBy = false;
		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private ITranslatableString offsetCaption = ImmutableTranslatableString.empty();
		private ITranslatableString description = ImmutableTranslatableString.empty();
		private String unit;
		private KPIFieldValueType valueType;
		private Integer numberPrecision;

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

		public Builder setNumberPrecision(final Integer numberPrecision)
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
