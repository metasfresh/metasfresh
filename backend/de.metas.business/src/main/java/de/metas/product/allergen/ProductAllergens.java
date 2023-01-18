package de.metas.product.allergen;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.Set;

@Value
@Builder
public class ProductAllergens
{
	@NonNull ProductId productId;
	@With
	@NonNull ImmutableSet<AllergenId> allergenIds;

	public boolean hasAllergen(@NonNull final AllergenId allergenId)
	{
		return allergenIds.contains(allergenId);
	}

	@NonNull
	public ProductAllergens addAllergens(@NonNull final Set<AllergenId> toAdd)
	{
		final ImmutableSet.Builder<AllergenId> mergedAllergenIds = ImmutableSet.builder();
		mergedAllergenIds.addAll(allergenIds);
		mergedAllergenIds.addAll(toAdd);

		return withAllergenIds(mergedAllergenIds.build());
	}
}
