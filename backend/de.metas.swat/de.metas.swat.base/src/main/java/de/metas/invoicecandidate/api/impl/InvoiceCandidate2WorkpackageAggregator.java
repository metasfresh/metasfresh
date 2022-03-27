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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageParamsBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoicingParams;
import de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.MapReduceAggregator;
import lombok.NonNull;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Properties;

/**
 * Takes {@link I_C_Invoice_Candidate}s, group them by "IC's header aggregation key" and add them {@link InvoiceCandWorkpackageProcessor} workpackages.
 *
 * @author tsa
 *
 */
/* package */class InvoiceCandidate2WorkpackageAggregator
		extends MapReduceAggregator<IWorkPackageBuilder, I_C_Invoice_Candidate>
{
	// services
	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	//
	// Parameters
	private final String _trxName;
	private final IWorkPackageQueue _workpackageQueue;
	private PInstanceId pInstanceId;
	private IWorkpackagePrioStrategy workpackagePriority = SizeBasedWorkpackagePrio.INSTANCE;
	private ILock invoiceCandidatesLock = ILock.NULL;
	private IInvoicingParams invoicingParams;
	private I_C_Async_Batch _asyncBatch = null;

	// status
	private final IdentityHashMap<IWorkPackageBuilder, ICNetAmtToInvoiceChecker> group2netAmtToInvoiceChecker = new IdentityHashMap<>();

	public InvoiceCandidate2WorkpackageAggregator(@NonNull final Properties ctx, @Nullable final String trxName)
	{
		_trxName = trxName; // null/none is accepted

		_workpackageQueue = workPackageQueueFactory.getQueueForEnqueuing(ctx, InvoiceCandWorkpackageProcessor.class);

		//
		// Configure: aggregation key builder, i.e. use invoice candidate's header aggregation key builder
		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
		final IAggregationKeyBuilder<I_C_Invoice_Candidate> itemAggregationKeyBuilder = aggregationBL.getHeaderAggregationKeyBuilder();
		setItemAggregationKeyBuilder(itemAggregationKeyBuilder);

		//
		// Configure: groups buffer size = 1
		// i.e. we keep only one group in memory at the time
		setGroupsBufferSize(1);
	}

	private IWorkPackageQueue getWorkPackageQueue()
	{
		return _workpackageQueue;
	}

	private String getTrxName()
	{
		return _trxName;
	}

	private ILock getInvoiceCandidatesLock()
	{
		Check.assumeNotNull(invoiceCandidatesLock, "invoiceCandidatesLock not null");
		return invoiceCandidatesLock;
	}

	public InvoiceCandidate2WorkpackageAggregator setInvoiceCandidatesLock(final ILock invoiceCandidatesLock)
	{
		this.invoiceCandidatesLock = invoiceCandidatesLock;
		return this;
	}

	/**
	 * Create a new workpackage.
	 */
	@Override
	protected IWorkPackageBuilder createGroup(final Object itemHashKey, final I_C_Invoice_Candidate item)
	{
		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchId(item)
				.map(asyncBatchBL::getAsyncBatchById)
				.orElse(null);

		final IWorkPackageBuilder workpackageBuilder = _workpackageQueue
				.newWorkPackage()
				.setAD_PInstance_ID(pInstanceId)
				.setPriority(workpackagePriority)
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null)) // we want the enqueuing user to be notified on problems
				.setC_Async_Batch(asyncBatch)
				.bindToTrxName(getTrxName());

		//
		// Create a new locker which will grab the locked invoice candidates from initial lock
		// and it will move them to a new owner which is created per workpackage
		final LockOwner workpackageElementsLockOwner = LockOwner.newOwner("IC_" + itemHashKey);
		final ILockCommand workpackageElementsLocker = getInvoiceCandidatesLock()
				.split()
				.setOwner(workpackageElementsLockOwner)
				.setAutoCleanup(false); // from this point own we don't want to allow the system to auto clean the locks
		workpackageBuilder.setElementsLocker(workpackageElementsLocker);

		//
		// Start tracking NetAmtToInvoice
		final ICNetAmtToInvoiceChecker netAmtToInvoiceChecker = new ICNetAmtToInvoiceChecker();
		group2netAmtToInvoiceChecker.put(workpackageBuilder, netAmtToInvoiceChecker);

		return workpackageBuilder;
	}

	/**
	 * Add invoice candidate to workpackage
	 */
	@Override
	protected void addItemToGroup(final IWorkPackageBuilder group, final I_C_Invoice_Candidate item)
	{
		group.addElement(item);

		//
		// Update NetAmtToInvoice checksum (08610)
		final ICNetAmtToInvoiceChecker netAmtToInvoiceChecker = getICNetAmtToInvoiceChecker(group);
		netAmtToInvoiceChecker.add(item);
	}

	private ICNetAmtToInvoiceChecker getICNetAmtToInvoiceChecker(final IWorkPackageBuilder group)
	{
		final ICNetAmtToInvoiceChecker netAmtToInvoiceChecker = group2netAmtToInvoiceChecker.get(group);
		Check.assumeNotNull(netAmtToInvoiceChecker, "netAmtToInvoiceChecker not null for {}", group);
		return netAmtToInvoiceChecker;
	}

	/**
	 * Close group: i.e. make workpackage as ready for processing.
	 */
	@Override
	protected void closeGroup(@NonNull final IWorkPackageBuilder group)
	{
		//
		// Create workpackage parameters (08610)
		final ICNetAmtToInvoiceChecker netAmtToInvoiceChecker = getICNetAmtToInvoiceChecker(group);
		final IWorkPackageParamsBuilder parameters = group.parameters();
		if (invoicingParams != null)
		{
			parameters.setParameters(invoicingParams.asMap());
		}
		parameters.setParameter(IInvoicingParams.PARA_Check_NetAmtToInvoice, netAmtToInvoiceChecker.getValue());

		if (_asyncBatch != null)
		{
			group.setC_Async_Batch(_asyncBatch);
		}

		//
		// Mark the workpackage as ready for processing (when trxName will be commited)
		group.buildAndEnqueue();
	}

	public InvoiceCandidate2WorkpackageAggregator setAD_PInstance_Creator_ID(@NonNull final PInstanceId adPInstanceId)
	{
		this.pInstanceId = adPInstanceId;
		return this;
	}

	public InvoiceCandidate2WorkpackageAggregator setC_Async_Batch(final I_C_Async_Batch asyncBatch)
	{
		_asyncBatch = asyncBatch;
		return this;
	}

	public InvoiceCandidate2WorkpackageAggregator setPriority(final IWorkpackagePrioStrategy priority)
	{
		workpackagePriority = priority;
		return this;
	}

	public InvoiceCandidate2WorkpackageAggregator setInvoicingParams(@NonNull final IInvoicingParams invoicingParams)
	{
		this.invoicingParams = invoicingParams;
		return this;
	}

	/**
	 * Gets unprocessed workpackages queue size
	 */
	public final int getQueueSize()
	{
		final int countUnprocessedWorkPackages = getWorkPackageQueue().size();
		return countUnprocessedWorkPackages;
	}
}
