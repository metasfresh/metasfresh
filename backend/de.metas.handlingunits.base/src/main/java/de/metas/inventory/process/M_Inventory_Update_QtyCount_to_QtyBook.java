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

import de.metas.i18n.AdMessageKey;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineHuRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

public class M_Inventory_Update_QtyCount_to_QtyBook extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	@NonNull private InventoryLineHuRepository inventoryLineHuRepository;

	@NonNull private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	@NonNull private static final AdMessageKey MSG_M_INVENTORY_UPDATE_QTYCOUNT_TO_QTYBOOK_ProcessedDoc = AdMessageKey.of("M_Inventory_Update_CountQty_to_BookQty_ProcessedDoc");

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

		// update M_InventoryLine
		inventoryDAO.setQtyCountToQtyBookForInventoryLines(inventoryId);

		// update M_InventoryLine_HU
		inventoryLineHuRepository.setQtyCountToQtyBookForInventoryLinesHU(inventoryId);

		return MSG_OK;
	}

}
