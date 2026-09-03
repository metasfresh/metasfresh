package de.metas.distribution.mobileui.external_services.product;

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
