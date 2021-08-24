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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.CreateProjectRequest;
import de.metas.project.service.ProjectService;
import de.metas.quantity.Quantity;
import de.metas.request.RequestId;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.servicerepair.project.model.PartOwnership;
import de.metas.servicerepair.project.model.ServiceRepairProjectConsumptionSummary;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import de.metas.servicerepair.project.repository.ServiceRepairProjectConsumptionSummaryRepository;
import de.metas.servicerepair.project.repository.ServiceRepairProjectCostCollectorRepository;
import de.metas.servicerepair.project.repository.ServiceRepairProjectTaskRepository;
import de.metas.servicerepair.project.repository.requests.CreateProjectCostCollectorRequest;
import de.metas.servicerepair.project.repository.requests.CreateRepairProjectTaskRequest;
import de.metas.servicerepair.project.repository.requests.CreateSparePartsProjectTaskRequest;
import de.metas.servicerepair.project.service.commands.CreateQuotationFromProjectCommand;
import de.metas.servicerepair.project.service.commands.CreateServiceRepairProjectCommand;
import de.metas.servicerepair.project.service.requests.AddQtyToProjectTaskRequest;
import de.metas.servicerepair.project.service.requests.CreateQuotationFromProjectRequest;
import de.metas.servicerepair.repair_order.RepairManufacturingCostCollector;
import de.metas.servicerepair.repair_order.RepairManufacturingOrderInfo;
import de.metas.servicerepair.repair_order.RepairManufacturingOrderService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Project;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderAndCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceRepairProjectService
{
	public static final AdWindowId AD_WINDOW_ID = AdWindowId.ofRepoId(541015); // FIXME hardcoded

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUReservationService huReservationService;
	private final RepairCustomerReturnsService repairCustomerReturnsService;
	private final RepairManufacturingOrderService repairManufacturingOrderService;
	private final ProjectService projectService;
	private final ServiceRepairProjectTaskRepository projectTaskRepository;
	private final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository;
	private final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository;

	public ServiceRepairProjectService(
			@NonNull final HUReservationService huReservationService,
			@NonNull final ProjectService projectService,
			@NonNull final RepairCustomerReturnsService repairCustomerReturnsService,
			@NonNull final RepairManufacturingOrderService repairManufacturingOrderService,
			@NonNull final ServiceRepairProjectTaskRepository projectTaskRepository,
			@NonNull final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository,
			@NonNull final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository)
	{
		this.huReservationService = huReservationService;
		this.projectService = projectService;
		this.repairCustomerReturnsService = repairCustomerReturnsService;
		this.repairManufacturingOrderService = repairManufacturingOrderService;
		this.projectTaskRepository = projectTaskRepository;
		this.projectCostCollectorRepository = projectCostCollectorRepository;
		this.projectConsumptionSummaryRepository = projectConsumptionSummaryRepository;
	}

	public ServiceRepairProjectInfo getById(@NonNull final ProjectId projectId)
	{
		return getByIdIfRepairProject(projectId)
				.orElseThrow(() -> new AdempiereException("Not a Service/Repair project: " + projectId));
	}

	private Optional<ServiceRepairProjectInfo> getByIdIfRepairProject(@NonNull final ProjectId projectId)
	{
		return toServiceRepairProjectInfo(projectService.getById(projectId));
	}

	private static Optional<ServiceRepairProjectInfo> toServiceRepairProjectInfo(@NonNull final I_C_Project record)
	{
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(record.getProjectCategory());
		if (!projectCategory.isServiceOrRepair())
		{
			return Optional.empty();
		}

		return Optional.of(ServiceRepairProjectInfo.builder()
				.projectId(ProjectId.ofRepoId(record.getC_Project_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.dateContract(TimeUtil.asLocalDate(record.getDateContract()))
				.dateFinish(TimeUtil.asZonedDateTime(record.getDateFinish()))
				.bpartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.bpartnerContactId(BPartnerContactId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getAD_User_ID()))
				.salesRepId(UserId.ofRepoIdOrNullIfSystem(record.getSalesRep_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.paymentTermId(PaymentTermId.ofRepoIdOrNull(record.getC_PaymentTerm_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoIdOrNull(record.getM_PriceList_Version_ID()))
				.campaignId(record.getC_Campaign_ID())
				.build());
	}

	private BPartnerId getProjectBPartnerId(@NonNull final ProjectId projectId)
	{
		return getById(projectId).getBpartnerId();
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
		return getByIdIfRepairProject(projectId).isPresent();
	}

	public ProjectId createProjectHeader(@NonNull final CreateProjectRequest request)
	{
		return projectService.createProject(request);
	}

	public void createProjectTask(@NonNull final CreateSparePartsProjectTaskRequest request)
	{
		final ServiceRepairProjectTask task = projectTaskRepository.createNew(request);

		for (final CreateSparePartsProjectTaskRequest.AlreadyReturnedQty alreadyReturnedQty : request.getAlreadyReturnedQtys())
		{
			reserveSparePartsFromHUs(task,
					alreadyReturnedQty.getQty(),
					ImmutableSet.of(alreadyReturnedQty.getSparePartsVhuId()),
					PartOwnership.OWNED_BY_CUSTOMER);
		}
	}

	public void createProjectTask(@NonNull final CreateRepairProjectTaskRequest request)
	{
		final ServiceRepairProjectTaskId taskId = projectTaskRepository.createNew(request);

		final ProjectId projectId = taskId.getProjectId();
		huReservationService.makeReservation(ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofProjectId(projectId))
				.productId(request.getProductId())
				.qtyToReserve(request.getQtyRequired())
				.customerId(getProjectBPartnerId(projectId))
				.huId(request.getRepairVhuId())
				.build())
				.orElseThrow(() -> new AdempiereException("Cannot make reservation"));
	}

	private void addQtyToProjectTask(@NonNull final AddQtyToProjectTaskRequest request)
	{
		projectTaskRepository.changeById(
				request.getTaskId(),
				task -> task.reduce(request));

		projectConsumptionSummaryRepository.change(
				ServiceRepairProjectConsumptionSummary.extractGroupingKey(request),
				summary -> summary.reduce(request));
	}

	public ServiceRepairProjectTask getTaskById(@NonNull final ServiceRepairProjectTaskId taskId)
	{
		return projectTaskRepository.getById(taskId);
	}

	public List<ServiceRepairProjectTask> getTaskByIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		return projectTaskRepository.getByIds(taskIds);
	}

	public OrderId createQuotationFromProject(@NonNull final CreateQuotationFromProjectRequest request)
	{
		return CreateQuotationFromProjectCommand.builder()
				.projectService(this)
				.projectId(request.getProjectId())
				.serviceProductId(request.getServiceProductId())
				.build()
				.execute();
	}

	public List<ServiceRepairProjectCostCollector> getByProjectIdButNotIncludedInCustomerQuotation(@NonNull final ProjectId projectId)
	{
		return projectCostCollectorRepository.getByProjectIdButNotIncludedInCustomerQuotation(projectId);
	}

	public void setCustomerQuotationToCostCollectors(@NonNull final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> map)
	{
		projectCostCollectorRepository.setCustomerQuotation(map);
	}

	public void reserveSparePartsFromHUs(
			@NonNull final ServiceRepairProjectTaskId taskId,
			@NonNull final Quantity qtyToReserve,
			@NonNull final ImmutableSet<HuId> fromHUIds)
	{
		final ServiceRepairProjectTask task = getTaskById(taskId);
		reserveSparePartsFromHUs(task, qtyToReserve, fromHUIds, PartOwnership.OWNED_BY_COMPANY);
	}

	private void reserveSparePartsFromHUs(
			@NonNull final ServiceRepairProjectTask task,
			@NonNull final Quantity qtyToReserve,
			@NonNull final ImmutableSet<HuId> fromHUIds,
			@NonNull final PartOwnership partOwnership)
	{
		final ProjectId projectId = task.getId().getProjectId();
		final HUReservation huReservation = huReservationService.makeReservation(ReserveHUsRequest.builder()
				.documentRef(HUReservationDocRef.ofProjectId(projectId))
				.productId(task.getProductId())
				.qtyToReserve(qtyToReserve)
				.customerId(getProjectBPartnerId(projectId))
				.huIds(fromHUIds)
				.build())
				.orElseThrow(() -> new AdempiereException("Cannot make reservation"));

		for (final HuId vhuId : huReservation.getVhuIds())
		{
			final Quantity qtyReserved = huReservation.getReservedQtyByVhuId(vhuId);

			createCostCollector(CreateProjectCostCollectorRequest.builder()
					.taskId(task.getId())
					.type(ServiceRepairProjectCostCollectorType.ofSparePartOwnership(partOwnership))
					.productId(task.getProductId())
					.qtyReserved(qtyReserved)
					.qtyConsumed(qtyReserved.toZero())
					.reservedVhuId(vhuId)
					.build());
		}
	}

	private void createCostCollector(@NonNull final CreateProjectCostCollectorRequest request)
	{
		final ServiceRepairProjectCostCollector costCollector = projectCostCollectorRepository.createNew(request);
		addQtyToProjectTask(extractAddQtyToProjectTaskRequest(costCollector));
	}

	public ImmutableSet<ServiceRepairProjectTaskId> retainIdsOfTypeSpareParts(final ImmutableSet<ServiceRepairProjectTaskId> taskIds)
	{
		return projectTaskRepository.retainIdsOfType(taskIds, ServiceRepairProjectTaskType.SPARE_PARTS);
	}

	public void releaseCompanyOwnedReservedSpareParts(final ImmutableSet<ServiceRepairProjectTaskId> taskIds)
	{
		final ImmutableSet<ServiceRepairProjectTaskId> sparePartsTaskIds = retainIdsOfTypeSpareParts(taskIds);
		if (sparePartsTaskIds.isEmpty())
		{
			throw new AdempiereException("No Spare Parts tasks");
		}

		final ImmutableList<ServiceRepairProjectCostCollector> costCollectorsToDelete = projectCostCollectorRepository.getByTaskIds(sparePartsTaskIds)
				.stream()
				.filter(ServiceRepairProjectCostCollector::isNotIncludedInCustomerQuotation)
				.filter(costCollector -> costCollector.getType() == ServiceRepairProjectCostCollectorType.SparePartsToBeInvoiced)
				.collect(ImmutableList.toImmutableList());

		deleteCostCollectors(costCollectorsToDelete, true);
	}

	private static AddQtyToProjectTaskRequest extractAddQtyToProjectTaskRequest(final ServiceRepairProjectCostCollector costCollector)
	{
		return AddQtyToProjectTaskRequest.builder()
				.taskId(costCollector.getTaskId())
				.productId(costCollector.getProductId())
				.qtyReserved(costCollector.getQtyReserved())
				.qtyConsumed(costCollector.getQtyConsumed())
				.build();
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

	public void createRepairOrders(@NonNull final List<ServiceRepairProjectTask> tasks)
	{
		Check.assumeNotEmpty(tasks, "tasks list shall not be empty");

		final ProjectId projectId = CollectionUtils.extractSingleElement(tasks, ServiceRepairProjectTask::getProjectId);
		final ServiceRepairProjectInfo project = getById(projectId);

		for (final ServiceRepairProjectTask task : tasks)
		{
			final PPOrderId repairOrderId = repairManufacturingOrderService.createRepairOrder(project, task);
			projectTaskRepository.save(task.withRepairOrderId(repairOrderId));
		}
	}

	public void recalculateSummary(@NonNull final ProjectId projectId)
	{
		final LinkedHashMap<ServiceRepairProjectConsumptionSummary.GroupingKey, ServiceRepairProjectConsumptionSummary> aggregates = new LinkedHashMap<>();
		for (final ServiceRepairProjectCostCollector costCollector : projectCostCollectorRepository.getByProjectId(projectId))
		{
			final ServiceRepairProjectConsumptionSummary aggregate = ServiceRepairProjectConsumptionSummary.builder()
					.groupingKey(ServiceRepairProjectConsumptionSummary.GroupingKey.builder()
							.projectId(costCollector.getId().getProjectId())
							.productId(costCollector.getProductId())
							.uomId(costCollector.getUomId())
							.build())
					.qtyReserved(costCollector.getQtyReserved())
					.qtyConsumed(costCollector.getQtyConsumed())
					.build();

			aggregates.merge(aggregate.getGroupingKey(), aggregate, ServiceRepairProjectConsumptionSummary::combine);
		}

		projectConsumptionSummaryRepository.saveProject(projectId, aggregates.values());
	}

	public void importCostsFromRepairOrderAndMarkTaskCompleted(@NonNull final RepairManufacturingOrderInfo repairOrder)
	{
		ServiceRepairProjectTask task = projectTaskRepository
				.getTaskByRepairOrderId(repairOrder.getProjectId(), repairOrder.getId())
				.orElseThrow(() -> new AdempiereException("No task found for " + repairOrder));

		final Set<PPOrderAndCostCollectorId> alreadyImportedRepairCostCollectorIds = projectCostCollectorRepository.retainExistingPPCostCollectorIds(
				repairOrder.getProjectId(),
				repairOrder.getCostCollectorIds());

		for (final RepairManufacturingCostCollector mfgCostCollector : repairOrder.getCostCollectors())
		{
			// skip already imported cost collectors
			if (alreadyImportedRepairCostCollectorIds.contains(mfgCostCollector.getId()))
			{
				continue;
			}

			createCostCollector(toCreateProjectCostCollectorRequest(task, mfgCostCollector));
		}

		if (!projectCostCollectorRepository.matchesByTaskAndProduct(task.getId(), repairOrder.getRepairedProductId()))
		{
			final ProductId repairedProductId = repairOrder.getRepairedProductId();
			final HuId repairedVhuId = Objects.requireNonNull(task.getRepairVhuId());
			final AttributeSetInstanceId asiId = handlingUnitsBL.createASIFromHUAttributes(repairedProductId, repairedVhuId);

			createCostCollector(CreateProjectCostCollectorRequest.builder()
					.taskId(task.getId())
					.type(ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
					.productId(repairedProductId)
					.asiId(asiId)
					.warrantyCase(task.getWarrantyCase())
					.qtyReserved(repairOrder.getRepairedQty())
					.reservedVhuId(repairedVhuId)
					.build());
		}

		task = task.withRepairOrderDone(true);
		projectTaskRepository.save(task);
	}

	private static CreateProjectCostCollectorRequest toCreateProjectCostCollectorRequest(
			@NonNull final ServiceRepairProjectTask task,
			@NonNull final RepairManufacturingCostCollector consumedComponentOrResource)
	{
		return CreateProjectCostCollectorRequest.builder()
				.taskId(task.getId())
				.type(ServiceRepairProjectCostCollectorType.RepairingConsumption)
				.productId(consumedComponentOrResource.getProductId())
				.qtyConsumed(consumedComponentOrResource.getQtyConsumed())
				.repairOrderCostCollectorId(consumedComponentOrResource.getId())
				.warrantyCase(task.getWarrantyCase())
				.build();
	}

	public void unimportCostsFromRepairOrder(@NonNull final RepairManufacturingOrderInfo repairOrder)
	{
		final ServiceRepairProjectTaskId taskId = projectTaskRepository
				.getTaskIdByRepairOrderId(repairOrder.getProjectId(), repairOrder.getId())
				.orElseThrow(() -> new AdempiereException("No task found for " + repairOrder));

		final ImmutableList<ServiceRepairProjectCostCollector> costCollectorsToDelete = projectCostCollectorRepository
				.getByTaskId(taskId)
				.stream()
				.filter(ServiceRepairProjectCostCollector::isNotIncludedInCustomerQuotation)
				.collect(ImmutableList.toImmutableList());

		deleteCostCollectors(costCollectorsToDelete, false);
	}

	private void deleteCostCollectors(
			@NonNull final Collection<ServiceRepairProjectCostCollector> costCollectors,
			final boolean deleteHUReservations)
	{
		if (costCollectors.isEmpty())
		{
			return;
		}

		final HashSet<HuId> reservedVHUIds = new HashSet<>();
		final HashSet<ServiceRepairProjectCostCollectorId> costCollectorIdsToDelete = new HashSet<>();
		final ArrayList<AddQtyToProjectTaskRequest> addQtyToProjectTaskRequests = new ArrayList<>();
		for (final ServiceRepairProjectCostCollector costCollector : costCollectors)
		{
			if (deleteHUReservations && costCollector.getVhuId() != null)
			{
				reservedVHUIds.add(costCollector.getVhuId());
			}

			addQtyToProjectTaskRequests.add(extractAddQtyToProjectTaskRequest(costCollector).negate());
			costCollectorIdsToDelete.add(costCollector.getId());
		}

		huReservationService.deleteReservations(reservedVHUIds);
		projectCostCollectorRepository.deleteByIds(costCollectorIdsToDelete);
		addQtyToProjectTaskRequests.forEach(this::addQtyToProjectTask);
	}

	public void unlinkQuotationFromProject(@NonNull final ProjectId projectId, @NonNull final OrderId quotationId)
	{
		projectCostCollectorRepository.unsetCustomerQuotation(projectId, quotationId);
	}

	public void unlinkProposalLineFromProject(@NonNull final OrderAndLineId proposalLineId)
	{
		projectCostCollectorRepository.unsetCustomerQuotationLine(proposalLineId);
	}
}
