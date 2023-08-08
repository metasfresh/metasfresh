package de.metas.handlingunits.rest_api.move_hu;

import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

public class MoveHUCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final HUTransformService huTransformService;

	private final HuId huId;
	private final HUQRCode huQRCode;
	private final GlobalQRCode targetQRCode;

	@Builder
	private MoveHUCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final MoveHURequest request)
	{
		this.huTransformService = HUTransformService.builder()
				.huQRCodesService(huQRCodesService)
				.build();

		this.huId = request.getHuId();
		this.huQRCode = request.getHuQRCode();
		this.targetQRCode = request.getTargetQRCode();

	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		if (LocatorQRCode.isTypeMatching(targetQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(targetQRCode);

			final HuId topLevelHUId = huTransformService.extractToTopLevelByQRCode(huId, huQRCode);

			huMovementBL.moveHUs(HUMovementGenerateRequest.builder()
					.toLocatorId(locatorQRCode.getLocatorId())
					.huIdToMove(topLevelHUId)
					.movementDate(SystemTime.asInstant())
					.build());
		}
		else
		{
			throw new AdempiereException("Move target not handled: " + targetQRCode);
		}
	}
}
