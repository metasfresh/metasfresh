package de.metas.pos;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSProduct
{
	@NonNull ProductId id;
	@NonNull ITranslatableString name;

	public String getName(@NonNull final String adLanguage) {return name.translate(adLanguage);}
}
