package org.eevolution.api;

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

import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_PP_Order;

import de.metas.document.IDocTypeDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;

public interface IPPOrderBL extends ISingletonService
{
	void setDefaults(I_PP_Order ppOrder);

	void setQtyEntered(I_PP_Order order, BigDecimal qtyEntered);

	void setQtyOrdered(I_PP_Order order, BigDecimal qtyOrdered);

	/**
	 * Add to Description
	 *
	 * @param orderO
	 * @param description text
	 */
	void addDescription(I_PP_Order order, String description);

	/**
	 * Set QtyBatchSize and QtyBatchs using Workflow and QtyOrdered
	 *
	 * @param order MO
	 * @param override if true, will set QtyBatchSize even if is already set (QtyBatchSize!=0)
	 */
	void updateQtyBatchs(I_PP_Order order, boolean override);

	/**
	 * @return true if ANY work was delivered for this MO (i.e. Stock Issue, Stock Receipt, Activity Control Report)
	 */
	boolean isSomethingProcessed(I_PP_Order ppOrder);

	Quantity getQtyOrdered(I_PP_Order ppOrder);

	/**
	 * Gets Open Qty (i.e. how much we still need to receive).
	 *
	 * @return Open Qty (Ordered - Received - Scrap)
	 */
	Quantity getQtyOpen(I_PP_Order ppOrder);

	Quantity getQtyReceived(I_PP_Order ppOrder);

	Quantity getQtyReceived(PPOrderId ppOrderId);

	Quantity getQtyScrapped(I_PP_Order ppOrder);

	Quantity getQtyRejected(I_PP_Order ppOrder);

	/**
	 * Gets the "direct" order line.
	 *
	 * A "direct" order line is the order line that directly triggered this manufacturing order. In other words order line's Product and manufacturing order's Product shall match.
	 *
	 * @param ppOrder manufacturing order
	 * @return direct order line or null
	 */
	I_C_OrderLine getDirectOrderLine(I_PP_Order ppOrder);

	/**
	 * Propagate Order's AD_Org_ID/Warehouse/Locator to BOM Lines
	 *
	 * @param ppOrder
	 */
	void updateBOMOrderLinesWarehouseAndLocator(I_PP_Order ppOrder);

	/**
	 * Sets manufacturing order's document type(s).
	 *
	 * @param ppOrder
	 * @param docBaseType
	 * @param docSubType document sub-type or {@link IDocTypeDAO#DOCSUBTYPE_Any}
	 * @throws DocTypeNotFoundException if no document type was found
	 */
	void setDocType(I_PP_Order ppOrder, String docBaseType, String docSubType);

	/**
	 * Set QtyOrdered=QtyDelivered, QtyClosed=QtyOrdered(old) - QtyDelivered
	 *
	 * @param ppOrder
	 */
	void closeQtyOrdered(I_PP_Order ppOrder);

	void uncloseQtyOrdered(I_PP_Order ppOrder);

	void changeScheduling(PPOrderScheduleChangeRequest request);

	void createOrderRouting(I_PP_Order ppOrder);

	void closeAllActivities(PPOrderId orderId);

	void voidOrderRouting(PPOrderId orderId);
}
