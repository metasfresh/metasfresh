package de.metas.handlingunits.inout;

import java.math.BigDecimal;

import org.adempiere.model.IContextAware;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.receiptschedule.impl.HUReceiptScheduleAllocBuilder;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_InOutLine_HU_Alloc;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IHUCustomerReturnAllocBuilder
{

	I_M_InOutLine_HU_Alloc build();

	I_M_InOutLine_HU_Alloc buildAndSave();

	IHUCustomerReturnAllocBuilder setContext(IContextAware context);

	IHUCustomerReturnAllocBuilder setQtyToAllocate(BigDecimal qtyToAllocate);

	IHUCustomerReturnAllocBuilder setM_InOutLine(I_M_InOutLine receiptLine);

	I_M_InOutLine_HU_Alloc build(I_M_InOutLine_HU_Alloc huAlloc);

	IHUCustomerReturnAllocBuilder setHU_QtyAllocated(BigDecimal qtyAllocated);

	IHUCustomerReturnAllocBuilder setHU_QtyAllocated(BigDecimal qtyAllocated, I_C_UOM uom);
	
	IHUCustomerReturnAllocBuilder setM_LU_HU(I_M_HU luHU);

	void setQtyWithIssues(BigDecimal zero);

	IHUCustomerReturnAllocBuilder setM_TU_HU(I_M_HU tuHUActual);

	IHUCustomerReturnAllocBuilder setVHU(I_M_HU vhu);

}
