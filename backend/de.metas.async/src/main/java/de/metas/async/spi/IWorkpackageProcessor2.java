package de.metas.async.spi;

import java.util.Optional;

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


import lombok.NonNull;
import org.adempiere.util.api.IParams;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.lock.api.ILock;
import de.metas.lock.exceptions.LockFailedException;

import javax.annotation.Nullable;

/**
 * NOTE: don't extend this interface directly, but use {@link WorkpackageProcessorAdapter}.
 *
 * @author tsa
 *
 */
public interface IWorkpackageProcessor2 extends IWorkpackageProcessor
{
	/**
	 * Sets workpackage parameters
	 *
	 * NOTE: don't call directly. It's called by API before the processor is executed.
	 */
	void setParameters(IParams workpackageParams);

	/**
	 * Sets the workpackage definition. This is exaclty the same as the workpackage that will be passed to {@link #processWorkPackage(I_C_Queue_WorkPackage, String)} method.
	 *
	 * NOTE: don't call directly. It's called by API before the processor is executed.
	 */
	void setC_Queue_WorkPackage(final I_C_Queue_WorkPackage workpackage);

	/**
	 *
	 * @return <ul>
	 *         <li><code>true</code> if the processor shall be executed in a separate transaction.
	 *         <li><code>false</code> if the processor shall be executed out of transaction.
	 *         </ul>
	 */
	boolean isRunInTransaction();

	/**
	 * Shall we allow this workpackage to be re-processed in future?
	 *
	 * If this method returns <code>false</code>, basically it means:
	 * <ul>
	 * <li>the workpackage will be flagged as Processed, so no future retries will be posible (i.e. by unchecking the IsError flag)
	 * <li>avoids discarding items from this workpackage on future workpackages because they were enqueued here
	 * </ul>
	 */
	boolean isAllowRetryOnError();

	@Override
	Result processWorkPackage(I_C_Queue_WorkPackage workPackage, @Nullable String localTrxName);

	/**
	 * Gets existing lock used to lock workpackage's elements.
	 *
	 * @throws LockFailedException if elements were locked but lock was not found
	 */
	Optional<ILock> getElementsLock();

	ILatchStragegy getLatchStrategy();
}
