package de.metas.async.spi;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;

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


import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;

import com.google.common.base.Optional;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;

/**
 * Implement what you want adapter for {@link IWorkpackageProcessor2}.
 *
 * @author tsa
 *
 */
public abstract class WorkpackageProcessorAdapter implements IWorkpackageProcessor2
{
	private IParams parameters = null;
	private I_C_Queue_WorkPackage workpackage;

	@Override
	public final void setParameters(final IParams parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return workpackage parameters; never return <code>null</code>
	 */
	protected final IParams getParameters()
	{
		return parameters == null ? IParams.NULL : parameters;
	}

	@Override
	public final void setC_Queue_WorkPackage(I_C_Queue_WorkPackage workpackage)
	{
		this.workpackage = workpackage;
	}

	protected final I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		Check.assumeNotNull(workpackage, "workpackage not null");
		return this.workpackage;
	}

	/**
	 * @return <code>true</code>, i.e. ask the executor to run this processor in transaction (backward compatibility)
	 */
	@Override
	public boolean isRunInTransaction()
	{
		return true;
	}

	@Override
	public boolean isAllowRetryOnError()
	{
		return true;
	}

	@Override
	public final Optional<ILock> getElementsLock()
	{
		final String elementsLockOwnerName = getParameters().getParameterAsString(PARAMETERNAME_ElementsLockOwner);
		if (Check.isEmpty(elementsLockOwnerName, true))
		{
			return Optional.absent(); // no lock was created for this workpackage
		}

		final LockOwner elementsLockOwner = LockOwner.forOwnerName(elementsLockOwnerName);
		return Optional.fromNullable(
				Services.get(ILockManager.class).getExistingLockForOwner(elementsLockOwner));
	}

	/**
	 * Returns the {@link NullLatchStrategy}.
	 */
	@Override
	public ILatchStragegy getLatchStrategy()
	{
		return NullLatchStrategy.INSTANCE;
	}

	public final <T> List<T> retrieveItems(final Class<T> modelType)
	{
		return Services.get(IQueueDAO.class).retrieveItems(getC_Queue_WorkPackage(), modelType, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * retrieves all active POs, even the ones that are caught in other packages
	 * @param modelType
	 * @return
	 */
	public final <T> List<T> retrieveAllItems(final Class<T> modelType)
	{
		return Services.get(IQueueDAO.class).retrieveAllItems(getC_Queue_WorkPackage(), modelType);
	}

	public final List<I_C_Queue_Element> retrieveQueueElements(final boolean skipAlreadyScheduledItems)
	{
		return Services.get(IQueueDAO.class).retrieveQueueElements(getC_Queue_WorkPackage(), skipAlreadyScheduledItems);
	}
}
