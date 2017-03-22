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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

/**
 * Wraps an {@link I_M_AttributeSetInstance}, uses definitions from NoPI's {@link I_M_HU_PI_Attribute}s.
 *
 * @author tsa
 *
 */
/* package */class ASIAttributeStorage extends AbstractAttributeStorage
{
	private final String id;
	private final I_M_AttributeSetInstance asi;

	public ASIAttributeStorage(final IAttributeStorageFactory storageFactory, final I_M_AttributeSetInstance asi)
	{
		super(storageFactory);

		Check.assumeNotNull(asi, "asi not null");
		this.asi = asi;

		id = I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=" + asi.getM_AttributeSetInstance_ID();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		return NullAttributeStorage.instance;
	}

	/**
	 * Always returns an empty collection.
	 */
	@Override
	public final List<IAttributeStorage> getChildAttributeStorages(boolean loadIfNeeded_IGNORED)
	{
		return Collections.emptyList();
	}

	@Override
	protected void toString(final ToStringHelper stringHelper)
	{
		stringHelper
				.add("id", id)
				.add("asi", asi)
				// .add("huDisplayName", Services.get(IHandlingUnitsBL.class).getDisplayName(getM_HU())) // used only for debugging)
				;
	}

	@Override
	protected List<IAttributeValue> loadAttributeValues()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUPIAttributesDAO huPIAttributesDAO = Services.get(IHUPIAttributesDAO.class);
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		//
		// Retrieve Template PI Attributes and build an M_Attribute_ID->M_HU_PI_Attribute map
		final Properties ctx = InterfaceWrapperHelper.getCtx(asi);
		final I_M_HU_PI noPI = handlingUnitsDAO.retrieveNoPI(ctx);

		//
		// Retrieve Attribute Instances for given ASI
		// Build M_Attribute_ID to M_AttributeInstance map
		final List<I_M_AttributeInstance> attributeInstances = attributesDAO.retrieveAttributeInstances(asi);
		final Map<Integer, I_M_AttributeInstance> attributeId2attributeInstance = new HashMap<Integer, I_M_AttributeInstance>(attributeInstances.size());
		for (final I_M_AttributeInstance instance : attributeInstances)
		{
			attributeId2attributeInstance.put(instance.getM_Attribute_ID(), instance);
		}

		//
		// Retrieve Template PI Attributes
		// find it's matching M_AttributeInstance and create an AttributeValue for it
		// If no M_AttributeInstance was found, create a plain one.
		final List<I_M_HU_PI_Attribute> piAttributes = huPIAttributesDAO.retrievePIAttributes(noPI);
		final List<IAttributeValue> result = new ArrayList<IAttributeValue>(piAttributes.size());
		for (final I_M_HU_PI_Attribute piAttribute : piAttributes)
		{
			final int attributeId = piAttribute.getM_Attribute_ID();
			I_M_AttributeInstance attributeInstance = attributeId2attributeInstance.get(attributeId);
			final boolean isGeneratedAttribute;
			if (attributeInstance == null)
			{
				//
				// 07698: If configured to not used in ASI, skip creation of the attribute
				if (!piAttribute.isUseInASI())
				{
					continue;
				}

				// No M_AttributeInstance was found for our PI Attribute
				// => create one but don't save
				final String trxName = InterfaceWrapperHelper.getTrxName(asi);
				attributeInstance = attributesDAO.createNewAttributeInstance(ctx, asi, attributeId, trxName);
				isGeneratedAttribute = true;
			}
			else
			{
				isGeneratedAttribute = false;
			}

			final IAttributeValue attributeValue = createAttributeValue(attributeInstance, piAttribute, isGeneratedAttribute);
			result.add(attributeValue);
		}

		return result;
	}

	private IAttributeValue createAttributeValue(final I_M_AttributeInstance attributeInstance,
			final I_M_HU_PI_Attribute piAttribute,
			final boolean isGeneratedAttribute)
	{
		final AIAttributeValue aiAttributeValue = new AIAttributeValue(this, attributeInstance, piAttribute, isGeneratedAttribute);
		return aiAttributeValue;
	}

	@Override
	protected List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, final Map<I_M_Attribute, Object> defaultAttributesValue)
	{
		throw new UnsupportedOperationException("Generating initial attributes not supported for " + this);
	}

	@Override
	public void updateHUTrxAttribute(final IMutableHUTransactionAttribute huTrxAttribute, final IAttributeValue fromAttributeValue)
	{
		huTrxAttribute.setReferencedObject(asi);
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected void addChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	@Override
	public void saveChangesIfNeeded()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setSaveOnChange(boolean saveOnChange)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getQtyUOMTypeOrNull()
	{
		// ASI attribute storages does not support Qty Storage
		return null;
	}
}
