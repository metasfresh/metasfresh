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

package de.metas.handlingunits.picking.job.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.model.I_M_Picking_Job_Line_Carrier_Service;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Carrier_Service;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

/**
 * Repository that deals with {@link I_Carrier_Service} records assigned to a picking-job line via
 * {@link I_M_Picking_Job_Line_Carrier_Service}.
 * <p>
 * Line-side counterpart of {@code de.metas.inoutcandidate.ShipmentScheduleCarrierServiceRepository}.
 *
 * Repository Tables: M_Picking_Job_Line_Carrier_Service
 * Repository Cluster: PickingJobLineCarrierServiceRepository
 */
@Repository
public class PickingJobLineCarrierServiceRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static PickingJobLineCarrierServiceRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(PickingJobLineCarrierServiceRepository.class,
				PickingJobLineCarrierServiceRepository::new);
	}

	public ImmutableSetMultimap<PickingJobLineId, CarrierServiceId> getAssignedServiceIdsMapByLineIds(@NonNull final Collection<PickingJobLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_M_Picking_Job_Line_Carrier_Service.class)
				.addInArrayFilter(I_M_Picking_Job_Line_Carrier_Service.COLUMNNAME_M_Picking_Job_Line_ID, lineIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> PickingJobLineId.ofRepoId(record.getM_Picking_Job_Line_ID()),
						record -> CarrierServiceId.ofRepoId(record.getCarrier_Service_ID())));
	}

	public void assignServicesToLine(@NonNull final PickingJobLineId lineId, @NonNull final Set<CarrierServiceId> serviceIds)
	{
		queryBL.createQueryBuilder(I_M_Picking_Job_Line_Carrier_Service.class)
				.addEqualsFilter(I_M_Picking_Job_Line_Carrier_Service.COLUMNNAME_M_Picking_Job_Line_ID, lineId)
				.create()
				.delete();

		final ImmutableSet<I_M_Picking_Job_Line_Carrier_Service> assignedCarrierServices = serviceIds.stream()
				.map(serviceId -> {
					final I_M_Picking_Job_Line_Carrier_Service po = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line_Carrier_Service.class);
					po.setM_Picking_Job_Line_ID(lineId.getRepoId());
					po.setCarrier_Service_ID(CarrierServiceId.toRepoId(serviceId));
					return po;
				})
				.collect(ImmutableSet.toImmutableSet());

		InterfaceWrapperHelper.saveAll(assignedCarrierServices);
	}
}
