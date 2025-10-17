package de.metas.inventory.mobileui.rest_api.mappers;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.products.ProductInfo;
import de.metas.inventory.mobileui.deps.products.ProductsLoadingCache;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseInfo;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.rest_api.json.JsonCountStatus;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJob;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJobLine;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryLineHU;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
public class JsonInventoryJobMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;
	@NonNull private final ProductsLoadingCache products;
	@NonNull private final HULoadingCache handlingUnits;
	@NonNull private final String adLanguage;

	public JsonInventoryJob toJson(final Inventory inventory)
	{
		final WarehouseInfo warehouseInfo = inventory.getWarehouseId() != null ? warehouses.getById(inventory.getWarehouseId()) : null;

		final ImmutableList<JsonInventoryJobLine> lines = inventory.getLines()
				.stream()
				.map(this::toJson)
				.collect(ImmutableList.toImmutableList());

		return JsonInventoryJob.builder()
				.id(inventory.getId())
				.documentNo(inventory.getDocumentNo())
				.movementDate(inventory.getMovementDate().toLocalDate().toString())
				.warehouseName(warehouseInfo != null ? warehouseInfo.getWarehouseName() : "")
				.lines(lines)
				.countStatus(computeCountStatus(lines))
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
				.countStatus(computeCountStatus(line))
				.build();
	}

	private static @NotNull JsonCountStatus computeCountStatus(final ImmutableList<JsonInventoryJobLine> lines)
	{
		return JsonCountStatus.combine(lines, JsonInventoryJobLine::getCountStatus)
				.orElse(JsonCountStatus.COUNTED); // consider counted when there are no lines (shall not happen)
	}

	private static @NonNull JsonCountStatus computeCountStatus(final InventoryLine line)
	{
		return JsonCountStatus.combine(line.getInventoryLineHUs(), JsonInventoryJobMapper::computeCountStatus)
				.orElse(JsonCountStatus.COUNTED);
	}

	private static JsonCountStatus computeCountStatus(final InventoryLineHU lineHU)
	{
		return JsonCountStatus.ofIsCountedFlag(lineHU.isCounted());
	}

	public List<JsonInventoryLineHU> toJsonInventoryLineHUs(@NonNull final InventoryLine line)
	{
		return line.getInventoryLineHUs()
				.stream()
				.filter(lineHU -> lineHU.getId() != null)
				.map(lineHU -> toJson(lineHU, line))
				.collect(ImmutableList.toImmutableList());
	}

	public JsonInventoryLineHU toJson(final InventoryLineHU lineHU, InventoryLine line)
	{
		final ProductInfo product = products.getById(line.getProductId());
		final HuId huId = lineHU.getHuId();

		final String productName = product.getProductName().translate(adLanguage);
		final String huDisplayName = huId != null ? handlingUnits.getDisplayName(huId) : null;

		return JsonInventoryLineHU.builder()
				.id(lineHU.getIdNotNull())
				.caption(CoalesceUtil.firstNotBlank(huDisplayName, productName))
				.productId(product.getProductId())
				.productNo(product.getProductNo())
				.productName(productName)
				.locatorId(line.getLocatorId().getRepoId())
				.locatorName(warehouses.getLocatorName(line.getLocatorId()))
				.huId(huId)
				.uom(lineHU.getUOMSymbol())
				.qtyBooked(lineHU.getQtyBook().toBigDecimal())
				.qtyCount(lineHU.getQtyCount().toBigDecimal())
				.countStatus(computeCountStatus(lineHU))
				.build();
	}
}
