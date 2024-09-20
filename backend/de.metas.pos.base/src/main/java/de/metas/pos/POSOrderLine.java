package de.metas.pos;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class POSOrderLine
{
	@NonNull String externalId;

	@NonNull ProductId productId;
	@NonNull String productName;
	@NonNull TaxCategoryId taxCategoryId;
	@NonNull TaxId taxId;
	@NonNull Quantity qty;
	@NonNull BigDecimal price;
	@NonNull BigDecimal amount;
	@NonNull BigDecimal taxAmt;

	@Builder(toBuilder = true)
	private POSOrderLine(
			@NonNull final String externalId,
			@NonNull final ProductId productId,
			@NonNull final String productName,
			@NonNull final TaxCategoryId taxCategoryId,
			@NonNull final TaxId taxId,
			@NonNull final Quantity qty,
			@NonNull final BigDecimal price,
			@NonNull final BigDecimal amount,
			@NonNull BigDecimal taxAmt)
	{
		this.externalId = externalId;
		this.productId = productId;
		this.productName = productName;
		this.taxCategoryId = taxCategoryId;
		this.taxId = taxId;
		this.qty = qty;
		this.price = price;
		this.amount = amount;

		this.taxAmt = taxAmt;
	}

	public BigDecimal getLineTotalAmt(final boolean isTaxIncluded)
	{
		return isTaxIncluded ? amount : amount.add(taxAmt);
	}
}
