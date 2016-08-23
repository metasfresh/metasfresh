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


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.IBPartnerAware;
import org.adempiere.mm.attributes.exceptions.AttributeRestrictedException;
import org.adempiere.mm.attributes.exceptions.NoAttributeGeneratorException;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import de.metas.fresh.model.I_C_BPartner;

public class ADRAttributeBL implements IADRAttributeBL
{
	private static final String MSG_NoARDAttribute = "de.metas.fresh.ADRAttribute.error";

	@Override
	public int getM_Attribute_ID(final IBPartnerAware bpartnerAware)
	{
		final int adClientId = bpartnerAware.getAD_Client_ID();
		final int adOrgId = bpartnerAware.getAD_Org_ID();
		final int adrAttributeId = Services.get(IADRAttributeDAO.class).retrieveADRAttributeId(adClientId, adOrgId);
		return adrAttributeId;
	}

	@Override
	public I_M_AttributeValue getCreateAttributeValue(final IContextAware context, final IBPartnerAware bpartnerAware)
	{
		final Properties ctx = context.getCtx();
		final I_C_BPartner partner = bpartnerAware.getC_BPartner();
		final boolean isSOTrx = bpartnerAware.isSOTrx();
		final String trxName = context.getTrxName();
		return getCreateAttributeValue(ctx, partner, isSOTrx, trxName);
	}

	@Override
	public I_M_AttributeValue getCreateAttributeValue(final Properties ctx, final I_C_BPartner partner, final boolean isSOTrx, final String trxName)
	{
		final I_M_AttributeValue attributeValue = Services.get(IADRAttributeDAO.class).retrieveADRAttributeValue(ctx, partner, isSOTrx);

		final AttributeAction attributeAction = Services.get(IAttributesBL.class).getAttributeAction(ctx);
		if (attributeValue == null)
		{
			if (!isBPartnerRequiresADR(partner, isSOTrx))
			{
				// Our BP either doesn't have the flags set or no ADR defined.
				return null;
			}

			if (attributeAction == AttributeAction.Error)
			{
				throw new AdempiereException(MSG_NoARDAttribute, new Object[] { getADRForBPartner(partner, isSOTrx) });

			}
			else if (attributeAction == AttributeAction.GenerateNew)
			{
				final I_M_Attribute adrAttribute = Services.get(IADRAttributeDAO.class).retrieveADRAttribute(partner);
				
				//FRESH-559: In case of null adrAttribute, return null
				if(adrAttribute == null)
				{
					return null;
				}
				
				final IAttributeValueGenerator generator = Services.get(IAttributesBL.class).getAttributeValueGenerator(adrAttribute);

				if (generator == null)
				{
					throw new NoAttributeGeneratorException(partner.getValue());
				}

				return generator.generateAttributeValue(ctx, I_C_BPartner.Table_ID, partner.getC_BPartner_ID(), isSOTrx, trxName);
			}
			else if (attributeAction == AttributeAction.Ignore)
			{
				// Ignore: do now throw error, no not generate new attribute
			}
			else
			{
				throw new AdempiereException("@NotSupported@ AttributeAction " + attributeAction);
			}
		}
		else
		{
			if (!Services.get(IAttributesBL.class).isSameTrx(attributeValue, isSOTrx))
			{
				if (attributeAction == AttributeAction.Error)
				{
					throw new AttributeRestrictedException(ctx, isSOTrx, attributeValue, partner.getValue());
				}

				// We have an attribute value, but it is marked for a different transaction. Change type to "null", to make it available for both.
				attributeValue.setAvailableTrx(null);
				InterfaceWrapperHelper.save(attributeValue);
			}
		}

		return attributeValue;
	}

	private boolean isBPartnerRequiresADR(final I_C_BPartner partner, final boolean isSOTrx)
	{
		final boolean hasADRChecked = (isSOTrx ? partner.isADRCustomer() : partner.isADRVendor());
		final boolean hasADRSpecified = !Check.isEmpty(getADRForBPartner(partner, isSOTrx));

		return (hasADRChecked && hasADRSpecified);
	}

	@Override
	public String getADRForBPartner(final I_C_BPartner partner, final boolean isSOTrx)
	{
		if (isSOTrx)
		{
			final boolean isADRCustomer = partner.isADRCustomer();

			if (isADRCustomer)
			{
				return partner.getFresh_AdRRegion();
			}
		}

		else
		{
			final boolean isADRVendor = partner.isADRVendor();

			if (isADRVendor)
			{
				return partner.getFresh_AdRVendorRegion();
			}
		}
		return null;
	}
}
