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

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IInAusLandAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import de.metas.util.Services;

public class InAusLandAttributeDAO implements IInAusLandAttributeDAO
{
	public static final String SYSCONFIG_InAusLandAttribute = "de.metas.fresh.In/AuslandAttribute";
	
	@Override
	public AttributeId retrieveInAusLandAttributeId(final int adClientId, final int adOrgId)
	{
		final int inAusAttributeId = Services.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_InAusLandAttribute,
						-1, // defaultValue
						adClientId,
						adOrgId);
		return AttributeId.ofRepoIdOrNull(inAusAttributeId);
	}

	@Override
	public AttributeListValue retrieveInAusLandAttributeValue(final Properties ctx, final String inAusLand)
	{
		
		final I_M_Attribute inAusLandAttribute = retrieveInAusLandAttribute(ctx);
		if (inAusLandAttribute == null)
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(inAusLandAttribute, inAusLand);
		
	}
	
	@Override
	public I_M_Attribute retrieveInAusLandAttribute(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final AttributeId inAusLandAttributeId = retrieveInAusLandAttributeId(adClientId, adOrgId);
		if (inAusLandAttributeId == null)
		{
			return null;
		}

		return Services.get(IAttributeDAO.class).getAttributeById(inAusLandAttributeId);
	}

}
