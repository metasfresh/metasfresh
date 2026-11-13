package de.metas.inoutcandidate.invalidation.impl;

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

import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the trx states {@link ThreadInheritedTrxState#isThreadTrxOpenForWrites(ITrx)} distinguishes, on a real (POJO) transaction.
 * <p>
 * Transactions start lazily (on their first DB access), so "inactive" alone does not mean "committed".
 */
class ThreadInheritedTrxStateTest
{
	private ITrxManager trxManager;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
	}

	@Test
	void noTrx_isNotOpen()
	{
		assertThat(ThreadInheritedTrxState.isThreadTrxOpenForWrites(null)).isFalse();
		assertThat(ThreadInheritedTrxState.isThreadTrxOpenForWrites(ITrx.TRX_None)).isFalse();
		assertThat(ThreadInheritedTrxState.isThreadInheritedTrxOpenForWrites()).as("no thread-inherited trx").isFalse();
	}

	@Test
	void activeTrx_isOpen()
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-active", true));
		trx.start();

		assertThat(ThreadInheritedTrxState.isThreadTrxOpenForWrites(trx)).isTrue();
	}

	/** e.g. the trx JavaProcess creates and sets as thread-inherited without starting it; the first write starts it */
	@Test
	void unstartedTrx_isOpen()
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-unstarted", true));
		assertThat(trx.isActive()).as("setup: created, never started").isFalse();

		assertThat(ThreadInheritedTrxState.isThreadTrxOpenForWrites(trx)).isTrue();
	}

	@Test
	void insideAfterCommit_beforeRestart_isNotOpen()
	{
		final AtomicReference<ITrx> trxInCallback = new AtomicReference<>();
		final AtomicReference<Boolean> open = new AtomicReference<>();
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
			final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
			trxInCallback.set(trx);
			open.set(ThreadInheritedTrxState.isThreadInheritedTrxOpenForWrites());
		}));

		assertThat(trxManager.isNull(trxInCallback.get())).as("setup: the committed trx is still thread-inherited in its callback").isFalse();
		assertThat(trxInCallback.get().isActive()).as("setup: committed, not restarted").isFalse();
		assertThat(open.get()).as("committed trx dispatching its AFTER_COMMIT listeners").isFalse();
	}

	@Test
	void insideAfterCommit_afterRestart_isOpen()
	{
		final AtomicReference<Boolean> open = new AtomicReference<>();
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
			// like the real transaction, which restarts itself on the first DB access of the callback
			trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone).start();
			open.set(ThreadInheritedTrxState.isThreadInheritedTrxOpenForWrites());
		}));

		assertThat(open.get()).as("restarted trx writes business data and commits at close()").isTrue();
	}

	@Test
	void insideAfterRollback_isNotOpen()
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-rollback", true));
		trx.start();
		final AtomicReference<Boolean> open = new AtomicReference<>();
		trx.getTrxListenerManager()
				.newEventListener(TrxEventTiming.AFTER_ROLLBACK)
				.registerHandlingMethod(rolledBackTrx -> open.set(ThreadInheritedTrxState.isThreadTrxOpenForWrites(rolledBackTrx)));

		trx.rollback();

		assertThat(open.get()).as("rolled-back trx dispatching its AFTER_ROLLBACK listeners").isFalse();
	}
}
