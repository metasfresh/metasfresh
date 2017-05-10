package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;

import org.adempiere.model.IContextAware;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.inout.IHUCustomerReturnAllocBuilder;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleAllocBuilder;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_InOutLine_HU_Alloc;
import de.metas.inoutcandidate.api.IReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleAllocBuilder;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUCustomerReturnAllocBuilder implements IHUCustomerReturnAllocBuilder
{

	@Override
	public I_M_InOutLine_HU_Alloc build()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_M_InOutLine_HU_Alloc buildAndSave()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReceiptScheduleAllocBuilder setContext(IContextAware context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReceiptScheduleAllocBuilder setQtyToAllocate(BigDecimal qtyToAllocate)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReceiptScheduleAllocBuilder setM_InOutLine(I_M_InOutLine receiptLine)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public HUReceiptScheduleAllocBuilder setHU_QtyAllocated(BigDecimal qtyToAllocate, I_C_UOM uom)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
