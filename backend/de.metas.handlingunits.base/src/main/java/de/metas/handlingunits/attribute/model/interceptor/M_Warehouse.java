/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.attribute.model.interceptor;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Interceptor(I_M_Warehouse.class)
@Component
public class M_Warehouse
{

	private final HUUniqueAttributesService huUniqueAttributesService;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);

	public M_Warehouse(@NonNull final HUUniqueAttributesService huUniqueAttributesService)
	{
		this.huUniqueAttributesService = huUniqueAttributesService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse })
	public void handleHUUniqueAttributes(@NonNull final I_M_Warehouse warehouse)
	{
		final List<LocatorId> locatorIds = warehouseDAO.getLocatorIds(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));

		if (warehouse.isQualityReturnWarehouse())
		{
			// delete hu unique attribute entries
			for (LocatorId locatorId : locatorIds)
			{
				final Iterator<I_M_HU> hus = huDAO.retrieveTopLevelHUsForLocator(locatorId);
				while (hus.hasNext())
				{
					huUniqueAttributesService.deleteHUUniqueAttributesForHUAttribute(HuId.ofRepoId(hus.next().getM_HU_ID()));
				}
			}
		}
		else
		{
			// create hu unique attribute entries
			for (LocatorId locatorId : locatorIds)
			{
				final Iterator<I_M_HU> hus = huDAO.retrieveTopLevelHUsForLocator(locatorId);
				while (hus.hasNext())
				{
					huUniqueAttributesService.createOrUpdateHUUniqueAttribute(HuId.ofRepoId(hus.next().getM_HU_ID()));
				}
			}
		}
	}
}
