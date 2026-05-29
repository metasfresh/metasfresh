package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.service.TrolleyService;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistributionWarehouseService
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final TrolleyService trolleyService;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getWarehouseName(warehouseId);
	}

	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return warehouseBL.getLocatorNameById(locatorId);
	}

	@NonNull
	public Optional<Workplace> getWorkplaceByUserId(@NonNull final UserId userId)
	{
		return workplaceService.getWorkplaceByUserId(userId);
	}

	public WarehouseInfo getWarehouseInfoByRepoId(final int warehouseRepoId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRepoId);
		return WarehouseInfo.builder()
				.warehouseId(warehouseId)
				.caption(warehouseBL.getWarehouseName(warehouseId))
				.build();
	}

	public LocatorInfo getLocatorInfoByRepoId(final int locatorRepoId)
	{
		final I_M_Locator locator = warehouseBL.getLocatorByRepoId(locatorRepoId);
		final LocatorId locatorId = LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID());
		return LocatorInfo.builder()
				.locatorId(locatorId)
				.qrCode(LocatorQRCode.ofLocator(locator))
				.caption(locator.getValue())
				.build();
	}

	public LocatorScannedCodeResolverResult resolveLocator(@NonNull final ScannedCode scannedCode)
	{
		return locatorScannedCodeResolver.resolve(scannedCode);
	}

	public Optional<LocatorQRCode> getTrolleyByUserId(@NonNull final UserId userId)
	{
		return trolleyService.getCurrent(userId);
	}
}
