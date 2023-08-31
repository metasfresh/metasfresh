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

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFDurationUnit;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
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
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPCostCollectorQuantities;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Cost_Collector;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class PPCostCollectorBL implements IPPCostCollectorBL
{
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPCostCollectorDAO costCollectorDAO = Services.get(IPPCostCollectorDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public I_PP_Cost_Collector getById(final PPCostCollectorId costCollectorId)
	{
		return costCollectorDAO.getById(costCollectorId);
	}

	@Override
	public PPCostCollectorQuantities getQuantities(@NonNull final I_PP_Cost_Collector cc)
	{
		final I_C_UOM uom = uomDAO.getById(cc.getC_UOM_ID());
		return PPCostCollectorQuantities.builder()
				.movementQty(Quantity.of(cc.getMovementQty(), uom))
				.scrappedQty(Quantity.of(cc.getScrappedQty(), uom))
				.rejectedQty(Quantity.of(cc.getQtyReject(), uom))
				.build();
	}

	@Override
	public void setQuantities(
			@NonNull final I_PP_Cost_Collector cc,
			@NonNull final PPCostCollectorQuantities from)
	{
		cc.setC_UOM_ID(from.getUomId().getRepoId());
		cc.setMovementQty(from.getMovementQty().toBigDecimal());
		cc.setScrappedQty(from.getScrappedQty().toBigDecimal());
		cc.setQtyReject(from.getRejectedQty().toBigDecimal());
	}

	@Override
	public Quantity getMovementQty(final I_PP_Cost_Collector cc)
	{
		return getQuantities(cc).getMovementQty();
	}

	@Override
	public Quantity getMovementQtyInStockingUOM(final I_PP_Cost_Collector cc)
	{
		final Quantity movementQty = getQuantities(cc).getMovementQty();
		final ProductId productId = ProductId.ofRepoId(cc.getM_Product_ID());
		return uomConversionBL.convertToProductUOM(movementQty, productId);
	}

	@Override
	public Quantity getTotalDurationReportedAsQuantity(@NonNull final I_PP_Cost_Collector cc)
	{
		final WFDurationUnit durationUnit = getDurationUnit(cc);
		final I_C_UOM uom = uomDAO.getByTemporalUnit(durationUnit.getTemporalUnit());
		final BigDecimal totalDurationBD = getTotalDurationReportedBD(cc);
		return Quantity.of(totalDurationBD, uom);
	}

	@Override
	public Duration getTotalDurationReported(@NonNull final I_PP_Cost_Collector cc)
	{
		final WFDurationUnit durationUnit = getDurationUnit(cc);
		final BigDecimal totalDurationBD = getTotalDurationReportedBD(cc);
		return durationUnit.toDuration(totalDurationBD);
	}

	private static WFDurationUnit getDurationUnit(@NonNull final I_PP_Cost_Collector cc)
	{
		//noinspection ConstantConditions
		return WFDurationUnit.ofCode(cc.getDurationUnit());
	}

	private static BigDecimal getTotalDurationReportedBD(@NonNull final I_PP_Cost_Collector cc)
	{
		return cc.getSetupTimeReal().add(cc.getDurationReal());
	}

	/**
	 * Suggests which Cost Collector Type to use for given Order BOM Line.
	 *
	 * @return Component Issue, Mix Variance or Method Change Variance
	 */
	private static CostCollectorType extractCostCollectorTypeToUseForComponentIssue(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@NonNull final ProductId productId)
	{
		final ProductId bomLineProductId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());

		if (!ProductId.equals(productId, bomLineProductId) || PPOrderUtil.isMethodChangeVariance(orderBOMLine))
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
		final ProductId productId = request.getProductId();
		final I_C_UOM bomLineUOM = ppOrderBOMBL.getBOMLineUOM(orderBOMLine);
		final CostCollectorType costCollectorType = extractCostCollectorTypeToUseForComponentIssue(orderBOMLine, productId);
		final PPOrderBOMLineId orderBOMLineId = PPOrderBOMLineId.ofRepoId(orderBOMLine.getPP_Order_BOMLine_ID());
		//
		final I_PP_Order order = orderBOMLine.getPP_Order();
		final ResourceId plantId = ResourceId.ofRepoId(order.getS_Resource_ID());

		//
		// Convert our Qtys from their qtyUOM to BOM's UOM
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		final Quantity qtyIssueConv = uomConversionBL.convertQuantityTo(request.getQtyIssue(), conversionCtx, bomLineUOM);
		final Quantity qtyScrapConv = uomConversionBL.convertQuantityTo(request.getQtyScrap(), conversionCtx, bomLineUOM);
		final Quantity qtyRejectConv = uomConversionBL.convertQuantityTo(request.getQtyReject(), conversionCtx, bomLineUOM);

		return createCollector(
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
	}

	@Override
	public I_PP_Cost_Collector createReceipt(final ReceiptCostCollectorCandidate candidate)
	{
		if (!candidate.isCoOrByProductReceipt())
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
			throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate + "\n Expected: " + orderBOMLine.getM_Product_ID());
		}

		return createIssue(ComponentIssueCreateRequest.builder()
				.orderBOMLine(orderBOMLine)
				.locatorId(candidate.getLocatorId())
				.attributeSetInstanceId(candidate.getAttributeSetInstanceId())
				.movementDate(candidate.getMovementDate())
				.qtyIssue(OrderBOMLineQuantities.adjustCoProductQty(candidate.getQtyToReceive()))
				.qtyScrap(OrderBOMLineQuantities.adjustCoProductQty(candidate.getQtyScrap()))
				.qtyReject(OrderBOMLineQuantities.adjustCoProductQty(candidate.getQtyReject()))
				.build());
	}

	@NonNull
	private I_PP_Cost_Collector createFinishedGoodsReceipt(@NonNull final ReceiptCostCollectorCandidate candidate)
	{
		final I_PP_Order order = candidate.getOrder();

		final Quantity qtyToDeliver = candidate.getQtyToReceive();
		final Quantity qtyScrap = candidate.getQtyScrap();
		final Quantity qtyReject = candidate.getQtyReject();
		final ZonedDateTime movementDate = candidate.getMovementDate();

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
		return costCollectorId > reversalCostCollectorId;
	}

	@Override
	public boolean isFloorStock(final I_PP_Cost_Collector cc)
	{
		final int ppOrderBOMLineId = cc.getPP_Order_BOMLine_ID();

		return queryBL
				.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_BOMLine_ID, ppOrderBOMLineId)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_IssueMethod, BOMComponentIssueMethod.FloorStock.getCode())
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	@Override
	public void updateCostCollectorFromOrder(
			final I_PP_Cost_Collector cc,
			final I_PP_Order from)
	{
		cc.setPP_Order_ID(from.getPP_Order_ID());
		cc.setAD_Org_ID(from.getAD_Org_ID());
		cc.setM_Warehouse_ID(from.getM_Warehouse_ID());
		cc.setAD_OrgTrx_ID(from.getAD_OrgTrx_ID());
		cc.setC_Activity_ID(from.getC_Activity_ID());
		cc.setC_Campaign_ID(from.getC_Campaign_ID());
		cc.setC_Project_ID(from.getC_Project_ID());
		cc.setUser1_ID(from.getUser1_ID());
		cc.setUser2_ID(from.getUser2_ID());
		cc.setDescription(from.getDescription());
		cc.setS_Resource_ID(from.getS_Resource_ID());
		cc.setM_Product_ID(from.getM_Product_ID());
		cc.setM_AttributeSetInstance_ID(from.getM_AttributeSetInstance_ID());

		final Quantity qtyRemainingToProduce = ppOrderBOMBL.getQuantities(from).getQtyRemainingToProduce();
		setQuantities(cc, PPCostCollectorQuantities.ofMovementQty(qtyRemainingToProduce));
	}

	@Override
	public void createActivityControl(final ActivityControlCreateRequest request)
	{
		final I_PP_Order order = request.getOrder();
		final ProductId mainProductId = ProductId.ofRepoId(order.getM_Product_ID());
		final AttributeSetInstanceId mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(order.getM_AttributeSetInstance_ID());
		final LocatorId receiptLocatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(order.getM_Locator_ID());

		final PPOrderRoutingActivity orderActivity = request.getOrderActivity();

		createCollector(
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
	public void createMaterialUsageVariance(
			final I_PP_Order ppOrder,
			final I_PP_Order_BOMLine line)
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
			final Quantity qtyOpen = ppOrderBOMBL.getQtyToIssue(line);

			final BigDecimal qtyUsageVariancePrevBD = costCollectorDAO.getQtyUsageVariance(ppOrderBOMLineId); // Previous booked usage variance
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
		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(locatorRepoId);

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
	public void createResourceUsageVariance(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final PPOrderRoutingActivity activity)
	{
		final ProductId mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final AttributeSetInstanceId mainProductAsiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID());
		final LocatorId receiptLocatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(ppOrder.getM_Locator_ID());

		//
		final Duration setupTimeReported = activity.getSetupTimeReal();
		final Duration durationReported = activity.getDurationReal();
		if (setupTimeReported.isZero() && durationReported.isZero())
		{
			// nothing reported on this activity => it's not a variance, this will be auto-reported on close
			return;
		}
		//
		final Duration setupTimeVariancePrev = costCollectorDAO.getTotalSetupTimeReal(activity, CostCollectorType.UsageVariance);
		final Duration durationVariancePrev = costCollectorDAO.getDurationReal(activity, CostCollectorType.UsageVariance);
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
		I_PP_Order order;
		ProductId productId;
		LocatorId locatorId;
		AttributeSetInstanceId attributeSetInstanceId;
		ResourceId resourceId;
		PPOrderBOMLineId ppOrderBOMLineId;
		PPOrderRoutingActivity orderActivity;
		CostCollectorType costCollectorType;
		ZonedDateTime movementDate;

		UomId uomId;
		Quantity qty;
		Quantity qtyScrap;
		Quantity qtyReject;

		Duration durationSetup;
		Duration duration;

		int pickingCandidateId;

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
			this.uomId = Quantity.getCommonUomIdOfAll(
					this.qty,
					this.qtyScrap,
					this.qtyReject
			);

			this.durationSetup = durationSetup != null ? durationSetup : Duration.ZERO;
			this.duration = duration != null ? duration : Duration.ZERO;

			this.pickingCandidateId = pickingCandidateId > 0 ? pickingCandidateId : -1;
		}
	}

	/**
	 * Create & Complete Cost Collector
	 */
	@NonNull
	private I_PP_Cost_Collector createCollector(@NonNull final CostCollectorCreateRequest request)
	{
		trxManager.assertThreadInheritedTrxExists();

		final I_PP_Order ppOrder = request.getOrder();
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
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
		cc.setM_Product_ID(ProductId.toRepoId(request.getProductId()));

		setQuantities(cc, PPCostCollectorQuantities.builder()
				.movementQty(request.getQty())
				.scrappedQty(request.getQtyScrap())
				.rejectedQty(request.getQtyReject())
				.build());

		final PPOrderRoutingActivity orderActivity = request.getOrderActivity();
		if (orderActivity != null)
		{
			cc.setPP_Order_Node_ID(orderActivity.getId().getRepoId());
			cc.setIsSubcontracting(orderActivity.isSubcontracting());

			final WFDurationUnit durationUnit = orderActivity.getDurationUnit();
			cc.setDurationUnit(durationUnit.getCode());
			cc.setSetupTimeReal(durationUnit.toBigDecimal(request.getDurationSetup()));
			cc.setDurationReal(durationUnit.toBigDecimal(request.getDuration()));
		}

		// If this is an material issue, we should use BOM Line's UOM
		if (request.getPpOrderBOMLineId() != null)
		{
			cc.setPP_Order_BOMLine_ID(request.getPpOrderBOMLineId().getRepoId());
		}

		if (request.getPickingCandidateId() > 0)
		{
			cc.setM_Picking_Candidate_ID(request.getPickingCandidateId());
		}

		costCollectorDAO.save(cc);

		//
		// Process the Cost Collector
		documentBL.processEx(cc,
				X_PP_Cost_Collector.DOCACTION_Complete,
				null // expectedDocStatus
		);

		return cc;
	}

	@Override
	public List<I_PP_Cost_Collector> getByOrderId(final PPOrderId ppOrderId)
	{
		return costCollectorDAO.getByOrderId(ppOrderId);
	}
}
