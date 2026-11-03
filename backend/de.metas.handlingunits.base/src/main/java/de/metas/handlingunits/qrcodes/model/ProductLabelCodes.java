package de.metas.handlingunits.qrcodes.model;

import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * What a product label says about its product: the product number, the GTIN and/or the EAN-13, any of them may be absent.
 * See {@link IHUQRCode#getProductLabelCodes()}.
 */
@Value
@Builder
public class ProductLabelCodes
{
	@Nullable String productNo;
	@Nullable GTIN GTIN;
	@Nullable EAN13 EAN13;

	public boolean isEmpty() {return productNo == null && GTIN == null && EAN13 == null;}
}
