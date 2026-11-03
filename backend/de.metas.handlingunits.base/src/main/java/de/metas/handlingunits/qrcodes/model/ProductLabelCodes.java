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
	@Nullable GTIN gtin;
	@Nullable EAN13 ean13;

	public boolean isEmpty() {return productNo == null && gtin == null && ean13 == null;}
}
