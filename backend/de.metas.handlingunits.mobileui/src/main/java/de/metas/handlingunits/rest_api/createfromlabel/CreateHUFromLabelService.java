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
import org.compiere.util.Env;
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

	@NonNull
	public JsonHUProposal propose(@NonNull final JsonProposeHUFromScannedCodeRequest request)
	{
		return ProposeHUFromScannedCodeCommand.builder()
				.huQRCodesService(huQRCodesService)
				.labelTargetLocatorResolver(labelTargetLocatorResolver)
				.clientId(Env.getClientId())
				.userId(Env.getLoggedUserId())
				.orgId(Env.getOrgId())
				.scannedCode(request.getScannedCode())
				.adLanguage(Env.getADLanguageOrBaseLanguage())
				.build()
				.execute();
	}

	/** @return the new HU, with its HU QR code created (so the standard actions, Move etc., work on it) */
	@NonNull
	public CreatedHU create(@NonNull final JsonCreateHUFromScannedCodeRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final HuId huId = request.toCommand(huQRCodesService, huQtyService, Env.getClientId()).execute();
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
