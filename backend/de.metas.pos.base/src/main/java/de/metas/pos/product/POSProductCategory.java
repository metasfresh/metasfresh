package de.metas.pos.product;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class POSProductCategory
{
	@NonNull POSProductCategoryId id;
	@NonNull String name;
	@Nullable String description;
}
