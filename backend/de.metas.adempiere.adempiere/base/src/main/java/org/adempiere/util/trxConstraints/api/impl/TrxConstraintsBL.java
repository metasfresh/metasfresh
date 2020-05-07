package org.adempiere.util.trxConstraints.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

public class TrxConstraintsBL implements ITrxConstraintsBL
{
	@VisibleForTesting
	public static final String SYSCONFIG_TRX_CONSTRAINTS_DISABLED = "org.compiere.util.trxConstraints.disabled";
	public static final boolean DEFAULT_TRX_CONSTRAINTS_DISABLED = true;

	/**
	 * Instance to return in case that trx constraints have been globally disabled
	 */
	public static final TrxConstraintsDisabled disabled = TrxConstraintsDisabled.get();

	private final Map<Thread, Deque<ITrxConstraints>> thread2TrxConstraint = new HashMap<Thread, Deque<ITrxConstraints>>();
	private final CloseableReentrantLock thread2TrxConstraintLock = new CloseableReentrantLock();

	@Override
	public ITrxConstraints getConstraints()
	{
		final Thread callingThread = Thread.currentThread();
		return getConstraints(callingThread);
	}
	
	protected boolean isDisabled()
	{
		final ISysConfigBL sysconfigBL = Services.get(ISysConfigBL.class);
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final boolean disabled = sysconfigBL.getBooleanValue(SYSCONFIG_TRX_CONSTRAINTS_DISABLED, DEFAULT_TRX_CONSTRAINTS_DISABLED, adClientId);
		return disabled;
	}

	@Override
	public ITrxConstraints getConstraints(final Thread thread)
	{
		if (isDisabled())
		{
			return disabled;
		}

		try (final CloseableReentrantLock lock = thread2TrxConstraintLock.open())
		{
			Deque<ITrxConstraints> stack = thread2TrxConstraint.get(thread);
			if (stack == null)
			{
				stack = new ArrayDeque<ITrxConstraints>();
				thread2TrxConstraint.put(thread, stack);
			}

			if (!stack.isEmpty())
			{
				return stack.getFirst();
			}

			final ITrxConstraints newInstance = new TrxConstraints();
			stack.push(newInstance);

			return newInstance;
		}
	}

	@Override
	public void saveConstraints()
	{
		final Thread callingThread = Thread.currentThread();

		try (final CloseableReentrantLock lock = thread2TrxConstraintLock.open())
		{
			final ITrxConstraints constraints = getConstraints(callingThread); // make sure that there is at least one instance
			if (isDisabled(constraints))
			{
				return;
			}

			final Deque<ITrxConstraints> stack = thread2TrxConstraint.get(callingThread);
			Check.assume(stack != null, "Stack for thread " + callingThread + " is not null");
			Check.assume(!stack.isEmpty(), "Stack for thread " + callingThread + " is not empty");

			final TrxConstraints constraintsNew = new TrxConstraints(constraints);
			stack.push(constraintsNew);
		}
	}

	@Override
	public void restoreConstraints()
	{
		final Thread callingThread = Thread.currentThread();

		try (final CloseableReentrantLock lock = thread2TrxConstraintLock.open())
		{
			final Deque<ITrxConstraints> stack = thread2TrxConstraint.get(callingThread);
			if (stack == null)
			{
				// There are no constraints for the calling thread.
				// In other words, getConstraints() hasn't been called yet.
				// Consequently there is nothing to restore.
				return;
			}

			Check.assume(!stack.isEmpty(), "Stack for thread " + callingThread + " is not empty");
			if (stack.size() <= 1)
			{
				// there is only the current constraint instance, but no saved instance.
				// Consequently there is nothing to restore.
				return;
			}

			stack.pop();
		}
	}

	@Override
	public boolean isDisabled(ITrxConstraints constraints)
	{
		return constraints instanceof TrxConstraintsDisabled;
	}
}
