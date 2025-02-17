package de.metas.pricing.service.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PricingTest
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private PricingTestHelper helper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new PricingTestHelper();

		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
	}

	@Test
	public void test_PriceWithAttributes_Bio_and_NonBio()
	{
		//
		// Setup prices
		helper.newProductPriceBuilder()
				.setASI(ASIBuilder.newInstance()
						.setAttribute(helper.attr_Country, helper.attr_Country_CH)
						.setAttribute(helper.attr_Label, helper.attr_Label_Bio)
						.build())
				.setPrice(3)
				.build();
		helper.newProductPriceBuilder()
				.setASI(ASIBuilder.newInstance()
						.setAttribute(helper.attr_Country, helper.attr_Country_CH)
						.setAttribute(helper.attr_Label, helper.attr_Label_NULL)
						.build())
				.setPrice(2)
				.build();

		//
		// Test not-Bio price
		{
			final IEditablePricingContext pricingCtx = helper.createPricingContextWithASI(ASIBuilder.newInstance()
					.setAttribute(helper.attr_Country, helper.attr_Country_CH)
					.build());

			final IPricingResult result = helper.calculatePrice(pricingCtx);
			assertThat(result.isCalculated()).isTrue();
			Assert.assertThat("not-Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(2)));
		}

		//
		// Test Bio price
		{
			final IEditablePricingContext pricingCtx = helper.createPricingContextWithASI(ASIBuilder.newInstance()
					.setAttribute(helper.attr_Country, helper.attr_Country_CH)
					.setAttribute(helper.attr_Label, helper.attr_Label_Bio)
					.build());

			final IPricingResult result = helper.calculatePrice(pricingCtx);
			Assert.assertThat("Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(3)));
		}
	}

	@Test
	public void test_MultipleDiscountChanges_WithAdvice()
	{
		final PricingResult build = PricingResult.builder()
				.priceDate(SystemTime.asLocalDate())
				.build();
		final Percent discount1 = Percent.of(10);
		final Percent discount2 = Percent.of(20);

		build.setDiscount(discount1);
		assertThat(build.getDiscount()).isEqualTo(discount1);
		assertThat(build.isDontOverrideDiscountAdvice()).isFalse();

		build.setDontOverrideDiscountAdvice(true);
		build.setDiscount(discount2);
		assertThat(build.getDiscount()).isEqualTo(discount1);
	}
}
