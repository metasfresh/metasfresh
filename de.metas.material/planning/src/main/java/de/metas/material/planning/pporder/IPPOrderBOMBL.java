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
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.planning.exception.MrpException;
import de.metas.quantity.Quantity;

public interface IPPOrderBOMBL extends ISingletonService
{

	I_PP_Order_BOM createOrderBOMAndLines(I_PP_Order ppOrder);

	void explodePhantom(I_PP_Order_BOMLine orderBOMLine, BigDecimal qtyOrdered);

	/**
	 * Gets Qty Open (i.e. Qty To Issue).
	 *
	 * Same as {@link #getQtyToIssue(I_PP_Order_BOMLine, BigDecimal)} but it will use the standard required quantity (i.e. {@link I_PP_Order_BOMLine#getQtyRequiered()}).
	 *
	 * @return Qty Open (Requiered - Delivered)
	 */
	BigDecimal getQtyToIssue(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty Open (i.e. Qty To Issue).
	 *
	 * @param orderBOMLine order bom line
	 * @param qtyToIssueRequiered quantity required to be considered (instead of standard qty required to issue from BOM Line)
	 * @return Qty Open (Requiered - Delivered)
	 */
	BigDecimal getQtyToIssue(I_PP_Order_BOMLine orderBOMLine, BigDecimal qtyToIssueRequiered);

	/**
	 * Gets qty which is required to issue (i.e. target quantity), without considering how much was issued until now.
	 *
	 * @param orderBOMLine
	 * @return qty required to issue (positive value)
	 */
	BigDecimal getQtyRequiredToIssue(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty To Receive
	 *
	 * @param orderBOMLine
	 * @return Qty To Receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 */
	BigDecimal getQtyToReceive(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets qty which is required to receive (i.e. target quantity).
	 *
	 * @param orderBOMLine
	 * @return Qty required to receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt (see {@link #isReceipt(I_PP_Order_BOMLine)}).
	 */
	BigDecimal getQtyRequiredToReceive(I_PP_Order_BOMLine orderBOMLine);

	void reserveStock(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	void addDescription(I_PP_Order_BOMLine orderBOMLine, String description);

	/**
	 * set Order BOM Line's Warehouse and Locator from {@link I_PP_Order} (manufacturing order header).
	 *
	 * NOTE: this method is not saving <code>orderBOMLine</code>.
	 *
	 * @param orderBOMLine
	 */
	void updateWarehouseAndLocator(I_PP_Order_BOMLine orderBOMLine);

	void setForceQtyReservation(I_PP_Order_BOMLine orderBOMLine, boolean forceQtyReservation);

	/**
	 * Returns the negated value of the given <code>qty</code>.
	 * <p>
	 * Note: In case of Co/By-Products, we need to issue negative Qtys
	 *
	 * @param qty
	 * @return
	 */
	BigDecimal adjustCoProductQty(BigDecimal qty);

	/**
	 * Adds given Qtys to {@link I_PP_Order_BOMLine}.
	 *
	 * @param ppOrderBOMLine
	 * @param isUsageVariance true if these quantities are coming from a usage variance cost collector
	 * @param qtyDeliveredToAdd
	 */
	void addQtyDelivered(I_PP_Order_BOMLine ppOrderBOMLine,
			boolean isUsageVariance,
			BigDecimal qtyDeliveredToAdd);

	/**
	 * Calculates how much qty is required for given BOM Line considering the actual quantity required of finished good.<br/>
	 * In other words, how much will be required considering that the delivered finish goods could be more then planned initially.<br/>
	 * By "actual quantity required of finished good" we mean the maximum between the "quantity required of finished good" and "quantity delivered of finished good".<br/>
	 * <br/>
	 * Example:<br/>
	 * Consider a manufacturing order with 100 finished goods ordered. Quantity that was actually produced is 100 finished goods.<br/>
	 * We have a component which needs 350mm for each finished good.<br/>
	 * So the total standard quantity required of that component, to produce 100 finish good items is 100 x 350mm = 35000mm.<br/>
	 * Same will be projected quantity required.<br/>
	 * <br/>
	 * Now, consider that quantity of finished goods produced is 110 (more then ordered).<br/>
	 * In this case projected quantity required will consider the quantity actually produced instead of quantity ordered, because it's bigger.<br/>
	 * So the result will be 110(quantity produced) x 350mm.<br/>
	 *
	 * @param orderBOMLine
	 * @return projected quantity required.
	 */
	BigDecimal calculateQtyRequiredProjected(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Calculates how much qty we STILL have to issue to cover proportionally the quantity of finished goods that was already received.
	 *
	 * @param orderBOMLine
	 * @param uom
	 * @return qty to issue (in given <code>uom</code>)
	 */
	Quantity calculateQtyToIssueBasedOnFinishedGoodReceipt(I_PP_Order_BOMLine orderBOMLine, I_C_UOM uom);

	void close(I_PP_Order_BOMLine line);

	void unclose(I_PP_Order_BOMLine line);

	void reserveStock(List<I_PP_Order_BOMLine> lines);

	/**
	 * Computes the quantity for the given {@code ppOrderLinePojo} based on infos from all three paramaters.
	 *
	 * @param ppOrderLinePojo
	 * @param ppOrderPojo
	 * @param qtyFinishedGood
	 * @return
	 */
	BigDecimal calculateQtyRequired(PPOrderLine ppOrderLinePojo, PPOrder ppOrderPojo, BigDecimal qtyFinishedGood);
}
