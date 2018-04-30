package de.metas.contracts.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.impl.PricingTestHelper;

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
	public void calculateSubscriptionPrice_test()
	{
		final I_C_Country contryDE = helper.createCountry("DE", PricingTestHelper.C_Currency_ID_EUR);
		final I_M_PriceList priceListDE = helper.createPriceList(helper.getDefaultPricingSystem(), contryDE);
		final I_M_PriceList_Version plvDE = helper.createPriceListVersion(priceListDE);

		final I_C_Country contryCH = helper.createCountry("CH", PricingTestHelper.C_Currency_ID_CHF);
		final I_M_PriceList priceListCH = helper.createPriceList(helper.getDefaultPricingSystem(), contryCH);
		final I_M_PriceList_Version plvCH = helper.createPriceListVersion(priceListCH);


		helper.newProductPriceBuilder(plvCH)
				.setPrice(3)
				.build();

		helper.newProductPriceBuilder(plvDE)
				.setPrice(5)
				.build();

		final IEditablePricingContext pricingCtx = helper.subscriptionPricingContextNew()
				.priceList(priceListDE)
				.priceListVersion(plvDE)
				.country(contryDE)
				.build();

		final IPricingResult result = helper.calculatePrice(pricingCtx);
		Assert.assertThat("PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(5)));
	}
	
	@Test
	public void calculateSubscriptionPriceEmptyCountryInPriceList_test()
	{

		final I_C_Country contryDE = helper.createCountry("DE", PricingTestHelper.C_Currency_ID_EUR);
		final I_M_PriceList priceListDE = helper.createPriceList(helper.getDefaultPricingSystem(), contryDE);
		priceListDE.setC_Country_ID(-1);
		save(priceListDE);
		final I_M_PriceList_Version plvDE = helper.createPriceListVersion(priceListDE);

		final I_C_Country contryCH = helper.createCountry("CH", PricingTestHelper.C_Currency_ID_CHF);
		final I_M_PriceList priceListCH = helper.createPriceList(helper.getDefaultPricingSystem(), contryCH);
		final I_M_PriceList_Version plvCH = helper.createPriceListVersion(priceListCH);


		helper.newProductPriceBuilder(plvCH)
				.setPrice(3)
				.build();

		helper.newProductPriceBuilder(plvDE)
				.setPrice(5)
				.build();

		final IEditablePricingContext pricingCtx = helper.subscriptionPricingContextNew()
				.priceList(priceListDE)
				.priceListVersion(plvDE)
				.country(contryDE)
				.build();

		final IPricingResult result = helper.calculatePrice(pricingCtx);
		Assert.assertThat("PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(5)));
	}
}
