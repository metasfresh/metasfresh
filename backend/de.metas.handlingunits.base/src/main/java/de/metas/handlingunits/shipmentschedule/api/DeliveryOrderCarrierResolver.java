/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleCarrierServiceRepository;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.shipper.gateway.spi.model.ResolvedCarrier;
import de.metas.shipping.mpackage.PackageId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Resolves, per shipment schedule, the carrier (product + goods-type + services) used when creating the
 * Shipper-Gateway delivery order at SEND time.
 * <p>
 * <b>Module direction:</b> the carrier source of truth is the picking-job <b>line</b>, which is only visible
 * here in {@code de.metas.handlingunits.base} — {@code de.metas.shipper.gateway.commons} must not depend on
 * the handlingunits module (that would create a dependency cycle). So the carrier is resolved here and passed
 * into commons as plain {@link ResolvedCarrier} data on the {@code DeliveryOrderCreateRequest}.
 * <p>
 * <b>LINE-FIRST, SCHEDULE-FALLBACK:</b> for each shipment schedule covered by the given packages, the carrier
 * from the picking-job line wins; a schedule with no line falls back to the schedule's own carrier
 * (product/goods-type on the schedule, services from {@link ShipmentScheduleCarrierServiceRepository}) —
 * exactly the source {@code ShipperGatewayFacade.createDeliveryOrderKey} read before this change.
 */
@Service
@RequiredArgsConstructor
public class DeliveryOrderCarrierResolver
{
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final ShipmentScheduleCarrierServiceRepository shipmentScheduleCarrierServiceRepository;
	@NonNull private final PickingJobRepository pickingJobRepository;

	@NonNull
	public ImmutableMap<ShipmentScheduleId, ResolvedCarrier> resolveByPackageIds(@NonNull final Collection<PackageId> packageIds)
	{
		if (packageIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		// the shipment schedules covered by these packages (the universe of schedules to resolve a carrier for)
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById = packageIds.stream()
				.flatMap(packageId -> shipmentScheduleRepository.loadByPackageId(packageId).stream())
				.collect(ImmutableMap.toImmutableMap(
						ShipmentSchedule::getId,
						schedule -> schedule,
						(existing, ignored) -> existing));
		if (schedulesById.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Set<ShipmentScheduleId> scheduleIds = schedulesById.keySet();

		// LINE: the picking-job line carrier per schedule (source of truth). Absent => no line => fall back.
		final Map<ShipmentScheduleId, ResolvedCarrier> lineCarrierByScheduleId =
				pickingJobRepository.getCarrierByScheduleIds(scheduleIds);

		final ImmutableMap.Builder<ShipmentScheduleId, ResolvedCarrier> result = ImmutableMap.builder();
		for (final ShipmentScheduleId scheduleId : scheduleIds)
		{
			final ResolvedCarrier lineCarrier = lineCarrierByScheduleId.get(scheduleId);
			result.put(scheduleId,
					lineCarrier != null
							? lineCarrier
							: fallbackToSchedule(schedulesById.get(scheduleId)));
		}
		return result.build();
	}

	@NonNull
	private ResolvedCarrier fallbackToSchedule(@NonNull final ShipmentSchedule schedule)
	{
		final ImmutableSet<CarrierServiceId> services =
				shipmentScheduleCarrierServiceRepository.getAssignedServiceIdsByShipmentScheduleId(schedule.getId());
		return ResolvedCarrier.builder()
				.carrierProductId(schedule.getCarrierProductId())
				.carrierGoodsTypeId(schedule.getCarrierGoodsTypeId())
				.carrierServices(services)
				.build();
	}
}
