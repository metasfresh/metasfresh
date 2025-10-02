package de.metas.inventory.mobileui.job.repository;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class ProductInfo
{
	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull ITranslatableString productName;
}
