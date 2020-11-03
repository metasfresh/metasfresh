package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.DurationUtils;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class PPCostCollectorBL implements IPPCostCollectorBL
{
	@Override
	public I_PP_Cost_Collector getById(final PPCostCollectorId costCollectorId)
	{
		return Services.get(IPPCostCollectorDAO.class).getById(costCollectorId);
	}

	@Override
	public Quantity getMovementQty(final I_PP_Cost_Collector cc)
	{
		return Quantity.of(cc.getMovementQty(), getStockingUOM(cc));
	}

	@Override
	public I_C_UOM getStockingUOM(final I_PP_Cost_Collector cc)
	{
		final ProductId productId = ProductId.ofRepoId(cc.getM_Product_ID());
		return Services.get(IProductBL.class).getStockUOM(productId);
	}

	@Override
	public Duration getTotalDurationReported(final I_PP_Cost_Collector cc)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(cc.getPP_Order_ID());
		final PPOrderRoutingActivityId activityId = PPOrderRoutingActivityId.ofRepoId(orderId, cc.getPP_Order_Node_ID());

		final IPPOrderRoutingRepository orderRoutingsRepo = Services.get(IPPOrderRoutingRepository.class);
		final PPOrderRoutingActivity activity = orderRoutingsRepo.getOrderRoutingActivity(activityId);
		final TemporalUnit durationUnit = activity.getDurationUnit();

		final Duration setupTimeReported = DurationUtils.toDuration(cc.getSetupTimeReal(), durationUnit);
		final Duration runningTimeReported = DurationUtils.toDuration(cc.getDurationReal(), durationUnit);
		final Duration totalDuration = setupTimeReported.plus(runningTimeReported);
		return totalDuration;
	}

	/**
	 * Suggests which Cost Collector Type to use for given Order BOM Line.
	 *
	 * @return Component Issue, Mix Variance or Method Change Variance
	 */
	private static CostCollectorType extractCostCollectorTypeToUseForComponentIssue(final I_PP_Order_BOMLine orderBOMLine)
	{
		if (PPOrderUtil.isMethodChangeVariance(orderBOMLine))
		{
			return CostCollectorType.MethodChangeVariance;
		}
		else if (PPOrderUtil.isCoOrByProduct(orderBOMLine))
		{
			return CostCollectorType.MixVariance;
		}
		else
		{
			return CostCollectorType.ComponentIssue;
		}
	}

	@Override
	public I_PP_Cost_Collector createIssue(final ComponentIssueCreateRequest request)
	{
		final I_PP_Order_BOMLine orderBOMLine = request.getOrderBOMLine();
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final I_C_UOM bomLineUOM = Services.get(IUOMDAO.class).getById(orderBOMLine.getC_UOM_ID());
		final CostCollectorType costCollectorType = extractCostCollectorTypeToUseForComponentIssue(orderBOMLine);
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		//
		final I_PP_Order order = orderBOMLine.getPP_Order();
		final ResourceId plantId = ResourceId.ofRepoId(order.getS_Resource_ID());

		//
		// Convert our Qtys from their qtyUOM to BOM's UOM
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		final Quantity qtyIssueConv = uomConversionBL.convertQuantityTo(request.getQtyIssue(), conversionCtx, bomLineUOM);
		final Quantity qtyScrapConv = uomConversionBL.convertQuantityTo(request.getQtyScrap(), conversionCtx, bomLineUOM);
		final Quantity qtyRejectConv = uomConversionBL.convertQuantityTo(request.getQtyReject(), conversionCtx, bomLineUOM);

		final I_PP_Cost_Collector cc = createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(costCollectorType)
						.order(order)
						.productId(productId)
						.locatorId(request.getLocatorId())
						.attributeSetInstanceId(request.getAttributeSetInstanceId())
						.resourceId(plantId)
						.ppOrderBOMLineId(orderBOMLineId)
						.movementDate(request.getMovementDate())
						.qty(qtyIssueConv)
						.qtyScrap(qtyScrapConv)
						.qtyReject(qtyRejectConv)
						.pickingCandidateId(request.getPickingCandidateId())
						.build());

		return cc;
	}

	@Override
	public I_PP_Cost_Collector createReceipt(final ReceiptCostCollectorCandidate candidate)
	{
		final I_PP_Order_BOMLine orderBOMLine = candidate.getOrderBOMLine();
		if (orderBOMLine == null)
		{
			return createFinishedGoodsReceipt(candidate);
		}
		else
		{
			return createCoProductReceipt(candidate);
		}
	}

	private I_PP_Cost_Collector createCoProductReceipt(final ReceiptCostCollectorCandidate candidate)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		//
		// Get and validate the BOM Line
		final I_PP_Order_BOMLine orderBOMLine = candidate.getOrderBOMLine();
		Check.assumeNotNull(orderBOMLine, LiberoException.class, "orderBOMLine not null");
		PPOrderUtil.assertReceipt(orderBOMLine);

		//
		// Validate Product
		final ProductId productId = candidate.getProductId();
		if (productId == null || productId.getRepoId() != orderBOMLine.getM_Product_ID())
		{
			throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate + "\n Expected: " + orderBOMLine.getM_Product());
		}

		return createIssue(ComponentIssueCreateRequest.builder()
				.orderBOMLine(orderBOMLine)
				.locatorId(candidate.getLocatorId())
				.attributeSetInstanceId(candidate.getAttributeSetInstanceId())
				.movementDate(candidate.getMovementDate())
				.qtyIssue(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyToReceive()))
				.qtyScrap(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyScrap()))
				.qtyReject(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyReject()))
				.build());
	}

	private I_PP_Cost_Collector createFinishedGoodsReceipt(final ReceiptCostCollectorCandidate candidate)
	{
		final I_PP_Order order = candidate.getOrder();

		final Quantity qtyReceived = getQtyReceived(order);

		final Quantity qtyToDeliver = candidate.getQtyToReceive();
		final Quantity qtyScrap = candidate.getQtyScrap();
		final Quantity qtyReject = candidate.getQtyReject();
		final ZonedDateTime movementDate = candidate.getMovementDate();

		if (qtyToDeliver.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0)
		{
			// Validate Product
			final ProductId productId = candidate.getProductId();
			if (productId == null || productId.getRepoId() != order.getM_Product_ID())
			{
				throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate
						+ "\n Expected: " + order.getM_Product_ID());
			}

			final LocatorId locatorId = candidate.getLocatorId();
			final AttributeSetInstanceId asiId = candidate.getAttributeSetInstanceId();

			return createCollector(
					CostCollectorCreateRequest.builder()
							.costCollectorType(CostCollectorType.MaterialReceipt)
							.order(order)
							.productId(productId)
							.locatorId(locatorId)
							.attributeSetInstanceId(asiId)
							.resourceId(ResourceId.ofRepoId(order.getS_Resource_ID()))
							.movementDate(movementDate)
							.qty(qtyToDeliver)
							.qtyScrap(qtyScrap)
							.qtyReject(qtyReject)
							.pickingCandidateId(candidate.getPickingCandidateId())
							.build());
		}

		// TODO: review this part. Drop qtyDelivered parameter because we can fetch it from PP_Order.QtyDelivered...
		// ... also consider moving this in PP_Order model validator
		order.setDateDelivered(TimeUtil.asTimestamp(movementDate));
		if (order.getDateStart() == null)
		{
			order.setDateStart(TimeUtil.asTimestamp(movementDate));
		}

		if (qtyReceived.add(qtyScrap).compareTo(qtyToDeliver) >= 0)
		{
			order.setDateFinish(TimeUtil.asTimestamp(movementDate));
		}

		Services.get(IPPOrderDAO.class).save(order);

		return null;
	}

	private Quantity getQtyReceived(final I_PP_Order ppOrder)
	{
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final I_C_UOM uom = Services.get(IProductBL.class).getStockUOM(productId);
		return Quantity.of(ppOrder.getQtyDelivered(), uom);
	}

	@Override
	public boolean isReversal(final I_PP_Cost_Collector cc)
	{
		Check.assumeNotNull(cc, "cc not null");
		final int costCollectorId = cc.getPP_Cost_Collector_ID();
		if (costCollectorId <= 0)
		{
			return false;
		}

		final int reversalCostCollectorId = cc.getReversal_ID();
		if (reversalCostCollectorId <= 0)
		{
			return false;
		}

		//
		// We consider given cost collector as a reversal if it's ID is bigger then the Reveral_ID.
		final boolean reversal = costCollectorId > reversalCostCollectorId;
		return reversal;
	}

	@Override
	public boolean isFloorStock(final I_PP_Cost_Collector cc)
	{
		final int ppOrderBOMLineId = cc.getPP_Order_BOMLine_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_BOMLine_ID, ppOrderBOMLineId)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_IssueMethod, X_PP_Order_BOMLine.ISSUEMETHOD_FloorStock)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	@Override
	public void updateCostCollectorFromOrder(final I_PP_Cost_Collector cc, final I_PP_Order from)
	{
		cc.setPP_Order_ID(from.getPP_Order_ID());
		cc.setAD_Org_ID(from.getAD_Org_ID());
		cc.setM_Warehouse_ID(from.getM_Warehouse_ID());
		cc.setAD_OrgTrx_ID(from.getAD_OrgTrx_ID());
		cc.setC_Activity_ID(from.getC_Activity_ID());
		cc.setC_Campaign_ID(from.getC_Campaign_ID());
		// cc.setC_Project_ID(from.getC_Project_ID()); Taken out because the Project is no longer a physical column in PP_Order #5328
		cc.setUser1_ID(from.getUser1_ID());
		cc.setUser2_ID(from.getUser2_ID());
		cc.setDescription(from.getDescription());
		cc.setS_Resource_ID(from.getS_Resource_ID());
		cc.setM_Product_ID(from.getM_Product_ID());
		cc.setC_UOM_ID(from.getC_UOM_ID());
		cc.setM_AttributeSetInstance_ID(from.getM_AttributeSetInstance_ID());
		cc.setMovementQty(from.getQtyOrdered());
	}

	@Override
	public I_PP_Cost_Collector createActivityControl(final ActivityControlCreateRequest request)
	{
		final I_PP_Order order = request.getOrder();
		final ProductId mainProductId = ProductId.ofRepoId(order.getM_Product_ID());
		final AttributeSetInstanceId mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(order.getM_AttributeSetInstance_ID());
		final LocatorId receiptLocatorId = Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(order.getM_Locator_ID());

		final PPOrderRoutingActivity orderActivity = request.getOrderActivity();

		return createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(CostCollectorType.ActivityControl)
						.order(order)
						.productId(mainProductId)
						.locatorId(receiptLocatorId)
						.attributeSetInstanceId(mainProductAsiId)
						.resourceId(orderActivity.getResourceId())
						.orderActivity(orderActivity)
						.movementDate(request.getMovementDate())
						.qty(request.getQtyMoved())
						.durationSetup(request.getDurationSetup())
						.duration(request.getDuration())
						.build());
	}

	@Override
	public void createMaterialUsageVariance(final I_PP_Order ppOrder, final I_PP_Order_BOMLine line)
	{
		if (PPOrderUtil.isMethodChangeVariance(line))
		{
			return;
		}

		final BOMComponentType componentType = BOMComponentType.ofCode(line.getComponentType());
		if (componentType.isVariant())
		{
			return;
		}

		final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoId(line.getPP_Order_BOMLine_ID());

		final Quantity qtyUsageVariance;
		{
			final IPPOrderBOMBL orderBOMsService = Services.get(IPPOrderBOMBL.class);
			final IPPCostCollectorDAO costCollectorsRepo = Services.get(IPPCostCollectorDAO.class);

			final Quantity qtyOpen = orderBOMsService.getQtyToIssue(line);

			final BigDecimal qtyUsageVariancePrevBD = costCollectorsRepo.getQtyUsageVariance(ppOrderBOMLineId); // Previous booked usage variance
			final Quantity qtyUsageVariancePrev = Quantity.of(qtyUsageVariancePrevBD, qtyOpen.getUOM());

			// Current usage variance = QtyOpen - Previous Usage Variance
			qtyUsageVariance = qtyOpen.subtract(qtyUsageVariancePrev);
		}
		if (qtyUsageVariance.signum() == 0)
		{
			return;
		}

		// Get Locator
		int locatorRepoId = line.getM_Locator_ID();
		if (locatorRepoId <= 0)
		{
			locatorRepoId = ppOrder.getM_Locator_ID();
		}
		final LocatorId locatorId = Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(locatorRepoId);

		//
		createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(CostCollectorType.UsageVariance)
						.order(ppOrder)
						.productId(ProductId.ofRepoId(line.getM_Product_ID()))
						.locatorId(locatorId)
						.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(line.getM_AttributeSetInstance_ID()))
						.resourceId(ResourceId.ofRepoId(ppOrder.getS_Resource_ID()))
						.ppOrderBOMLineId(ppOrderBOMLineId)
						.qty(qtyUsageVariance)
						.build());
	}

	@Override
	public void createResourceUsageVariance(@NonNull final I_PP_Order ppOrder, @NonNull final PPOrderRoutingActivity activity)
	{
		final ProductId mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final AttributeSetInstanceId mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID());
		final LocatorId receiptLocatorId = Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(ppOrder.getM_Locator_ID());

		//
		final Duration setupTimeReported = activity.getSetupTimeReal();
		final Duration durationReported = activity.getDurationReal();
		if (setupTimeReported.isZero() && durationReported.isZero())
		{
			// nothing reported on this activity => it's not a variance, this will be auto-reported on close
			return;
		}
		//
		final IPPCostCollectorDAO costCollectorsRepo = Services.get(IPPCostCollectorDAO.class);
		final Duration setupTimeVariancePrev = costCollectorsRepo.getTotalSetupTimeReal(activity, CostCollectorType.UsageVariance);
		final Duration durationVariancePrev = costCollectorsRepo.getDurationReal(activity, CostCollectorType.UsageVariance);
		final Duration setupTimePlanned = activity.getSetupTimeRequired();
		final Duration durationPlanned = activity.getDurationRequired();
		final Quantity qtyToProcess = activity.getQtyToDeliver();
		//
		final Duration setupTimeVariance = setupTimePlanned.minus(setupTimeReported).minus(setupTimeVariancePrev);
		final Duration durationVariance = durationPlanned.minus(durationReported).minus(durationVariancePrev);
		//
		if (qtyToProcess.isZero() && setupTimeVariance.isZero() && durationVariance.isZero())
		{
			return;
		}
		//
		createCollector(CostCollectorCreateRequest.builder()
				.costCollectorType(CostCollectorType.UsageVariance)
				.order(ppOrder)
				.productId(mainProductId)
				.locatorId(receiptLocatorId)
				.attributeSetInstanceId(mainProductAsiId)
				.resourceId(activity.getResourceId())
				.orderActivity(activity)
				.qty(qtyToProcess)
				.durationSetup(setupTimeVariance)
				.duration(durationVariance)
				.build());
	}

	@Value
	private static class CostCollectorCreateRequest
	{
		private final I_PP_Order order;
		private final ProductId productId;
		private final LocatorId locatorId;
		private final AttributeSetInstanceId attributeSetInstanceId;
		private final ResourceId resourceId;
		private final PPOrderBOMLineId ppOrderBOMLineId;
		private final PPOrderRoutingActivity orderActivity;
		private final CostCollectorType costCollectorType;
		private final ZonedDateTime movementDate;
		private final Quantity qty;
		private final Quantity qtyScrap;
		private final Quantity qtyReject;
		private final Duration durationSetup;
		private final Duration duration;
		
		private final int pickingCandidateId;

		@Builder
		private CostCollectorCreateRequest(
				@NonNull final I_PP_Order order,
				final ProductId productId,
				final LocatorId locatorId,
				final AttributeSetInstanceId attributeSetInstanceId,
				final ResourceId resourceId,
				final PPOrderBOMLineId ppOrderBOMLineId,
				@Nullable final PPOrderRoutingActivity orderActivity,
				@NonNull final CostCollectorType costCollectorType,
				@Nullable final ZonedDateTime movementDate,
				//
				@NonNull final Quantity qty,
				@Nullable final Quantity qtyScrap,
				@Nullable final Quantity qtyReject,
				//
				@Nullable final Duration durationSetup,
				@Nullable final Duration duration,
				//
				final int pickingCandidateId)
		{
			this.order = order;
			this.productId = productId;
			this.locatorId = locatorId;
			this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
			this.resourceId = resourceId;
			this.ppOrderBOMLineId = ppOrderBOMLineId;
			this.orderActivity = orderActivity;
			this.costCollectorType = costCollectorType;
			this.movementDate = movementDate != null ? movementDate : SystemTime.asZonedDateTime();

			this.qty = qty;
			this.qtyScrap = qtyScrap != null ? qtyScrap : qty.toZero();
			this.qtyReject = qtyReject != null ? qtyReject : qty.toZero();

			this.durationSetup = durationSetup != null ? durationSetup : Duration.ZERO;
			this.duration = duration != null ? duration : Duration.ZERO;
			
			this.pickingCandidateId = pickingCandidateId > 0 ? pickingCandidateId : -1;
		}
	}

	/**
	 * Create & Complete Cost Collector
	 */
	private I_PP_Cost_Collector createCollector(@NonNull final CostCollectorCreateRequest request)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final I_PP_Order ppOrder = request.getOrder();
		final DocTypeId docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector)
				.adClientId(ppOrder.getAD_Client_ID())
				.adOrgId(ppOrder.getAD_Org_ID())
				.build());

		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		cc.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		cc.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		cc.setPosted(false);
		cc.setProcessed(false);
		cc.setProcessing(false);
		cc.setIsActive(true);

		updateCostCollectorFromOrder(cc, ppOrder);

		cc.setC_DocType_ID(docTypeId.getRepoId());
		cc.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		cc.setCostCollectorType(request.getCostCollectorType().getCode());

		cc.setM_Locator_ID(LocatorId.toRepoId(request.getLocatorId()));
		cc.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId().getRepoId());
		cc.setS_Resource_ID(ResourceId.toRepoId(request.getResourceId()));
		cc.setMovementDate(TimeUtil.asTimestamp(request.getMovementDate()));
		cc.setDateAcct(TimeUtil.asTimestamp(request.getMovementDate()));
		cc.setMovementQty(request.getQty().toBigDecimal());
		cc.setScrappedQty(request.getQtyScrap().toBigDecimal());
		cc.setQtyReject(request.getQtyReject().toBigDecimal());
		cc.setM_Product_ID(ProductId.toRepoId(request.getProductId()));

		final PPOrderRoutingActivity orderActivity = request.getOrderActivity();
		if (orderActivity != null)
		{
			cc.setPP_Order_Node_ID(orderActivity.getId().getRepoId());
			cc.setIsSubcontracting(orderActivity.isSubcontracting());

			final TemporalUnit durationUnit = orderActivity.getDurationUnit();
			cc.setSetupTimeReal(DurationUtils.toBigDecimal(request.getDurationSetup(), durationUnit));
			cc.setDurationReal(DurationUtils.toBigDecimal(request.getDuration(), durationUnit));
		}

		// If this is an material issue, we should use BOM Line's UOM
		if (request.getPpOrderBOMLineId() != null)
		{
			cc.setPP_Order_BOMLine_ID(request.getPpOrderBOMLineId().getRepoId());
			cc.setC_UOM_ID(-1); // we set the BOM Line UOM on beforeSave
		}
		
		if(request.getPickingCandidateId() > 0)
		{
			cc.setM_Picking_Candidate_ID(request.getPickingCandidateId());
		}

		Services.get(IPPCostCollectorDAO.class).save(cc);

		//
		// Process the Cost Collector
		Services.get(IDocumentBL.class).processEx(cc,
				X_PP_Cost_Collector.DOCACTION_Complete,
				null // expectedDocStatus
		);

		return cc;
	}
}
