package de.metas.workflow.rest_api.service;

import com.google.common.collect.HashBiMap;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrolleyService
{
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IUserDAO userDAO = Services.get(IUserDAO.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;
	@NonNull private final List<TrolleyReleaseListener> releaseListeners;

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
			final UserId currentHolderUserId = userId2locator.inverse().get(locatorQRCode);
			if (currentHolderUserId != null && !currentHolderUserId.equals(userId))
			{
				final String holderName = userDAO.retrieveUserFullName(currentHolderUserId);
				throw TrolleyAlreadyAssignedException.forNamedConflict(holderName, locatorQRCode);
			}
			userId2locator.put(userId, locatorQRCode);
		}

		return locatorQRCode;
	}

	public void clearCurrent(@NonNull final UserId userId)
	{
		trxManager.runInNewTrx(() -> {
			for (final TrolleyReleaseListener listener : releaseListeners)
			{
				listener.onTrolleyReleased(userId);
			}
			synchronized (userId2locator)
			{
				userId2locator.remove(userId);
			}
		});
	}

	public Optional<LocatorQRCode> getCurrent(@NonNull final UserId userId)
	{
		synchronized (userId2locator)
		{
			return Optional.ofNullable(userId2locator.get(userId));
		}
	}
}
