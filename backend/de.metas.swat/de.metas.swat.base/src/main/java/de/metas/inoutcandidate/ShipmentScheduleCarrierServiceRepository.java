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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Service;
import org.compiere.model.I_M_ShipmentSchedule_Carrier_Service;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * Repository that deals with {@link I_Carrier_Service} records assigned via {@link I_M_ShipmentSchedule_Carrier_Service}.
 */
@Repository
public class ShipmentScheduleCarrierServiceRepository
{

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ShipmentScheduleCarrierServiceRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ShipmentScheduleCarrierServiceRepository.class ,
				ShipmentScheduleCarrierServiceRepository::new);
	}

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
}
