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

import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.compiere.model.I_M_InventoryLine;

public class M_Inventory_Update_CountQty_to_BookQty extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
			return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(MSG_M_INVENTORY_UPDATE_QTYCOUNT_TO_QTYBOOK_ProcessedDoc));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());

		// update M_InventoryLine
		final ICompositeQueryUpdater<I_M_InventoryLine> updaterInventoryLine = queryBL.createCompositeQueryUpdater(I_M_InventoryLine.class)
				.addSetColumnFromColumn(I_M_InventoryLine.COLUMNNAME_QtyCount, ModelColumnNameValue.forColumnName(I_M_InventoryLine.COLUMNNAME_QtyBook));

		queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.create().update(updaterInventoryLine);

		// update M_InventoryLine_HU
		final ICompositeQueryUpdater<I_M_InventoryLine_HU> updaterInventoryLineHU = queryBL.createCompositeQueryUpdater(I_M_InventoryLine_HU.class)
				.addSetColumnFromColumn(I_M_InventoryLine_HU.COLUMNNAME_QtyCount, ModelColumnNameValue.forColumnName(I_M_InventoryLine_HU.COLUMNNAME_QtyBook));

		queryBL.createQueryBuilder(I_M_InventoryLine_HU.class)
				.addEqualsFilter(I_M_InventoryLine_HU.COLUMNNAME_M_Inventory_ID, inventoryId)
				.create().update(updaterInventoryLineHU);

		return MSG_OK;
	}

}
