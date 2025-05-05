/*
 * #%L
 * de.metas.business
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

package de.metas.warehouseassignment;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product_Warehouse;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductWarehouseAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	Optional<ProductWarehouseAssignments> getByProductId(@NonNull final ProductId productId)
	{
		return Optional.of(getWarehouseIds(productId))
				.filter(warehouseIds -> !warehouseIds.isEmpty())
				.map(warehouseIds -> ProductWarehouseAssignments.builder()
						.productId(productId)
						.warehouseIds(warehouseIds)
						.build());
	}

	void save(@NonNull final ProductWarehouseAssignments productWarehouseAssignment)
	{
		final ProductId productId = productWarehouseAssignment.getProductId();
		final ImmutableSet<WarehouseId> storedWarehouseIds = getWarehouseIds(productWarehouseAssignment.getProductId());

		final ImmutableSet<WarehouseId> warehouseIdsToDelete = storedWarehouseIds
				.stream()
				.filter(storedWarehouseId -> !productWarehouseAssignment.isWarehouseAssigned(storedWarehouseId))
				.collect(ImmutableSet.toImmutableSet());

		delete(productId, warehouseIdsToDelete);

		productWarehouseAssignment.getWarehouseIds()
				.stream()
				.filter(warehouseId -> !storedWarehouseIds.contains(warehouseId))
				.forEach(warehouseId -> insert(productId, warehouseId));

	}

	private void delete(@NonNull final ProductId productId, @NonNull final ImmutableSet<WarehouseId> warehouseIds)
	{
		queryBL.createQueryBuilder(I_M_Product_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Warehouse.COLUMNNAME_M_Product_ID, productId)
				.addInArrayFilter(I_M_Product_Warehouse.COLUMNNAME_M_Warehouse_ID, warehouseIds)
				.create()
				.deleteDirectly();
	}

	private void insert(
			@NonNull final ProductId productId,
			@NonNull final WarehouseId warehouseId)
	{
		final I_M_Product_Warehouse productWarehouseRecord = InterfaceWrapperHelper.newInstance(I_M_Product_Warehouse.class);

		productWarehouseRecord.setM_Product_ID(productId.getRepoId());
		productWarehouseRecord.setM_Warehouse_ID(warehouseId.getRepoId());

		saveRecord(productWarehouseRecord);
	}

	@NonNull
	private ImmutableSet<WarehouseId> getWarehouseIds(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Warehouse.COLUMNNAME_M_Product_ID, productId)
				.stream()
				.map(I_M_Product_Warehouse::getM_Warehouse_ID)
				.map(WarehouseId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
