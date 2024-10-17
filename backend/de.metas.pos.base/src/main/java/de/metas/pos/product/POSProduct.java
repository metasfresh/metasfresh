package de.metas.pos.product;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class POSProduct
{
	@NonNull ProductId id;
	@NonNull ITranslatableString name;
	@NonNull Amount price;
	@NonNull ITranslatableString currencySymbol;
	@NonNull UomIdAndSymbol uom;
	@Nullable UomIdAndSymbol catchWeightUom;
	@Nullable @With BigDecimal catchWeight;

	@NonNull TaxCategoryId taxCategoryId;

	@NonNull ImmutableSet<POSProductCategoryId> categoryIds;

	public String getName(@NonNull final String adLanguage) {return name.translate(adLanguage);}

	public String getCurrencySymbol(@NonNull final String adLanguage) {return currencySymbol.translate(adLanguage);}

	@Value(staticConstructor = "of")
	public static class UomIdAndSymbol
	{
		@NonNull UomId uomId;
		@NonNull String uomSymbol;
	}
}
