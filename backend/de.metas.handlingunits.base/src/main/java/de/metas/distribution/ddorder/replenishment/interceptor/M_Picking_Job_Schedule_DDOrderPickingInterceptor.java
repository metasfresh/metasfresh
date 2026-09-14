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

package de.metas.distribution.ddorder.replenishment.interceptor;

import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Drives the DD_Order picking-replenishment flow off the workstation assignment
 * ({@link I_M_Picking_Job_Schedule}).
 */
@Interceptor(I_M_Picking_Job_Schedule.class)
@Component
@RequiredArgsConstructor
public class M_Picking_Job_Schedule_DDOrderPickingInterceptor
{
	@NonNull private final DDOrderPickingReplenishmentService replenishmentService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertCanChange(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		replenishmentService.assertCanChange(jobSchedule);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void scheduleReconcileAfterCommit(@NonNull final I_M_Picking_Job_Schedule record)
	{
		replenishmentService.scheduleReconcileAfterCommit(PickingJobScheduleRepository.fromRecord(record));
	}

	/** Separate from the afterNew timing: a brand-new assignment has no old values, so asking for the group it left would read a group key of zeroes. */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE })
	public void scheduleReconcileOfAffectedGroupsAfterCommit(@NonNull final I_M_Picking_Job_Schedule record)
	{
		replenishmentService.scheduleReconcileOfAffectedGroupsAfterCommit(record);
	}

	/**
	 * Passed as a domain object, not an id: AFTER_DELETE means the row is already gone, so this record is the last place its delivery can be read from.
	 */
	// Must stay synchronous: the deferrable FK ddorderline_pjs_pickingjobsched is checked at this transaction's commit.
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void voidDDOrderOnDelete(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		replenishmentService.voidDDOrdersForDeletedAssignment(PickingJobScheduleRepository.fromRecord(jobSchedule));
	}
}
