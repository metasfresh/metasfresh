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
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.ActivityControlCreateRequest;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderNodeBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate.ReceiptCostCollectorCandidateBuilder;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_Workflow;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public class PPCostCollectorBL implements IPPCostCollectorBL
{
	// private final transient Logger log = CLogMgt.getLogger(getClass());

	@Override
	public ReceiptCostCollectorCandidateBuilder createReceiptCostCollectorCandidate()
	{
		return ReceiptCostCollectorCandidate.builder();
	}

	/**
	 * Suggests which Cost Collector Type to use for given Order BOM Line.
	 * 
	 * @return Component Issue, Mix Variance or Method Change Variance
	 */
	private static String getCostCollectorTypeToUse(final I_PP_Order_BOMLine orderBOMLine)
	{
		if (orderBOMLine.getQtyBatch().signum() == 0 && orderBOMLine.getQtyBOM().signum() == 0)
		{
			return X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance;
		}
		else if (PPOrderUtil.isCoOrByProduct(orderBOMLine))
		{
			return X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance;
		}
		else
		{
			return X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue;
		}
	}

	@Override
	public boolean isMaterialReceipt(
			@NonNull final I_PP_Cost_Collector cc,
			final boolean considerCoProductsAsReceipt)
	{
		final String costCollectorType = cc.getCostCollectorType();

		//
		// Case: regular receipts
		if (X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt.equals(costCollectorType))
		{
			return true;
		}

		//
		// Case: by/co-product receipts
		if (X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance.equals(costCollectorType))
		{
			if (considerCoProductsAsReceipt)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		return false;
	}

	@Override
	public boolean isMaterialIssue(
			@NonNull final I_PP_Cost_Collector cc,
			final boolean considerCoProductsAsIssue)
	{
		final String costCollectorType = cc.getCostCollectorType();

		//
		// Case: and actual Material Issue
		if (X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue.equals(costCollectorType))
		{
			return true;
		}

		// If this cost collector is not linked to a Order BOM Line
		// => it's not an material issue for sure
		if (cc.getPP_Order_BOMLine_ID() < 0)
		{
			return false;
		}

		//
		// Case Method Change Variance: we issue product, but not the one from BOM Line
		if (X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance.equals(costCollectorType))
		{
			return true;
		}

		//
		// Case Mix Variance: we received (i.e. negative issue) a by/co-product
		if (X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance.equals(costCollectorType))
		{
			if (considerCoProductsAsIssue)
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		return false;
	}

	@Override
	public boolean isCoOrByProductReceipt(final I_PP_Cost_Collector cc)
	{
		Check.assumeNotNull(cc, LiberoException.class, "cc not null");
		final String costCollectorType = cc.getCostCollectorType();
		return X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance.equals(costCollectorType);
	}

	@Override
	public boolean isActivityControl(final I_PP_Cost_Collector cc)
	{
		Check.assumeNotNull(cc, LiberoException.class, "cc not null");
		final String costCollectorType = cc.getCostCollectorType();

		return X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl.equals(costCollectorType);
	}

	@Override
	public I_PP_Cost_Collector createIssue(final ComponentIssueCreateRequest request)
	{
		final I_PP_Order_BOMLine orderBOMLine = request.getOrderBOMLine();
		final I_PP_Order order = orderBOMLine.getPP_Order();

		//
		// Convert our Qtys from their qtyUOM to BOM's UOM
		final I_C_UOM qtyUOM = request.getQtyUOM();
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final I_M_Product product = orderBOMLine.getM_Product();
		final I_C_UOM bomUOM = orderBOMLine.getC_UOM();
		final BigDecimal qtyIssueConv = uomConversionBL.convertQty(product, request.getQtyIssue(), qtyUOM, bomUOM);
		final BigDecimal qtyScrapConv = uomConversionBL.convertQty(product, request.getQtyScrap(), qtyUOM, bomUOM);
		final BigDecimal qtyRejectConv = uomConversionBL.convertQty(product, request.getQtyReject(), qtyUOM, bomUOM);

		final I_PP_Cost_Collector cc = createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(getCostCollectorTypeToUse(orderBOMLine))
						.order(order)
						.productId(product.getM_Product_ID())
						.locatorId(request.getLocatorId())
						.attributeSetInstanceId(request.getAttributeSetInstanceId())
						.resourceId(order.getS_Resource_ID())
						.ppOrderBOMLineId(orderBOMLine.getPP_Order_BOMLine_ID())
						.movementDate(TimeUtil.asTimestamp(request.getMovementDate()))
						.qty(qtyIssueConv)
						.qtyScrap(qtyScrapConv)
						.qtyReject(qtyRejectConv)
						.build());

		return cc;
	}

	@Override
	public I_PP_Cost_Collector createReceipt(final IReceiptCostCollectorCandidate candidate)
	{
		final I_PP_Order_BOMLine orderBOMLine = candidate.getPP_Order_BOMLine();
		if (orderBOMLine == null)
		{
			return createFinishedGoodsReceipt(candidate);
		}
		else
		{
			return createCoProductReceipt(candidate);
		}
	}

	private I_PP_Cost_Collector createCoProductReceipt(final IReceiptCostCollectorCandidate candidate)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		//
		// Get and validate the BOM Line
		final I_PP_Order_BOMLine orderBOMLine = candidate.getPP_Order_BOMLine();
		Check.assumeNotNull(orderBOMLine, LiberoException.class, "orderBOMLine not null");
		PPOrderUtil.assertReceipt(orderBOMLine);

		//
		// Validate Product
		final I_M_Product product = candidate.getM_Product();
		if (product == null || product.getM_Product_ID() != orderBOMLine.getM_Product_ID())
		{
			throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate + "\n Expected: " + orderBOMLine.getM_Product());
		}

		return createIssue(ComponentIssueCreateRequest.builder()
				.orderBOMLine(orderBOMLine)
				.locatorId(getM_Locator_ID_ToUse(candidate))
				.attributeSetInstanceId(candidate.getM_AttributeSetInstance_ID())
				.movementDate(candidate.getMovementDate())
				.qtyUOM(candidate.getC_UOM())
				.qtyIssue(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyToReceive()))
				.qtyScrap(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyScrap()))
				.qtyReject(ppOrderBOMBL.adjustCoProductQty(candidate.getQtyReject()))
				.build());
	}

	private I_PP_Cost_Collector createFinishedGoodsReceipt(final IReceiptCostCollectorCandidate candidate)
	{
		final I_PP_Order order = candidate.getPP_Order();
		Check.assumeNotNull(order, LiberoException.class, "order not null");

		final BigDecimal qtyDelivered = order.getQtyDelivered();
		final BigDecimal qtyToDeliver = candidate.getQtyToReceive();
		final BigDecimal qtyScrap = candidate.getQtyScrap();
		final BigDecimal qtyReject = candidate.getQtyReject();
		final Timestamp movementDate = candidate.getMovementDate();

		if (qtyToDeliver.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0)
		{
			// Validate Product
			final I_M_Product product = candidate.getM_Product();
			if (product == null || product.getM_Product_ID() != order.getM_Product_ID())
			{
				throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate
						+ "\n Expected: " + order.getM_Product());
			}

			final int locatorId = getM_Locator_ID_ToUse(candidate);
			final int asiId = candidate.getM_AttributeSetInstance_ID();

			return createCollector(
					CostCollectorCreateRequest.builder()
							.costCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt)
							.order(order)
							.productId(product.getM_Product_ID())
							.locatorId(locatorId)
							.attributeSetInstanceId(asiId)
							.resourceId(order.getS_Resource_ID())
							.movementDate(movementDate)
							.qty(qtyToDeliver)
							.qtyScrap(qtyScrap)
							.qtyReject(qtyReject)
							.build());
		}

		// TODO: review this part. Drop qtyDelivered parameter because we can fetch it from PP_Order.QtyDelivered...
		// ... also consider moving this in PP_Order model validator
		order.setDateDelivered(movementDate);
		if (order.getDateStart() == null)
		{
			order.setDateStart(movementDate);
		}

		BigDecimal DQ = qtyDelivered;
		BigDecimal SQ = qtyScrap;
		BigDecimal OQ = qtyToDeliver;
		if (DQ.add(SQ).compareTo(OQ) >= 0)
		{
			order.setDateFinish(movementDate);
		}

		InterfaceWrapperHelper.save(order);

		return null;
	}

	private static final int getM_Locator_ID_ToUse(final IReceiptCostCollectorCandidate candidate)
	{
		int locatorId = candidate.getM_Locator_ID();
		if (locatorId > 0)
		{
			return locatorId;
		}

		final I_PP_Order ppOrder = candidate.getPP_Order();
		Check.assumeNotNull(ppOrder, LiberoException.class, "ppOrder not null");
		return ppOrder.getM_Locator_ID();
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
				.match();
	}

	@Override
	public void setPP_Order(final I_PP_Cost_Collector cc, final I_PP_Order order)
	{
		final I_PP_Order_Workflow ppOrderWorkflow = Services.get(IPPOrderBL.class).getPP_Order_Workflow(order);

		cc.setPP_Order(order);
		cc.setPP_Order_Workflow(ppOrderWorkflow);
		cc.setAD_Org_ID(order.getAD_Org_ID());
		cc.setM_Warehouse_ID(order.getM_Warehouse_ID());
		cc.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		cc.setC_Activity_ID(order.getC_Activity_ID());
		cc.setC_Campaign_ID(order.getC_Campaign_ID());
		cc.setC_Project_ID(order.getC_Project_ID());
		cc.setDescription(order.getDescription());
		cc.setS_Resource_ID(order.getS_Resource_ID());
		cc.setM_Product_ID(order.getM_Product_ID());
		cc.setC_UOM_ID(order.getC_UOM_ID());
		cc.setM_AttributeSetInstance_ID(order.getM_AttributeSetInstance_ID());
		cc.setMovementQty(order.getQtyOrdered());
	}

	@Override
	public I_PP_Cost_Collector createActivityControl(ActivityControlCreateRequest request)
	{
		final I_PP_Order_Node node = request.getNode();
		final I_PP_Order order = node.getPP_Order();

		return createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl)
						.order(order)
						.productId(order.getM_Product_ID())
						.locatorId(order.getM_Locator_ID())
						.attributeSetInstanceId(order.getM_AttributeSetInstance_ID())
						.resourceId(node.getS_Resource_ID())
						.ppOrderNodeId(node.getPP_Order_Node_ID())
						.movementDate(request.getMovementDate())
						.qty(request.getQtyMoved())
						.durationSetup(request.getDurationSetup())
						.duration(request.getDuration())
						.build());
	}

	@Override
	public void createUsageVariance(final I_PP_Order_BOMLine line)
	{
		if (PPOrderUtil.isMethodChangeVariance(line))
		{
			return;
		}
		if (PPOrderUtil.isComponentTypeOneOf(line, X_PP_Order_BOMLine.COMPONENTTYPE_Variant))
		{
			return;
		}

		final I_PP_Order order = line.getPP_Order();

		final BigDecimal qtyUsageVariance;
		{
			final BigDecimal qtyUsageVariancePrev = Services.get(IPPOrderBOMDAO.class).retrieveQtyUsageVariance(line); // Previous booked usage variance
			final BigDecimal qtyOpen = Services.get(IPPOrderBOMBL.class).getQtyToIssue(line);
			// Current usage variance = QtyOpen - Previous Usage Variance
			qtyUsageVariance = qtyOpen.subtract(qtyUsageVariancePrev);
		}
		if (qtyUsageVariance.signum() == 0)
		{
			return;
		}

		// Get Locator
		int locatorId = line.getM_Locator_ID();
		if (locatorId <= 0)
		{
			locatorId = order.getM_Locator_ID();
		}

		//
		createCollector(
				CostCollectorCreateRequest.builder()
						.costCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance)
						.order(order)
						.productId(line.getM_Product_ID())
						.locatorId(locatorId)
						.attributeSetInstanceId(line.getM_AttributeSetInstance_ID())
						.resourceId(order.getS_Resource_ID())
						.ppOrderBOMLineId(line.getPP_Order_BOMLine_ID())
						.qty(qtyUsageVariance)
						.build());
	}

	@Override
	public void createUsageVariance(final I_PP_Order_Node activity)
	{
		final I_PP_Order order = activity.getPP_Order();
		//
		final BigDecimal setupTimeReal = BigDecimal.valueOf(activity.getSetupTimeReal());
		final BigDecimal durationReal = BigDecimal.valueOf(activity.getDurationReal());
		if (setupTimeReal.signum() == 0 && durationReal.signum() == 0)
		{
			// nothing reported on this activity => it's not a variance, this will be auto-reported on close
			return;
		}
		//
		IPPOrderNodeBL ppOrderNodeBL = Services.get(IPPOrderNodeBL.class);
		final BigDecimal setupTimeVariancePrev = ppOrderNodeBL.getSetupTimeUsageVariance(activity);
		final BigDecimal durationVariancePrev = ppOrderNodeBL.getDurationUsageVariance(activity);
		final BigDecimal setupTimeRequired = BigDecimal.valueOf(activity.getSetupTimeRequiered());
		final BigDecimal durationRequired = BigDecimal.valueOf(activity.getDurationRequiered());
		final BigDecimal qtyOpen = ppOrderNodeBL.getQtyToDeliver(activity);
		//
		final BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
		final BigDecimal durationVariance = durationRequired.subtract(durationReal).subtract(durationVariancePrev);
		//
		if (qtyOpen.signum() == 0 && setupTimeVariance.signum() == 0 && durationVariance.signum() == 0)
		{
			return;
		}
		//
		createCollector(CostCollectorCreateRequest.builder()
				.costCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance)
				.order(order)
				.productId(order.getM_Product_ID())
				.locatorId(order.getM_Locator_ID())
				.attributeSetInstanceId(order.getM_AttributeSetInstance_ID())
				.resourceId(activity.getS_Resource_ID())
				.ppOrderNodeId(activity.getPP_Order_Node_ID())
				.qty(qtyOpen)
				.durationSetup(setupTimeVariance.intValueExact())
				.duration(durationVariance) // duration
				.build());
	}

	@Value
	private static class CostCollectorCreateRequest
	{
		private final I_PP_Order order;
		private final int productId;
		private final int locatorId;
		private final int attributeSetInstanceId;
		private final int resourceId;
		private final int ppOrderBOMLineId;
		private final int ppOrderNodeId;
		private final String costCollectorType;
		private final Date movementDate;
		private final BigDecimal qty;
		private final BigDecimal qtyScrap;
		private final BigDecimal qtyReject;
		private final int durationSetup;
		private final BigDecimal duration;

		@Builder
		private CostCollectorCreateRequest(
				@NonNull final I_PP_Order order,
				final int productId,
				final int locatorId,
				final int attributeSetInstanceId,
				final int resourceId,
				final int ppOrderBOMLineId,
				final int ppOrderNodeId,
				@NonNull final String costCollectorType,
				@Nullable final Date movementDate,
				//
				@NonNull final BigDecimal qty,
				@Nullable final BigDecimal qtyScrap,
				@Nullable final BigDecimal qtyReject,
				//
				final int durationSetup,
				@Nullable final BigDecimal duration)
		{
			this.order = order;
			this.productId = productId;
			this.locatorId = locatorId;
			this.attributeSetInstanceId = attributeSetInstanceId > 0 ? attributeSetInstanceId : 0;
			this.resourceId = resourceId;
			this.ppOrderBOMLineId = ppOrderBOMLineId;
			this.ppOrderNodeId = ppOrderNodeId;
			this.costCollectorType = costCollectorType;
			this.movementDate = movementDate != null ? movementDate : SystemTime.asDayTimestamp();
			this.qty = qty;
			this.qtyScrap = qtyScrap != null ? qtyScrap : BigDecimal.ZERO;
			this.qtyReject = qtyReject != null ? qtyReject : BigDecimal.ZERO;
			this.durationSetup = durationSetup;
			this.duration = duration != null ? duration : BigDecimal.ZERO;
		}
	}

	/**
	 * Create & Complete Cost Collector
	 */
	private static I_PP_Cost_Collector createCollector(@NonNull final CostCollectorCreateRequest request)
	{
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		final I_PP_Order ppOrder = request.getOrder();
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector)
				.adClientId(ppOrder.getAD_Client_ID())
				.adOrgId(ppOrder.getAD_Org_ID())
				.build());

		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		Services.get(IPPCostCollectorBL.class).setPP_Order(cc, ppOrder);
		cc.setPP_Order_BOMLine_ID(request.getPpOrderBOMLineId());
		cc.setPP_Order_Node_ID(request.getPpOrderNodeId());
		cc.setC_DocType_ID(docTypeId);
		cc.setC_DocTypeTarget_ID(docTypeId);
		cc.setCostCollectorType(request.getCostCollectorType());
		cc.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		cc.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		cc.setIsActive(true);
		cc.setM_Locator_ID(request.getLocatorId());
		cc.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId());
		cc.setS_Resource_ID(request.getResourceId());
		cc.setMovementDate(TimeUtil.asTimestamp(request.getMovementDate()));
		cc.setDateAcct(TimeUtil.asTimestamp(request.getMovementDate()));
		cc.setMovementQty(request.getQty());
		cc.setScrappedQty(request.getQtyScrap());
		cc.setQtyReject(request.getQtyReject());
		cc.setSetupTimeReal(new BigDecimal(request.getDurationSetup()));
		cc.setDurationReal(request.getDuration());
		cc.setPosted(false);
		cc.setProcessed(false);
		cc.setProcessing(false);
		cc.setUser1_ID(ppOrder.getUser1_ID());
		cc.setUser2_ID(ppOrder.getUser2_ID());
		cc.setM_Product_ID(request.getProductId());
		if (request.getPpOrderNodeId() > 0)
		{
			final I_PP_Order_Node ppOrderNode = InterfaceWrapperHelper.load(request.getPpOrderNodeId(), I_PP_Order_Node.class);
			cc.setIsSubcontracting(ppOrderNode.isSubcontracting());
		}
		// If this is an material issue, we should use BOM Line's UOM
		if (request.getPpOrderBOMLineId() > 0)
		{
			cc.setC_UOM(null); // we set the BOM Line UOM on beforeSave
		}

		InterfaceWrapperHelper.save(cc);

		//
		// Process the Cost Collector
		Services.get(IDocumentBL.class).processEx(cc,
				X_PP_Cost_Collector.DOCACTION_Complete,
				null // expectedDocStatus
		);

		return cc;
	}
}
