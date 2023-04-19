package de.metas.handlingunits.receiptschedule.model.validator;

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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUAssignmentListener;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * 06833: ReceiptSchedules (which are created by async) shall directly destroy HUs depending on their status.
 *
 * @author al
 */
@Validator(I_M_ReceiptSchedule.class)
public class M_ReceiptSchedule
{
	@Init
	public void init()
	{
		Services.get(IHUAssignmentBL.class)
				.registerHUAssignmentListener(ReceiptScheduleHUAssignmentListener.instance);
	}

	/**
	 * After deleting a receipt schedule, destroy it's HUs, which are no longer valid
	 *
	 * @param rs
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onReceiptScheduleDelete(final I_M_ReceiptSchedule rs)
	{
		Check.assumeNotNull(rs, "Receipt schedule is null", rs);

		// List containing all the allocations for the give receipt schedule (including the ones without HU
		final List<I_M_ReceiptSchedule_Alloc> allocations = Services.get(IReceiptScheduleDAO.class).retrieveRsaForRs(rs);

		final String trxName = InterfaceWrapperHelper.getTrxName(rs);

		// 07232
		// Remove the assigned handling units

		// List of handling units receipt schedule allocations.
		// This is where we take the hus from and we mark them as destroyed
		final List<de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc> huAllocs = Services.get(IHUReceiptScheduleDAO.class).retrieveAllHandlingUnitAllocations(rs, trxName);

		Services.get(IHUReceiptScheduleBL.class).destroyHandlingUnits(huAllocs, trxName);

		// Finally, delete all the allocations ((hu or not hu)
		for (final I_M_ReceiptSchedule_Alloc alloc : allocations)
		{
			InterfaceWrapperHelper.delete(alloc);
		}
	}
}
