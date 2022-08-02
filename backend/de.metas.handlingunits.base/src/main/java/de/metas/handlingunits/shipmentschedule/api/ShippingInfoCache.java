/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_Shipper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * This is a short-term-cache; use it only within one method and one thread. It can load data on demand, but has no invalidation mechanism.
 */
public class ShippingInfoCache
{
	private final IShipmentScheduleBL shipmentScheduleBL;
	private final IShipmentScheduleEffectiveBL scheduleEffectiveBL;
	private final IShipperDAO shipperDAO;

	private final HashMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById = new HashMap<>();
	private final HashMap<String, I_M_Shipper> shipperByInternalName = new HashMap<>();

	@Builder
	private ShippingInfoCache(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final IShipmentScheduleEffectiveBL scheduleEffectiveBL,
			@NonNull final IShipperDAO shipperDAO)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.scheduleEffectiveBL = scheduleEffectiveBL;
		this.shipperDAO = shipperDAO;
	}

	public void warmUpForShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		CollectionUtils.getAllOrLoad(
				shipmentSchedulesById,
				shipmentScheduleIds,
				shipmentScheduleBL::getByIds);
	}

	public void warmUpForShipperInternalNames(@NonNull final Collection<String> shipperInternalNameCollection)
	{
		CollectionUtils.getAllOrLoad(
				shipperByInternalName,
				shipperInternalNameCollection,
				shipperDAO::getByInternalName);
	}

	public I_M_ShipmentSchedule getShipmentScheduleById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedulesById.computeIfAbsent(shipmentScheduleId, shipmentScheduleBL::getById);
	}

	public OrgId getOrgId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
		return OrgId.ofRepoId(shipmentSchedule.getAD_Org_ID());
	}

	public BPartnerId getBPartnerId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
		return scheduleEffectiveBL.getBPartnerId(shipmentSchedule);
	}

	public Optional<AsyncBatchId> getAsyncBatchId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getShipmentScheduleById(shipmentScheduleId);
		return Optional.ofNullable(AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID()));
	}

	@Nullable
	public ShipperId getShipperId(@Nullable final String shipperInternalName)
	{
		if (Check.isBlank(shipperInternalName))
		{
			return null;
		}

		final I_M_Shipper shipper = shipperByInternalName.computeIfAbsent(shipperInternalName, this::loadShipper);

		return shipper != null
				? ShipperId.ofRepoId(shipper.getM_Shipper_ID())
				: null;
	}

	@Nullable
	public String getTrackingURL(@NonNull final String shipperInternalName)
	{
		final I_M_Shipper shipper = shipperByInternalName.computeIfAbsent(shipperInternalName, this::loadShipper);

		return shipper != null
				? shipper.getTrackingURL()
				: null;
	}

	@Nullable
	private I_M_Shipper loadShipper(@NonNull final String shipperInternalName)
	{
		return shipperDAO.getByInternalName(ImmutableSet.of(shipperInternalName)).get(shipperInternalName);
	}
}
