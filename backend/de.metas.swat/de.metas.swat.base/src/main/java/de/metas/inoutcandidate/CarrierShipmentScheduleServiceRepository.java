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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.CCache;
import de.metas.inout.ShipmentScheduleId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Goods_Type;
import org.compiere.model.I_Carrier_Service;
import org.compiere.model.I_M_ShipmentSchedule_Carrier_Service;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

/**
 * Repository that deals with {@link I_Carrier_Service} records assigned via {@link I_M_ShipmentSchedule_Carrier_Service}.
 */
@Repository
public class CarrierShipmentScheduleServiceRepository
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final CCache<String, CarrierService> carrierServicesByExternalId = CCache.newLRUCache(I_Carrier_Service.Table_Name + "#by#M_Shipper_ID#ExternalId", 100, 0);

	public ImmutableSet<CarrierServiceId> getAssignedServiceIdsByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return ImmutableSet.copyOf(queryBL.createQueryBuilder(I_M_ShipmentSchedule_Carrier_Service.class)
				.addEqualsFilter(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.andCollect(I_M_ShipmentSchedule_Carrier_Service.COLUMN_Carrier_Service_ID)
				.create()
				.listIds(CarrierServiceId::ofRepoId));
	}

	public ImmutableSet<CarrierServiceId> getAssignedServiceIdsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ImmutableSet.copyOf(queryBL.createQueryBuilder(I_M_ShipmentSchedule_Carrier_Service.class)
				.addInArrayFilter(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.andCollect(I_M_ShipmentSchedule_Carrier_Service.COLUMN_Carrier_Service_ID)
				.create()
				.listIds(CarrierServiceId::ofRepoId));
	}

	public void removeAssignedServiceIdsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		queryBL.createQueryBuilder(I_M_ShipmentSchedule_Carrier_Service.class)
				.addInArrayFilter(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.delete();
	}

	public ImmutableSetMultimap<ShipmentScheduleId, CarrierServiceId> getAssignedServiceIdsMapByShipmentScheduleIds(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_Carrier_Service.class)
				.addInArrayFilter(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						s -> ShipmentScheduleId.ofRepoId(s.getM_ShipmentSchedule_ID()),
						s -> CarrierServiceId.ofRepoId(s.getCarrier_Service_ID())));
	}

	public void assignServicesToShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId, final @NonNull Set<CarrierServiceId> serviceIds)
	{
		queryBL.createQueryBuilder(I_M_ShipmentSchedule_Carrier_Service.class)
				.addEqualsFilter(I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.delete();
		final ImmutableSet<I_M_ShipmentSchedule_Carrier_Service> assignedCarrierServices = serviceIds.stream()
				.map(serviceId -> {
					final I_M_ShipmentSchedule_Carrier_Service po = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_Carrier_Service.class);
					po.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(shipmentScheduleId));
					po.setCarrier_Service_ID(CarrierServiceId.toRepoId(serviceId));
					return po;
				})
				.collect(ImmutableSet.toImmutableSet());

		InterfaceWrapperHelper.saveAll(assignedCarrierServices);
	}

	@Nullable
	private CarrierService getCachedServiceByShipperExternalId(@NonNull final ShipperId shipperId, @Nullable final String externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return carrierServicesByExternalId.getOrLoad(shipperId + externalId, () ->
				queryBL.createQueryBuilder(I_Carrier_Service.class)
						.addEqualsFilter(I_Carrier_Goods_Type.COLUMNNAME_M_Shipper_ID, shipperId)
						.addEqualsFilter(I_Carrier_Goods_Type.COLUMNNAME_ExternalId, externalId)
						.firstOptional()
						.map(CarrierShipmentScheduleServiceRepository::fromRecord)
						.orElse(null));
	}

	private static CarrierService fromRecord(@NotNull final I_Carrier_Service service)
	{
		return CarrierService.builder()
				.id(CarrierServiceId.ofRepoId(service.getCarrier_Service_ID()))
				.externalId(service.getExternalId())
				.name(service.getName())
				.build();
	}

	private CarrierService createShipperService(@NonNull final ShipperId shipperId, @NonNull final CarrierService shipperProduct)
	{
		final I_Carrier_Service po = InterfaceWrapperHelper.newInstance(I_Carrier_Service.class);
		po.setM_Shipper_ID(shipperId.getRepoId());
		po.setExternalId(shipperProduct.getExternalId());
		po.setName(shipperProduct.getName());
		InterfaceWrapperHelper.saveRecord(po);
		return fromRecord(po);
	}
}
