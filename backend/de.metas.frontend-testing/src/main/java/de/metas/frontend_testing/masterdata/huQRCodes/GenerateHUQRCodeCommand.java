package de.metas.frontend_testing.masterdata.huQRCodes;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class GenerateHUQRCodeCommand
{
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private MasterdataContext context;
	@NonNull private final JsonGenerateHUQRCodeRequest request;
	@NonNull private final Identifier identifier;

	public JsonGenerateHUQRCodeResponse execute()
	{
		final List<HUQRCode> huQRCodes = huQRCodesService.generate(HUQRCodeGenerateRequest.builder()
				.count(1)
				.huPackingInstructionsId(context.getId(request.getPackingInstructions(), HuPackingInstructionsId.class))
				.productId(context.getId(request.getProduct(), ProductId.class))
				.build());
		final HUQRCode huQRCode = CollectionUtils.singleElement(huQRCodes);

		return JsonGenerateHUQRCodeResponse.builder()
				.qrCode(huQRCode.toGlobalQRCodeString())
				.build();
	}
}
