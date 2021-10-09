package de.metas.freightcost;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.freighcost.FreightCostRepository;
import de.metas.freighcost.FreightCostRule;
import de.metas.freighcost.FreightCostService;
import de.metas.interfaces.I_C_BPartner;
import de.metas.money.CurrencyId;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderFreightCostsService;
import de.metas.payment.PaymentRule;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.model.I_M_FreightCost;
import org.adempiere.model.I_M_FreightCostDetail;
import org.adempiere.model.I_M_FreightCostShipper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class FreightCostTest
{
	private static final BigDecimal ZERO = BigDecimal.ZERO;
	private static final BigDecimal ONE = BigDecimal.ONE;
	private static final BigDecimal FIVE = new BigDecimal(5);
	private static final BigDecimal TEN = BigDecimal.TEN;
	private static final BigDecimal N99 = new BigDecimal(99);
	private static final BigDecimal N100 = new BigDecimal(100);
	private static final BigDecimal N123 = new BigDecimal(123);
	private static final BigDecimal N150 = new BigDecimal(150);
	private static final BigDecimal N200 = new BigDecimal(200);
	private static final BigDecimal N201 = new BigDecimal(201);

	private OrderFreightCostsService orderFreightCostsService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		new PricingTestHelper();

		final FreightCostRepository freightCostRepo = new FreightCostRepository();
		final FreightCostService freightCostService = new FreightCostService(freightCostRepo);
		orderFreightCostsService = new OrderFreightCostsService(freightCostService, new BPartnerOrderParamsRepository());

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		de.metas.common.util.time.SystemTime.setFixedTimeSource("2019-07-10T16:11:23+01:00[Europe/Berlin]");
	}

	@Test
	public void orderWithFreightCost_FreightAmt()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");

		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FixPrice.getCode(),
				N123,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(N123));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostLine = orderLines.get(1);

		assertThat(freightCostLine.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostLine.getPriceActual(), comparesEqualTo(N123));

	}

	@Test
	public void orderWithFreightCost_FreightAmtFromPricelist()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");
		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FixPrice.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_PricingSystem pricingSystem = createPricingSystem("PricingSystem");
		order1.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList pricelist = createPriceList(pricingSystem.getM_PricingSystem_ID(), currency1);

		final I_C_TaxCategory taxCategory1 = createTaxCategory("TaxCategory1", country1.getC_Country_ID());

		createProductPrice(
				pricelist.getM_PriceList_ID(),
				freightCostProduct.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N123,
				taxCategory1.getC_TaxCategory_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostLine = orderLines.get(1);

		assertThat(freightCostLine.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostLine.getPriceActual(), comparesEqualTo(N123));

	}

	@Test
	public void orderWithFreightCost_Included()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");
		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FreightIncluded.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_PricingSystem pricingSystem = createPricingSystem("PricingSystem");
		order1.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList pricelist = createPriceList(pricingSystem.getM_PricingSystem_ID(), currency1);

		final I_C_TaxCategory taxCategory1 = createTaxCategory("TaxCategory1", country1.getC_Country_ID());

		createProductPrice(
				pricelist.getM_PriceList_ID(),
				freightCostProduct.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N123,
				taxCategory1.getC_TaxCategory_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(1));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

	}

	@Test
	public void orderWithFreightCost_NonCustomFreightCostRule_Calculated()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");
		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.Calculated.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_PricingSystem pricingSystem = createPricingSystem("PricingSystem");
		order1.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList pricelist = createPriceList(pricingSystem.getM_PricingSystem_ID(), currency1);

		final I_C_TaxCategory taxCategory1 = createTaxCategory("TaxCategory1", country1.getC_Country_ID());

		createProductPrice(
				pricelist.getM_PriceList_ID(),
				freightCostProduct.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N123,
				taxCategory1.getC_TaxCategory_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(1));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

	}

	@Test
	public void orderWithFreightCost_NonCustomFreightCostRule_Line()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");
		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.Line.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_PricingSystem pricingSystem = createPricingSystem("PricingSystem");
		order1.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList pricelist = createPriceList(pricingSystem.getM_PricingSystem_ID(), currency1);

		final I_C_TaxCategory taxCategory1 = createTaxCategory("TaxCategory1", country1.getC_Country_ID());

		createProductPrice(
				pricelist.getM_PriceList_ID(),
				freightCostProduct.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N123,
				taxCategory1.getC_TaxCategory_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(1));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

	}

	@Test
	public void orderWithFreightCost_FlatShippingFee_1break()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		partner1.setPaymentRule(PaymentRule.OnCredit.getCode());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");

		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FlatShippingFee.getCode(),
				N123,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				TEN,
				FIVE);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(N123));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostLine = orderLines.get(1);

		assertThat(freightCostLine.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostLine.getPriceActual(), comparesEqualTo(FIVE));
	}

	@Test
	public void orderWithFreightCost_FlatShippingFee_2breaks()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");
		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				N100,
				TEN);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				N200,
				FIVE);

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FlatShippingFee.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N150,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostOrderLine = orderLines.get(1);
		assertThat(freightCostOrderLine.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostOrderLine.getPriceActual(), comparesEqualTo(FIVE));

		final I_C_Order order2 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FlatShippingFee.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N99,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		orderFreightCostsService.addFreightRateLineIfNeeded(order2);

		Services.get(IDocumentBL.class).processIt(order2, IDocument.ACTION_Complete);

		assertThat(order2.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines2 = Services.get(IOrderDAO.class).retrieveOrderLines(order2);

		assertThat(orderLines2.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine2 = orderLines2.get(0);
		assertThat(productOrderLine2.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostOrderLine2 = orderLines2.get(1);
		assertThat(freightCostOrderLine2.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostOrderLine2.getPriceActual(), comparesEqualTo(TEN));

		final I_C_Order order3 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FlatShippingFee.getCode(),
				ZERO,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				N201,
				currency1,
				DeliveryViaRule.Shipper.getCode());

		orderFreightCostsService.addFreightRateLineIfNeeded(order3);

		Services.get(IDocumentBL.class).processIt(order3, IDocument.ACTION_Complete);

		assertThat(order3.getFreightAmt(), comparesEqualTo(ZERO));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines3 = Services.get(IOrderDAO.class).retrieveOrderLines(order3);

		assertThat(orderLines3.size(), comparesEqualTo(2));

		final de.metas.interfaces.I_C_OrderLine productOrderLine3 = orderLines3.get(0);
		assertThat(productOrderLine3.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

		final de.metas.interfaces.I_C_OrderLine freightCostOrderLine3 = orderLines3.get(1);
		assertThat(freightCostOrderLine3.getM_Product_ID(), comparesEqualTo(freightCostProduct.getM_Product_ID()));
		assertThat(freightCostOrderLine3.getPriceActual(), comparesEqualTo(ZERO));
	}

	@Test
	public void orderWithFreightCost_DeliveryViaRulePickup()
	{

		final I_C_BP_Group bpGroup = createBPGroup("BPGroup");

		final I_C_BPartner shipperPartner = createPartner("ShipperPartner", bpGroup.getC_BP_Group_ID());

		final I_M_Shipper shipper1 = createShipper("Shipper1", shipperPartner.getC_BPartner_ID());

		final I_C_BPartner partner1 = createPartner("Partner1", bpGroup.getC_BP_Group_ID());
		partner1.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		save(partner1);

		final CurrencyId currency1 = createCurrency(CurrencyCode.EUR);

		final I_C_Country country1 = createCountry("Country1", currency1);

		final I_C_BPartner_Location location1 = createBPartnerLocation(partner1.getC_BPartner_ID(), country1.getC_Country_ID());

		final I_M_Product_Category productCategory = createProductCategory("ProductCategory1");

		final I_C_UOM uom1 = createUOM("uom1");
		final I_M_Product product1 = createProduct("Product1", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_C_Order order1 = createSalesOrder(
				partner1.getC_BPartner_ID(),
				location1.getC_BPartner_Location_ID(),
				FreightCostRule.FixPrice.getCode(),
				N123,
				product1.getM_Product_ID(),
				uom1.getC_UOM_ID(),
				TEN,
				currency1,
				DeliveryViaRule.Pickup.getCode());

		final I_M_Product freightCostProduct = createProduct("FreightCostProduct", uom1.getC_UOM_ID(), productCategory.getM_Product_Category_ID());

		final I_M_FreightCost freightCost = createFreightCost(freightCostProduct.getM_Product_ID(), "FreightCost1");

		final Timestamp validForm = de.metas.common.util.time.SystemTime.asDayTimestamp();

		final I_M_FreightCostShipper freightCostShipper = createFreightCostShipper(freightCost.getM_FreightCost_ID(),
				shipper1.getM_Shipper_ID(),
				validForm);

		createFreightCostDetail(country1.getC_Country_ID(),
				currency1,
				freightCostShipper.getM_FreightCostShipper_ID(),
				ZERO,
				ZERO);

		orderFreightCostsService.addFreightRateLineIfNeeded(order1);

		Services.get(IDocumentBL.class).processIt(order1, IDocument.ACTION_Complete);

		assertThat(order1.getFreightAmt(), comparesEqualTo(N123));

		final List<de.metas.interfaces.I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order1);

		assertThat(orderLines.size(), comparesEqualTo(1));

		final de.metas.interfaces.I_C_OrderLine productOrderLine = orderLines.get(0);
		assertThat(productOrderLine.getM_Product_ID(), comparesEqualTo(product1.getM_Product_ID()));

	}

	private I_C_BP_Group createBPGroup(final String name)
	{
		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		bpGroup.setName(name);

		save(bpGroup);

		return bpGroup;
	}

	private I_C_BPartner createPartner(final String partnerName, final int bpGroupId)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(partnerName);
		partner.setC_BP_Group_ID(bpGroupId);
		partner.setPaymentRule(PaymentRule.OnCredit.getCode());

		save(partner);

		return partner;
	}

	private I_M_Shipper createShipper(final String shipperName, final int bpartnerId)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setName(shipperName);
		shipper.setC_BPartner_ID(bpartnerId);

		save(shipper);
		return shipper;
	}

	private CurrencyId createCurrency(final CurrencyCode code)
	{
		final Currency currency = PlainCurrencyDAO.prepareCurrency()
				.currencyCode(code)
				.build();

		return currency.getId();
	}

	private I_C_Country createCountry(final String countryName, final CurrencyId currencyId)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setName(countryName);
		country.setC_Currency_ID(currencyId.getRepoId());

		save(country);

		return country;
	}

	private I_C_BPartner_Location createBPartnerLocation(final int partnerId, final int countryId)
	{
		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId);
		location.setAddress1("Address1");

		save(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setC_BPartner_ID(partnerId);
		bpLocation.setIsBillTo(true);
		bpLocation.setIsShipTo(true);

		save(bpLocation);

		return bpLocation;
	}

	private I_M_Product_Category createProductCategory(final String name)
	{
		final I_M_Product_Category productCateogry = newInstance(I_M_Product_Category.class);
		productCateogry.setName(name);

		save(productCateogry);

		return productCateogry;
	}

	private I_C_UOM createUOM(final String uomName)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);

		uom.setName(uomName);
		uom.setUOMSymbol(uomName);

		save(uom);

		return uom;
	}

	private I_M_Product createProduct(final String productName, int uomId, final int productCategoryId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName(productName);
		product.setValue(productName);
		product.setC_UOM_ID(uomId);
		product.setM_Product_Category_ID(productCategoryId);

		save(product);

		return product;
	}

	private I_C_Order createSalesOrder(final int partnerId,
									   final int locationId,
									   final String freightCostRule,
									   final BigDecimal freightAmt,
									   final int productId,
									   final int uomId,
									   final BigDecimal price,
									   final CurrencyId currencyId,
									   final String deliveryViaRule)
	{
		final I_C_Order order = newInstance(I_C_Order.class);

		order.setIsSOTrx(true);
		order.setC_BPartner_ID(partnerId);
		order.setC_BPartner_Location_ID(locationId);
		order.setFreightCostRule(freightCostRule);
		order.setDeliveryViaRule(deliveryViaRule);
		order.setFreightAmt(freightAmt);
		order.setC_Currency_ID(currencyId.getRepoId());
		order.setDateOrdered(SystemTime.asDayTimestamp());

		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);

		orderLine.setM_Product_ID(productId);
		orderLine.setC_UOM_ID(uomId);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_Currency_ID(currencyId.getRepoId());
		orderLine.setPriceEntered(price);
		orderLine.setPriceActual(price);
		orderLine.setQtyEntered(ONE);
		orderLine.setQtyOrdered(ONE);

		save(orderLine);

		return order;
	}

	private I_M_FreightCost createFreightCost(final int freightCostProductId, final String name)
	{
		final I_M_FreightCost freightCost = newInstance(I_M_FreightCost.class);
		freightCost.setM_Product_ID(freightCostProductId);
		freightCost.setName(name);
		freightCost.setValue(name);
		freightCost.setIsDefault(true);

		save(freightCost);

		return freightCost;
	}

	private I_M_FreightCostShipper createFreightCostShipper(final int freightCostId, final int shipperId, final Timestamp validFrom)
	{
		final I_M_FreightCostShipper freightCostShipper = newInstance(I_M_FreightCostShipper.class);

		freightCostShipper.setM_FreightCost_ID(freightCostId);
		freightCostShipper.setM_Shipper_ID(shipperId);
		freightCostShipper.setValidFrom(validFrom);

		save(freightCostShipper);

		return freightCostShipper;

	}

	private I_M_FreightCostDetail createFreightCostDetail(
			final int countryId,
			final CurrencyId currencyId,
			final int freightCostShipperId,
			final BigDecimal shipmentValueAmt,
			final BigDecimal freightAmt)
	{
		final I_M_FreightCostDetail freightCostDetail = newInstance(I_M_FreightCostDetail.class);
		freightCostDetail.setC_Country_ID(countryId);
		freightCostDetail.setC_Currency_ID(currencyId.getRepoId());
		freightCostDetail.setM_FreightCostShipper_ID(freightCostShipperId);
		freightCostDetail.setShipmentValueAmt(shipmentValueAmt);
		freightCostDetail.setFreightAmt(freightAmt);

		save(freightCostDetail);

		return freightCostDetail;
	}

	private I_M_PricingSystem createPricingSystem(final String name)
	{
		final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
		pricingSystem.setName(name);
		pricingSystem.setValue(name);

		save(pricingSystem);

		return pricingSystem;
	}

	private I_M_PriceList createPriceList(final int pricingSystemId, final CurrencyId currencyId)
	{
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystemId);
		priceList.setC_Currency_ID(currencyId.getRepoId());
		priceList.setIsSOPriceList(true);

		save(priceList);

		return priceList;
	}

	private I_C_TaxCategory createTaxCategory(final String taxCateogryName, final int countryId)
	{
		final I_C_TaxCategory taxCateogry = newInstance(I_C_TaxCategory.class);

		taxCateogry.setName(taxCateogryName);

		save(taxCateogry);

		final I_C_Tax tax = newInstance(I_C_Tax.class);

		tax.setC_TaxCategory_ID(taxCateogry.getC_TaxCategory_ID());
		tax.setC_Country_ID(countryId);
		tax.setValidFrom(de.metas.common.util.time.SystemTime.asDayTimestamp());

		save(tax);

		return taxCateogry;
	}

	private I_M_ProductPrice createProductPrice(
			final int priceListId,
			final int productId,
			final int uomId,
			final BigDecimal price,
			final int taxCategoryId)
	{

		final I_M_PriceList_Version pricelistVersion = newInstance(I_M_PriceList_Version.class);
		pricelistVersion.setM_PriceList_ID(priceListId);
		pricelistVersion.setValidFrom(de.metas.common.util.time.SystemTime.asDayTimestamp());
		save(pricelistVersion);

		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_PriceList_Version_ID(pricelistVersion.getM_PriceList_Version_ID());
		productPrice.setM_Product_ID(productId);
		productPrice.setIsAttributeDependant(false);
		productPrice.setC_TaxCategory_ID(taxCategoryId);
		productPrice.setC_UOM_ID(uomId);
		productPrice.setPriceStd(price);

		save(productPrice);

		return productPrice;
	}
}
