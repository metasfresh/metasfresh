package de.metas.pos;

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

	public String getName(@NonNull final String adLanguage) {return name.translate(adLanguage);}

	public String getCurrencySymbol(@NonNull final String adLanguage) {return currencySymbol.translate(adLanguage);}

	/**
	 * The UOM that {@link #price} — and any quantity stated against it (e.g. a returned quantity) — are
	 * expressed in: the catch-weight UOM (e.g. kg) when the product is priced by catch weight, else the
	 * product's own {@link #uom}. Mirrors {@code POSProductsLoader#toPOSProduct}'s own use of the price row's
	 * {@code C_UOM_ID} for both cases — {@link #price} is always per that UOM, never per {@link #uom} when
	 * {@link #catchWeightUom} is set.
	 */
	public UomIdAndSymbol getPriceUom()
	{
		return catchWeightUom != null ? catchWeightUom : uom;
	}

	@Value(staticConstructor = "of")
	public static class UomIdAndSymbol
	{
		@NonNull UomId uomId;
		@NonNull String uomSymbol;
	}
}
