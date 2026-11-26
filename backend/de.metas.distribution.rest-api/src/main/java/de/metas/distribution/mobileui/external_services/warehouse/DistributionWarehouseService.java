package de.metas.distribution.mobileui.external_services.warehouse;

import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.service.TrolleyService;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.Locator;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseRepository;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DistributionWarehouseService
{
	@NonNull private final WarehouseRepository warehouseRepository;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final TrolleyService trolleyService;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseRepository.getWarehouseName(warehouseId);
	}

	public String getLocatorName(@NonNull final LocatorId locatorId)
	{
		return warehouseRepository.getLocatorNameById(locatorId);
	}

	@NonNull
	public Optional<Workplace> getWorkplaceByUserId(@NonNull final UserId userId)
	{
		return workplaceService.getWorkplaceByUserId(userId);
	}

	@NonNull
	public Set<LocatorId> getAllPackingPlacePickFromLocatorIds()
	{
		return workplaceService.getAllPackingPlacePickFromLocatorIds();
	}

	public WarehouseInfo getWarehouseInfoByRepoId(final int warehouseRepoId)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRepoId);
		return WarehouseInfo.builder()
				.warehouseId(warehouseId)
				.caption(warehouseRepository.getWarehouseName(warehouseId))
				.build();
	}

	public LocatorInfo getLocatorInfoByRepoId(final int locatorRepoId)
	{
		final Locator locator = warehouseRepository.getLocatorByRepoId(locatorRepoId);
		return toLocatorInfo(locator);
	}

	public LocatorInfo getLocatorInfoById(@NonNull final LocatorId locatorId)
	{
		final Locator locator = warehouseRepository.getLocatorById(locatorId);
		return toLocatorInfo(locator);
	}

	private static LocatorInfo toLocatorInfo(final Locator locator)
	{
		return LocatorInfo.builder()
				.locatorId(locator.getLocatorId())
				.qrCode(locator.getQrCode())
				.caption(locator.getValue())
				.priorityNo(locator.getPriorityNo())
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
