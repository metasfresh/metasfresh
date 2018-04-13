package org.adempiere.pricing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.IContextAware;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

public class MDiscountSchemaTest
{
	protected MDiscountSchemaDAO dao;
	protected MDiscountSchemaBL bl;

	public Properties ctx;
	public String trxName;

	public final IContextAware contextProvider = new IContextAware()
	{
		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return trxName;
		}
	};

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		dao = new MDiscountSchemaDAO();
		bl = new MDiscountSchemaBL();
		Services.registerService(IMDiscountSchemaDAO.class, dao);

		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;

	}

	@Test
	public void testRetrieveBreaks()
	{
		final I_M_DiscountSchema schema1 = createSchema();
		final I_M_DiscountSchema schema2 = createSchema();

		final I_M_DiscountSchemaBreak schemaBreak1 = createBreak(schema1, 10);
		final I_M_DiscountSchemaBreak schemaBreak2 = createBreak(schema1, 20);

		final I_M_DiscountSchemaBreak schemaBreak3 = createBreak(schema2, 10);

		final List<I_M_DiscountSchemaBreak> breaks = dao.retrieveBreaks(schema1);

		assertThat(breaks).hasSize(2);
		assertThat(breaks).contains(schemaBreak1, schemaBreak2);
		assertThat(breaks).doesNotContain(schemaBreak3);
	}

	@Test
	public void testRetrieveLines()
	{
		final I_M_DiscountSchema schema1 = createSchema();
		final I_M_DiscountSchema schema2 = createSchema();

		final I_M_DiscountSchemaLine schemaLine1 = createLine(schema1, 10);
		final I_M_DiscountSchemaLine schemaLine2 = createLine(schema1, 20);

		final I_M_DiscountSchemaLine schemaLine3 = createLine(schema2, 10);

		final List<I_M_DiscountSchemaLine> lines = dao.retrieveLines(schema1);

		assertThat(lines).hasSize(2);
		assertThat(lines).contains(schemaLine1, schemaLine2);
		assertThat(lines).doesNotContain(schemaLine3);
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_for_same_product_FirstBreak()
	{
		final I_M_Product_Category category = createM_ProductCategory("Category1");
		final I_M_Product product = createM_Product("Product1", category);

		final List<I_M_DiscountSchemaBreak> breaks = createDiscountSchemaBreaks(product);

		final I_M_DiscountSchemaBreak actualSchemaBreak = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product.getM_Product_ID(),
				category.getM_Product_Category_ID(),
				new BigDecimal(15),
				new BigDecimal(30));

		final I_M_DiscountSchemaBreak expectedDiscountSchemaBreak = breaks.get(0);

		assertThat(actualSchemaBreak).isNotNull();
		assertThat(expectedDiscountSchemaBreak.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_for_same_product_SecondBreak()
	{
		final I_M_Product_Category category = createM_ProductCategory("Category1");
		final I_M_Product product = createM_Product("Product1", category);

		final List<I_M_DiscountSchemaBreak> breaks = createDiscountSchemaBreaks(product);

		final I_M_DiscountSchemaBreak actualSchemaBreak = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product.getM_Product_ID(),
				category.getM_Product_Category_ID(),
				new BigDecimal(25),
				new BigDecimal(50));

		final I_M_DiscountSchemaBreak expectedDiscountSchemaBreak = breaks.get(1);

		assertThat(actualSchemaBreak).isNotNull();
		assertThat(expectedDiscountSchemaBreak.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_with_Multiple_breaks_defined_with_no_product()
	{
		final I_M_Product_Category category = createM_ProductCategory("Category1");
		final I_M_Product product = createM_Product("Product1", category);

		final List<I_M_DiscountSchemaBreak> breaks = createDiscountSchemaBreaks(product);

		final I_M_DiscountSchemaBreak expectedDiscountSchemaBreak = breaks.get(1);
		expectedDiscountSchemaBreak.setM_Product_ID(-1);
		save(expectedDiscountSchemaBreak);

		final I_M_DiscountSchemaBreak actualSchemaBreak = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product.getM_Product_ID(),
				category.getM_Product_Category_ID(),
				new BigDecimal(25),
				new BigDecimal(50));

		assertThat(actualSchemaBreak).isNotNull();
		assertThat(expectedDiscountSchemaBreak.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_NoAttributes()
	{
		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_DiscountSchema schema1 = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak1 = createBreak(schema1, 10);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		save(schemaBreak1);

		final List<I_M_DiscountSchemaBreak> breaks = dao.retrieveBreaks(schema1);

		final I_M_DiscountSchemaBreak actualSchemaBreak1 = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak1.getM_DiscountSchemaBreak_ID());

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(-1);

		save(schemaBreak1);

		final I_M_DiscountSchemaBreak actualSchemaBreak2 = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak2).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak2.getM_DiscountSchemaBreak_ID());

		schemaBreak1.setM_Product_Category_ID(-1);
		schemaBreak1.setM_Product_ID(-1);

		final I_M_DiscountSchemaBreak actualSchemaBreak3 = bl.pickApplyingBreak(
				breaks,
				-1, // attribute value
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak3).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak3.getM_DiscountSchemaBreak_ID());
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
		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		save(schemaBreak2);

		List<I_M_DiscountSchemaBreak> breaks = dao.retrieveBreaks(schema1);

		final I_M_DiscountSchemaBreak actualSchemaBreak1 = bl.pickApplyingBreak(
				breaks,
				attrValue1.getM_AttributeValue_ID(),
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak1.getM_DiscountSchemaBreak_ID());

		final I_M_DiscountSchemaBreak actualSchemaBreak2 = bl.pickApplyingBreak(
				breaks,
				-1,
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak2).isNull();

		final I_M_DiscountSchemaBreak actualSchemaBreak3 = bl.pickApplyingBreak(
				breaks,
				attrValue2.getM_AttributeValue_ID(),
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak3).isNotNull();
		assertThat(schemaBreak2.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak3.getM_DiscountSchemaBreak_ID());

		// test also if seqNo is still respected
		schemaBreak1.setM_AttributeValue(null);
		save(schemaBreak1);

		breaks = dao.retrieveBreaks(schema1);

		final I_M_DiscountSchemaBreak actualSchemaBreak4 = bl.pickApplyingBreak(
				breaks,
				attrValue2.getM_AttributeValue_ID(),
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak4).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak4.getM_DiscountSchemaBreak_ID());
	}

	@Test
	public void testPickApplyingBreak_AttributeInstances_EmptyInstance()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(null);
		save(schemaBreak2);

		List<I_M_DiscountSchemaBreak> breaks = dao.retrieveBreaks(schema1);

		List<I_M_AttributeInstance> instances = Collections.emptyList();
		final I_M_DiscountSchemaBreak actualSchemaBreak1 = bl.pickApplyingBreak(
				breaks,
				instances,
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(schemaBreak2.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak1.getM_DiscountSchemaBreak_ID());
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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<>();
		instances.add(instance1);
		instances.add(instance2);

		List<I_M_DiscountSchemaBreak> breaks = dao.retrieveBreaks(schema1);

		final I_M_DiscountSchemaBreak actualSchemaBreak1 = bl.pickApplyingBreak(
				breaks,
				instances,
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		);

		assertThat(actualSchemaBreak1).isNotNull();
		assertThat(schemaBreak1.getM_DiscountSchemaBreak_ID()).isEqualTo(actualSchemaBreak1.getM_DiscountSchemaBreak_ID());
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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));
		save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));
		save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<>();
		instances.add(instance1);
		instances.add(instance2);

		// Discount 0 (because no breaks were applied)


		final CalculateDiscountRequest request = CalculateDiscountRequest.builder()
				.schema(schema1)
				.qty(new BigDecimal(100))
				.Price(new BigDecimal(1))
				.M_Product_ID(product1.getM_Product_ID())
				.M_Product_Category_ID(category1.getM_Product_Category_ID())
				.bPartnerFlatDiscount(new BigDecimal(1))
				.build();

		final BigDecimal price = bl.calculatePrice(request);

		assertThat(price).isEqualByComparingTo(BigDecimal.ONE);

		schemaBreak1.setM_AttributeValue(null);
		save(schemaBreak1);

		final BigDecimal price2 = bl.calculatePrice(request);
		final BigDecimal expectedPrice = new BigDecimal("0.500000");
		assertThat(expectedPrice).isEqualByComparingTo(price2);
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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 = create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));
		save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 = create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);
		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));
		save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<>();
		instances.add(instance1);
		instances.add(instance2);

		// Discount 0 (because no breaks were applied)

		final CalculateDiscountRequest request = CalculateDiscountRequest.builder()
				.schema(schema1)
				.qty(new BigDecimal(100))
				.Price(new BigDecimal(1))
				.M_Product_ID(product1.getM_Product_ID())
				.M_Product_Category_ID(category1.getM_Product_Category_ID())
				.instances(instances)
				.bPartnerFlatDiscount(new BigDecimal(1))
				.build();

		final BigDecimal price = bl.calculatePrice(request);

		BigDecimal expectedPrice = new BigDecimal("0.500000");
		assertThat(expectedPrice).isEqualByComparingTo(price);

		final I_M_AttributeValue attrValue3 = createAttrValue(attr1, "Attr Value 3");
		schemaBreak1.setM_AttributeValue(attrValue3);
		save(schemaBreak1);

		final BigDecimal price2 = bl.calculatePrice(request);

		expectedPrice = new BigDecimal("0.750000");
		assertThat(expectedPrice).isEqualByComparingTo(price2);
	}

	private I_M_AttributeInstance createAttributeInstance(final I_M_Attribute attr, final I_M_AttributeValue attrValue)
	{
		final I_M_AttributeInstance attrInstance = newInstance(I_M_AttributeInstance.class, contextProvider);
		attrInstance.setM_Attribute(attr);
		attrInstance.setM_AttributeValue(attrValue);
		save(attrInstance);
		return attrInstance;
	}

	private I_M_AttributeValue createAttrValue(final I_M_Attribute attr, final String attrValueName)
	{
		final I_M_AttributeValue attrValue = newInstance(I_M_AttributeValue.class, contextProvider);
		attrValue.setM_Attribute(attr);
		attrValue.setName(attrValueName);
		save(attrValue);
		return attrValue;
	}

	private I_M_Attribute createAttr(final String attrName)
	{
		final I_M_Attribute attr = newInstance(I_M_Attribute.class, contextProvider);
		attr.setName(attrName);
		save(attr);
		return attr;
	}

	private I_M_DiscountSchemaLine createLine(final I_M_DiscountSchema schema, final int seqNo)
	{
		final I_M_DiscountSchemaLine schemaLine = newInstance(I_M_DiscountSchemaLine.class, contextProvider);
		schemaLine.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaLine.setSeqNo(seqNo);
		save(schemaLine);
		return schemaLine;
	}

	private I_M_Product createM_Product(final String value, final I_M_Product_Category category)
	{
		final I_M_Product product = newInstance(I_M_Product.class, contextProvider);
		product.setValue(value);
		product.setM_Product_Category(category);
		save(product);
		return product;
	}

	private I_M_Product_Category createM_ProductCategory(final String value)
	{
		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class, contextProvider);
		productCategory.setValue(value);
		save(productCategory);
		return productCategory;
	}

	private List<I_M_DiscountSchemaBreak> createDiscountSchemaBreaks(final I_M_Product product)
	{
		final List<I_M_DiscountSchemaBreak> breaks = new ArrayList<>();

		final I_M_DiscountSchema schema = createSchema();
		final I_M_DiscountSchemaBreak schemaBreak1 = createBreak(schema, 10);
		schemaBreak1.setM_Product_ID(product.getM_Product_ID());
		schemaBreak1.setBreakDiscount(BigDecimal.TEN);
		schemaBreak1.setBreakValue(BigDecimal.TEN);
		save(schemaBreak1);
		breaks.add(schemaBreak1);

		final I_M_DiscountSchemaBreak schemaBreak2 = createBreak(schema, 20);
		schemaBreak2.setM_Product_ID(product.getM_Product_ID());
		schemaBreak2.setBreakDiscount(BigDecimal.valueOf(20));
		schemaBreak2.setBreakValue(BigDecimal.valueOf(20));
		save(schemaBreak2);
		breaks.add(schemaBreak2);

		return breaks;
	}

	public I_M_DiscountSchema createSchema()
	{
		final I_M_DiscountSchema schema = newInstance(I_M_DiscountSchema.class, contextProvider);
		save(schema);
		return schema;
	}

	private I_M_DiscountSchemaBreak createBreak(final I_M_DiscountSchema schema, final int seqNo)
	{
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		save(schema);
		
		final I_M_DiscountSchemaBreak schemaBreak = newInstance(I_M_DiscountSchemaBreak.class, contextProvider);
		schemaBreak.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaBreak.setSeqNo(seqNo);
		save(schemaBreak);
		return schemaBreak;
	}
}
