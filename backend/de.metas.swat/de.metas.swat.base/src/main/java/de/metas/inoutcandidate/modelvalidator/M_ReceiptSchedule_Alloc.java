package de.metas.inoutcandidate.modelvalidator;

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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;

@Validator(I_M_ReceiptSchedule_Alloc.class)
public class M_ReceiptSchedule_Alloc
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void updateQtyMovedAdd(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleAdded(receiptScheduleAlloc);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE })
	public void updateQtyMovedChange(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleUpdated(receiptScheduleAlloc);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void updateQtyMovedSubtract(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleDeleted(receiptScheduleAlloc);
	}
}
