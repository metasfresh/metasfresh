package de.metas.async.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.setTrxName;

/*
 * #%L
 * de.metas.async
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

import java.util.LinkedHashSet;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.MDC.MDCCloseable;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageParamsBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.logging.TableRecordMDC;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/* package */class WorkPackageBuilder implements IWorkPackageBuilder
{
	// Parameters
	private final WorkPackageBlockBuilder _blockBuilder;
	private IWorkpackagePrioStrategy _priority = SizeBasedWorkpackagePrio.INSTANCE;
	private I_C_Async_Batch asyncBatch = null;
	private boolean asyncBatchSet = false;
	private UserId userInChargeId;
	private WorkPackageParamsBuilder _parametersBuilder;
	private String _trxName = ITrx.TRXNAME_None;
	private boolean _trxNameBound = false;
	private final LinkedHashSet<TableRecordReference> elements = new LinkedHashSet<>();
	/** Locker used to lock enqueued elements */
	private ILockCommand _elementsLocker = null;
	/** Lock aquired when enqueued elements were locked */
	private Future<ILock> _futureElementsLock;

	// Status
	private final AtomicBoolean built = new AtomicBoolean(false);

	/* package */ WorkPackageBuilder(@NonNull final WorkPackageBlockBuilder blockBuilder)
	{
		_blockBuilder = blockBuilder;
	}

	@Override
	public IWorkPackageBlockBuilder end()
	{
		return _blockBuilder;
	}

	@Override
	public I_C_Queue_WorkPackage build()
	{
		// Add parameter "ElementsLockOwner" if we are are locking
		final ILockCommand elementsLocker = getElementsLockerOrNull();
		if (elementsLocker != null)
		{
			parameters().setParameter(IWorkpackageProcessor.PARAMETERNAME_ElementsLockOwner, elementsLocker.getOwner().getOwnerName());
		}

		// Mark as built.
		// From now one, any changes are prohibited.
		markAsBuilt();

		// Create the workpackage
		final IWorkPackageQueue workpackageQueue = getWorkpackageQueue();
		final I_C_Queue_Block queueBlock = getC_Queue_Block();
		final String queueBlockOriginalTrxName = getTrxName(queueBlock);

		final IWorkpackagePrioStrategy workpackagePriority = getPriority();

		try (final IAutoCloseable temporary = () -> setTrxName(queueBlock, queueBlockOriginalTrxName))
		{
			// Fact: the queueBlock's trxName is used when creating the workpackage and its elements.
			// Therefore we temporarily set it to be our _trxName.
			// Otherways, if the current trx fails, the workpackage will have been created, but not have been flagged as "ReadyForProcessing" (which sucks).
			setTrxName(queueBlock, _trxName);

			@SuppressWarnings("deprecation") // Suppressing the warning, because *this class* is the workpackage builder to be used
			final I_C_Queue_WorkPackage workpackage = workpackageQueue.enqueueWorkPackage(
					queueBlock,
					workpackagePriority);
			try (final MDCCloseable workpackageRecordMDC = TableRecordMDC.putTableRecordReference(workpackage))
			{
				// Set the Async batch if provided
				// TODO: optimize this and set everything in one shot and then save it.
				if (asyncBatchSet)
				{
					workpackage.setC_Async_Batch(asyncBatch);
					Services.get(IQueueDAO.class).save(asyncBatch);
				}

				if (userInChargeId != null)
				{
					workpackage.setAD_User_InCharge_ID(userInChargeId.getRepoId());
				}

				// Create workpackage parameters
				if (_parametersBuilder != null)
				{
					_parametersBuilder.setC_Queue_WorkPackage(workpackage);
					_parametersBuilder.build();
				}

				createWorkpackageElements(workpackageQueue, workpackage);

				//
				// Lock enqueued workpackage elements
				if (elementsLocker != null)
				{
					_futureElementsLock = elementsLocker.acquireBeforeTrxCommit(_trxName);
				}

				//
				// Actually mark the workpackage as ready for processing
				// NOTE: method also accepts null transaction and in that case it will immediately mark as ready for processing
				workpackageQueue.markReadyForProcessingAfterTrxCommit(workpackage, _trxName);
			}
			return workpackage;
		}
	}

	private void createWorkpackageElements(
			@NonNull final IWorkPackageQueue workpackageQueue,
			@NonNull final I_C_Queue_WorkPackage workpackage)
	{
		for (final TableRecordReference element : elements)
		{
			workpackageQueue.enqueueElement(workpackage, element.getAD_Table_ID(), element.getRecord_ID());
		}
	}

	private final I_C_Queue_Block getC_Queue_Block()
	{
		return _blockBuilder.getCreateBlock();
	}

	@Override
	public WorkPackageBuilder setPriority(final IWorkpackagePrioStrategy priority)
	{
		assertNotBuilt();

		_priority = priority;
		return this;
	}

	private final IWorkpackagePrioStrategy getPriority()
	{
		return _priority;
	}

	private final IWorkPackageQueue getWorkpackageQueue()
	{
		return _blockBuilder.getWorkPackageQueue();
	}

	private final void assertNotBuilt()
	{
		Check.assume(!built.get(), "not already built");
	}

	/**
	 * Marks this builder as already executed. Fails if it was already marked before.
	 */
	private final void markAsBuilt()
	{
		final boolean wasAlreadyBuilt = built.getAndSet(true);
		Check.assume(!wasAlreadyBuilt, "not already built");
	}

	@Override
	public WorkPackageBuilder addElement(final Object model)
	{
		assertNotBuilt();

		//
		// Add the model to elements to enqueue
		final TableRecordReference record = TableRecordReference.ofOrNull(model);
		elements.add(record);

		//
		// Enqueue record to be locked when the workpackage is built
		final ILockCommand locker = getElementsLockerOrNull();
		if (locker != null)
		{
			locker.addRecord(record);
		}

		return this;
	}

	@Override
	public WorkPackageBuilder addElements(final Iterable<?> models)
	{
		for (final Object model : models)
		{
			addElement(model);
		}

		return this;
	}

	@Override
	public WorkPackageBuilder bindToTrxName(final String trxName)
	{
		assertNotBuilt();
		Check.assume(!_trxNameBound, "builder not already bound to a transaction");

		_trxName = trxName;
		_trxNameBound = true;

		return this;
	}

	@Override
	public IWorkPackageParamsBuilder parameters()
	{
		assertNotBuilt();

		if (_parametersBuilder == null)
		{
			_parametersBuilder = new WorkPackageParamsBuilder(this);
		}
		return _parametersBuilder;
	}

	@Override
	public WorkPackageBuilder setElementsLocker(final ILockCommand elementsLocker)
	{
		assertNotBuilt();
		_elementsLocker = elementsLocker;
		return this;
	}

	private final ILockCommand getElementsLockerOrNull()
	{
		return _elementsLocker;
	}

	@Override
	public Future<ILock> getElementsLock()
	{
		return _futureElementsLock;
	}

	@Override
	public void discard()
	{
		assertNotBuilt();
		// TODO split the items of this WP from the main lock and unlock them.
		markAsBuilt();
	}

	@Override
	public WorkPackageBuilder setC_Async_Batch(final I_C_Async_Batch asyncBatch)
	{
		assertNotBuilt();
		this.asyncBatch = asyncBatch;
		this.asyncBatchSet = true;
		return this;
	}

	@Override
	public IWorkPackageBuilder setUserInChargeId(@Nullable final UserId userInChargeId)
	{
		assertNotBuilt();
		this.userInChargeId = userInChargeId;
		return this;
	}
}
