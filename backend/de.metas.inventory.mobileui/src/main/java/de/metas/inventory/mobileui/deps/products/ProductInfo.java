package de.metas.inventory.mobileui.deps.products;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PACKAGE)
public class ProductInfo
{
	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull ITranslatableString productName;
}
