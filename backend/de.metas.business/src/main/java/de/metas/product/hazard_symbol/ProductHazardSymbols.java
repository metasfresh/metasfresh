package de.metas.product.hazard_symbol;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class ProductHazardSymbols
{
	@NonNull ProductId productId;
	@NonNull ImmutableSet<HazardSymbolId> hazardSymbolIds;
}
