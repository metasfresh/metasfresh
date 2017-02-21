package org.adempiere.pricing.api.impl;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.model.I_C_PricingRule;
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

import com.google.common.collect.ImmutableList;

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

public class PricingTestHelper
{
	private final IPricingBL pricingBL;

	public static final int C_Currency_ID_EUR = 102;
	public I_C_Country defaultCountry;

	public I_M_PricingSystem defaultPricingSystem;
	public I_M_PriceList defaultPriceList;
	public I_M_PriceList_Version defaultPriceListVerion;

	public I_M_Product defaultProduct;

	public I_M_Attribute attr_Country;
	public I_M_AttributeValue attr_Country_DE;
	public I_M_AttributeValue attr_Country_CH;

	public I_M_Attribute attr_Label;
	public I_M_AttributeValue attr_Label_Bio;
	public final I_M_AttributeValue attr_Label_NULL = null;

	public PricingTestHelper()
	{
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

	protected List<String> getPricingRuleClassnamesToRegister()
	{
		return ImmutableList.copyOf(new String[] {
				// "de.metas.handlingunits.pricing.spi.impl.HUPricing" //
				"de.metas.pricing.attributebased.impl.AttributePricing" //
				, "de.metas.adempiere.pricing.spi.impl.rules.ProductScalePrice" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceListVersionVB" //
				, "org.adempiere.pricing.spi.impl.rules.PriceListVersion" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceListVB" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceList" //
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceListVB" //
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceList" //
				, "org.adempiere.pricing.spi.impl.rules.Discount" //
				// , "de.metas.procurement.base.pricing.spi.impl.ProcurementFlatrateRule" //
				// , "de.metas.flatrate.pricing.spi.impl.ContractDiscount" //
		});
	}

	private final void createPricingRules()
	{
		final List<String> classnames = getPricingRuleClassnamesToRegister();

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

	public final I_C_Country createCountry(final String countryCode, final int currencyId)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Country.class, ITrx.TRXNAME_None);
		country.setCountryCode(countryCode);
		country.setName(countryCode);
		country.setC_Currency_ID(currencyId);
		InterfaceWrapperHelper.save(country);
		return country;
	}

	public final I_M_Product createProduct(final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	public final I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(Env.getCtx(), I_M_PricingSystem.class, ITrx.TRXNAME_None);
		pricingSystem.setName("Test_" + getClass().getName());
		InterfaceWrapperHelper.save(pricingSystem);
		return pricingSystem;
	}

	public final I_M_PriceList createPriceList(final I_M_PricingSystem pricingSystem, final I_C_Country country)
	{
		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class, pricingSystem);
		priceList.setM_PricingSystem(pricingSystem);
		priceList.setC_Country(country);
		priceList.setC_Currency_ID(country.getC_Currency_ID());
		priceList.setPricePrecision(2);
		InterfaceWrapperHelper.save(priceList);
		return priceList;
	}

	public final I_M_PriceList_Version createPriceListVersion(final I_M_PriceList priceList)
	{
		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class, priceList);
		plv.setM_PriceList(priceList);
		plv.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		InterfaceWrapperHelper.save(plv);
		return plv;
	}

	public final I_M_Attribute createM_Attribute(final String name, final String attributeValueType)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(attributeValueType);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	public final I_M_AttributeValue createM_AttributeValue(final I_M_Attribute attribute, final String value)
	{
		final I_M_AttributeValue av = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, attribute);
		av.setM_Attribute(attribute);
		av.setValue(value);
		av.setName(value);
		InterfaceWrapperHelper.save(av);
		return av;
	}

	public final IAttributeSetInstanceAware asiAware(final I_M_AttributeSetInstance asi)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OrderLine.class, ITrx.TRXNAME_None);
		// orderLine.setM_Product(defaultProduct);
		orderLine.setM_AttributeSetInstance(asi);
		return InterfaceWrapperHelper.create(orderLine, IAttributeSetInstanceAware.class);
	}

	public final IEditablePricingContext createPricingContext()
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setM_PricingSystem_ID(defaultPricingSystem.getM_PricingSystem_ID());
		pricingCtx.setM_PriceList_ID(defaultPriceList.getM_PriceList_ID());
		pricingCtx.setM_PriceList_Version_ID(defaultPriceListVerion.getM_PriceList_Version_ID());
		pricingCtx.setM_Product_ID(defaultProduct.getM_Product_ID());

		return pricingCtx;
	}

	public final IEditablePricingContext createPricingContextWithASI(final I_M_AttributeSetInstance asi)
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		pricingCtx.setReferencedObject(asiAware(asi));
		return pricingCtx;
	}

	public ProductPriceBuilder newProductPriceBuilder()
	{
		return new ProductPriceBuilder(defaultPriceListVerion, defaultProduct);
	}

	public IPricingResult calculatePrice(final IPricingContext pricingCtx)
	{
		return pricingBL.calculatePrice(pricingCtx);
	}

}
