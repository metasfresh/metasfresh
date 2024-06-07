/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit;

import com.google.common.collect.ImmutableMap;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class QtyDemandSupplyRepository
{
	@NonNull
	public ProductWithDemandSupplyCollection getByProductIds(@NonNull final Collection<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ProductWithDemandSupplyCollection.of(ImmutableMap.of());
		}

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT * FROM getQtyDemandQtySupply( p_M_Product_IDs :=" + DB.TO_ARRAY(productIds, sqlParams) + ")";

		return DB.retrieveRows(sql, sqlParams, QtyDemandSupplyRepository::ofResultSet)
				.stream()
				.collect(ProductWithDemandSupplyCollection.toProductsWithDemandSupply());
	}

	@NonNull
	private static ProductWithDemandSupply ofResultSet(@NonNull final ResultSet rs) throws SQLException
	{
		return ProductWithDemandSupply.builder()
				.warehouseId(WarehouseId.ofRepoIdOrNull(rs.getInt("M_Warehouse_ID")))
				.attributesKey(AttributesKey.ofString(rs.getString("AttributesKey")))
				.productId(ProductId.ofRepoId(rs.getInt("M_Product_ID")))
				.uomId(UomId.ofRepoId(rs.getInt("C_UOM_ID")))
				.qtyReserved(rs.getBigDecimal("QtyReserved"))
				.qtyToMove(rs.getBigDecimal("QtyToMove"))
				.build();
	}
}
