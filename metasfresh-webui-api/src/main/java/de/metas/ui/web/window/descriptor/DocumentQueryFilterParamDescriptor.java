package de.metas.ui.web.window.descriptor;

import java.util.Map;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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

public class DocumentQueryFilterParamDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	private final String fieldName;
	private final DocumentFieldWidgetType widgetType;
	private final ITranslatableString displayName;
	private final boolean rangeParameter;

	private DocumentQueryFilterParamDescriptor(final Builder builder)
	{
		super();

		fieldName = builder.fieldName;
		Check.assumeNotNull(fieldName, "Parameter fieldName is not null");

		widgetType = builder.widgetType;
		Check.assumeNotNull(widgetType, "Parameter widgetType is not null");

		displayName = builder.displayName;
		Check.assumeNotNull(displayName, "Parameter displayNameTrls is not null");
		
		rangeParameter = builder.rangeParameter;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
				.add("widgetType", widgetType)
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
		return displayName.translate(adLanguage);
	}

	public boolean isRangeParameter()
	{
		return rangeParameter;
	}

	public static final class Builder
	{
		private String fieldName;
		private DocumentFieldWidgetType widgetType;
		private ITranslatableString displayName;
		private boolean rangeParameter;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilterParamDescriptor build()
		{
			return new DocumentQueryFilterParamDescriptor(this);
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
			this.displayName = ImmutableTranslatableString.ofMap(displayNameTrls);
			return this;
		}

		public Builder setDisplayName(final String displayName)
		{
			this.displayName = ImmutableTranslatableString.constant(displayName);
			return this;
		}

		public Builder setRangeParameter(final boolean rangeParameter)
		{
			this.rangeParameter = rangeParameter;
			return this;
		}
	}

}
