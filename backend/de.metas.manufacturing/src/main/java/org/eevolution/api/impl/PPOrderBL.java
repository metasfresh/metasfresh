/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.eevolution.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.planning.WorkingTime;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.OrderQtyChangeRequest;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivityTemplateId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_WF_Node_Template;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.api.PPOrderScheduleChangeRequest;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.X_PP_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class PPOrderBL implements IPPOrderBL
{
	private static final Logger logger = LogManager.getLogger(PPOrderBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL orderBOMService = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPRoutingRepository routingRepo = Services.get(IPPRoutingRepository.class);
	private final IPPOrderRoutingRepository orderRoutingRepo = Services.get(IPPOrderRoutingRepository.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IPPCostCollectorBL costCollectorsService = Services.get(IPPCostCollectorBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@VisibleForTesting
	static final String SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS = "de.metas.manufacturing.PP_Order.canBeExportedAfterSeconds";

	@Override
	public I_PP_Order getById(@NonNull final PPOrderId id)
	{
		return ppOrdersRepo.getById(id);
	}

	@Override
	public void save(final I_PP_Order ppOrder)
	{
		ppOrdersRepo.save(ppOrder);
	}

	@Override
	public I_PP_Order createOrder(@NonNull final PPOrderCreateRequest request)
	{
		return CreateOrderCommand.builder()
				.request(request)
				.build()
				.execute();
	}

	@Override
	public Stream<I_PP_Order> streamManufacturingOrders(final ManufacturingOrderQuery query)
	{
		return ppOrdersRepo.streamManufacturingOrders(query);
	}

	@Override
	public void setDefaults(final I_PP_Order ppOrder)
	{
		ppOrder.setLine(10);
		ppOrder.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);
		ppOrder.setDescription("");
		// ppOrder.setQtyDelivered(BigDecimal.ZERO);
		// ppOrder.setQtyReject(BigDecimal.ZERO);
		// ppOrder.setQtyScrap(BigDecimal.ZERO);
		ppOrder.setIsSelected(false);
		ppOrder.setIsSOTrx(false);
		ppOrder.setIsApproved(false);
		ppOrder.setIsPrinted(false);
		ppOrder.setProcessed(false);
		ppOrder.setProcessing(false);
		ppOrder.setPosted(false);
		setDocType(ppOrder, PPOrderDocBaseType.MANUFACTURING_ORDER, /* docSubType */null);
		ppOrder.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		ppOrder.setDocAction(X_PP_Order.DOCACTION_Complete);
	}

	@Override
	public void addDescription(
			final I_PP_Order order,
			final String description)
	{
		final String desc = order.getDescription();
		if (desc == null || desc.isEmpty())
		{
			order.setDescription(description);
		}
		else
		{
			order.setDescription(desc + " | " + description);
		}
	}    // addDescription

	@Override
	public void updateQtyBatchs(
			@NonNull final I_PP_Order order,
			final boolean alwaysUpdateQtyBatchSize)
	{
		BigDecimal qtyBatchSize = order.getQtyBatchSize();
		if (qtyBatchSize.signum() == 0 || alwaysUpdateQtyBatchSize)
		{
			final PPRoutingId routingId = PPRoutingId.ofRepoIdOrNull(order.getAD_Workflow_ID());
			// No routing set, or is just a new record
			if (routingId == null)
			{
				return;
			}

			final PPRouting routing = routingRepo.getById(routingId);

			qtyBatchSize = routing.getQtyPerBatch().setScale(0, RoundingMode.UP);
			order.setQtyBatchSize(qtyBatchSize);
		}

		final int numberOfBatches;
		if (qtyBatchSize.signum() == 0)
		{
			numberOfBatches = 1;
		}
		else
		{
			final Quantity qtyRequiredToProduce = getQuantities(order).getQtyRequiredToProduce();
			numberOfBatches = qtyRequiredToProduce.divide(qtyBatchSize, 0, RoundingMode.UP).toBigDecimal().intValueExact();
		}
		order.setQtyBatchs(BigDecimal.valueOf(numberOfBatches));
	}

	@Override
	public boolean isSomethingProcessed(final I_PP_Order ppOrder)
	{
		//
		// Any finished good received or any component issued?
		if (orderBOMService.isSomethingReported(ppOrder))
		{
			return true;
		}

		//
		// Routing
		final PPOrderId orderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final PPOrderRouting orderRouting = orderRoutingRepo.getByOrderId(orderId);
		return orderRouting.isSomethingProcessed();
	}

	@Override
	public void addQty(@NonNull final OrderQtyChangeRequest request)
	{
		final I_PP_Order order = ppOrdersRepo.getById(request.getPpOrderId());

		PPOrderQuantities qtys = getQuantities(order);

		final ProductId productId = ProductId.ofRepoId(order.getM_Product_ID());
		final UomId uomId = qtys.getUomId();
		final OrderQtyChangeRequest requestConv = request.convertQuantities(qty -> uomConversionBL.convertQuantityTo(qty, productId, uomId));

		qtys = qtys.reduce(requestConv);
		orderBOMService.setQuantities(order, qtys);

		//
		// Update PP Order Dates
		final Timestamp date = TimeUtil.asTimestamp(request.getDate());
		order.setDateDelivered(date); // overwrite=last
		if (order.getDateStart() == null)
		{
			order.setDateStart(date);
		}

		if (qtys.getQtyRemainingToProduce().signum() <= 0)
		{
			order.setDateFinish(date);
		}

		ppOrdersRepo.save(order);
	}

	@Override
	public PPOrderQuantities getQuantities(@NonNull final I_PP_Order order)
	{
		return orderBOMService.getQuantities(order);
	}

	@Override
	public I_C_OrderLine getDirectOrderLine(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, LiberoException.class, "ppOrder not null");

		final I_C_OrderLine orderLine = ppOrder.getC_OrderLine();
		if (orderLine == null)
		{
			return null;
		}

		//
		// Check: if orderline's Product is not the same as MO's Product
		// ... it means this is a MO for an intermediare product which will be used in another manufacturing order
		// to produce the final product which will be shipped for this order line
		//
		// So we return null because this order line is not the "direct" order line for this product
		if (orderLine.getM_Product_ID() != ppOrder.getM_Product_ID())
		{
			return null;
		}

		return orderLine;
	}

	@Override
	public void updateBOMOrderLinesWarehouseAndLocator(final I_PP_Order ppOrder)
	{
		for (final I_PP_Order_BOMLine orderBOMLine : ppOrderBOMsRepo.retrieveOrderBOMLines(ppOrder))
		{
			PPOrderUtil.updateBOMLineWarehouseAndLocatorFromOrder(orderBOMLine, ppOrder);
			ppOrderBOMsRepo.save(orderBOMLine);
		}
	}

	@Override
	public void setDocType(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final PPOrderDocBaseType docBaseType,
			@Nullable final String docSubType)
	{
		final DocTypeId docTypeId = docTypesRepo.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseType.getCode())
				.docSubType(docSubType)
				.adClientId(ppOrder.getAD_Client_ID())
				.adOrgId(ppOrder.getAD_Org_ID())
				.build());

		ppOrder.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		ppOrder.setC_DocType_ID(docTypeId.getRepoId());
	}

	@Override
	public void closeOrder(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrdersRepo.getById(ppOrderId);

		ppOrder.setPlanningStatus(PPOrderPlanningStatus.COMPLETE.getCode());
		ppOrdersRepo.save(ppOrder);

		documentBL.processEx(ppOrder, X_PP_Order.DOCACTION_Close);
	}

	@Override
	public void closeQtyOrdered(final I_PP_Order ppOrder)
	{
		orderBOMService.changeQuantities(ppOrder, PPOrderQuantities::close);
		ppOrdersRepo.save(ppOrder);
	}

	@Override
	public void uncloseQtyOrdered(@NonNull final I_PP_Order ppOrder)
	{
		orderBOMService.changeQuantities(ppOrder, PPOrderQuantities::unclose);
		ppOrdersRepo.save(ppOrder);
	}

	@Override
	public void changeScheduling(@NonNull final PPOrderScheduleChangeRequest request)
	{
		orderRoutingRepo.changeActivitiesScheduling(request.getOrderId(), request.getActivityChangeRequests());
		ppOrdersRepo.changeOrderScheduling(request.getOrderId(), request.getScheduledStartDate(), request.getScheduledEndDate());
	}

	@Override
	public void createOrderRouting(@NonNull final I_PP_Order ppOrderRecord)
	{
		final PPOrderRouting orderRouting = CreateOrderRoutingCommand.builder()
				.routingId(PPRoutingId.ofRepoId(ppOrderRecord.getAD_Workflow_ID()))
				.ppOrderId(PPOrderId.ofRepoId(ppOrderRecord.getPP_Order_ID()))
				.dateStartSchedule(ppOrderRecord.getDateStartSchedule().toInstant())
				.qtyOrdered(getQuantities(ppOrderRecord).getQtyRequiredToProduce())
				.build()
				.execute();

		orderRoutingRepo.save(orderRouting);

		copyAttachmentsFromTemplates(orderRouting);
	}

	private void copyAttachmentsFromTemplates(final PPOrderRouting orderRouting)
	{
		if (Adempiere.isUnitTestMode())
		{
			// atm copying attachments in unit test is not possible
			return;
		}

		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			copyAttachmentsFromTemplate(activity, attachmentEntryService);
		}
	}

	private static void copyAttachmentsFromTemplate(
			@NonNull final PPOrderRoutingActivity activity,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		final PPRoutingActivityTemplateId activityTemplateId = activity.getActivityTemplateId();
		if (activityTemplateId == null)
		{
			return;
		}

		final TableRecordReference activityTemplateRef = TableRecordReference.of(I_AD_WF_Node_Template.Table_Name, activityTemplateId);
		final TableRecordReference activityRef = TableRecordReference.of(I_PP_Order_Node.Table_Name, activity.getId());
		final TableRecordReference orderRef = TableRecordReference.of(I_PP_Order.Table_Name, activity.getOrderId());

		attachmentEntryService.shareAttachmentLinks(
				ImmutableList.of(activityTemplateRef),
				ImmutableList.of(orderRef, activityRef));
	}

	@Override
	public void closeAllActivities(@NonNull final PPOrderId orderId)
	{
		reportQtyToProcessOnNotStartedActivitiesIfApplies(orderId);

		final PPOrderRouting orderRouting = orderRoutingRepo.getByOrderId(orderId);

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			final PPOrderRoutingActivityStatus activityStatus = activity.getStatus();
			if (activityStatus == PPOrderRoutingActivityStatus.IN_PROGRESS
					|| activityStatus == PPOrderRoutingActivityStatus.COMPLETED)
			{
				orderRouting.closeActivity(activity.getId());
			}
		}

		orderRoutingRepo.save(orderRouting);
	}

	private void reportQtyToProcessOnNotStartedActivitiesIfApplies(final PPOrderId orderId)
	{
		final I_PP_Order orderRecord = ppOrdersRepo.getById(orderId);
		final PPOrderDocBaseType docBaseType = PPOrderDocBaseType.ofCode(orderRecord.getDocBaseType());
		if (docBaseType.isRepairOrder())
		{
			return;
		}

		final PPOrderRouting orderRouting = orderRoutingRepo.getByOrderId(orderId);
		final ZonedDateTime reportDate = de.metas.common.util.time.SystemTime.asZonedDateTime();

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			final PPOrderRoutingActivityStatus activityStatus = activity.getStatus();
			if (activityStatus == PPOrderRoutingActivityStatus.NOT_STARTED)
			{
				final Quantity qtyToProcess = activity.getQtyToDeliver();
				if (qtyToProcess.signum() <= 0)
				{
					// TODO: should we create a negate CC?
					continue;
				}

				final Duration setupTimeRemaining = activity.getSetupTimeRemaining();
				final WorkingTime durationRemaining = WorkingTime.builder()
						.durationPerOneUnit(activity.getDurationPerOneUnit())
						.unitsPerCycle(activity.getUnitsPerCycle())
						.qty(qtyToProcess)
						.activityTimeUnit(activity.getDurationUnit())
						.build();

				costCollectorsService.createActivityControl(ActivityControlCreateRequest.builder()
						.order(orderRecord)
						.orderActivity(activity)
						.movementDate(reportDate)
						.qtyMoved(qtyToProcess)
						.durationSetup(setupTimeRemaining)
						.duration(durationRemaining.getDuration())
						.build());
			}
		}
	}

	@Override
	public void uncloseActivities(@NonNull final PPOrderId orderId)
	{
		final PPOrderRouting orderRouting = orderRoutingRepo.getByOrderId(orderId);

		for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
		{
			final PPOrderRoutingActivityStatus activityStatus = activity.getStatus();
			if (activityStatus == PPOrderRoutingActivityStatus.CLOSED)
			{
				orderRouting.uncloseActivity(activity.getId());
			}
		}

		orderRoutingRepo.save(orderRouting);
	}

	@Override
	public Optional<QtyCalculationsBOM> getOpenPickingOrderBOM(@NonNull final PPOrderId pickingOrderId)
	{
		final I_PP_Order pickingOrder = ppOrdersRepo.getById(pickingOrderId);
		final DocStatus pickingOrderDocStatus = DocStatus.ofCode(pickingOrder.getDocStatus());
		if (!pickingOrderDocStatus.isCompleted())
		{
			return Optional.empty();
		}

		return Optional.of(orderBOMService.getQtyCalculationsBOM(pickingOrder));
	}

	@Override
	public void updateCanBeExportedAfter(@NonNull final I_PP_Order order)
	{
		final APIExportStatus exportStatus = APIExportStatus.ofNullableCode(order.getExportStatus(), APIExportStatus.Pending);
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID());
		order.setCanBeExportedFrom(computeCanBeExportedFrom(exportStatus, clientAndOrgId));
	}

	private Timestamp computeCanBeExportedFrom(
			@NonNull final APIExportStatus exportStatus,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		// we see "not-yet-set" as equivalent to "pending"
		if (!Objects.equals(exportStatus, APIExportStatus.Pending))
		{
			logger.debug("computeCanBeExportedAfter: CanBeExportedFrom=MAX({}) because exportStatus={}", Env.MAX_DATE, exportStatus);
			return Env.MAX_DATE;
		}

		final Duration canBeExportedAfter = getCanBeExportedAfter(clientAndOrgId);

		final Instant now = SystemTime.asInstant();
		final Instant canBeExportedFrom = now.plus(canBeExportedAfter);
		logger.debug("computeCanBeExportedAfter: CanBeExportedFrom={} ({} + {})", canBeExportedFrom, now, canBeExportedAfter);

		return TimeUtil.asTimestamp(canBeExportedFrom);
	}

	private Duration getCanBeExportedAfter(final ClientAndOrgId clientAndOrgId)
	{
		final int seconds = sysConfigBL.getIntValue(
				SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
				0, // default
				clientAndOrgId.getClientId().getRepoId(),
				clientAndOrgId.getOrgId().getRepoId());

		return seconds > 0 ? Duration.ofSeconds(seconds) : Duration.ZERO;
	}

	@Override
	public void updateExportStatus(@NonNull final APIExportStatus newExportStatus, @NonNull final PInstanceId pinstanceId)
	{
		final AtomicInteger allCounter = new AtomicInteger(0);
		final AtomicInteger updatedCounter = new AtomicInteger(0);

		queryBL.createQueryBuilder(I_PP_Order.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterateAndStream()
				.forEach(record -> {
					allCounter.incrementAndGet();
					if (Objects.equals(record.getExportStatus(), newExportStatus.getCode()))
					{
						return;
					}
					record.setExportStatus(newExportStatus.getCode());
					updateCanBeExportedFrom(record);
					InterfaceWrapperHelper.saveRecord(record);

					updatedCounter.incrementAndGet();
				});

		Loggables.withLogger(logger, Level.INFO).addLog("Updated {} out of {} PP_Order", updatedCounter.get(), allCounter.get());
	}

	@Override
	public void updateCanBeExportedFrom(@NonNull final I_PP_Order ppOrder)
	{
		// we see "not-yet-set" as equivalent to "pending"
		final APIExportStatus exportStatus = APIExportStatus.ofNullableCode(ppOrder.getExportStatus(), APIExportStatus.Pending);
		if (!Objects.equals(exportStatus, APIExportStatus.Pending))
		{
			ppOrder.setCanBeExportedFrom(Env.MAX_DATE);
			logger.debug("exportStatus={}; -> set CanBeExportedFrom={}", ppOrder.getExportStatus(), Env.MAX_DATE);
			return;
		}

		final int canBeExportedAfterSeconds = sysConfigBL.getIntValue(
				SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
				ppOrder.getAD_Client_ID(),
				ppOrder.getAD_Org_ID());
		if (canBeExportedAfterSeconds >= 0)
		{
			final Instant instant = Instant.now().plusSeconds(canBeExportedAfterSeconds);
			ppOrder.setCanBeExportedFrom(TimeUtil.asTimestamp(instant));
			logger.debug("canBeExportedAfterSeconds={}; -> set CanBeExportedFrom={}", canBeExportedAfterSeconds, ppOrder.getCanBeExportedFrom());
		}
	}

	@Override
	public void setC_OrderLine(@NonNull final PPOrderId ppOrderId, @NonNull final OrderLineId orderLineId)
	{
		final I_PP_Order ppOrder = getById(ppOrderId);
		final I_C_OrderLine ol = orderDAO.getOrderLineById(orderLineId);
		ppOrder.setC_OrderLine(ol);

		ppOrdersRepo.save(ppOrder);
	}

	@Override
	public void postPPOrderCreatedEvent(final @NonNull I_PP_Order ppOrder)
	{
		final PPOrderPojoConverter ppOrderConverter = SpringContextHolder.instance.getBean(PPOrderPojoConverter.class);
		final PostMaterialEventService materialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);

		final PPOrder ppOrderPojo = ppOrderConverter.toPPOrder(ppOrder);

		final PPOrderCreatedEvent ppOrderCreatedEvent = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ppOrder.getAD_Client_ID(), ppOrder.getAD_Org_ID()))
				.ppOrder(ppOrderPojo)
				.directlyPickIfFeasible(PPOrderUtil.pickIfFeasible(ppOrderPojo.getPpOrderData()))
				.build();

		materialEventService.postEventAfterNextCommit(ppOrderCreatedEvent);
	}

	@Override
	public void completeDocument(@NonNull final I_PP_Order ppOrder)
	{
		documentBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		Loggables.addLog(
				"Completed ppOrder; PP_Order_ID={}; DocumentNo={}",
				ppOrder.getPP_Order_ID(), ppOrder.getDocumentNo());
	}

	@Override
	public Set<ProductId> getProductIdsToIssue(@NonNull final PPOrderId ppOrderId)
	{
		return orderBOMService.getProductIdsToIssue(ppOrderId);
	}

}
