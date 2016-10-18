package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.awt.im.spi.InputMethod;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.net.IHostIdentifier;
import org.adempiere.util.net.NetUtils;
import org.apache.commons.lang3.StringUtils;
import org.compiere.apps.AppsAction;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IInputMethod;
import de.metas.adempiere.form.terminal.AbstractPropertiesPanelModel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModelConfigurator;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.adempiere.form.terminal.field.constraint.MinMaxNumericFieldConstraint;
import de.metas.device.adempiere.IDeviceBL;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IWeightableBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageListener;
import de.metas.handlingunits.attribute.storage.impl.AttributeStorageListenerAdapter;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.logging.LogManager;

public class HUAttributeSetPropertiesModel extends AbstractPropertiesPanelModel
{
	private static final String ERR_MinValue = "de.metas.handlingunits.MinValueError";
	private static final String ERR_MaxValue = "de.metas.handlingunits.MaxValueError";

	private static final String PROP_CONFIG_AlwaysEditable = "AlwaysEditable";

	private IndexedAttributeStorage _indexedAttributeStorage = IndexedAttributeStorage.NULL;
	private final ReentrantLock _indexedAttributeStorageLock = new ReentrantLock();

	private boolean readonly = false; // always readonly flag
	private boolean attributesEditableOnlyIfVHU = false;

	private static final transient Logger logger = LogManager.getLogger(HUAttributeSetPropertiesModel.class);

	private final IAttributeStorageListener attributeStorageListener = new AttributeStorageListenerAdapter()
	{
		@Override
		public void onAttributeValueChanged(IAttributeValueContext attributeValueContext, IAttributeStorage storage, IAttributeValue attributeValue, Object valueOld)
		{
			final I_M_Attribute attribute = attributeValue.getM_Attribute();
			final String propertyName = attribute.getValue();
			firePropertyValueChanged(propertyName);
		}

		/**
		 * When underlying attribute storage is disposed, prevently remove it from this model.
		 */
		@Override
		public void onAttributeStorageDisposed(final IAttributeStorage storage)
		{
			logger.debug("Reseting the properties model because attribute storage was disposed: {}", HUAttributeSetPropertiesModel.this);

			// Make sure it makes sense to reset current storage
			final IAttributeStorage currentStorage = getIndexedAttributeStorage().getAttributeStorage();
			if (currentStorage == null)
			{
				return;
			}
			if (currentStorage != storage)
			{
				final HUException ex = new HUException("Got attribute storage disposed event on a storage on which we should not listen."
						+ "\n Expected storage: " + currentStorage
						+ "\n Actual storage that we got: " + storage
						+ "\n Properties model: " + HUAttributeSetPropertiesModel.this);
				logger.warn(ex.getLocalizedMessage(), ex);
				return;
			}

			// Actually reset current attribute storage and also fire events so the UI will know that it needs to reload.
			setAttributeStorage(null);
		};

		//@formatter:off
		@Override public String toString(){ return "HUAttributeSetPropertiesModel[<anonymous AttributeStorageListenerAdapter>]"; };
		//@formatter:on
	};

	public HUAttributeSetPropertiesModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		logger.debug("New instance: {}", this);
	}

	@Override
	public void dispose()
	{
		// Invoke super first to also make sure the isDisposed() flag is set.
		super.dispose();

		// Set underlying attribute storage to null.
		// This will unregister listeners from old attribute storage (if any).
		setAttributeStorage(null, false); // fireEvent=false
	}

	public final void setAttributeStorage(final IAttributeStorage attributeSet)
	{
		final boolean fireEvent = true;
		setAttributeStorage(attributeSet, fireEvent);
	}

	private final void setAttributeStorage(final IAttributeStorage attributeStorage, final boolean fireEvent)
	{
		_indexedAttributeStorageLock.lock();
		try
		{
			final IAttributeStorage attributeStorageOld = this._indexedAttributeStorage.getAttributeStorage();
			this._indexedAttributeStorage = IndexedAttributeStorage.of(attributeStorage);

			logger.debug("Attribute storage old: {}", attributeStorageOld);
			logger.debug("Attribute storage new: {}", attributeStorage);

			//
			// Remove listeners from old attribute storage
			if (attributeStorageOld != null)
			{
				attributeStorageOld.removeListener(attributeStorageListener);
			}

			//
			// Add listeners to new attribute storage
			if (attributeStorage != null)
			{
				attributeStorage.addListener(attributeStorageListener);
			}
		}
		finally
		{
			_indexedAttributeStorageLock.unlock();
		}

		// Notify listeners that our model content has fully changed
		// NOTE: we do this outside of the lock because the listeners would access this object's content.
		if (fireEvent)
		{
			fireContentChanged();
		}
	}

	/**
	 * Gets underlying indexed attribute storage.
	 *
	 * IMPORTANT: make sure you have ONLY one call of this method, in each method you are using it!!!
	 *
	 * @return
	 */
	private IndexedAttributeStorage getIndexedAttributeStorage()
	{
		_indexedAttributeStorageLock.lock();
		try
		{
			return _indexedAttributeStorage;
		}
		finally
		{
			_indexedAttributeStorageLock.unlock();
		}
	}

	/**
	 * Task 04966: Queries the device API to find out if there are devices available for
	 * <ul>
	 * <li>the given <code>attribute</code> and</li>
	 * <li>the current host IP (taken from {@link NetUtils#getLocalHost()}).</li>
	 * </ul>
	 * If there are such devices, then this method creates a {@link InputMethod} for echa available {@link IDeviceRequest} that has an {@link ISingleValueResponse}.<br>
	 * The background of this last restriction is that (at least for now) we want just one value to set it to the attribute propersies editor.
	 *
	 * @param attribute
	 * @return
	 * @see #getAdditionalInputMethods(int)
	 *
	 */
	@SuppressWarnings("rawtypes")
	private static List<IInputMethod<?>> mkAdditionalInputMethods(final I_M_Attribute attribute)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		final List<IInputMethod<?>> inputMethods = new ArrayList<>();

		final IHostIdentifier myHost = NetUtils.getLocalHost();

		final IDeviceBL deviceBL = Services.get(IDeviceBL.class);
		final List<String> devicesForThisAttribute = deviceBL.getAllDeviceNamesForAttrAndHost(
				attribute,
				myHost);

		if (logger.isInfoEnabled())
			logger.info(String.format("Devices for host %s and attributte %s: ", myHost, attribute.getValue()) + devicesForThisAttribute);

		// If there are more than one device for this attribute, we use the device names' first characters for the button texts.
		// How many chars we need depends of how log the common prefix is.
		final String commonPrefix;
		if (devicesForThisAttribute.size() == 1)
		{
			commonPrefix = ""; // only one dev => we will do with the dev name's first character
		}
		else
		{
			commonPrefix = StringUtils.getCommonPrefix(devicesForThisAttribute.toArray(new String[0]));
		}
		for (final String deviceName : devicesForThisAttribute)
		{
			// trying to access the device.
			final IDevice device;
			try
			{
				device = deviceBL.createAndConfigureDeviceOrReturnExisting(ctx, deviceName, myHost);
			}
			catch (final Exception e)
			{
				final String msg = String.format("Unable to access device %s from host %s. Details:\n%s", deviceName, myHost, e.getLocalizedMessage());
				logger.warn(msg, e);
				Services.get(IClientUI.class).warn(Env.WINDOW_MAIN, msg);
				continue;
			}

			// OK, we were able to access it. Now add a button for each sort of request to our panel
			final IDevice deviceToAddInputMethodFor = device;

			final List<IDeviceRequest<ISingleValueResponse>> allRequestsFor = deviceBL.getAllRequestsFor(deviceName, attribute, ISingleValueResponse.class);

			if (logger.isInfoEnabled())
				logger.info(String.format("Found these requests for deviceName %s and attribute %s: ", deviceName, attribute.getValue()) + allRequestsFor);

			for (final IDeviceRequest<ISingleValueResponse> request : allRequestsFor)
			{
				final IInputMethod<?> method = new IInputMethod<Object>()
				{
					final private String buttonText = commonPrefix + deviceName.charAt(commonPrefix.length());

					@Override
					public AppsAction getAppsAction()
					{
						// TODO 04966: polish..e.g. see to it that there is a nice icon etc (but consider that maybe this is not the right place).
						return AppsAction.builder()
								.setAction(buttonText)
								.setToolTipText(buttonText)
								.build();
					}

					@Override
					public Object invoke()
					{
						logger.debug("This: {}, Device: {}; Request: {}", this, deviceToAddInputMethodFor, request);

						final ISingleValueResponse response = deviceToAddInputMethodFor.accessDevice(request);
						logger.debug("Device {}; Response: {}", deviceToAddInputMethodFor, response);

						return response.getSingleValue();
					}

					// @formatter:off
					@Override public String toString() { return "IInputMethod[buttonText=" + buttonText + "]"; }
					// @formatter:on
				};
				inputMethods.add(method);
			}
		}
		return ImmutableList.copyOf(inputMethods);
	}

	@Override
	public String getPropertyDisplayName(final String propertyName)
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		final I_M_Attribute attribute = indexedAttributeStorage.getM_Attribute(propertyName);
		return attribute.getName();
	}

	@Override
	public int getDisplayType(final String propertyName)
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		final IAttributeValue attributeValue = indexedAttributeStorage.getAttributeValue(propertyName);
		if (attributeValue.isList())
		{
			return DisplayType.List;
		}

		final String valueType = attributeValue.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(valueType))
		{
			return DisplayType.String;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(valueType))
		{
			return DisplayType.Number;
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(valueType))
		{
			return DisplayType.Date;
		}
		else
		{
			// not supported, but we fallback to string
			return DisplayType.String;
		}
	}

	@Override
	public Object getPropertyValue(final String propertyName)
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();
		return indexedAttributeStorage.getPropertyValue(propertyName);
	}

	@Override
	public List<? extends NamePair> getPropertyAvailableValues(final String propertyName)
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		final I_M_Attribute attribute = indexedAttributeStorage.getM_Attribute(propertyName);
		Check.assumeNotNull(attribute, "attribute exists for propertyName={}", propertyName);

		final IAttributeValue av = indexedAttributeStorage.getAttributeValue(attribute);
		final List<? extends NamePair> availableValues = av.getAvailableValues();
		return availableValues;
	}

	@Override
	public ITerminalLookup getPropertyLookup(final String propertyName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPropertyValue(final String propertyName, final Object value)
	{
		// NOTE: if the model is disposed then the underlying indexedAttributeStorage would be "null", so it's safe to not check.
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		logger.debug("Setting propertyName={} to value={} on indexedAttributeStorage={} (this={})", propertyName, value, indexedAttributeStorage, this);
		indexedAttributeStorage.setPropertyValue(propertyName, value);
	}

	@Override
	public boolean isEditable(final String propertyName)
	{
		//
		// Check if globally readonly
		if (readonly)
		{
			return false;
		}

		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		//
		// Get the attribute. If no attribute, consider it readonly
		final I_M_Attribute attribute = indexedAttributeStorage.getM_Attribute(propertyName);
		if (attribute == null)
		{
			// shall not happen, but nevermind, tell that attribute is not editable
			return false;
		}

		//
		// If we are asked to allow editing attributes ONLY if we deal with a VHU, enforce it.
		if (attributesEditableOnlyIfVHU && !indexedAttributeStorage.isVirtualHU())
		{
			// not a virtual HU => readonly
			return false;
		}

		//
		// Check properties if it's always editable
		final ITerminalContext terminalContext = getTerminalContext();
		final IPropertiesPanelModelConfigurator propConfigurator = terminalContext.getService(IPropertiesPanelModelConfigurator.class);
		final Boolean isAlwaysEditable = propConfigurator.getValueBooleanOrNull(PROP_CONFIG_AlwaysEditable, attribute);
		if (isAlwaysEditable != null) // if configuration override was found
		{
			return isAlwaysEditable;
		}

		//
		// Ask the attribute set if user is allowed to change the attribute value directly
		final IAttributeValueContext calloutCtx = createCalloutContext();
		if (indexedAttributeStorage.getAttributeStorage().isReadonlyUI(calloutCtx, attribute))
		{
			return false;
		}

		//
		// Fallback: Assume attribute is editable
		return true;
	}

	@Override
	public ITerminalFieldConstraint<Object> getConstraint(final String propertyName)
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		final I_M_Attribute attribute = indexedAttributeStorage.getM_Attribute(propertyName);
		final IAttributeValue attributeValue = indexedAttributeStorage.getAttributeStorage().getAttributeValue(attribute);
		if (attributeValue.isList())
		{
			// We don't support list constraints atm
			return null;
		}

		final String attributeValueType = attributeValue.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final org.adempiere.mm.attributes.model.I_M_Attribute attributeEx = InterfaceWrapperHelper.create(attribute, org.adempiere.mm.attributes.model.I_M_Attribute.class);
			final BigDecimal valueMin = getValueMin(attributeEx);
			final BigDecimal valueMax = getValueMax(attributeEx);

			//
			// Case: valueMin and valueMax could not be null but both empty
			// NOTE: this can happen because of a bug in ADempiere UI where user cannot set ValueMin/ValueMax back to null so set those values to ZERO.
			if ((valueMin == null || valueMin.signum() == 0)
					&& (valueMax == null || valueMax.signum() == 0))
			{
				return null;
			}

			if (null != valueMin || null != valueMax)
			{
				final MinMaxNumericFieldConstraint constraint = new MinMaxNumericFieldConstraint(HUAttributeSetPropertiesModel.ERR_MinValue, HUAttributeSetPropertiesModel.ERR_MaxValue);
				constraint.setMinValue(valueMin);
				constraint.setMaxValue(valueMax);

				return constraint;
			}
		}
		return null;
	}

	/**
	 * Task 04966: Returns additional input methods for the given attribute idx.
	 * <p>
	 * Background: We query the device API to find out if there are devices available for
	 * <ul>
	 * <li>the given <code>attribute</code> and</li>
	 * <li>the current host IP (taken from {@link NetUtils#getLocalHost()}).</li>
	 * </ul>
	 * If there are such devices, then this method creates a {@link InputMethod} for echa available {@link IDeviceRequest} that has an {@link ISingleValueResponse}.<br>
	 * The background of this last restriction is that (at least for now) we want just one value to set it to the attribute propersies editor.
	 *
	 * @param attribute
	 * @return
	 * @see IDeviceBL
	 *
	 */
	@Override
	public List<IInputMethod<?>> getAdditionalInputMethods(final String propertyName)
	{
		return getIndexedAttributeStorage().getAdditionalInputMethods(propertyName);
	}

	private BigDecimal getValueMin(final org.adempiere.mm.attributes.model.I_M_Attribute attribute)
	{
		if (InterfaceWrapperHelper.isNull(attribute, org.adempiere.mm.attributes.model.I_M_Attribute.COLUMNNAME_ValueMin))
		{
			return null;
		}
		else
		{
			return attribute.getValueMin();
		}
	}

	private BigDecimal getValueMax(final org.adempiere.mm.attributes.model.I_M_Attribute attribute)
	{
		if (InterfaceWrapperHelper.isNull(attribute, org.adempiere.mm.attributes.model.I_M_Attribute.COLUMNNAME_ValueMax))
		{
			return null;
		}
		else
		{
			return attribute.getValueMax();
		}
	}

	/**
	 * Sets always readonly flag
	 *
	 * @param readonly
	 */
	public void setReadonly(final boolean readonly)
	{
		this.readonly = readonly;
	}

	public void setAttributesEditableOnlyIfVHU(final boolean attributesEditableOnlyIfVHU)
	{
		this.attributesEditableOnlyIfVHU = attributesEditableOnlyIfVHU;
	}

	private IAttributeValueContext createCalloutContext()
	{
		final boolean weightableOnlyIfVHU = attributesEditableOnlyIfVHU;
		final IAttributeValueContext calloutCtx = new DefaultAttributeValueContext();
		calloutCtx.setParameter(IWeightableBL.PROPERTY_WeightableOnlyIfVHU, weightableOnlyIfVHU);
		return calloutCtx;
	}

	@Override
	public List<String> getPropertyNames()
	{
		return getIndexedAttributeStorage().getPropertyNames();
	}

	@Override
	public void commitEdit()
	{
		final IndexedAttributeStorage indexedAttributeStorage = getIndexedAttributeStorage();

		// Don't commit anything if the model was already disposed.
		// This prevents data corruption, hard to debug.
		if (indexedAttributeStorage.isDisposed())
		{
			return;
		}

		final IAttributeStorage attributeStorage = indexedAttributeStorage.getAttributeStorage();
		if (attributeStorage == null)
		{
			return;
		}

		attributeStorage.saveChangesIfNeeded();
	}

	@Override
	public String toString()
	{
		return "HUAttributeSetPropertiesModel [_indexedAttributeStorage=" + _indexedAttributeStorage + ", _indexedAttributeStorageLock=" + _indexedAttributeStorageLock + ", readonly=" + readonly + ", attributesEditableOnlyIfVHU=" + attributesEditableOnlyIfVHU + ", attributeStorageListener=" + attributeStorageListener + "]";
	}

	/**
	 * Immutable {@link IAttributeStorage} wrapper which also contains indexed attributes and other additional informations.
	 * <p>
	 * By "indexed attributes", we mean the ability to work with the storage in terms of attribute-property names (at the core that's usually <code>M_Attribute.Value</code>).
	 *
	 * @author tsa
	 *
	 */
	private static final class IndexedAttributeStorage
	{
		public static final IndexedAttributeStorage NULL = new IndexedAttributeStorage();

		public static final IndexedAttributeStorage of(final IAttributeStorage attributeStorage)
		{
			if (attributeStorage == null)
			{
				return NULL;
			}
			return new IndexedAttributeStorage(attributeStorage);
		}

		/**
		 * The actual wrapped attributes.
		 */
		private final IAttributeStorage attributeStorage;

		private final ImmutableList<String> propertyNames;
		private final Map<String, I_M_Attribute> propertyName2attribute;
		private final Map<String, List<IInputMethod<?>>> propertyName2AdditionalInputAction; // task 04966

		/** true if attribute set's underlying HU is a virtual HU */
		private final boolean virtualHU;

		/** Null constructor */
		private IndexedAttributeStorage()
		{
			super();
			this.attributeStorage = NullAttributeStorage.instance;
			this.propertyNames = ImmutableList.of();
			this.propertyName2attribute = Collections.emptyMap();
			this.propertyName2AdditionalInputAction = Collections.emptyMap();
			this.virtualHU = false;
		}

		private IndexedAttributeStorage(final IAttributeStorage attributeStorage)
		{
			super();

			final List<String> propertyNames = new ArrayList<>();
			final Map<String, I_M_Attribute> propertyName2attribute = new HashMap<>();
			final HashMap<String, List<IInputMethod<?>>> propertyName2AdditionalInputAction = new HashMap<>(); // task 04966

			for (final IAttributeValue attributeValue : attributeStorage.getAttributeValues())
			{
				// Skip attributes which will never ever be displayed to user
				if (!attributeValue.isDisplayedUI())
				{
					continue;
				}

				final I_M_Attribute attribute = attributeValue.getM_Attribute();
				final String propertyName = attribute.getValue();

				propertyNames.add(propertyName);
				propertyName2attribute.put(propertyName, attribute);

				// task 04966
				final List<IInputMethod<?>> inputMethods = mkAdditionalInputMethods(attribute);
				propertyName2AdditionalInputAction.put(propertyName, ImmutableList.copyOf(inputMethods));
			}

			this.attributeStorage = attributeStorage;
			this.propertyNames = ImmutableList.copyOf(propertyNames);
			this.propertyName2attribute = ImmutableMap.copyOf(propertyName2attribute);
			this.propertyName2AdditionalInputAction = ImmutableMap.copyOf(propertyName2AdditionalInputAction);
			this.virtualHU = attributeStorage.isVirtual();
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "[" + attributeStorage + "]";
		}

		/** @return attribute storage */
		public final IAttributeStorage getAttributeStorage()
		{
			return attributeStorage;
		}

		public final boolean isDisposed()
		{
			final HUKeyAttributeStorage huKeyAttributeStorage = HUKeyAttributeStorage.castOrNull(attributeStorage);
			if (huKeyAttributeStorage != null)
			{
				return huKeyAttributeStorage.isDisposed();
			}
			return false; // not disposed
		}

		public final List<String> getPropertyNames()
		{
			return propertyNames;
		}

		/** @return attribute or null */
		public final I_M_Attribute getM_Attribute(final String propertyName)
		{
			return propertyName2attribute.get(propertyName);
		}

		public List<IInputMethod<?>> getAdditionalInputMethods(final String propertyName)
		{
			return propertyName2AdditionalInputAction.get(propertyName);
		}

		private final IHUAttributesDAO getHUAttributesDAO()
		{
			return attributeStorage.getHUAttributeStorageFactory().getHUAttributesDAO();
		}

		public final IAttributeValue getAttributeValue(final I_M_Attribute attribute)
		{
			return attributeStorage.getAttributeValue(attribute);
		}

		public final IAttributeValue getAttributeValue(final String propertyName)
		{
			final I_M_Attribute attribute = getM_Attribute(propertyName);
			return attributeStorage.getAttributeValue(attribute);
		}

		public final Object getPropertyValue(final String propertyName)
		{
			final I_M_Attribute attribute = getM_Attribute(propertyName);
			if (attribute == null)
			{
				return null;
			}

			return getAttributeStorage().getValue(attribute);
		}

		public final void setPropertyValue(final String propertyName, final Object value)
		{
			try (final IAutoCloseable autoflushDisabler = getHUAttributesDAO().temporaryDisableAutoflush())
			{
				final I_M_Attribute attribute = getM_Attribute(propertyName);
				attributeStorage.setValue(attribute, value);
			}
		}

		public final boolean isVirtualHU()
		{
			return virtualHU;
		}
	}
}
