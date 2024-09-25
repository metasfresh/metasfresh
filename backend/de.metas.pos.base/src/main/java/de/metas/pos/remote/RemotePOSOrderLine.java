package de.metas.pos.remote;

import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class RemotePOSOrderLine
{
	@NonNull String uuid;

	@NonNull ProductId productId;
	@NonNull String productName;
	@NonNull TaxCategoryId taxCategoryId;

	@NonNull BigDecimal price;

	@NonNull BigDecimal qty;
	@NonNull UomId uomId;

	@Nullable BigDecimal catchWeight;
	@Nullable UomId catchWeightUomId;
}
