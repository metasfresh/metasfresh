package de.metas.handlingunits.sourcehu;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface ISourceHuService extends ISingletonService
{
	/**
	 * Return {@code true} if the given HU is referenced by an active {@link I_M_Source_HU}.<br>
	 * Note that we use the ID for performance reasons.
	 * 
	 * @param huId
	 * @return
	 */
	boolean isSourceHU(int huId);

	List<I_M_HU> retrieveTopLevelButOnlyIfActualSourceHU(List<I_M_HU> vhus);

	boolean isSourceHUOrChildOfSourceHU(int huId);

	/**
	 * Returns those fine picking source HUs whose location and product match any the given query.
	 * 
	 * @param query
	 * @return
	 */
	List<I_M_HU> retrieveActiveSourceHUs(ActiveSourceHusQuery query);

	/**
	 * Specifies which source HUs (products and warehouse) to retrieve in particular
	 */
	@lombok.Value
	@lombok.Builder
	class ActiveSourceHusQuery
	{
		/**
		 * Query for HUs that have any of the given product IDs. Empty means that no HUs will be found.
		 */
		@Singular
		Set<Integer> productIds;

		int warehouseId;

		/**
		 * @param shipmentSchedules the schedules to make the new instance from; may not be {@code null}. Empty means that no HUs will be found.
		 */
		public static ActiveSourceHusQuery fromShipmentSchedules(@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules)
		{
			final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
			final Set<Integer> productIds = shipmentSchedules.stream().map(s -> s.getM_Product_ID()).collect(Collectors.toSet());

			final int effectiveWarehouseId = shipmentSchedules.isEmpty() ? -1 : shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedules.get(0));

			return new ActiveSourceHusQuery(productIds, effectiveWarehouseId);
		}
	}

	I_M_Source_HU addSourceHu(int huId);

	/**
	 * 
	 * @param huId
	 * @return {@code true} if anything was deleted.
	 */
	boolean removeSourceHu(int huId);
}
