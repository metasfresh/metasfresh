/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.invoice.process;

import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceSchedule;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Set values to the selected C_InvoiceSchedule.
 * We don't want to allow this via UI because there is a MI that invalidates affected invoice-candidates, which might take some time and make the UI seem to hang.
 */
public class C_InvoiceSchedule_CreateOrUpdate extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_Name, mandatory = true)
	private String name;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceDistance, mandatory = true)
	private BigDecimal invoiceDistance;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency, mandatory = true)
	private String invoiceFrequency;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceDay)
	private BigDecimal invoiceDay;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_InvoiceWeekDay)
	private String invoiceWeekDay;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_IsAmount, mandatory = true)
	private boolean isAmount;

	@Param(parameterName = I_C_InvoiceSchedule.COLUMNNAME_Amt)
	private BigDecimal amt;

	private boolean isNewRecord = false;
	
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty() || getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.accept();
		}
		return ProcessPreconditionsResolution.rejectWithInternalReason("There has to be zero or one record selected.");
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord;
		if (getProcessInfo().getRecord_ID() <= 0)
		{
			invoiceScheduleRecord = InterfaceWrapperHelper.newInstance(I_C_InvoiceSchedule.class);
			isNewRecord = true;
		}
		else
		{
			invoiceScheduleRecord = getProcessInfo().getRecord(I_C_InvoiceSchedule.class);
		}

		if (Check.isNotBlank(name))
		{
			invoiceScheduleRecord.setName(StringUtils.trim(name));
		}
		invoiceScheduleRecord.setInvoiceDistance(invoiceDistance.intValue());
		invoiceScheduleRecord.setInvoiceFrequency(invoiceFrequency);
		invoiceScheduleRecord.setInvoiceDay(Math.min(31, Math.max(1, invoiceDay.intValue()))); // enforce a value between 1 and 31
		invoiceScheduleRecord.setInvoiceWeekDay(invoiceWeekDay);
		invoiceScheduleRecord.setIsAmount(isAmount);
		invoiceScheduleRecord.setAmt(amt);

		InterfaceWrapperHelper.saveRecord(invoiceScheduleRecord);
		return MSG_OK;
	}

	/**
	 * I also defined the values in AD_ProcessParam.DefaultLogic, but the expressions weren't used.
	 */
	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (getProcessInfo().getRecord_ID() <= 0)
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}

		final I_C_InvoiceSchedule invoiceScheduleRecord = getProcessInfo().getRecord(I_C_InvoiceSchedule.class);
		if (I_C_InvoiceSchedule.COLUMNNAME_Name.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getName();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_InvoiceDistance.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getInvoiceDistance();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getInvoiceFrequency();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_InvoiceDay.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getInvoiceDay();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_InvoiceWeekDay.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getInvoiceWeekDay();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_IsAmount.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.isAmount();
		}
		else if (I_C_InvoiceSchedule.COLUMNNAME_Amt.equals(parameter.getColumnName()))
		{
			return invoiceScheduleRecord.getAmt();
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	/**
	 * Needed because otherwise we need a cache-reset in case a new record was added.
	 */
	@Override
	protected final void postProcess(final boolean success)
	{
		if (success && isNewRecord)
		{
			getView().invalidateAll();
		}
	}
}
