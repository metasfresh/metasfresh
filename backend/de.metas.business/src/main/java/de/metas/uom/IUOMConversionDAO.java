package de.metas.uom;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IUOMConversionDAO extends ISingletonService
{
	UOMConversionsMap getProductConversions(ProductId productId);

	UOMConversionsMap getGenericConversions();
	
	void createUOMConversion(@NonNull CreateUOMConversionRequest request);

	void updateUOMConversion(@NonNull UpdateUOMConversionRequest request);
}
