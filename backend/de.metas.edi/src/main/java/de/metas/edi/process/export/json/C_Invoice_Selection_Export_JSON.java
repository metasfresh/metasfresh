/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.process.export.json;

import de.metas.edi.model.I_C_Invoice;
import de.metas.error.AdIssueId;
import de.metas.error.impl.ErrorManager;
import de.metas.invoice.InvoiceId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import java.util.stream.Stream;

import static de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON.PARAM_C_INVOICE_ID;

/**
 * Gets the selected invoices that are completed and EDI-enabled, and invokes {@link C_Invoice_EDI_Export_JSON} for each of them.
 */
public class C_Invoice_Selection_Export_JSON extends JavaProcess implements IProcessPrecondition
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ErrorManager errorManager = Services.get(ErrorManager.class);

	private int countExported;
	private int countErrors;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt() throws Exception
	{
		countExported = 0;
		countErrors = 0;
		retrieveValidSelectedDocuments().forEach(this::performNestedProcessInvocation);

		return countExported > 0 && countErrors == 0 ? MSG_OK : MSG_Error;
	}

	private void performNestedProcessInvocation(@NonNull final InvoiceId invoiceId)
	{
		final StringBuilder logMessage = new StringBuilder("Export C_Invoice_ID={} ==>").append(invoiceId.getRepoId());
		try
		{
			final ProcessExecutor processExecutor = ProcessInfo
					.builder()
					.setProcessCalledFrom(getProcessInfo().getProcessCalledFrom())
					.setAD_ProcessByValue(C_Invoice_EDI_Export_JSON.class.getName())
					.addParameter(PARAM_C_INVOICE_ID, invoiceId.getRepoId())
					.buildAndPrepareExecution()
					.executeSync();

			final ProcessExecutionResult result = processExecutor.getResult();
			final String summary = result.getSummary();

			if (MSG_OK.equals(summary))
				countExported++;
			else
				countErrors++;

			logMessage.append("AD_PInstance_ID=").append(PInstanceId.toRepoId(processExecutor.getProcessInfo().getPinstanceId()))
					.append("; Summary=").append(summary);

		}
		catch (final Exception e)
		{
			final AdIssueId issueId = errorManager.createIssue(e);
			logMessage
					.append("Failed with AD_Issue_ID=").append(issueId.getRepoId())
					.append("; Exception: ").append(e.getMessage());
			countErrors++;
		}
		finally
		{
			addLog(logMessage.toString());
		}
	}

	@NonNull
	private Stream<InvoiceId> retrieveValidSelectedDocuments()
	{
		return createSelectedInvoicesQueryBuilder()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_C_Invoice.COLUMNNAME_IsSOTrx, true)
				.addEqualsFilter(org.compiere.model.I_C_Invoice.COLUMNNAME_DocStatus, I_C_Invoice.DOCSTATUS_Completed)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_IsEdiEnabled, true)
				.create()
				.iterateAndStreamIds(InvoiceId::ofRepoId);
	}

	protected IQueryBuilder<I_C_Invoice> createSelectedInvoicesQueryBuilder()
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.filter(getProcessInfo().getQueryFilterOrElseFalse());
	}
}
