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

package de.metas.warehouseassignment.repository;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.warehouseassignment.ProductWarehouseAssignmentId;
import de.metas.warehouseassignment.model.ProductWarehouseAssignment;
import de.metas.warehouseassignment.model.ProductWarehouseAssignmentCreateRequest;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product_Warehouse;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductWarehouseAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public int deleteForProductId(@NonNull final ProductId productId)
	{
		return queryByProductId(productId)
				.deleteDirectly();
	}

	@NonNull
	public ImmutableList<ProductWarehouseAssignment> getForProductId(@NonNull final ProductId productId)
	{
		return queryByProductId(productId)
				.iterateAndStream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public void create(@NonNull final ProductWarehouseAssignmentCreateRequest request)
	{
		request.getWarehouseIds()
				.forEach(warehouseId -> save(warehouseId, request.getProductId()));
	}

	@NonNull
	private ProductWarehouseAssignment fromRecord(@NonNull final I_M_Product_Warehouse record)
	{
		return ProductWarehouseAssignment.builder()
				.id(ProductWarehouseAssignmentId.ofRepoId(record.getM_Product_Warehouse_ID()))
				.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.build();
	}

	private void save(
			@NonNull final WarehouseId warehouseId,
			@NonNull final ProductId productId)
	{
		final I_M_Product_Warehouse productWarehouseRecord = InterfaceWrapperHelper.newInstance(I_M_Product_Warehouse.class);

		productWarehouseRecord.setM_Product_ID(productId.getRepoId());
		productWarehouseRecord.setM_Warehouse_ID(warehouseId.getRepoId());

		saveRecord(productWarehouseRecord);
	}

	@NonNull
	private IQuery<I_M_Product_Warehouse> queryByProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Warehouse.COLUMNNAME_M_Product_ID, productId)
				.create();
	}
}
