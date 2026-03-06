package de.metas.workflow.rest_api.service;

import com.google.common.collect.HashBiMap;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrolleyService
{
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	private final HashBiMap<UserId, LocatorQRCode> userId2locator = HashBiMap.create();

	public LocatorQRCode setCurrent(@NonNull final UserId userId, @NonNull final ScannedCode scannedCode)
	{
		final LocatorQRCode locatorQRCode = locatorScannedCodeResolver.resolve(scannedCode).getLocatorQRCode();
		final LocatorId locatorId = locatorQRCode.getLocatorId();
		final I_M_Warehouse warehouse = warehouseDAO.getById(locatorId.getWarehouseId());
		if (!warehouse.isInTransit())
		{
			throw new AdempiereException("Warehouse is not in transit"); // TODO trl
		}

		synchronized (userId2locator)
		{
			userId2locator.forcePut(userId, locatorQRCode);
		}

		return locatorQRCode;
	}

	public Optional<LocatorQRCode> getCurrent(@NonNull final UserId userId)
	{
		synchronized (userId2locator)
		{
			return Optional.ofNullable(userId2locator.get(userId));
		}
	}
}
