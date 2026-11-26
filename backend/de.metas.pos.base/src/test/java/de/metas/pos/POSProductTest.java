package de.metas.pos;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.TranslatableStrings;
import de.metas.pos.POSProduct.UomIdAndSymbol;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link POSProduct#getPriceUom()}: which UOM a product's {@link POSProduct#getPrice()} — and any qty stated
 * against it, e.g. a POS return line — are expressed in. Needed by the {@code /api/v2/pos/returns} endpoint,
 * which derives the return line's price and UOM server-side (the client sends product + qty only, never a
 * price) via {@link POSProductsService}.
 */
class POSProductTest
{
	@Test
	void priceUom_isCatchWeightUom_whenProductIsPricedByCatchWeight()
	{
		final UomIdAndSymbol uom = UomIdAndSymbol.of(UomId.ofRepoId(1), "Stk");
		final UomIdAndSymbol catchWeightUom = UomIdAndSymbol.of(UomId.ofRepoId(2), "kg");

		final POSProduct product = newProduct(uom, catchWeightUom);

		assertThat(product.getPriceUom()).isEqualTo(catchWeightUom);
	}

	@Test
	void priceUom_isProductUom_whenProductHasNoCatchWeight()
	{
		final UomIdAndSymbol uom = UomIdAndSymbol.of(UomId.ofRepoId(1), "Stk");

		final POSProduct product = newProduct(uom, null);

		assertThat(product.getPriceUom()).isEqualTo(uom);
	}

	private static POSProduct newProduct(final UomIdAndSymbol uom, final UomIdAndSymbol catchWeightUom)
	{
		return POSProduct.builder()
				.id(ProductId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("product"))
				.price(Amount.of(new BigDecimal("15.50"), CurrencyCode.EUR))
				.currencySymbol(TranslatableStrings.anyLanguage("€"))
				.uom(uom)
				.catchWeightUom(catchWeightUom)
				.taxCategoryId(TaxCategoryId.ofRepoId(1))
				.build();
	}
}
