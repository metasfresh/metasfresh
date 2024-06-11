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

import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.api.IMovementDAO;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Interceptor(I_M_MovementLine.class)
@Component
@RequiredArgsConstructor
public class M_MovementLine
{
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final DDOrderLowLevelDAO ddOrderDAO;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_M_MovementLine.COLUMNNAME_DD_OrderLine_ID)
	public void setDDOrderLineQtyDelivered(@NonNull final I_M_MovementLine movementLineRecord)
	{
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoIdOrNull(movementLineRecord.getDD_OrderLine_ID());
		if (ddOrderLineId == null)
		{
			return;
		}

		trxManager.runAfterCommit(() -> {
			final BigDecimal movementQty = movementDAO.retrieveMovementQtyForDDOrderLine(ddOrderLineId);
			final I_DD_OrderLine ddOrderLineRecord = ddOrderDAO.getLineById(ddOrderLineId);
			ddOrderLineRecord.setQtyDelivered(movementQty);

			saveRecord(ddOrderLineRecord);
		});
	}
}
