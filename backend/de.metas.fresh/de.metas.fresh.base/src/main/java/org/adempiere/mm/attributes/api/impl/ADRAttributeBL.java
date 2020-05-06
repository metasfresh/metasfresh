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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeListValueTrxRestriction;
import org.adempiere.mm.attributes.api.AttributeAction;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.IBPartnerAware;
import org.adempiere.mm.attributes.exceptions.AttributeRestrictedException;
import org.adempiere.mm.attributes.exceptions.NoAttributeGeneratorException;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;

import de.metas.fresh.model.I_C_BPartner;
import de.metas.lang.SOTrx;
import de.metas.util.Check;
import de.metas.util.Services;

public class ADRAttributeBL implements IADRAttributeBL
{
	private static final String MSG_NoARDAttribute = "de.metas.fresh.ADRAttribute.error";

	@Override
	public AttributeId getAttributeId(final IBPartnerAware bpartnerAware)
	{
		final int adClientId = bpartnerAware.getAD_Client_ID();
		final int adOrgId = bpartnerAware.getAD_Org_ID();
		final AttributeId adrAttributeId = Services.get(IADRAttributeDAO.class).retrieveADRAttributeId(adClientId, adOrgId);
		return adrAttributeId;
	}

	@Override
	public AttributeListValue getCreateAttributeValue(final IContextAware context, final IBPartnerAware bpartnerAware)
	{
		final Properties ctx = context.getCtx();
		final I_C_BPartner partner = bpartnerAware.getC_BPartner();
		final boolean isSOTrx = bpartnerAware.isSOTrx();
		final String trxName = context.getTrxName();
		return getCreateAttributeValue(ctx, partner, isSOTrx, trxName);
	}

	@Override
	public AttributeListValue getCreateAttributeValue(final Properties ctx, final I_C_BPartner partner, final boolean isSOTrx, final String trxName)
	{
		final AttributeListValue attributeValue = Services.get(IADRAttributeDAO.class).retrieveADRAttributeValue(ctx, partner, isSOTrx);

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

				final int bpartnerTableId = Services.get(IADTableDAO.class).retrieveTableId(I_C_BPartner.Table_Name);
				return generator.generateAttributeValue(ctx, bpartnerTableId, partner.getC_BPartner_ID(), isSOTrx, trxName);
			}
			else if (attributeAction == AttributeAction.Ignore)
			{
				// Ignore: do now throw error, no not generate new attribute
			}
			else
			{
				throw new AdempiereException("@NotSupported@ AttributeAction " + attributeAction);
			}
			
			return attributeValue;
		}
		else
		{
			final SOTrx soTrx = SOTrx.ofBoolean(isSOTrx);
			if(!attributeValue.isMatchingSOTrx(soTrx))
			{
				if (attributeAction == AttributeAction.Error)
				{
					throw new AttributeRestrictedException(ctx, soTrx, attributeValue, partner.getValue());
				}

				// We have an attribute value, but it is marked for a different transaction. Change type to "null", to make it available for both.
				final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
				return attributesRepo.changeAttributeValue(AttributeListValueChangeRequest.builder()
						.id(attributeValue.getId())
						.availableForTrx(AttributeListValueTrxRestriction.ANY_TRANSACTION)
						.build());
			}
			else
			{
				return attributeValue;
			}
		}
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
