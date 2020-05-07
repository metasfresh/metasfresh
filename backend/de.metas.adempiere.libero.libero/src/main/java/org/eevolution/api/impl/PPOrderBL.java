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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.MDocType;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_Workflow;
import org.eevolution.model.X_PP_Order;

import de.metas.document.IDocTypeDAO;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import de.metas.product.IStorageBL;

public class PPOrderBL implements IPPOrderBL
{
	// private final transient Logger log = CLogMgt.getLogger(getClass());

	@Override
	public void setDefaults(final I_PP_Order ppOrder)
	{
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(Env.getCtx(), MDocType.DOCBASETYPE_ManufacturingOrder, ppOrder.getAD_Client_ID(), ppOrder.getAD_Org_ID(), ITrx.TRXNAME_None);

		ppOrder.setLine(10);
		ppOrder.setPriorityRule(X_PP_Order.PRIORITYRULE_Medium);
		ppOrder.setDescription("");
		ppOrder.setQtyDelivered(BigDecimal.ZERO);
		ppOrder.setQtyReject(BigDecimal.ZERO);
		ppOrder.setQtyScrap(BigDecimal.ZERO);
		ppOrder.setIsSelected(false);
		ppOrder.setIsSOTrx(false);
		ppOrder.setIsApproved(false);
		ppOrder.setIsPrinted(false);
		ppOrder.setProcessed(false);
		ppOrder.setProcessing(false);
		ppOrder.setPosted(false);
		ppOrder.setC_DocTypeTarget_ID(docTypeId);
		ppOrder.setC_DocType_ID(docTypeId);
		ppOrder.setDocStatus(X_PP_Order.DOCSTATUS_Drafted);
		ppOrder.setDocAction(X_PP_Order.DOCACTION_Complete);
	}


	/**
	 * Set Qty Entered - enforce entered UOM
	 *
	 * @param QtyEntered
	 */
	@Override
	public void setQtyEntered(final I_PP_Order order, final BigDecimal QtyEntered)
	{
		final BigDecimal qtyEnteredToUse;
		if (QtyEntered != null && order.getC_UOM_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(order);
			final int precision = Services.get(IUOMConversionBL.class).getPrecision(ctx, order.getC_UOM_ID());
			qtyEnteredToUse = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			qtyEnteredToUse = QtyEntered;
		}
		order.setQtyEntered(qtyEnteredToUse);
	}	// setQtyEntered

	/**
	 * Set Qty Ordered - enforce Product UOM
	 *
	 * @param QtyOrdered
	 */
	@Override
	public void setQtyOrdered(final I_PP_Order order, final BigDecimal QtyOrdered)
	{
		final BigDecimal qtyOrderedToUse;
		if (QtyOrdered != null)
		{
			final I_M_Product product = order.getM_Product();
			final int precision = Services.get(IProductBL.class).getUOMPrecision(product);
			qtyOrderedToUse = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			qtyOrderedToUse = QtyOrdered;
		}
		order.setQtyOrdered(qtyOrderedToUse);
	}	// setQtyOrdered

