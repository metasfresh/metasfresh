package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * de.metas.fresh.base
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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import de.metas.fresh.model.I_C_BPartner;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;

public class SubProducerAttributeBL implements ISubProducerAttributeBL
{
	@Override
	public void updateAttributesOnSubProducerChanged(final Properties ctx,
			final IAttributeSet attributeSet,
			final boolean subProducerJustInitialized)
	{
		// Set the Marke ADR from the subproducer
		recalculateADR(ctx, attributeSet, subProducerJustInitialized);
	}

	private void recalculateADR(final Properties ctx,
			final IAttributeSet attributeSet,
			final boolean subProducerJustInitialized)
	{
		final I_M_Attribute attr_MarkeADR = Services.get(IADRAttributeDAO.class).retrieveADRAttribute(ctx);
		if (attr_MarkeADR == null)
		{
			return;
		}

		final IAttributeStorage attributeStorage = (IAttributeStorage)attributeSet;
		if(!attributeStorage.hasAttribute(attr_MarkeADR))
		{
			return; // skip if the attribute storage does not have the ADR attribute
		}
		if (subProducerJustInitialized && attributeStorage.getValue(attr_MarkeADR) != null)
		{
			return; // task 08782: the sub-producer was set only now, so we keep the pre-existing ADR value.
		}

		// This variable will keep the partner for who we want to compute the ADR Region attribute
		// It can be either the sub-producer,if it exists, or the partner if it doesn't
		I_C_BPartner partner = getSubProducerBPartnerOrNull(ctx, attributeStorage);
		if (partner == null)
		{

			final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);

			partner = InterfaceWrapperHelper.create(hu.getC_BPartner(), de.metas.fresh.model.I_C_BPartner.class);

			//
			// If there is no BPartner we have to set the ADR attribute to null
			if (partner == null)
			{
				Check.assume(!subProducerJustInitialized, "partner=null for attributeSet={}, therefore subProducerJustInitialized is false", attributeSet);
				attributeStorage.setValueToNull(attr_MarkeADR);
				return;
			}
		}

		// isSoTrx = false because we only need it in Wareneingang POS
		// TODO: Check if this will be needed somewhere else and fix accordingly
		final boolean isSOTrx = false;
		final I_M_AttributeValue markeADR = Services.get(IADRAttributeBL.class).getCreateAttributeValue(ctx, partner, isSOTrx, ITrx.TRXNAME_None);
		final String markeADRValue = markeADR == null ? null : markeADR.getValue();

		attributeStorage.setValue(attr_MarkeADR, markeADRValue);
	}

	private I_C_BPartner getSubProducerBPartnerOrNull(final Properties ctx, final IAttributeSet attributeStorage)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_Attribute attr_SubProducer = attributeDAO.retrieveAttributeByValue(ctx, Constants.ATTR_SubProducerBPartner_Value, I_M_Attribute.class);
		if (attr_SubProducer == null)
		{
			return null;
		}

		final BigDecimal subProducerIdBD = attributeStorage.getValueAsBigDecimal(attr_SubProducer);
		final int subProducerID = subProducerIdBD.intValueExact();
		if (subProducerID <= 0)
		{
			return null;
		}
		final I_C_BPartner subProducer = InterfaceWrapperHelper.create(ctx, subProducerID, I_C_BPartner.class, ITrx.TRXNAME_None);
		return subProducer;
	}
}
