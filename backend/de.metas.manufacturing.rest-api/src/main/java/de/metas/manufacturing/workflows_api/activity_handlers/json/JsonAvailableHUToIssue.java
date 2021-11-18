package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.handlingunits.HUBarcode;
import de.metas.manufacturing.order.PPOrderAvailableHUToIssue;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonAvailableHUToIssue
{
	@NonNull String huBarcode;
	@NonNull String productId;
	@NonNull String uom;
	@NonNull BigDecimal qtyAvailable;

	public static JsonAvailableHUToIssue of(@NonNull final PPOrderAvailableHUToIssue from)
	{
		return builder()
				.huBarcode(HUBarcode.ofHuId(from.getHuId()).getAsString())
				.productId(String.valueOf(from.getProductId().getRepoId()))
				.uom(from.getAvailableQty().getUOMSymbol())
				.qtyAvailable(from.getAvailableQty().toBigDecimal())
				.build();
	}
}
