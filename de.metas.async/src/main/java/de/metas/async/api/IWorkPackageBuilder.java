package de.metas.async.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.concurrent.Future;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;

public interface IWorkPackageBuilder
{
	/**
	 * Creates the workpackage and marks it as ready for processing (but also see {@link #bindToTrxName(String)}).
	 * <p>
	 * Is this builder's parent ({@link IWorkPackageBlockBuilder}) was not yet created/stored in the DB, this method will do it on the fly.
	 * <p>
	 * If a locker was specified (see {@link #setElementsLocker(ILockCommand)}) all elements will be locked.
	 */
	I_C_Queue_WorkPackage build();

	/**
	 * This is the sibling of {@link #build()}, but it doesn't build/enqueue the work package. Instead it discards it.
	 * Note that this method also marks the package builder as "build", so no more elements can be added after this method was called.
	 *
	 * <b>IMPORTANT</b> as of now, the method does nothing about possible locks.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08756_EDI_Lieferdispo_Lieferschein_und_Complete_%28101564484292%29
	 */
	void discard();

	/**
	 * Only return the (parent) block builder. Don't do anything else (no sideeffects)
	 * @return parent builder
	 */
	IWorkPackageBlockBuilder end();

	/**
	 * Start creating the workpackage parameters.
	 *
	 * NOTE: the {@link IWorkPackageParamsBuilder} will trigger the creation of {@link I_C_Queue_WorkPackage}.
	 */
	IWorkPackageParamsBuilder parameters();

	/**
	 * Sets the workpackage's queue priority. If no particular priority is set, the system will use {@link IWorkPackageQueue#PRIORITY_AUTO}.
	 */
	IWorkPackageBuilder setPriority(IWorkpackagePrioStrategy priority);

	/**
	 * Sets the async batch (optional).
	 *
	 * If the async batch it's not set, it will be inherited.
	 *
	 * @param asyncBatch
	 */
	IWorkPackageBuilder setC_Async_Batch(I_C_Async_Batch asyncBatch);

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
	 * @param models
	 * @see #addElement(Object)
	 */
	IWorkPackageBuilder addElements(Iterable<?> models);

	/**
	 * Ask the builder to "bind" the new workpackage to given transaction.
	 * As a consequence, the workpackage will be marked as "ready for processing" when this transaction is commited.
	 * 
	 * If the transaction is null, the workpackage will be marked as ready immediately, on build.
	 *
	 * @param trxName
	 */
	IWorkPackageBuilder bindToTrxName(String trxName);
	
	/**
	 * Ask the builder to "bind" the new workpackage to current thread inerited transaction.
	 * As a consequence, the workpackage will be marked as "ready for processing" when this transaction is commited.
	 * 
	 * If there is no thread inherited transaction, the workpackage will be marked as ready immediately, on build.
	 */
	default IWorkPackageBuilder bindToThreadInheritedTrx()
	{
		return bindToTrxName(ITrx.TRXNAME_ThreadInherited);
	}

	/** Sets locker to be used to lock enqueued elements */
	IWorkPackageBuilder setElementsLocker(ILockCommand elementsLocker);

	/**
	 * @return
	 *         Lock aquired when enqueued elements were locked (on {@link #build()}).
	 *         Could be null if no lock was aquired.
	 */
	Future<ILock> getElementsLock();
}
