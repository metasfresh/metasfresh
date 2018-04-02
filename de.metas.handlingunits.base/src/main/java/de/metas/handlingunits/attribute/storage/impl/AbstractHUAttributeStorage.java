package de.metas.handlingunits.attribute.storage.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import lombok.NonNull;

public abstract class AbstractHUAttributeStorage extends AbstractAttributeStorage implements IHUAware
{
	// Services
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private boolean saveOnChange = false;

	public AbstractHUAttributeStorage(@NonNull final IAttributeStorageFactory storageFactory)
	{
		super(storageFactory);
	}

	/**
	 *
	 * @param saveOnChange if true, attribute values will be saved to database on each change
	 */
	@Override
	public final void setSaveOnChange(final boolean saveOnChange)
	{
		//
		// Iterate existing attribute values and set "saveOnChange" option to them.
		// There is no need to actually load the attributes, because when they will be loaded they will be correctly configured.
		for (final IAttributeValue attributeValue : getAttributeValuesCurrent())
		{
			if (attributeValue instanceof HUAttributeValue)
			{
				final HUAttributeValue huAttributeValue = (HUAttributeValue)attributeValue;
				huAttributeValue.setSaveOnChange(saveOnChange);
				// NOTE: setSaveOnChange will also save the value right away if it was just activated
			}
		}

		//
		// Propagate the flag to currently loaded children
		getChildAttributeStorages(false).forEach(childAttributeStorage -> childAttributeStorage.setSaveOnChange(saveOnChange));

		this.saveOnChange = saveOnChange;
	}

	protected final boolean isSaveOnChange()
	{
		return this.saveOnChange;
	}

	@Override
	public void saveChangesIfNeeded()
	{
		// Make sure we are not saving the changes if this stoarge was disposed (not matter what!),
		// because this could override attribute values which are newer and which were updated outside of this storage.
		if (!assertNotDisposed())
		{
			return;
		}

		if (saveOnChange)
		{
			// all changes were saved when they happened, so there is no point to save them now
			return;
		}

		//
		// Iterate existing attribute values and save them.
		for (final IAttributeValue attributeValue : getAttributeValuesCurrent())
		{
			if (!(attributeValue instanceof HUAttributeValue))
			{
				continue;
			}

			final HUAttributeValue huAttributeValue = (HUAttributeValue)attributeValue;
			huAttributeValue.save();
		}
	}

	/**
	 *
	 * @return handling unit or null
	 */
	@Override
	public abstract I_M_HU getM_HU();

	@Override
	protected final List<IAttributeValue> loadAttributeValues()
	{
		final IHUAttributesDAO huAttributesDAO = getHUAttributesDAO();

		final I_M_HU hu = getM_HU();
		if (hu == null)
		{
			return Collections.emptyList();
		}

		logger.trace("Loading attributes for {}", this);

		final List<I_M_HU_Attribute> huAttributes = huAttributesDAO.retrieveAttributesOrdered(hu);
		return toAttributeValues(huAttributes);
	}

