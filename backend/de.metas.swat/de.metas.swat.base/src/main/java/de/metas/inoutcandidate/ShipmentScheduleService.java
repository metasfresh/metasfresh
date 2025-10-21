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

import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleService
{
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final CarrierShipmentScheduleServiceRepository carrierServiceRepository;
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;

	public ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		final ShipmentSchedule shipmentSchedule = shipmentScheduleRepository.getById(id);
		shipmentSchedule.setCarrierServices(carrierServiceRepository.getAssignedServiceIdsByShipmentScheduleId(shipmentSchedule.getId()));
		return shipmentSchedule;
	}

	public void save(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleRepository.save(shipmentSchedule);
		carrierServiceRepository.assignServicesToShipmentSchedule(shipmentSchedule.getId(), shipmentSchedule.getCarrierServices());
	}

	public boolean isEligibleForCarrierAdvise(@Nullable final ShipmentScheduleId id)
	{
		if (id == null) {return true;}

		final boolean isScheduled = pickingJobScheduleRepository.getByShipmentScheduleId(id)
				.map(PickingJobSchedule::isScheduled).orElse(false);
		final boolean isPickingStarted = false; //TODO PickingJob is not accessible here (null or !DocStatus.isNotProcessed)
		return !isScheduled && !isPickingStarted;
	}
}
