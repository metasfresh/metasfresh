package de.metas.inventory.mobileui.deps.warehouse;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService
{
	@NonNull private final IWarehouseDAO dao = Services.get(IWarehouseDAO.class);

	public WarehousesLoadingCache newLoadingCache()
	{
		return WarehousesLoadingCache.builder()
				.dao(dao)
				.build();
	}
}
