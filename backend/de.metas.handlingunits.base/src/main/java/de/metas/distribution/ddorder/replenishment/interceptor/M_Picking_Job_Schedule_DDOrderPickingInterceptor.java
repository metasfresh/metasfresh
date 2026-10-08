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
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.repository.PickingJobScheduleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Drives the DD_Order picking-replenishment flow off the workstation assignment
 * ({@link I_M_Picking_Job_Schedule}) — the assignment of a shipment-schedule line to a picking workplace
 * is what now creates / updates / voids the replenishment DD_Order (it replaced the former
 * {@code M_ShipmentSchedule}-driven trigger).
 *
 * <ul>
 *   <li>{@code beforeChange}: sync picker-busy guard — refuse changing an assignment whose DD_Order has an
 *       active picking job.</li>
 *   <li>{@code afterNew} / {@code afterChange}: schedule an after-commit reconcile for the assignment
 *       (create or recreate the DD_Order).</li>
 *   <li>{@code afterDelete}: void + unlink the existing DD_Order SYNCHRONOUSLY in the current (delete)
 *       transaction — there is nothing left to pick at that workstation. This must NOT be deferred to
 *       after-commit: the deferrable FK {@code mpickingjobschedule_ddorder} (DD_Order/DD_OrderLine →
 *       M_Picking_Job_Schedule) is checked at the delete-trx commit, so the linked DD_Order has to be
 *       voided and unlinked before the assignment row is flushed.</li>
 * </ul>
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void scheduleReconcileAfterCommit(@NonNull final I_M_Picking_Job_Schedule record)
	{
		replenishmentService.scheduleReconcileAfterCommit(PickingJobScheduleRepository.fromRecord(record));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void voidDDOrderOnDelete(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		replenishmentService.voidDDOrdersForDeletedAssignment(
				PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID()));
	}
}
