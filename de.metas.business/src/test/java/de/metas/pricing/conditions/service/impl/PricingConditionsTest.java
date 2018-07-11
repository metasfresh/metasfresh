package de.metas.pricing.conditions.service.impl;

import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createAttr;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createAttrValue;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createAttributeInstance;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createBreak;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createLine;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createM_Product;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createM_ProductCategory;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createQueryForQty;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createQueryForQtyAndAttributeValues;
import static de.metas.pricing.conditions.service.impl.PricingConditionsTestUtils.createSchema;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.lang.Percent;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.product.ProductAndCategoryAndManufacturerId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, PaymentTermService.class })
public class PricingConditionsTest
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private PricingConditionsRepository repo;
	private PricingConditionsService service;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		repo = new PricingConditionsRepository();
		service = new PricingConditionsService();
		Services.registerService(IPricingConditionsRepository.class, repo);
	}

	@Test
	public void testRetrieveBreaks()
	{
		final I_M_DiscountSchema schema1 = PricingConditionsTestUtils.createSchema();
		final I_M_DiscountSchema schema2 = PricingConditionsTestUtils.createSchema();

		final I_M_DiscountSchemaBreak schemaBreak1 = PricingConditionsTestUtils.createBreak(schema1, 10);
		final I_M_DiscountSchemaBreak schemaBreak2 = PricingConditionsTestUtils.createBreak(schema1, 20);

		final I_M_DiscountSchemaBreak schemaBreak3 = PricingConditionsTestUtils.createBreak(schema2, 10);

		final List<I_M_DiscountSchemaBreak> breaks = repo.streamSchemaBreakRecords(Env.getCtx(), ImmutableList.of(schema1.getM_DiscountSchema_ID()), ITrx.TRXNAME_ThreadInherited)
				.collect(ImmutableList.toImmutableList());

		assertThat(breaks).hasSize(2);
		assertThat(breaks).contains(schemaBreak1, schemaBreak2);
		assertThat(breaks).doesNotContain(schemaBreak3);
	}

	@Test
	public void testRetrieveLines()
	{
		final I_M_DiscountSchema schema1 = PricingConditionsTestUtils.createSchema();
		final I_M_DiscountSchema schema2 = PricingConditionsTestUtils.createSchema();

		final I_M_DiscountSchemaLine schemaLine1 = createLine(schema1, 10);
		final I_M_DiscountSchemaLine schemaLine2 = createLine(schema1, 20);

		final I_M_DiscountSchemaLine schemaLine3 = createLine(schema2, 10);

		final List<I_M_DiscountSchemaLine> lines = repo.retrieveLines(Env.getCtx(), schema1.getM_DiscountSchema_ID(), ITrx.TRXNAME_ThreadInherited);

		assertThat(lines).hasSize(2);
		assertThat(lines).contains(schemaLine1, schemaLine2);
		assertThat(lines).doesNotContain(schemaLine3);
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_for_same_product_FirstBreak()
	{
		final PricingConditionsTestData1 data = PricingConditionsTestData1.newInstance();
		data.assertSchemaBreakIdForQty(15).isEqualTo(data.break10.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_for_same_product_SecondBreak()
	{
		final PricingConditionsTestData1 data = PricingConditionsTestData1.newInstance();
		data.assertSchemaBreakIdForQty(25).isEqualTo(data.break20.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_with_no_product()
	{
		final PricingConditionsTestData1 data = PricingConditionsTestData1.newInstance();
		{
			data.break20.setM_Product_ID(-1);
			save(data.break20);
		}

		data.assertSchemaBreakIdForQty(25).isEqualTo(data.break20.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_NoAttributes_1()
	{
		final I_M_Product product = createM_Product("Product1");

		final I_M_DiscountSchema schema = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak = createBreak(schema, 10);
		schemaBreak.setM_Product_Category_ID(product.getM_Product_Category_ID());
		schemaBreak.setM_Product_ID(product.getM_Product_ID());
		save(schemaBreak);

		final PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema));
		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQty(product, 15));

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak));
	}

	@Test
	public void testPickApplyingBreak_NoAttributes_2()
	{
		final I_M_Product product = createM_Product("Product1");

		final I_M_DiscountSchema schema = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak = createBreak(schema, 10);
		schemaBreak.setM_Product_Category_ID(product.getM_Product_Category_ID());
		schemaBreak.setM_Product_ID(-1);
		save(schemaBreak);

		final PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema));
		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQty(product, 15));

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak));
	}

	@Test
	public void testPickApplyingBreak_NoAttributes_3()
	{
		final I_M_Product product = createM_Product("Product1");

		final I_M_DiscountSchema schema = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak = createBreak(schema, 10);
		schemaBreak.setM_Product_Category_ID(-1);
		schemaBreak.setM_Product_ID(-1);
		save(schemaBreak);

		final PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema));
		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQty(product, 15));

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak));
	}

	@Test
	public void testPickApplyingBreak_AttributeValue()
	{
		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");
		final I_M_AttributeValue attrValue2 = createAttrValue(attr1, "AttrValue2");

		final I_M_DiscountSchema schema1 = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(BigDecimal.valueOf(1));
		save(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(BigDecimal.valueOf(2));
		save(schemaBreak2);

		PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema1));

		//
		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQtyAndAttributeValues(product1, 15, attrValue1));
		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak1));

		//
		final PricingConditionsBreak actualSchemaBreak2 = pricingConditions.pickApplyingBreak(createQueryForQty(product1, 15));
		assertThat(actualSchemaBreak2).isNull();

		//
		final PricingConditionsBreak actualSchemaBreak3 = pricingConditions.pickApplyingBreak(createQueryForQtyAndAttributeValues(product1, 15, attrValue2));
		assertThat(actualSchemaBreak3).isNotNull();
		assertThat(actualSchemaBreak3.getId()).isEqualTo(id(schemaBreak2));

		// test also if seqNo is still respected
		schemaBreak1.setM_AttributeValue(null);
		save(schemaBreak1);
		pricingConditions = repo.retrievePricingConditionsById(id(schema1));

		final PricingConditionsBreak actualSchemaBreak4 = pricingConditions.pickApplyingBreak(createQueryForQtyAndAttributeValues(product1, 15, attrValue2));
		assertThat(actualSchemaBreak4).isNotNull();
		assertThat(actualSchemaBreak4.getId()).isEqualTo(id(schemaBreak1));
	}

	@Test
	public void testPickApplyingBreak_AttributeInstances_EmptyInstance()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");

		final I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		save(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(null);
		save(schemaBreak2);

		final PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema1));

		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQty(product1, 15));
		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak2));
	}

	@Test
	public void testPickApplyingBreak_AttributeInstances()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");
		final I_M_AttributeValue attrValue2 = createAttrValue(attr1, "AttrValue2");

		final I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		save(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		save(schemaBreak2);

		final PricingConditions pricingConditions = repo.retrievePricingConditionsById(id(schema1));

		final PricingConditionsBreak actualSchemaBreak1 = pricingConditions.pickApplyingBreak(createQueryForQtyAndAttributeValues(product1, 15, attrValue1, attrValue2));
		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(actualSchemaBreak1.getId()).isEqualTo(id(schemaBreak1));
	}

	@Test
	public void testCalculatePrice_NoInstances()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");
		final I_M_AttributeValue attrValue2 = createAttrValue(attr1, "AttrValue2");

		final I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));
		save(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));
		save(schemaBreak2);

		final BigDecimal price = BigDecimal.valueOf(1);

		// Discount 0 (because no breaks were applied)
		final CalculatePricingConditionsRequest request = CalculatePricingConditionsRequest.builder()
				.pricingConditionsId(id(schema1))
				.pricingConditionsBreakQuery(PricingConditionsBreakQuery.builder()
						.product(productAndCategoryId(product1))
						.qty(new BigDecimal(100))
						.price(price)
						.build())
				.bpartnerFlatDiscount(Percent.of(1))
				.build();

		final BigDecimal priceAfterConditions1 = calculatePrice(price, request);

		assertThat(priceAfterConditions1).isEqualByComparingTo(BigDecimal.ONE);

		schemaBreak1.setM_AttributeValue(null);
		save(schemaBreak1);

		final BigDecimal priceAfterConditions2 = calculatePrice(price, request);
		assertThat(priceAfterConditions2).isEqualByComparingTo(new BigDecimal("0.500000"));
	}

	@Test
	public void testCalculatePrice_Instances()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");
		final I_M_AttributeValue attrValue2 = createAttrValue(attr1, "AttrValue2");

		final I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));
		save(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));
		save(schemaBreak2);

		final BigDecimal price = BigDecimal.valueOf(1);

		// Discount 0 (because no breaks were applied)
		final CalculatePricingConditionsRequest request = CalculatePricingConditionsRequest.builder()
				.pricingConditionsId(id(schema1))
				.pricingConditionsBreakQuery(PricingConditionsBreakQuery.builder()
						.product(productAndCategoryId(product1))
						.qty(new BigDecimal(100))
						.price(price)
						.attributeInstance(createAttributeInstance(attr1, attrValue1))
						.attributeInstance(createAttributeInstance(attr1, attrValue2))
						.build())
				.bpartnerFlatDiscount(Percent.of(1))
				.build();

		final BigDecimal priceAfterConditions1 = calculatePrice(price, request);
		assertThat(priceAfterConditions1).isEqualByComparingTo(new BigDecimal("0.500000"));

		final I_M_AttributeValue attrValue3 = createAttrValue(attr1, "Attr Value 3");
		schemaBreak1.setM_AttributeValue(attrValue3);
		save(schemaBreak1);

		final BigDecimal priceAfterConditions2 = calculatePrice(price, request);
		assertThat(priceAfterConditions2).isEqualByComparingTo(new BigDecimal("0.750000"));
	}

	private static PricingConditionsId id(final I_M_DiscountSchema record)
	{
		return PricingConditionsId.ofDiscountSchemaId(record.getM_DiscountSchema_ID());
	}

	private static PricingConditionsBreakId id(final I_M_DiscountSchemaBreak record)
	{
		return PricingConditionsBreakId.of(record.getM_DiscountSchema_ID(), record.getM_DiscountSchemaBreak_ID());
	}

	private static final ProductAndCategoryAndManufacturerId productAndCategoryId(final I_M_Product product)
	{
		return ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), product.getM_Product_Category_ID(), product.getManufacturer_ID());
	}

	private BigDecimal calculatePrice(final BigDecimal price, final CalculatePricingConditionsRequest request)
	{
		final PricingConditionsResult result = service.calculatePricingConditions(request).orElse(null);
		if (result == null)
		{
			return price;
		}

		final Percent discount = result.getDiscount();
		return discount.subtractFromBase(price, 6);
	}
}
