package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ScaleProductPriceListTest
{
	ScaleProductPrice scalePrice(final String qtyMin, final String price)
	{
		final BigDecimal priceBD = new BigDecimal(price);

		return ScaleProductPrice.builder()
				.quantityMin(new BigDecimal(qtyMin))
				.priceStd(priceBD)
				.priceList(priceBD)
				.priceLimit(priceBD)
				.build();
	}

	ScaleProductPriceList scalePrices(final ScaleProductPrice... prices)
	{
		return ScaleProductPriceList.ofList(ImmutableList.copyOf(prices));
	}

	@Nested
	class getByQuantity
	{
		@Test
		void standardCase()
		{
			final ScaleProductPriceList scalePrices = scalePrices(
					scalePrice("1", "100"),
					scalePrice("10", "200"),
					scalePrice("20", "300")
			);

			assertThat(scalePrices.getByQuantity(new BigDecimal("0"))).isEmpty();
			assertThat(scalePrices.getByQuantity(new BigDecimal("1"))).contains(scalePrice("1", "100"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("9"))).contains(scalePrice("1", "100"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("10"))).contains(scalePrice("10", "200"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("11"))).contains(scalePrice("10", "200"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("19"))).contains(scalePrice("10", "200"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("20"))).contains(scalePrice("20", "300"));
			assertThat(scalePrices.getByQuantity(new BigDecimal("999"))).contains(scalePrice("20", "300"));
		}
	}
}