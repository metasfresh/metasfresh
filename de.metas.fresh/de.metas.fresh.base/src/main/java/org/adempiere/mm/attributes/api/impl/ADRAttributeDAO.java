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

import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.Env;

import de.metas.fresh.model.I_C_BPartner;

public class ADRAttributeDAO implements IADRAttributeDAO
{
	// constant for the name of ADR Attribute
	public static final String SYSCONFIG_ADRAttribute = "de.metas.fresh.ADRAttribute";

	@Override
	public int retrieveADRAttributeId(final int adClientId, final int adOrgId)
	{
		final int adrAttributeId = Services.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_ADRAttribute,
						-1, // defaultValue
						adClientId,
						adOrgId);
		
		return adrAttributeId;
	}

	@Override
	public I_M_Attribute retrieveADRAttribute(final org.compiere.model.I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int adClientId = bpartner.getAD_Client_ID();
		final int adOrgId = bpartner.getAD_Org_ID();

		return retrieveADRAttribute(ctx, adClientId, adOrgId);
	}

	@Override
	public I_M_Attribute retrieveADRAttribute(Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		return retrieveADRAttribute(ctx, adClientId, adOrgId);
	}

	private I_M_Attribute retrieveADRAttribute(Properties ctx, int adClientId, int adOrgId)
	{
		final int adrAttributeId = retrieveADRAttributeId(adClientId, adOrgId);
		if (adrAttributeId <= 0)
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).retrieveAttributeById(ctx, adrAttributeId);
	}

	@Override
	public I_M_AttributeValue retrieveADRAttributeValue(final Properties ctx, final I_C_BPartner partner, boolean isSOTrx)
	{
		Check.assumeNotNull(partner, "partner not null");

		final String adrRegionValue = Services.get(IADRAttributeBL.class).getADRForBPartner(partner, isSOTrx);
		if (Check.isEmpty(adrRegionValue, true))
		{
			return null;
		}

		final I_M_Attribute adrAttribute = retrieveADRAttribute(partner);
		if (adrAttribute == null)
		{
			return null;
		}

		I_M_AttributeValue attributeValue = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(adrAttribute, adrRegionValue);
		return attributeValue;
	}

}
