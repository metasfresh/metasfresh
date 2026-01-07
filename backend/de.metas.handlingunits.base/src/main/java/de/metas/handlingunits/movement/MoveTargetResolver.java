package de.metas.handlingunits.movement;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

class MoveTargetResolver
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	public MoveTarget resolve(@NonNull final ScannedCode target)
	{
		final GlobalQRCode globalQRCode = target.toGlobalQRCodeIfMatching().orNullIfError();

		if (globalQRCode != null)
		{
			if (LocatorQRCode.isTypeMatching(globalQRCode))
			{
				final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(globalQRCode);
				return resolveLocatorQRCode(locatorQRCode);
			}
			else if (HUQRCode.isTypeMatching(globalQRCode))
			{
				return resolveHUQRCode(globalQRCode);
			}
			else
			{
				throw new AdempiereException("Move target not handled: " + globalQRCode);
			}
		}

		//
		// Try searching by locator value
		{
			final LocatorQRCode locatorQRCode = warehouseBL.getLocatorQRCodeByValue(target.getAsString()).orElse(null);
			if (locatorQRCode != null)
			{
				return resolveLocatorQRCode(locatorQRCode);
			}
		}

		throw new AdempiereException("Move target not handled: " + target);

	}

	private MoveTarget resolveLocatorQRCode(final LocatorQRCode locatorQRCode)
	{
		final I_M_Locator locator = warehouseBL.getLocatorById(locatorQRCode.getLocatorId(), I_M_Locator.class);
		return MoveTarget.builder()
				.locatorId(LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID()))
				.isPlaceAggTUsOnNewLUAfterMove(locator.isPlaceAggHUOnNewLU())
				.build();
	}

	private static MoveTarget resolveHUQRCode(final GlobalQRCode globalQRCode)
	{
		return MoveTarget.builder()
				.huQRCode(HUQRCode.fromGlobalQRCode(globalQRCode))
				.isPlaceAggTUsOnNewLUAfterMove(false)
				.build();
	}
}
