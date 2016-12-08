package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nullable;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;

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
	public static final Builder builder(final String fieldName)
	{
		return new Builder(fieldName);
	}

	private static final Logger logger = LogManager.getLogger(DocumentFieldDescriptor.class);

	/** Internal field name (aka ColumnName) */
	private final String fieldName;
	private final ITranslatableString caption;
	/** Detail ID or null if this is a field in main sections */
	private final DetailId detailId;

	/** Is this the key field ? */
	private final boolean key;
	private final boolean parentLink;
	private final boolean virtualField;
	private final boolean calculated;

	private final DocumentFieldWidgetType widgetType;

	private final Class<?> valueClass;

	private final LookupDescriptorProvider lookupDescriptorProvider;

	private final Optional<IExpression<?>> defaultValueExpression;
	private final List<IDocumentFieldCallout> callouts;

	public static enum Characteristic
	{
		PublicField //
		, AdvancedField //
		, SideListField //
		, GridViewField //
		, AllowFiltering //
		//
		, SpecialField_DocumentNo //
		, SpecialField_DocStatus //
		, SpecialField_DocAction //
		, SpecialField_DocumentSummary //
		;

		public static final List<Characteristic> SPECIALFIELDS = ImmutableList.of(SpecialField_DocumentNo, SpecialField_DocStatus, SpecialField_DocAction, SpecialField_DocumentSummary);
	};

	private final Set<Characteristic> characteristics;

	private final ILogicExpression readonlyLogic;
	private final boolean alwaysUpdateable;
	private final ILogicExpression displayLogic;
	private final ILogicExpression mandatoryLogic;

	private final Optional<DocumentFieldDataBindingDescriptor> dataBinding;

	private final DocumentFieldDependencyMap dependencies;

	private DocumentFieldDescriptor(final Builder builder)
	{
		super();
		fieldName = Preconditions.checkNotNull(builder.fieldName, "name is null");
		caption = builder.getCaption();
		detailId = builder.getDetailId();

		key = builder.isKey();
		parentLink = builder.parentLink;
		virtualField = builder.virtualField;
		calculated = builder.calculated;

		widgetType = builder.getWidgetType();
		valueClass = builder.getValueClass();

		lookupDescriptorProvider = builder.getLookupDescriptorProvider();

		defaultValueExpression = Preconditions.checkNotNull(builder.defaultValueExpression, "defaultValueExpression not null");

		characteristics = Sets.immutableEnumSet(builder.characteristics);
		readonlyLogic = builder.getReadonlyLogicEffective();
		alwaysUpdateable = builder.alwaysUpdateable;
		displayLogic = builder.displayLogic;
		mandatoryLogic = builder.getMandatoryLogicEffective();

		dataBinding = builder.getDataBinding();

		dependencies = builder.buildDependencies();

		callouts = ImmutableList.copyOf(builder.callouts);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("fieldName", fieldName)
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

	public DetailId getDetailId()
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

	public LookupDescriptor getLookupDescriptor(final LookupScope scope)
	{
		return lookupDescriptorProvider.provideForScope(scope);
	}

	public LookupSource getLookupSourceType()
	{
		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupScope.DocumentField);
		return lookupDescriptor == null ? null : lookupDescriptor.getLookupSourceType();
	}

	@Nullable
	public LookupDataSource createLookupDataSource(final LookupScope scope)
	{
		final LookupDescriptor lookupDescriptor = getLookupDescriptor(scope);
		if (lookupDescriptor == null)
		{
			return null;
		}

		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);

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

	public <T extends DocumentFieldDataBindingDescriptor> T getDataBindingNotNull(final Class<T> bindingClass)
	{
		@SuppressWarnings("unchecked")
		final T dataBindingCasted = (T)dataBinding.orElseThrow(() -> new IllegalStateException("No databinding defined for " + this));
		return dataBindingCasted;
	}

	public DocumentFieldDependencyMap getDependencies()
	{
		return dependencies;
	}

	public Object convertToValueClass(final Object value, final LookupValueByIdSupplier lookupDataSource)
	{
		return convertToValueClass(fieldName, value, valueClass, lookupDataSource);
	}

	/**
	 * Converts given value to target class.
	 *
	 * @param value value to be converted
	 * @param targetType target type
	 * @param lookupDataSource optional Lookup data source, if needed
	 * @return converted value
	 */
	public <T> T convertToValueClass(final Object value, final Class<T> targetType, final LookupValueByIdSupplier lookupDataSource)
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
	public static <T> T convertToValueClass(final String fieldName, final Object value, final Class<T> targetType, final LookupValueByIdSupplier lookupDataSource)
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
				else if (value instanceof LookupValue)
				{
					@SuppressWarnings("unchecked")
					final T valueConv = (T)(Integer)((LookupValue)value).getIdAsInt();
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
				else if (Number.class.isAssignableFrom(fromType))
				{
					final int valueInt = ((Number)value).intValue();
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
			throw new AdempiereException("Failed converting " + fieldName + "'s value '" + value + "' (" + fromType + ") to " + targetType
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

	/**
	 * Builder
	 */
	public static final class Builder
	{
		/** Logic expression which evaluates as <code>true</code> when IsActive flag exists but it's <code>false</code> */
		private static final ILogicExpression LOGICEXPRESSION_NotActive;
		/** Logic expression which evaluates as <code>true</code> when Processed flag exists and it's <code>true</code> */
		private static final ILogicExpression LOGICEXPRESSION_Processed;

		static
		{
			final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
			LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + WindowConstants.FIELDNAME_IsActive + "/Y@=N", ILogicExpression.class);
			LOGICEXPRESSION_Processed = expressionFactory.compile("@" + WindowConstants.FIELDNAME_Processed + "/N@=Y | @" + WindowConstants.FIELDNAME_Processing + "/N@=Y", ILogicExpression.class);
		}

		private DocumentFieldDescriptor _fieldBuilt;

		private final String fieldName;
		private ITranslatableString caption;
		private ITranslatableString description;
		public DetailId _detailId;

		private boolean key = false;
		private boolean parentLink = false;
		private boolean virtualField;
		private boolean calculated;

		private DocumentFieldWidgetType _widgetType;
		public Class<?> _valueClass;

		// Lookup
		private LookupDescriptorProvider lookupDescriptorProvider = LookupDescriptorProvider.NULL;

		private Optional<IExpression<?>> defaultValueExpression = Optional.empty();

		private final Set<Characteristic> characteristics = new TreeSet<>();
		private ILogicExpression _entityReadonlyLogic = ILogicExpression.FALSE;
		private ILogicExpression _readonlyLogic = ILogicExpression.FALSE;
		private ILogicExpression _readonlyLogicEffective = null;
		private boolean alwaysUpdateable;
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression _mandatoryLogic = ILogicExpression.FALSE;
		private ILogicExpression _mandatoryLogicEffective = null;

		private Optional<DocumentFieldDataBindingDescriptor> _dataBinding = Optional.empty();

		private final List<IDocumentFieldCallout> callouts = new ArrayList<>();

		private Builder(final String fieldName)
		{
			super();
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			this.fieldName = fieldName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("name", fieldName)
					.add("detailId", _detailId)
					.add("widgetType", _widgetType)
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

		public String getFieldName()
		{
			return fieldName;
		}

		public Builder setCaption(final Map<String, String> captionTrls, final String defaultCaption)
		{
			caption = ImmutableTranslatableString.ofMap(captionTrls, defaultCaption);
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = ImmutableTranslatableString.constant(caption);
			return this;
		}

		public ITranslatableString getCaption()
		{
			if (caption == null)
			{
				return ImmutableTranslatableString.constant(fieldName);
			}

			return caption;
		}

		public Builder setDescription(final Map<String, String> descriptionTrls, final String defaultDescription)
		{
			description = ImmutableTranslatableString.ofMap(descriptionTrls, defaultDescription);
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setDescription(final String description)
		{
			this.description = ImmutableTranslatableString.constant(description);
			return this;
		}

		public ITranslatableString getDescription()
		{
			if (description == null)
			{
				return ImmutableTranslatableString.constant("");
			}
			return description;
		}

		/* package */Builder setDetailId(final DetailId detailId)
		{
			assertNotBuilt();
			_detailId = detailId;
			return this;
		}

		private DetailId getDetailId()
		{
			return _detailId;
		}

		public Builder setKey(final boolean key)
		{
			assertNotBuilt();
			this.key = key;
			return this;
		}

		public boolean isKey()
		{
			return key;
		}

		public Builder setParentLink(final boolean parentLink)
		{
			assertNotBuilt();
			this.parentLink = parentLink;
			return this;
		}

		public boolean isParentLink()
		{
			return parentLink;
		}

		public Builder setVirtualField(final boolean virtualField)
		{
			assertNotBuilt();
			this.virtualField = virtualField;
			return this;
		}

		public boolean isVirtualField()
		{
			return virtualField;
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
			this._widgetType = widgetType;
			return this;
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			Preconditions.checkNotNull(_widgetType, "widgetType is null");
			return _widgetType;
		}

		public Builder setLookupDescriptorProvider(final LookupDescriptorProvider lookupDescriptorProvider)
		{
			Check.assumeNotNull(lookupDescriptorProvider, "Parameter lookupDescriptorProvider is not null");
			this.lookupDescriptorProvider = lookupDescriptorProvider;
			return this;
		}

		public Builder setLookupDescriptorProvider(final LookupDescriptor lookupDescriptor)
		{
			setLookupDescriptorProvider(LookupDescriptorProvider.singleton(lookupDescriptor));
			return this;
		}

		public Builder setLookupDescriptorProvider_None()
		{
			setLookupDescriptorProvider(LookupDescriptorProvider.NULL);
			return this;
		}

		private LookupDescriptorProvider getLookupDescriptorProvider()
		{
			return lookupDescriptorProvider;
		}

		public LookupSource getLookupSourceType()
		{
			final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.provideForScope(LookupScope.DocumentField);
			return lookupDescriptor == null ? null : lookupDescriptor.getLookupSourceType();
		}

		public Builder setValueClass(final Class<?> valueClass)
		{
			assertNotBuilt();
			this._valueClass = valueClass;
			return this;
		}
		
		private Class<?> getValueClass()
		{
			if(_valueClass != null)
			{
				return _valueClass;
			}
			
			final DocumentFieldWidgetType widgetType = getWidgetType();
			return widgetType.getValueClass();
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
			assertNotBuilt();
			characteristics.add(c);
			return this;
		}

		public boolean hasCharacteristic(final Characteristic c)
		{
			return characteristics.contains(c);
		}

		public Builder addCharacteristicIfTrue(final boolean test, final Characteristic c)
		{
			if (test)
			{
				addCharacteristic(c);
			}

			return this;
		}

		public boolean isSpecialField()
		{
			return !Collections.disjoint(characteristics, Characteristic.SPECIALFIELDS);
		}

		/* package */ void setEntityReadonlyLogic(final ILogicExpression entityReadonlyLogic)
		{
			_entityReadonlyLogic = entityReadonlyLogic;
		}

		private ILogicExpression getEntityReadonlyLogic()
		{
			return _entityReadonlyLogic;
		}

		public Builder setReadonlyLogic(final ILogicExpression readonlyLogic)
		{
			assertNotBuilt();
			_readonlyLogic = Preconditions.checkNotNull(readonlyLogic);
			return this;
		}

		public Builder setReadonlyLogic(final boolean readonly)
		{
			setReadonlyLogic(ConstantLogicExpression.of(readonly));
			return this;
		}

		public ILogicExpression getReadonlyLogic()
		{
			return _readonlyLogic;
		}

		private ILogicExpression getReadonlyLogicEffective()
		{
			if (_readonlyLogicEffective == null)
			{
				_readonlyLogicEffective = buildReadonlyLogicEffective();
			}
			return _readonlyLogicEffective;
		}

		private ILogicExpression buildReadonlyLogicEffective()
		{
			if (isParentLink())
			{
				return ILogicExpression.TRUE;
			}

			if (isVirtualField())
			{
				return ILogicExpression.TRUE;
			}

			if (isKey())
			{
				return ILogicExpression.TRUE;
			}

			// If the tab is always readonly, we can assume any field in that tab is readonly
			final ILogicExpression entityReadonlyLogic = getEntityReadonlyLogic();
			if (entityReadonlyLogic.isConstantTrue())
			{
				return ILogicExpression.TRUE;
			}

			// Case: DocumentNo special field not be readonly
			if (hasCharacteristic(Characteristic.SpecialField_DocumentNo))
			{
				return LOGICEXPRESSION_NotActive.or(LOGICEXPRESSION_Processed);
			}

			// Case: DocAction
			if (hasCharacteristic(Characteristic.SpecialField_DocAction))
			{
				return ILogicExpression.FALSE;
			}

			final ILogicExpression fieldReadonlyLogic = getReadonlyLogic();
			if (fieldReadonlyLogic.isConstantTrue())
			{
				return ILogicExpression.TRUE;
			}

			final String fieldName = getFieldName();
			if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(fieldName))
			{
				// NOTE: from UI perspective those are readonly (i.e. it will be managed by persistence layer)
				return ILogicExpression.TRUE;
			}

			if (hasCharacteristic(Characteristic.SpecialField_DocStatus))
			{
				// NOTE: DocStatus field shall always be readonly
				return ILogicExpression.TRUE;
			}

			ILogicExpression readonlyLogic = fieldReadonlyLogic;
			// FIXME: not sure if using tabReadonlyLogic here is OK, because the tab logic shall be applied to parent tab!
			if (!entityReadonlyLogic.isConstantFalse())
			{
				readonlyLogic = entityReadonlyLogic.or(fieldReadonlyLogic);
			}

			//
			// Consider field readonly if the row is not active
			// .. and this property is not the IsActive flag.
			if (!WindowConstants.FIELDNAME_IsActive.equals(fieldName))
			{
				readonlyLogic = LOGICEXPRESSION_NotActive.or(readonlyLogic);
			}

			//
			// Consider field readonly if the row is processed.
			// In case we deal with an AlwaysUpdateable field, this logic do not apply.
			final boolean alwaysUpdateable = isAlwaysUpdateable();
			if (!alwaysUpdateable)
			{
				readonlyLogic = LOGICEXPRESSION_Processed.or(readonlyLogic);
			}

			return readonlyLogic;
		}

		public Builder setAlwaysUpdateable(final boolean alwaysUpdateable)
		{
			assertNotBuilt();
			this.alwaysUpdateable = alwaysUpdateable;
			return this;
		}

		public boolean isAlwaysUpdateable()
		{
			return alwaysUpdateable;
		}

		public Builder setDisplayLogic(final ILogicExpression displayLogic)
		{
			assertNotBuilt();
			this.displayLogic = Preconditions.checkNotNull(displayLogic);
			return this;
		}

		public Builder setDisplayLogic(final boolean display)
		{
			setDisplayLogic(ConstantLogicExpression.of(display));
			return this;
		}

		public ILogicExpression getDisplayLogic()
		{
			return displayLogic;
		}

		public boolean isPossiblePublicField()
		{
			// Always publish the key columns, else the client won't know what to talk about ;)
			if (isKey())
			{
				return true;
			}

			// If display logic is not constant then we don't know if this field will be ever visible
			// so we are publishing it
			if (!displayLogic.isConstant())
			{
				return true;
			}

			// Publish this field only if it's displayed
			return displayLogic.isConstantTrue();
		}

		public Builder setMandatoryLogic(final ILogicExpression mandatoryLogic)
		{
			assertNotBuilt();
			_mandatoryLogic = Preconditions.checkNotNull(mandatoryLogic);
			return this;
		}

		public Builder setMandatoryLogic(final boolean mandatory)
		{
			setMandatoryLogic(ConstantLogicExpression.of(mandatory));
			return this;
		}

		public ILogicExpression getMandatoryLogic()
		{
			return _mandatoryLogic;
		}

		private ILogicExpression getMandatoryLogicEffective()
		{
			if (_mandatoryLogicEffective == null)
			{
				_mandatoryLogicEffective = buildMandatoryLogicEffective();
			}
			return _mandatoryLogicEffective;
		}

		private final ILogicExpression buildMandatoryLogicEffective()
		{
			if (isParentLink())
			{
				return ILogicExpression.TRUE;
			}

			final String fieldName = getFieldName();
			if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(fieldName))
			{
				// NOTE: from UI perspective those are not mandatory (i.e. it will be managed by persistence layer)
				return ILogicExpression.FALSE;
			}

			if (isVirtualField())
			{
				return ILogicExpression.FALSE;
			}

			// FIXME: hardcoded M_AttributeSetInstance_ID mandatory logic = false
			// Reason: even if we set it's default value to "0" some callouts are setting it to NULL,
			// and then the document saving API is failing because it considers this column as NOT filled.
			if (WindowConstants.FIELDNAME_M_AttributeSetInstance_ID.equals(fieldName))
			{
				return ILogicExpression.FALSE;
			}

			// Corner case:
			// e.g. C_Order.M_Shipper_ID has AD_Field.IsMandatory=Y, AD_Field.IsDisplayed=N, AD_Column.IsMandatory=N
			// => we need to NOT enforce setting it because it's not needed, user cannot change it and it might be no callouts to set it.
			// Else, we won't be able to save our document.
			final boolean publicField = hasCharacteristic(Characteristic.PublicField);
			final ILogicExpression mandatoryLogic = getMandatoryLogic();
			final boolean mandatory = mandatoryLogic.isConstantTrue();
			final DocumentFieldDataBindingDescriptor fieldDataBinding = getDataBinding().orElse(null);
			final boolean mandatoryDB = fieldDataBinding != null && fieldDataBinding.isMandatory();
			if (!publicField && mandatory && !mandatoryDB)
			{
				return ILogicExpression.FALSE;
			}

			// Case: DocumentNo special field shall always be mandatory
			if (hasCharacteristic(Characteristic.SpecialField_DocumentNo))
			{
				return ILogicExpression.TRUE;
			}

			if (mandatory)
			{
				return ILogicExpression.TRUE;
			}

			return mandatoryLogic;
		}

		public Builder setDataBinding(final DocumentFieldDataBindingDescriptor dataBinding)
		{
			assertNotBuilt();
			_dataBinding = Optional.ofNullable(dataBinding);
			return this;
		}

		private Optional<DocumentFieldDataBindingDescriptor> getDataBinding()
		{
			return _dataBinding;
		}

		private DocumentFieldDependencyMap buildDependencies()
		{
			final DocumentFieldDependencyMap.Builder dependencyMapBuilder = DocumentFieldDependencyMap.builder()
					.add(fieldName, getReadonlyLogicEffective().getParameters(), DependencyType.ReadonlyLogic)
					.add(fieldName, getDisplayLogic().getParameters(), DependencyType.DisplayLogic)
					.add(fieldName, getMandatoryLogicEffective().getParameters(), DependencyType.MandatoryLogic);

			final LookupDescriptor lookupDescriptor = getLookupDescriptorProvider().provideForScope(LookupScope.DocumentField);
			if (lookupDescriptor != null)
			{
				dependencyMapBuilder.add(fieldName, lookupDescriptor.getDependsOnFieldNames(), DependencyType.LookupValues);
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
