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

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoicecandidate.InvoiceCandidateLockingUtil;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandUpdateSchedulerService;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.api.impl.InvoiceCandUpdateSchedulerRequest;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesChangesChecker;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
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
	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandWorkpackageProcessor.class);

	private final IInvoiceGenerateResult _result;
	private InvoicingParams _invoicingParams = null; // lazy loaded

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
			// Validate all invoice candidates
			updateInvalid(localCtx, candidatesOfPackage, localTrxName);

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
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(getC_Queue_WorkPackage().getC_Async_Batch_ID());

		invoiceCandUpdateSchedulerService.scheduleForUpdate(InvoiceCandUpdateSchedulerRequest.of(localCtx, localTrxName, asyncBatchId));

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

	private IInvoicingParams getInvoicingParams()
	{
		if (_invoicingParams == null)
		{
			_invoicingParams = new InvoicingParams(getParameters());
		}
		return _invoicingParams;
	}

	private void updateInvalid(
			@NonNull final Properties localCtx,
			@NonNull final List<I_C_Invoice_Candidate> candidates,
			@Nullable final String localTrxName)
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
				.setBeforeChanges(candidates);

		// Validate all invoice candidates
		final ILock elementsLock = getOrReCreateElementsLock(candidates);
		invoiceCandBL.updateInvalid()
				.setContext(localCtx, localTrxName)
				.setLockedBy(elementsLock)
				.setTaggedWithAnyTag()
				.setOnlyInvoiceCandidateIds(InvoiceCandidateIdsSelection.extractFixedIdsSet(candidates))
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

	private ILock getOrReCreateElementsLock(@NonNull final List<I_C_Invoice_Candidate> invoiceCandidateRecords)
	{
		final Optional<ILock> elementsLock = getElementsLock();
		if (elementsLock.isPresent())
		{
			return elementsLock.get();
		}

		// this happens if processing this workpackage failed once in the past.
		// because in that case, the framework unlocked all workpackage elements
		Loggables.addLog("The lock specified in the package parameter is gone! Trying to obtain a new lock");

		final String uniqueLockOwnerSuffix = I_C_Queue_WorkPackage.Table_Name + "_" + getC_Queue_WorkPackage().getC_Queue_WorkPackage_ID();

		final ILock lock = InvoiceCandidateLockingUtil.lockInvoiceCandidates(invoiceCandidateRecords, uniqueLockOwnerSuffix);

		// update the parameter; it is used to release the lock we just obtained when the workpackage was processed
		final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
		workpackageParamDAO.setParameterValue(
				getC_Queue_WorkPackage(),
				PARAMETERNAME_ElementsLockOwner,
				lock.getOwner().getOwnerName());
		final IParams parameters = workpackageParamDAO.retrieveWorkpackageParams(getC_Queue_WorkPackage());
		setParameters(parameters);

		Loggables.addLog("Obtained new lock with ownerName={} and updated our package parameter", lock.getOwner().getOwnerName());
		return lock;
	}
}
