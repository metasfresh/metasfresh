package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.model.lookup.LookupDataSource;

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

@SuppressWarnings("serial")
public final class DocumentFieldDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(DocumentFieldDescriptor.class);

	/** Internal field name (aka ColumnName) */
	@JsonProperty("fieldName")
	private final String fieldName;
	@JsonProperty("caption")
	private final ITranslatableString caption;
	/** Detail ID or null if this is a field in main sections */
	@JsonProperty("detailId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String detailId;

	/** Is this the key field ? */
	@JsonProperty("key")
	private final boolean key;
	@JsonProperty("parentLink")
	private final boolean parentLink;
	@JsonProperty("virtualField")
	private final boolean virtualField;
	@JsonProperty("calculated")
	private final boolean calculated;

	@JsonProperty("widgetType")
	private final DocumentFieldWidgetType widgetType;

	@JsonProperty("valueClass")
	private final Class<?> valueClass;

	// @JsonProperty("defaultValueExpression")
	@JsonIgnore                 // FIXME: JSON serialization/deserialization of optional defaultValueExpression
	private final Optional<IExpression<?>> defaultValueExpression;

	@JsonIgnore                 // FIXME: JSON serialization/deserialization of callouts
	private final List<IDocumentFieldCallout> callouts;

	public static enum Characteristic
	{
		PublicField //
		, AdvancedField //
		, SideListField //
		, GridViewField //
		, AllowFiltering //
	};

	@JsonProperty("characteristics")
	private final Set<Characteristic> characteristics;

	@JsonProperty("readonlyLogic")
	private final ILogicExpression readonlyLogic;
	@JsonProperty("alwaysUpdateable")
	private final boolean alwaysUpdateable;
	@JsonProperty("displayLogic")
	private final ILogicExpression displayLogic;
	@JsonProperty("mandatoryLogic")
	private final ILogicExpression mandatoryLogic;

	@JsonProperty("data-binding")
	private final Optional<DocumentFieldDataBindingDescriptor> dataBinding;

	@JsonIgnore
	private final DocumentFieldDependencyMap dependencies;

	private DocumentFieldDescriptor(final Builder builder)
	{
		super();
		fieldName = Preconditions.checkNotNull(builder.fieldName, "name is null");
		caption = builder.getCaption();
		detailId = builder.detailId;

		key = builder.key;
		parentLink = builder.parentLink;
		virtualField = builder.virtualField;
		calculated = builder.calculated;

		widgetType = Preconditions.checkNotNull(builder.widgetType, "widgetType is null");

		valueClass = Preconditions.checkNotNull(builder.valueClass, "value class not null");

		defaultValueExpression = Preconditions.checkNotNull(builder.defaultValueExpression, "defaultValueExpression not null");

		characteristics = Sets.immutableEnumSet(builder.characteristics);
		readonlyLogic = builder.readonlyLogic;
		alwaysUpdateable = builder.alwaysUpdateable;
		displayLogic = builder.displayLogic;
		mandatoryLogic = builder.mandatoryLogic;

		dataBinding = builder.getDataBinding();

		dependencies = builder.buildDependencies();

		callouts = ImmutableList.copyOf(builder.callouts);
	}

	@JsonCreator
	private DocumentFieldDescriptor(
			@JsonProperty("fieldName") final String fieldName //
			, @JsonProperty("detailId") final String detailId //
			, @JsonProperty("key") final boolean key //
			, @JsonProperty("parentLink") final boolean parentLink //
			, @JsonProperty("virtualField") final boolean virtualField //
			, @JsonProperty("calculated") final boolean calculated //
			, @JsonProperty("widgetType") final DocumentFieldWidgetType widgetType //
			, @JsonProperty("valueClass") final Class<?> valueClass //
	// , @JsonProperty("defaultValueExpression") final IStringExpression defaultValueExpression //
	, @JsonProperty("characteristics") final Set<Characteristic> characteristics //
	, @JsonProperty("readonlyLogic") final ILogicExpression readonlyLogic //
	, @JsonProperty("alwaysUpdateable") final boolean alwaysUpdateable //
	, @JsonProperty("displayLogic") final ILogicExpression displayLogic //
	, @JsonProperty("mandatoryLogic") final ILogicExpression mandatoryLogic //
	, @JsonProperty("data-binding") final DocumentFieldDataBindingDescriptor dataBinding //
	)
	{
		this(new Builder()
				.setFieldName(fieldName)
				.setDetailId(detailId)
				.setKey(key)
				.setParentLink(parentLink)
				.setVirtualField(virtualField)
				.setCalculated(calculated)
				.setWidgetType(widgetType)
				.setValueClass(valueClass)
				// .setDefaultValueExpression(defaultValueExpression)
				.addCharacteristics(characteristics)
				.setReadonlyLogic(readonlyLogic)
				.setAlwaysUpdateable(alwaysUpdateable)
				.setDisplayLogic(displayLogic)
				.setMandatoryLogic(mandatoryLogic)
				.setDataBinding(dataBinding));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", fieldName)
				.add("detailId", detailId)
				.add("widgetType", widgetType)
				.add("characteristics", characteristics.isEmpty() ? null : characteristics)
				.add("fieldDataBinding", dataBinding)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}
	
	public ITranslatableString getCaption()
	{
		return caption;
	}

	public String getDetailId()
	{
		return detailId;
	}

	public boolean isKey()
	{
		return key;
	}

	public boolean isParentLink()
	{
		return parentLink;
	}

	public boolean isVirtualField()
	{
		return virtualField;
	}

	public boolean isCalculated()
	{
		return calculated;
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	public Class<?> getValueClass()
	{
		return valueClass;
	}

	public Optional<IExpression<?>> getDefaultValueExpression()
	{
		return defaultValueExpression;
	}

	public boolean hasCharacteristic(final Characteristic c)
	{
		return characteristics.contains(c);
	}

	public ILogicExpression getReadonlyLogic()
	{
		return readonlyLogic;
	}

	public boolean isAlwaysUpdateable()
	{
		return alwaysUpdateable;
	}

	public ILogicExpression getDisplayLogic()
	{
		return displayLogic;
	}

	public ILogicExpression getMandatoryLogic()
	{
		return mandatoryLogic;
	}

	/**
	 * @return field data binding info
	 */
	public Optional<DocumentFieldDataBindingDescriptor> getDataBinding()
	{
		return dataBinding;
	}

	public DocumentFieldDependencyMap getDependencies()
	{
		return dependencies;
	}

	/**
	 * Converts given value to target class.
	 *
	 * @param value value to be converted
	 * @param targetType target type
	 * @param lookupDataSource optional Lookup data source, if needed
	 * @return converted value
	 */
	public <T> T convertToValueClass(final Object value, final Class<T> targetType, final LookupDataSource lookupDataSource)
	{
		return convertToValueClass(fieldName, value, targetType, lookupDataSource);
	}

	/**
	 * Converts given value to target class.
	 *
	 * @param fieldName field name, needed only for logging purposes
	 * @param value value to be converted
	 * @param targetType target type
	 * @param lookupDataSource optional Lookup data source, if needed
	 * @return converted value
	 */
	public static <T> T convertToValueClass(final String fieldName, final Object value, final Class<T> targetType, final LookupDataSource lookupDataSource)
	{
		if (value == null)
		{
			return null;
		}

		final Class<?> fromType = value.getClass();

		try
		{
			// Corner case: we need to convert Timestamp(which extends Date) to strict Date because else all value changed comparing methods will fail
			if (java.util.Date.class.equals(targetType) && Timestamp.class.equals(fromType))
			{
				@SuppressWarnings("unchecked")
				final T valueConv = (T)JSONDate.fromTimestamp((Timestamp)value);
				return valueConv;
			}

			if (targetType.isAssignableFrom(fromType))
			{
				if (!targetType.equals(fromType))
				{
					logger.warn("Possible optimization issue: target type is assignable from source type, but they are not the same class."
							+ "\n In future we will disallow this case, so please check and fix it."
							+ "\n Field name: " + fieldName
							+ "\n Target type: " + targetType
							+ "\n Source type: " + fromType
							+ "\n Value: " + value
							+ "\n LookupDataSource: " + lookupDataSource);
				}

				@SuppressWarnings("unchecked")
				final T valueConv = (T)value;
				return valueConv;
			}

			if (String.class == targetType)
			{
				if (Map.class.isAssignableFrom(fromType))
				{
					// this is not allowed for consistency. let it fail.
				}
				// For any other case, blindly convert it to string
				else
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)value.toString();
					return valueConv;
				}
			}
			else if (java.util.Date.class == targetType)
			{
				if (value instanceof String)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)JSONDate.fromJson((String)value);
					return valueConv;
				}
			}
			else if (Integer.class == targetType)
			{
				if (value instanceof String)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(Integer)Integer.parseInt((String)value);
					return valueConv;
				}
				else if (value instanceof Number)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(Integer)((Number)value).intValue();
					return valueConv;
				}
			}
			else if (BigDecimal.class == targetType)
			{
				if (String.class == fromType)
				{
					final String valueStr = (String)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(valueStr.isEmpty() ? BigDecimal.ZERO : new BigDecimal(valueStr));
					return valueConv;
				}
				else if (Integer.class == fromType || int.class == fromType)
				{
					final int valueInt = (int)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)BigDecimal.valueOf(valueInt);
					return valueConv;
				}
			}
			else if (Boolean.class == targetType)
			{
				@SuppressWarnings("unchecked")
				final T valueConv = (T)DisplayType.toBoolean(value, Boolean.FALSE);
				return valueConv;
			}
			else if (IntegerLookupValue.class == targetType)
			{
				if (Map.class.isAssignableFrom(fromType))
				{
					@SuppressWarnings("unchecked")
					final Map<String, String> map = (Map<String, String>)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)JSONLookupValue.integerLookupValueFromJsonMap(map);
					return valueConv;
				}
				else if (Integer.class.isAssignableFrom(fromType))
				{
					final Integer valueInt = (Integer)value;
					if (lookupDataSource != null)
					{
						final LookupValue valueLookup = lookupDataSource.findById(valueInt);
						final T valueConv = convertToValueClass(fieldName, valueLookup, targetType, /* lookupDataSource */null);
						// TODO: what if valueConv was not found?
						return valueConv;
					}
				}
				else if (String.class == fromType)
				{
					final String valueStr = (String)value;
					if (valueStr.isEmpty())
					{
						return null;
					}

					if (lookupDataSource != null)
					{
						final LookupValue valueLookup = lookupDataSource.findById(valueStr);
						final T valueConv = convertToValueClass(fieldName, valueLookup, targetType, /* lookupDataSource */null);
						// TODO: what if valueConv was not found?
						return valueConv;
					}
				}
				else if (StringLookupValue.class == fromType)
				{
					final StringLookupValue stringLookupValue = (StringLookupValue)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)IntegerLookupValue.of(stringLookupValue);
					return valueConv;
				}
			}
			else if (StringLookupValue.class == targetType)
			{
				if (Map.class.isAssignableFrom(fromType))
				{
					@SuppressWarnings("unchecked")
					final Map<String, String> map = (Map<String, String>)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)JSONLookupValue.stringLookupValueFromJsonMap(map);
					return valueConv;
				}
				else if (String.class == fromType)
				{
					final String valueStr = (String)value;
					if (valueStr.isEmpty())
					{
						return null;
					}

					if (lookupDataSource != null)
					{
						final LookupValue valueLookup = lookupDataSource.findById(valueStr);
						final T valueConv = convertToValueClass(fieldName, valueLookup, targetType, /* lookupDataSource */null);
						// TODO: what if valueConv was not found?
						return valueConv;
					}
				}
				else if (IntegerLookupValue.class == fromType)
				{
					final IntegerLookupValue lookupValueInt = (IntegerLookupValue)value;
					@SuppressWarnings("unchecked")
					final T valueConv = (T)StringLookupValue.of(lookupValueInt.getIdAsString(), lookupValueInt.getDisplayName());
					return valueConv;
				}
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot convert " + fieldName + "'s value '" + value + "' (" + fromType + ") to " + targetType
					+ "\n LookupDataSource: " + lookupDataSource //
					, e);
		}

		throw new AdempiereException("Cannot convert " + fieldName + "'s value '" + value + "' (" + fromType + ") to " + targetType
				+ "\n LookupDataSource: " + lookupDataSource //
		);
	}

	/* package */List<IDocumentFieldCallout> getCallouts()
	{
		return callouts;
	}

	public static final class Builder
	{

		private DocumentFieldDescriptor _fieldBuilt;

		private String fieldName;
		private ITranslatableString caption;
		public String detailId;

		private boolean key = false;
		private boolean parentLink = false;
		private boolean virtualField;
		private boolean calculated;

		private DocumentFieldWidgetType widgetType;
		public Class<?> valueClass;

		private Optional<IExpression<?>> defaultValueExpression = Optional.empty();

		private final Set<Characteristic> characteristics = new TreeSet<>();
		private ILogicExpression readonlyLogic = ILogicExpression.FALSE;
		private boolean alwaysUpdateable;
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression mandatoryLogic = ILogicExpression.FALSE;

		private Optional<DocumentFieldDataBindingDescriptor> _dataBinding = Optional.empty();

		private final List<IDocumentFieldCallout> callouts = new ArrayList<>();


		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("name", fieldName)
					.add("detailId", detailId)
					.add("widgetType", widgetType)
					.add("characteristics", characteristics.isEmpty() ? null : characteristics)
					.toString();
		}

		public DocumentFieldDescriptor getOrBuild()
		{
			if (_fieldBuilt == null)
			{
				_fieldBuilt = new DocumentFieldDescriptor(this);
			}
			return _fieldBuilt;
		}

		private final void assertNotBuilt()
		{
			if (_fieldBuilt != null)
			{
				throw new IllegalStateException("Already built: " + this);
			}
		}

		public Builder setFieldName(final String fieldName)
		{
			assertNotBuilt();
			this.fieldName = fieldName;
			return this;
		}
		
		public Builder setCaption(final Map<String, String> captionTrls)
		{
			this.caption = ImmutableTranslatableString.ofMap(captionTrls);
			return this;
		}
		
		private ITranslatableString getCaption()
		{
			if (caption != null)
			{
				return caption;
			}
			
			return ImmutableTranslatableString.constant(fieldName);
		}

		public Builder setDetailId(final String detailId)
		{
			assertNotBuilt();
			this.detailId = Strings.emptyToNull(detailId);
			return this;
		}

		public Builder setKey(final boolean key)
		{
			assertNotBuilt();
			this.key = key;
			return this;
		}

		public Builder setParentLink(final boolean parentLink)
		{
			assertNotBuilt();
			this.parentLink = parentLink;
			return this;
		}

		public Builder setVirtualField(final boolean virtualField)
		{
			assertNotBuilt();
			this.virtualField = virtualField;
			return this;
		}

		public Builder setCalculated(final boolean calculated)
		{
			assertNotBuilt();
			this.calculated = calculated;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			assertNotBuilt();
			this.widgetType = widgetType;
			return this;
		}

		public Builder setValueClass(final Class<?> valueClass)
		{
			assertNotBuilt();
			this.valueClass = valueClass;
			return this;
		}

		public Builder setDefaultValueExpression(final Optional<IExpression<?>> defaultValueExpression)
		{
			assertNotBuilt();
			this.defaultValueExpression = Preconditions.checkNotNull(defaultValueExpression);
			return this;
		}

		public Builder setDefaultValueExpression(final IExpression<?> defaultValueExpression)
		{
			assertNotBuilt();
			this.defaultValueExpression = Optional.of(defaultValueExpression);
			return this;
		}

		public Builder addCharacteristic(final Characteristic c)
		{
			characteristics.add(c);
			return this;
		}

		public Builder addCharacteristicIfTrue(final boolean test, final Characteristic c)
		{
			if (test)
			{
				characteristics.add(c);
			}

			return this;
		}

		private Builder addCharacteristics(final Collection<Characteristic> characteristics)
		{
			characteristics.addAll(characteristics);
			return this;
		}

		public Builder setReadonlyLogic(final ILogicExpression readonlyLogic)
		{
			assertNotBuilt();
			this.readonlyLogic = Preconditions.checkNotNull(readonlyLogic);
			return this;
		}

		public Builder setAlwaysUpdateable(final boolean alwaysUpdateable)
		{
			assertNotBuilt();
			this.alwaysUpdateable = alwaysUpdateable;
			return this;
		}

		public Builder setDisplayLogic(final ILogicExpression displayLogic)
		{
			assertNotBuilt();
			this.displayLogic = Preconditions.checkNotNull(displayLogic);
			return this;
		}

		public Builder setMandatoryLogic(final ILogicExpression mandatoryLogic)
		{
			assertNotBuilt();
			this.mandatoryLogic = Preconditions.checkNotNull(mandatoryLogic);
			return this;
		}

		public Builder setDataBinding(final DocumentFieldDataBindingDescriptor dataBinding)
		{
			assertNotBuilt();
			_dataBinding = Optional.ofNullable(dataBinding);
			return this;
		}

		public Optional<DocumentFieldDataBindingDescriptor> getDataBinding()
		{
			return _dataBinding;
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			DocumentFieldDependencyMap.Builder dependencyMapBuilder = DocumentFieldDependencyMap.builder()
					.add(fieldName, readonlyLogic.getParameters(), DependencyType.ReadonlyLogic)
					.add(fieldName, displayLogic.getParameters(), DependencyType.DisplayLogic)
					.add(fieldName, mandatoryLogic.getParameters(), DependencyType.MandatoryLogic);

			final DocumentFieldDataBindingDescriptor dataBinding = getDataBinding().orElse(null);
			if (dataBinding != null)
			{
				dependencyMapBuilder.add(fieldName, dataBinding.getLookupValuesDependsOnFieldNames(), DependencyType.LookupValues);
			}

			return dependencyMapBuilder.build();
		}

		public Builder addCallout(final IDocumentFieldCallout callout)
		{
			Check.assumeNotNull(callout, "Parameter callout is not null");

			if (callouts.contains(callout))
			{
				logger.warn("Skip adding {} because it was already added to {}", callout, this);
				return this;
			}
			callouts.add(callout);
			return this;
		}

		/* package */List<IDocumentFieldCallout> getCallouts()
		{
			return callouts;
		}

	}
}
