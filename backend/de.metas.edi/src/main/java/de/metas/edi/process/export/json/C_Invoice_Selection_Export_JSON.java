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

import de.metas.document.engine.DocStatus;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
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

import javax.annotation.Nullable;
import java.util.stream.Stream;

import static de.metas.edi.process.export.json.C_Invoice_EDI_Export_JSON.PARAM_C_INVOICE_ID;

/**
 * Gets the selected invoices that are completed and EDI-enabled, and invokes {@link C_Invoice_EDI_Export_JSON} for each of them.
 */
public class C_Invoice_Selection_Export_JSON extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey errorMsg = AdMessageKey.of("de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON_ProcessingError");
	private static final AdMessageKey notReadyInfo = AdMessageKey.of("de.metas.edi.process.export.json.C_Invoice_Selection_Export_JSON_InvoiceNotReady");

	protected final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

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
		if (context.isSingleSelection())
		{
			final InvoiceId invoiceId = extractSingleSelectedInvoiceId(context);
			if (invoiceId == null)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("No invoice selected");
			}
			final I_C_Invoice invoiceRecord = invoiceDAO.getByIdOutOfTrx(invoiceId, I_C_Invoice.class);
			final boolean singleInvoiceCanBeEdiSent = DocStatus.ofCode(invoiceRecord.getDocStatus()).isCompleted()
					&& invoiceRecord.isEdiEnabled()
					&& EDIExportStatus.ofCode(invoiceRecord.getEDI_ExportStatus()).isPending();
			if (!singleInvoiceCanBeEdiSent)
			{
				return ProcessPreconditionsResolution.reject(notReadyInfo);
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	protected InvoiceId extractSingleSelectedInvoiceId(final @NonNull IProcessPreconditionsContext context)
	{
		return InvoiceId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
	}

	@Override
	protected final String doIt() throws Exception
	{
		countExported = 0;
		countErrors = 0;
		retrieveValidSelectedDocuments().forEach(this::performNestedProcessInvocation);

		if (countExported > 0 && countErrors == 0)
		{
			return MSG_OK;
		}
		return MSG_Error + Services.get(IMsgBL.class).getMsg(getCtx(), errorMsg, new Object[] { countExported, countErrors });
	}

	private void performNestedProcessInvocation(@NonNull final InvoiceId invoiceId)
	{
		final StringBuilder logMessage = new StringBuilder("Export C_Invoice_ID={} ==>").append(invoiceId.getRepoId());
		try
		{
			final ProcessExecutor processExecutor = ProcessInfo
					.builder()
					.setProcessCalledFrom(getProcessInfo().getProcessCalledFrom())
					.setAD_ProcessByClassname(C_Invoice_EDI_Export_JSON.class.getName())
					.addParameter(PARAM_C_INVOICE_ID, invoiceId.getRepoId())
					.setRecord(I_C_Invoice.Table_Name, invoiceId.getRepoId()) // will be stored in the AD_Pinstance
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
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_EDI_ExportStatus, I_EDI_Document.EDI_EXPORTSTATUS_Pending)
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
