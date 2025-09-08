package de.metas.product.allergen;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ProductAllergens
{
	@NonNull ProductId productId;
	@NonNull ImmutableSet<AllergenId> allergenIds;
}
