package de.metas.handlingunits.rest_api.move_hu;

import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

public class MoveHUCommand
{
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private final HuId huId;
	private final HUQRCode huQRCode;
	private final GlobalQRCode targetQRCode;

	public MoveHUCommand(@NonNull final MoveHURequest request)
	{
		this.huId = request.getHuId();
		this.huQRCode = request.getHuQRCode();
		this.targetQRCode = request.getTargetQRCode();
	}

	public void execute()
	{
		if (LocatorQRCode.isTypeMatching(targetQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(targetQRCode);

			huMovementBL.moveHUs(HUMovementGenerateRequest.builder()
					.toLocatorId(locatorQRCode.getLocatorId())
					.huIdToMove(huId)
					.movementDate(SystemTime.asInstant())
					.build());
		}
		else
		{
			throw new AdempiereException("Move target not handled: " + targetQRCode);
		}
	}
}
