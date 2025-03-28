package de.metas.handlingunits.shipping;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreatePackageForHURequest
{
	@NonNull I_M_HU hu;
	@NonNull ShipperId shipperId;

	public static CreatePackageForHURequestBuilder builderFrom(@NonNull final I_M_HU hu)
	{
		return builder().hu(hu);
	}
}
