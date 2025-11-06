/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.process;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public final class CarrierAdviseProcessService
{
	@NonNull public static final AdMessageKey ONLY_EXACTLY_ONE_SHIPPER_ALLOWED = AdMessageKey.of("MoreThanOneOrNoShipperSelected");

	// Services
	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public boolean isSingleShipper(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		return getShipperIds(shipmentScheduleIds).size() == 1;
	}

	public ImmutableSet<ShipperId> getShipperIds(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		return CollectionUtils.extractDistinctElements(shipmentScheduleService.getByIds(shipmentScheduleIds), ShipmentSchedule::getShipperId)
				.stream()
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void requestCarrierAdvises(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds, final boolean isIncludeCarrierAdviseManual)
	{
		shipmentScheduleService.getByIds(shipmentScheduleIds)
				.forEach(schedule -> requestCarrierAdvise(schedule, isIncludeCarrierAdviseManual));
	}

	public void requestCarrierAdvises(@NonNull final ShipmentScheduleQuery query)
	{
		shipmentScheduleService.getBy(query)
				.forEach(schedule -> requestCarrierAdvise(schedule, false));
	}

	private void requestCarrierAdvise(@NonNull final ShipmentSchedule shipmentSchedule, final boolean isIncludeCarrierAdviseManual)
	{
		trxManager.runInThreadInheritedTrx(() -> {
			if (shipmentScheduleService.isNotEligibleForManualCarrierAdvise(shipmentSchedule, isIncludeCarrierAdviseManual))
			{
				return;
			}

			shipmentSchedule.setCarrierAdvisingStatus(CarrierAdviseStatus.Requested);
			shipmentSchedule.setCarrierProductId(null);
			shipmentScheduleService.save(shipmentSchedule);
		});
	}

	public void updateEligibleShipmentSchedules(@NonNull final CarrierAdviseUpdateRequest request)
	{
		shipmentScheduleService.updateByQuery(request.getQuery(), schedule -> updateEligibleShipmentSchedule(schedule, request));
	}

	private void updateEligibleShipmentSchedule(@NonNull final ShipmentSchedule schedule, @NonNull final CarrierAdviseUpdateRequest request)
	{
		if (shipmentScheduleService.isNotEligibleForManualCarrierAdvise(schedule, request.isIncludeCarrierAdviseManual()))
		{
			return;
		}

		schedule.setCarrierAdvisingStatus(CarrierAdviseStatus.Manual);
		schedule.setCarrierProductId(request.getCarrierProductId());
		schedule.setCarrierGoodsTypeId(request.getCarrierGoodsTypeId());
		schedule.setCarrierServices(request.getCarrierServiceIds());
	}
}
