package de.metas.inventory.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_Product;


/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class InventoryDAO implements IInventoryDAO
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return load(inventoryId, I_M_Inventory.class);
	}

	@Override
	public I_M_InventoryLine getLineById(@NonNull final InventoryLineId inventoryLineId)
	{
		return load(inventoryLineId, I_M_InventoryLine.class);
	}

	@Override
	public boolean hasLines(@NonNull final InventoryId inventoryId)
	{
		return queryLinesForInventoryId(inventoryId)
				.create()
				.anyMatch();
	}

	@Override
	public <T extends I_M_InventoryLine> List<T> retrieveLinesForInventoryId(
			@NonNull final InventoryId inventoryId,
			@NonNull final Class<T> type)
	{
		return queryLinesForInventoryId(inventoryId)
				.create()
				.list(type);
	}

	private IQueryBuilder<I_M_InventoryLine> queryLinesForInventoryId(@NonNull final InventoryId inventoryId)
	{
		return queryBL
				.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.orderBy(I_M_InventoryLine.COLUMN_Line)
				.orderBy(I_M_InventoryLine.COLUMN_M_InventoryLine_ID);
	}

	@Override
	public void setInventoryLinesProcessed(@NonNull final InventoryId inventoryId, final boolean processed)
	{
		queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.addNotEqualsFilter(I_M_InventoryLine.COLUMNNAME_Processed, processed)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_M_InventoryLine.COLUMNNAME_Processed, processed)
				.execute();
	}

	@Override
	public List<ProductId> retrieveUsedProductsByInventoryIds(@NonNull final Collection<Integer> invetoryIds)
	{
		if (invetoryIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addInArrayFilter(I_M_InventoryLine.COLUMN_M_Inventory_ID, invetoryIds)
				.andCollectChildren(I_M_InventoryLine.COLUMNNAME_M_Product_ID, I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.listDistinct(I_M_Product.COLUMNNAME_M_Product_ID,  ProductId.class)
				;
	}


	@Override
	public void save(I_M_InventoryLine inventoryLine)
	{
		saveRecord(inventoryLine);
	}
}
