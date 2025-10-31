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

package de.metas.inventory.impl;

import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.inventory.IInventoryLineHUDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.compiere.model.I_M_InventoryLine;

public class InventoryLineHUDAO implements IInventoryLineHUDAO
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void setQtyCountToQtyBookForInventory(@NonNull final InventoryId inventoryId)
	{
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
	}
}
