package de.metas.material.planning.pporder;

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
import java.util.Optional;

import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.document.sequence.DocSequenceId;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.MrpException;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import de.metas.util.lang.Percent;

public interface IPPOrderBOMBL extends ISingletonService
{
	void createOrderBOMAndLines(I_PP_Order ppOrder);

	void explodePhantom(I_PP_Order_BOMLine orderBOMLine, Quantity qtyOrdered);

	I_C_UOM getStockingUOM(I_PP_Order_BOMLine orderBOMLine);

	/** @return qty already issued (positive, in case of components) or received (negative, in case of co/by products) */
	Quantity getQtyIssuedOrReceived(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty Open (i.e. Qty To Issue).
	 *
	 * Same as {@link #getQtyToIssue(I_PP_Order_BOMLine, Quantity)} but it will use the standard required quantity (i.e. {@link I_PP_Order_BOMLine#getQtyRequiered()}).
	 *
	 * @return Qty Open (Requiered - Delivered)
	 */
	Quantity getQtyToIssue(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty Open (i.e. Qty To Issue).
	 *
	 * @param orderBOMLine order bom line
	 * @param qtyToIssueRequiered quantity required to be considered (instead of standard qty required to issue from BOM Line)
	 * @return Qty Open (Requiered - Delivered)
	 */
	Quantity getQtyToIssue(I_PP_Order_BOMLine orderBOMLine, Quantity qtyToIssueRequiered);

	/**
	 * Gets qty which is required to issue (i.e. target quantity), without considering how much was issued until now.
	 *
	 * @param orderBOMLine
	 * @return qty required to issue (positive value)
	 */
	Quantity getQtyRequiredToIssue(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty To Receive
	 *
	 * @param orderBOMLine
	 * @return Qty To Receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 */
	Quantity getQtyToReceive(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets qty which is required to receive (i.e. target quantity).
	 *
	 * @param orderBOMLine
	 * @return Qty required to receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 */
	Quantity getQtyRequiredToReceive(I_PP_Order_BOMLine orderBOMLine);
	
	Percent getCoProductCostDistributionPercent(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Returns the negated value of the given <code>qty</code>.
	 * <p>
	 * Note: In case of Co/By-Products, we need to issue negative Qtys
	 *
	 * @param qty
	 * @return
	 */
	BigDecimal adjustCoProductQty(BigDecimal qty);

	Quantity adjustCoProductQty(Quantity qty);

	void addQty(OrderBOMLineQtyChangeRequest request);

	/**
	 * Calculates how much qty we STILL have to issue to cover proportionally the quantity of finished goods that was already received.
	 *
	 * @param orderBOMLine
	 * @param uom
	 * @return qty to issue (in given <code>uom</code>)
	 */
	Quantity computeQtyToIssueBasedOnFinishedGoodReceipt(I_PP_Order_BOMLine orderBOMLine, I_C_UOM uom);

	void voidBOMLine(I_PP_Order_BOMLine line);

	void close(I_PP_Order_BOMLine line);

	void unclose(I_PP_Order_BOMLine line);

	/**
	 * Computes the quantity for the given {@code ppOrderLinePojo} based on infos from all three parameters.
	 *
	 * @param ppOrderLinePojo
	 * @param ppOrderPojo
	 * @param qtyFinishedGood
	 * @return
	 */
	Quantity computeQtyRequired(PPOrderLine ppOrderLinePojo, BigDecimal qtyFinishedGood);

	boolean isSomethingReportedOnBOMLines(PPOrderId ppOrderId);
	
	Optional<DocSequenceId> getSerialNoSequenceId(PPOrderId ppOrderId);
}
