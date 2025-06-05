package de.metas.contracts.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.impl.PricingTestHelper;

public class SubscriptionPricingRuleTest
{
	private SubscriptionPricingTestHelper helper;

	@BeforeEach
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();
		helper = new SubscriptionPricingTestHelper();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
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
				.orgId(OrgId.ANY)
				.priceList(priceListDE)
				.priceListVersion(plvDE)
				.country(contryDE)
				.build();

		final IPricingResult result = helper.calculatePrice(pricingCtx);
		assertThat(result.getPriceStd()).isEqualByComparingTo("5");
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
				.orgId(OrgId.ANY)
				.priceList(priceListDE)
				.priceListVersion(plvDE)
				.country(contryDE)
				.build();

		final IPricingResult result = helper.calculatePrice(pricingCtx);
		assertThat(result.getPriceStd()).isEqualByComparingTo("5");
	}
}
