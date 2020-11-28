package de.metas.handlingunits.allocation.strategy;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.HUItemType;
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

@Service
@VisibleForTesting
public class AllocationStrategySupportingServicesFacade
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	// private IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private IHandlingUnitsDAO getHandlingUnitsDAO()
	{
		// FIXME: find out why if we are using the "handlingUnitsDAO" field, tests are failing
		return Services.get(IHandlingUnitsDAO.class);
	}

	public boolean isDeveloperMode()
	{
		return developerModeBL.isEnabled();
	}

	public I_M_HU_PI getVirtualPI(final Properties ctx)
	{
		return getHandlingUnitsDAO().retrieveVirtualPI(ctx);
	}

	public I_M_HU_Item getFirstNotPureVirtualItem(@NonNull final I_M_HU_Item item)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = getHandlingUnitsDAO();

		I_M_HU_Item itemFirstNotPureVirtual = item;
		while (itemFirstNotPureVirtual != null && handlingUnitsBL.isPureVirtual(itemFirstNotPureVirtual))
		{
			final I_M_HU parentHU = itemFirstNotPureVirtual.getM_HU();
			itemFirstNotPureVirtual = handlingUnitsDAO.retrieveParentItem(parentHU);
		}

		// shall not happen
		if (itemFirstNotPureVirtual == null)
		{
			throw new AdempiereException("No not pure virtual HU item found for " + item);
		}

		return itemFirstNotPureVirtual;
	}

	public boolean isVirtual(final I_M_HU_Item item)
	{
		return handlingUnitsBL.isVirtual(item);
	}

	public List<I_M_HU_Item> retrieveItems(@NonNull final I_M_HU hu)
	{
		return getHandlingUnitsDAO().retrieveItems(hu);
	}

	public HUItemType getItemType(final I_M_HU_Item item)
	{
		return HUItemType.ofCode(handlingUnitsBL.getItemType(item));
	}

	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		return getHandlingUnitsDAO().retrieveIncludedHUs(item);
	}

	public I_M_HU_PI getIncluded_HU_PI(@NonNull final I_M_HU_Item item)
	{
		return handlingUnitsBL.getIncludedPI(item);
	}

	public void deleteHU(final I_M_HU hu)
	{
		getHandlingUnitsDAO().delete(hu);
	}
}
