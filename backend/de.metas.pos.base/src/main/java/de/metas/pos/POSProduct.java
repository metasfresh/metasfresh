package de.metas.pos;

import de.metas.currency.Amount;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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

	@NonNull TaxCategoryId taxCategoryId;

	public String getName(@NonNull final String adLanguage) {return name.translate(adLanguage);}

	public String getCurrencySymbol(@NonNull final String adLanguage) {return currencySymbol.translate(adLanguage);}

	@Value(staticConstructor = "of")
	public static class UomIdAndSymbol
	{
		@NonNull UomId uomId;
		@NonNull String uomSymbol;
	}
}
