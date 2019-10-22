package de.metas.pricing.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.time.FixedTimeSource;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PriceListDAOTest
{
	private static I_M_PricingSystem pricingSystem;
	private static I_C_Country country;

	private static CurrencyId EURO;

	private final IPriceListDAO priceListDAO = new PlainPriceListDAO();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		pricingSystem = createPricingSystem("TEST PS", "TEST PS");
		country = createCountry();

		EURO = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		SystemTime.setTimeSource(new FixedTimeSource(2019, 8, 27, 14, 17, 23));
	}

	@Test
	public void test_retrievePriceListByPricingSyst_WithCountryMatched()
	{
		createPriceList(pricingSystem,
				"test price list",
				true,
				country,
				EURO);

		final I_M_PriceList pl2 = createPriceList(pricingSystem,
				"test price list",
				true,
				null,
				EURO);

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

		final PriceListId plId = priceListDAO.retrievePriceListIdByPricingSyst(
				PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()),
				toBPartnerLocationId(bpl),
				SOTrx.SALES);

		assertThat(plId).isNotNull();
		assertThat(plId.getRepoId()).isEqualByComparingTo(pl2.getM_PriceList_ID());
	}

	@Test
	public void test_retrievePriceListByPricingSyst_WithCountryNull()
	{
		final I_M_PriceList pl1 = createPriceList(pricingSystem,
				"test price list",
				true,
				country,
				EURO);

		createPriceList(pricingSystem,
				"test price list",
				true,
				country,
				EURO);

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

		final PriceListId plId = priceListDAO.retrievePriceListIdByPricingSyst(
				PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()),
				toBPartnerLocationId(bpl),
				SOTrx.SALES);

		assertThat(plId).isNotNull();
		assertThat(plId.getRepoId()).isEqualByComparingTo(pl1.getM_PriceList_ID());
	}

	private BPartnerLocationId toBPartnerLocationId(@NonNull final I_C_BPartner_Location bplRecord)
	{
		return BPartnerLocationId.ofRepoId(
				bplRecord.getC_BPartner_ID(),
				bplRecord.getC_BPartner_Location_ID());
	}

	@Test
	public void mutateCustomerPrices_basedOnProducts()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;

		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		final BigDecimal customerSurcharge3 = new BigDecimal(666);
		createSchemaLine(customerSchema, -1, product3.getM_Product_ID(), customerSurcharge3);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customerSchema.getM_DiscountSchema_ID());

		assertThat(newestPriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestPriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newProductPrices = newProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newProductPrices.size()).isEqualTo(3);

		final Optional<I_M_ProductPrice> productPrice1OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productPrice1OrNull).isNotNull();

		final I_M_ProductPrice productPrice1 = productPrice1OrNull.get();

		assertThat(productPrice1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customerSurcharge1));

		final Optional<I_M_ProductPrice> productPrice2OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productPrice2OrNull).isNotNull();

		final I_M_ProductPrice productPrice2 = productPrice2OrNull.get();

		assertThat(productPrice2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customerSurcharge2));

		final Optional<I_M_ProductPrice> productPrice3OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product3.getM_Product_ID())
				.findFirst();

		assertThat(productPrice3OrNull).isNotNull();

		final I_M_ProductPrice productPrice3 = productPrice3OrNull.get();

		assertThat(productPrice3.getPriceStd()).isEqualByComparingTo(basePriceProduct3.add(customerSurcharge3));

	}

	@Test
	public void mutateCustomerPrices_multipleCustomers()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;

		// Customer 1
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		final BigDecimal customerSurcharge3 = new BigDecimal(666);
		createSchemaLine(customerSchema, -1, product3.getM_Product_ID(), customerSurcharge3);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Customer2

		final I_M_PricingSystem customer2PricingSystem = createPricingSystem(
				"CUSTOMER 2 PRICING SYSTEM",
				"CUSTOMER 2 PRICING SYSTEM");

		createPartner("Customer2",
				isAllowPriceMutation,
				isCustomer,
				customer2PricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customer2PriceList = createPriceList(
				customer2PricingSystem,
				"CUSTOMER 2 PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customer2Schema = createSchema(oldValidFrom);

		final BigDecimal customer2Surcharge1 = new BigDecimal(123);
		createSchemaLine(customer2Schema, -1, product1.getM_Product_ID(), customer2Surcharge1);

		final BigDecimal customer2Surcharge2 = new BigDecimal(456);
		createSchemaLine(customer2Schema, -1, product2.getM_Product_ID(), customer2Surcharge2);

		final BigDecimal customer2Surcharge3 = new BigDecimal(789);
		createSchemaLine(customer2Schema, -1, product3.getM_Product_ID(), customer2Surcharge3);

		final I_M_PriceList_Version customer2OldPLV = createPLV(customer2PriceList.getM_PriceList_ID(),
				oldValidFrom,
				customer2Schema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal custom2PriceProduct1 = new BigDecimal(4);
		createProductPrice(product1, custom2PriceProduct1, customer2OldPLV.getM_PriceList_Version_ID());

		final BigDecimal custom2PriceProduct2 = new BigDecimal(5);
		createProductPrice(product2, custom2PriceProduct2, customer2OldPLV.getM_PriceList_Version_ID());

		final BigDecimal custom2PriceProduct3 = new BigDecimal(6);
		createProductPrice(product3, custom2PriceProduct3, customer2OldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		// Customer 1
		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customerSchema.getM_DiscountSchema_ID());

		assertThat(newestPriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestPriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newProductPrices = newProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newProductPrices.size()).isEqualTo(3);

		final Optional<I_M_ProductPrice> productPrice1OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productPrice1OrNull).isNotNull();

		final I_M_ProductPrice productPrice1 = productPrice1OrNull.get();

		assertThat(productPrice1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customerSurcharge1));

		final Optional<I_M_ProductPrice> productPrice2OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productPrice2OrNull).isNotNull();

		final I_M_ProductPrice productPrice2 = productPrice2OrNull.get();

		assertThat(productPrice2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customerSurcharge2));

		final Optional<I_M_ProductPrice> productPrice3OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product3.getM_Product_ID())
				.findFirst();

		assertThat(productPrice3OrNull).isNotNull();

		final I_M_ProductPrice productPrice3 = productPrice3OrNull.get();

		assertThat(productPrice3.getPriceStd()).isEqualByComparingTo(basePriceProduct3.add(customerSurcharge3));

		// Customer 2

		final I_M_PriceList_Version newestCustomer2PriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customer2PriceList.getM_PriceList_ID()));

		assertThat(newestCustomer2PriceListVersion).isNotNull();

		assertThat(newestCustomer2PriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customer2OldPLV.getM_PriceList_Version_ID());

		assertThat(newestCustomer2PriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestCustomer2PriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customer2Schema.getM_DiscountSchema_ID());

		assertThat(newestCustomer2PriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newCustomer2ProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestCustomer2PriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newCustomer2ProductPrices = newCustomer2ProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newCustomer2ProductPrices.size()).isEqualTo(3);

		final Optional<I_M_ProductPrice> productCustomer2Price1OrNull = newCustomer2ProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productCustomer2Price1OrNull).isNotNull();

		final I_M_ProductPrice productCustomer2Price1 = productCustomer2Price1OrNull.get();

		assertThat(productCustomer2Price1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customer2Surcharge1));

		final Optional<I_M_ProductPrice> productCustomer2Price2OrNull = newCustomer2ProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productCustomer2Price2OrNull).isNotNull();

		final I_M_ProductPrice productCustomer2Price2 = productCustomer2Price2OrNull.get();

		assertThat(productCustomer2Price2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customer2Surcharge2));

		final Optional<I_M_ProductPrice> productCustomer2Price3OrNull = newCustomer2ProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product3.getM_Product_ID())
				.findFirst();

		assertThat(productCustomer2Price3OrNull).isNotNull();

		final I_M_ProductPrice productCustomer2Price3 = productCustomer2Price3OrNull.get();

		assertThat(productCustomer2Price3.getPriceStd()).isEqualByComparingTo(basePriceProduct3.add(customer2Surcharge3));
	}

	@Test
	public void mutateCustomerPrices_onlyForProductsFromCustomerSchema()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customerSchema.getM_DiscountSchema_ID());

		assertThat(newestPriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestPriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newProductPrices = newProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newProductPrices.size()).isEqualTo(2);

		final Optional<I_M_ProductPrice> productPrice1OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productPrice1OrNull).isNotNull();

		final I_M_ProductPrice productPrice1 = productPrice1OrNull.get();

		assertThat(productPrice1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customerSurcharge1));

		final Optional<I_M_ProductPrice> productPrice2OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productPrice2OrNull).isNotNull();

		final I_M_ProductPrice productPrice2 = productPrice2OrNull.get();

		assertThat(productPrice2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customerSurcharge2));

	}

	@Test
	public void mutateCustomerPrices_noCustomerSchema()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				-1,
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

	}

	@Test
	public void mutateCustomerPrices_customerHasIntermediatePLVs()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		final BigDecimal customerSurcharge3 = new BigDecimal(666);
		createSchemaLine(customerSchema, -1, product3.getM_Product_ID(), customerSurcharge3);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		final Timestamp intermediateValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 5, 1));

		final I_M_DiscountSchema customerIntermediateSchema = createSchema(intermediateValidFrom);

		createSchemaLine(customerIntermediateSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		createSchemaLine(customerIntermediateSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		createSchemaLine(customerIntermediateSchema, -1, product3.getM_Product_ID(), customerSurcharge3);

		final I_M_PriceList_Version customerIntermediatePLV = createPLV(customerPriceList.getM_PriceList_ID(),
				intermediateValidFrom,
				customerIntermediateSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal intermediateCustomPriceProduct1 = new BigDecimal(4);
		createProductPrice(product1, intermediateCustomPriceProduct1, customerIntermediatePLV.getM_PriceList_Version_ID());

		final BigDecimal intermediateCustomPriceProduct2 = new BigDecimal(5);
		createProductPrice(product2, intermediateCustomPriceProduct2, customerIntermediatePLV.getM_PriceList_Version_ID());

		final BigDecimal intermediateCustomPriceProduct3 = new BigDecimal(6);
		createProductPrice(product3, intermediateCustomPriceProduct3, customerIntermediatePLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final int versionsCount = countPLVs(customerPriceList);

		assertThat(versionsCount).isEqualByComparingTo(3);
		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerIntermediatePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customerIntermediatePLV.getM_DiscountSchema_ID());

		assertThat(newestPriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestPriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newProductPrices = newProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newProductPrices.size()).isEqualTo(3);

		final Optional<I_M_ProductPrice> productPrice1OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productPrice1OrNull).isNotNull();

		final I_M_ProductPrice productPrice1 = productPrice1OrNull.get();

		assertThat(productPrice1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customerSurcharge1));

		final Optional<I_M_ProductPrice> productPrice2OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productPrice2OrNull).isNotNull();

		final I_M_ProductPrice productPrice2 = productPrice2OrNull.get();

		assertThat(productPrice2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customerSurcharge2));

		final Optional<I_M_ProductPrice> productPrice3OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product3.getM_Product_ID())
				.findFirst();

		assertThat(productPrice3OrNull).isNotNull();

		final I_M_ProductPrice productPrice3 = productPrice3OrNull.get();

		assertThat(productPrice3.getPriceStd()).isEqualByComparingTo(basePriceProduct3.add(customerSurcharge3));

	}

	private int countPLVs(final I_M_PriceList priceList)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMN_M_PriceList_ID, priceList.getM_PriceList_ID())
				.create()
				.count();

	}

	@Test
	public void mutateCustomerPrices_partnerNotAllowedPriceMutation()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, -1, product1.getM_Product_ID(), baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, -1, product2.getM_Product_ID(), baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, -1, product3.getM_Product_ID(), baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = false;
		final boolean isCustomer = true;
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, -1, product1.getM_Product_ID(), customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, -1, product2.getM_Product_ID(), customerSurcharge2);

		final BigDecimal customerSurcharge3 = new BigDecimal(666);
		createSchemaLine(customerSchema, -1, product3.getM_Product_ID(), customerSurcharge3);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

	}

	@Test
	public void mutateCustomerPrices_basedOnProductCategories()
	{
		final I_AD_User user = createUser("User");

		final I_M_PricingSystem originalBasePricingSystem = createPricingSystem(
				"ORIGINAL BASE PRICING SYSTEM",
				"ORIGINAL BASE PRICING SYSTEM");

		final I_M_PriceList originalBasePriceList = createPriceList(
				originalBasePricingSystem,
				"ORIGINAL BASE PRICELIST",
				true,
				country,
				EURO);

		final Timestamp oldValidFrom = TimeUtil.asTimestamp(LocalDate.of(2019, 1, 1));

		final I_M_Product_Category productCategory1 = createProductCategory("ProductCategory1");
		final I_M_Product product1 = createProduct(productCategory1.getM_Product_Category_ID(), "Product1");

		final I_M_Product_Category productCategory2 = createProductCategory("ProductCategory2");
		final I_M_Product product2 = createProduct(productCategory2.getM_Product_Category_ID(), "Product2");

		final I_M_Product_Category productCategory3 = createProductCategory("ProductCategory3");
		final I_M_Product product3 = createProduct(productCategory3.getM_Product_Category_ID(), "Product3");

		final I_M_DiscountSchema baseSchema = createSchema(oldValidFrom);

		final BigDecimal baseSurcharge1 = new BigDecimal(10);
		createSchemaLine(baseSchema, productCategory1.getM_Product_Category_ID(), -1, baseSurcharge1);

		final BigDecimal baseSurcharge2 = new BigDecimal(20);
		createSchemaLine(baseSchema, productCategory2.getM_Product_Category_ID(), -1, baseSurcharge2);

		final BigDecimal baseSurcharge3 = new BigDecimal(30);
		createSchemaLine(baseSchema, productCategory3.getM_Product_Category_ID(), -1, baseSurcharge3);

		final I_M_PriceList_Version originalBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				oldValidFrom,
				baseSchema.getM_DiscountSchema_ID(),
				-1);

		final BigDecimal basePriceProduct1 = new BigDecimal(111);
		createProductPrice(product1, basePriceProduct1, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct2 = new BigDecimal(222);
		createProductPrice(product2, basePriceProduct2, originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal basePriceProduct3 = new BigDecimal(333);
		createProductPrice(product3, basePriceProduct3, originalBasePLV.getM_PriceList_Version_ID());

		final I_M_PricingSystem customerPricingSystem = createPricingSystem(
				"CUSTOMER PRICING SYSTEM",
				"CUSTOMER PRICING SYSTEM");

		final boolean isAllowPriceMutation = true;
		final boolean isCustomer = true;
		createPartner("Customer1",
				isAllowPriceMutation,
				isCustomer,
				customerPricingSystem.getM_PricingSystem_ID());

		final I_M_PriceList customerPriceList = createPriceList(
				customerPricingSystem,
				"CUSTOMER PRICELIST",
				true,
				country,
				EURO);

		final I_M_DiscountSchema customerSchema = createSchema(oldValidFrom);

		final BigDecimal customerSurcharge1 = new BigDecimal(444);
		createSchemaLine(customerSchema, productCategory1.getM_Product_Category_ID(), -1, customerSurcharge1);

		final BigDecimal customerSurcharge2 = new BigDecimal(555);
		createSchemaLine(customerSchema, productCategory2.getM_Product_Category_ID(), -1, customerSurcharge2);

		final BigDecimal customerSurcharge3 = new BigDecimal(666);
		createSchemaLine(customerSchema, productCategory3.getM_Product_Category_ID(), -1, customerSurcharge3);

		final I_M_PriceList_Version customerOldPLV = createPLV(customerPriceList.getM_PriceList_ID(),
				oldValidFrom,
				customerSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct1 = new BigDecimal(1);
		createProductPrice(product1, customPriceProduct1, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct2 = new BigDecimal(2);
		createProductPrice(product2, customPriceProduct2, customerOldPLV.getM_PriceList_Version_ID());

		final BigDecimal customPriceProduct3 = new BigDecimal(3);
		createProductPrice(product3, customPriceProduct3, customerOldPLV.getM_PriceList_Version_ID());

		// Here starts the testing of the Price Mutation functionality

		final I_M_PriceList_Version newBasePLV = createPLV(originalBasePriceList.getM_PriceList_ID(),
				SystemTime.asDayTimestamp(),
				baseSchema.getM_DiscountSchema_ID(),
				originalBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product1, basePriceProduct1.add(baseSurcharge1), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product2, basePriceProduct2.add(baseSurcharge2), newBasePLV.getM_PriceList_Version_ID());

		createProductPrice(product3, basePriceProduct3.add(baseSurcharge3), newBasePLV.getM_PriceList_Version_ID());

		priceListDAO.mutateCustomerPrices(PriceListVersionId.ofRepoId(newBasePLV.getM_PriceList_Version_ID()), UserId.ofRepoId(user.getAD_User_ID()));

		final I_M_PriceList_Version newestPriceListVersion = priceListDAO.retrieveNewestPriceListVersion(PriceListId.ofRepoId(customerPriceList.getM_PriceList_ID()));

		assertThat(newestPriceListVersion).isNotNull();

		assertThat(newestPriceListVersion.getM_PriceList_Version_ID()).isNotEqualByComparingTo(customerOldPLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_Pricelist_Version_Base_ID()).isEqualByComparingTo(originalBasePLV.getM_PriceList_Version_ID());

		assertThat(newestPriceListVersion.getM_DiscountSchema_ID()).isEqualByComparingTo(customerSchema.getM_DiscountSchema_ID());

		assertThat(newestPriceListVersion.getValidFrom()).isEqualTo(SystemTime.asDayTimestamp());

		final Stream<I_M_ProductPrice> newProductPricesStream = priceListDAO.retrieveProductPrices(PriceListVersionId.ofRepoId(newestPriceListVersion.getM_PriceList_Version_ID()),
				ImmutableSet.of());

		final ImmutableList<I_M_ProductPrice> newProductPrices = newProductPricesStream.collect(ImmutableList.toImmutableList());

		assertThat(newProductPrices.size()).isEqualTo(3);

		final Optional<I_M_ProductPrice> productPrice1OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product1.getM_Product_ID())
				.findFirst();

		assertThat(productPrice1OrNull).isNotNull();

		final I_M_ProductPrice productPrice1 = productPrice1OrNull.get();

		assertThat(productPrice1.getPriceStd()).isEqualByComparingTo(basePriceProduct1.add(customerSurcharge1));

		final Optional<I_M_ProductPrice> productPrice2OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product2.getM_Product_ID())
				.findFirst();

		assertThat(productPrice2OrNull).isNotNull();

		final I_M_ProductPrice productPrice2 = productPrice2OrNull.get();

		assertThat(productPrice2.getPriceStd()).isEqualByComparingTo(basePriceProduct2.add(customerSurcharge2));

		final Optional<I_M_ProductPrice> productPrice3OrNull = newProductPrices.stream()
				.filter(pp -> pp.getM_Product_ID() == product3.getM_Product_ID())
				.findFirst();

		assertThat(productPrice3OrNull).isNotNull();

		final I_M_ProductPrice productPrice3 = productPrice3OrNull.get();

		assertThat(productPrice3.getPriceStd()).isEqualByComparingTo(basePriceProduct3.add(customerSurcharge3));

	}

	private I_M_PricingSystem createPricingSystem(final String value, final String name)
	{
		final I_M_PricingSystem ps = newInstance(I_M_PricingSystem.class);
		ps.setValue(value);
		ps.setName(name);
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

	private I_M_PriceList createPriceList(
			final I_M_PricingSystem pricingSystem,
			final String name,
			final boolean isSOTrx,
			final I_C_Country country,
			final CurrencyId currencyId)
	{
		final I_M_PriceList pl = newInstance(I_M_PriceList.class);
		pl.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		pl.setName(name);
		pl.setIsSOPriceList(isSOTrx);
		pl.setC_Country_ID(country != null ? country.getC_Country_ID() : -1);
		pl.setC_Currency_ID(currencyId.getRepoId());
		save(pl);
		return pl;
	}

	private I_AD_User createUser(final String userName)
	{
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setName(userName);
		save(user);

		return user;
	}

	private I_M_Product_Category createProductCategory(final String name)
	{
		final I_M_Product_Category cateogry = newInstance(I_M_Product_Category.class);
		cateogry.setName(name);

		save(cateogry);
		return cateogry;
	}

	private I_M_Product createProduct(final int productCategoryId, final String name)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(productCategoryId);
		product.setValue(name);
		product.setName(name);

		save(product);
		return product;
	}

	private static I_M_DiscountSchema createSchema(final Timestamp validFrom)
	{
		final I_M_DiscountSchema schema = newInstance(I_M_DiscountSchema.class);
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Pricelist);
		schema.setValidFrom(validFrom);
		save(schema);
		return schema;
	}

	private void createSchemaLine(final I_M_DiscountSchema schema,
			final int productCategoryId,
			final int productId,
			final BigDecimal surcharge)
	{
		final I_M_DiscountSchemaLine line = newInstance(I_M_DiscountSchemaLine.class);
		line.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		line.setM_Product_ID(productId);
		line.setM_Product_Category_ID(productCategoryId);
		line.setStd_AddAmt(surcharge);

		save(line);

	}

	private I_M_PriceList_Version createPLV(final int priceListId,
			Timestamp validFrom,
			int schemaId,
			int basePLVId)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class);

		plv.setM_PriceList_ID(priceListId);
		plv.setValidFrom(validFrom);
		plv.setM_DiscountSchema_ID(schemaId);
		plv.setM_Pricelist_Version_Base_ID(basePLVId);

		save(plv);

		return plv;
	}

	private void createProductPrice(final I_M_Product product,
			final BigDecimal price,
			final int plvId)
	{
		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_Product_ID(product.getM_Product_ID());
		productPrice.setM_PriceList_Version_ID(plvId);
		productPrice.setPriceStd(price);

		save(productPrice);
	}

	private I_C_BPartner createPartner(final String name,
			final boolean isAllowPriceMutation,
			final boolean isCustomer,
			final int customerPricingSystemId)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setIsAllowPriceMutation(isAllowPriceMutation);
		partner.setIsCustomer(isCustomer);
		partner.setM_PricingSystem_ID(customerPricingSystemId);

		save(partner);

		return partner;
	}

	public static I_M_DiscountSchemaLine createLine(final I_M_DiscountSchema schema, final int seqNo)
	{
		final I_M_DiscountSchemaLine schemaLine = newInstance(I_M_DiscountSchemaLine.class);
		schemaLine.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaLine.setSeqNo(seqNo);
		save(schemaLine);
		return schemaLine;
	}
}
