package de.metas.uom;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

public interface IUOMConversionDAO extends ISingletonService
{
	UOMConversionsMap getProductConversions(ProductId productId);

	UOMConversionsMap getProductConversionsOrNull(ProductId productId);

	UOMConversionsMap getGenericConversions();
	
	void createUOMConversion(CreateUOMConversionRequest request);
}
