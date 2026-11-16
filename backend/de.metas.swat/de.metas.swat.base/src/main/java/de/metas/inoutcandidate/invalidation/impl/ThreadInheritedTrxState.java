package de.metas.inoutcandidate.invalidation.impl;

import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Decides whether recompute flags may be written into the thread-inherited transaction, i.e. whether they would commit or roll back
 * with the caller's business change.
 * <p>
 * "Inactive" does not mean "committed": transactions start lazily, on their first DB access (e.g. {@code JavaProcess} sets a created,
 * not yet started trx as thread-inherited). The states, for a thread trx {@code T}:
 * <ul>
 * <li>{@code T} active (started, or restarted after a commit) - open: the flags are written in {@code T}, which commits them with the change.</li>
 * <li>{@code T} never started - open: the flag write starts {@code T}, and the business change follows in it.</li>
 * <li>{@code T} committed (or rolled back, or closed) and dispatching its post-commit listeners, not restarted - NOT open: the change is
 * already committed, and a write would restart {@code T} where it may never be committed.</li>
 * </ul>
 * The last state is told apart from "never started" by the listener timing: while any listener runs (BEFORE_COMMIT, AFTER_COMMIT,
 * AFTER_ROLLBACK, AFTER_CLOSE), no BEFORE_COMMIT listener can be registered; outside listener dispatch it can.
 * <p>
 * Known, accepted edge: the timing API cannot tell BEFORE_COMMIT from AFTER_COMMIT, and BEFORE_COMMIT listeners can run on an INACTIVE trx
 * ({@code AbstractTrx.commit} fires them without starting the trx, and e.g. {@code JavaProcess.endTrx} commits trxs that were never started).
 * Such an inactive trx inside BEFORE_COMMIT is treated as not open, so the flag is written out of trx.
 * Today this is practically unreachable: the only BEFORE_COMMIT registrants touching HU data register after DB reads, which start the trx.
 */
final class ThreadInheritedTrxState
{
	private ThreadInheritedTrxState()
	{
	}

	static boolean isThreadInheritedTrxOpenForWrites()
	{
		return isThreadTrxOpenForWrites(Services.get(ITrxManager.class).getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone));
	}

	/**
	 * @return {@code true} if flags written in the given (thread-inherited) trx commit or roll back with the caller's change; see the class Javadoc
	 */
	static boolean isThreadTrxOpenForWrites(@Nullable final ITrx trx)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (trx == null || trxManager.isNull(trx))
		{
			return false;
		}
		if (trxManager.isActive(trx))
		{
			return true;
		}
		// inactive: never started (open) or in a listener dispatch - post-commit, or (accepted edge, see class Javadoc) BEFORE_COMMIT of a never-started trx (not open)
		return trx.getTrxListenerManager().canRegisterOnTiming(TrxEventTiming.BEFORE_COMMIT);
	}
}
