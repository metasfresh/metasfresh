package de.metas.invoicecandidate.async.spi.impl;

import ch.qos.logback.classic.Level;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.slf4j.Logger;

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
	private final ILockManager lockManager = Services.get(ILockManager.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, @NonNull final String trxName)
	{
		final int pinstanceInt = getParameters().getParameterAsInt(I_AD_PInstance.COLUMNNAME_AD_PInstance_ID, workpackage.getAD_PInstance_ID());
		Check.assume(pinstanceInt > 0, "pinstanceInt>=0 on workpackage={}", workpackage);

		final PInstanceId pinstanceId = PInstanceId.ofRepoId(pinstanceInt);

		Check.assume(workpackage.getC_Async_Batch_ID() > 0, "workpackage.getC_Async_Batch_ID() > 0 for workpackage=", workpackage);
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(workpackage.getC_Async_Batch_ID());

		final ILockCommand elementsLocker = lockManager.lock()
				.setOwner(LockOwner.newOwner(getClass().getSimpleName()))
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_C_Invoice_Candidate.class, pinstanceId);

		try (final ILockAutoCloseable lock = elementsLocker.acquire().asAutoCloseable())
		{
			processWorkPackage0(pinstanceId, asyncBatchId);
		}

		return Result.SUCCESS;
	}

	private void processWorkPackage0(@NonNull final PInstanceId pinstanceId, @NonNull final AsyncBatchId asyncBatchId)
	{
		final Iterator<I_C_Invoice> invoiceIterator = retrieveSelection(pinstanceId);

		while (invoiceIterator.hasNext())
		{
			final I_C_Invoice invoice = invoiceIterator.next();

			if (!canVoidPaidInvoice(invoice))
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("C_Invoice_ID={}: Skipping paid invoice because we can't void it.", invoice.getC_Invoice_ID());
				continue;
			}

			final Set<InvoiceCandidateId> invoiceCandIds = invoiceCandBL.voidAndReturnInvoiceCandIds(invoice);

			if (invoiceCandIds.isEmpty())
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("C_Invoice_ID={}: Skipping invoice that is not based on invoice candidates.", invoice.getC_Invoice_ID());
				continue;
			}

			invoiceCandIds.forEach(invoiceCandId -> invoiceCandBL.setAsyncBatch(invoiceCandId, asyncBatchId));

			final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchById(asyncBatchId);

			invoiceCandBL.enqueueForInvoicing()
					.setContext(getCtx())
					.setC_Async_Batch(asyncBatch)
					.setInvoicingParams(getIInvoicingParams())
					.setFailIfNothingEnqueued(true)
					.enqueueInvoiceCandidateIds(invoiceCandIds);
		}
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

	private boolean canVoidPaidInvoice(@NonNull final I_C_Invoice invoice)
	{
		final I_C_Invoice inv = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);

		final List<I_C_Payment> availablePayments = allocationDAO.retrieveInvoicePayments(inv);

		// if there is no payment and is paid (can be payed with credit memo, but no real payment), we can not void
		return !(invoice.isPaid() && availablePayments.isEmpty());
	}

	@NonNull
	private IInvoicingParams getIInvoicingParams()
	{
		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setUpdateLocationAndContactForInvoice(true);
		invoicingParams.setIgnoreInvoiceSchedule(false);
		invoicingParams.setSupplementMissingPaymentTermIds(true);

		return invoicingParams;
	}
}
