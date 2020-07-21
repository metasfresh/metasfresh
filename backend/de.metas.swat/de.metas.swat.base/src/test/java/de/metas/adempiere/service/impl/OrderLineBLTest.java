package de.metas.adempiere.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderLineBL;
import de.metas.pricing.rules.MockedPricingRule;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

public class OrderLineBLTest
{
	private Properties ctx;

	@BeforeEach
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
		pricingRule.setName(pricingRule.getClassname());
		InterfaceWrapperHelper.save(pricingRule);
	}

	private I_C_OrderLine createOrderLine(final BigDecimal price, boolean isManualPrice)
	{
		I_C_OrderLine orderline = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);

		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(uom);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setM_Product_Category_ID(20);
		InterfaceWrapperHelper.save(product);

		final CurrencyId currency = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_M_PriceList priceList = InterfaceWrapperHelper.create(ctx, I_M_PriceList.class, ITrx.TRXNAME_None);
		priceList.setC_Currency_ID(currency.getRepoId());
		priceList.setM_PricingSystem_ID(1000000);
		InterfaceWrapperHelper.save(priceList);

		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setM_PriceList_ID(priceList.getM_PriceList_ID());
		order.setC_Currency_ID(currency.getRepoId());
		order.setC_BPartner_ID(10);
		InterfaceWrapperHelper.save(order);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.create(ctx, I_M_PriceList_Version.class, ITrx.TRXNAME_None);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		InterfaceWrapperHelper.save(plv);

		final I_C_UOM priceUom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(priceUom);

		// Define conversion: uom->priceUom
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(uom.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(priceUom.getC_UOM_ID()))
				.fromToMultiplier(BigDecimal.ONE)
				// .toFromMultiplier(BigDecimal.ONE)
				.build());

		final I_M_ProductPrice productprice = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice.class, ITrx.TRXNAME_None);
		productprice.setM_Product_ID(product.getM_Product_ID());
		productprice.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
		productprice.setC_UOM_ID(priceUom.getC_UOM_ID());
		InterfaceWrapperHelper.save(productprice);

		orderline.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());

		orderline.setM_Product_ID(product.getM_Product_ID());

		orderline.setC_Order(order);

		orderline.setIsManualPrice(isManualPrice);

		orderline.setPriceEntered(price);

		orderline.setQtyOrdered(BigDecimal.ONE);

		orderline.setPrice_UOM_ID(priceUom.getC_UOM_ID());

		orderline.setC_UOM_ID(uom.getC_UOM_ID());

		return orderline;
	}

	@Test
	public void test_ManualPrice()
	{
		final BigDecimal expectedPrice = new BigDecimal(23);

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, true);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		assertThat(orderline.getPriceEntered()).as("PriceEntered").isEqualByComparingTo(expectedPrice);
		assertThat(orderline.getPriceActual()).as("PriceActual").isEqualByComparingTo(expectedPrice);
	}

	@Test
	public void test_Not_ManualPrice()
	{
		final BigDecimal expectedPrice = Env.ONEHUNDRED;

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, false);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		assertThat(orderline.getPriceEntered()).as("PriceEntered").isEqualByComparingTo(expectedPrice);
		assertThat(orderline.getPriceActual()).as("PriceActual").isEqualByComparingTo(expectedPrice);
	}

	@Test
	public void test_ManualPrice_Qty_10()
	{
		final BigDecimal expectedPrice = new BigDecimal(23);

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, true);

		orderline.setQtyOrdered(BigDecimal.TEN);
		InterfaceWrapperHelper.save(orderline);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		assertThat(orderline.getPriceEntered()).as("PriceEntered").isEqualByComparingTo(expectedPrice);
		assertThat(orderline.getPriceActual()).as("PriceActual").isEqualByComparingTo(expectedPrice);
	}

	@Test
	public void test_NotManualPrice_Qty_10()
	{
		final BigDecimal expectedPrice = Env.ONEHUNDRED;

		final I_C_OrderLine orderline = createOrderLine(expectedPrice, false);

		orderline.setQtyOrdered(BigDecimal.TEN);
		InterfaceWrapperHelper.save(orderline);

		Services.get(IOrderLineBL.class).updatePrices(orderline);

		assertThat(orderline.getPriceEntered()).as("PriceEntered").isEqualByComparingTo(expectedPrice);
		assertThat(orderline.getPriceActual()).as("PriceActual").isEqualByComparingTo(expectedPrice);
	}
}
