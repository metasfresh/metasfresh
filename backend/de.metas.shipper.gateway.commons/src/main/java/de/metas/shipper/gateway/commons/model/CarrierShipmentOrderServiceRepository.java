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

package de.metas.shipper.gateway.commons.model;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.inoutcandidate.CarrierService;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Goods_Type;
import org.compiere.model.I_Carrier_Service;
import org.compiere.model.I_Carrier_ShipmentOrder_Service;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Repository that deals with {@link I_Carrier_Service} records assigned via {@link I_Carrier_ShipmentOrder_Service}.
 */
@Repository
public class CarrierShipmentOrderServiceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final CCache<String, CarrierService> carrierServicesByExternalId = CCache.newLRUCache(I_Carrier_Service.Table_Name + "#by#M_Shipper_ID#ExternalId", 100, 0);
	final CCache<String, CarrierService> carrierServicesById = CCache.newLRUCache(I_Carrier_Service.Table_Name + "#byId", 100, 0);

	@Nullable
	public CarrierService getCachedCarrierServiceById(@Nullable final CarrierServiceId serviceId)
	{
		if (serviceId == null)
		{
			return null;
		}
		return carrierServicesById.getOrLoad(serviceId.toString(), () ->
				queryBL.createQueryBuilder(I_Carrier_Service.class)
						.addEqualsFilter(I_Carrier_Service.COLUMNNAME_Carrier_Service_ID, serviceId)
						.firstOptional()
						.map(CarrierShipmentOrderServiceRepository::fromRecord)
						.orElse(null));
	}

	public Set<CarrierService> getAssignedServicesByDeliveryOrderId(@NonNull final DeliveryOrderId carrierShipmentOrderId)
	{
		return queryBL.createQueryBuilder(I_Carrier_ShipmentOrder_Service.class)
				.addEqualsFilter(I_Carrier_ShipmentOrder_Service.COLUMNNAME_Carrier_ShipmentOrder_ID, carrierShipmentOrderId)
				.andCollect(I_Carrier_ShipmentOrder_Service.COLUMN_Carrier_Service_ID)
				.create()
				.stream()
				.map(CarrierShipmentOrderServiceRepository::fromRecord)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void assignServiceToDeliveryOrder(@NonNull final DeliveryOrderId deliveryOrderId, @NonNull final ShipperId shipperId, @NonNull final CarrierService service)
	{
		final CarrierService actualService = getOrCreateService(shipperId, service.getExternalId(), service.getName());
		final I_Carrier_ShipmentOrder_Service po = InterfaceWrapperHelper.newInstance(I_Carrier_ShipmentOrder_Service.class);
		po.setCarrier_ShipmentOrder_ID(deliveryOrderId.getRepoId());
		po.setCarrier_Service_ID(CarrierServiceId.toRepoId(actualService.getId()));
		InterfaceWrapperHelper.saveRecord(po);
	}

	@NonNull
	public CarrierService getOrCreateService(@NonNull final ShipperId shipperId, @NonNull final String externalId, @NonNull final String name)
	{
		final CarrierService cachedService = getCachedServiceByShipperExternalId(shipperId, externalId);
		if (cachedService != null)
		{
			return cachedService;
		}
		return createShipperService(shipperId, externalId, name);
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
						.map(CarrierShipmentOrderServiceRepository::fromRecord)
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

	private CarrierService createShipperService(@NonNull final ShipperId shipperId, @NonNull final String externalId, @NonNull final String name)
	{
		final I_Carrier_Service po = InterfaceWrapperHelper.newInstance(I_Carrier_Service.class);
		po.setM_Shipper_ID(shipperId.getRepoId());
		po.setExternalId(externalId);
		po.setName(name);
		InterfaceWrapperHelper.saveRecord(po);
		return fromRecord(po);
	}
}
