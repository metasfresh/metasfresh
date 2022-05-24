package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;

@Value(staticConstructor = "of")
public class AlternativePickFrom
{
	@Delegate
	@NonNull AlternativePickFromKey key;
	@NonNull Quantity availableQty;
}
