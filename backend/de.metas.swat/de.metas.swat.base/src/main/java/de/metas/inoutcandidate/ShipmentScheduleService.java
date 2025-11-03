/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.inoutcandidate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleService
{
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final CarrierShipmentScheduleServiceRepository carrierServiceRepository;
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;

	public ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		final ShipmentSchedule shipmentSchedule = shipmentScheduleRepository.getById(id);
		shipmentSchedule.setCarrierServices(carrierServiceRepository.getAssignedServiceIdsByShipmentScheduleId(shipmentSchedule.getId()));
		return shipmentSchedule;
	}

	public ImmutableList<ShipmentSchedule> getByIds(@NonNull final ImmutableSet<ShipmentScheduleId> ids)
	{
		return addCarrierServices(shipmentScheduleRepository.getByIds(ids));
	}

	public List<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		return addCarrierServices(shipmentScheduleRepository.getMapBy(query));
	}

	private ImmutableList<ShipmentSchedule> addCarrierServices(@NonNull final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> shipmentSchedules)
	{
		if (shipmentSchedules.isEmpty())
		{
			return shipmentSchedules.values().asList();
		}

		final ImmutableMap<ShipmentScheduleId, ImmutableSet<CarrierServiceId>> carrierServicesByShipmentScheduleId = carrierServiceRepository.getAssignedServiceIdsMapByShipmentScheduleIds(shipmentSchedules.keySet());

		for (final ShipmentSchedule shipmentSchedule : shipmentSchedules.values())
		{
			final ImmutableSet<CarrierServiceId> carrierServices = carrierServicesByShipmentScheduleId.getOrDefault(shipmentSchedule.getId(), ImmutableSet.of());
			shipmentSchedule.setCarrierServices(carrierServices);
		}

		return shipmentSchedules.values().asList();
	}


	public void save(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleRepository.save(shipmentSchedule);
		carrierServiceRepository.assignServicesToShipmentSchedule(shipmentSchedule.getId(), shipmentSchedule.getCarrierServices());
	}

	public boolean isEligibleForCarrierAdvise(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final CarrierAdviseStatus carrierAdviseStatus = CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status());
		if (shipmentSchedule.isProcessed()
				|| shipmentSchedule.isClosed()
				|| !shipmentSchedule.isActive()
				|| ShipperId.ofRepoIdOrNull(shipmentSchedule.getM_Shipper_ID()) == null
				|| shipmentScheduleEffectiveBL.getQtyToDeliverBD(shipmentSchedule).signum() <= 0
				|| (carrierAdviseStatus != null && !carrierAdviseStatus.isEligibleForAutoEnqueue()))
		{
			return false;
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(shipmentSchedule.getM_ShipmentSchedule_ID());
		return shipmentScheduleId == null || !pickingJobScheduleRepository.anyMatch(PickingJobScheduleQuery.builder().onlyShipmentScheduleId(shipmentScheduleId).build());
	}

	public boolean isEligibleForCarrierAdvise(@NonNull final ShipmentSchedule shipmentSchedule, final boolean isIncludeCarrierAdviseManual)
	{
		if (shipmentSchedule.getShipperId() == null
				|| shipmentSchedule.isProcessed()
				|| shipmentSchedule.isClosed()
				|| !shipmentSchedule.isActive()
				|| shipmentSchedule.getQuantityToDeliver().signum() <= 0
		        || !isIncludeCarrierAdviseManual && shipmentSchedule.getCarrierAdvisingStatus().isManual()
				|| !shipmentSchedule.getCarrierAdvisingStatus().isEligibleForManualEnqueue())
		{
			return false;
		}

		return !pickingJobScheduleRepository.anyMatch(PickingJobScheduleQuery.builder()
				.onlyShipmentScheduleId(shipmentSchedule.getId())
				.build());
	}
}