	@Override
	protected final List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, final Map<I_M_Attribute, Object> defaultAttributesValue)
	{
		final I_M_HU hu = getM_HU();
		Check.assumeNotNull(hu, "hu not null");

		final IHUPIAttributesDAO huPIAttributesDAO = getHUPIAttributesDAO();

		//
		// Retrieve M_HU_PI_Attributes
		// gh #460: in case of an aggregate HU which is created right now, we need to get the pi version the HUBuilder was invoked with.
		// note that we can't yet get it from the HU's parent item itself, because that item is not yet finalized.
		final I_M_HU_PI_Version piVersion = IHUBuilder.BUILDER_INVOCATION_HU_PI_VERSION.getValue(hu, handlingUnitsBL.getPIVersion(hu));
		final List<I_M_HU_PI_Attribute> piAttributes = huPIAttributesDAO.retrievePIAttributes(piVersion);

		//
		// Generate all M_HU_Attribute records and convert them to IAttributeValue
		// final List<I_M_HU_Attribute> huAttributes = new ArrayList<I_M_HU_Attribute>(piAttributes.size());
		final List<IAttributeValue> attributeValues = new ArrayList<>(piAttributes.size());
		for (final I_M_HU_PI_Attribute piAttribute : piAttributes)
		{
			final Object valueInitialDefault = getDefaultAttributeValue(defaultAttributesValue, piAttribute.getM_Attribute());
			final IAttributeValue attributeValue = generateAttributeValueOrNull(attributesCtx, hu, piAttribute, valueInitialDefault);
			if (attributeValue == null)
			{
				continue;
			}
			attributeValues.add(attributeValue);
		}

		return attributeValues;
	}

	private IAttributeValue generateAttributeValueOrNull(final IAttributeValueContext attributesCtx, final I_M_HU hu, final I_M_HU_PI_Attribute piAttribute, final Object valueInitialDefault)
	{
		//
		// Generate initial M_HU_Attribute (not saved)
		final I_M_HU_Attribute huAttribute = createM_HU_Attribute(hu, piAttribute);
		if (huAttribute == null)
		{
			return null;
		}

		//
		// Wrap the "huAttribute" to IAttributeValue
		final IAttributeValue attributeValue = toAttributeValue(huAttribute);

		//
		// Generate initial/seed value
		final IAttributeValueCallout handler = attributeValue.getAttributeValueCallout();
		final Object valueInitial = handler.generateSeedValue(this, attributeValue.getM_Attribute(), valueInitialDefault);
		attributeValue.setValueInitial(valueInitial);
		attributeValue.setValue(attributesCtx, valueInitial);

		//
		// Generate value using generator (if any)
		final IAttributeValueGenerator generator = attributeValue.getAttributeValueGeneratorOrNull();
		if (generator != null)
		{
			try
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(huAttribute, true); // useClientOrgFromModel = true
				final I_M_Attribute attribute = attributeValue.getM_Attribute();
				final String valueType = attributeValue.getAttributeValueType();
				if (generator.canGenerateValue(ctx, this, attribute))
				{
					final Object valueGenerated;
					if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(valueType))
					{
						final String valueStr = generator.generateStringValue(ctx, this, attribute);
						valueGenerated = valueStr;
					}
					else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(valueType))
					{
						final BigDecimal valueBD = generator.generateNumericValue(ctx, this, attribute);
						valueGenerated = valueBD;
					}
					else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(valueType))
					{
						final Date valueDate = generator.generateDateValue(ctx, this, attribute);
						valueGenerated = valueDate;
					}
					else
					{
						throw new UnsupportedOperationException("ValueType not supported: " + valueType + "(attribute=" + attribute + ", generator=" + generator + ")");
					}
					attributeValue.setValue(attributesCtx, valueGenerated);
				}
			}
			catch (final UnsupportedOperationException e)
			{
				// FIXME: don't control the flow by throwing exceptions
				logger.info("Skip generating value because is not supported."
						+ "\nM_HU_Attribute=" + huAttribute
						+ "\nGenerator=" + generator, e);
			}
		}

		//
		// Save the M_HU_Attribute and return
		save(huAttribute);
		return attributeValue;
	}

	/**
	 *
	 * @param hu
	 * @param piAttr
	 * @return generated {@link I_M_HU_Attribute} (not saved)
	 */
	private I_M_HU_Attribute createM_HU_Attribute(final I_M_HU hu, final I_M_HU_PI_Attribute piAttr)
	{
		// Don't create HU Attribute for inactive PI Attribute
		if (!piAttr.isActive())
		{
			return null;
		}

		final IHUAttributesDAO huAttributesDAO = getHUAttributesDAO();
		final I_M_HU_Attribute huAttribute = huAttributesDAO.newHUAttribute(hu);
		huAttribute.setAD_Org_ID(hu.getAD_Org_ID());
		huAttribute.setM_HU(hu);
		huAttribute.setM_HU_PI_Attribute(piAttr);
		huAttribute.setM_Attribute(piAttr.getM_Attribute());

		// NOTE: don't save it!

		return huAttribute;
	}

	private final void save(final I_M_HU_Attribute huAttribute)
	{
		final IHUAttributesDAO huAttributesDAO = getHUAttributesDAO();
		try
		{
			huAttributesDAO.save(huAttribute);
		}
		catch (final Exception e)
		{
			// it's pretty hard to track it due to aspects, so throwing it here for now
			throw new AdempiereException("Could not save attribute " + huAttribute + ", due to " + e.getLocalizedMessage(), e);
		}
	}

	protected final List<IAttributeValue> toAttributeValues(final List<I_M_HU_Attribute> huAttributes)
	{
		if (huAttributes.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<IAttributeValue> attributeValues = new ArrayList<>(huAttributes.size());
		for (final I_M_HU_Attribute huAttr : huAttributes)
		{
			final IAttributeValue huAttributeValue = toAttributeValue(huAttr);
			attributeValues.add(huAttributeValue);
		}

		return attributeValues;
	}

	/**
	 * Helper method for creating {@link IAttributeValue} from {@link I_M_HU_Attribute}.
	 *
	 * @param huAttribute
	 * @return created {@link IAttributeValue}
	 */
	private final IAttributeValue toAttributeValue(final I_M_HU_Attribute huAttribute)
	{
		final HUAttributeValue huAttributeValue = new HUAttributeValue(this, huAttribute, isSaveOnChange());
		return huAttributeValue;
	}

	protected final IHandlingUnitsDAO getHandlingUnitsDAO()
	{
		return handlingUnitsDAO;
	}

	@Override
	public void updateHUTrxAttribute(final IMutableHUTransactionAttribute huTrxAttribute, final IAttributeValue fromAttributeValue)
	{
		assertNotDisposed();

		//
		// Set M_HU
		final I_M_HU hu = getM_HU();
		huTrxAttribute.setReferencedObject(hu);

		//
		// Set HU related fields
		//
		// NOTE: we assume given "attributeValue" was created by "toAttributeValue"
		if (fromAttributeValue instanceof HUAttributeValue)
		{
			final HUAttributeValue attributeValueImpl = (HUAttributeValue)fromAttributeValue;

			final I_M_HU_PI_Attribute piAttribute = attributeValueImpl.getM_HU_PI_Attribute();
			huTrxAttribute.setM_HU_PI_Attribute(piAttribute);

			final I_M_HU_Attribute huAttribute = attributeValueImpl.getM_HU_Attribute();
			huTrxAttribute.setM_HU_Attribute(huAttribute);
		}
		else
		{
			throw new AdempiereException("Attribute value " + fromAttributeValue + " is not valid for this storage (" + this + ")");
		}
	}

	@Override
	public final String getQtyUOMTypeOrNull()
	{
		final I_M_HU hu = getM_HU();
		final IHUStorageDAO huStorageDAO = getHUStorageDAO();
		return huStorageDAO.getC_UOMTypeOrNull(hu);
	}

	@Override
	public final BigDecimal getStorageQtyOrZERO()
	{
		final IHUStorageFactory huStorageFactory = getAttributeStorageFactory().getHUStorageFactory();
		final IHUStorage storage = huStorageFactory.getStorage(getM_HU());
		final BigDecimal fullStorageQty = storage.getQtyForProductStorages();
		return fullStorageQty;
	}

	@Override
	public final boolean isVirtual()
	{
		return handlingUnitsBL.isVirtual(getM_HU());
	}

	@Override
	public int getM_Warehouse_ID()
	{
		final I_M_HU hu = getM_HU();
		if (hu == null)
		{
			return -1;
		}

		final I_M_Locator locator = hu.getM_Locator();
		if(locator == null)
		{
			return -1;
		}

		return locator.getM_Warehouse_ID();
	}

}
