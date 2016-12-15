package org.adempiere.pricing;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.pricing.spi.impl.rules.MockedPricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.X_M_DiscountSchemaLine;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.pricing.attributebased.I_M_ProductPrice;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute;
import de.metas.pricing.attributebased.I_M_ProductPrice_Attribute_Line;
import de.metas.pricing.attributebased.impl.AttributePricing;
import de.metas.testsupport.AbstractTestSupport;

/**
 *
 * TODO: we need to figure it out wtf this test is testing and avoid producing such tests in future
 *
 * @author tsa
 *
 */
public class PricingRuleTest extends AbstractTestSupport
{
	private IPricingBL pricingBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		pricingBL = Services.get(IPricingBL.class);

		MockedPricingRule.INSTANCE.reset();
	}

	@Test
	public void attributePricingTest()
	{
		createPricingRule(AttributePricing.class, 10);

		final I_M_Product product = product("1", 100);
		final I_M_AttributeValue value = attributeValue("test");
		value.setM_Attribute_ID(100);
		InterfaceWrapperHelper.save(value);
		final I_M_ProductPrice_Attribute productPriceAttribute = productPriceAttribute(value.getM_AttributeValue_ID());
		productPriceAttribute.setPriceList(BigDecimal.TEN);
		productPriceAttribute.setSeqNo(20);
		InterfaceWrapperHelper.save(productPriceAttribute);

		final I_M_AttributeValue badValue = attributeValue("badTest");
		badValue.setM_Attribute_ID(100);
		InterfaceWrapperHelper.save(badValue);
		final I_M_ProductPrice_Attribute badProductPriceAttribute = productPriceAttribute(badValue.getM_AttributeValue_ID());
		badProductPriceAttribute.setPriceList(BigDecimal.ONE);
		badProductPriceAttribute.setSeqNo(10);
		InterfaceWrapperHelper.save(badProductPriceAttribute);

		final I_M_PriceList priceList = priceList(100);

		final I_M_PriceList_Version priceListVersion = priceListVersion(100);
		priceListVersion.setM_PriceList_ID(priceList.getM_PriceList_ID());
		InterfaceWrapperHelper.save(priceListVersion);

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.create(productPrice(100), I_M_ProductPrice.class);
		productPrice.setM_Product_ID(product.getM_Product_ID());
		productPrice.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		productPrice.setIsAttributeDependant(true);
		InterfaceWrapperHelper.save(productPrice);

		productPriceAttribute.setM_ProductPrice(productPrice);
		badProductPriceAttribute.setM_ProductPrice(productPrice);
		InterfaceWrapperHelper.save(productPriceAttribute);

		final I_M_AttributeInstance attributeInstance = attributeInstance(100, "test");
		attributeInstance.setM_Attribute_ID(value.getM_Attribute_ID());
		attributeInstance.setM_AttributeValue(value);
		InterfaceWrapperHelper.save(attributeInstance);

		final I_M_ProductPrice_Attribute_Line productPriceAttributeLine = productPriceAttributeLine(100);

		productPriceAttributeLine.setM_Attribute_ID(value.getM_Attribute_ID());
		productPriceAttributeLine.setM_AttributeValue(value);
		productPriceAttributeLine.setM_ProductPrice_Attribute(productPriceAttribute);
		InterfaceWrapperHelper.save(productPriceAttributeLine);

		final I_M_ProductPrice_Attribute_Line badProductPriceAttributeLine = productPriceAttributeLine(101);

		badProductPriceAttributeLine.setM_Attribute_ID(badValue.getM_Attribute_ID());
		badProductPriceAttributeLine.setM_AttributeValue(badValue);
		badProductPriceAttributeLine.setM_ProductPrice_Attribute(badProductPriceAttribute);
		InterfaceWrapperHelper.save(badProductPriceAttributeLine);

		final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstance(100);

		final I_C_OrderLine orderLine = orderLine("test");
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setM_AttributeSetInstance_ID(attributeSetInstance.getM_AttributeSetInstance_ID());
		InterfaceWrapperHelper.save(orderLine);

		{
			final IEditablePricingContext context = pricingBL.createInitialContext(product.getM_Product_ID(), 1, 0, BigDecimal.ONE, true);
			context.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
			context.setReferencedObject(orderLine);
			final IPricingResult result = pricingBL.calculatePrice(context);

			Assert.assertEquals("Price shall be calculated", true, result.isCalculated());
			Assert.assertEquals("Incorrect price", BigDecimal.TEN, result.getPriceList());
		}

		attributeInstance.setM_AttributeValue_ID(value.getM_AttributeValue_ID());
		InterfaceWrapperHelper.save(attributeInstance);
		createPricingRule(MockedPricingRule.class, 20);

		// NOTE: because pricing rules are cached, we need to reset the cache in order to have our newly create pricing rule considered
		CacheMgt.get().reset();

		{
			final IEditablePricingContext context2 = pricingBL.createInitialContext(
					product.getM_Product_ID(),
					2, // C_BPartner_ID
					-1, // C_UOM_ID
					BigDecimal.ONE, // Qty
					true // IsSOTrx
					);
			context2.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());

			final IPricingResult result2 = pricingBL.calculatePrice(context2);

			Assert.assertEquals("Price shall be calculated", true, result2.isCalculated());
			Assert.assertEquals("Incorrect price", MockedPricingRule.INSTANCE.priceToReturn, result2.getPriceList());
		}

		// Test with discount schema
		{
			final I_M_DiscountSchemaLine discountSchemaLine = discountSchemaLine(100);
			discountSchemaLine.setStd_Discount(BigDecimal.TEN);
			InterfaceWrapperHelper.save(discountSchemaLine);

			discountSchemaLine.setStd_Base(X_M_DiscountSchemaLine.STD_BASE_Listenpreis);

			InterfaceWrapperHelper.save(discountSchemaLine);

			productPriceAttribute.setPriceStd(BigDecimal.ONE);
			InterfaceWrapperHelper.save(productPriceAttribute);

			Assert.assertEquals("Incorrect price", BigDecimal.ONE, productPriceAttribute.getPriceStd());

			final List<de.metas.adempiere.model.I_M_ProductPrice> productPriceAsList = new ArrayList<de.metas.adempiere.model.I_M_ProductPrice>();
			productPriceAsList.add(productPrice);
			pricingBL.getAggregatedPricingRule(Env.getCtx()).updateFromDiscounLine(priceListVersion, productPriceAsList.iterator(), discountSchemaLine);

			Assert.assertEquals("Incorrect price", new BigDecimal(9.0), productPriceAttribute(value.getM_AttributeValue_ID()).getPriceStd().setScale(0, RoundingMode.UP));
		}
	}

	private I_C_PricingRule createPricingRule(final Class<? extends IPricingRule> pricingRuleClass, final int seqNo)
	{
		final I_C_PricingRule pricingRuleDef = InterfaceWrapperHelper.create(Env.getCtx(), I_C_PricingRule.class, ITrx.TRXNAME_None);
		pricingRuleDef.setClassname(pricingRuleClass.getName());
		pricingRuleDef.setSeqNo(seqNo);

		InterfaceWrapperHelper.save(pricingRuleDef);

		return pricingRuleDef;
	}

	private I_M_ProductPrice_Attribute productPriceAttribute(final int valueId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_ProductPrice_Attribute productPriceAttribute = db.getFirstOnly(I_M_ProductPrice_Attribute.class, new IQueryFilter<I_M_ProductPrice_Attribute>()
		{

			@Override
			public boolean accept(I_M_ProductPrice_Attribute pojo)
			{
				return Check.equals(pojo.getM_ProductPrice_Attribute_ID(), valueId);
			}
		});

		if (productPriceAttribute == null)
		{
			productPriceAttribute = db.newInstance(Env.getCtx(), I_M_ProductPrice_Attribute.class);
			productPriceAttribute.setM_ProductPrice_Attribute_ID(valueId);
			InterfaceWrapperHelper.save(productPriceAttribute);
		}

		return productPriceAttribute;
	}

	private I_M_ProductPrice_Attribute_Line productPriceAttributeLine(final int valueId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_M_ProductPrice_Attribute_Line productPriceAttribute = db.getFirstOnly(I_M_ProductPrice_Attribute_Line.class, new IQueryFilter<I_M_ProductPrice_Attribute_Line>()
		{

			@Override
			public boolean accept(I_M_ProductPrice_Attribute_Line pojo)
			{
				return Check.equals(pojo.getM_ProductPrice_Attribute_Line_ID(), valueId);
			}
		});

		if (productPriceAttribute == null)
		{
			productPriceAttribute = db.newInstance(Env.getCtx(), I_M_ProductPrice_Attribute_Line.class);
			productPriceAttribute.setM_ProductPrice_Attribute_Line_ID(valueId);
			InterfaceWrapperHelper.save(productPriceAttribute);
		}

		return productPriceAttribute;
	}

}
