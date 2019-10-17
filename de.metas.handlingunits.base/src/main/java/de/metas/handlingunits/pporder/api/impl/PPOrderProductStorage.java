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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;

/**
 * Product storage for {@link I_PP_Order} header (i.e. finished goods).
 *
 * This storage allows negative Qty because we want to allow receiving more finished goods then expected.
 *
 * It's qty methods will return:
 * <ul>
 * <li>{@link #getQtyCapacity()} - target Qty that we want to receive on this manufacturing order (i.e. QtyOrdered)
 * <li>{@link #getQty()} - how much we still need to receive
 * <li>{@link #getQtyFree()} - how much we already received on this manufacturing order (i.e. QtyDelivered + QtyScrap)
 * </ul>
 *
 * Use this storage when you want to manipulate finished goods qtys of a given manufacturing order (i.e. from manufacturing order receipt).
 */
public class PPOrderProductStorage extends AbstractProductStorage
{
	private final I_PP_Order ppOrder;
	private boolean staled = false;

	public PPOrderProductStorage(final I_PP_Order ppOrder)
	{
		super();
		setConsiderForceQtyAllocationFromRequest(false); // TODO: consider changing it to "true" (default)
		this.ppOrder = ppOrder;
	}

	@Override
	protected Capacity retrieveTotalCapacity()
	{
		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(ppOrder.getC_UOM_ID());
		// we allow negative qty because we want to allow receiving more then expected
		final boolean allowNegativeCapacity = true;
		final BigDecimal qtyCapacity = ppOrder.getQtyOrdered(); // i.e. target Qty To Receive

		return Capacity.createCapacity(
				qtyCapacity, // qty
				productId, // product
				uom, // uom
				allowNegativeCapacity // allowNegativeCapacity
				);
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		final BigDecimal qtyToReceive = Services.get(IPPOrderBL.class).getQtyOpen(ppOrder).toBigDecimal();

		return qtyToReceive;
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	private final void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(ppOrder);
		staled = false;
	}
}
