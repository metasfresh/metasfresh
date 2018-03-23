package de.metas.handlingunits.attribute.spi.impl;

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


import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.spi.IHUTrxAttributeProcessor;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;

/**
 * Process an {@link I_M_HU_Trx_Attribute} for an {@link I_M_AttributeSetInstance}.
 *
 * @author tsa
 *
 */
public class HUTrxAttributeProcessor_ASI implements IHUTrxAttributeProcessor
{

	@Override
	public void processSave(final IHUContext huContext, final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedModel)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(referencedModel, I_M_AttributeSetInstance.class);
		final String trxName = InterfaceWrapperHelper.getTrxName(huTrxAttribute);
		final int attributeId = huTrxAttribute.getM_Attribute_ID();

		I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asi, attributeId);
		if (ai == null)
		{
			// throw new AdempiereException("No attribute instance was found."
			// + "\n ASI=" + asi
			// + "\n Attribute=" + huTrxAttribute.getM_Attribute());

			final Properties ctx = InterfaceWrapperHelper.getCtx(asi);
			ai = attributeDAO.createNewAttributeInstance(ctx, asi, attributeId, trxName);
		}

		ai.setValueNumber(huTrxAttribute.getValueNumber());
		ai.setValueDate(huTrxAttribute.getValueDate());
		ai.setValue(huTrxAttribute.getValue());
		InterfaceWrapperHelper.save(ai);
	}

	@Override
	public void processDrop(final IHUContext huContext, final I_M_HU_Trx_Attribute huTrxAttribute, final Object referencedModel)
	{
		// TODO Decide: shall we implement this. I think we should not because dropping an attribute from ASI shall not be possible from here
		throw new UnsupportedOperationException();
	}

}
