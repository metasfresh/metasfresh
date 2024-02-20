package de.metas.async.api.impl;

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageParamsBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.async.spi.impl.SizeBasedWorkpackagePrio;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.logging.TableRecordMDC;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

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

/* package */class WorkPackageBuilder implements IWorkPackageBuilder
{
	// Parameters
	private final Properties _ctx;
	@NonNull
	private final QueuePackageProcessorId _queuePackageProcessorId;
	private final IWorkPackageQueue _workPackageQueue;

	private PInstanceId adPInstanceId;

	private IWorkpackagePrioStrategy _priority = SizeBasedWorkpackagePrio.INSTANCE;
	private AsyncBatchId asyncBatchId = null;
	private boolean asyncBatchSet = false;
	private UserId userInChargeId;
	private WorkPackageParamsBuilder _parametersBuilder;
	private String _trxName = ITrx.TRXNAME_None;
	private boolean _trxNameBound = false;
	private final LinkedHashSet<TableRecordReference> elements = new LinkedHashSet<>();
	/**
	 * Locker used to lock enqueued elements
	 */
	private ILockCommand _elementsLocker = null;

	// Status
	private final AtomicBoolean built = new AtomicBoolean(false);

	/* package */ WorkPackageBuilder(
			final Properties _ctx,
			final IWorkPackageQueue _workPackageQueue,
			@NonNull final QueuePackageProcessorId _queuePackageProcessorId)
	{
		this._ctx = _ctx;
		this._queuePackageProcessorId = _queuePackageProcessorId;
		this._workPackageQueue = _workPackageQueue;
	}

	@Override
	public I_C_Queue_WorkPackage buildAndEnqueue()
	{
		// Add parameter "ElementsLockOwner" if we are locking
		final ILockCommand elementsLocker = getElementsLockerOrNull();
		if (elementsLocker != null)
		{
			parameters().setParameter(IWorkpackageProcessor.PARAMETERNAME_ElementsLockOwner, elementsLocker.getOwner().getOwnerName());
		}

		// Mark as built.
		// From now one, any changes are prohibited.
		markAsBuilt();

		// Create the workpackage
		final I_C_Queue_WorkPackage workPackage = buildWithPackageProcessor();

		return enqueue(workPackage, elementsLocker);
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

	@NonNull
	public I_C_Queue_WorkPackage buildWithPackageProcessor()
	{
		final I_C_Queue_WorkPackage newWorkPackage = InterfaceWrapperHelper.create(_ctx, I_C_Queue_WorkPackage.class, ITrx.TRXNAME_None);

		newWorkPackage.setC_Queue_PackageProcessor_ID(_queuePackageProcessorId.getRepoId());
		newWorkPackage.setAD_PInstance_ID(PInstanceId.toRepoId(adPInstanceId));

		return newWorkPackage;
	}

	@Override
	public WorkPackageBuilder setPriority(final IWorkpackagePrioStrategy priority)
	{
		assertNotBuilt();

		_priority = priority;
		return this;
	}

	private IWorkpackagePrioStrategy getPriority()
	{
		return _priority;
	}

	private IWorkPackageQueue getWorkpackageQueue()
	{
		return _workPackageQueue;
	}

	private void assertNotBuilt()
	{
		Check.assume(!built.get(), "not already built");
	}

	/**
	 * Marks this builder as already executed. Fails if it was already marked before.
	 */
	private void markAsBuilt()
	{
		final boolean wasAlreadyBuilt = built.getAndSet(true);
		Check.assume(!wasAlreadyBuilt, "not already built");
	}

	@Override
	public WorkPackageBuilder addElement(@NonNull final Object model)
	{
		//
		// Add the model to elements to enqueue
		final TableRecordReference record = TableRecordReference.ofOrNull(model);
		return addElement(record);
	}

	@NonNull
	private WorkPackageBuilder addElement(@NonNull final TableRecordReference record)
	{
		assertNotBuilt();

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

	private ILockCommand getElementsLockerOrNull()
	{
		return _elementsLocker;
	}

	@Override
	public void discard()
	{
		assertNotBuilt();
		// TODO split the items of this WP from the main lock and unlock them.
		markAsBuilt();
	}

	@Override
	public WorkPackageBuilder setC_Async_Batch(@Nullable final I_C_Async_Batch asyncBatch)
	{
		if (asyncBatch == null)
		{
			return this;
		}

		assertNotBuilt();

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoId(asyncBatch.getC_Async_Batch_ID());

		return setC_Async_Batch_ID(asyncBatchId);
	}

	@Override
	public IWorkPackageBuilder setUserInChargeId(@Nullable final UserId userInChargeId)
	{
		assertNotBuilt();
		this.userInChargeId = userInChargeId;
		return this;
	}

	@Override
	public WorkPackageBuilder setC_Async_Batch_ID(@Nullable final AsyncBatchId asyncBatchId)
	{
		if (asyncBatchId == null)
		{
			return this;
		}

		assertNotBuilt();

		this.asyncBatchId = asyncBatchId;
		this.asyncBatchSet = true;

		return this;
	}

	@Override
	public IWorkPackageBuilder setAD_PInstance_ID(final PInstanceId adPInstanceId)
	{
		this.adPInstanceId = adPInstanceId;
		return this;
	}

	@NonNull
	private I_C_Queue_WorkPackage enqueue(@NonNull final I_C_Queue_WorkPackage workPackage, @Nullable final ILockCommand elementsLocker)
	{
		final IWorkPackageQueue workPackageQueue = getWorkpackageQueue();

		final IWorkpackagePrioStrategy workPackagePriority = getPriority();

		final String originalWorkPackageTrxName = getTrxName(workPackage);

		try (final IAutoCloseable ignored = () -> setTrxName(workPackage, originalWorkPackageTrxName))
		{
			// Fact: the workpackage trxName is used when creating the workpackage and its elements.
			// Therefore, we temporarily set it to be our _trxName.
			// Otherwise, if the current trx fails, the workpackage will have been created, but not have been flagged as "ReadyForProcessing" (which sucks).
			setTrxName(workPackage, _trxName);

			@SuppressWarnings("deprecation") // Suppressing the warning, because *this class* is the workpackage builder to be used
			final I_C_Queue_WorkPackage workpackage = workPackageQueue.enqueueWorkPackage(
					workPackage,
					workPackagePriority);

			try (final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(workpackage))
			{
				// Set the Async batch if provided
				// TODO: optimize this and set everything in one shot and then save it.
				if (asyncBatchSet)
				{
					workpackage.setC_Async_Batch_ID(AsyncBatchId.toRepoId(asyncBatchId));
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

				createWorkpackageElements(workPackageQueue, workpackage);

				//
				// Lock enqueued workpackage elements
				if (elementsLocker != null)
				{
					final ILock lock = elementsLocker.acquire();
					lock.unlockAllAfterTrxRollback();
				}

				//
				// Actually mark the workpackage as ready for processing
				// NOTE: method also accepts null transaction and in that case it will immediately mark as ready for processing
				workPackageQueue.markReadyForProcessingAfterTrxCommit(workpackage, _trxName);
			}
			return workpackage;
		}
	}
}
