/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.common.util.time.SystemTime;
import de.metas.invoice.InvoiceVerificationRunId;
import de.metas.invoice.service.IInvoiceVerificationBL;
import de.metas.invoice.service.InvoiceVerificationRunStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Verification_Run;

public class C_Invoice_Verification_Run_Execute extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInvoiceVerificationBL invoiceVerificationDAO = Services.get(IInvoiceVerificationBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		else if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		else if (!isValidVerificationRequest(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already in progress/finished.");
		}
		return ProcessPreconditionsResolution.accept();
	}

	private boolean isValidVerificationRequest(final @NonNull IProcessPreconditionsContext context)
	{
		final InvoiceVerificationRunStatus currentStatus = getInvoiceVerificationRunStatus(context);
		return InvoiceVerificationRunStatus.Planned.equals(currentStatus);
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Invoice_Verification_Run run = getInvoiceVerificationRun();
		setAsRunning(run);
		final InvoiceVerificationRunId invoiceVerificationRunId = InvoiceVerificationRunId.ofRepoId(run.getC_Invoice_Verification_Set_ID(), run.getC_Invoice_Verification_Run_ID());

		invoiceVerificationDAO.createVerificationRunLines(invoiceVerificationRunId);
		setCompleted(run);
		return MSG_OK;
	}

	private void setAsRunning(final I_C_Invoice_Verification_Run run)
	{
		run.setStatus(InvoiceVerificationRunStatus.Running.getCode());
		run.setDateStart(SystemTime.asTimestamp());
		run.setAD_PInstance_ID(getPinstanceId().getRepoId());
		InterfaceWrapperHelper.save(run);
	}

	private void setCompleted(final I_C_Invoice_Verification_Run run)
	{
		run.setStatus(InvoiceVerificationRunStatus.Finished.getCode());
		run.setDateEnd(SystemTime.asTimestamp());
		InterfaceWrapperHelper.save(run);
	}

	private InvoiceVerificationRunStatus getInvoiceVerificationRunStatus(final @NonNull IProcessPreconditionsContext context)
	{
		final I_C_Invoice_Verification_Run run = Check.assumeNotNull(queryBL.createQueryBuilder(I_C_Invoice_Verification_Run.class)
				.addOnlyActiveRecordsFilter()
				.filter(context.getQueryFilter(I_C_Invoice_Verification_Run.class))
				.create().firstOnly(I_C_Invoice_Verification_Run.class), "C_Invoice_Verification_Run with ID {} not found", context.getSingleSelectedRecordId());
		return Check.isBlank(run.getStatus()) ? InvoiceVerificationRunStatus.Planned : InvoiceVerificationRunStatus.ofNullableCode(run.getStatus());
	}

	@NonNull
	private I_C_Invoice_Verification_Run getInvoiceVerificationRun()
	{
		return Check.assumeNotNull(queryBL.createQueryBuilder(I_C_Invoice_Verification_Run.class)
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilterOrElseFalse())
				.create().firstOnly(I_C_Invoice_Verification_Run.class), "C_Invoice_Verification_Run with ID {} not found", getProcessInfo().getRecord_ID());
	}

}