	@Override
	public void addDescription(final I_PP_Order order, final String description)
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
	}	// addDescription

	@Override
	public void updateQtyBatchs(I_PP_Order order, boolean override)
	{
		BigDecimal qtyBatchSize = order.getQtyBatchSize();
		if (qtyBatchSize.signum() == 0 || override)
		{
			final int AD_Workflow_ID = order.getAD_Workflow_ID();
			// No workflow entered, or is just a new record:
			if (AD_Workflow_ID <= 0)
			{
				return;
			}

			final I_AD_Workflow wf = order.getAD_Workflow();
			qtyBatchSize = wf.getQtyBatchSize().setScale(0, RoundingMode.UP);
			order.setQtyBatchSize(qtyBatchSize);
		}

		final BigDecimal qtyBatchs;
		if (qtyBatchSize.signum() == 0)
		{
			qtyBatchs = BigDecimal.ONE;
		}
		else
		{
			final BigDecimal qtyOrdered = order.getQtyOrdered();
			qtyBatchs = qtyOrdered.divide(qtyBatchSize, 0, BigDecimal.ROUND_UP);
		}
		order.setQtyBatchs(qtyBatchs);
	}

	@Override
	public void orderStock(final I_PP_Order ppOrder)
	{
		//
		// Check if locator was changed. If yes, we need to unreserve first what was reserved before
		final I_PP_Order ppOrderOld = InterfaceWrapperHelper.createOld(ppOrder, I_PP_Order.class);
		if (ppOrderOld.getM_Locator_ID() != ppOrder.getM_Locator_ID())
		{
			final BigDecimal qtyReservedNew = orderStock(ppOrderOld, BigDecimal.ZERO);
			if (qtyReservedNew.signum() != 0)
			{
				throw new LiberoException("Cannot unreserve all stock for " + ppOrderOld);
			}
			ppOrder.setQtyReserved(BigDecimal.ZERO);
		}

		//
		// Adjust stock reservation to "how much we still need to receive"
		final BigDecimal qtyReservedTarget;
		if (isQtyReservationEnabled(ppOrder))
		{
			qtyReservedTarget = getQtyOpen(ppOrder);
		}
		else
		{
			qtyReservedTarget = BigDecimal.ZERO;
		}
		final BigDecimal qtyReservedNew = orderStock(ppOrder, qtyReservedTarget);
		ppOrder.setQtyReserved(qtyReservedNew);

	} // reserveStock

	/**
	 * Reserve/Unreserve stock.
	 *
	 * NOTE: this method is not changing the given <code>ppOrder</code>.
	 *
	 * @param ppOrder
	 * @param qtyReservedRequested how much we want to have reserved at the end.
	 * @return how much was actual reserved at the end; you can use this value to set {@link I_PP_Order#setQtyReserved(BigDecimal)}.
	 */
	private BigDecimal orderStock(final I_PP_Order ppOrder, final BigDecimal qtyReservedRequested)
	{
		//
		// Calculate how much we really need to reserve more/less:
		final BigDecimal qtyReservedActual = ppOrder.getQtyReserved();
		BigDecimal qtyReservedTarget = qtyReservedRequested;
		final BigDecimal qtyReservedDiff;
		final I_M_Product product = ppOrder.getM_Product();
		if (!Services.get(IProductBL.class).isStocked(product))
		{
			//
			// Case: we are dealing with a product which is not stocked
			// => we need to make sure we have zero reservations
			qtyReservedDiff = qtyReservedActual.negate();
			qtyReservedTarget = BigDecimal.ZERO;
		}
		else if (qtyReservedTarget.signum() < 0)
		{
			//
			// Case: We issued more then it was needed
			// We just need to unreserve what was reserved until now
			qtyReservedDiff = qtyReservedActual.negate();
			qtyReservedTarget = BigDecimal.ZERO;
		}
		else
		{
			qtyReservedDiff = qtyReservedTarget.subtract(qtyReservedActual);
		}

		//
		// Update Storage (if we have something to reserve/unreserve)
		if (qtyReservedDiff.signum() != 0)
		{
			final I_M_Locator locator = ppOrder.getM_Locator();
			final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);
			final String trxName = InterfaceWrapperHelper.getTrxName(ppOrder);
			Services.get(IStorageBL.class).addQtyOrdered(ctx,
					locator,
					product.getM_Product_ID(),
					ppOrder.getM_AttributeSetInstance_ID(),
					qtyReservedDiff,
					trxName);
		}

		// update line
		// orderBOMLine.setQtyReserved(qtyReservedTarget);

		return qtyReservedTarget;
	}

	@Override
	public boolean isDelivered(final I_PP_Order ppOrder)
	{
		if (ppOrder.getQtyDelivered().signum() > 0 || ppOrder.getQtyScrap().signum() > 0 || ppOrder.getQtyReject().signum() > 0)
		{
			return true;
		}

		for (final I_PP_Order_BOMLine line : Services.get(IPPOrderBOMDAO.class).retrieveOrderBOMLines(ppOrder))
		{
			if (line.getQtyDelivered().signum() > 0)
			{
				return true;
			}
		}

		for (final I_PP_Order_Node node : Services.get(IPPOrderWorkflowDAO.class).retrieveNodes(ppOrder))
		{
			if (node.getQtyDelivered().signum() > 0)
			{
				return true;
			}
			if (node.getDurationReal() > 0)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public BigDecimal getQtyOpen(final I_PP_Order ppOrder)
	{
		final BigDecimal qtyOrdered = ppOrder.getQtyOrdered();
		final BigDecimal qtyDelivered = ppOrder.getQtyDelivered();
		final BigDecimal qtyScrap = ppOrder.getQtyScrap();
		final BigDecimal qtyToDeliver = qtyOrdered.subtract(qtyDelivered).subtract(qtyScrap);
		return qtyToDeliver;
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
		final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		for (final I_PP_Order_BOMLine orderBOMLine : ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder))
		{
			orderBOMLine.setPP_Order(ppOrder); // for caching (to not load the PP_Order again)
			ppOrderBOMBL.updateWarehouseAndLocator(orderBOMLine);
			InterfaceWrapperHelper.save(orderBOMLine);
		}
	}

	private final static String DYNATTR_ForceQtyReservation = PPOrderBL.class.getName() + "#ForceQtyReservation";

	@Override
	public void setForceQtyReservation(final I_PP_Order ppOrder, final boolean forceQtyReservation)
	{
		InterfaceWrapperHelper.setDynAttribute(ppOrder, DYNATTR_ForceQtyReservation, forceQtyReservation);
	}

	private boolean isQtyReservationEnabled(final I_PP_Order ppOrder)
	{
		if (ppOrder.isProcessed())
		{
			return true;
		}

		final Boolean forceQtyReservation = InterfaceWrapperHelper.getDynAttribute(ppOrder, DYNATTR_ForceQtyReservation);
		if (forceQtyReservation != null && forceQtyReservation.booleanValue())
		{
			return true;
		}

		// If we already have reserved a quantity, continue doing reservations
		if (ppOrder.getQtyReserved().signum() != 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public I_PP_Order_Workflow getPP_Order_Workflow(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		//
		// Check if we have it "cached" as a dynamic attribute
		final String key = I_PP_Order_Workflow.class.getName();
		I_PP_Order_Workflow ppOrderWorkflow = InterfaceWrapperHelper.getDynAttribute(ppOrder, key);
		if (ppOrderWorkflow != null)
		{
			return ppOrderWorkflow;
		}

		//
		// Retrieve from database
		ppOrderWorkflow = Services.get(IPPOrderWorkflowDAO.class).retrieveOrderWorkflow(ppOrder);
		InterfaceWrapperHelper.setDynAttribute(ppOrder, key, ppOrderWorkflow);
		return ppOrderWorkflow;
	}

	@Override
	public void setDocType(final I_PP_Order ppOrder, final String docBaseType, final String docSubType)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		Check.assumeNotEmpty(docBaseType, "docBaseType not empty");

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ppOrder);
		final String trxName = InterfaceWrapperHelper.getTrxName(ppOrder);
		final int adClientId = ppOrder.getAD_Client_ID();
		final int adOrgId = ppOrder.getAD_Org_ID();
		final int docTypeId = docTypeDAO.getDocTypeId(ctx, docBaseType, docSubType, adClientId, adOrgId, trxName);
		ppOrder.setC_DocTypeTarget_ID(docTypeId);
		ppOrder.setC_DocType_ID(docTypeId);
	}

	@Override
	public void closeQtyOrdered(final I_PP_Order ppOrder)
	{
		final BigDecimal qtyOrderedOld = ppOrder.getQtyOrdered();
		final BigDecimal qtyDelivered = ppOrder.getQtyDelivered();

		ppOrder.setQtyBeforeClose(qtyOrderedOld);
		setQtyOrdered(ppOrder, qtyDelivered);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Clear Ordered Quantities
		// NOTE: at this point we assume QtyOrdered==QtyDelivered => QtyReserved (new)=0
		orderStock(ppOrder);
	}

	@Override
	public void uncloseQtyOrdered(final I_PP_Order ppOrder)
	{
		final BigDecimal qtyOrderedBeforeClose = ppOrder.getQtyBeforeClose();

		ppOrder.setQtyOrdered(qtyOrderedBeforeClose);
		ppOrder.setQtyBeforeClose(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Update Ordered Quantities
		orderStock(ppOrder);
	}
}
