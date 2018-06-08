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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut_Receipt
{
	/**
	 * When a receipt is reversed we need to reverse the allocations too.
	 * 
	 * Precisely, we create {@link I_M_ReceiptSchedule_Alloc} records to allocate reversal lines to initial {@link I_M_ReceiptSchedule}
	 * 
	 * @param receipt
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void reverseReceiptScheduleAllocs(final I_M_InOut receipt)
	{
		// Only if it's a receipt
		if (receipt.isSOTrx())
		{
			return;
		}

		for (final I_M_InOutLine line : Services.get(IInOutDAO.class).retrieveLines(receipt))
		{
			final List<I_M_ReceiptSchedule_Alloc> lineAllocs = Services.get(IReceiptScheduleDAO.class).retrieveRsaForInOutLine(line);
			if (lineAllocs.isEmpty())
			{
				continue;
			}

			final I_M_InOutLine reversalLine = line.getReversalLine();
			Check.assumeNotNull(reversalLine, "reversalLine not null (original line: {})", line);
			Check.assume(reversalLine.getM_InOutLine_ID() > 0, "reversalLine not null (original line: {})", line);

			reverseAllocations(lineAllocs, reversalLine);
		}
	}

	private void reverseAllocations(final List<I_M_ReceiptSchedule_Alloc> lineAllocs, final I_M_InOutLine reversalLine)
	{
		for (final I_M_ReceiptSchedule_Alloc lineAlloc : lineAllocs)
		{
			reverseAllocation(lineAlloc, reversalLine);
		}
	}

	private void reverseAllocation(I_M_ReceiptSchedule_Alloc lineAlloc, I_M_InOutLine reversalLine)
	{
		final I_M_ReceiptSchedule_Alloc reversalLineAlloc = Services.get(IReceiptScheduleBL.class).reverseAllocation(lineAlloc);
		reversalLineAlloc.setM_InOutLine(reversalLine);
		InterfaceWrapperHelper.save(reversalLineAlloc);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID })
	public void deleteReceiptScheduleAllocs(final I_M_InOut receipt)
	{
		// Only if it's a receipt
		if (receipt.isSOTrx())
		{
			return;
		}

		Services.get(IReceiptScheduleDAO.class)
				.retrieveRsaForInOut(receipt)
				.forEach(rsa -> InterfaceWrapperHelper.delete(rsa));
	}

	
	
}
