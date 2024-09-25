package de.metas.pos;

import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class POSOrderLine
{
	@NonNull String externalId;

	@NonNull ProductId productId;
	@NonNull String productName;
	@NonNull TaxCategoryId taxCategoryId;
	@NonNull TaxId taxId;
	@NonNull Quantity qty;
	@Nullable Quantity catchWeight;
	@NonNull Money price;
	@NonNull Money amount;
	@NonNull Money taxAmt;

	@Builder(toBuilder = true)
	private POSOrderLine(
			@NonNull final String externalId,
			@NonNull final ProductId productId,
			@NonNull final String productName,
			@NonNull final TaxCategoryId taxCategoryId,
			@NonNull final TaxId taxId,
			@NonNull final Quantity qty, 
			@Nullable final Quantity catchWeight,
			@NonNull final Money price,
			@NonNull final Money amount,
			@NonNull Money taxAmt)
	{
		Money.assertSameCurrency(price, amount, taxAmt);
		
		this.externalId = externalId;
		this.productId = productId;
		this.productName = productName;
		this.taxCategoryId = taxCategoryId;
		this.taxId = taxId;
		this.qty = qty;
		this.catchWeight = catchWeight;
		this.price = price;
		this.amount = amount;

		this.taxAmt = taxAmt;
	}

	public Money getLineTotalAmt(final boolean isTaxIncluded)
	{
		return isTaxIncluded ? amount : amount.add(taxAmt);
	}
}
