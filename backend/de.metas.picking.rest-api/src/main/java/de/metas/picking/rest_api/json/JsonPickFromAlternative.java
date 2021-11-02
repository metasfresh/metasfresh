package de.metas.picking.rest_api.json;

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
	@NonNull String huBarcode;
	@NonNull String uom;
	@NonNull BigDecimal qtyAvailable;

	public static JsonPickFromAlternative of(@NonNull PickingJobPickFromAlternative from)
	{
		return builder()
				.id(from.getId().getAsString())
				.huBarcode(from.getPickFromHUBarcode().getAsString())
				.uom(from.getQtyAvailable().getUOMSymbol())
				.qtyAvailable(from.getQtyAvailable().toBigDecimal())
				.build();
	}
}
