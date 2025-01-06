package de.metas.quantity;

import de.metas.product.ProductId;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UomId;
import lombok.NonNull;

@FunctionalInterface
public interface UOMConversionRateProvider
{
	UOMConversionRate getRate(@NonNull ProductId productId, @NonNull UomId fromUomId, @NonNull UomId toUomId);
}
