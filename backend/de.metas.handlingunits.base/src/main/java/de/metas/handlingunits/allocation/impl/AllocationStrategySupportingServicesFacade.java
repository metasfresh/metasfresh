package de.metas.handlingunits.allocation.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IDeveloperModeBL;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class AllocationStrategySupportingServicesFacade
{
	public static AllocationStrategySupportingServicesFacade newInstance()
	{
		return new AllocationStrategySupportingServicesFacade();
	}

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	public boolean isDeveloperMode()
	{
		return developerModeBL.isEnabled();
	}

	public I_M_HU_PI getVirtualPI(final Properties ctx)
	{
		return handlingUnitsDAO.retrieveVirtualPI(ctx);
	}

	public boolean isPureVirtual(final I_M_HU_Item huItem)
	{
		return handlingUnitsBL.isPureVirtual(huItem);
	}

	public boolean isVirtual(final I_M_HU_Item item)
	{
		return handlingUnitsBL.isVirtual(item);
	}

	public List<I_M_HU_Item> retrieveItems(@NonNull final I_M_HU hu)
	{
		return handlingUnitsDAO.retrieveItems(hu);
	}

	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		return handlingUnitsDAO.retrieveParentItem(hu);
	}

	public String getItemType(final I_M_HU_Item item)
	{
		return handlingUnitsBL.getItemType(item);
	}

	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(item);
	}

	public I_M_HU_PI getIncluded_HU_PI(final I_M_HU_Item item)
	{
		return handlingUnitsBL.getPIItem(item).getIncluded_HU_PI();
	}

	public void deleteHU(final I_M_HU hu)
	{
		handlingUnitsDAO.delete(hu);
	}
}
