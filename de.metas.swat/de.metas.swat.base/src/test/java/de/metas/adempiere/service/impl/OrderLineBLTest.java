package de.metas.adempiere.service.impl;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.pricing.spi.impl.rules.MockedPricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;

public class OrderLineBLTest
{
	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		//
		// Setup mocked pricing rule
		MockedPricingRule.INSTANCE.reset();
		final I_C_PricingRule pricingRule = InterfaceWrapperHelper.create(ctx, I_C_PricingRule.class, ITrx.TRXNAME_None);
		pricingRule.setSeqNo(10);
		pricingRule.setClassname(MockedPricingRule.class.getName());
		InterfaceWrapperHelper.save(pricingRule);
	}

	private I_C_OrderLine createOrderLine(final BigDecimal price, boolean isManualPrice)
	{
		I_C_OrderLine orderline = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);

		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(uom);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(product);

		final I_C_Currency currency = InterfaceWrapperHelper.create(ctx, I_C_Currency.class, ITrx.TRXNAME_None);
		currency.setStdPrecision(0);
		InterfaceWrapperHelper.save(currency);

		final I_M_PriceList priceList = InterfaceWrapperHelper.create(ctx, I_M_PriceList.class, ITrx.TRXNAME_None);
		priceList.setC_Currency_ID(currency.getC_Currency_ID());
		InterfaceWrapperHelper.save(priceList);

		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setM_PriceList_ID(priceList.getM_PriceList_ID());
		InterfaceWrapperHelper.save(order);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(ctx, I_M_PriceList_Version.class, ITrx.TRXNAME_None);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		InterfaceWrapperHelper.save(plv);

		final I_C_UOM priceUom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(priceUom);

		// Define conversion: uom->priceUom
		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.create(ctx, I_C_UOM_Conversion.class, ITrx.TRXNAME_None);
		conversion.setC_UOM_ID(uom.getC_UOM_ID());
		conversion.setC_UOM_To_ID(priceUom.getC_UOM_ID());
		conversion.setMultiplyRate(BigDecimal.ONE);
		InterfaceWrapperHelper.save(conversion);

		final I_M_ProductPrice productprice = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice.class, ITrx.TRXNAME_None);
		productprice.setM_Product_ID(product.getM_Product_ID());
		productprice.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
		productprice.setC_UOM(priceUom);
		InterfaceWrapperHelper.save(productprice);

		orderline.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());

		orderline.setM_Product(product);

		orderline.setC_Order(order);

		orderline.setIsManualPrice(isManualPrice);

		orderline.setPriceEntered(price);

		orderline.setQtyOrdered(BigDecimal.ONE);

		orderline.setPrice_UOM(priceUom);

		orderline.setC_UOM(uom);

		return orderline;
	}


	@Test
	public void test_ManualPrice()
	{
		final BigDecimal expectedPrice = new BigDecimal(23);

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, true);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		Assert.assertThat("Invalid PriceEntered", orderline.getPriceEntered(), Matchers.comparesEqualTo(expectedPrice));
		Assert.assertThat("Invalid PriceActual", orderline.getPriceActual(), Matchers.comparesEqualTo(expectedPrice));
	}

	@Test
	public void test_Not_ManualPrice()
	{
		final BigDecimal expectedPrice = Env.ONEHUNDRED;

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, false);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		Assert.assertThat("Invalid PriceEntered", orderline.getPriceEntered(), Matchers.comparesEqualTo(expectedPrice));
		Assert.assertThat("Invalid PriceActual", orderline.getPriceActual(), Matchers.comparesEqualTo(expectedPrice));
	}

	@Test
	public void test_ManualPrice_Qty_10()
	{
		final BigDecimal expectedPrice = new BigDecimal(23);

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, true);

		orderline.setQtyOrdered(BigDecimal.TEN);
		InterfaceWrapperHelper.save(orderline);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		Assert.assertThat("Invalid PriceEntered", orderline.getPriceEntered(), Matchers.comparesEqualTo(expectedPrice));
		Assert.assertThat("Invalid PriceActual", orderline.getPriceActual(), Matchers.comparesEqualTo(expectedPrice));
	}

	@Test
	public void test_NotManualPrice_Qty_10()
	{
		final BigDecimal expectedPrice = Env.ONEHUNDRED;

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, false);

		orderline.setQtyOrdered(BigDecimal.TEN);
		InterfaceWrapperHelper.save(orderline);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		Assert.assertThat("Invalid PriceEntered", orderline.getPriceEntered(), Matchers.comparesEqualTo(expectedPrice));
		Assert.assertThat("Invalid PriceActual", orderline.getPriceActual(), Matchers.comparesEqualTo(expectedPrice));
	}
}
