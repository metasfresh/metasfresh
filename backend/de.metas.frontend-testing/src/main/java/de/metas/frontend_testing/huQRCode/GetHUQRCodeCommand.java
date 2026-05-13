package de.metas.frontend_testing.huQRCode;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

/**
 * Resolves a masterdata identifier (e.g. {@code "lu1"}) to its handling-unit
 * global QR code string. Used by Playwright tests that need to scan an HU
 * which was created indirectly (typically by a picking flow) and therefore
 * has no QR code in the {@code handlingUnits} section of the masterdata response.
 */
public class GetHUQRCodeCommand
{
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final JsonGetHUQRCodeRequest request;

	@Builder
	private GetHUQRCodeCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final JsonGetHUQRCodeRequest request)
	{
		this.huQRCodesService = huQRCodesService;
		this.request = request;
	}

	public JsonGetHUQRCodeResponse execute()
	{
		final MasterdataContext context = buildContext();
		final HuId huId = context.getId(request.getIdentifier(), HuId.class);
		final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId);

		return JsonGetHUQRCodeResponse.builder()
				.huId(huId.getRepoId())
				.qrCode(qrCode.toGlobalQRCodeString())
				.build();
	}

	private MasterdataContext buildContext()
	{
		final MasterdataContext context = new MasterdataContext();

		final JsonCreateMasterdataResponse masterdata = request.getMasterdata();
		if (masterdata != null)
		{
			masterdata.getHandlingUnits().forEach((identifierStr, handlingUnit) ->
					context.putIdentifier(Identifier.ofString(identifierStr), HuId.ofObject(handlingUnit.getHuId())));
			masterdata.getWarehouses().forEach((identifierStr, warehouse) ->
					context.putIdentifier(Identifier.ofString(identifierStr), WarehouseId.ofRepoId(warehouse.getWarehouseId())));
		}

		context.putFromJson(request.getContext());

		return context;
	}
}
