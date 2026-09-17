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

/**
 * Resolves, per shipment schedule, the carrier (product + goods-type + services) used when creating the
 * Shipper-Gateway delivery order at SEND time.
 * <p>
 * <b>Module direction:</b> resolved here in {@code de.metas.handlingunits.base} and passed into
 * {@code de.metas.shipper.gateway.commons} as plain {@link ResolvedCarrier} data on the
 * {@code DeliveryOrderCreateRequest} — commons must not depend on the handlingunits module (dependency cycle).
 * <p>
 * <b>SCHEDULE-SOURCED:</b> the carrier (product/goods-type on the schedule, services from
 * {@link ShipmentScheduleCarrierServiceRepository}) comes from the shipment schedule, which is the source of
 * truth. The delivery order is created per shipment at send time and freezes the then-current schedule carrier;
 * a schedule re-advised between partial shipments therefore yields the correct (different) carrier per shipment.
 * We deliberately do NOT source from the picking-job line: a schedule shipped in several partial shipments
 * legitimately carries different carriers across its lines, which cannot be reduced to a single send-time carrier.
 */
@Service
@RequiredArgsConstructor
public class DeliveryOrderCarrierResolver
{
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final ShipmentScheduleCarrierServiceRepository shipmentScheduleCarrierServiceRepository;

	@NonNull
	public ImmutableMap<ShipmentScheduleId, ResolvedCarrier> resolveByPackageIds(@NonNull final Collection<PackageId> packageIds)
	{
		if (packageIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		// the shipment schedules covered by these packages (the universe of schedules to resolve a carrier for);
		// batched — one load for all packages rather than re-loading per package
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById =
				shipmentScheduleRepository.loadByPackageIds(ImmutableSet.copyOf(packageIds)).values().stream()
						.collect(ImmutableMap.toImmutableMap(
								ShipmentSchedule::getId,
								schedule -> schedule,
								(existing, ignored) -> existing));
		if (schedulesById.isEmpty())
		{
			return ImmutableMap.of();
		}

		// The delivery order is created per shipment at SEND time; the shipment schedule is the carrier source of
		// truth (it is re-advised between partial shipments, so each shipment's order freezes the then-current
		// carrier). We deliberately do NOT source from the picking-job line: a schedule shipped in several partial
		// shipments legitimately carries different (re-advised) carriers across its lines, which cannot be reduced
		// to one — and the line has no per-shipment scope to disambiguate them.
		final ImmutableMap.Builder<ShipmentScheduleId, ResolvedCarrier> result = ImmutableMap.builder();
		for (final ShipmentSchedule schedule : schedulesById.values())
		{
			result.put(schedule.getId(), resolveFromSchedule(schedule));
		}
		return result.build();
	}

	/**
	 * Resolves the carrier (incl. the {@code manual} flag) for each of the given shipment schedules — same
	 * SCHEDULE-SOURCED resolution as {@link #resolveByPackageIds}, but for a set of schedules already in hand
	 * (e.g. the schedules of a picked HU in the picking carrier-advise consistency check).
	 */
	@NonNull
	public ImmutableMap<ShipmentScheduleId, ResolvedCarrier> resolveBySchedules(@NonNull final Collection<ShipmentSchedule> schedules)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ResolvedCarrier> result = ImmutableMap.builder();
		schedules.stream()
				.collect(ImmutableMap.toImmutableMap(ShipmentSchedule::getId, schedule -> schedule, (existing, ignored) -> existing))
				.values()
				.forEach(schedule -> result.put(schedule.getId(), resolveFromSchedule(schedule)));
		return result.build();
	}

	@NonNull
	private ResolvedCarrier resolveFromSchedule(@NonNull final ShipmentSchedule schedule)
	{
		final ImmutableSet<CarrierServiceId> services =
				shipmentScheduleCarrierServiceRepository.getAssignedServiceIdsByShipmentScheduleId(schedule.getId());
		return ResolvedCarrier.builder()
				.carrierProductId(schedule.getCarrierProductId())
				.carrierGoodsTypeId(schedule.getCarrierGoodsTypeId())
				.carrierServices(services)
				.manual(schedule.getCarrierAdvisingStatus().isManual())
				.build();
	}
}
