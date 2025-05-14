package de.metas.async.api;

import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.lock.api.ILockCommand;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

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

public interface IWorkPackageBuilder
{
	/**
	 * Creates the workpackage and marks it as ready for processing (but also see {@link #bindToTrxName(String)}).
	 * <p>
	 * If a locker was specified (see {@link #setElementsLocker(ILockCommand)}) all elements will be locked.
	 */
	I_C_Queue_WorkPackage buildAndEnqueue();

	@NonNull
	default QueueWorkPackageId buildAndGetId()
	{
		final I_C_Queue_WorkPackage workpackage = buildAndEnqueue();
		return QueueWorkPackageId.ofRepoId(workpackage.getC_Queue_WorkPackage_ID());
	}

	/**
	 * This is the sibling of {@link #buildAndEnqueue()}, but it doesn't build/enqueue the work package. Instead, it discards it.
	 * Note that this method also marks the package builder as "build", so no more elements can be added after this method was called.
	 * <p>
	 * <b>IMPORTANT</b> as of now, the method does nothing about possible locks.
	 */
	void discard();

	/**
	 * Creates or returns the existing workpackage parameters builder of this package builder.
	 * <p>
	 * NOTE: the {@link IWorkPackageParamsBuilder} will trigger the creation of {@link I_C_Queue_WorkPackage}.
	 */
	IWorkPackageParamsBuilder parameters();

	default IWorkPackageBuilder parameters(final Map<String, ?> parameters)
	{
		parameters().setParameters(parameters);
		return this;
	}

	default IWorkPackageBuilder parameters(final IParams parameters)
	{
		parameters().setParameters(parameters);
		return this;
	}

	default IWorkPackageBuilder parameter(final String parameterName, final Object parameterValue)
	{
		parameters().setParameter(parameterName, parameterValue);
		return this;
	}

	/**
	 * Set a work-package parameter to the value of the given UUID.
	 * When a work-package with a correlation-id is processed, a {@link de.metas.async.event.WorkpackageProcessedEvent} is posted.
	 * <p>
	 * Works in conjunction with {@link de.metas.async.event.WorkpackagesProcessedWaiter}.
	 */
	default IWorkPackageBuilder setCorrelationId(@Nullable final UUID correlationId)
	{
		final String uuidStr = correlationId != null ? correlationId.toString() : null;

		parameter(Async_Constants.ASYNC_PARAM_CORRELATION_UUID, uuidStr);
		return this;
	}

	/**
	 * Sets the workpackage queue priority. If no particular priority is set, the system will use {@link IWorkPackageQueue#PRIORITY_AUTO}.
	 */
	IWorkPackageBuilder setPriority(IWorkpackagePrioStrategy priority);

	/**
	 * Sets the async batch (optional).
	 * <p>
	 * If the async batch it's not set, it will be inherited.
	 */
	IWorkPackageBuilder setC_Async_Batch(@Nullable I_C_Async_Batch asyncBatch);

	/**
	 * Sets workpackage user in charge.
	 * This will be the user which will be notified in case the workpackage processing fails.
	 */
	IWorkPackageBuilder setUserInChargeId(@Nullable UserId userInChargeId);

	/**
	 * Adds given model to workpackage elements.
	 * If a locker is already set (see {@link #setElementsLocker(ILockCommand)}) this model will be also added to locker.
	 *
	 * @param model model or {@link ITableRecordReference}.
	 */
	IWorkPackageBuilder addElement(Object model);

	/**
	 * Convenient method to add a collection of models.
	 *
	 * @see #addElement(Object)
	 */
	IWorkPackageBuilder addElements(Iterable<?> models);

	/**
	 * Ask the builder to "bind" the new workpackage to given transaction.
	 * As a consequence, the workpackage will be marked as "ready for processing" when this transaction is committed.
	 * <p>
	 * If the transaction is null, the workpackage will be marked as ready immediately, on build.
	 */
	IWorkPackageBuilder bindToTrxName(@Nullable String trxName);

	/**
	 * Ask the builder to "bind" the new workpackage to current thread inherited transaction.
	 * As a consequence, the workpackage will be marked as "ready for processing" when this transaction is committed.
	 * <p>
	 * If there is no thread inherited transaction, the workpackage will be marked as ready immediately, on build.
	 */
	default IWorkPackageBuilder bindToThreadInheritedTrx()
	{
		return bindToTrxName(ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Sets locker to be used to lock enqueued elements.
	 * The elements are unlocked right after the WP was processed.
	 */
	IWorkPackageBuilder setElementsLocker(ILockCommand elementsLocker);

	/**
	 * Overloading set async batch, to enable setting async batch also by id (optional).
	 * If the asyncBatchId is not set, it will be inherited.
	 */
	IWorkPackageBuilder setC_Async_Batch_ID(@Nullable AsyncBatchId asyncBatchId);

	I_C_Queue_WorkPackage buildWithPackageProcessor();

	IWorkPackageBuilder setAD_PInstance_ID(final PInstanceId adPInstanceId);
}
