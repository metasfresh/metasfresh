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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IADRAttributeBL;
import org.adempiere.mm.attributes.api.IADRAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import de.metas.fresh.model.I_C_BPartner;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class ADRAttributeDAO implements IADRAttributeDAO
{
	// constant for the name of ADR Attribute
	public static final String SYSCONFIG_ADRAttribute = "de.metas.fresh.ADRAttribute";

	@Override
	public AttributeId retrieveADRAttributeId(final int adClientId, final int adOrgId)
	{
		final int adrAttributeId = Services.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_ADRAttribute,
						-1, // defaultValue
						adClientId,
						adOrgId);

		return AttributeId.ofRepoIdOrNull(adrAttributeId);
	}

	@Override
	public I_M_Attribute retrieveADRAttribute(final org.compiere.model.I_C_BPartner bpartner)
	{
		final int adClientId = bpartner.getAD_Client_ID();
		final int adOrgId = bpartner.getAD_Org_ID();

		return retrieveADRAttribute(adClientId, adOrgId);
	}

	@Override
	public I_M_Attribute retrieveADRAttribute(Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		return retrieveADRAttribute(adClientId, adOrgId);
	}

	private I_M_Attribute retrieveADRAttribute(int adClientId, int adOrgId)
	{
		final AttributeId adrAttributeId = retrieveADRAttributeId(adClientId, adOrgId);
		if (adrAttributeId == null)
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).getAttributeById(adrAttributeId);
	}

	@Override
	public AttributeListValue retrieveADRAttributeValue(final Properties ctx, @NonNull final I_C_BPartner partner, boolean isSOTrx)
	{
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

		return Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(adrAttribute, adrRegionValue);
	}

}
