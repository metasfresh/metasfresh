package de.metas.pos.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.pos.product.POSProduct;
import de.metas.pos.product.POSProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonProduct
{
	@NonNull ProductId id;
	@NonNull String name;
	@NonNull BigDecimal price;
	@NonNull String currencySymbol;
	@NonNull UomId uomId;
	@NonNull String uomSymbol;
	@Nullable UomId catchWeightUomId;
	@Nullable String catchWeightUomSymbol;
	@Nullable BigDecimal catchWeight;
	@NonNull TaxCategoryId taxCategoryId;
	@NonNull Set<POSProductCategoryId> categoryIds;

	public static JsonProduct from(@NonNull final POSProduct product, @NonNull final String adLanguage)
	{
		return JsonProduct.builder()
				.id(product.getId())
				.name(product.getName(adLanguage))
				.price(product.getPrice().toBigDecimal())
				.currencySymbol(product.getCurrencySymbol(adLanguage))
				.uomId(product.getUom().getUomId())
				.uomSymbol(product.getUom().getUomSymbol())
				.catchWeightUomId(product.getCatchWeightUom() != null ? product.getCatchWeightUom().getUomId() : null)
				.catchWeightUomSymbol(product.getCatchWeightUom() != null ? product.getCatchWeightUom().getUomSymbol() : null)
				.catchWeight(product.getCatchWeight())
				.taxCategoryId(product.getTaxCategoryId())
				.categoryIds(product.getCategoryIds())
				.build();
	}

	public static List<JsonProduct> fromList(@NonNull final List<POSProduct> list, @NonNull final String adLanguage)
	{
		return list.stream()
				.map(product -> from(product, adLanguage))
				.sorted(Comparator.comparing(JsonProduct::getName))
				.collect(ImmutableList.toImmutableList());
	}
}
