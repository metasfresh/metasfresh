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
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate.ReceiptCostCollectorCandidateBuilder;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.MPPCostCollector;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.document.IDocTypeDAO;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderUtil;
import lombok.NonNull;

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
	 * @param orderBOMLine
	 * @return cost collector type
	 * @see X_PP_Cost_Collector.COSTCOLLECTORTYPE_*
	 */
	private String getCostCollectorTypeToUse(final I_PP_Order_BOMLine orderBOMLine)
	{
		String costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue;

		// Method Variance
		if (orderBOMLine.getQtyBatch().signum() == 0
				&& orderBOMLine.getQtyBOM().signum() == 0)
		{
			costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance;
		}
		else
		{
			if (PPOrderUtil.isComponentTypeOneOf(orderBOMLine, X_PP_Order_BOMLine.COMPONENTTYPE_Co_Product, X_PP_Order_BOMLine.COMPONENTTYPE_By_Product))
			{
				costCollectorType = X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance;
			}
		}

		return costCollectorType;
	}

	@Override
	public boolean isMaterialReceipt(final I_PP_Cost_Collector cc)
	{
		final boolean considerCoProductsAsReceipt = true;
		return isMaterialReceipt(cc, considerCoProductsAsReceipt);
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
	public I_PP_Cost_Collector createIssue(
			final IContextAware context,
			final I_PP_Order_BOMLine orderBOMLine,
			final int locatorId,
			final int attributeSetInstanceId,
			final Date movementDate,
			final BigDecimal qtyIssue,
			final BigDecimal qtyScrap,
			final BigDecimal qtyReject,
			final I_C_UOM qtyUOM)
	{
		final I_PP_Order order = orderBOMLine.getPP_Order();

		final String costCollectorType = getCostCollectorTypeToUse(orderBOMLine);

		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(ctx,
				X_C_DocType.DOCBASETYPE_ManufacturingCostCollector,
				order.getAD_Client_ID(),
				order.getAD_Org_ID(),
				trxName);

		//
		// Convert our Qtys from their qtyUOM to BOM's UOM
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final I_M_Product product = orderBOMLine.getM_Product();
		final I_C_UOM bomUOM = orderBOMLine.getC_UOM();
		final BigDecimal qtyIssueConv = uomConversionBL.convertQty(product, qtyIssue, qtyUOM, bomUOM);
		final BigDecimal qtyScrapConv = uomConversionBL.convertQty(product, qtyScrap, qtyUOM, bomUOM);
		final BigDecimal qtyRejectConv = uomConversionBL.convertQty(product, qtyReject, qtyUOM, bomUOM);

		final I_PP_Cost_Collector cc = MPPCostCollector.createCollector(
				order, 															// MPPOrder
				product.getM_Product_ID(),									// M_Product_ID
				locatorId,														// M_Locator_ID
				attributeSetInstanceId,											// M_AttributeSetInstance_ID
				order.getS_Resource_ID(),										// S_Resource_ID
				orderBOMLine.getPP_Order_BOMLine_ID(),							// PP_Order_BOMLine_ID
				0,																// PP_Order_Node_ID: not relevant
				docTypeId, 														// C_DocType_ID,
				costCollectorType, 												// Cost Collector Type
				TimeUtil.asTimestamp(movementDate),								// MovementDate
				qtyIssueConv,													// Qty To Issue
				qtyScrapConv,													// Qty Scapped
				qtyRejectConv,													// Qty Rejected
				0,																// durationSetup: not relevant
				BigDecimal.ZERO													// duration: not relevant
		);

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
			throw new LiberoException("@Invalid@ @M_Product_ID@: " + candidate
					+ "\n Expected: " + orderBOMLine.getM_Product());
		}

		final int locatorId = getM_Locator_ID_ToUse(candidate);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(orderBOMLine);
		return createIssue(
				context,
				orderBOMLine,
				locatorId,
				candidate.getM_AttributeSetInstance_ID(),
				candidate.getMovementDate(),
				ppOrderBOMBL.adjustCoProductQty(candidate.getQtyToReceive()), // qtyIssue
				ppOrderBOMBL.adjustCoProductQty(candidate.getQtyScrap()), // qtyScrap
				ppOrderBOMBL.adjustCoProductQty(candidate.getQtyReject()), // qtyReject
				candidate.getC_UOM());
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

			return MPPCostCollector.createCollector(
					order,															// MPPOrder
					product.getM_Product_ID(),										// M_Product_ID
					locatorId,														// M_Locator_ID
					asiId,															// M_AttributeSetInstance_ID
					order.getS_Resource_ID(),										// S_Resource_ID
					0, 																// PP_Order_BOMLine_ID
					0,																// PP_Order_Node_ID
					-1, 															// C_DocType_ID: auto
					X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt,			// Production "+"
					movementDate,													// MovementDate
					qtyToDeliver, qtyScrap, qtyReject,								// qty,scrap,reject
					0,																// durationSetup
					BigDecimal.ZERO													// duration
			);
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

	private final int getM_Locator_ID_ToUse(final IReceiptCostCollectorCandidate candidate)
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

}
