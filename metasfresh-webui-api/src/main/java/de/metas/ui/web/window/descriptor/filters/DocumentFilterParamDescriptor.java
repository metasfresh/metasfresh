package de.metas.ui.web.window.descriptor.filters;

import org.adempiere.util.Check;
import org.compiere.model.MQuery.Operator;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

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

public class DocumentFilterParamDescriptor
{
	static final Builder builder()
	{
		return new Builder();
	}

	private final boolean joinAnd; 
	private final String parameterName;
	private final String fieldName;
	private final DocumentFieldWidgetType widgetType;
	private final ITranslatableString displayName;
	
	private final Operator operator;
	private final Object defaultValue;
	private final Object defaultValueTo;

	private final LookupDescriptor lookupDescriptor;
	
	private final boolean mandatory;

	private DocumentFilterParamDescriptor(final Builder builder)
	{
		super();
		
		joinAnd = builder.joinAnd;

		parameterName = builder.parameterName;
		Check.assumeNotEmpty(parameterName, "parameterName is not empty");

		fieldName = builder.fieldName;
		Check.assumeNotEmpty(fieldName, "fieldName is not empty");

		widgetType = builder.widgetType;
		Check.assumeNotNull(widgetType, "Parameter widgetType is not null");

		displayName = builder.displayName;
		Check.assumeNotNull(displayName, "Parameter displayNameTrls is not null");

		operator = builder.operator;

		defaultValue = builder.defaultValue;
		defaultValueTo = builder.defaultValueTo;
		
		lookupDescriptor = builder.lookupDescriptor;
		
		mandatory = builder.mandatory;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("join", joinAnd ? "AND" : "OR")
				.add("parameterName", parameterName)
				.add("fieldName", fieldName)
				.add("widgetType", widgetType)
				.add("operator", operator)
				.add("defaultValue", defaultValue)
				.add("defaultValueTo", defaultValueTo)
				.add("lookupDescriptor", lookupDescriptor)
				.add("required", mandatory)
				.toString();
	}
	
	public boolean isJoinAnd()
	{
		return joinAnd;
	}

	public String getParameterName()
	{
		return parameterName;
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
	
	public Operator getOperator()
	{
		return operator;
	}
	
	public boolean isRange()
	{
		return operator != null && operator.isRangeOperator();
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}

	public Object getDefaultValueTo()
	{
		return defaultValueTo;
	}
	
	public LookupDataSource getLookupDataSource()
	{
		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);		
	}
	
	public boolean isMandatory()
	{
		return mandatory;
	}

	public static final class Builder
	{
		private boolean joinAnd = true;
		private String fieldName;
		private String parameterName;
		private DocumentFieldWidgetType widgetType;
		private ITranslatableString displayName;
		private Operator operator = Operator.EQUAL;
		private Object defaultValue;
		private Object defaultValueTo;
		private LookupDescriptor lookupDescriptor;
		private boolean mandatory = false;

		private Builder()
		{
			super();
		}

		/* package */DocumentFilterParamDescriptor build()
		{
			return new DocumentFilterParamDescriptor(this);
		}

		public Builder setJoinAnd(boolean joinAnd)
		{
			this.joinAnd = joinAnd;
			return this;
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		public String getFieldName()
		{
			return fieldName;
		}

		public Builder setParameterName(String parameterName)
		{
			this.parameterName = parameterName;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}
		
		public DocumentFieldWidgetType getWidgetType()
		{
			return this.widgetType;
		}

		public Builder setDisplayName(final ITranslatableString displayName)
		{
			this.displayName = ImmutableTranslatableString.copyOf(displayName);
			return this;
		}

		public Builder setDisplayName(final String displayName)
		{
			this.displayName = ImmutableTranslatableString.constant(displayName);
			return this;
		}
		
		public ITranslatableString getDisplayName()
		{
			return displayName;
		}

		public Builder setOperator(Operator operator)
		{
			Check.assumeNotNull(operator, "Parameter operator is not null");
			this.operator = operator;
			return this;
		}

		public Builder setDefaultValue(final Object defaultValue)
		{
			this.defaultValue = defaultValue;
			return this;
		}

		public Builder setDefaultValueTo(final Object defaultValueTo)
		{
			this.defaultValueTo = defaultValueTo;
			return this;
		}
		
		public Builder setLookupDescriptor(final LookupDescriptor lookupDescriptor)
		{
			this.lookupDescriptor = lookupDescriptor;
			return this;
		}
		
		public Builder setMandatory(boolean mandatory)
		{
			this.mandatory = mandatory;
			return this;
		}
	}

}
