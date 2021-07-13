package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderQuantities;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;

import java.math.BigDecimal;

/**
 * Product storage for {@link I_PP_Order} header (i.e. finished goods).
 * <p>
 * This storage allows negative Qty because we want to allow receiving more finished goods then expected.
 * <p>
 * It's qty methods will return:
 * <ul>
 * <li>{@link #getQtyCapacity()} - target Qty that we want to receive on this manufacturing order (i.e. QtyOrdered)
 * <li>{@link #getQty()} - how much we still need to receive
 * <li>{@link #getQtyFree()} - how much we already received on this manufacturing order (i.e. QtyDelivered + QtyScrap)
 * </ul>
 * <p>
 * Use this storage when you want to manipulate finished goods qtys of a given manufacturing order (i.e. from manufacturing order receipt).
 */
public class PPOrderProductStorage extends AbstractProductStorage
{
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);

	private final I_PP_Order ppOrder;
	private boolean staled = false;

	public PPOrderProductStorage(final I_PP_Order ppOrder)
	{
		setConsiderForceQtyAllocationFromRequest(false); // TODO: consider changing it to "true" (default)
		this.ppOrder = ppOrder;
	}

	@Override
	protected Capacity retrieveTotalCapacity()
	{
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		// we allow negative qty because we want to allow receiving more then expected
		final PPOrderQuantities ppOrderQtys = getPPOrderQuantities();
		final Quantity qtyCapacity = ppOrderQtys.getQtyRequiredToProduce(); // i.e. target Qty To Receive
		final boolean allowNegativeCapacity = true;

		return Capacity.createCapacity(
				qtyCapacity.toBigDecimal(), // qty
				productId, // product
				qtyCapacity.getUOM(), // uom
				allowNegativeCapacity // allowNegativeCapacity
		);
	}

	private PPOrderQuantities getPPOrderQuantities()
	{
		return orderBOMBL.getQuantities(ppOrder);
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		final PPOrderQuantities ppOrderQtys = getPPOrderQuantities();
		final Quantity qtyToReceive = ppOrderQtys.getQtyRemainingToProduce();

		return qtyToReceive.toBigDecimal();
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	private void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(ppOrder);
		staled = false;
	}
}
