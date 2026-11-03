package de.metas.inventory.mobileui.deps.warehouse;

import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverRequest;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService
{
	@NonNull private final IWarehouseDAO dao = Services.get(IWarehouseDAO.class);
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	public WarehousesLoadingCache newLoadingCache()
	{
		return WarehousesLoadingCache.builder()
				.dao(dao)
				.build();
	}

	public LocatorScannedCodeResolverResult resolveLocator(@NonNull final LocatorScannedCodeResolverRequest request)
	{
		return locatorScannedCodeResolver.resolve(request);
	}
}
