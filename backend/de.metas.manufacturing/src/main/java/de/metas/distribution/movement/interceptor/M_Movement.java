/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.distribution.movement.interceptor;

import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.MovementId;
import org.compiere.model.I_M_Movement;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Movement.class)
@Component
@RequiredArgsConstructor
public class M_Movement
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final DDOrderLowLevelService ddOrderLowLevelService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_M_Movement.COLUMNNAME_DocStatus)
	public void setDDOrderLineQtyDelivered(@NonNull final I_M_Movement movementRecord)
	{
		trxManager.runAfterCommit(() -> ddOrderLowLevelService.updateQtyDeliveredFromMovement(MovementId.ofRepoId(movementRecord.getM_Movement_ID())));
	}
}
