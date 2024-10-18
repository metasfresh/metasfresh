package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.product.POSProductCategory;
import de.metas.pos.product.POSProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonProductCategory
{
	@NonNull POSProductCategoryId id;
	@NonNull String name;
	@Nullable String description;
	boolean hasImage;

	public static List<JsonProductCategory> fromCollection(@NonNull Collection<POSProductCategory> productCategories)
	{
		return productCategories.stream()
				.map(JsonProductCategory::from)
				.sorted(Comparator.comparing(JsonProductCategory::getName))
				.collect(ImmutableList.toImmutableList());
	}

	public static JsonProductCategory from(@NonNull POSProductCategory productCategory)
	{
		return builder()
				.id(productCategory.getId())
				.name(productCategory.getName())
				.description(productCategory.getDescription())
				.hasImage(productCategory.getImageId() != null)
				.build();
	}
}
