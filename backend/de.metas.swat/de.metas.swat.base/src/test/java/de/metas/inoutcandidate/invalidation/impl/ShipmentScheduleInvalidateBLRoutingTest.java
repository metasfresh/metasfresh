package de.metas.inoutcandidate.invalidation.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Pins how {@link ShipmentScheduleInvalidateBL} routes the storage-segment flag by the thread-inherited transaction.
 */
class ShipmentScheduleInvalidateBLRoutingTest
{
	private ITrxManager trxManager;
	private IShipmentScheduleInvalidateRepository repo;
	private ShipmentScheduleInvalidateBL bl;
	private IShipmentScheduleSegment segment;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);
		repo = Mockito.mock(IShipmentScheduleInvalidateRepository.class);
		Services.registerService(IShipmentScheduleInvalidateRepository.class, repo);
		bl = new ShipmentScheduleInvalidateBL(new PickingBOMService());
		segment = ImmutableShipmentScheduleSegment.builder().productId(1).anyBPartner().build();
	}

	@Test
	void activeThreadTrx_flagsInTrx()
	{
		trxManager.runInThreadInheritedTrx(() -> bl.flagForRecomputeStorageSegment(segment));

		verify(repo, times(1)).invalidateStorageSegmentsInThreadInheritedTrx(any());
		verify(repo, never()).invalidateStorageSegments(any(), any());
	}

	@Test
	void noThreadTrx_flagsOutOfTrx()
	{
		bl.flagForRecomputeStorageSegment(segment);
		bl.flagSegmentForRecompute(ImmutableSet.of(segment));

		verify(repo, times(2)).invalidateStorageSegments(any(), isNull());
		verify(repo, never()).invalidateStorageSegmentsInThreadInheritedTrx(any());
	}

	/** After-commit callbacks run on the committed, now inactive, trx until its first DB access. */
	@Test
	void committedThreadTrxInAfterCommit_flagsOutOfTrx()
	{
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> bl.flagForRecomputeStorageSegment(segment)));

		verify(repo, times(1)).invalidateStorageSegments(any(), isNull());
		verify(repo, never()).invalidateStorageSegmentsInThreadInheritedTrx(any());
	}

	/** Transactions start lazily (e.g. a process trx before its first SQL statement): an unstarted trx is not a committed one. */
	@Test
	void unstartedThreadTrx_flagsInTrx()
	{
		final String trxName = trxManager.createTrxName("test-unstarted", true);
		assertThat(trxManager.getTrx(trxName).isActive()).as("setup: created, never started").isFalse();

		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			bl.flagForRecomputeStorageSegment(segment);
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}

		verify(repo, times(1)).invalidateStorageSegmentsInThreadInheritedTrx(any());
		verify(repo, never()).invalidateStorageSegments(any(), any());
	}

	/** A collector on a committed trx is never flushed, so the notification must flag synchronously and never go async. */
	@Test
	void notifySegmentsChanged_committedThreadTrxInAfterCommit_flagsOutOfTrxSynchronously()
	{
		final ITaskExecutorService taskExecutorService = Mockito.mock(ITaskExecutorService.class);
		Services.registerService(ITaskExecutorService.class, taskExecutorService);
		final ShipmentScheduleInvalidateBL blSpy = Mockito.spy(bl);
		final IShipmentScheduleSegment validSegment = ImmutableShipmentScheduleSegment.builder().productId(1).bpartnerId(2).locatorId(3).build();

		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> blSpy.notifySegmentsChanged(ImmutableSet.of(validSegment))));

		verify(blSpy, times(1)).flagSegmentsForRecomputeOutOfTrx(any());
		verify(taskExecutorService, never()).submit(any(Runnable.class), any(String.class));
	}

	/** An unstarted trx gets the collector, which flags after the trx committed; nothing synchronous, nothing async. */
	@Test
	void notifySegmentsChanged_unstartedThreadTrx_collectsUntilCommit() throws Exception
	{
		final ITaskExecutorService taskExecutorService = Mockito.mock(ITaskExecutorService.class);
		Services.registerService(ITaskExecutorService.class, taskExecutorService);
		final ShipmentScheduleInvalidateBL blSpy = Mockito.spy(bl);
		final IShipmentScheduleSegment validSegment = ImmutableShipmentScheduleSegment.builder().productId(1).bpartnerId(2).locatorId(3).build();

		final String trxName = trxManager.createTrxName("test-unstarted", true);
		final ITrx trx = trxManager.getTrx(trxName);
		assertThat(trx.isActive()).as("setup: created, never started").isFalse();

		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			blSpy.notifySegmentsChanged(ImmutableSet.of(validSegment));

			verify(blSpy, never()).flagSegmentsForRecomputeOutOfTrx(any());
			verify(blSpy, never()).flagSegmentsForRecomputeInThreadInheritedTrx(any());
			verify(taskExecutorService, never()).submit(any(Runnable.class), any(String.class));

			trx.start(); // the business change's first DB access
			trx.commit(true);
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}

		verify(blSpy, times(1)).flagSegmentsForRecomputeOutOfTrx(ImmutableList.of(validSegment));
		verify(taskExecutorService, never()).submit(any(Runnable.class), any(String.class));
	}
}
