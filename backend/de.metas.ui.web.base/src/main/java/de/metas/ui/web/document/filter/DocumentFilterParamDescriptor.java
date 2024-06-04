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
import java.util.function.UnaryOperator;

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

	@SuppressWarnings("StringOperationCanBeSimplified")
	public static final String AUTOFILTER_INITIALVALUE_DATE_NOW = new String("NOW");
	@SuppressWarnings("StringOperationCanBeSimplified")
	public static final String AUTOFILTER_INITIALVALUE_CURRENT_LOGGED_USER = new String("@#AD_User_ID@");
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
		return lookupDescriptor.map(LookupDataSourceFactory.sharedInstance()::getLookupDataSource);
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
	private Object convertValueToFieldType(@Nullable final Object value)
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

	public Operator getOperatorOrEqualsIfNull() {return operator != null ? operator : Operator.EQUAL;}

	/**
	 * This explicitly written class adds some extensions to the standard builder class which is generated by lombok from the annotation of the {@code DocumentFilterParamDescriptor}'s constructor.
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static final class Builder
	{
		public String getFieldName()
		{
			return fieldName;
		}

		public String getParameterName()
		{
			return parameterName;
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			return widgetType;
		}

		public Builder displayName(@NonNull final ITranslatableString displayName)
		{
			this.displayName = TranslatableStrings.copyOf(displayName);
			return this;
		}

		public Builder displayName(final String displayName)
		{
			this.displayName = TranslatableStrings.constant(displayName);
			return this;
		}

		public Builder displayName(final AdMessageKey displayName)
		{
			return displayName(TranslatableStrings.adMessage(displayName));
		}

		public ITranslatableString getDisplayName()
		{
			return displayName;
		}

		public Builder lookupDescriptor(@NonNull final Optional<LookupDescriptor> lookupDescriptor)
		{
			this.lookupDescriptor = lookupDescriptor;
			return this;
		}

		public Builder lookupDescriptor(@Nullable final LookupDescriptor lookupDescriptor)
		{
			return lookupDescriptor(Optional.ofNullable(lookupDescriptor));
		}

		public Builder lookupDescriptor(@NonNull final UnaryOperator<LookupDescriptor> mapper)
		{
			if (this.lookupDescriptor!= null) // don't replace with isPresent() since it could be null at this point
			{
				return lookupDescriptor(this.lookupDescriptor.map(mapper));
			}
			return this;
		}
	}
}
