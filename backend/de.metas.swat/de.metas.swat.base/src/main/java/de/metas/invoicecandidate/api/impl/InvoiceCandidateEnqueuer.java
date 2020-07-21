package de.metas.invoicecandidate.api.impl;

import static de.metas.common.util.CoalesceUtil.coalesce;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.base.Joiner;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.impl.ConstantWorkpackagePrio;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceCandidateLockingUtil;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoiceCandidatesChangesChecker;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.logging.TableRecordMDC;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/**
 *
 *
 * TODO there is duplicated code from <code>de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer</code>.
 * Please deduplicate it when there is time.
 * My favorite solution would be to create a "locking item-chump-processor" to do all the magic.
 *
 */
/* package */class InvoiceCandidateEnqueuer implements IInvoiceCandidateEnqueuer
{
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_QTY_TO_INVOICE = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_QtyToInvoice");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_APPROVAL = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_ApprovalForInvoicing");
	private static final AdMessageKey MSG_IncompleteGroupsFound_1P = AdMessageKey.of("InvoiceCandEnqueuer_IncompleteGroupsFound");

	// services
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	// Parameters
	private Properties _ctx = Env.getCtx();

	private boolean _failIfNothingEnqueued;
	private Boolean _failOnChanges = null;
	private boolean _failOnInvoiceCandidateError = false; // "false" for backward compatibility
	private BigDecimal _totalNetAmtToInvoiceChecksum;
	private IInvoicingParams _invoicingParams;
	private I_C_Async_Batch _asyncBatch = null;
	private IWorkpackagePrioStrategy _priority = null;

	private boolean setWorkpackageADPInstanceCreatorId = true;

	@Override
	public IInvoiceCandidateEnqueueResult enqueueInvoiceCandidateIds(final Set<InvoiceCandidateId> invoiceCandidateIds)
	{
		Check.assumeNotEmpty(invoiceCandidateIds, "invoiceCandidateIds is not empty");

		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, ITrx.TRXNAME_None);

		setWorkpackageADPInstanceCreatorId = false;
		_failOnInvoiceCandidateError = true;

		return enqueueSelection(invoiceCandidatesSelectionId);
	}

	@Override
	public IInvoiceCandidateEnqueueResult enqueueSelection(@NonNull final PInstanceId pinstanceId)
	{
		return trxManager.callInThreadInheritedTrx(() -> lockAndEnqueueSelection(pinstanceId));
	}

	private final IInvoiceCandidateEnqueueResult lockAndEnqueueSelection(@NonNull final PInstanceId pinstanceId)
	{
		final ILock icLock = InvoiceCandidateLockingUtil.lockInvoiceCandidatesForSelection(pinstanceId);
		try (final ILockAutoCloseable l = icLock.asAutocloseableOnTrxClose(ITrx.TRXNAME_ThreadInherited))
		{
			return enqueueSelectionInTrx(icLock, pinstanceId);
		}
	}

	private final IInvoiceCandidateEnqueueResult enqueueSelectionInTrx(
			@NonNull final ILock icLock,
			@NonNull final PInstanceId pinstanceId)
	{
		trxManager.assertThreadInheritedTrxExists();

		final Iterable<I_C_Invoice_Candidate> invoiceCandidates = retrieveSelection(pinstanceId);

		//
		// Create invoice candidates changes checker.
		final IInvoiceCandidatesChangesChecker icChangesChecker = newInvoiceCandidatesChangesChecker();
		icChangesChecker.setBeforeChanges(invoiceCandidates);

		//
		// Prepare
		prepareSelectionForEnqueueing(pinstanceId);
		// NOTE: after running that method we expect some invoice candidates to be invalidated, but that's not a problem because:
		// * the ones which are in our selection, we will updated right now (see below)
		// * the other ones will be updated later, asynchronously

		//
		// Updating invalid candidates to make sure that they e.g. have the correct header aggregation key and thus the correct ordering
		// also, we need to make sure that each ICs was updated at least once, so that it has a QtyToInvoice > 0 (task 08343)
		invoiceCandBL.updateInvalid()
				.setContext(getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setLockedBy(icLock)
				.setTaggedWithAnyTag()
				.setOnlyC_Invoice_Candidates(invoiceCandidates)
				.update();

		//
		// Make sure there are no changes in amounts or relevant fields (if that is required)
		icChangesChecker.assertNoChanges(invoiceCandidates);

		//
		// Create workpackages.
		// NOTE: loading them again after we made sure that they are fairly up to date.
		final InvoiceCandidate2WorkpackageAggregator workpackageAggregator = new InvoiceCandidate2WorkpackageAggregator(getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setInvoiceCandidatesLock(icLock)
				.setInvoicingParams(getInvoicingParams())
				.setC_Async_Batch(_asyncBatch);

		if (setWorkpackageADPInstanceCreatorId)
		{
			workpackageAggregator.setAD_PInstance_Creator_ID(pinstanceId);
		}

		final int workpackageQueueSizeBeforeEnqueueing = workpackageAggregator.getQueueSize();
		int invoiceCandidateSelectionCount = 0; // how many eligible items were in given selection
		final ICNetAmtToInvoiceChecker totalNetAmtToInvoiceChecksum = new ICNetAmtToInvoiceChecker();

		for (final I_C_Invoice_Candidate icRecord : invoiceCandidates)
		{
			try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
			{
				// Fail if the invoice candidate has issues
				if (isFailOnInvoiceCandidateError() && icRecord.isError())
				{
					throw new AdempiereException(icRecord.getErrorMsg())
							.setParameter("invoiceCandidate", icRecord);
				}

				// Check if invoice candidate is eligible for enqueueing
				if (!isEligibleForEnqueueing(icRecord))
				{
					continue;
				}

				//
				// Add invoice candidate to workpackage
				workpackageAggregator.add(icRecord);

				//
				// 06283 : use the priority from the first invoice candidate of each group
				// NTH: use the max prio of all candidates of the group
				final IWorkpackagePrioStrategy priorityToUse;
				if (_priority == null)
				{
					if (!Check.isEmpty(icRecord.getPriority()))
					{
						priorityToUse = ConstantWorkpackagePrio.fromString(icRecord.getPriority());
					}
					else
					{
						priorityToUse = SizeBasedWorkpackagePrio.INSTANCE;// fallback to default
					}
				}
				else
				{
					priorityToUse = _priority;
				}

				workpackageAggregator.setPriority(priorityToUse);

				invoiceCandidateSelectionCount++; // increment AFTER validating that it was approved for invoicing etc
				totalNetAmtToInvoiceChecksum.add(icRecord);
			}
		}

		//
		// Make sure all workpackages are marked as ready for processing
		workpackageAggregator.closeAllGroups();

		//
		// If no workpackages were created, display error message that no selection was made (07666)
		if (isFailIfNothingEnqueued() && invoiceCandidateSelectionCount <= 0)
		{
			throw new AdempiereException("@" + MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P + "@");
		}

		//
		// Create an return the enqueuing result
		final IInvoiceCandidateEnqueueResult result = new InvoiceCandidateEnqueueResult(
				invoiceCandidateSelectionCount,
				workpackageAggregator.getGroupsCount(),
				workpackageQueueSizeBeforeEnqueueing,
				totalNetAmtToInvoiceChecksum.getValue(),
				icLock);

		return result;
	}

	/**
	 * @return true if invoice candidate is eligible for enqueueing
	 */
	private boolean isEligibleForEnqueueing(final I_C_Invoice_Candidate ic)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		//
		// 07666: If selected, only use the invoices flagged as approved for invoicing
		if (getInvoicingParams().isOnlyApprovedForInvoicing() && !ic.isApprovalForInvoicing())
		{
			final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_APPROVAL, new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.addLog(msg);
			return false;
		}

		//
		// Check other reasons no to enqueue this ic: Processed, IsError, DateToInvoice.
		// NOTE: having this line in the middle because we will display only one skip reason and SKIPPED_QTY_TO_INVOICE is usually less informative if the IC was already processed
		if (invoiceCandBL.isSkipCandidateFromInvoicing(ic, getInvoicingParams().isIgnoreInvoiceSchedule()))
		{
			// NOTE: we are not logging any reason because the method already logged the reason if any.
			return false;
		}

		//
		// task 04372: we will enqueue invoice candidates with QtyToInvoice = 0, *only if* they also have QtyOrdered = 0
		// task 08343: logic moved here from the where clause in C_Invoice_Candidate_EnqueueSelection
		if (ic.getQtyOrdered().signum() != 0 && ic.getQtyToInvoice().signum() == 0)
		{
			final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_QTY_TO_INVOICE, new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.addLog(msg);
			return false;
		}

		return true;
	}

	private final void prepareSelectionForEnqueueing(final PInstanceId selectionId)
	{
		//
		// Check incomplete compensation groups
		final Set<String> incompleteOrderDocumentNo = invoiceCandDAO.retrieveOrderDocumentNosForIncompleteGroupsFromSelection(selectionId);
		if (!incompleteOrderDocumentNo.isEmpty())
		{
			final String incompleteOrderDocumentNoStr = Joiner.on(", ").join(incompleteOrderDocumentNo);
			throw new AdempiereException(MSG_IncompleteGroupsFound_1P, new Object[] { incompleteOrderDocumentNoStr });
		}

		// Note: we set dateInvoiced and updateDateAcct *before* enqueuing, because they are always relevant for aggregation (no matter which aggregation rules we choose)
		// That means that they may cause ICs to end up in the same package or in different packages.
		// If nothing else, to change this after enqueuing would lead to trouble with IInvoiceCandidatesChangesChecker, if the change has effects of that aggregation.

		//
		// Updating candidates previous to enqueueing, if the parameter has been set (task 03905)
		// task 08628: always make sure that every IC has the *same* dateInvoiced. possible other dates that were previously set don't matter.
		// This is critical because we assume that dateInvoiced is *implicitly* part of the aggregation key, so different values would fail the invoicing
		final IInvoicingParams invoicingParams = getInvoicingParams();
		final LocalDate paramDateInvoiced = invoicingParams.getDateInvoiced();
		if (paramDateInvoiced != null)
		{
			invoiceCandDAO.updateDateInvoiced(paramDateInvoiced, selectionId);
		}

		//
		// Updating candidates previous to enqueueing, if the parameter has been set (task 08437)
		// task 08628: same as for dateInvoiced
		final LocalDate paramDateAcct = coalesce(invoicingParams.getDateAcct(), paramDateInvoiced);
		if (paramDateAcct != null)
		{
			invoiceCandDAO.updateDateAcct(paramDateAcct, selectionId);
		}

		//
		// Update POReference (task 07978)
		final String poReference = invoicingParams.getPOReference();
		if (!Check.isEmpty(poReference, true))
		{
			invoiceCandDAO.updatePOReference(poReference, selectionId);
		}

		// issue https://github.com/metasfresh/metasfresh/issues/3809
		if (invoicingParams.isSupplementMissingPaymentTermIds())
		{
			invoiceCandDAO.updateMissingPaymentTermIds(selectionId);
		}

		//
		// Flag those invoice candidates as approved, since user decided to invoice them.
		// Also, this will prevent changing the prices, net amounts etc while invoicing.
		invoiceCandDAO.updateApprovalForInvoicingToTrue(selectionId);
	}

	/** NOTE: we designed this method for the case of enqueuing a big number of invoice candidates. */
	private final Iterable<I_C_Invoice_Candidate> retrieveSelection(final PInstanceId pinstanceId)
	{
		return () -> {
			final Properties ctx = getCtx();
			trxManager.assertThreadInheritedTrxExists();
			return invoiceCandDAO.retrieveIcForSelection(ctx, pinstanceId, ITrx.TRXNAME_ThreadInherited);
		};
	}

	@Override
	public IInvoiceCandidateEnqueuer setContext(@NonNull final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	/**
	 * @return context; never returns null
	 */
	private final Properties getCtx()
	{
		return _ctx;
	}

	@Override
	public InvoiceCandidateEnqueuer setFailIfNothingEnqueued(final boolean failIfNothingEnqueued)
	{
		this._failIfNothingEnqueued = failIfNothingEnqueued;
		return this;
	}

	private final boolean isFailIfNothingEnqueued()
	{
		return _failIfNothingEnqueued;
	}

	private final boolean isFailOnInvoiceCandidateError()
	{
		return _failOnInvoiceCandidateError;
	}

	@Override
	public IInvoiceCandidateEnqueuer setInvoicingParams(IInvoicingParams invoicingParams)
	{
		this._invoicingParams = invoicingParams;
		return this;
	}

	private final IInvoicingParams getInvoicingParams()
	{
		Check.assumeNotNull(_invoicingParams, "invoicingParams not null");
		return _invoicingParams;
	}

	@Override
	public final InvoiceCandidateEnqueuer setFailOnChanges(final boolean failOnChanges)
	{
		this._failOnChanges = failOnChanges;
		return this;
	}

	private final boolean isFailOnChanges()
	{
		// Use the explicit setting if available
		if (_failOnChanges != null)
		{
			return _failOnChanges;
		}

		// Use the sysconfig setting
		return sysConfigBL.getBooleanValue(SYSCONFIG_FailOnChanges, DEFAULT_FailOnChanges);
	}

	private final IInvoiceCandidatesChangesChecker newInvoiceCandidatesChangesChecker()
	{
		if (isFailOnChanges())
		{
			return new InvoiceCandidatesChangesChecker()
					.setTotalNetAmtToInvoiceChecksum(_totalNetAmtToInvoiceChecksum);
		}
		else
		{
			return IInvoiceCandidatesChangesChecker.NULL;
		}
	}

	@Override
	public IInvoiceCandidateEnqueuer setTotalNetAmtToInvoiceChecksum(BigDecimal totalNetAmtToInvoiceChecksum)
	{
		this._totalNetAmtToInvoiceChecksum = totalNetAmtToInvoiceChecksum;
		return this;
	}

	@Override
	public IInvoiceCandidateEnqueuer setC_Async_Batch(final I_C_Async_Batch asyncBatch)
	{
		_asyncBatch = asyncBatch;
		return this;
	}

	@Override
	public IInvoiceCandidateEnqueuer setPriority(final IWorkpackagePrioStrategy priority)
	{
		_priority = priority;
		return this;
	}
}
