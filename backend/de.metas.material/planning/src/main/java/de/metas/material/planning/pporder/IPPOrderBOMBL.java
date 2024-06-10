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

import de.metas.document.sequence.DocSequenceId;
import de.metas.material.planning.exception.MrpException;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

public interface IPPOrderBOMBL extends ISingletonService
{
	I_PP_Order_BOMLine getOrderBOMLineById(PPOrderBOMLineId orderBOMLineId);

	<T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(PPOrderId orderId, Class<T> orderBOMLineClass);

	PPOrderQuantities getQuantities(
			@NonNull I_PP_Order ppOrder,
			@NonNull UomId targetUOMId);

	PPOrderQuantities getQuantities(@NonNull I_PP_Order ppOrder);

	void setQuantities(
			@NonNull I_PP_Order ppOrder,
			@NonNull PPOrderQuantities from);

	void changeQuantities(
			@NonNull I_PP_Order ppOrder,
			@NonNull UnaryOperator<PPOrderQuantities> updater);

	void createOrderBOMAndLines(I_PP_Order ppOrder);

	void explodePhantom(
			I_PP_Order_BOMLine orderBOMLine,
			Quantity qtyOrdered);

	I_C_UOM getBOMLineUOM(I_PP_Order_BOMLine bomLine);

	@NonNull UomId getBOMLineUOMId(@NonNull I_PP_Order_BOMLine bomLine);

	@NonNull UomId getBOMLineUOMId(PPOrderBOMLineId orderBOMLineId);

	OrderBOMLineQuantities getQuantities(PPOrderBOMLineId orderBOMLineId);
	
	OrderBOMLineQuantities getQuantities(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets Qty Open (i.e. Qty To Issue).
	 *
	 * @return Qty Open (Standard Required - Delivered)
	 */
	Quantity getQtyToIssue(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets qty which is required to issue (i.e. target quantity), without considering how much was issued until now.
	 *
	 * @return qty required to issue (positive value)
	 */
	Quantity getQtyRequiredToIssue(I_PP_Order_BOMLine orderBOMLine);

	void setQtyRequiredToIssueOrReceive(
			I_PP_Order_BOMLine orderBOMLine,
			Quantity qtyRequired);

	/**
	 * Gets Qty To Receive
	 *
	 * @return Qty To Receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt
	 */
	Quantity getQtyToReceive(I_PP_Order_BOMLine orderBOMLine);

	/**
	 * Gets qty which is required to receive (i.e. target quantity).
	 *
	 * @return Qty required to receive (positive)
	 * @throws MrpException if BOM Line is not of type receipt
	 */
	Quantity getQtyRequiredToReceive(I_PP_Order_BOMLine orderBOMLine);

	Percent getCoProductCostDistributionPercent(I_PP_Order_BOMLine orderBOMLine, @NonNull Quantity mainProductQty);

	void addQty(OrderBOMLineQtyChangeRequest request);

	Quantity getQtyRequired(ComputeQtyRequiredRequest computeQtyRequiredRequest);

	Quantity computeQtyRequiredByQtyOfFinishedGoods(
			I_PP_Product_BOMLine productBOMLine,
			Quantity qtyFinishedGood);

	/**
	 * Calculates how much qty we STILL have to issue to cover proportionally the quantity of finished goods that was already received.
	 *
	 * @return qty to issue (in given <code>targetUOM</code>)
	 */
	Quantity computeQtyToIssueBasedOnFinishedGoodReceipt(
			@NonNull I_PP_Order_BOMLine orderBOMLine,
			@NonNull I_C_UOM targetUOM,
			@NonNull DraftPPOrderQuantities draftQuantities);

	void voidBOMLine(I_PP_Order_BOMLine line);

	void validateBeforeClose(I_PP_Order_BOMLine line);

	void close(I_PP_Order_BOMLine line);

	void unclose(I_PP_Order_BOMLine line);

	boolean isSomethingReported(final I_PP_Order ppOrder);

	Optional<DocSequenceId> getSerialNoSequenceId(PPOrderId ppOrderId);

	QtyCalculationsBOM getQtyCalculationsBOM(I_PP_Order order);

	void save(I_PP_Order_BOMLine orderBOMLine);

	Set<ProductId> getProductIdsToIssue(PPOrderId ppOrderId);
}
