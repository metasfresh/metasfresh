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
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ShipmentScheduleService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

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
		return loadCarrierServices(shipmentScheduleRepository.getByIds(ids));
	}

	public ImmutableList<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		return loadCarrierServices(shipmentScheduleRepository.getMapBy(query));
	}

	private ImmutableList<ShipmentSchedule> loadCarrierServices(@NonNull final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> shipmentSchedules)
	{
		if (shipmentSchedules.isEmpty())
		{
			return shipmentSchedules.values().asList();
		}

		final ImmutableSetMultimap<ShipmentScheduleId, CarrierServiceId> carrierServicesByShipmentScheduleId = carrierServiceRepository.getAssignedServiceIdsMapByShipmentScheduleIds(shipmentSchedules.keySet());

		for (final ShipmentSchedule shipmentSchedule : shipmentSchedules.values())
		{
			final ImmutableSet<CarrierServiceId> carrierServices = carrierServicesByShipmentScheduleId.get(shipmentSchedule.getId());
			shipmentSchedule.setCarrierServices(carrierServices);
		}

		return shipmentSchedules.values().asList();
	}

	public void updateByQuery(@NonNull final ShipmentScheduleQuery query, @NonNull final Consumer<ShipmentSchedule> updater)
	{
		trxManager.runInThreadInheritedTrx(() -> {
			final ImmutableList<ShipmentSchedule> schedules = getBy(query);
			if (schedules.isEmpty())
			{
				return;
			}

			for (final ShipmentSchedule schedule : schedules)
			{
				updater.accept(schedule);
				save(schedule);
			}
		});
	}

	public void save(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleRepository.save(shipmentSchedule);
		carrierServiceRepository.assignServicesToShipmentSchedule(shipmentSchedule.getId(), shipmentSchedule.getCarrierServicesIfLoaded());
	}

	public void removeAssignedServiceIdsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		carrierServiceRepository.removeAssignedServiceIdsByShipmentScheduleIds(shipmentScheduleIds);
	}

	@Value
	@Builder(toBuilder = true)
	private static class EligibleCarrierAdviseRequest
	{
		@Nullable ShipmentScheduleId shipmentScheduleId;
		@Nullable ShipperId shipperId;
		boolean isProcessed;
		boolean isClosed;
		boolean isActive;
		@NonNull BigDecimal quantityToDeliver;
		@Nullable CarrierAdviseStatus carrierAdvisingStatus;

		boolean isAuto;
		boolean isIncludeCarrierAdviseManual;

		public static EligibleCarrierAdviseRequest of(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL)
		{
			return EligibleCarrierAdviseRequest.builder()
					.shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(shipmentSchedule.getM_ShipmentSchedule_ID()))
					.shipperId(ShipperId.ofRepoIdOrNull(shipmentSchedule.getM_Shipper_ID()))
					.isProcessed(shipmentSchedule.isProcessed())
					.isClosed(shipmentSchedule.isClosed())
					.isActive(shipmentSchedule.isActive())
					.quantityToDeliver(shipmentScheduleEffectiveBL.getQtyToDeliverBD(shipmentSchedule))
					.carrierAdvisingStatus(CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status()))
					.build();
		}

		public static EligibleCarrierAdviseRequest of(@NonNull final ShipmentSchedule shipmentSchedule)
		{
			return EligibleCarrierAdviseRequest.builder()
					.shipmentScheduleId(shipmentSchedule.getId())
					.shipperId(shipmentSchedule.getShipperId())
					.isProcessed(shipmentSchedule.isProcessed())
					.isClosed(shipmentSchedule.isClosed())
					.isActive(shipmentSchedule.isActive())
					.quantityToDeliver(shipmentSchedule.getQuantityToDeliver().toBigDecimal())
					.carrierAdvisingStatus(shipmentSchedule.getCarrierAdvisingStatus())
					.build();
		}
	}

	public boolean isEligibleForAutoCarrierAdvise(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final EligibleCarrierAdviseRequest request = EligibleCarrierAdviseRequest.of(shipmentSchedule, shipmentScheduleEffectiveBL);
		return isEligibleForCarrierAdvise(request.toBuilder().isAuto(true).build());
	}

	public boolean isNotEligibleForManualCarrierAdvise(@NonNull final ShipmentSchedule shipmentSchedule, final boolean isIncludeCarrierAdviseManual)
	{
		final EligibleCarrierAdviseRequest request = EligibleCarrierAdviseRequest.of(shipmentSchedule);
		return !isEligibleForCarrierAdvise(request.toBuilder().isIncludeCarrierAdviseManual(isIncludeCarrierAdviseManual).build());
	}

	private boolean isEligibleForCarrierAdvise(@NonNull final EligibleCarrierAdviseRequest request)
	{
		if (request.getShipperId() == null
				|| request.isProcessed()
				|| request.isClosed()
				|| !request.isActive()
				|| request.getQuantityToDeliver().signum() <= 0)
		{
			return false;
		}

		final CarrierAdviseStatus carrierAdviseStatus = request.getCarrierAdvisingStatus();
		if (request.isAuto)
		{
			if (carrierAdviseStatus != null && !carrierAdviseStatus.isEligibleForAutoEnqueue()) {return false;}
		}
		else
		{ // Manual
			if (carrierAdviseStatus == null || !carrierAdviseStatus.isEligibleForManualEnqueue()) {return false;}
			if (!request.isIncludeCarrierAdviseManual && carrierAdviseStatus.isManual()) {return false;}
		}

		final ShipmentScheduleId shipmentScheduleId = request.getShipmentScheduleId();
		if (shipmentScheduleId == null) {return true;}

		return !pickingJobScheduleRepository.anyMatch(PickingJobScheduleQuery.builder().onlyShipmentScheduleId(shipmentScheduleId).build());
	}

}
