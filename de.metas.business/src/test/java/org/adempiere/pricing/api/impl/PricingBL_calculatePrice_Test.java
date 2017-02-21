package org.adempiere.pricing.api.impl;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;

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

public class PricingBL_calculatePrice_Test
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();
	
	private IPricingBL pricingBL;

	private static final int C_Currency_ID_EUR = 102;
	private I_C_Country defaultCountry;

	private I_M_PricingSystem defaultPricingSystem;
	private I_M_PriceList defaultPriceList;
	private I_M_PriceList_Version defaultPriceListVerion;

	private I_M_Product defaultProduct;

	private I_M_Attribute attr_Country;
	@SuppressWarnings("unused")
	private I_M_AttributeValue attr_Country_DE;
	private I_M_AttributeValue attr_Country_CH;

	private I_M_Attribute attr_Label;
	private I_M_AttributeValue attr_Label_Bio;
	private final I_M_AttributeValue attr_Label_NULL = null;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		createPricingRules();

		defaultCountry = createCountry("DE", C_Currency_ID_EUR);
		defaultPricingSystem = createPricingSystem();
		defaultPriceList = createPriceList(defaultPricingSystem, defaultCountry);
		defaultPriceListVerion = createPriceListVersion(defaultPriceList);
		//
		defaultProduct = createProduct("Product1");
		//
		attr_Country = createM_Attribute("Country", X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		attr_Country_CH = createM_AttributeValue(attr_Country, "CH");
		attr_Country_DE = createM_AttributeValue(attr_Country, "DE");
		//
		attr_Label = createM_Attribute("Label", X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		attr_Label_Bio = createM_AttributeValue(attr_Label, "Bio");

		pricingBL = Services.get(IPricingBL.class);
	}

	@Test
	public void test_PriceWithAttributes_01()
	{
		//
		// Setup prices
		ProductPriceBuilder.newInstance(defaultPriceListVerion, defaultProduct)
				.setASI(ASIBuilder.newInstance()
						.setAttribute(attr_Country, attr_Country_CH)
						.setAttribute(attr_Label, attr_Label_Bio)
						.build())
				.setPrice(3)
				.build();
		ProductPriceBuilder.newInstance(defaultPriceListVerion, defaultProduct)
				.setASI(ASIBuilder.newInstance()
						.setAttribute(attr_Country, attr_Country_CH)
						.setAttribute(attr_Label, attr_Label_NULL)
						.build())
				.setPrice(2)
				.build();

		//
		// Test not-Bio price
		{
			final IEditablePricingContext pricingCtx = createPricingContextWithASI(ASIBuilder.newInstance()
					.setAttribute(attr_Country, attr_Country_CH)
					.build());

			final IPricingResult result = pricingBL.calculatePrice(pricingCtx);
			Assert.assertThat("not-Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(2)));
		}
		

		//
		// Test Bio price
		{
			final IEditablePricingContext pricingCtx = createPricingContextWithASI(ASIBuilder.newInstance()
					.setAttribute(attr_Country, attr_Country_CH)
					.setAttribute(attr_Label, attr_Label_Bio)
					.build());

			final IPricingResult result = pricingBL.calculatePrice(pricingCtx);
			Assert.assertThat("Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(3)));
		}

	}

	private final void createPricingRules()
	{
		final String[] classnames = new String[] {
				// "org.adempiere.pricing.spi.impl.rules.PriceListVersionVB" // commented out, requires DB access
				"org.adempiere.pricing.spi.impl.rules.PriceListVersion" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceListVB" // commented out, requires DB access
				// , "org.adempiere.pricing.spi.impl.rules.PriceList" // commented out, requires DB access
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceListVB" // commented out, requires DB access
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceList" // commented out, requires DB access
				, "org.adempiere.pricing.spi.impl.rules.Discount" //
				, "de.metas.adempiere.pricing.spi.impl.rules.ProductScalePrice" //
				// , "de.metas.flatrate.pricing.spi.impl.ContractDiscount" //
				, "de.metas.pricing.attributebased.impl.AttributePricing" //
				// , "de.metas.handlingunits.pricing.spi.impl.HUPricing" //
				// , "de.metas.procurement.base.pricing.spi.impl.ProcurementFlatrateRule" //
		};

		int nextSeqNo = 10;
		for (final String classname : classnames)
		{
			final I_C_PricingRule pricingRule = InterfaceWrapperHelper.create(Env.getCtx(), I_C_PricingRule.class, ITrx.TRXNAME_None);
			pricingRule.setClassname(classname);
			pricingRule.setIsActive(true);
			pricingRule.setSeqNo(nextSeqNo);
			InterfaceWrapperHelper.save(pricingRule);

			nextSeqNo += 10;
		}
	}

	private final I_C_Country createCountry(final String countryCode, final int currencyId)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Country.class, ITrx.TRXNAME_None);
		country.setCountryCode(countryCode);
		country.setName(countryCode);
		country.setC_Currency_ID(currencyId);
		InterfaceWrapperHelper.save(country);
		return country;
	}

	private final I_M_Product createProduct(final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private final I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(Env.getCtx(), I_M_PricingSystem.class, ITrx.TRXNAME_None);
		pricingSystem.setName("Test_" + getClass().getName());
		InterfaceWrapperHelper.save(pricingSystem);
		return pricingSystem;
	}

	private final I_M_PriceList createPriceList(final I_M_PricingSystem pricingSystem, final I_C_Country country)
	{
		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class, pricingSystem);
		priceList.setM_PricingSystem(pricingSystem);
		priceList.setC_Country(country);
		priceList.setC_Currency_ID(country.getC_Currency_ID());
		priceList.setPricePrecision(2);
		InterfaceWrapperHelper.save(priceList);
		return priceList;
	}

	private final I_M_PriceList_Version createPriceListVersion(final I_M_PriceList priceList)
	{
		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class, priceList);
		plv.setM_PriceList(priceList);
		plv.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		InterfaceWrapperHelper.save(plv);
		return plv;
	}

	private final I_M_Attribute createM_Attribute(final String name, final String attributeValueType)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(attributeValueType);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	private final I_M_AttributeValue createM_AttributeValue(final I_M_Attribute attribute, final String value)
	{
		final I_M_AttributeValue av = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, attribute);
		av.setM_Attribute(attribute);
		av.setValue(value);
		av.setName(value);
		InterfaceWrapperHelper.save(av);
		return av;
	}

	private final IAttributeSetInstanceAware asiAware(final I_M_AttributeSetInstance asi)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OrderLine.class, ITrx.TRXNAME_None);
		// orderLine.setM_Product(defaultProduct);
		orderLine.setM_AttributeSetInstance(asi);
		return InterfaceWrapperHelper.create(orderLine, IAttributeSetInstanceAware.class);
	}

	private final IEditablePricingContext createPricingContextWithASI(final I_M_AttributeSetInstance asi)
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setM_PricingSystem_ID(defaultPricingSystem.getM_PricingSystem_ID());
		pricingCtx.setM_PriceList_ID(defaultPriceList.getM_PriceList_ID());
		pricingCtx.setM_PriceList_Version_ID(defaultPriceListVerion.getM_PriceList_Version_ID());
		pricingCtx.setM_Product_ID(defaultProduct.getM_Product_ID());
		pricingCtx.setReferencedObject(asiAware(asi));

		return pricingCtx;
	}

}
