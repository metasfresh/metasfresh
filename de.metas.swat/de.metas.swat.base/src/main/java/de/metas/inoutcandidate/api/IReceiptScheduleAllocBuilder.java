package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.util.lang.IContextAware;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;

public interface IReceiptScheduleAllocBuilder
{
	I_M_ReceiptSchedule_Alloc build();

	I_M_ReceiptSchedule_Alloc buildAndSave();

	ReceiptScheduleAllocBuilder setContext(IContextAware context);

	IReceiptScheduleAllocBuilder setM_ReceiptSchedule(I_M_ReceiptSchedule receiptSchedule);

	IReceiptScheduleAllocBuilder setQtyToAllocate(BigDecimal qtyToAllocate);

	ReceiptScheduleAllocBuilder setQtyWithIssues(BigDecimal qtyWithIssues);

	IReceiptScheduleAllocBuilder setM_InOutLine(I_M_InOutLine receiptLine);

}
