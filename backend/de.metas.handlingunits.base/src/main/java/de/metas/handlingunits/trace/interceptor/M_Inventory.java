/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.trace.interceptor;

import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_M_Inventory.class)
@Component
class M_Inventory
{

	private IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	private final HUTraceEventsService huTraceEventsService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public M_Inventory(@NonNull final HUTraceEventsService huTraceEventsService)
	{
		this.huTraceEventsService = huTraceEventsService;
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_CLOSE,
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_UNCLOSE,
			ModelValidator.TIMING_AFTER_VOID
	}, afterCommit = true)
	public void addTraceEvent(@NonNull final I_M_Inventory inventory)
	{
		Services.get(ITrxManager.class).runInNewTrx(() -> addTraceEvent0(inventory));
	}

	private void addTraceEvent0(@NonNull final I_M_Inventory inventory)
	{

		final List<I_M_InventoryLine> inventoryLines = inventoryDAO.retrieveLinesForInventoryId(InventoryId.ofRepoIdOrNull(inventory.getM_Inventory_ID()));

		huTraceEventsService.createAndAddFor(inventory, inventoryLines);
	}
}