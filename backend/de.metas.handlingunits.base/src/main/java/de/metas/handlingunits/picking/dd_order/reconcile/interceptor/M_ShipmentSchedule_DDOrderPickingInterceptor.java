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

package de.metas.handlingunits.picking.dd_order.reconcile.interceptor;

import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule_DDOrderPickingInterceptor
{
	private final DDOrderPickingReconcileBL reconcileBL;

	public M_ShipmentSchedule_DDOrderPickingInterceptor(@NonNull final DDOrderPickingReconcileBL reconcileBL)
	{
		this.reconcileBL = reconcileBL;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertCanChange(@NonNull final I_M_ShipmentSchedule schedule)
	{
		reconcileBL.assertCanChange(schedule);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void scheduleReconcileAfterCommit(@NonNull final I_M_ShipmentSchedule schedule)
	{
		reconcileBL.scheduleReconcileAfterCommit(schedule);
	}
}
