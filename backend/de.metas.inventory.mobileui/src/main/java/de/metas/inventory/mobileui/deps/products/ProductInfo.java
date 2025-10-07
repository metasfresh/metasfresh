package de.metas.inventory.mobileui.deps.products;

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
	@NonNull String productNo;
	@NonNull ITranslatableString productName;
}
