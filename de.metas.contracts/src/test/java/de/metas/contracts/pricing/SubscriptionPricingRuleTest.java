package de.metas.contracts.pricing;

import java.math.BigDecimal;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.impl.PricingTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubscriptionPricingRuleTest
{

	private SubscriptionPricingTestHelper helper;

	@Before
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();
		helper = new SubscriptionPricingTestHelper();
	}

	@Test
	public void calculateSubscriptionPricet_test()
	{

		final I_C_Country contryCH = helper.createCountry("DE", PricingTestHelper.C_Currency_ID_EUR);
		final I_M_PriceList priceListCH = helper.createPriceList(helper.getDefaultPricingSystem(), contryCH);
		final I_M_PriceList_Version plvCH = helper.createPriceListVersion(priceListCH);

		final I_C_Country contryDE = helper.createCountry("CH", PricingTestHelper.C_Currency_ID_CHF);
		final I_M_PriceList priceListDE = helper.createPriceList(helper.getDefaultPricingSystem(), contryDE);
		final I_M_PriceList_Version plvDE = helper.createPriceListVersion(priceListDE);


		helper.newProductPriceBuilder(plvCH)
				.setPrice(3)
				.build();

		helper.newProductPriceBuilder(plvDE)
				.setPrice(3)
				.build();

		final IEditablePricingContext pricingCtx = helper.subscriptionPricingContextNew()
				.priceList(priceListDE)
				.priceListVersion(plvDE)
				.build();

		final IPricingResult result = helper.calculatePrice(pricingCtx);
		Assert.assertThat("PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(3)));

	}
}
