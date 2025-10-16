package de.metas.inventory.mobileui.rest_api.mappers;

import de.metas.inventory.mobileui.deps.handlingunits.HandlingUnitsService;
import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mappers
{
	@NonNull private final WarehouseService warehouseService;
	@NonNull private final ProductService productService;
	@NonNull private final HandlingUnitsService handlingUnitsService;

	public JsonInventoryJobMapper newJsonInventoryJobMapper(@NonNull final JsonOpts jsonOpts)
	{
		return JsonInventoryJobMapper.builder()
				.warehouses(warehouseService.newLoadingCache())
				.products(productService.newLoadingCache())
				.handlingUnits(handlingUnitsService.newLoadingCache())
				.adLanguage(jsonOpts.getAdLanguage())
				.build();
	}

	public JsonResolveHUResponseMapper newJsonResolveHUResponse(@NonNull final JsonOpts jsonOpts)
	{
		return JsonResolveHUResponseMapper.builder()
				.warehouses(warehouseService.newLoadingCache())
				.products(productService.newLoadingCache())
				.handlingUnits(handlingUnitsService.newLoadingCache())
				//
				.adLanguage(jsonOpts.getAdLanguage())
				//
				.build();
	}
}
