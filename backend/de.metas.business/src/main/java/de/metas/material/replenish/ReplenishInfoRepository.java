/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.replenish;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Replenish;
import org.springframework.stereotype.Repository;

@Repository
public class ReplenishInfoRepository
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Convenience method
	 */
	public ReplenishInfo getBy(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return getBy(materialDescriptor.getWarehouseId(), ProductId.ofRepoId(materialDescriptor.getProductId()));
	}

	public ReplenishInfo getBy(@NonNull final WarehouseId warehouseId, @NonNull final ProductId productId)
	{
		final I_M_Replenish replenishRecord = queryBL.createQueryBuilder(I_M_Replenish.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Replenish.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_Replenish.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.create()
				.firstOnly(I_M_Replenish.class);

		final UomId uomId = productBL.getStockUOMId(productId);

		final StockQtyAndUOMQty min;
		final StockQtyAndUOMQty max;
		if (replenishRecord == null)
		{
			min = StockQtyAndUOMQtys.createZero(productId, uomId);
			max = StockQtyAndUOMQtys.createZero(productId, uomId);
		}
		else
		{
			min = StockQtyAndUOMQtys.createConvert(replenishRecord.getLevel_Min(), productId, uomId);
			max = StockQtyAndUOMQtys.createConvert(replenishRecord.getLevel_Max(), productId, uomId);
		}

		return ReplenishInfo.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.min(min)
				.max(max)
				.build();
	}

}
