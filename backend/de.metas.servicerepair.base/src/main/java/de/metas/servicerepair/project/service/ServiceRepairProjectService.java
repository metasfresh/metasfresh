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
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.material.planning.ProductPlanningService;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.CreateProjectRequest;
import de.metas.project.service.ProjectService;
import de.metas.quantity.Quantity;
import de.metas.request.RequestId;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
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
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Project;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceRepairProjectService
{
	public static final AdWindowId AD_WINDOW_ID = AdWindowId.ofRepoId(541015); // FIXME hardcoded

	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final ProductPlanningService productPlanningService;
	private final HUReservationService huReservationService;
	private final RepairCustomerReturnsService repairCustomerReturnsService;
	private final ProjectService projectService;
	private final ServiceRepairProjectTaskRepository projectTaskRepository;
	private final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository;
	private final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository;

	public ServiceRepairProjectService(
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final ProjectService projectService,
			@NonNull final RepairCustomerReturnsService repairCustomerReturnsService,
			@NonNull final ServiceRepairProjectTaskRepository projectTaskRepository,
			@NonNull final ServiceRepairProjectCostCollectorRepository projectCostCollectorRepository,
			@NonNull final ServiceRepairProjectConsumptionSummaryRepository projectConsumptionSummaryRepository)
	{
		this.productPlanningService = productPlanningService;
		this.huReservationService = huReservationService;
		this.projectService = projectService;
		this.repairCustomerReturnsService = repairCustomerReturnsService;
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
		projectTaskRepository.createNew(request);
	}
	public void createProjectTask(@NonNull final CreateRepairProjectTaskRequest request)
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

	public List<ServiceRepairProjectTask> getTaskByIds(@NonNull final Set<ServiceRepairProjectTaskId> taskIds)
	{
		return projectTaskRepository.getByIds(taskIds);
	}

	public OrderId createQuotationFromProject(final ProjectId projectId)
	{
		return CreateQuotationFromProjectCommand.builder()
				.projectService(this)
				.projectId(projectId)
				.build()
				.execute();
	}

	public List<ServiceRepairProjectCostCollector> getCostCollectorsByProjectButNotInProposal(@NonNull final ProjectId projectId)
	{
		return projectCostCollectorRepository.getCostCollectorsByProjectButNotInProposal(projectId);
	}

	public void setCustomerQuotationToCostCollectors(@NonNull final Map<ServiceRepairProjectCostCollectorId, OrderAndLineId> map)
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
		return projectTaskRepository.retainIdsOfType(taskIds, ServiceRepairProjectTaskType.SPARE_PARTS);
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

	public void createRepairOrders(@NonNull final List<ServiceRepairProjectTask> tasks)
	{
		Check.assumeNotEmpty(tasks, "tasks list shall not be empty");

		final ProjectId projectId = CollectionUtils.extractSingleElement(tasks, ServiceRepairProjectTask::getProjectId);
		final ServiceRepairProjectInfo project = getById(projectId);

		tasks.forEach(task -> createRepairOrder(project, task));
	}

	private void createRepairOrder(
			@NonNull final ServiceRepairProjectInfo project,
			@NonNull final ServiceRepairProjectTask task)
	{
		if (!ServiceRepairProjectTaskType.REPAIR_ORDER.equals(task.getType()))
		{
			throw new AdempiereException("Not an repair order task: " + task);
		}
		if (!ServiceRepairProjectTaskStatus.NOT_STARTED.equals(task.getStatus()))
		{
			throw new AdempiereException("Task already started: " + task);
		}
		if (task.getRepairOrderId() != null)
		{
			throw new AdempiereException("A Repair Order was already created: " + task);
		}

		final WarehouseId warehouseId = project.getWarehouseId();
		if (warehouseId == null)
		{
			throw new AdempiereException("No warehouse for " + project);
		}
		final ResourceId plantId = productPlanningService.getPlantOfWarehouse(warehouseId)
				.orElseThrow(() -> new AdempiereException("No plant for warehouse " + warehouseId));

		final Instant now = SystemTime.asInstant();
		final I_PP_Order repairOrder = ppOrderBL.createOrder(PPOrderCreateRequest.builder()
				.docBaseType(PPOrderDocBaseType.REPAIR_ORDER)
				.clientAndOrgId(task.getClientAndOrgId())
				.warehouseId(warehouseId)
				.plantId(plantId)
				//
				.productId(task.getProductId())
				// TODO .attributeSetInstanceId()
				.qtyRequired(task.getQtyRequired())
				//
				.dateOrdered(now)
				.datePromised(now)
				.dateStartSchedule(now)
				//
				.completeDocument(true)
				//
				.build());
		final PPOrderId repairOrderId = PPOrderId.ofRepoId(repairOrder.getPP_Order_ID());

		huPPOrderQtyDAO.save(CreateReceiptCandidateRequest.builder()
				.orderId(repairOrderId)
				.orgId(OrgId.ofRepoId(repairOrder.getAD_Org_ID()))
				.date(TimeUtil.asZonedDateTime(now))
				.locatorId(LocatorId.ofRepoId(repairOrder.getM_Warehouse_ID(), repairOrder.getM_Locator_ID()))
				.topLevelHUId(task.getRepairVhuId())
				.productId(task.getProductId())
				.qtyToReceive(task.getQtyRequired())
				.alreadyProcessed(true)
				.build());

		projectTaskRepository.save(task.withRepairOrderId(repairOrderId));
	}
}
