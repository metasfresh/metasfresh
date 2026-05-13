package de.metas.workflow.rest_api.service;

import com.google.common.collect.HashBiMap;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrolleyService
{
	private static final String SYSCONFIG_INCLUDE_HOLDER_NAME = "de.metas.workflow.rest_api.TrolleyService.IncludeHolderNameInConflictError";

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
			final UserId currentHolderUserId = userId2locator.inverse().get(locatorQRCode);
			if (currentHolderUserId != null && !currentHolderUserId.equals(userId))
			{
				throw buildTrolleyAlreadyAssignedException(currentHolderUserId, locatorQRCode);
			}
			userId2locator.put(userId, locatorQRCode);
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

	private TrolleyAlreadyAssignedException buildTrolleyAlreadyAssignedException(
			@NonNull final UserId currentHolderUserId,
			@NonNull final LocatorQRCode locatorQRCode)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean includeHolderName = sysConfigBL.getBooleanValue(SYSCONFIG_INCLUDE_HOLDER_NAME, false);
		if (includeHolderName)
		{
			final String holderName = lookupHolderDisplayName(currentHolderUserId);
			if (holderName != null && !holderName.trim().isEmpty())
			{
				return TrolleyAlreadyAssignedException.forNamedConflict(holderName, locatorQRCode);
			}
		}
		return TrolleyAlreadyAssignedException.forGenericConflict(locatorQRCode);
	}

	@Nullable
	private String lookupHolderDisplayName(@NonNull final UserId userId)
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		return userDAO.retrieveUserFullName(userId);
	}
}
