package de.metas.distribution.workflows_api;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ProductInfo
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString caption;
}
