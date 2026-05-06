/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.pricing.rules.price_list_version;

import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.impl.ASIBuilder;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Regression tests for {@link UniqueProductPriceFallbackRule}.
 *
 * <p>Background — me03 issue 29144 ("Fehlermeldung Produkt nicht auf Preisliste trotz Eintrag",
 * Spavetti / sp80): the strict three-rule pipeline (HU, attribute, main) leaves the result
 * uncalculated when the only candidate price for a product carries
 * {@code IsAttributeDependant = Y} or {@code M_HU_PI_Item_Product_ID} set, because
 * {@link MainProductPriceRule}'s filters reject it. The new fallback engages only after the
 * strict rules have failed and only when the data is unambiguous (exactly one active+valid
 * {@code M_ProductPrice}).
 *
 * <p>The packing-material variant ({@code M_HU_PI_Item_Product_ID}) is exercised in
 * {@code de.metas.handlingunits.base} where {@code de.metas.handlingunits.model.I_M_ProductPrice}
 * exposes the column setter. This class covers the {@code IsAttributeDependant} variant, which is
 * sufficient to verify the fallback's flag-agnostic uniqueness logic.
 */
@ExtendWith(AdempiereTestWatcher.class)
class UniqueProductPriceFallbackRuleTest
{
	private PricingTestHelper helper;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		helper = new PricingTestHelper();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	private I_M_ProductPrice newBaselinePrice(final int amount)
	{
		return helper.newProductPriceBuilder().setPrice(amount).build();
	}

	private I_M_ProductPrice newAttributeDependentPrice(final int amount, final String countryValue)
	{
		return helper.newProductPriceBuilder()
				.setASI(ASIBuilder.newInstance()
						.setAttribute(helper.attr_Country, countryValue)
						.build())
				.setPrice(amount)
				.build();
	}

	@Test
	@DisplayName("Single attribute-dependent price, no order-line ASI → fallback uses it")
	void uniqueRecord_attributeDependent_isPickedUp()
	{
		newAttributeDependentPrice(11, "DE");

		final IPricingResult result = helper.calculatePrice(helper.createPricingContext());

		assertThat(result.isCalculated()).as("calculated").isTrue();
		assertThat(result.getPriceStd()).as("PriceStd").isEqualByComparingTo(BigDecimal.valueOf(11));
	}

	@Test
	@DisplayName("Tagged + baseline siblings → MainProductPriceRule wins on baseline; fallback skipped")
	void taggedPlusBaseline_baselineWins()
	{
		newAttributeDependentPrice(10, "DE");
		newBaselinePrice(20);

		final IPricingResult result = helper.calculatePrice(helper.createPricingContext());

		assertThat(result.isCalculated()).as("calculated").isTrue();
		assertThat(result.getPriceStd())
				.as("PriceStd should match the baseline, not the tagged record")
				.isEqualByComparingTo(BigDecimal.valueOf(20));
	}

	@Test
	@DisplayName("Two tagged records, no baseline → uniqueness gate skips, result stays uncalculated")
	void twoTaggedRecords_uniquenessGateSkips()
	{
		newAttributeDependentPrice(10, "DE");
		newAttributeDependentPrice(15, "CH");

		final IPricingResult result = helper.calculatePrice(helper.createPricingContext());

		assertThat(result.isCalculated())
				.as("calculated; the fallback must NOT silently disambiguate two tagged records")
				.isFalse();
	}

	@Test
	@DisplayName("No M_ProductPrice records → result stays uncalculated (existing behaviour preserved)")
	void noRecords_resultUncalculated()
	{
		// no M_ProductPrice rows created
		final IPricingResult result = helper.calculatePrice(helper.createPricingContext());

		assertThat(result.isCalculated()).as("calculated").isFalse();
	}
}
