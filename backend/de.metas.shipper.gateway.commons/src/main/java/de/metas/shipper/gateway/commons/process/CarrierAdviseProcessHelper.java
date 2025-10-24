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
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.shipper.gateway.commons.async.AdviseDeliveryOrderWorkpackageProcessor;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.SpringContextHolder;

import java.util.Objects;

final class CarrierAdviseProcessHelper
{
	public static CarrierAdviseProcessHelper newInstance()
	{
		return CarrierAdviseProcessHelper.builder()
				.shipmentScheduleService(SpringContextHolder.instance.getBean(ShipmentScheduleService.class))
				.trxManager(Services.get(ITrxManager.class))
				.build();
	}

	// Services
	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final ITrxManager trxManager;



	@Builder
	private CarrierAdviseProcessHelper(
			@NonNull final ShipmentScheduleService shipmentScheduleService,
			@NonNull final ITrxManager trxManager
			)
	{
		this.shipmentScheduleService = shipmentScheduleService;
		this.trxManager = trxManager;
	}

	public boolean isSingleShipper(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		return getShipperIds(shipmentScheduleIds).size() == 1;
	}

	public ImmutableSet<ShipperId> getShipperIds(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		return CollectionUtils.extractDistinctElements(shipmentScheduleService.getByIds(shipmentScheduleIds).values(), ShipmentSchedule::getShipperId)
				.stream()
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void requestCarrierAdvises(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		shipmentScheduleService.getByIds(shipmentScheduleIds).values()
				.forEach(this::requestCarrierAdvice);
	}

	private void requestCarrierAdvice(final ShipmentSchedule shipmentSchedule)
	{
		trxManager.assertThreadInheritedTrxNotExists();
		trxManager.runInThreadInheritedTrx(() -> advise(shipmentSchedule));
	}

	private void advise(final ShipmentSchedule shipmentSchedule)
	{
		if (!shipmentScheduleService.isEligibleForCarrierAdvise(shipmentSchedule))
		{
			return;
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentSchedule.getId();
		AdviseDeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(shipmentScheduleId, null);

		shipmentSchedule.setCarrierAdvisingStatus(CarrierAdviseStatus.Requested);
		shipmentSchedule.setCarrierProductId(null);
		shipmentScheduleService.save(shipmentSchedule);
	}
}
