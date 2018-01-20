package de.metas.ui.web.document.filter;

import org.adempiere.util.Check;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.Value;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public final class DocumentFilterParamDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final boolean joinAnd;
	private final String parameterName;
	private final String fieldName;
	private final DocumentFieldWidgetType widgetType;
	private final Class<?> valueClass;
	private final ITranslatableString displayName;
	private final boolean showIncrementDecrementButtons;

	private final Operator operator;
	private final Object defaultValue;
	private final Object defaultValueTo;

	private final boolean mandatory;
	private final LookupDescriptor lookupDescriptor;

	public static final String AUTOFILTER_INITIALVALUE_DATE_NOW = new String("NOW");
	private final Object autoFilterInitialValue;

	private DocumentFilterParamDescriptor(final Builder builder)
	{
		joinAnd = builder.joinAnd;

		parameterName = builder.parameterName;
		Check.assumeNotEmpty(parameterName, "parameterName is not empty");

		fieldName = builder.getFieldName();
		Check.assumeNotEmpty(fieldName, "fieldName is not empty");

		widgetType = builder.getWidgetType();
		Check.assumeNotNull(widgetType, "Parameter widgetType is not null");
		valueClass = DescriptorsFactoryHelper.getValueClass(builder.widgetType, builder.lookupDescriptor);

		displayName = builder.getDisplayName();
		Check.assumeNotNull(displayName, "Parameter displayNameTrls is not null");

		showIncrementDecrementButtons = builder.showIncrementDecrementButtons;

		operator = builder.operator;

		defaultValue = builder.defaultValue;
		defaultValueTo = builder.defaultValueTo;

		lookupDescriptor = builder.lookupDescriptor;

		mandatory = builder.mandatory;

		autoFilterInitialValue = builder.autoFilterInitialValue;
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayName.translate(adLanguage);
	}

	public boolean isRange()
	{
		return operator != null && operator.isRangeOperator();
	}

	public LookupDataSource getLookupDataSource()
	{
		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
	}

	private final LookupDataSource getLookupDataSourceOrNull()
	{
		if (lookupDescriptor == null)
		{
			return null;
		}
		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);
	}

	public Object convertValueFromJson(final Object jsonValue)
	{
		return DocumentFieldDescriptor.convertToValueClass(getFieldName(), jsonValue, getWidgetType(), getValueClass(), getLookupDataSourceOrNull());
	}

	public boolean isAutoFilter()
	{
		return autoFilterInitialValue != null;
	}

	public boolean isAutoFilterInitialValueIsDateNow()
	{
		return widgetType.isDateOrTime() && AUTOFILTER_INITIALVALUE_DATE_NOW.equals(autoFilterInitialValue);
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
		private boolean showIncrementDecrementButtons;

		private Object autoFilterInitialValue;

		private Builder()
		{
		}

		/* package */DocumentFilterParamDescriptor build()
		{
			return new DocumentFilterParamDescriptor(this);
		}

		public Builder setJoinAnd(final boolean joinAnd)
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

		Builder setParameterName(final String parameterName)
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
			return widgetType;
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

		public Builder setOperator(final Operator operator)
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

		public Builder setMandatory(final boolean mandatory)
		{
			this.mandatory = mandatory;
			return this;
		}

		public Builder setShowIncrementDecrementButtons(final boolean showIncrementDecrementButtons)
		{
			this.showIncrementDecrementButtons = showIncrementDecrementButtons;
			return this;
		}

		public Builder setAutoFilterInitialValue(Object autoFilterInitialValue)
		{
			this.autoFilterInitialValue = autoFilterInitialValue;
			return this;
		}
	}

}
