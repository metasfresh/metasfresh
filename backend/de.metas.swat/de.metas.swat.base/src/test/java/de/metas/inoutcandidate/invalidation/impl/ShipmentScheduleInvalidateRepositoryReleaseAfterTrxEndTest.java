package de.metas.inoutcandidate.invalidation.impl;

import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
 * Pins {@link ShipmentScheduleInvalidateRepository#releaseRecomputeMarkersAfterTrxEnd(PInstanceId)}: with an open thread-inherited trx the
 * release runs only after the trx ended (commit, rollback or close), exactly once; without a trx it runs immediately.
 */
class ShipmentScheduleInvalidateRepositoryReleaseAfterTrxEndTest
{
	private static final PInstanceId SELECTION_ID = PInstanceId.ofRepoId(123);

	private ITrxManager trxManager;
	private ShipmentScheduleInvalidateRepository repository;

	@AfterEach
	void afterEach()
	{
		trxManager.setThreadInheritedTrxName(null);
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);

		repository = Mockito.spy(new ShipmentScheduleInvalidateRepository());
		doNothing().when(repository).releaseRecomputeMarkerOutOfTrx(any());
	}

	@Test
	void noTrx_releasesImmediately()
	{
		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void openTrx_releasesAfterCommit_notBefore()
	{
		trxManager.runInThreadInheritedTrx(() -> {
			repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
			verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());
		});

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void openTrx_releasesAfterRollback_notBefore()
	{
		assertThatThrownBy(() -> trxManager.runInThreadInheritedTrx(() -> {
			repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
			verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());
			throw new IllegalStateException("force rollback");
		})).hasMessageContaining("force rollback");

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	/** e.g. the trx JavaProcess creates and sets as thread-inherited without starting it: AbstractTrx.commit/rollback fire the listeners of such a trx too */
	@Test
	void unstartedTrx_releasesAfterCommit_notBefore() throws Exception
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-unstarted-commit", true));
		assertThat(trx.isActive()).as("setup: created, never started").isFalse();
		trxManager.setThreadInheritedTrxName(trx.getTrxName());

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
		verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());

		trx.commit(true);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void unstartedTrx_releasesAfterRollback_notBefore() throws Exception
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-unstarted-rollback", true));
		assertThat(trx.isActive()).as("setup: created, never started").isFalse();
		trxManager.setThreadInheritedTrxName(trx.getTrxName());
		// the real Trx rolls back a never-started trx successfully (Trx.rollbackNative: no connection -> true); mirror that in the POJO trx
		((PlainTrxManager)trxManager).setFailRollbackIfTrxNotStarted(false);

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
		verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());

		trx.rollback(true);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	/** a failing commit (here: a BEFORE_COMMIT listener throws): AbstractTrx.commit fires no AFTER_COMMIT/AFTER_ROLLBACK, only close() fires AFTER_CLOSE */
	@Test
	void commitFails_thenClose_releasesExactlyOnce() throws Exception
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-commit-fails", true));
		trxManager.setThreadInheritedTrxName(trx.getTrxName());
		trx.getTrxListenerManager().newEventListener(TrxEventTiming.BEFORE_COMMIT)
				.registerHandlingMethod(t -> {
					throw new IllegalStateException("commit fails");
				});

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
		assertThatThrownBy(() -> trx.commit(true)).hasMessageContaining("commit fails");
		verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());

		trx.close();

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	/** a failing rollback (POJO seam: rollbackNative of a never-started trx throws while failRollbackIfTrxNotStarted): no AFTER_ROLLBACK, only close() fires AFTER_CLOSE */
	@Test
	void rollbackFails_thenClose_releasesExactlyOnce()
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-rollback-fails", true));
		trxManager.setThreadInheritedTrxName(trx.getTrxName());
		((PlainTrxManager)trxManager).setFailRollbackIfTrxNotStarted(true);

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
		assertThatThrownBy(() -> trx.rollback(true)).isInstanceOf(RuntimeException.class);
		verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());

		trx.close();

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void commitThenClose_releasesExactlyOnce() throws Exception
	{
		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-commit-close", true));
		trxManager.setThreadInheritedTrxName(trx.getTrxName());

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);
		trx.commit(true);
		trx.close();

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	/** the listeners only log a failing handler; a release that failed after the commit is retried when the trx is closed, and a successful one is not repeated */
	@Test
	void releaseFailsAfterCommit_isRetriedAtClose_andSucceedsOnce() throws Exception
	{
		final AtomicInteger succeeded = new AtomicInteger();
		doThrow(new IllegalStateException("release fails")).doAnswer(invocation -> {
			succeeded.incrementAndGet();
			return null;
		}).when(repository).releaseRecomputeMarkerOutOfTrx(any());

		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-release-retry", true));
		trxManager.setThreadInheritedTrxName(trx.getTrxName());
		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID);

		trx.commit(true);
		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(succeeded.get()).as("the release failed after the commit").isZero();

		trx.close();

		verify(repository, times(2)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(succeeded.get()).as("retried at close, succeeded once").isEqualTo(1);
	}
}
