package de.metas.inventory.mobileui.mappers;

import de.metas.inventory.mobileui.deps.products.ProductService;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryJsonMapperService
{
	@NonNull private final WarehouseService warehouseService;
	@NonNull private final ProductService productService;

	public InventoryJsonMapper newMapper(@NonNull final JsonOpts jsonOpts)
	{
		return InventoryJsonMapper.builder()
				.warehouses(warehouseService.newLoadingCache())
				.products(productService.newLoadingCache())
				.adLanguage(jsonOpts.getAdLanguage())
				.build();
	}
}
