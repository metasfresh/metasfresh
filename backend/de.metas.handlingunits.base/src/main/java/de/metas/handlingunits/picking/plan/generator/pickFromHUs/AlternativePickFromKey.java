package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value(staticConstructor = "of")
public class AlternativePickFromKey
{
	@NonNull LocatorId locatorId;
	@NonNull HuId huId;
	@NonNull ProductId productId;
}
