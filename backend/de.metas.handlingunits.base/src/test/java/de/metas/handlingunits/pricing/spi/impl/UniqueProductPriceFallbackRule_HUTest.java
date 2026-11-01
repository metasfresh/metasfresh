/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.pricing.spi.impl;

import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Customer-shape regression tests for the {@code UniqueProductPriceFallbackRule}.
 *
 * <p>me03 issue 29144 (Spavetti / sp80): the customer's only {@code M_ProductPrice} for the
 * product carries {@code M_HU_PI_Item_Product_ID}. The order line has no packing-material
 * context, so {@code HUPricing} cannot match, {@code AttributePricing} skips, and
 * {@code MainProductPriceRule}'s {@code HUPIItemProductMatcher_None} excludes the tagged record
 * — leaving the result uncalculated and raising "Produkt nicht auf Preisliste".
 *
 * <p>The new fallback fires after the strict trio fails and accepts the unique tagged record.
 *
 * <p>The flag-agnostic uniqueness logic is also exercised in
 * {@code de.metas.business / UniqueProductPriceFallbackRuleTest} via the
 * {@code IsAttributeDependant} variant; this test class focuses on the
 * {@code M_HU_PI_Item_Product_ID} variant since the column lives on the HU-extended interface.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class UniqueProductPriceFallbackRule_HUTest
{
	private HUPricingTestHelper helper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new HUPricingTestHelper();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	@Test
	@DisplayName("Single packing-tagged price, order line without packing context → fallback uses it")
	public void uniquePackingTaggedPrice_isPickedUp()
	{
		helper.newProductPriceBuilder()
				.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1)
				.setPrice(7)
				.build();

		final IEditablePricingContext pricingCtx = helper.createPricingContext();
		// no setReferencedObject → no packing material, no ASI on the order line

		final IPricingResult result = helper.calculatePrice(pricingCtx);

		assertThat(result.isCalculated()).as("calculated").isTrue();
		assertThat(result.getPriceStd()).as("PriceStd").isEqualByComparingTo(BigDecimal.valueOf(7));
	}

	@Test
	@DisplayName("Two packing-tagged prices (PI1, PI2), no order-line context → uniqueness gate skips")
	public void twoPackingTaggedPrices_uniquenessGateSkips()
	{
		helper.newProductPriceBuilder()
				.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1)
				.setPrice(7)
				.build();
		helper.newProductPriceBuilder()
				.setM_HU_PI_Item_Product(helper.defaultPIItemProduct2)
				.setPrice(11)
				.build();

		final IEditablePricingContext pricingCtx = helper.createPricingContext();

		final IPricingResult result = helper.calculatePrice(pricingCtx);

		assertThat(result.isCalculated())
				.as("calculated; the fallback must NOT silently pick one of two tagged records")
				.isFalse();
	}
}
