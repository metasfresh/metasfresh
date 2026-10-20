package de.metas.inoutcandidate.invalidation.impl;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
 * <p>
 * Also pins the bounded retry: flags released by a failed batch get one retry pass with the next generation, unless the failed
 * batch's generation already reached the cap.
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
		doReturn(0).when(repository).releaseRecomputeMarkerOutOfTrx(any());
	}

	@Test
	void noTrx_releasesImmediately()
	{
		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void openTrx_releasesAfterCommit_notBefore()
	{
		trxManager.runInThreadInheritedTrx(() -> {
			repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
			verify(repository, never()).releaseRecomputeMarkerOutOfTrx(any());
		});

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
	}

	@Test
	void openTrx_releasesAfterRollback_notBefore()
	{
		assertThatThrownBy(() -> trxManager.runInThreadInheritedTrx(() -> {
			repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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

		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);
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
			return 0;
		}).when(repository).releaseRecomputeMarkerOutOfTrx(any());

		final ITrx trx = trxManager.getTrx(trxManager.createTrxName("test-release-retry", true));
		trxManager.setThreadInheritedTrxName(trx.getTrxName());
		repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0);

		trx.commit(true);
		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(succeeded.get()).as("the release failed after the commit").isZero();

		trx.close();

		verify(repository, times(2)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(succeeded.get()).as("retried at close, succeeded once").isEqualTo(1);
	}

	private static List<I_C_Queue_WorkPackage> recomputeWorkpackages()
	{
		return POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class).stream()
				.filter(wp -> UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getName().equals(wp.getC_Queue_PackageProcessor().getClassname()))
				.collect(Collectors.toList());
	}

	private static int retryGenerationOf(final I_C_Queue_WorkPackage workpackage)
	{
		return Services.get(IWorkpackageParamDAO.class)
				.retrieveWorkpackageParams(workpackage)
				.getParameterAsInt(UpdateInvalidShipmentSchedulesWorkpackageProcessor.PARAM_RecomputeRetryGeneration, 0);
	}

	private void failBatchOfGeneration(final int retryGeneration)
	{
		assertThatThrownBy(() -> trxManager.runInThreadInheritedTrx(() -> {
			repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, retryGeneration);
			throw new IllegalStateException("force rollback");
		})).hasMessageContaining("force rollback");
	}

	/** a failed recompute workpackage is not retried by the async framework: the flags it released get a retry pass, or they wait for an unrelated change */
	@Test
	void failedBatch_generation0_releasedFlags_enqueuesOneRetryPassOfGeneration1()
	{
		doReturn(2).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		failBatchOfGeneration(0);

		final List<I_C_Queue_WorkPackage> workpackages = recomputeWorkpackages();
		assertThat(workpackages).hasSize(1);
		assertThat(retryGenerationOf(workpackages.get(0))).isEqualTo(1);
	}

	@Test
	void failedBatch_generationBelowCap_enqueuesOneRetryPassOfGenerationCap()
	{
		final int cap = UpdateInvalidShipmentSchedulesWorkpackageProcessor.DEFAULT_MaxRetriesAfterFailedBatch;
		doReturn(1).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		failBatchOfGeneration(cap - 1);

		final List<I_C_Queue_WorkPackage> workpackages = recomputeWorkpackages();
		assertThat(workpackages).hasSize(1);
		assertThat(retryGenerationOf(workpackages.get(0))).isEqualTo(cap);
	}

	/** a poison schedule fails every batch; at the cap the chain ends and the flags wait for the next business change */
	@Test
	void failedBatch_generationAtCap_enqueuesNothing()
	{
		final int cap = UpdateInvalidShipmentSchedulesWorkpackageProcessor.DEFAULT_MaxRetriesAfterFailedBatch;
		doReturn(1).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		failBatchOfGeneration(cap);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(recomputeWorkpackages()).isEmpty();
	}

	@Test
	void failedBatch_capFromSysConfig()
	{
		Services.get(ISysConfigBL.class).setValue(UpdateInvalidShipmentSchedulesWorkpackageProcessor.SYSCONFIG_MaxRetriesAfterFailedBatch, 1, ClientId.SYSTEM, OrgId.ANY);
		doReturn(1).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		failBatchOfGeneration(1);

		assertThat(recomputeWorkpackages()).isEmpty();
	}

	/** a failed batch whose flags were all gone already (nothing released) needs no retry pass */
	@Test
	void failedBatch_nothingReleased_enqueuesNothing()
	{
		doReturn(0).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		failBatchOfGeneration(0);

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(recomputeWorkpackages()).isEmpty();
	}

	/** after a successful batch its flags were deleted in its trx: nothing is released and nothing is enqueued */
	@Test
	void successfulBatch_nothingReleased_enqueuesNothing()
	{
		doReturn(0).when(repository).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);

		trxManager.runInThreadInheritedTrx(() -> repository.releaseRecomputeMarkersAfterTrxEnd(SELECTION_ID, 0));

		verify(repository, times(1)).releaseRecomputeMarkerOutOfTrx(SELECTION_ID);
		assertThat(recomputeWorkpackages()).isEmpty();
	}
}
