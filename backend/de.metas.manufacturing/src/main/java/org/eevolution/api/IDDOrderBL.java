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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.ISingletonService;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;

public interface IDDOrderBL extends ISingletonService
{
	I_DD_Order getById(int ddOrderId);

	/**
	 * Gets Qty that needs to be received so far to destination locator.
	 *
	 * i.e. QtyOrdered - QtyDelivered
	 *
	 * The UOM is line's UOM.
	 */
	BigDecimal getQtyToReceive(I_DD_OrderLine ddOrderLine);

	/**
	 * Gets Qty that needs to be received so far to destination locator.
	 *
	 * The UOM is line's UOM.
	 *
	 * @see #getQtyToReceive(I_DD_OrderLine)
	 */
	BigDecimal getQtyToReceive(I_DD_OrderLine_Alternative ddOrderLineAlt);

	/**
	 * Gets Qty that needs to be received so far to destination locator.
	 *
	 * The UOM is line's UOM.
	 *
	 * @see #getQtyToReceive(I_DD_OrderLine)
	 */
	BigDecimal getQtyToReceive(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt);

	/**
	 * Gets Qty that needs to be shipped so far from source locator.
	 *
	 * The UOM is line's UOM.
	 */
	BigDecimal getQtyToShip(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt);

	IDDOrderMovementBuilder createMovementBuilder();

	/**
	 * Retrieves the given <code>ddOrderLine</code>'s order and invokes {@link #completeDDOrderIfNeeded(I_DD_Order)}.
	 */
	void completeDDOrderIfNeeded(I_DD_OrderLine ddOrderLine);

	/**
	 * If the given <code>ddOrder</code> is in status draft or in progress, then the method attempts to complete it. Otherwise, it assumes that the ddOrder is already completed and throws an exception
	 * if that is not the case.
	 */
	void completeDDOrderIfNeeded(I_DD_Order ddOrder);

	/**
	 * Complete all of the given DD_Orders.
	 * 
	 * @param ddOrders DD_Orders to complete.
	 * @see #completeDDOrderIfNeeded(I_DD_Order)
	 */
	void completeDDOrdersIfNeeded(Collection<? extends I_DD_Order> ddOrders);

	/**
	 * Search for Source Plant based on source locator ({@link I_DD_OrderLine#getM_Locator()}).
	 */
	@Nullable
	I_S_Resource findPlantFromOrNull(I_DD_OrderLine ddOrderLine);

	/**
	 * Checks if given DD_OrderLine's Movement is about receiving materials to target warehouse.
	 * 
	 * NOTE: this method assumes that the movementLine is linked to a DD Order line, else an exception will be thrown.
	 * 
	 * @return <code>true</code> if given DD_OrderLine's Movement is about receiving materials to target warehouse; <code>false</code> if it's about shipping materials from source warehouse to
	 *         in-transit warehouse.
	 */
	boolean isMovementReceipt(I_M_MovementLine movementLine);

	/**
	 * Complete Forward and Backward DD Orders, if they are on the same plant.
	 * Task 08059
	 */
	void completeForwardAndBackwardDDOrders(I_DD_Order ddOrder);

	/**
	 * {@link I_DD_Order#setMRP_AllowCleanup(boolean)} to <code>false</code> to backward and forward DD Orders, if they are on the same plant.
	 * 
	 * Task 08059
	 */
	void disallowMRPCleanupOnForwardAndBackwardDDOrders(I_DD_Order ddOrder);

	void completeBackwardDDOrders(I_M_Forecast forecast);

}
