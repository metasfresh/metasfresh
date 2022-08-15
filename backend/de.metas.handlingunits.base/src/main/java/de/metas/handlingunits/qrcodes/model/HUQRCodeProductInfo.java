package de.metas.handlingunits.qrcodes.model;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class HUQRCodeProductInfo
{
	@NonNull ProductId id;
	@NonNull String code;
	@NonNull String name;
}
