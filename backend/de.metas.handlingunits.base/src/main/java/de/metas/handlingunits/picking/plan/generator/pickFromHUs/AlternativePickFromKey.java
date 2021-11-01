package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class AlternativePickFromKey
{
	@NonNull HuId huId;
	@NonNull ProductId productId;
}
