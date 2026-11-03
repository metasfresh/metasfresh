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

package de.metas.inventory.process;

import de.metas.handlingunits.inventory.InventoryService;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_Inventory_Update_QtyCount_to_QtyBook extends JavaProcess implements IProcessPrecondition
{
	@NonNull private static final AdMessageKey MSG_M_INVENTORY_UPDATE_QTYCOUNT_TO_QTYBOOK_ProcessedDoc = AdMessageKey.of("M_Inventory_Update_CountQty_to_BookQty_ProcessedDoc");

	@NonNull private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (context.isProcessedDocument().isTrue())
		{
			return ProcessPreconditionsResolution.reject(MSG_M_INVENTORY_UPDATE_QTYCOUNT_TO_QTYBOOK_ProcessedDoc);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());
		inventoryService.setQtyCountToQtyBookForInventory(inventoryId);

		return MSG_OK;
	}

}
