package de.metas.product.asidata;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Domain entity representing a row from {@code M_Product_ASI_Data}.
 * <p>
 * Holds product identification fields (GTINs, product numbers, descriptions)
 * that are specific to an attribute set instance and optionally a business partner.
 */
@Value
@Builder
public class ProductASIData
{
	@Nullable String gtin;
	@Nullable String eanCU;
	@Nullable String upc;
	@Nullable String productNo;
	@Nullable String productName;
	@Nullable String productDescription;
	int seqNo;
}
