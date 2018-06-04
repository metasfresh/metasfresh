package org.adempiere.uom.api;

import lombok.Value;

@Value
/* package */final class UOMConversionContext implements IUOMConversionContext
{
	private final int productId;

	public UOMConversionContext(final int productId)
	{
		this.productId = productId > 0 ? productId : -1;
	}
}
