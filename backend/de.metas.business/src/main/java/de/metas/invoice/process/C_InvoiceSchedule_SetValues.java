/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.invoice.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceSchedule;

import java.math.BigDecimal;

/**
 * Set values to the selected C_InvoiceSchedule. 
 * We don't want to allow this via UI because there is a MI that invalidates affected invoice-candidates, which might take some time and make the UI seem to hang.
 */
public class C_InvoiceSchedule_SetValues extends JavaProcess implements IProcessPrecondition
{
	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceDistance, mandatory = true)
	private Integer invoiceDistance;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency, mandatory = true)
	private String invoiceFrequency;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceDay)
	private Integer invoiceDay;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceWeekDay)
	private String invoiceWeekDay;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_IsAmount, mandatory = true)
	private boolean isAmount;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_Amt)
	private BigDecimal amt;
	
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}
	
	@Override
	protected String doIt() throws Exception
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = getProcessInfo().getRecord(I_C_InvoiceSchedule.class);
		
		invoiceScheduleRecord.setInvoiceDistance(invoiceDistance);
		invoiceScheduleRecord.setInvoiceFrequency(invoiceFrequency);
		invoiceScheduleRecord.setInvoiceDay(invoiceDay);
		invoiceScheduleRecord.setInvoiceWeekDay(invoiceWeekDay);
		invoiceScheduleRecord.setIsAmount(isAmount);
		invoiceScheduleRecord.setAmt(amt);

		InterfaceWrapperHelper.saveRecord(invoiceScheduleRecord);
		return MSG_OK;
	}
}
