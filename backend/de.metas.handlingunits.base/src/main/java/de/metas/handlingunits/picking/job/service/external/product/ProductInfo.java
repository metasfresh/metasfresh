package de.metas.handlingunits.picking.job.service.external.product;

import de.metas.gs1.GS1ProductCodesCollection;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductCategoryId;
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
	@NonNull GS1ProductCodesCollection gs1ProductCodes;
	@NonNull ProductCategoryId productCategoryId;
	@NonNull ITranslatableString name;
}
