package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Guards the {@code M_ShipmentSchedule_ID IN (...)} bind-parameter overflow fix on
 * {@link PickingJobRepository#getScheduleIdsWithDraftedPickingJob(Set)}: the full user close-selection must not be
 * folded into a single {@code IN (?,?,...)} filter, which overflows the PostgreSQL/JDBC 2-byte bind-parameter limit
 * (max 32767) once the set exceeds that many entries. The chunking overload
 * {@code getScheduleIdsWithDraftedPickingJob(scheduleIds, maxIdsPerChunk)} partitions the set to stay under the cap;
 * this test drives that overload directly (with a tiny cap) rather than reproducing >32767 rows. Mirrors
 * {@code de.metas.picking.job_schedule.repository.PickingJobScheduleRepositoryChunkingTest}.
 */
class PickingJobRepositoryChunkingTest
{
	private static final int ORG_ID = 1;

	private PickingJobRepository pickingJobRepository;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		pickingJobRepository = PickingJobRepository.newInstanceForUnitTesting();
	}

	private void createDraftedPickingJobLine(@NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setAD_Org_ID(ORG_ID);
		job.setDocStatus(PickingJobDocStatus.Drafted.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);

		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setAD_Org_ID(ORG_ID);
		line.setM_Picking_Job_ID(job.getM_Picking_Job_ID());
		line.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);
	}

	/**
	 * 5 schedules with a drafted picking-job line + 1 schedule with none — the chunked call (chunk size 2, well
	 * below the 6-id total) must return exactly the same set as an oversized single-chunk call (10 000): no loss,
	 * no duplication, and the schedule without a drafted job stays excluded.
	 */
	@Test
	void getScheduleIdsWithDraftedPickingJob_chunked_isEquivalentToUnchunked()
	{
		final ImmutableList<ShipmentScheduleId> scheduleIdsWithDraftedJob = ImmutableList.of(
				ShipmentScheduleId.ofRepoId(700001),
				ShipmentScheduleId.ofRepoId(700002),
				ShipmentScheduleId.ofRepoId(700003),
				ShipmentScheduleId.ofRepoId(700004),
				ShipmentScheduleId.ofRepoId(700005));
		scheduleIdsWithDraftedJob.forEach(this::createDraftedPickingJobLine);

		final ShipmentScheduleId scheduleIdWithoutJob = ShipmentScheduleId.ofRepoId(700006);

		final ImmutableSet<ShipmentScheduleId> queryIds = ImmutableSet.<ShipmentScheduleId>builder()
				.addAll(scheduleIdsWithDraftedJob)
				.add(scheduleIdWithoutJob)
				.build();

		final ImmutableSet<ShipmentScheduleId> chunked = pickingJobRepository.getScheduleIdsWithDraftedPickingJob(queryIds, 2);
		final ImmutableSet<ShipmentScheduleId> unchunked = pickingJobRepository.getScheduleIdsWithDraftedPickingJob(queryIds, 10_000);

		assertThat(chunked)
				.as("chunked result must contain exactly the same rows as the unchunked result (no loss, no duplication)")
				.isEqualTo(unchunked);
		assertThat(chunked)
				.as("only schedules with a drafted picking job are returned")
				.containsExactlyInAnyOrderElementsOf(scheduleIdsWithDraftedJob);
	}

	/** The chunk-size cap must stay strictly positive and within the JDBC 2-byte bind-param limit. */
	@Test
	void maxShipmentScheduleIdsPerQuery_staysUnderJdbc2ByteParamLimit()
	{
		assertThat(PickingJobRepository.MAX_SHIPMENT_SCHEDULE_IDS_PER_QUERY)
				.isGreaterThan(0)
				.isLessThanOrEqualTo(32767);
	}

	/** A schedule-id set at or below the cap takes the fast path — identical to the plain (unchunked) call. */
	@Test
	void getScheduleIdsWithDraftedPickingJob_withSetSizeAtOrBelowCap_isEquivalentToPlainCall()
	{
		final ImmutableList<ShipmentScheduleId> scheduleIds = ImmutableList.of(
				ShipmentScheduleId.ofRepoId(800001),
				ShipmentScheduleId.ofRepoId(800002),
				ShipmentScheduleId.ofRepoId(800003));
		scheduleIds.forEach(this::createDraftedPickingJobLine);

		final Set<ShipmentScheduleId> queryIds = ImmutableSet.copyOf(scheduleIds);

		final ImmutableSet<ShipmentScheduleId> capped = pickingJobRepository.getScheduleIdsWithDraftedPickingJob(queryIds, 10_000);
		final ImmutableSet<ShipmentScheduleId> plain = pickingJobRepository.getScheduleIdsWithDraftedPickingJob(queryIds);

		assertThat(capped).as("fast-path equivalence: cap far above set size behaves like the plain call").isEqualTo(plain);
	}
}
