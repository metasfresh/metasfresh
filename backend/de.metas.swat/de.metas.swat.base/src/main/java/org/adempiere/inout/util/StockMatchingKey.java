package org.adempiere.inout.util;

import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.Set;

@Value
@Builder
public class StockMatchingKey
{
	@NonNull ProductId productId;
	@NonNull WarehouseId warehouseId;
	@NonNull AttributesKey attributesKey;

	public boolean matching(@NonNull final StockDataQuery query)
	{
		//
		// Product
		if (!ProductId.equals(query.getProductId(), productId))
		{
			return false;
		}

		//
		// Warehouse
		final Set<WarehouseId> queryWarehouseIds = query.getWarehouseIds();
		if (!queryWarehouseIds.isEmpty() && !queryWarehouseIds.contains(warehouseId))
		{
			return false;
		}

		//
		// Attributes
		final boolean queryMatchesAll = query.getStorageAttributesKey().isAll();
		final boolean queryMatchesStockDetail = AttributesKey.equals(query.getStorageAttributesKey(), attributesKey);
		return queryMatchesAll || queryMatchesStockDetail;
	}

	public boolean includes(@NonNull final StockMatchingKey otherMatchingKey)
	{
		return ProductId.equals(productId, otherMatchingKey.getProductId())
				&& WarehouseId.equals(warehouseId, otherMatchingKey.getWarehouseId())
				&& (attributesKey.isAll() || AttributesKey.equals(attributesKey, otherMatchingKey.getAttributesKey()));
	}

}
