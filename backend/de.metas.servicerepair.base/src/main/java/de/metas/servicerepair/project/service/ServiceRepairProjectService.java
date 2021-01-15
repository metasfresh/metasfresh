/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.CreateProjectRequest;
import de.metas.project.service.ProjectService;
import de.metas.quantity.Quantity;
import de.metas.request.RequestId;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.servicerepair.project.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.ServiceRepairProjectTask;
import de.metas.servicerepair.project.ServiceRepairProjectTaskId;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class ServiceRepairProjectService
{
	public static final AdWindowId AD_WINDOW_ID = AdWindowId.ofRepoId(541015); // FIXME hardcoded

	private final HUReservationService huReservationService;
	private final RepairCustomerReturnsService repairCustomerReturnsService;
	private final ProjectService projectService;
	private final ServiceRepairProjectTaskRepository projectTaskRepository;
	private final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository;
	private final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository;

	public ServiceRepairProjectService(
			@NonNull final HUReservationService huReservationService,
			@NonNull final ProjectService projectService,
			@NonNull final RepairCustomerReturnsService repairCustomerReturnsService,
			@NonNull final ServiceRepairProjectTaskRepository projectTaskRepository,
			@NonNull final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository,
			@NonNull final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository)
	{
		this.huReservationService = huReservationService;
		this.projectService = projectService;
		this.repairCustomerReturnsService = repairCustomerReturnsService;
		this.projectTaskRepository = projectTaskRepository;
		this.projectCostCollectorRepository = projectCostCollectorRepository;
		this.projectConsumptionSummaryRepository = projectConsumptionSummaryRepository;
	}

	public I_C_Project getById(@NonNull final ProjectId projectId)
	{
		return projectService.getById(projectId);
	}

	public ProjectId createProjectFromRequest(final RequestId requestId)
	{
		return CreateServiceRepairProjectCommand.builder()
				.projectService(this)
				.customerReturnsService(repairCustomerReturnsService)
				.requestId(requestId)
				.build()
				.execute();
	}

	public boolean isServiceOrRepair(@NonNull final ProjectId projectId)
	{
		final I_C_Project project = getById(projectId);
		return isServiceOrRepair(project);
	}

	public boolean isServiceOrRepair(@NonNull final I_C_Project project)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory());
		return projectCategory.isServiceOrRepair();
	}

	ProjectId createProject(@NonNull final CreateProjectRequest request)
	{
		return projectService.createProject(request);
	}

	void createProjectTask(@NonNull final CreateProjectTaskRequest request)
	{
		projectTaskRepository.createNew(request);
	}

	private void addQtyToProjectTask(@NonNull final AddQtyToProjectTaskRequest request)
	{
		final ServiceRepairProjectTask changedTask = projectTaskRepository.changeById(
				request.getTaskId(),
				task -> task.reduce(request));

		projectConsumptionSummaryRepository.change(
				request.getTaskId().getProjectId(),
				changedTask.getProductId(),
				request.getUomId(),
				summary -> summary.reduce(request));
	}

	public ServiceRepairProjectTask getTaskById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		return projectTaskRepository.getById(taskId);
	}

	public OrderId createQuotationFromProject(final ProjectId projectId)
	{
		return CreateQuotationFromProjectCommand.builder()
				.projectService(this)
				.projectId(projectId)
				.build()
				.execute();
	}

	List<ServiceRepairProjectCostCollector> getCostCollectorsByProjectButNotInProposal(@NonNull final ProjectId projectId)
	{
		return projectCostCollectorRepository.getCostCollectorsByProjectButNotInProposal(projectId);
	}

	void setCustomerQuotationToCostCollectors(@NonNull final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> map)
	{
		projectCostCollectorRepository.setCustomerQuotationToCostCollectors(map);
	}

	public void reserveSparePartsFromHUs(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final ImmutableSet<HuId> fromHUIds)
	{
		final I_C_Project project = projectService.getById(taskId.getProjectId());
		final ServiceRepairProjectTask task = getTaskById(taskId);

		final HUReservation huReservation = huReservationService.makeReservation(ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofProjectId(task.getId().getProjectId()))
				.productId(task.getProductId())
				.qtyToReserve(task.getQtyToReserve())
				.customerId(BPartnerId.ofRepoId(project.getC_BPartner_ID()))
				.huIds(fromHUIds)
				.build())
				.orElse(null);
		if (huReservation == null)
		{
			throw new AdempiereException("Cannot make reservation");
		}

		for (final HuId vhuId : huReservation.getVhuIds())
		{
			final Quantity qtyReserved = huReservation.getReservedQtyByVhuId(vhuId);
			final Quantity qtyConsumed = qtyReserved.toZero();

			projectCostCollectorRepository.createNew(CreateProjectCostCollectorRequest.builder()
					.taskId(task.getId())
					.productId(task.getProductId())
					.qtyReserved(qtyReserved)
					.qtyConsumed(qtyConsumed)
					.reservedVhuId(vhuId)
					.build());
		}

		addQtyToProjectTask(AddQtyToProjectTaskRequest.builder()
				.taskId(task.getId())
				.qtyReserved(huReservation.getReservedQtySum())
				.build());
	}

	public ImmutableSet<ServiceRepairProjectTaskId> retainIdsOfTypeSpareParts(final ImmutableSet<ServiceRepairProjectTaskId> taskIds)
	{
		return projectTaskRepository.retainIdsOfTypeSpareParts(taskIds);
	}

	public void releaseReservedSpareParts(final ImmutableSet<ServiceRepairProjectTaskId> taskIds)
	{
		final ImmutableSet<ServiceRepairProjectTaskId> sparePartsTaskIds = retainIdsOfTypeSpareParts(taskIds);
		if (sparePartsTaskIds.isEmpty())
		{
			throw new AdempiereException("No Spare Parts tasks");
		}

		final List<ServiceRepairProjectCostCollector> costCollectors = projectCostCollectorRepository.getAndDeleteByTaskIds(taskIds);

		final ImmutableSet<HuId> reservedSparePartsVHUIds = costCollectors.stream()
				.map(ServiceRepairProjectCostCollector::getReservedSparePartsVHUId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		huReservationService.deleteReservations(reservedSparePartsVHUIds);

		for (final ServiceRepairProjectCostCollector costCollector : costCollectors)
		{
			addQtyToProjectTask(AddQtyToProjectTaskRequest.builder()
					.taskId(costCollector.getTaskId())
					.qtyReserved(costCollector.getQtyReserved().negate())
					.qtyConsumed(costCollector.getQtyConsumed().negate())
					.build());
		}
	}

	public boolean isSparePartsTask(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		final ImmutableSet<ServiceRepairProjectTaskId> result = retainIdsOfTypeSpareParts(ImmutableSet.of(taskId));
		return result.size() == 1
				&& ServiceRepairProjectTaskId.equals(result.asList().get(0), taskId);
	}

	public List<ServiceRepairProjectCostCollector> getCostCollectorsByQuotationLineIds(@NonNull final Set<OrderAndLineId> quotationLineIds)
	{
		return projectCostCollectorRepository.getByQuotationLineIds(quotationLineIds);
	}

	public void transferVHUsFromProjectToSalesOrderLine(
			@NonNull final ProjectId fromProjectId,
			@NonNull final OrderAndLineId salesOrderLineId,
			@NonNull final Set<HuId> vhuIds)
	{
		huReservationService.transferReservation(
				HUReservationDocRef.ofProjectId(fromProjectId),
				HUReservationDocRef.ofSalesOrderLineId(salesOrderLineId.getOrderLineId()),
				vhuIds);
	}

	public void transferVHUsFromSalesOrderToProject(
			@NonNull final Set<OrderAndLineId> fromSalesOrderLineIds,
			@NonNull final ProjectId toProjectId)
	{
		final ImmutableSet<HUReservationDocRef> from = fromSalesOrderLineIds.stream()
				.map(HUReservationDocRef::ofSalesOrderLineId)
				.collect(ImmutableSet.toImmutableSet());

		huReservationService.transferReservation(from, HUReservationDocRef.ofProjectId(toProjectId));
	}
}
