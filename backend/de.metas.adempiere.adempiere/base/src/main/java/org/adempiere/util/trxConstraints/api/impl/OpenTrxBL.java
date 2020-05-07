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


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.adempiere.util.trxConstraints.api.TrxConstraintException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

public class OpenTrxBL implements IOpenTrxBL
{
	private static final Logger logger = LogManager.getLogger(OpenTrxBL.class);

	/**
	 * Thread ID used to group and store trxNames which does not belong to any thread.
	 * 
	 * e.g. Thread ended and TrxConstraints allows to have open transactions after thread end.
	 */
	private static final int THREAD_ENDED_ID = -100;

	/**
	 * Waits for a thread's end and makes sure that all of the tread's transactions are closed or (if the thread is allowed to leave back open transaction) registers the transactions under the
	 * thread-ID {@link #THREAD_ENDED_ID}.
	 * 
	 */
	private class ThreadEndWaiter implements Runnable
	{
		private final Thread threadToWaitFor;

		public ThreadEndWaiter(final Thread threadToWaitFor)
		{
			this.threadToWaitFor = threadToWaitFor;
		}

		@Override
		public void run()
		{
			final long threadId = threadToWaitFor.getId();
			final String stacktraceOnStart = mkStacktrace(threadToWaitFor);

			final ITrxConstraints constraints = Services.get(ITrxConstraintsBL.class).getConstraints(threadToWaitFor);
			try
			{
				threadToWaitFor.join();
			}
			catch (InterruptedException e)
			{
				Check.assume(false, "There is no InterruptedException");
			}

			final Collection<String> trxNames = threadId2TrxName.remove(threadId);
			if (trxNames != null && !trxNames.isEmpty())
			{
				if (constraints.isActive() && !constraints.isAllowTrxAfterThreadEnd())
				{
					for (final String unclosedTrxName : trxNames)
					{
						final ITrx trx = Services.get(ITrxManager.class).get(unclosedTrxName, OnTrxMissingPolicy.Fail);

						final String msg = "Rollback and closing transaction '" + trx + "' after thread " + threadToWaitFor + " has finished\n"
								+ mkStacktraceInfo(threadToWaitFor, stacktraceOnStart);
						logger.error(msg);
						trx.rollback();
						trx.close();
					}
				}
				else
				{
					// it's ok to have unclosed transactions after thread end
					getTrxNamesForThreadId(THREAD_ENDED_ID).addAll(trxNames);
				}
			}
		}
	};

	private final Map<Long, Collection<String>> threadId2TrxName = Collections.synchronizedMap(new HashMap<Long, Collection<String>>());

	private final Map<Thread, ThreadEndWaiter> threadId2ThreadEndWaiter = Collections.synchronizedMap(new WeakHashMap<Thread, ThreadEndWaiter>());

	private final Map<String, Timer> trxName2TimeoutTimer = Collections.synchronizedMap(new HashMap<String, Timer>());

	private final Map<String, Collection<ITrxSavepoint>> trxName2SavePoint = Collections.synchronizedMap(new HashMap<String, Collection<ITrxSavepoint>>());

	private final Map<String, String> trxName2Stacktrace = new HashMap<String, String>();

	@Override
	public void onCommit(ITrx trx)
	{
		// reset the timeout timer
		removeTimeoutTimer(trx);

		final String stacktraceOnTimerStart = mkStacktrace();
		trxName2Stacktrace.put(trx.getTrxName(), stacktraceOnTimerStart);
		startTimeoutTimer(trx, stacktraceOnTimerStart);
	}

	@Override
	public void onClose(ITrx trx)
	{
		removeTimeoutTimer(trx);
		getTrxNamesForCurrentThread().remove(trx.getTrxName());
		getTrxNamesForThreadId(THREAD_ENDED_ID).remove(trx.getTrxName());
		trxName2Stacktrace.remove(trx.getTrxName());
		trxName2SavePoint.remove(trx.getTrxName());
	}

	@Override
	public void onTimeOutChange(final ITrx trx)
	{
		onCommit(trx);
	}

