package de.metas.invoicecandidate.async.spi.impl;

import ch.qos.logback.classic.Level;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.contracts.ICommissionTriggerService;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.compiere.util.Env.getCtx;

public class RecreateInvoiceWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(RecreateInvoiceWorkpackageProcessor.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ICommissionTriggerService commissionTriggerService = SpringContextHolder.instance.getBean(ICommissionTriggerService.class);

	private final static AdMessageKey MSG_SKIPPED_INVOICE_NON_PAYMENT_ALLOC_INVOLVED = AdMessageKey.of("MSG_SKIPPED_INVOICE_NON_PAYMENT_ALLOC_INVOLVED");
	private static final AdMessageKey MSG_SKIPPED_INVOICE_DUE_TO_COMMISSION = AdMessageKey.of("MSG_SKIPPED_INVOICE_DUE_TO_COMMISSION");

	@Override
	public boolean isRunInTransaction() {return false;}

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @Nullable final String trxName_IGNORED)
	{
		final int pinstanceInt = getParameters().getParameterAsInt(I_AD_PInstance.COLUMNNAME_AD_PInstance_ID, workpackage.getAD_PInstance_ID());
		Check.assume(pinstanceInt > 0, "pinstanceInt>=0 on workpackage={}", workpackage);
		final PInstanceId pinstanceId = PInstanceId.ofRepoId(pinstanceInt);

		Check.assume(workpackage.getC_Async_Batch_ID() > 0, "workpackage.getC_Async_Batch_ID() > 0 for workpackage=", workpackage);
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(workpackage.getC_Async_Batch_ID());
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

		final ILockCommand elementsLocker = lockManager.lock()
				.setOwner(LockOwner.newOwner(getClass().getSimpleName()))
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_C_Invoice_Candidate.class, pinstanceId);

		try (final ILockAutoCloseable ignored = elementsLocker.acquire().asAutoCloseable())
		{
			processWorkPackage0(pinstanceId, asyncBatch);
		}

		return Result.SUCCESS;
	}

	private void processWorkPackage0(@NonNull final PInstanceId pinstanceId, @NonNull final I_C_Async_Batch asyncBatch)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final Iterator<I_C_Invoice> invoiceIterator = retrieveSelection(pinstanceId);
		while (invoiceIterator.hasNext())
		{
			final I_C_Invoice invoice = invoiceIterator.next();

			if (!canReversePaidInvoice(invoice))
			{
				loggable.addLog("C_Invoice_ID={}: Skipping paid invoice because we can't reverse it.", invoice.getC_Invoice_ID());
				continue;
			}

			final Set<InvoiceCandidateId> invoiceCandIds = reverseAndReturnInvoiceCandIds(invoice);
			loggable.addLog("C_Invoice_ID={}: reversal done",invoice.getC_Invoice_ID());
			if (invoiceCandIds.isEmpty())
			{
				loggable.addLog("C_Invoice_ID={}: Skipping invoice from re-invoicing because it is not based on invoice candidates.", invoice.getC_Invoice_ID());
				continue;
			}

			loggable.addLog("C_Invoice_ID={}: Start enqueueing invoice candidates for re-invoicing", invoice.getC_Invoice_ID());
			enqueueForInvoicing(invoiceCandIds, asyncBatch);
			loggable.addLog("C_Invoice_ID={}: Done enqueueing invoice candidates for re-invoicing", invoice.getC_Invoice_ID());
		}
	}

	private Set<InvoiceCandidateId> reverseAndReturnInvoiceCandIds(final I_C_Invoice invoice)
	{
		// NOTE: we have to separate voidAndReturnInvoiceCandIds and enqueueForInvoicing in 2 transactions because
		// InvoiceCandidateEnqueuer is calling updateSelectionBeforeEnqueueing in a new transaction (for some reason?!?)
		// and that is introducing a deadlock in case we are also changing C_Invoice_Candidate table here.
		trxManager.assertThreadInheritedTrxNotExists();

		return trxManager.callInNewTrx(() -> invoiceCandBL.reverseAndReturnInvoiceCandIds(invoice));
	}

	private void enqueueForInvoicing(final Set<InvoiceCandidateId> invoiceCandIds, final @NonNull I_C_Async_Batch asyncBatch)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setC_Async_Batch(asyncBatch)
				.setInvoicingParams(getIInvoicingParams())
				.setFailIfNothingEnqueued(true)
				.enqueueInvoiceCandidateIds(invoiceCandIds);
	}

	@NonNull
	private Iterator<I_C_Invoice> retrieveSelection(@NonNull final PInstanceId pinstanceId)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterate(I_C_Invoice.class);
	}

	private boolean canReversePaidInvoice(@NonNull final I_C_Invoice invoice)
	{
		final I_C_Invoice inv = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);

		if (hasAnyNonPaymentAllocations(inv))
		{
			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(MSG_SKIPPED_INVOICE_NON_PAYMENT_ALLOC_INVOLVED)
					.contentADMessageParam(inv.getDocumentNo())
					.recipientUserId(Env.getLoggedUserId())
					.build();
			notificationBL.send(userNotificationRequest);

			return false;
		}

		if (commissionTriggerService.isContainsCommissionTriggers(InvoiceId.ofRepoId(invoice.getC_Invoice_ID())))
		{
			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(MSG_SKIPPED_INVOICE_DUE_TO_COMMISSION)
					.contentADMessageParam(inv.getDocumentNo())
					.recipientUserId(Env.getLoggedUserId())
					.build();
			notificationBL.send(userNotificationRequest);

			return false;
		}

		return true;
	}

	@NonNull
	private InvoicingParams getIInvoicingParams()
	{
		return InvoicingParams.builder()
				.updateLocationAndContactForInvoice(true)
				.ignoreInvoiceSchedule(false)
				.build();
	}

	private boolean hasAnyNonPaymentAllocations(@NonNull final I_C_Invoice invoice)
	{
		final List<I_C_AllocationLine> availableAllocationLines = allocationDAO.retrieveAllocationLines(invoice);

		return availableAllocationLines.stream()
				.anyMatch(allocationLine -> allocationLine.getC_Payment_ID() <= 0);
	}
}
