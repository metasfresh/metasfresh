package de.metas.invoicecandidate.api.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.NullLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoiceCandidatesChangesChecker;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;

/**
 *
 *
 * TODO there is duplicated code from <code>de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer</code>. Please deduplicate it when there is time. my favorite solution would be to
 * create a "locking item-chump-processor" to do all the magic.
 *
 */
/* package */class InvoiceCandidateEnqueuer implements IInvoiceCandidateEnqueuer
{
	@SuppressWarnings("unused")
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_QTY_TO_INVOICE = "InvoiceCandBL_Invoicing_Skipped_QtyToInvoice";
	@SuppressWarnings("unused")
	private static final String MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_APPROVAL = "InvoiceCandBL_Invoicing_Skipped_ApprovalForInvoicing";

	// services
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	// Parameters
	private Properties _ctx;
	private String _trxNameInitial;
	private String _trxName;
	private ILoggable _loggable = NullLoggable.instance;
	private boolean _failIfNothingEnqueued;
	private Boolean _failOnChanges = null;
	private BigDecimal _totalNetAmtToInvoiceChecksum;
	private IInvoicingParams _invoicingParams;

	@Override
	public IInvoiceCandidateEnqueueResult enqueueSelection(final int adPInstanceId)
	{
		final Mutable<IInvoiceCandidateEnqueueResult> resultRef = new Mutable<>();
		final String trxNameInitial = getTrxNameInitial();
		trxManager.run(trxNameInitial, new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				setTrxName(localTrxName);

				final ILock icLock = lockInvoiceCandidatesForSelection(adPInstanceId);
				try (final ILockAutoCloseable l = icLock.asAutocloseableOnTrxClose(localTrxName))
				{
					final IInvoiceCandidateEnqueueResult result = enqueueSelection0(icLock, adPInstanceId);
					resultRef.setValue(result);
				}
			}

			@Override
			public void doFinally()
			{
				setTrxName(null);
			}
		});

		return resultRef.getValue();
	}

	private final IInvoiceCandidateEnqueueResult enqueueSelection0(final ILock icLock, final int adPInstanceId)
	{
		Check.assumeNotNull(icLock, "icLock not null");
		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");
		final Properties ctx = getCtx();
		final String trxName = getTrxNameNotNull();

		final Iterable<I_C_Invoice_Candidate> invoiceCandidates = retrieveSelection(adPInstanceId);

		//
		// Create invoice candidates changes checker.
		final IInvoiceCandidatesChangesChecker icChangesChecker = newInvoiceCandidatesChangesChecker();
		icChangesChecker.setBeforeChanges(invoiceCandidates);

		//
		// Prepare
		prepareSelectionForEnqueueing(adPInstanceId);
		// NOTE: after running that method we expect some invoice candidates to be invalidated, but that's not a problem because:
		// * the ones which are in our selection, we will update right now (see below)
		// * the other ones will be updated later, asynchronously

		//
		// Updating invalid candidates to make sure that they e.g. have the correct header aggregation key and thus the correct ordering
		// also, we need to make sure that each ICs was updated at least once, so that it has a QtyToInvoice > 0 (task 08343)
		invoiceCandBL.updateInvalid()
				.setContext(getCtx(), getTrxNameNotNull())
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
		final InvoiceCandidate2WorkpackageAggregator workpackageAggregator = new InvoiceCandidate2WorkpackageAggregator(ctx, trxName)
				.setAD_PInstance_ID(adPInstanceId)
				.setInvoiceCandidatesLock(icLock);

		final int workpackageQueueSizeBeforeEnqueueing = workpackageAggregator.getQueueSize();
		int invoiceCandidateSelectionCount = 0; // how many eligible items were in given selection
		final ICNetAmtToInvoiceChecker totalNetAmtToInvoiceChecksum = new ICNetAmtToInvoiceChecker();

		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			// Check if invoice candidate is eligible for enqueueing
			if (!isEligibleForEnqueueing(ic))
			{
				continue;
			}

			//
			// Add invoice candidate to workpackage
			workpackageAggregator.add(ic);

			//
			// 07666: Set approval back to false after enqueuing and save within transaction
			try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
			{
				ic.setApprovalForInvoicing(false);
				InterfaceWrapperHelper.save(ic);
			}

			invoiceCandidateSelectionCount++; // increment AFTER validating that it was approved for invoicing etc
			totalNetAmtToInvoiceChecksum.add(ic);

			//
			// Do an intermediate commit every 100 enqueued ICs
			// this will also cause the previously finished (not the current one!) work package to be flagged as ready to be processed
// workaround: avoid the system to attempty slitting of the last WP two times which results in an exception
//			if (workpackageAggregator.getItemsCount() % 100 == 0)
//			{
//				workpackageAggregator.closeAllGroupExceptLastUsed();
//				trxManager.commit(trxName);
//			}
		}

		//
		// Make sure all workpackages are marked as ready for processing
		workpackageAggregator.closeAllGroups();

		//
		// If no workpackages were created, display error message that no selection was made (07666)
		if (isFailIfNothingEnqueued() && invoiceCandidateSelectionCount <= 0)
		{
			throw new AdempiereException(msgBL.getMsg(ctx, MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P));
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

	/** Lock all invoice candidates for selection and return an auto-closable lock. */
	private final ILock lockInvoiceCandidatesForSelection(final int adPInstanceId)
	{
		final LockOwner lockOwner = LockOwner.newOwner("ICEnqueuer", adPInstanceId);
		return lockManager.lock()
				.setOwner(lockOwner)
				// allow these locks to be cleaned-up on server starts.
				// NOTE: when we will add the ICs to workpackages we will move the ICs to another owner and we will also set AutoCleanup=false
				.setAutoCleanup(true)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_C_Invoice_Candidate.class, adPInstanceId)
				.acquire();
	}

	/**
	 * @return true if invoice candidate is eligible for enqueueing
	 */
	private boolean isEligibleForEnqueueing(final I_C_Invoice_Candidate ic)
	{
		final ILoggable loggable = getLoggable();

		//
		// 07666: If selected, only use the invoices flagged as approved for invoicing
		if (isOnlyApprovedForInvoicing() && !ic.isApprovalForInvoicing())
		{
			// don't log; it's obvious for the user, and currently if won't happen anyways (die to the select's whereclause)
			// final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_APPROVAL, new Object[] { ic.getC_Invoice_Candidate_ID() });
			// loggable.addLog(msg);
			return false;
		}

		//
		// Check other reasons no to enqueue this ic: Processed, IsError, DateToInvoice.
		// NOTE: having this line in the middle because we will display only one skip reason and SKIPPED_QTY_TO_INVOICE is usually less informative if the IC was already processed
		if (invoiceCandBL.isSkipCandidateFromInvoicing(ic, isIgnoreInvoiceSchedule(), loggable))
		{
			// NOTE: we are not logging any reason because the method already logged the reason if any.
			return false;
		}

		//
		// task 04372: we will enqueue invoice candidates with QtyToInvoice = 0, *if* they also have QtyOrdered = 0
		// task 08343: logic moved here from the where clause in C_Invoice_Candidate_EnqueueSelection
		if (ic.getQtyOrdered().signum() != 0 && invoiceCandBL.getQtyToInvoice(ic).signum() == 0)
		{
			// don't log; it's obvious for the user and there might be a lot of skippings because of this
			// final String msg = msgBL.getMsg(getCtx(), MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_QTY_TO_INVOICE, new Object[] { ic.getC_Invoice_Candidate_ID() });
			// loggable.addLog(msg);
			return false;
		}

		return true;
	}

	private final void prepareSelectionForEnqueueing(final int adPInstanceId)
	{
		final Timestamp today = invoiceCandBL.getToday();
		final String trxName = getTrxNameNotNull();

		//
		// Updating candidates previous to enqueueing, if the parameter has been set (task 03905)
		// task 08628: always make sure that every IC has the *same* dateInvoiced. possible other dates that were previously set don't matter.
		// this is critical because we assume that dateInvoiced is not part of the aggregation key, so different values would fail the invoicing
		final Timestamp dateInvoiced = getDateInvoiced() != null ? getDateInvoiced() : invoiceCandBL.getToday();
		invoiceCandDAO.updateDateInvoiced(dateInvoiced, adPInstanceId, trxName);

		//
		// Updating candidates previous to enqueueing, if the parameter has been set (task 08437)
		// task 08628: same as for dateInvoiced
		final Timestamp dateAcct = getDateAcct() != null ? getDateAcct() : dateInvoiced;
		invoiceCandDAO.updateDateAcct(dateAcct, adPInstanceId, trxName);

		//
		// Update POReference (task 07978)
		final String poReference = getPOReference();
		if (!Check.isEmpty(poReference, true))
		{
			invoiceCandDAO.updatePOReference(poReference, adPInstanceId, trxName);
		}

		//
		// Make sure invoicing dates are correctly set
		// * DateInvoiced - set it to today if null
		// * DateAcct - set it to DateInvoiced if null
		//
		// NOTE: before we group the invoices by their header aggregation key,
		// we need to make sure that all of them have the DateInvoiced and DateAcct set.
		// If not, they will have different aggregation key in case one has a implicit DateInvoiced (i.e. today) and other IC has an explicit DateInvoiced.
		invoiceCandDAO.updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced,
				today, // value
				true, // updateOnlyIfNull
				adPInstanceId, // selectionId
				trxName);
		invoiceCandDAO.updateColumnForSelection(
				I_C_Invoice_Candidate.COLUMNNAME_DateAcct,
				ModelColumnNameValue.forColumnName(I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced), // value
				true, // updateOnlyIfNull
				adPInstanceId, // selectionId
				trxName);
	}

	private final Iterable<I_C_Invoice_Candidate> retrieveSelection(final int adPInstanceId)
	{
		// NOTE: we designed this method for the case of enqueuing 1mio invoice candidates.

		return new Iterable<I_C_Invoice_Candidate>()
		{
			@Override
			public Iterator<I_C_Invoice_Candidate> iterator()
			{
				final Properties ctx = getCtx();
				final String trxName = getTrxNameNotNull();
				return invoiceCandDAO.retrieveIcForSelection(ctx, adPInstanceId, trxName);
			}
		};
	}

	@Override
	public IInvoiceCandidateEnqueuer setContext(final Properties ctx, final String trxName)
	{
		this._ctx = ctx;
		this._trxNameInitial = trxName;
		return this;
	}

	/**
	 * @return context; never returns null
	 */
	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	/**
	 * @return initial transaction, i.e. the transaction used when this enqueuer was called
	 */
	private final String getTrxNameInitial()
	{
		return _trxNameInitial;
	}

	/**
	 * @return transaction name; never returns an null transaction
	 */
	private final String getTrxNameNotNull()
	{
		trxManager.assertTrxNameNotNull(_trxName);
		return _trxName;
	}

	private final void setTrxName(final String trxName)
	{
		this._trxName = trxName;
	}

	private final boolean isOnlyApprovedForInvoicing()
	{
		return getInvoicingParams().isOnlyApprovedForInvoicing();
	}

	private final boolean isIgnoreInvoiceSchedule()
	{
		return getInvoicingParams().isIgnoreInvoiceSchedule();
	}

	@Override
	public IInvoiceCandidateEnqueuer setLoggable(final ILoggable loggable)
	{
		Check.assumeNotNull(loggable, "loggable not null");
		this._loggable = loggable;
		return this;
	}

	private final ILoggable getLoggable()
	{
		return _loggable;
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

	private final Timestamp getDateInvoiced()
	{
		return getInvoicingParams().getDateInvoiced();
	}

	private final Timestamp getDateAcct()
	{
		return getInvoicingParams().getDateAcct();
	}

	private final String getPOReference()
	{
		return getInvoicingParams().getPOReference();
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
					.setLogger(getLoggable())
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
}
