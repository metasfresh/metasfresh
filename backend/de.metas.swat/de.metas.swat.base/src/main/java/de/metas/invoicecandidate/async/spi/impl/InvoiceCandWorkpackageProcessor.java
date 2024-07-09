package de.metas.invoicecandidate.async.spi.impl;

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

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerService;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.api.impl.InvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesChangesChecker;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.lock.api.ILock;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * Generate {@link I_C_Invoice}s for given {@link I_C_Invoice_Candidate}s.
 */
public class InvoiceCandWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	// services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceCandUpdateSchedulerService invoiceCandUpdateSchedulerService = Services.get(IInvoiceCandUpdateSchedulerService.class);
	private final transient IWorkPackageBL workPackageBL = Services.get(IWorkPackageBL.class);
	private static final Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandWorkpackageProcessor.class);

	private final IInvoiceGenerateResult _result;

	/**
	 * @param result result to be used when processing
	 */
	public InvoiceCandWorkpackageProcessor(@NonNull final IInvoiceGenerateResult result)
	{
		_result = result;
	}

	public InvoiceCandWorkpackageProcessor()
	{
		this(Services.get(IInvoiceCandBL.class).createInvoiceGenerateResult(false));
	}

	/**
	 * Processes the given invoice candidates into invoices
	 */
	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// use the workpackge's ctx. It contains the client, org and user that created the queu-block this package belongs to
		final Properties localCtx = InterfaceWrapperHelper.getCtx(workPackage);

		final List<I_C_Invoice_Candidate> candidatesOfPackage = queueDAO.retrieveAllItems(workPackage, I_C_Invoice_Candidate.class);

		try (final IAutoCloseable ignored = invoiceCandBL.setUpdateProcessInProgress())
		{
			// At this line, we used to update invalid ICs, but that's not needed anymore,
			// because we made sure that this is done by the respective invoice-candidate-processor.

			// Generate invoices from them
			final UserNotificationsInvoiceGenerateResult createInvoiceResults = new UserNotificationsInvoiceGenerateResult(getInvoiceGenerateResult())
					.setNotificationRecipientUserId(UserId.ofRepoId(workPackage.getCreatedBy())); // Events shall be sent to workpackage creator
			invoiceCandBL.generateInvoices()
					.setContext(localCtx, localTrxName)
					.setCollector(createInvoiceResults)
					.setInvoicingParams(getInvoicingParams())
					.setIgnoreInvoiceSchedule(true) // we don't need to check for the invoice schedules because ICs that would be skipped here would already have been skipped on enqueue time.
					.generateInvoices(candidatesOfPackage.iterator());

			// Log invoices generation result
			final String createInvoiceResultsSummary = createInvoiceResults.getSummary(localCtx);
			logger.info(createInvoiceResultsSummary);
			Loggables.addLog(createInvoiceResultsSummary);

			// invalidate them all at once
			invoiceCandDAO.invalidateCands(candidatesOfPackage);
		}

		//
		// After invoices were generated, schedule another update invalid workpackage to update any remaining invoice candidates.
		// This is a workaround and we do that because our testers reported that randomly, when we do mass invoicing,
		// sometimes there are some invoice candidates invalidated.
		invoiceCandUpdateSchedulerService.scheduleForUpdate(InvoiceCandUpdateSchedulerRequest.of(localCtx, localTrxName));

		return Result.SUCCESS;
	}

	/**
	 * @return invoice generate result/collector; never returns null
	 */
	public final IInvoiceGenerateResult getInvoiceGenerateResult()
	{
		return _result;
	}

	@Override
	public String toString()
	{
		return String.format("InvoiceCandWorkpackageProcessor [result=%s, queueDAO=%s, invoiceCandBL=%s, invoiceCandDAO=%s, workPackageBL=%s]",
				_result, queueDAO, invoiceCandBL, invoiceCandDAO, workPackageBL);
	}

	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameters());
	}
}
