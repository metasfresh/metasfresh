/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job_schedule.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.inout.PriorityRule;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.order.OrderId;
import de.metas.order.OrderPickingType;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.shipping.CarrierProductId;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceRepository;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesCommand.SYSCONFIG_CARRIER_PRODUCT_REQUIRED;

public class PickingJobScheduleAutoAssignCommand
{
	// Constants
	private static final int DEFAULT_QUERY_LIMIT = 1000;
	@NonNull private static final String SYSCONFIG_QUERY_LIMIT = "de.metas.handlingunits.picking.job_schedule.service.commands.PickingJobScheduleAutoAssignCommand.QueryLimit";

	// Services
	@NonNull private final WorkplaceRepository workplaceRepository;
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;
	@NonNull private final PickingJobShipmentScheduleService pickingJobShipmentScheduleService;
	@NonNull private final ISysConfigBL sysConfigBL;
	@NonNull private final PickingJobProductService pickingJobProductService;

	// Params
	@NonNull private final PickingJobScheduleAutoAssignRequest request;

	// State
	private ImmutableList<Workplace> workplaces;
	private WorkplacesCapacity workplacesCapacity;
	private ImmutableMap<ProductId, Product> productsById;
	private ImmutableMap<OrderId, BigDecimal> totalQtyToDeliverByOrderId;

	@Builder
	private PickingJobScheduleAutoAssignCommand(
			@NonNull final WorkplaceRepository workplaceRepository,
			@NonNull final PickingJobScheduleRepository pickingJobScheduleRepository,
			@NonNull final PickingJobShipmentScheduleService pickingJobShipmentScheduleService,
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final PickingJobProductService pickingJobProductService,
			@NonNull final PickingJobScheduleAutoAssignRequest request)
	{
		this.workplaceRepository = workplaceRepository;
		this.pickingJobScheduleRepository = pickingJobScheduleRepository;
		this.pickingJobShipmentScheduleService = pickingJobShipmentScheduleService;
		this.sysConfigBL = sysConfigBL;
		this.pickingJobProductService = pickingJobProductService;
		this.request = request;
	}

