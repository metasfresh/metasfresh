package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/** The one product a scanned label identifies, together with the parsed label */
@Value
@Builder
class LabelProduct
{
	@NonNull IHUQRCode code;
	@NonNull ProductId productId;
}
