package de.metas.ui.web.window.descriptor;

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
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.devices.providers.DeviceDescriptorsProvider;
import de.metas.ui.web.devices.providers.DeviceDescriptorsProviders;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDependencyMap.DependencyType;
import de.metas.ui.web.window.model.IDocumentFieldValueProvider;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

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

public final class DocumentFieldDescriptor
{
	public static Builder builder(final String fieldName)
	{
		return new Builder(fieldName);
	}

	private static final Logger logger = LogManager.getLogger(DocumentFieldDescriptor.class);

	/** Internal field name (aka ColumnName) */
	private final String fieldName;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	/** Detail ID or null if this is a field in main sections */
	private final DetailId detailId;

	/** Is this the key field ? */
	private final boolean key;
	private final boolean calculated;

	@Getter
	private final boolean parentLink;
	@Getter
	private final String parentLinkFieldName;

	private final DocumentFieldWidgetType widgetType;
	private final int fieldMaxLength;
	private final boolean allowShowPassword; // in case widgetType is Password
	private final ButtonFieldActionDescriptor buttonActionDescriptor;
	private final BarcodeScannerType barcodeScannerType;

	private final WidgetSize widgetSize;

	private final Class<?> valueClass;

	private final LookupDescriptorProvider lookupDescriptorProvider;
	private final boolean supportZoomInto;

	private final boolean virtualField;
	private final Optional<IDocumentFieldValueProvider> virtualFieldValueProvider;

	private final Optional<IExpression<?>> defaultValueExpression;
	private final ImmutableList<IDocumentFieldCallout> callouts;

	public enum Characteristic
	{
		PublicField //
		, AdvancedField //
		, SideListField //
		, GridViewField //
		//
		, SpecialField_DocumentNo //
		, SpecialField_DocStatus //
		, SpecialField_DocAction //
		// , SpecialField_DocumentSummary //
		;
	}

	private static final List<Characteristic> SPECIALFIELDS_ToExcludeFromLayout = ImmutableList.of(
			// Characteristic.SpecialField_DocumentNo // NOP, don't exclude it (see https://github.com/metasfresh/metasfresh-webui-api/issues/291 )
			Characteristic.SpecialField_DocStatus //
			, Characteristic.SpecialField_DocAction //
	// , SpecialField_DocumentSummary // NOP, don't exclude DocumentSummary because if it's layout it shall be editable at least when new (e.g. C_BPartner.Name)
	);

	private final Set<Characteristic> characteristics;

	private final ILogicExpression readonlyLogic;
	private final boolean alwaysUpdateable;
	private final ILogicExpression displayLogic;
	private final ILogicExpression mandatoryLogic;

	private final Optional<DocumentFieldDataBindingDescriptor> dataBinding;

	private final DocumentFieldDependencyMap dependencies;

	//
	// Default filtering options
	private final DocumentFieldDefaultFilterDescriptor defaultFilterInfo;

	@Getter
	private final DeviceDescriptorsProvider deviceDescriptorsProvider;

