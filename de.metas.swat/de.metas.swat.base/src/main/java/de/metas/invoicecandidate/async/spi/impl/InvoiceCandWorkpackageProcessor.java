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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerService;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.api.impl.InvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesChangesChecker;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;

/**
 * Generate {@link I_C_Invoice}s for given {@link I_C_Invoice_Candidate}s.
 *
 * @author tsa
 *
 */
public class InvoiceCandWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	// services
	private final transient IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IWorkPackageBL workPackageBL = Services.get(IWorkPackageBL.class);
	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandWorkpackageProcessor.class);

	private final IInvoiceGenerateResult _result;
	private InvoicingParams _invoicingParams = null; // lazy loaded

	/**
	 *
	 * @param result result to be used when processing
	 */
	public InvoiceCandWorkpackageProcessor(final IInvoiceGenerateResult result)
	{
		super();
		Check.assumeNotNull(result, "result not null");
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
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// use the workpackge's ctx. It contains the client, org and user that created the queu-block this package belongs to
		final Properties localCtx = InterfaceWrapperHelper.getCtx(workPackage);

		final List<I_C_Invoice_Candidate> candidatesOfPackage = queueDAO.retrieveItems(workPackage, I_C_Invoice_Candidate.class, localTrxName);

		try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
		{
			// Validate all invoice candidates
			updateInvalid(localCtx, candidatesOfPackage, localTrxName);

			// Generate invoices from them
			final EventRecorderInvoiceGenerateResult createInvoiceResults = new EventRecorderInvoiceGenerateResult(getInvoiceGenerateResult())
					.setEventRecipientUserId(workPackage.getCreatedBy()); // Events shall be sent to workpackage creator
			invoiceCandBL.generateInvoices()
					.setContext(localCtx, localTrxName)
					.setLoggable(Loggables.get())
					.setCollector(createInvoiceResults)
					.setInvoicingParams(getInvoicingParams())
					.setIgnoreInvoiceSchedule(true) // we don't need to check for the invoice schedules because ICs where would be skipped here would already be skipped on enqueue time.
					.generateInvoices(candidatesOfPackage.iterator());

			// Log invoices generation result
			final String createInvoiceResultsSummary = createInvoiceResults.getSummary(localCtx);
			logger.info(createInvoiceResultsSummary);
			Loggables.get().addLog(createInvoiceResultsSummary);

			// invalidate them all at once
			invoiceCandDAO.invalidateCands(candidatesOfPackage, localTrxName);
		}

		//
		// After invoices were generated, schedule another update invalid workpackage to update any remaining invoice candidates.
		// This is a workaround and we do that because our testers reported that randomly, when we do mass invoicing,
		// sometimes there are some invoice candidates invalidated.
		Services.get(IInvoiceCandUpdateSchedulerService.class)
				.scheduleForUpdate(InvoiceCandUpdateSchedulerRequest.of(localCtx, localTrxName));

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

	private final IInvoicingParams getInvoicingParams()
	{
		if (_invoicingParams == null)
		{
			_invoicingParams = new InvoicingParams(getParameters());
		}
		return _invoicingParams;
	}

	private void updateInvalid(final Properties localCtx, final List<I_C_Invoice_Candidate> candidates, final String localTrxName)
	{
		// If there are no invoice candidates, there nothing we can do
		if (candidates.isEmpty())
		{
			return;
		}

		// Invoice candidates changes tracker: tracks if sensitive informations (i.e. amount)
		// from invoice candidates were changed after validation.
		// If that's the case, an exception shall be thrown.
		final InvoiceCandidatesChangesChecker icChangesChecker = new InvoiceCandidatesChangesChecker()
				.setLogger(Loggables.get())
				.setBeforeChanges(candidates);

		//
		// Validate all invoice candidates
		final Optional<ILock> elementsLock = getElementsLock();
		invoiceCandBL.updateInvalid()
				.setContext(localCtx, localTrxName)
				.setLockedBy(elementsLock.orNull())
				.setTaggedWithAnyTag()
				.setOnlyC_Invoice_Candidates(candidates)
				.update();

		// Make sure the invoice candidate is fresh before we actually use it (task 06162, also see javadoc of updateInvalid method)
		InterfaceWrapperHelper.refreshAll(candidates);

		//
		// If any of the enqueued invoice candidates are flagged "ToRecompute" we cannot generate the invoice now,
		// but we will postpone the workpackage processing
		for (final I_C_Invoice_Candidate ic : candidates)
		{
			if (invoiceCandDAO.isToRecompute(ic))
			{
				// at least one candidate is currently not valid. Retry later.
				throw WorkpackageSkipRequestException.create("Candidate " + ic + " is currently not valid (maybe others too). Retry later");
			}
		}

		//
		// Make sure no sensitive informations were changed
		icChangesChecker.assertNoChanges(candidates);
	}
}
