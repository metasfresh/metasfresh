package de.metas.pricing.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

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

import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.time.FixedTimeSource;
import de.metas.util.time.SystemTime;

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

		final I_M_PriceList pl = priceListDAO.retrievePriceListByPricingSyst(PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()), bpl, SOTrx.SALES);

		assertThat(pl).isNotNull();
		assertThat(pl.getM_PriceList_ID()).isEqualByComparingTo(pl2.getM_PriceList_ID());
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

		final I_M_PriceList pl = priceListDAO.retrievePriceListByPricingSyst(PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()), bpl, SOTrx.SALES);

		assertThat(pl).isNotNull();
		assertThat(pl.getM_PriceList_ID()).isEqualByComparingTo(pl1.getM_PriceList_ID());
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

	private I_M_PriceList createPriceList(final I_M_PricingSystem pricingSystem,
			final String name,
			final boolean isSOTrx,
			final I_C_Country country,
			final CurrencyId currencyId)
	{
		final I_M_PriceList pl = newInstance(I_M_PriceList.class);
		pl.setM_PricingSystem(pricingSystem);
		pl.setName(name);
		pl.setIsSOPriceList(isSOTrx);
		pl.setC_Country(country);
		pl.setC_Currency_ID(currencyId.getRepoId());
		save(pl);
		return pl;
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

	private I_M_Product createProduct(final int productCategoryId, final String name)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(productCategoryId);
		product.setValue(name);
		product.setName(name);

		save(product);
		return product;
	}

	private I_M_Product_Category createProductCategory(final String name)
	{
		final I_M_Product_Category cateogry = newInstance(I_M_Product_Category.class);
		cateogry.setName(name);

		save(cateogry);
		return cateogry;
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

	private static I_M_DiscountSchema createSchema(final Timestamp validFrom)
	{
		final I_M_DiscountSchema schema = newInstance(I_M_DiscountSchema.class);
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Pricelist);
		schema.setValidFrom(validFrom);
		save(schema);
		return schema;
	}

	private I_AD_User createUser(final String userName)
	{
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setName(userName);
		save(user);

		return user;
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
