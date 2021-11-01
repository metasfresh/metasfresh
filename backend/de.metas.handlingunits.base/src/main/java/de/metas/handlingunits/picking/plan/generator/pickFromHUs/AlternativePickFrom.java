package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class AlternativePickFrom
{
	@NonNull AlternativePickFromKey key;
	@NonNull Quantity availableQty;
}
