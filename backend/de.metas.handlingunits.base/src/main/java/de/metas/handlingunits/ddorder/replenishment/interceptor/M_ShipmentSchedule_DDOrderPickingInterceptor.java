/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.ddorder.replenishment.interceptor;

import de.metas.handlingunits.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
@RequiredArgsConstructor
public class M_ShipmentSchedule_DDOrderPickingInterceptor
{
	@NonNull private final DDOrderPickingReplenishmentService reconcileService;

	// Only on CHANGE: a brand-new schedule has no existing DD_Order yet, so there is nothing for the
	// picker-busy guard to protect — and the record's PK is still 0 at BEFORE_NEW, which the BL cannot resolve.
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertCanChange(@NonNull final I_M_ShipmentSchedule schedule)
	{
		reconcileService.assertCanChange(schedule);
	}

	// Only fire reconcile when the columns that drive classifyAction actually changed.
	// Other saves (e.g. qty-delivered updates triggered by DD_Order void) must not re-trigger a CREATE.
	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_M_ShipmentSchedule.COLUMNNAME_IsActive,
					I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override,
					I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID,
					I_M_ShipmentSchedule.COLUMNNAME_Processed,
					I_M_ShipmentSchedule.COLUMNNAME_IsClosed
			})
	public void scheduleReconcileAfterCommit(@NonNull final I_M_ShipmentSchedule schedule)
	{
		reconcileService.scheduleReconcileAfterCommit(schedule);
	}
}
