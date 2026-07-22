package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * A shipment schedule has an unfinished picking order iff it is referenced (via {@link I_M_Picking_Job_Line}
 * OR {@link I_M_Picking_Job_Step}) by a {@code Drafted} {@link I_M_Picking_Job}. Completed/Voided jobs (and
 * schedules with no picking job at all) do not count.
 */
class ShipmentSchedulePickingInfoServiceTest
{
	private PickingJobRepository pickingJobRepository;
	private ShipmentSchedulePickingInfoService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		pickingJobRepository = PickingJobRepository.newInstanceForUnitTesting();
		service = new ShipmentSchedulePickingInfoService(pickingJobRepository);
	}

	private int createPickingJob(@NonNull final PickingJobDocStatus docStatus)
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setDocStatus(docStatus.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);
		return job.getM_Picking_Job_ID();
	}

	private void createLineReferencingSchedule(final int pickingJobId, @NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setM_Picking_Job_ID(pickingJobId);
		line.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);
	}

	private void createStepReferencingSchedule(final int pickingJobId, @NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_Picking_Job_Step step = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
		step.setM_Picking_Job_ID(pickingJobId);
		step.setM_ShipmentSchedule_ID(scheduleId.getRepoId());
		step.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(step);
	}

	@Test
	void draftedJob_referencedViaLine_scheduleIsReturned()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(201);
		final int jobId = createPickingJob(PickingJobDocStatus.Drafted);
		createLineReferencingSchedule(jobId, scheduleId);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result)
				.as("a Drafted job referencing the schedule via M_Picking_Job_Line must be reported as unfinished picking")
				.containsExactly(scheduleId);
	}

	@Test
	void draftedJob_referencedViaStep_scheduleIsReturned()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(202);
		final int jobId = createPickingJob(PickingJobDocStatus.Drafted);
		createStepReferencingSchedule(jobId, scheduleId);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result)
				.as("a Drafted job referencing the schedule via M_Picking_Job_Step must be reported as unfinished picking")
				.containsExactly(scheduleId);
	}

	@Test
	void completedJob_scheduleIsNotReturned()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(203);
		final int jobId = createPickingJob(PickingJobDocStatus.Completed);
		createLineReferencingSchedule(jobId, scheduleId);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result)
				.as("a Completed job must NOT block the schedule")
				.isEmpty();
	}

	@Test
	void voidedJob_scheduleIsNotReturned()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(204);
		final int jobId = createPickingJob(PickingJobDocStatus.Voided);
		createStepReferencingSchedule(jobId, scheduleId);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result)
				.as("a Voided job must NOT block the schedule")
				.isEmpty();
	}

	@Test
	void noPickingJob_scheduleIsNotReturned()
	{
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(205);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(ImmutableSet.of(scheduleId));

		Assertions.assertThat(result)
				.as("a schedule with no picking job at all must NOT be reported as unfinished picking")
				.isEmpty();
	}

	@Test
	void multipleSchedules_onlyTheOffendingSubsetIsReturned()
	{
		final ShipmentScheduleId busyScheduleId = ShipmentScheduleId.ofRepoId(206);
		final ShipmentScheduleId finishedScheduleId = ShipmentScheduleId.ofRepoId(207);
		final ShipmentScheduleId noJobScheduleId = ShipmentScheduleId.ofRepoId(208);

		final int draftedJobId = createPickingJob(PickingJobDocStatus.Drafted);
		createLineReferencingSchedule(draftedJobId, busyScheduleId);

		final int completedJobId = createPickingJob(PickingJobDocStatus.Completed);
		createLineReferencingSchedule(completedJobId, finishedScheduleId);

		final Set<ShipmentScheduleId> result = service.retrieveScheduleIdsWithUnfinishedPicking(
				ImmutableSet.of(busyScheduleId, finishedScheduleId, noJobScheduleId));

		Assertions.assertThat(result)
				.as("only the schedule with a Drafted picking job must be returned")
				.containsExactly(busyScheduleId);
	}
}