	public void execute()
	{
		workplaces = workplaceRepository.getAllActive()
				.stream()
				.filter(workplace -> workplace.getMaxPickingJobs() > 0)
				.collect(ImmutableList.toImmutableList());

		if (workplaces.isEmpty())
		{
			return;
		}

		final ImmutableSet<WarehouseId> warehouseIds = workplaces.stream()
				.map(Workplace::getWarehouseId)
				.collect(ImmutableSet.toImmutableSet());

		final QueryLimit queryLimit = getQueryLimit();
		final ImmutableList<ShipmentSchedule> shipmentSchedulesCandidates = pickingJobShipmentScheduleService.getBy(
						ShipmentScheduleQuery.builder()
								.warehouseIds(warehouseIds)
								.preparationDate(request.getPreparationDate())
								.fromCompleteOrderOrNullOrder(true)
								.includeWithQtyToDeliverZero(false)
								.includeProcessed(false)
								.includeInvalid(false)
								.isScheduledForPicking(false)
								.orderByOrderId(true)
								.limit(queryLimit)
								.build());

		if (shipmentSchedulesCandidates.isEmpty()) {return;}

		// On limit reach exclude the last order to avoid having the order only partially included
		final OrderId orderIdToExclude = queryLimit.isLimitHitOrExceeded(shipmentSchedulesCandidates) ?
				shipmentSchedulesCandidates.get(shipmentSchedulesCandidates.size() - 1).getOrderId()
				: null;
		final boolean isCarrierProductRequired = isCarrierProductRequired();
		final ImmutableList<ShipmentSchedule> allEligibleShipmentSchedules = shipmentSchedulesCandidates
				.stream()
				.filter(sched -> sched.getCarrierProductId() != null || !isCarrierProductRequired)
				.filter(shipmentSchedule -> orderIdToExclude == null || !OrderId.equals(shipmentSchedule.getOrderId(), orderIdToExclude))
				.collect(ImmutableList.toImmutableList());

		if (allEligibleShipmentSchedules.isEmpty()) {return;}

		final ImmutableSet<WorkplaceId> workplaceIds = workplaces.stream()
				.map(Workplace::getId)
				.collect(ImmutableSet.toImmutableSet());

		final List<PickingJobSchedule> existingPickingJobSchedules = pickingJobScheduleRepository.stream(
				PickingJobScheduleQuery.builder()
						.workplaceIds(workplaceIds)
						.isProcessed(false)
						.build()
		).collect(ImmutableList.toImmutableList());

		workplacesCapacity = new WorkplacesCapacity();
		existingPickingJobSchedules.forEach(jobSched ->
				workplacesCapacity.increase(jobSched.getWorkplaceId())
		);

		final ImmutableSet<ProductId> productIds = allEligibleShipmentSchedules.stream()
				.map(ShipmentSchedule::getProductId)
				.collect(ImmutableSet.toImmutableSet());
		productsById = pickingJobProductService.getByIdsAsMap(productIds);

		totalQtyToDeliverByOrderId = allEligibleShipmentSchedules.stream()
				.filter(sched -> sched.getOrderId() != null)
				.collect(ImmutableMap.toImmutableMap(
						ShipmentSchedule::getOrderId,
						schedule -> schedule.getQuantityToDeliver().toBigDecimal(),
						BigDecimal::add
				));

		for (final ShipmentSchedule schedule : allEligibleShipmentSchedules)
		{
			final Workplace matchingWorkplace = findMatchingWorkplace(schedule);
			if (matchingWorkplace == null)
			{
				continue;
			}

			CreateOrUpdatePickingJobSchedulesCommand.builder()
					.pickingJobScheduleRepository(pickingJobScheduleRepository)
					.pickingJobShipmentScheduleService(pickingJobShipmentScheduleService)
					.request(CreateOrUpdatePickingJobSchedulesRequest.builder()
							.workplaceId(matchingWorkplace.getId())
							.shipmentScheduleAndJobScheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(schedule.getId()))
							.qtyToPickBD(schedule.getQuantityToDeliver().toBigDecimal())
							.build())
					.build()
					.execute();

			workplacesCapacity.increase(matchingWorkplace.getId());
		}
	}

	@NonNull
	private QueryLimit getQueryLimit()
	{
		return QueryLimit.ofInt(sysConfigBL.getIntValue(SYSCONFIG_QUERY_LIMIT, DEFAULT_QUERY_LIMIT));
	}

	@NonNull
	private boolean isCarrierProductRequired()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_CARRIER_PRODUCT_REQUIRED, false);
	}

	@Nullable
	private Workplace findMatchingWorkplace(
			@NonNull final ShipmentSchedule schedule)
	{
		for (final Workplace workplace : workplaces)
		{
			if (!workplacesCapacity.hasCapacity(workplace))
			{
				continue;
			}

			if (!WarehouseId.equals(workplace.getWarehouseId(), schedule.getWarehouseId()))
			{
				continue;
			}

			if (!isOrderPickingTypeCompatible(workplace.getOrderPickingType(), schedule))
			{
				continue;
			}

			if (!isProductCompatible(workplace, schedule))
			{
				continue;
			}

			if (!isCarrierCompatible(workplace, schedule))
			{
				continue;
			}

			if (!isExternalSystemCompatible(workplace, schedule))
			{
				continue;
			}

			if (!isPriorityCompatible(workplace, schedule))
			{
				continue;
			}

			return workplace;
		}

		return null;
	}

	private boolean isOrderPickingTypeCompatible(
			@Nullable final OrderPickingType orderPickingType,
			@NonNull final ShipmentSchedule schedule)
	{
		if (orderPickingType == null)
		{
			return true;
		}

		final BigDecimal totalQtyToDeliver = schedule.getOrderId() == null ? schedule.getQuantityToDeliver().toBigDecimal()
				: totalQtyToDeliverByOrderId.get(schedule.getOrderId());

		switch (orderPickingType)
		{
			case Single:
				return totalQtyToDeliver.compareTo(BigDecimal.ONE) == 0;
			case Multiple:
				return totalQtyToDeliver.compareTo(BigDecimal.ONE) > 0;
			default:
				return true;
		}
	}

	private boolean isProductCompatible(
			@NonNull final Workplace workplace,
			@NonNull final ShipmentSchedule schedule)
	{
		final ImmutableSet<ProductId> workplaceProducts = workplace.getProductIds();
		final ImmutableSet<ProductCategoryId> workplaceCategories = workplace.getProductCategoryIds();
		if (workplaceProducts.isEmpty() && workplaceCategories.isEmpty())
		{
			return true;
		}

		final ProductId scheduleProductId = schedule.getProductId();
		if (!workplaceProducts.isEmpty() && workplaceProducts.contains(scheduleProductId))
		{
			return true;
		}

		final ProductCategoryId productCategoryId = productsById.get(scheduleProductId).getProductCategoryId();
		return !workplaceCategories.isEmpty() && workplaceCategories.contains(productCategoryId);
	}

	private boolean isCarrierCompatible(
			@NonNull final Workplace workplace,
			@NonNull final ShipmentSchedule schedule)
	{
		final ImmutableSet<CarrierProductId> workplaceCarrierProducts = workplace.getCarrierProductIds();
		if (workplaceCarrierProducts.isEmpty())
		{
			return true;
		}

		final CarrierProductId carrierProductId = schedule.getCarrierProductId();
		if (carrierProductId == null)
		{
			return false;
		}

		return workplaceCarrierProducts.contains(carrierProductId);
	}

	private boolean isExternalSystemCompatible(
			@NonNull final Workplace workplace,
			@NonNull final ShipmentSchedule schedule)
	{
		final ImmutableSet<ExternalSystemId> workplaceExternalSystems = workplace.getExternalSystemIds();
		if (workplaceExternalSystems.isEmpty())
		{
			return true;
		}

		final ExternalSystemId externalSystemId = schedule.getExternalSystemId();
		if (externalSystemId == null)
		{
			return false;
		}

		return workplaceExternalSystems.contains(externalSystemId);
	}

	private boolean isPriorityCompatible(
			@NonNull final Workplace workplace,
			@NonNull final ShipmentSchedule schedule)
	{
		final PriorityRule priorityRule = workplace.getPriorityRule();

		if (priorityRule == null)
		{
			return true;
		}

		return PriorityRule.equals(priorityRule, schedule.getPriorityRule());
	}

	private static class WorkplacesCapacity
	{
		private final Map<WorkplaceId, Integer> assignedCountPerWorkplace = new HashMap<>();

		void increase(@NonNull final WorkplaceId workplaceId)
		{
			assignedCountPerWorkplace.merge(workplaceId, 1, Integer::sum);
		}

		boolean hasCapacity(@NonNull final Workplace workplace)
		{
			final int currentAssigned = assignedCountPerWorkplace.getOrDefault(workplace.getId(), 0);
			return currentAssigned < workplace.getMaxPickingJobs();
		}
	}
}
