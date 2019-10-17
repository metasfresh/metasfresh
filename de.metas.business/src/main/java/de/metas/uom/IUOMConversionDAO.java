package de.metas.uom;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

public interface IUOMConversionDAO extends ISingletonService
{
	UOMConversionsMap getProductConversions(ProductId productId);

	UOMConversionsMap getGenericConversions();
	
	void createUOMConversion(CreateUOMConversionRequest request);
}