	private DocumentFieldDescriptor(final Builder builder)
	{
		fieldName = Preconditions.checkNotNull(builder.fieldName, "name is null");
		caption = builder.getCaption();
		description = builder.getDescription();
		detailId = builder.getDetailId();

		key = builder.isKey();
		calculated = builder.isCalculated();

		parentLink = builder.parentLink;
		parentLinkFieldName = builder.parentLinkFieldName;

		widgetType = builder.getWidgetType();
		fieldMaxLength = builder.getFieldMaxLength();

		widgetSize = builder.getWidgetSize();
		allowShowPassword = builder.isAllowShowPassword();
		buttonActionDescriptor = builder.getButtonActionDescriptor();
		barcodeScannerType = builder.getBarcodeScannerType();
		valueClass = builder.getValueClass();

		lookupDescriptorProvider = builder.getLookupDescriptorProvider();
		supportZoomInto = builder.isSupportZoomInto();

		defaultValueExpression = Preconditions.checkNotNull(builder.defaultValueExpression, "defaultValueExpression not null");

		virtualField = builder.isVirtualField();
		virtualFieldValueProvider = builder.getVirtualFieldValueProvider();

		characteristics = Sets.immutableEnumSet(builder.characteristics);
		readonlyLogic = builder.getReadonlyLogicEffective();
		alwaysUpdateable = builder.alwaysUpdateable;
		displayLogic = builder.displayLogic;
		mandatoryLogic = builder.getMandatoryLogicEffective();

		dataBinding = builder.getDataBinding();

		dependencies = builder.buildDependencies();

		callouts = builder.buildCallouts();

		//
		// Default filtering
		defaultFilterInfo = builder.defaultFilterInfo;

		deviceDescriptorsProvider = builder.getDeviceDescriptorsProvider();
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

	public ITranslatableString getDescription()
	{
		return description;
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public boolean isKey()
	{
		return key;
	}

	public boolean isVirtualField()
	{
		return virtualField;
	}

	public Optional<IDocumentFieldValueProvider> getVirtualFieldValueProvider()
	{
		return virtualFieldValueProvider;
	}

	public boolean isCalculated()
	{
		return calculated;
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	public int getFieldMaxLength()
	{
		return fieldMaxLength;
	}

	public WidgetSize getWidgetSize()
	{
		return widgetSize;
	}

	public boolean isAllowShowPassword()
	{
		return allowShowPassword;
	}

	public BarcodeScannerType getBarcodeScannerType()
	{
		return barcodeScannerType;
	}

	public ButtonFieldActionDescriptor getButtonActionDescriptor()
	{
		return buttonActionDescriptor;
	}

	public Class<?> getValueClass()
	{
		return valueClass;
	}

	public boolean isSupportZoomInto()
	{
		return supportZoomInto;
	}

	public Optional<LookupDescriptor> getLookupDescriptor()
	{
		return lookupDescriptorProvider.provide();
	}

	public Optional<LookupDescriptor> getLookupDescriptorForFiltering()
	{
		return lookupDescriptorProvider.provideForFilter();
	}

	public Optional<LookupDataSource> createLookupDataSource()
	{
		return getLookupDescriptor()
				.map(LookupDataSourceFactory.instance::getLookupDataSource);
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

	public Object convertToValueClass(@Nullable final Object value, @Nullable final LookupValueByIdSupplier lookupDataSource)
	{
		return DataTypes.convertToValueClass(fieldName, value, widgetType, valueClass, lookupDataSource);
	}

	/**
	 * Converts given value to target class.
	 *
	 * @param value value to be converted
	 * @param targetType target type
	 * @param widgetType optional widget type
	 * @param lookupDataSource optional Lookup data source, if needed
	 * @return converted value
	 */
	public <T> T convertToValueClass(
			@Nullable final Object value,
			@Nullable final DocumentFieldWidgetType widgetType,
			@NonNull final Class<T> targetType,
			@Nullable final LookupValueByIdSupplier lookupDataSource)
	{
		return DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);
	}

	/* package */List<IDocumentFieldCallout> getCallouts()
	{
		return callouts;
	}

	public boolean hasFileringInfo()
	{
		return defaultFilterInfo != null;
	}

	public DocumentFieldDefaultFilterDescriptor getDefaultFilterInfo()
	{
		return defaultFilterInfo;
	}

	/**
	 * Builder
	 */
	public static final class Builder
	{
		private DocumentFieldDescriptor _fieldBuilt;

		private final String fieldName;
		private ITranslatableString caption;
		private ITranslatableString description;
		public DetailId _detailId;

		private boolean key = false;
		private boolean parentLink = false;
		private String parentLinkFieldName;
		private boolean virtualField;
		private Optional<IDocumentFieldValueProvider> virtualFieldValueProvider = Optional.empty();
		private boolean calculated;

		private DocumentFieldWidgetType _widgetType;
		private WidgetSize _widgetSize;
		private int _fieldMaxLength;
		private Class<?> _valueClass;
		private boolean _allowShowPassword = false; // in case widgetType is Password
		private BarcodeScannerType _barcodeScannerType;

		// Lookup
		private LookupDescriptorProvider lookupDescriptorProvider = LookupDescriptorProviders.NULL;

		private Optional<IExpression<?>> defaultValueExpression = Optional.empty();

		private final Set<Characteristic> characteristics = new TreeSet<>();
		private ILogicExpression _entityReadonlyLogic = ConstantLogicExpression.FALSE;
		private ILogicExpression _readonlyLogic = ConstantLogicExpression.FALSE;
		private ILogicExpression _readonlyLogicEffective = null;

		private boolean alwaysUpdateable = false;
		private ILogicExpression displayLogic = ConstantLogicExpression.TRUE;
		private ILogicExpression _mandatoryLogic = ConstantLogicExpression.FALSE;
		private ILogicExpression _mandatoryLogicEffective = null;

		private Optional<DocumentFieldDataBindingDescriptor> _dataBinding = Optional.empty();

		private final List<IDocumentFieldCallout> callouts = new ArrayList<>();

		private ButtonFieldActionDescriptor buttonActionDescriptor = null;

		/** See {@link #setTooltipIconName(String)}. */
		@Getter
		private String tooltipIconName = null;

		//
		// Default filtering options
		private DocumentFieldDefaultFilterDescriptor defaultFilterInfo = null;

		private DeviceDescriptorsProvider deviceDescriptorsProvider = DeviceDescriptorsProviders.empty();

		private Builder(final String fieldName)
		{
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

		private void assertNotBuilt()
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
			caption = TranslatableStrings.ofMap(captionTrls, defaultCaption);
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = TranslatableStrings.constant(caption);
			return this;
		}

		public ITranslatableString getCaption()
		{
			if (caption == null)
			{
				return TranslatableStrings.constant(fieldName);
			}

			return caption;
		}

		public Builder setDescription(final Map<String, String> descriptionTrls, final String defaultDescription)
		{
			description = TranslatableStrings.ofMap(descriptionTrls, defaultDescription);
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setDescription(final String description)
		{
			this.description = TranslatableStrings.constant(description);
			return this;
		}

		public ITranslatableString getDescription()
		{
			if (description == null)
			{
				return TranslatableStrings.empty();
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

		/**
		 * @return true if included entity (i.e. detail tab)
		 */
		private boolean isDetail()
		{
			return getDetailId() != null;
		}

		public Builder setKey(final boolean key)
		{
			assertNotBuilt();
			this.key = key;
			return this;
		}

		boolean isKey()
		{
			return key;
		}

		public Builder setParentLink(final boolean parentLink, final String parentLinkFieldName)
		{
			assertNotBuilt();
			this.parentLink = parentLink;
			this.parentLinkFieldName = parentLinkFieldName;
			return this;
		}

		private boolean isParentLinkEffective()
		{
			return parentLink && isDetail();
		}

		public Builder setVirtualField(final boolean virtualField)
		{
			assertNotBuilt();
			this.virtualField = virtualField;
			virtualFieldValueProvider = Optional.empty();
			return this;
		}

		public Builder setVirtualField(@NonNull final IDocumentFieldValueProvider virtualFieldValueProvider)
		{
			assertNotBuilt();
			virtualField = true;
			this.virtualFieldValueProvider = Optional.of(virtualFieldValueProvider);
			return this;
		}

		public boolean isVirtualField()
		{
			return virtualField;
		}

		private Optional<IDocumentFieldValueProvider> getVirtualFieldValueProvider()
		{
			return virtualFieldValueProvider;
		}

		public Builder setCalculated(final boolean calculated)
		{
			assertNotBuilt();
			this.calculated = calculated;
			return this;
		}

		private boolean isCalculated()
		{
			if (isVirtualField())
			{
				return true;
			}
			return calculated;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			assertNotBuilt();
			_widgetType = widgetType;
			return this;
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			Preconditions.checkNotNull(_widgetType, "widgetType is null");
			return _widgetType;
		}

		public WidgetSize getWidgetSize()
		{
			return _widgetSize;
		}

		public Builder setFieldMaxLength(final int fieldMaxLength)
		{
			this._fieldMaxLength = fieldMaxLength;
			return this;
		}

		public int getFieldMaxLength()
		{
			return _fieldMaxLength;
		}

		public Builder setAllowShowPassword(final boolean allowShowPassword)
		{
			this._allowShowPassword = allowShowPassword;
			return this;
		}

		private boolean isAllowShowPassword()
		{
			return _allowShowPassword;
		}

		public Builder barcodeScannerType(final BarcodeScannerType barcodeScannerType)
		{
			this._barcodeScannerType = barcodeScannerType;
			return this;
		}

		private BarcodeScannerType getBarcodeScannerType()
		{
			return _barcodeScannerType;
		}

		public Builder setLookupDescriptorProvider(@NonNull final LookupDescriptorProvider lookupDescriptorProvider)
		{
			this.lookupDescriptorProvider = lookupDescriptorProvider;
			return this;
		}

		public Builder setLookupDescriptorProvider(@Nullable final LookupDescriptor lookupDescriptor)
		{
			setLookupDescriptorProvider(LookupDescriptorProviders.ofNullableInstance(lookupDescriptor));
			return this;
		}

		public Builder setLookupDescriptorProvider_None()
		{
			setLookupDescriptorProvider(LookupDescriptorProviders.NULL);
			return this;
		}

		private LookupDescriptorProvider getLookupDescriptorProvider()
		{
			return lookupDescriptorProvider;
		}

		public Optional<LookupDescriptor> getLookupDescriptor()
		{
			return getLookupDescriptorProvider().provide();
		}

		public Optional<String> getLookupTableName()
		{
			return getLookupDescriptorProvider().getTableName();
		}

		public Builder setValueClass(final Class<?> valueClass)
		{
			assertNotBuilt();
			_valueClass = valueClass;
			return this;
		}

		private Class<?> getValueClass()
		{
			if (_valueClass != null)
			{
				return _valueClass;
			}

			final DocumentFieldWidgetType widgetType = getWidgetType();
			if (widgetType.getValueClassOrNull() != null)
			{
				return widgetType.getValueClassOrNull();
			}

			final LookupDescriptorProvider lookupDescriptor = getLookupDescriptorProvider();
			if (lookupDescriptor != null)
			{
				return lookupDescriptor.isNumericKey() ? IntegerLookupValue.class : StringLookupValue.class;
			}

			throw new AdempiereException("valueClass is unknown for " + this);
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

		public Builder removeCharacteristic(final Characteristic c)
		{
			assertNotBuilt();
			characteristics.remove(c);
			return this;
		}

		public boolean isSpecialFieldToExcludeFromLayout()
		{
			return !Collections.disjoint(characteristics, SPECIALFIELDS_ToExcludeFromLayout);
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

		private ILogicExpression getReadonlyLogic()
		{
			return _readonlyLogic;
		}

		public ILogicExpression getReadonlyLogicEffective()
		{
			if (_readonlyLogicEffective == null)
			{
				_readonlyLogicEffective = buildReadonlyLogicEffective();
			}
			return _readonlyLogicEffective;
		}

		private ILogicExpression buildReadonlyLogicEffective()
		{
			if (isParentLinkEffective())
			{
				return ConstantLogicExpression.TRUE;
			}

			if (isVirtualField())
			{
				return ConstantLogicExpression.TRUE;
			}

			if (isKey())
			{
				return ConstantLogicExpression.TRUE;
			}

			// If the tab is always readonly, we can assume any field in that tab is readonly
			final ILogicExpression entityReadonlyLogic = getEntityReadonlyLogic();
			if (entityReadonlyLogic.isConstantTrue())
			{
				return ConstantLogicExpression.TRUE;
			}

			// Case: DocAction
			if (hasCharacteristic(Characteristic.SpecialField_DocAction))
			{
				return ConstantLogicExpression.FALSE;
			}

			final ILogicExpression fieldReadonlyLogic = getReadonlyLogic();
			if (fieldReadonlyLogic.isConstantTrue())
			{
				return ConstantLogicExpression.TRUE;
			}

			final String fieldName = getFieldName();
			if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(fieldName))
			{
				// NOTE: from UI perspective those are readonly (i.e. it will be managed by persistence layer)
				return ConstantLogicExpression.TRUE;
			}

			if (hasCharacteristic(Characteristic.SpecialField_DocStatus))
			{
				// NOTE: DocStatus field shall always be readonly
				return ConstantLogicExpression.TRUE;
			}

			ILogicExpression readonlyLogic = fieldReadonlyLogic;
			// FIXME: not sure if using tabReadonlyLogic here is OK, because the tab logic shall be applied to parent tab!
			if (!entityReadonlyLogic.isConstantFalse())
			{
				readonlyLogic = entityReadonlyLogic.or(fieldReadonlyLogic);
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

		public Builder setDisplayLogic(final String displayLogic)
		{
			setDisplayLogic(LogicExpressionCompiler.instance.compile(displayLogic));
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

		private ILogicExpression getMandatoryLogicEffective()
		{
			if (_mandatoryLogicEffective == null)
			{
				_mandatoryLogicEffective = buildMandatoryLogicEffective();
			}
			return _mandatoryLogicEffective;
		}

		private ILogicExpression buildMandatoryLogicEffective()
		{
			if (isParentLinkEffective())
			{
				return ConstantLogicExpression.TRUE;
			}

			final String fieldName = getFieldName();
			if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(fieldName))
			{
				// NOTE: from UI perspective those are not mandatory (i.e. it will be managed by persistence layer)
				return ConstantLogicExpression.FALSE;
			}

			if (isVirtualField())
			{
				return ConstantLogicExpression.FALSE;
			}

			// FIXME: hardcoded M_AttributeSetInstance_ID mandatory logic = false
			// Reason: even if we set it's default value to "0" some callouts are setting it to NULL,
			// and then the document saving API is failing because it considers this column as NOT filled.
			if (WindowConstants.FIELDNAME_M_AttributeSetInstance_ID.equals(fieldName))
			{
				return ConstantLogicExpression.FALSE;
			}

			// Corner case:
			// e.g. C_Order.M_Shipper_ID has AD_Field.IsMandatory=Y, AD_Field.IsDisplayed=N, AD_Column.IsMandatory=N
			// => we need to NOT enforce setting it because it's not needed, user cannot change it and it might be no callouts to set it.
			// Else, we won't be able to save our document.
			final boolean publicField = hasCharacteristic(Characteristic.PublicField);
			final ILogicExpression mandatoryLogic = _mandatoryLogic;
			final boolean mandatory = mandatoryLogic.isConstantTrue();
			final DocumentFieldDataBindingDescriptor fieldDataBinding = getDataBinding().orElse(null);
			final boolean mandatoryDB = fieldDataBinding != null && fieldDataBinding.isMandatory();
			if (!publicField && mandatory && !mandatoryDB)
			{
				return ConstantLogicExpression.FALSE;
			}

			// Case: DocumentNo special field shall always be mandatory
			if (hasCharacteristic(Characteristic.SpecialField_DocumentNo))
			{
				return ConstantLogicExpression.TRUE;
			}

			if (mandatory)
			{
				return ConstantLogicExpression.TRUE;
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
					.add(fieldName, getReadonlyLogicEffective().getParameterNames(), DependencyType.ReadonlyLogic)
					.add(fieldName, getDisplayLogic().getParameterNames(), DependencyType.DisplayLogic)
					.add(fieldName, getMandatoryLogicEffective().getParameterNames(), DependencyType.MandatoryLogic);

			final LookupDescriptor lookupDescriptor = getLookupDescriptorProvider().provide().orElse(null);
			if (lookupDescriptor != null)
			{
				dependencyMapBuilder.add(fieldName, lookupDescriptor.getDependsOnFieldNames(), DependencyType.LookupValues);
			}

			final IDocumentFieldValueProvider virtualFieldValueProvider = getVirtualFieldValueProvider().orElse(null);
			if (virtualFieldValueProvider != null)
			{
				dependencyMapBuilder.add(fieldName, virtualFieldValueProvider.getDependsOnFieldNames(), DependencyType.FieldValue);
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

		public Builder addCallout(final ILambdaDocumentFieldCallout lambdaCallout)
		{
			final LambdaDocumentFieldCallout callout = new LambdaDocumentFieldCallout(getFieldName(), lambdaCallout);
			addCallout(callout);
			return this;
		}

		private ImmutableList<IDocumentFieldCallout> buildCallouts()
		{
			return ImmutableList.copyOf(callouts);
		}

		public Builder setButtonActionDescriptor(final ButtonFieldActionDescriptor buttonActionDescriptor)
		{
			this.buttonActionDescriptor = buttonActionDescriptor;
			return this;
		}

		public ButtonFieldActionDescriptor getButtonActionDescriptor()
		{
			return buttonActionDescriptor;
		}

		public boolean isSupportZoomInto()
		{
			// Allow zooming into key column. It shall open precisely this record in a new window
			// (see https://github.com/metasfresh/metasfresh/issues/1687 to understand the use-case)
			// In future we shall think to narrow it down only to included tabs and only for those tables which also have a window where they are the header document.
			if (isKey())
			{
				return true;
			}

			final DocumentFieldWidgetType widgetType = getWidgetType();
			if (!widgetType.isSupportZoomInto())
			{
				return false;
			}

			final Class<?> valueClass = getValueClass();
			if (StringLookupValue.class.isAssignableFrom(valueClass))
			{
				return false;
			}

			final String lookupTableName = getLookupTableName().orElse(null);
			if (WindowConstants.TABLENAME_AD_Ref_List.equals(lookupTableName))
			{
				return false;
			}

			return true;
		}

		public Builder setDefaultFilterInfo(DocumentFieldDefaultFilterDescriptor defaultFilterInfo)
		{
			this.defaultFilterInfo = defaultFilterInfo;
			return this;
		}

		/**
		 * Setting this to a non-{@code null} value means that this field is a tooltip field,
		 * i.e. it represents a tooltip that is attached to some other field.
		 */
		public Builder setTooltipIconName(@Nullable final String tooltipIconName)
		{
			this.tooltipIconName = tooltipIconName;
			return this;
		}

		/**
		 * @return true if this field has ORDER BY instructions
		 */
		public boolean isDefaultOrderBy()
		{
			final DocumentFieldDataBindingDescriptor dataBinding = getDataBinding().orElse(null);
			return dataBinding != null ? dataBinding.isDefaultOrderBy() : false;
		}

		public int getDefaultOrderByPriority()
		{
			// we assume isDefaultOrderBy() was checked before calling this method
			return getDataBinding().get().getDefaultOrderByPriority();
		}

		public boolean isDefaultOrderByAscending()
		{
			// we assume isDefaultOrderBy() was checked before calling this method
			return getDataBinding().get().isDefaultOrderByAscending();
		}

		public Builder deviceDescriptorsProvider(@NonNull final DeviceDescriptorsProvider deviceDescriptorsProvider)
		{
			this.deviceDescriptorsProvider = deviceDescriptorsProvider;
			return this;
		}

		private DeviceDescriptorsProvider getDeviceDescriptorsProvider()
		{
			return deviceDescriptorsProvider;
		}
	}
}