	@Override
	public void onNewTrx(final ITrx trx)
	{
		final ITrxConstraints constraints = getConstraints();

		if (Services.get(ITrxConstraintsBL.class).isDisabled(constraints))
		{
			return; // nothing to do
		}

		final Collection<String> trxNamesForCurrentThread = getTrxNamesForCurrentThread();
		if (constraints.isActive())
		{
			final int maxTrx = constraints.getMaxTrx();
			final int trxCount = trxNamesForCurrentThread.size();
			if (maxTrx < trxCount)
			{
				throw new TrxConstraintException("Current thread '" + (Thread.currentThread().getName()) + "' has too many open transactions."
						+ "\n Transactions: " + trxNamesForCurrentThread
						+ "\n Transactions count: " + trxCount
						+ "\n Max Allowed transactions: " + maxTrx
						+ "\n Trx Constraints: " + constraints);
			}
			if (constraints.isOnlyAllowedTrxNamePrefixes())
			{
				boolean allowed = false;

				if (constraints.getAllowedTrxNamePrefixes().contains(trx.getTrxName()))
				{
					allowed = true;
				}
				else
				{
					for (final String allowedPrefix : constraints.getAllowedTrxNamePrefixes())
					{
						if (allowedPrefix == null)
						{
							// Assuming that is allowedPrefix == null AND trx.getTrxName() == null, then
							// constraints.getAllowedTrxNamePrefixes().contains(trx.getTrxName()) already returned true
							Check.assume(trx.getTrxName() != null, "");

							continue;
						}
						if (trx.getTrxName() != null && trx.getTrxName().startsWith(allowedPrefix))
						{
							allowed = true;
							break;
						}
					}
				}
				if (!allowed)
				{
					throw new TrxConstraintException("TrxName " + trx.getTrxName() + " is not allowed. Allowed prefixes: "
							+ constraints.getAllowedTrxNamePrefixes())
									.setParameter("ITrxConstraints", constraints);
				}
			}
			if (constraints.getTrxTimeoutSecs() > 0)
			{
				final String stacktraceOnTimerStart = mkStacktrace();
				trxName2Stacktrace.put(trx.getTrxName(), stacktraceOnTimerStart);
				startTimeoutTimer(trx, stacktraceOnTimerStart);
			}
		}

		trxNamesForCurrentThread.add(trx.getTrxName());

		// make sure that we wait for the current thread to end
		synchronized (threadId2ThreadEndWaiter)
		{
			if (!threadId2ThreadEndWaiter.containsKey(Thread.currentThread()))
			{
				final Thread currentThread = Thread.currentThread();
				final ThreadEndWaiter threadEndWaiter = new ThreadEndWaiter(currentThread);
				final Thread thread = new Thread(threadEndWaiter, currentThread.getName() + "-OpenTrx-ThreadEndWaiter");
				thread.setDaemon(true);
				thread.start();
				threadId2ThreadEndWaiter.put(Thread.currentThread(), threadEndWaiter);
			}
		}
	}

