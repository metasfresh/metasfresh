package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.springframework.stereotype.Service;

/** HU Manager "create HU from a scanned label": the preview (writes nothing) and OK (one single-HU inventory, one CU). */
@Service
@RequiredArgsConstructor
public class CreateHUFromLabelService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUQtyService huQtyService;
	@NonNull private final LabelTargetLocatorResolver labelTargetLocatorResolver;

	/** @return the command with the services set; the caller sets the scanned code, client, org, user and language, then {@code build().execute()} */
	@NonNull
	public ProposeHUFromScannedCodeCommand.ProposeHUFromScannedCodeCommandBuilder propose()
	{
		return ProposeHUFromScannedCodeCommand.builder()
				.huQRCodesService(huQRCodesService)
				.labelTargetLocatorResolver(labelTargetLocatorResolver);
	}

	/** @return the new HU, with its HU QR code created (so the standard actions, Move etc., work on it) */
	@NonNull
	public CreatedHU create(@NonNull final JsonCreateHUFromScannedCodeRequest request, @NonNull final ClientId clientId)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final HuId huId = CreateHUFromScannedCodeCommand.builder()
					.huQRCodesService(huQRCodesService)
					.huQtyService(huQtyService)
					.clientId(clientId)
					.scannedCode(request.getScannedCode())
					.qty(request.getQty())
					.locatorId(LocatorQRCode.ofGlobalQRCodeJsonString(request.getLocatorQRCode()).getLocatorId())
					.build()
					.execute();
			final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId); // creates the QR code if missing, like the HU lookup by value does
			return new CreatedHU(huId, qrCode);
		});
	}

	@Value
	public static class CreatedHU
	{
		@NonNull HuId huId;
		@NonNull HUQRCode qrCode;
	}
}
