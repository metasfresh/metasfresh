package de.metas.picking.rest_api.json;

import de.metas.gs1.GS1ProductCodes;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonPickingJobLineProductCodes
{
	@Nullable GTIN gtin;
	@Nullable EAN13 ean13;
	@Nullable EAN13ProductCode ean13ProductCode;

	@Nullable
	public static JsonPickingJobLineProductCodes ofNullable(@Nullable final GS1ProductCodes from)
	{
		return from != null && !from.isEmpty()
				? of(from)
				: null;
	}

	@NonNull
	public static JsonPickingJobLineProductCodes of(@NonNull final GS1ProductCodes from)
	{
		return builder()
				.gtin(from.getGtin())
				.ean13(from.getEan13())
				.ean13ProductCode(from.getEan13ProductCode())
				.build();
	}
}
