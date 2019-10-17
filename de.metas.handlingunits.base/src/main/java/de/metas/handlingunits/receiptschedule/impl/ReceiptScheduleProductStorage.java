package de.metas.handlingunits.receiptschedule.impl;

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

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;

/* package */class ReceiptScheduleProductStorage extends AbstractProductStorage
{
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final I_M_ReceiptSchedule schedule;
	private final Capacity capacityTotal;
	private boolean staled = false;

	public ReceiptScheduleProductStorage(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule schedule, final boolean enforceCapacity)
	{
		super();
		this.schedule = InterfaceWrapperHelper.create(schedule, I_M_ReceiptSchedule.class);

		final boolean allowNegativeCapacity = !enforceCapacity; // true because we want to over/under allocate on this receipt schedule
		capacityTotal = Capacity.createCapacity(
				receiptScheduleBL.getQtyOrdered(schedule), // qty
				ProductId.ofRepoId(schedule.getM_Product_ID()), // product
				Services.get(IUOMDAO.class).getById(schedule.getC_UOM_ID()), // uom
				allowNegativeCapacity
				);
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		//
		// Capacity is the total Qty required on receipt schedule
		final BigDecimal qtyCapacity = getTotalCapacity().toBigDecimal();
		final BigDecimal qtyMoved = receiptScheduleBL.getQtyMoved(schedule);
		// final BigDecimal qtyAllocatedOnHUs = Services.get(IHUReceiptScheduleDAO.class).getQtyAllocatedOnHUs(schedule);
		final BigDecimal qtyAllocatedOnHUs = BigDecimal.ZERO;

		final BigDecimal qtyToMove = qtyCapacity.subtract(qtyMoved).subtract(qtyAllocatedOnHUs);

		// NOTE: Qty to Move is the actual storage qty
		// because this is an inbound transaction
		// and storage qty means qty which is available to take out
		return qtyToMove;
	}

	@Override
	protected Capacity retrieveTotalCapacity()
	{
		return capacityTotal;
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

		InterfaceWrapperHelper.refresh(schedule);
		staled = false;
	}
}
