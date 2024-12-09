package de.metas.ui.web.document.filter;

import de.metas.i18n.AdMessageKey;
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
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

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
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DocumentFilterParamDescriptor
{
<<<<<<< HEAD
	public static Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(DocumentFilterParamDescriptor.class);

	boolean joinAnd;
	String parameterName;
	String fieldName;
	DocumentFieldWidgetType widgetType;
	Class<?> valueClass;
	ITranslatableString displayName;
	boolean showIncrementDecrementButtons;

	Operator operator;
	Object defaultValue;
	Object defaultValueTo;

	boolean mandatory;
	Optional<LookupDescriptor> lookupDescriptor;
=======
	private static final Logger logger = LogManager.getLogger(DocumentFilterParamDescriptor.class);

	boolean joinAnd;
	@NonNull String parameterName;
	@NonNull String fieldName;
	@NonNull DocumentFieldWidgetType widgetType;
	@NonNull Class<?> valueClass;
	@NonNull ITranslatableString displayName;
	boolean showIncrementDecrementButtons;

	Operator operator;
	@Nullable Object defaultValue;
	@Nullable Object defaultValueTo;

	boolean mandatory;
	@NonNull Optional<LookupDescriptor> lookupDescriptor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@SuppressWarnings("StringOperationCanBeSimplified")
	public static final String AUTOFILTER_INITIALVALUE_DATE_NOW = new String("NOW");
	@SuppressWarnings("StringOperationCanBeSimplified")
	public static final String AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER = new String("@#AD_User_ID@");
<<<<<<< HEAD
	Object autoFilterInitialValue;

	BarcodeScannerType barcodeScannerType;

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
=======
	@Nullable Object autoFilterInitialValue;

	@Nullable BarcodeScannerType barcodeScannerType;

	@SuppressWarnings("OptionalAssignedToNull")
	@lombok.Builder(toBuilder = true, builderClassName = "Builder")
	private DocumentFilterParamDescriptor(
			final boolean joinAnd,
			@NonNull final String parameterName,
			@NonNull final String fieldName,
			@NonNull final DocumentFieldWidgetType widgetType,
			@NonNull final ITranslatableString displayName,
			final boolean showIncrementDecrementButtons,
			final Operator operator,
			@Nullable final Object defaultValue,
			@Nullable final Object defaultValueTo,
			final boolean mandatory,
			@Nullable final Optional<LookupDescriptor> lookupDescriptor,
			@Nullable final Object autoFilterInitialValue,
			@Nullable final BarcodeScannerType barcodeScannerType)
	{
		Check.assumeNotEmpty(parameterName, "parameterName is not empty");
		Check.assumeNotEmpty(fieldName, "fieldName is not empty");
		Check.assumeNotNull(displayName, "Parameter displayNameTrls is not null");

		this.joinAnd = joinAnd;
		this.parameterName = parameterName;
		this.fieldName = fieldName;
		this.widgetType = widgetType;
		this.displayName = displayName;
		this.showIncrementDecrementButtons = showIncrementDecrementButtons;
		this.operator = operator;
		this.defaultValue = defaultValue;
		this.defaultValueTo = defaultValueTo;
		this.mandatory = mandatory;
		this.lookupDescriptor = lookupDescriptor != null ? lookupDescriptor : Optional.empty();
		this.autoFilterInitialValue = autoFilterInitialValue;
		this.barcodeScannerType = barcodeScannerType;

		valueClass = DescriptorsFactoryHelper.getValueClass(this.widgetType, this.lookupDescriptor);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

	@Nullable
	public Object getDefaultValueConverted()
	{
		return convertValueToFieldType(getDefaultValue());
	}

	@Nullable
	public Object getDefaultValueToConverted()
	{
		return convertValueToFieldType(getDefaultValueTo());
	}

	@Nullable
	public Object convertValueFromJson(final Object jsonValue)
	{
		return convertValueToFieldType(jsonValue);
	}

	@Nullable
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

<<<<<<< HEAD
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
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

=======
	public Operator getOperatorOrEqualsIfNull() {return operator != null ? operator : Operator.EQUAL;}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static final class Builder
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		public String getFieldName()
		{
			return fieldName;
		}

<<<<<<< HEAD
		void setParameterName(final String parameterName)
		{
			this.parameterName = parameterName;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		public DocumentFieldWidgetType getWidgetType()
		{
			return widgetType;
		}

<<<<<<< HEAD
		public Builder barcodeScannerType(final BarcodeScannerType barcodeScannerType)
		{
			this.barcodeScannerType = barcodeScannerType;
			return this;
		}

		public Builder setDisplayName(final ITranslatableString displayName)
=======
		public Builder displayName(@NonNull final ITranslatableString displayName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			this.displayName = TranslatableStrings.copyOf(displayName);
			return this;
		}

<<<<<<< HEAD
		public Builder setDisplayName(final String displayName)
=======
		public Builder displayName(final String displayName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			this.displayName = TranslatableStrings.constant(displayName);
			return this;
		}

<<<<<<< HEAD
		public Builder setDisplayName(final AdMessageKey displayName)
		{
			return setDisplayName(TranslatableStrings.adMessage(displayName));
=======
		public Builder displayName(final AdMessageKey displayName)
		{
			return displayName(TranslatableStrings.adMessage(displayName));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		public ITranslatableString getDisplayName()
		{
			return displayName;
		}

<<<<<<< HEAD
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
=======
		public Builder lookupDescriptor(@NonNull final Optional<LookupDescriptor> lookupDescriptor)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			this.lookupDescriptor = lookupDescriptor;
			return this;
		}

<<<<<<< HEAD
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

		public Builder setAutoFilterInitialValue(final Object autoFilterInitialValue)
		{
			this.autoFilterInitialValue = autoFilterInitialValue;
			return this;
=======
		public Builder lookupDescriptor(@Nullable final LookupDescriptor lookupDescriptor)
		{
			return lookupDescriptor(Optional.ofNullable(lookupDescriptor));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

}
