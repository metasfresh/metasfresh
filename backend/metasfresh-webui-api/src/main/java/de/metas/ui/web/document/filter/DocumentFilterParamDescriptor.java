package de.metas.ui.web.document.filter;

import java.util.Optional;

import javax.annotation.Nullable;

import org.slf4j.Logger;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import lombok.NonNull;
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
	public static Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(DocumentFilterParamDescriptor.class);

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
	private final Optional<LookupDescriptor> lookupDescriptor;

	public static final String AUTOFILTER_INITIALVALUE_DATE_NOW = new String("NOW");
	public static final String AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER = new String("@#AD_User_ID@");
	private final Object autoFilterInitialValue;

	private final BarcodeScannerType barcodeScannerType;

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
		
		barcodeScannerType = builder.barcodeScannerType;
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayName.translate(adLanguage);
	}

	public boolean isRange()
	{
		return operator != null && operator.isRangeOperator();
	}

	public Optional<LookupDataSource> getLookupDataSource()
	{
		return lookupDescriptor.map(LookupDataSourceFactory.instance::getLookupDataSource);
	}

	public Object getDefaultValueConverted()
	{
		return convertValueToFieldType(getDefaultValue());
	}

	public Object getDefaultValueToConverted()
	{
		return convertValueToFieldType(getDefaultValueTo());
	}

	public Object convertValueFromJson(final Object jsonValue)
	{
		return convertValueToFieldType(jsonValue);
	}

	private Object convertValueToFieldType(final Object value)
	{
		Object valueConv = DataTypes.convertToValueClass(
				getFieldName(),
				value,
				getWidgetType(),
				getValueClass(),
				getLookupDataSource().orElse(null));

		// Convert empty string to null
		// This is a workaround until task https://github.com/metasfresh/metasfresh-webui-frontend/issues/2040 is done.
		if (valueConv instanceof String && ((String)valueConv).isEmpty())
		{
			valueConv = null;
			logger.warn("Converted empty string to null for value={}, descriptor={}. \n This issue shall be solved by https://github.com/metasfresh/metasfresh-webui-frontend/issues/2040.", value, this);
		}

		return valueConv;
	}

	public boolean isAutoFilter()
	{
		return autoFilterInitialValue != null;
	}

	public boolean isAutoFilterInitialValueIsDateNow()
	{
		return widgetType.isDateOrTime() && AUTOFILTER_INITIALVALUE_DATE_NOW.equals(autoFilterInitialValue);
	}

	public boolean isAutoFilterInitialValueIsCurrentLoggedUser()
	{
		return widgetType.isLookup() && AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER.equals(autoFilterInitialValue);
	}

	public static final class Builder
	{
		private boolean joinAnd = true;
		private String fieldName;
		private String parameterName;
		private DocumentFieldWidgetType widgetType;
		private BarcodeScannerType barcodeScannerType;
		private ITranslatableString displayName;
		private Operator operator = Operator.EQUAL;
		private Object defaultValue;
		private Object defaultValueTo;
		private Optional<LookupDescriptor> lookupDescriptor = Optional.empty();
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

		public Builder barcodeScannerType(final BarcodeScannerType barcodeScannerType)
		{
			this.barcodeScannerType = barcodeScannerType;
			return this;
		}

		public Builder setDisplayName(final ITranslatableString displayName)
		{
			this.displayName = TranslatableStrings.copyOf(displayName);
			return this;
		}

		public Builder setDisplayName(final String displayName)
		{
			this.displayName = TranslatableStrings.constant(displayName);
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

		public Builder setLookupDescriptor(@NonNull final Optional<LookupDescriptor> lookupDescriptor)
		{
			this.lookupDescriptor = lookupDescriptor;
			return this;
		}

		public Builder setLookupDescriptor(@Nullable final LookupDescriptor lookupDescriptor)
		{
			return setLookupDescriptor(Optional.ofNullable(lookupDescriptor));
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
