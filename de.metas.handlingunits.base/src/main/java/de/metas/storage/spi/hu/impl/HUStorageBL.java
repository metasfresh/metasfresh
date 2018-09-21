package de.metas.storage.spi.hu.impl;

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


import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.util.CacheCtx;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.util.Services;

public class HUStorageBL implements IHUStorageBL
{
	@Override
	@Cached(cacheName = I_M_HU_PI_Attribute.Table_Name + "#VHU#M_Attribute_IDs")
	public Set<Integer> getAvailableAttributeIds(@CacheCtx final Properties ctx)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUPIAttributesDAO piAttributesDAO = Services.get(IHUPIAttributesDAO.class);

		final I_M_HU_PI virtualPI = handlingUnitsDAO.retrieveVirtualPI(ctx);
		final List<I_M_HU_PI_Attribute> piAttributes = piAttributesDAO.retrievePIAttributes(virtualPI);
		final Set<Integer> attributeIds = new HashSet<>(piAttributes.size());
		for (final I_M_HU_PI_Attribute piAttribute : piAttributes)
		{
			attributeIds.add(piAttribute.getM_Attribute_ID());
		}
		return attributeIds;
	}

}
