package de.metas.ui.web.dashboard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
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

	private final String fieldName;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final KPIFieldValueType valueType;

	private final List<String> esPath;
	private final boolean esTimeField;
	private final BucketValueExtractor valueExtractor;

	private KPIField(final Builder builder)
	{
		super();
		fieldName = builder.fieldName;
		caption = builder.caption;
		description = builder.description;
		valueType = builder.valueType;

		esPath = builder.esPath;
		esTimeField = builder.esTimeField;
		valueExtractor = createValueExtractor(esPath);
	}

	private static BucketValueExtractor createValueExtractor(final List<String> path)
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

		switch (valueType)
		{
			case Date:
			{
				if(value instanceof String)
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
				break;
			}
			case DateTime:
			{
				if(value instanceof String)
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
				break;
			}
			case Number:
			{
				if (value instanceof String)
				{
					return new BigDecimal(value.toString());
				}
				else if (value instanceof Double)
				{
					return BigDecimal.valueOf(((Double)value).doubleValue());
				}
				else if (value instanceof Number)
				{
					return BigDecimal.valueOf(((Number)value).intValue());
				}
				break;
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
		
		// Fallback
		return value;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("esPath", esPath)
				.add("valueType", valueType)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public KPIFieldValueType getValueType()
	{
		return valueType;
	}

	public List<String> getESPath()
	{
		return esPath;
	}

	public boolean isESTimeField()
	{
		return esTimeField;
	}

	public BucketValueExtractor getValueExtractor()
	{
		return valueExtractor;
	}

	public static final class Builder
	{
		private String fieldName;
		private ITranslatableString caption;
		private ITranslatableString description = ImmutableTranslatableString.empty();
		private KPIFieldValueType valueType;
		private boolean esTimeField;
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

		public Builder setValueType(final KPIFieldValueType valueType)
		{
			this.valueType = valueType;
			return this;
		}

		public Builder setESPath(final String esPath)
		{
			this.esPath = PATH_SPLITTER.splitToList(esPath);
			return this;
		}

		public Builder setESTimeField(final boolean esTimeField)
		{
			this.esTimeField = esTimeField;
			return this;
		}

	}
}
