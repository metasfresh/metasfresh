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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Wraps an {@link I_M_AttributeSetInstance}; returns values of the packing item template's {@link I_M_HU_PI_Attribute}s.
 * attribute values from the wrapped ASI are <b>not</b> included, unless they are also in the packing item template.
 * The ASI is mostly used to set {@link IAttributeValue#isNew()} (if it's not in the ASI, then it's new afaiu).
 */
/* package */ class ASIWithPackingItemTemplateAttributeStorage extends AbstractAttributeStorage
{
	private final String id;
	private final I_M_AttributeSetInstance asi;

	public ASIWithPackingItemTemplateAttributeStorage(
			@NonNull final IAttributeStorageFactory storageFactory,
			@NonNull final I_M_AttributeSetInstance asi)
	{
		super(storageFactory);

		this.asi = asi;
		this.id = I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=" + asi.getM_AttributeSetInstance_ID();
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
		final IHUPIAttributesDAO huPIAttributesDAO = Services.get(IHUPIAttributesDAO.class);
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		//
		// Retrieve Attribute Instances for given ASI
		// Build M_Attribute_ID to M_AttributeInstance map
		final List<I_M_AttributeInstance> attributeInstances = attributeDAO.retrieveAttributeInstances(asi);
		final Map<AttributeId, I_M_AttributeInstance> attributeId2attributeInstance = new HashMap<>(attributeInstances.size());
		for (final I_M_AttributeInstance instance : attributeInstances)
		{
			attributeId2attributeInstance.put(AttributeId.ofRepoId(instance.getM_Attribute_ID()), instance);
		}

		//
		// Retrieve Template PI Attributes
		// find it's matching M_AttributeInstance and create an AttributeValue for it
		// If no M_AttributeInstance was found, create a plain one.
		final PIAttributes piAttributes = huPIAttributesDAO.retrievePIAttributes(HuPackingInstructionsVersionId.TEMPLATE);
		final List<IAttributeValue> result = new ArrayList<>();
		for (final AttributeId attributeId : piAttributes.getAttributeIds())
		{
			final I_M_HU_PI_Attribute piAttribute = piAttributes.getByAttributeId(attributeId);
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
				final Properties ctx = InterfaceWrapperHelper.getCtx(asi);
				final String trxName = InterfaceWrapperHelper.getTrxName(asi);
				attributeInstance = attributeDAO.createNewAttributeInstance(ctx, asi, attributeId, trxName);
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

	private IAttributeValue createAttributeValue(
			final I_M_AttributeInstance attributeInstance,
			final I_M_HU_PI_Attribute piAttribute,
			final boolean isGeneratedAttribute)
	{
		final AIWithHUPIAttributeValue aiAttributeValue = new AIWithHUPIAttributeValue(this, attributeInstance, piAttribute, isGeneratedAttribute);
		return aiAttributeValue;
	}

	@Override
	protected List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, final Map<AttributeId, Object> defaultAttributesValue)
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
