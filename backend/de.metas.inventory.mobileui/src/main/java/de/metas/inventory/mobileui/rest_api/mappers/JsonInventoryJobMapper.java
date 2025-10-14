package de.metas.inventory.mobileui.rest_api.mappers;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.inventory.mobileui.deps.products.ProductInfo;
import de.metas.inventory.mobileui.deps.products.ProductsLoadingCache;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseInfo;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJob;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJobLine;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

@Builder(access = AccessLevel.PACKAGE)
public class JsonInventoryJobMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;
	@NonNull private final ProductsLoadingCache products;
	@NonNull private final String adLanguage;

	public JsonInventoryJob toJson(final Inventory inventory)
	{
		final WarehouseInfo warehouseInfo = inventory.getWarehouseId() != null ? warehouses.getById(inventory.getWarehouseId()) : null;

		return JsonInventoryJob.builder()
				.id(inventory.getId())
				.documentNo(inventory.getDocumentNo())
				.movementDate(inventory.getMovementDate().toLocalDate().toString())
				.warehouseName(warehouseInfo != null ? warehouseInfo.getWarehouseName() : "")
				.lines(inventory.getLines()
						.stream()
						.map(this::toJson)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonInventoryJobLine toJson(final InventoryLine line)
	{
		final ProductInfo productInfo = products.getById(line.getProductId());

		final LocatorId locatorId = line.getLocatorId();
		final String locatorName = warehouses.getLocatorName(locatorId);

		return JsonInventoryJobLine.builder()
				.id(line.getIdNonNull())
				.caption(productInfo.getProductNo() + "_" + productInfo.getProductName())
				.productId(productInfo.getProductId())
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName().translate(adLanguage))
				.locatorId(locatorId.getRepoId())
				.locatorName(locatorName)
				.uom(line.getUOMSymbol())
				.qtyBooked(line.getQtyBookFixed().toBigDecimal())
				.qtyCount(line.getQtyCountFixed().toBigDecimal())
				.build();
	}

}
