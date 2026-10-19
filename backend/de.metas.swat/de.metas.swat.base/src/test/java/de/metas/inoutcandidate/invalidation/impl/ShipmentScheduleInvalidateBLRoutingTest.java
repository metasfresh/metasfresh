package de.metas.inoutcandidate.invalidation.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;
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
	void committedInactiveThreadTrx_flagsOutOfTrx() throws Exception
	{
		final String trxName = trxManager.createTrxName("test-committed", true);
		final ITrx trx = trxManager.getTrx(trxName);
		trx.start();
		trx.commit(true);
		assertThat(trxManager.isActive(trx)).as("setup: committed trx is inactive").isFalse();

		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			bl.flagForRecomputeStorageSegment(segment);
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}

		verify(repo, times(1)).invalidateStorageSegments(any(), isNull());
		verify(repo, never()).invalidateStorageSegmentsInThreadInheritedTrx(any());
	}
}
