package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PricingSystem;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Location;
import de.metas.product.IProductPA;

public class ProductPATest
{
	private static I_M_PricingSystem pricingSystem;
	private static I_C_Country country;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		pricingSystem = createPricingSystem();
		country = createCountry();
	}

	private I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem ps = newInstance(I_M_PricingSystem.class);
		ps.setValue("TEST PS");
		ps.setName("TEST PS");
		save(ps);

		return ps;
	}

	private I_C_Country createCountry()
	{
		final I_C_Country c = newInstance(I_C_Country.class);
		c.setAD_Language("de_DE");
		c.setCountryCode("DE");
		save(c);

		return c;
	}

	@Test
	public void test_retrievePriceListByPricingSyst_WithCountryMatched()
	{
		final I_M_PriceList pl1 = newInstance(I_M_PriceList.class);
		pl1.setM_PricingSystem(pricingSystem);
		pl1.setName("test price list");
		pl1.setIsSOPriceList(true);
		pl1.setC_Country(country);
		save(pl1);

		final I_M_PriceList pl2 = newInstance(I_M_PriceList.class);
		pl2.setM_PricingSystem(pricingSystem);
		pl2.setName("test price list");
		pl2.setIsSOPriceList(true);
		save(pl2);

		final I_C_Country otherCountry = createCountry();
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Address1");
		location.setC_Country(otherCountry);
		save(location);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_Location(location);
		bpl.setC_BPartner(bpartner);
		save(bpl);

		final I_M_PriceList pl = Services.get(IProductPA.class).retrievePriceListByPricingSyst(pricingSystem.getM_PricingSystem_ID(), bpl, true);

		assertThat(pl).isNotNull();
		assertThat(pl.getM_PriceList_ID()).isEqualByComparingTo(pl2.getM_PriceList_ID());
	}

	@Test
	public void test_retrievePriceListByPricingSyst_WithCountryNull()
	{
		final I_M_PriceList pl1 = newInstance(I_M_PriceList.class);
		pl1.setM_PricingSystem(pricingSystem);
		pl1.setName("test price list");
		pl1.setIsSOPriceList(true);
		pl1.setC_Country(country);
		save(pl1);

		final I_M_PriceList pl2 = newInstance(I_M_PriceList.class);
		pl2.setM_PricingSystem(pricingSystem);
		pl2.setName("test price list");
		pl2.setIsSOPriceList(true);
		save(pl2);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Address1");
		location.setC_Country(country);
		save(location);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class);
		bpl.setC_Location(location);
		bpl.setC_BPartner(bpartner);
		save(bpl);

		final I_M_PriceList pl = Services.get(IProductPA.class).retrievePriceListByPricingSyst(pricingSystem.getM_PricingSystem_ID(), bpl, true);

		assertThat(pl).isNotNull();
		assertThat(pl.getM_PriceList_ID()).isEqualByComparingTo(pl1.getM_PriceList_ID());
	}
}
