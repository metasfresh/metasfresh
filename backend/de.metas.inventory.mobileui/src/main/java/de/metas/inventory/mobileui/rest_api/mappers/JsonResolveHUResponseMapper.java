package de.metas.inventory.mobileui.rest_api.mappers;

import de.metas.inventory.mobileui.deps.handlingunits.HULoadingCache;
import de.metas.inventory.mobileui.deps.products.ProductInfo;
import de.metas.inventory.mobileui.deps.products.ProductsLoadingCache;
import de.metas.inventory.mobileui.deps.warehouse.WarehousesLoadingCache;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonAttribute;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHUResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;

@Builder(access = AccessLevel.PACKAGE)
public class JsonResolveHUResponseMapper
{
	@NonNull private final WarehousesLoadingCache warehouses;
	@NonNull private final ProductsLoadingCache products;
	@NonNull private final HULoadingCache handlingUnits;
	@NonNull private final String adLanguage;

	public JsonResolveHUResponse toJson(@NonNull final ResolveHUResponse response)
	{
		final ProductInfo productInfo = products.getById(response.getProductId());

		return JsonResolveHUResponse.builder()
				.scannedCode(response.getScannedCode())
				.lineId(response.getLineId())
				.huId(response.getHuId())
				.huDisplayName(response.getHuId() != null ? handlingUnits.getDisplayName(response.getHuId()) : null)
				.locatorId(response.getLocatorId())
				.locatorName(warehouses.getLocatorName(response.getLocatorId()))
				.productId(response.getProductId())
				.productNo(productInfo.getProductNo())
				.productName(productInfo.getProductName().translate(adLanguage))
				.uom(response.getQtyBooked().getUOMSymbol())
				.qtyBooked(response.getQtyBooked().toBigDecimal())
				.counted(response.isCounted())
				.qtyCount(response.getQtyCount() != null ? response.getQtyCount().toBigDecimal() : null)
				.attributes(JsonAttribute.of(response.getAttributes(), adLanguage))
				.build();
	}
}
