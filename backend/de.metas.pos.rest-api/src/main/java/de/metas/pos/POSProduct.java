package de.metas.pos;

import de.metas.currency.Amount;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSProduct
{
	@NonNull ProductId id;
	@NonNull ITranslatableString name;
	@NonNull Amount price;
	@NonNull ITranslatableString currencySymbol;
	@NonNull UomId uomId;
	@NonNull String uomSymbol;

	public String getName(@NonNull final String adLanguage) {return name.translate(adLanguage);}

	public String getCurrencySymbol(@NonNull final String adLanguage) {return currencySymbol.translate(adLanguage);}
}
