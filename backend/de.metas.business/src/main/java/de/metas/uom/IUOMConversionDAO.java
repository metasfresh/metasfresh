package de.metas.uom;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IUOMConversionDAO extends ISingletonService
{
	@NonNull
	UOMConversionsMap getProductConversions(@NonNull ProductId productId);

	UOMConversionsMap getGenericConversions();
	
	void createUOMConversion(@NonNull CreateUOMConversionRequest request);

	void updateUOMConversion(@NonNull UpdateUOMConversionRequest request);
}