	/**
	 * Note: at the scheduled time we use the constraints instance obtained at timer start (and not a possibly different instance that is active at the scheduled time).
	 * 
	 * @param trx
	 */
	private void startTimeoutTimer(final ITrx trx, final String stacktraceOnTimerStart)
	{
		synchronized (trxName2TimeoutTimer)
		{
			Check.assume(!trxName2TimeoutTimer.containsKey(trx.getTrxName()), "No timeout timer registered to trxName " + trx.getTrxName());

			final ITrxConstraints constraints = getConstraints();
			if (Services.get(ITrxConstraintsBL.class).isDisabled(constraints))
			{
				return; // nothing to do
			}

			final int trxTimeoutSecs = constraints.getTrxTimeoutSecs();
			if (trxTimeoutSecs <= 0)
			{
				return; // nothing to do
			}

			final Thread callingThread = Thread.currentThread();

			final Timer timer = new Timer(callingThread.getName() + "-" + trx.getTrxName() + "_OpenTrx_TimeOut", true);
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					final String msg =
							"Transaction '" + trx + "' created by " + callingThread + " exceeded timeout of " + trxTimeoutSecs + " seconds\n"
									+ mkStacktraceInfo(callingThread, stacktraceOnTimerStart);
					logger.error(msg);
					if (!constraints.isTrxTimeoutLogOnly())
					{
						logger.error("Rollback and closing " + trx);
						trx.rollback();
						trx.close();
					}
				}
			}, trxTimeoutSecs * 1000);

			trxName2TimeoutTimer.put(trx.getTrxName(), timer);
		}
	}

	/**
	 * cancel and remove the timeout timer for the given trx.
	 * 
	 * @param trx
	 */
	private void removeTimeoutTimer(ITrx trx)
	{
		final Timer timeoutTimer = trxName2TimeoutTimer.remove(trx.getTrxName());
		if (timeoutTimer != null)
		{
			timeoutTimer.cancel();
		}
	}

	private String mkStacktrace()
	{
		return mkStacktrace(Thread.currentThread());
	}

	private String mkStacktrace(final Thread thread)
	{
		final StringBuilder sb = new StringBuilder();
		for (final StackTraceElement ste : thread.getStackTrace())
		{
			sb.append("\tat " + ste + "\n");
		}
		return sb.toString();
	}

	@Override
	public void onRollback(final ITrx trx)
	{
		removeTimeoutTimer(trx);

		final String stacktraceOnTimerStart = mkStacktrace();
		trxName2Stacktrace.put(trx.getTrxName(), stacktraceOnTimerStart);
		startTimeoutTimer(trx, stacktraceOnTimerStart);
	}

	@Override
	public void onSetSavepoint(final ITrx trx, final ITrxSavepoint savepoint)
	{
		final ITrxConstraints constraints = getConstraints();
		if (Services.get(ITrxConstraintsBL.class).isDisabled(constraints))
		{
			return; // nothing to do
		}

		if (constraints.isActive())
		{
			if (constraints.getMaxSavepoints() < getSavepointsForTrxName(trx.getTrxName()).size())
			{
				throw new TrxConstraintException("Transaction '" +
						trx + "' has too many unreleased savepoints: " + getSavepointsForTrxName(trx.getTrxName()));
			}
		}
	}

	@Override
	public void onReleaseSavepoint(final ITrx trx, final ITrxSavepoint savepoint)
	{
		getSavepointsForTrxName(trx.getTrxName()).remove(savepoint);
	}

	private static ITrxConstraints getConstraints()
	{
		return Services.get(ITrxConstraintsBL.class).getConstraints();
	}

	private Collection<String> getTrxNamesForCurrentThread()
	{
		return getTrxNamesForThreadId(Thread.currentThread().getId());
	}

	private Collection<String> getTrxNamesForThreadId(final long threadId)
	{
		synchronized (threadId2TrxName)
		{
			Collection<String> trxNames = threadId2TrxName.get(threadId);
			if (trxNames == null)
			{
				trxNames = new HashSet<String>();
				threadId2TrxName.put(threadId, trxNames);
			}
			return trxNames;
		}
	}

	private Collection<ITrxSavepoint> getSavepointsForTrxName(final String trxName)
	{
		synchronized (trxName2SavePoint)
		{
			Collection<ITrxSavepoint> savepoints = trxName2SavePoint.get(trxName);
			if (savepoints == null)
			{
				savepoints = new HashSet<ITrxSavepoint>();
				trxName2SavePoint.put(trxName, savepoints);
			}
			return savepoints;
		}
	}

	private String mkStacktraceInfo(final Thread thread, final String beginStacktrace)
	{
		final StringBuilder msgSB = new StringBuilder();
		msgSB.append(">>> Stacktrace at trx creation, commit or rollback:\n");
		msgSB.append(beginStacktrace);
		msgSB.append(">>> Current stacktrace of the creating thread:\n");
		if (thread.isAlive())
		{
			msgSB.append(mkStacktrace(thread));
		}
		else
		{
			msgSB.append("\t(Thread already finished)\n");
		}
		final String msg = msgSB.toString();
		return msg;
	}

	@Override
	public String getCreationStackTrace(final String trxName)
	{
		return trxName2Stacktrace.get(trxName);
	}
}
