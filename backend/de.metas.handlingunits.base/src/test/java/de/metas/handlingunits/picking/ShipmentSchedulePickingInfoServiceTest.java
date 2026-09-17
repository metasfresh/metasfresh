package de.metas.handlingunits.picking;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
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
 * <p>
 * Exercises the {@link IShipmentSchedulePickingInfoService#newUnfinishedPickingFilter() filter} the service
 * exposes: it is applied to a plain {@code M_ShipmentSchedule} query (mirroring how the close process folds it
 * into its selection query) and the matched schedules are asserted.
 */
class ShipmentSchedulePickingInfoServiceTest
{
	private IQueryBL queryBL;
	private ShipmentSchedulePickingInfoService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		queryBL = Services.get(IQueryBL.class);
		service = new ShipmentSchedulePickingInfoService(PickingJobRepository.newInstanceForUnitTesting());
	}

	private int createShipmentSchedule()
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(schedule);
		return schedule.getM_ShipmentSchedule_ID();
	}

	private int createPickingJob(@NonNull final PickingJobDocStatus docStatus)
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setDocStatus(docStatus.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);
		return job.getM_Picking_Job_ID();
	}

	private void createLineReferencingSchedule(final int pickingJobId, final int scheduleId)
	{
		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setM_Picking_Job_ID(pickingJobId);
		line.setM_ShipmentSchedule_ID(scheduleId);
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);
	}

	private void createStepReferencingSchedule(final int pickingJobId, final int scheduleId)
	{
		final I_M_Picking_Job_Step step = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Step.class);
		step.setM_Picking_Job_ID(pickingJobId);
		step.setM_ShipmentSchedule_ID(scheduleId);
		step.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(step);
	}

	/** @return the ids of all {@code M_ShipmentSchedule} rows matched by the service's unfinished-picking filter. */
	private Set<Integer> schedulesWithUnfinishedPicking()
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.filter(service.newUnfinishedPickingFilter())
				.create()
				.stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Test
	void draftedJob_referencedViaLine_scheduleMatches()
	{
		final int scheduleId = createShipmentSchedule();
		createLineReferencingSchedule(createPickingJob(PickingJobDocStatus.Drafted), scheduleId);

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("a Drafted job referencing the schedule via M_Picking_Job_Line must be reported as unfinished picking")
				.containsExactly(scheduleId);
	}

	@Test
	void draftedJob_referencedViaStep_scheduleMatches()
	{
		final int scheduleId = createShipmentSchedule();
		createStepReferencingSchedule(createPickingJob(PickingJobDocStatus.Drafted), scheduleId);

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("a Drafted job referencing the schedule via M_Picking_Job_Step must be reported as unfinished picking")
				.containsExactly(scheduleId);
	}

	@Test
	void completedJob_scheduleDoesNotMatch()
	{
		final int scheduleId = createShipmentSchedule();
		createLineReferencingSchedule(createPickingJob(PickingJobDocStatus.Completed), scheduleId);

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("a Completed job must NOT block the schedule")
				.isEmpty();
	}

	@Test
	void voidedJob_scheduleDoesNotMatch()
	{
		final int scheduleId = createShipmentSchedule();
		createStepReferencingSchedule(createPickingJob(PickingJobDocStatus.Voided), scheduleId);

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("a Voided job must NOT block the schedule")
				.isEmpty();
	}

	@Test
	void noPickingJob_scheduleDoesNotMatch()
	{
		createShipmentSchedule();

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("a schedule with no picking job at all must NOT be reported as unfinished picking")
				.isEmpty();
	}

	@Test
	void multipleSchedules_onlyTheOffendingSubsetMatches()
	{
		final int busyScheduleId = createShipmentSchedule();
		final int finishedScheduleId = createShipmentSchedule();
		createShipmentSchedule(); // a schedule with no picking job at all

		createLineReferencingSchedule(createPickingJob(PickingJobDocStatus.Drafted), busyScheduleId);
		createLineReferencingSchedule(createPickingJob(PickingJobDocStatus.Completed), finishedScheduleId);

		Assertions.assertThat(schedulesWithUnfinishedPicking())
				.as("only the schedule with a Drafted picking job must be reported")
				.containsExactly(busyScheduleId);
	}
}
