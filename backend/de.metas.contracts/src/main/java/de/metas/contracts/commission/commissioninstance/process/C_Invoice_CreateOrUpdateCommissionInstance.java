package de.metas.contracts.commission.commissioninstance.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import ch.qos.logback.classic.Level;
import de.metas.contracts.commission.commissioninstance.interceptor.C_InvoiceFacadeService;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class C_Invoice_CreateOrUpdateCommissionInstance
		extends JavaProcess
		implements IProcessPrecondition
{
	private final C_InvoiceFacadeService invoiceFacadeService = SpringContextHolder.instance.getBean(C_InvoiceFacadeService.class);

	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final Logger logger = LogManager.getLogger(C_Invoice_CreateOrUpdateCommissionInstance.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx // each invoice candidate is processed in its own transaction
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Invoice> selectedICsFilter = getProcessInfo().getQueryFilterOrElseFalse();
		logger.debug("selectedICsFilter={}", selectedICsFilter);

		Loggables.withLogger(logger, Level.DEBUG).addLog("Processing sales order InvoiceCandidates");
		final Iterator<InvoiceId> invoiceIds = createInvoiceIdIterator(selectedICsFilter);
		final Result result = processInvoices(invoiceIds);
		Loggables.withLogger(logger, Level.DEBUG).addLog("Processed {} InvoiceCandidates; anyException={}", result.getCounter(), result.isAnyException());

		return MSG_OK;
	}

	private Iterator<InvoiceId> createInvoiceIdIterator(@NonNull final IQueryFilter<I_C_Invoice> selectedInvoicesFilter)
	{
	final IQueryBuilder<I_C_Invoice> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.filter(selectedInvoicesFilter);

		final Iterator<InvoiceId> invoiceIds = queryBuilder
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterateIds(InvoiceId::ofRepoId);
		return invoiceIds;
	}

	private Result processInvoices(@NonNull final Iterator<InvoiceId> invoiceIds)
	{
		int counter = 0;
		boolean anyException = false;
		while (invoiceIds.hasNext())
		{
			final InvoiceId invoiceId = invoiceIds.next();
			try (final MDCCloseable invoiceCandidateIdMDC = TableRecordMDC.putTableRecordReference(I_C_Invoice.Table_Name, invoiceId))
			{
				trxManager.runInNewTrx(() -> {
					logger.debug("Processing invoiceCandidate");

					final I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);
					invoiceFacadeService.syncInvoiceToCommissionInstance(invoiceRecord);
				});
				counter++;
			}
			catch (final RuntimeException e)
			{
				anyException = true;
				final AdIssueId adIssueId = errorManager.createIssue(e);
				Loggables.withLogger(logger, Level.DEBUG)
						.addLog("C_Invoice_ID={}: Caught {} and created AD_Issue_ID={}; exception-message={}",
								invoiceId.getRepoId(), e.getClass(), adIssueId.getRepoId(), e.getLocalizedMessage());
			}
		}
		return new Result(counter, anyException);
	}

	@Value
	private static class Result
	{
		int counter;
		boolean anyException;
	}
}
