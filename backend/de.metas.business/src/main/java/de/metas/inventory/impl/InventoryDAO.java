package de.metas.inventory.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return load(inventoryId, I_M_Inventory.class);
	}

	@Override
	public I_M_InventoryLine getLineById(@NonNull final InventoryLineId inventoryLineId)
	{
		return getLineById(inventoryLineId, I_M_InventoryLine.class);
	}

	@Override
	public <T extends I_M_InventoryLine> T getLineById(@NonNull final InventoryLineId inventoryLineId, @NonNull Class<T> modelType)
	{
		return load(inventoryLineId, modelType);
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
	public Set<ProductId> retrieveUsedProductsByInventoryIds(@NonNull final Collection<InventoryId> inventoryIds)
	{
		if (inventoryIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<ProductId> productIds = queryBL.createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InventoryLine.COLUMN_M_Inventory_ID, inventoryIds)
				.create()
				.listDistinct(I_M_Product.COLUMNNAME_M_Product_ID, ProductId.class);

		return ImmutableSet.copyOf(productIds);
	}

	@Override
	public Optional<Instant> getMinInventoryDate(@NonNull final Collection<InventoryId> inventoryIds)
	{
		if (inventoryIds.isEmpty())
		{
			return Optional.empty();
		}

		return queryBL.createQueryBuilder(I_M_Inventory.class)
				.addInArrayFilter(I_M_Inventory.COLUMN_M_Inventory_ID, inventoryIds)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Inventory.COLUMN_MovementDate)
				.create()
				.stream()
				.limit(1)
				.map(inventory -> inventory.getMovementDate().toInstant())
				.findFirst();
	}

	@Override
	public void save(I_M_InventoryLine inventoryLine)
	{
		saveRecord(inventoryLine);
	}

	@Override
	public Stream<I_M_Inventory> stream(@NonNull final IQueryFilter<I_M_Inventory> inventoryFilter)
	{
		return queryBL.createQueryBuilder(I_M_Inventory.class)
				.filter(inventoryFilter)
				.create()
				.iterateAndStream();
	}
}
