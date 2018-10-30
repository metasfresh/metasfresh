package de.metas.handlingunits.attribute.spi.impl;

import org.adempiere.mm.attributes.AttributeId;

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


import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.spi.IHUTrxAttributeProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;

/**
 * Process an {@link I_M_HU_Trx_Attribute} for an {@link I_M_HU}.
 *
 * @author tsa
 *
 */
public class HUTrxAttributeProcessor_HU implements IHUTrxAttributeProcessor
{
	@Override
	public void processSave(final IHUContext huContext, final I_M_HU_Trx_Attribute trxAttribute, final Object referencedModel)
	{
		if (!HUConstants.DEBUG_07277_processHUTrxAttribute)
		{
			return; // FIXME debuging
		}

		final I_M_HU hu = InterfaceWrapperHelper.create(referencedModel, I_M_HU.class);

		final IHUAttributesDAO huAttributesDAO = huContext.getHUAttributeStorageFactory().getHUAttributesDAO();
		final I_M_HU_Attribute huAttributeExisting = retrieveHUAttribute(huAttributesDAO, trxAttribute);

		final I_M_HU_Attribute huAttribute;
		if (huAttributeExisting == null)
		{
			// Create new attribute
			huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, hu);
			huAttribute.setAD_Org_ID(hu.getAD_Org_ID());
			huAttribute.setM_HU(hu);
			huAttribute.setM_Attribute_ID(trxAttribute.getM_Attribute_ID());

			huAttribute.setM_HU_PI_Attribute_ID(trxAttribute.getM_HU_PI_Attribute_ID());
		}
		else
		{
			huAttribute = huAttributeExisting;
		}

		//
		// Update values
		huAttribute.setValue(trxAttribute.getValue());
		huAttribute.setValueNumber(trxAttribute.getValueNumber());

		huAttributesDAO.save(huAttribute);
	}

	@Override
	public void processDrop(final IHUContext huContext, final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedModel)
	{
		final IHUAttributesDAO huAttributesDAO = huContext.getHUAttributeStorageFactory().getHUAttributesDAO();
		final I_M_HU_Attribute huAttributeExisting = retrieveHUAttribute(huAttributesDAO, huTrxAttribute);
		if (huAttributeExisting == null)
		{
			throw new InvalidAttributeValueException("Retrieved HUAttribute cannot be null (" + huTrxAttribute.getM_Attribute() + "): " + huTrxAttribute);
		}
		huAttributesDAO.delete(huAttributeExisting);
	}

	private I_M_HU_Attribute retrieveHUAttribute(final IHUAttributesDAO huAttributesDAO, final I_M_HU_Trx_Attribute trx)
	{
		if (trx.getM_HU_Attribute_ID() > 0)
		{
			final I_M_HU_Attribute huAttributeExisting = trx.getM_HU_Attribute();
			if (huAttributeExisting != null && huAttributeExisting.getM_HU_Attribute_ID() > 0)
			{
				return huAttributeExisting;
			}
		}

		final I_M_HU hu = trx.getM_HU();
		final AttributeId attributeId = AttributeId.ofRepoId(trx.getM_Attribute_ID());
		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(hu, attributeId);
		return huAttribute;
	}

}
