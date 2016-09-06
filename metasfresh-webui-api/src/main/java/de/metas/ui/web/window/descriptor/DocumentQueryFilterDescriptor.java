package de.metas.ui.web.window.descriptor;

import java.util.Map;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class DocumentQueryFilterDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String fieldName;
	private final DocumentFieldWidgetType widgetType;
	private final Map<String, String> displayNameTrls;
	private final boolean frequentUsed;

	private final boolean requiresParameters;
	private final boolean rangeParameter;

	private DocumentQueryFilterDescriptor(final Builder builder)
	{
		super();

		fieldName = builder.fieldName;
		Check.assumeNotNull(fieldName, "Parameter fieldName is not null");

		widgetType = builder.widgetType;
		Check.assumeNotNull(widgetType, "Parameter widgetType is not null");

		displayNameTrls = builder.displayNameTrls == null ? ImmutableMap.of() : ImmutableMap.copyOf(builder.displayNameTrls);

		frequentUsed = builder.frequentUsed;

		requiresParameters = builder.requiresParameters;
		rangeParameter = builder.rangeParameter;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("widgetType", widgetType)
				.add("requiresParameters", requiresParameters)
				.add("range", rangeParameter)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayNameTrls.get(adLanguage);
	}

	public boolean isFrequentUsed()
	{
		return frequentUsed;
	}

	public boolean isRequiresParameters()
	{
		return requiresParameters;
	}

	public boolean isRangeParameter()
	{
		return rangeParameter;
	}

	public static final class Builder
	{
		private String fieldName;
		private DocumentFieldWidgetType widgetType;
		private Map<String, String> displayNameTrls;
		public boolean frequentUsed;
		private boolean rangeParameter;
		private boolean requiresParameters;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilterDescriptor build()
		{
			return new DocumentQueryFilterDescriptor(this);
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}

		public Builder setDisplayName(final Map<String, String> displayNameTrls)
		{
			this.displayNameTrls = displayNameTrls;
			return this;
		}

		public Builder setFrequentUsed(final boolean frequentUsed)
		{
			this.frequentUsed = frequentUsed;
			return this;
		}

		public Builder setRequiresParameters(final boolean requiresParameters)
		{
			this.requiresParameters = requiresParameters;
			return this;
		}

		public Builder setRangeParameter(final boolean rangeParameter)
		{
			this.rangeParameter = rangeParameter;
			return this;
		}
	}
}
