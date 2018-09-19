package org.adempiere.uom.api;

import de.metas.product.ProductId;
import lombok.Value;

@Value
/* package */final class UOMConversionContext implements IUOMConversionContext
{
	private final ProductId productId;

	public UOMConversionContext(final ProductId productId)
	{
		this.productId = productId;
	}
}
