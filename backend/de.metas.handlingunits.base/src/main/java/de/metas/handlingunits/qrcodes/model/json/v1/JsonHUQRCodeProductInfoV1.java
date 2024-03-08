package de.metas.handlingunits.qrcodes.model.json.v1;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonHUQRCodeProductInfoV1
{
	@NonNull ProductId id;
	@NonNull String code;
	@NonNull String name;
}
