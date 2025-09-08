package de.metas.picking.rest_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.job.model.PickingJobPickFromAlternative;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPickFromAlternative
{
	@NonNull String id;
	@NonNull String locatorName;
	@NonNull JsonDisplayableQRCode huQRCode;
	@NonNull String uom;
	@NonNull BigDecimal qtyAvailable;

	public static JsonPickFromAlternative of(@NonNull PickingJobPickFromAlternative from)
	{
		return builder()
				.id(from.getId().getAsString())
				.locatorName(from.getLocatorInfo().getCaption())
				.huQRCode(from.getPickFromHU().getQrCode().toRenderedJson())
				.uom(from.getQtyAvailable().getUOMSymbol())
				.qtyAvailable(from.getQtyAvailable().toBigDecimal())
				.build();
	}
}
