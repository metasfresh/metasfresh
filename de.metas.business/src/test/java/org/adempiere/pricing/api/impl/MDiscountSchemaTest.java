package org.adempiere.pricing.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
import org.adempiere.model.InterfaceWrapperHelper;
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
import org.compiere.util.Env;
import org.junit.Assert;
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

		Assert.assertEquals(breaks.size(), 2);
		Assert.assertTrue(" breaks contain schemabreak1", breaks.contains(schemaBreak1));
		Assert.assertTrue(" breaks contain schemabreak2", breaks.contains(schemaBreak2));
		Assert.assertFalse(" breaks contain schemabreak3", breaks.contains(schemaBreak3));

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

		Assert.assertEquals(lines.size(), 2);
		Assert.assertTrue(" Lines contain schemaLine1", lines.contains(schemaLine1));
		Assert.assertTrue(" Lines contain schemaLine2", lines.contains(schemaLine2));
		Assert.assertFalse(" Lines contain schemaLine3", lines.contains(schemaLine3));

	}

	@Test
	public void testPickApplyingBreak_NoAttrbiutes()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_DiscountSchemaBreak schemaBreak1 = createBreak(schema1, 10);

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());

		InterfaceWrapperHelper.save(schemaBreak1);

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

		Assert.assertNotNull(actualSchemaBreak1);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak1.getM_DiscountSchemaBreak_ID());

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(-1);

		InterfaceWrapperHelper.save(schemaBreak1);

		final I_M_DiscountSchemaBreak actualSchemaBreak2 =
				bl.pickApplyingBreak(
						breaks,
						-1, // attribute value
						true,
						product1.getM_Product_ID(),
						category1.getM_Product_Category_ID(),
						new BigDecimal(15), // not relevant in this test
						new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
				);

		Assert.assertNotNull(actualSchemaBreak2);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak2.getM_DiscountSchemaBreak_ID());

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

		Assert.assertNotNull(actualSchemaBreak3);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak3.getM_DiscountSchemaBreak_ID());

	}

	@Test
	public void testPickApplyingBreak_AttributeValue()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");
		final I_M_AttributeValue attrValue2 = createAttrValue(attr1, "AttrValue2");

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 =
				InterfaceWrapperHelper.create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);

		InterfaceWrapperHelper.save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 =
				InterfaceWrapperHelper.create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);

		InterfaceWrapperHelper.save(schemaBreak2);

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

		Assert.assertNotNull(actualSchemaBreak1);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak1.getM_DiscountSchemaBreak_ID());

		final I_M_DiscountSchemaBreak actualSchemaBreak2 = bl.pickApplyingBreak(
				breaks,
				-1,
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
				);

		Assert.assertNull(actualSchemaBreak2);

		final I_M_DiscountSchemaBreak actualSchemaBreak3 = bl.pickApplyingBreak(
				breaks,
				attrValue2.getM_AttributeValue_ID(),
				true,
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(15), // not relevant in this test
				new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
				);

		Assert.assertNotNull(actualSchemaBreak3);
		Assert.assertTrue(schemaBreak2.getM_DiscountSchemaBreak_ID() == actualSchemaBreak3.getM_DiscountSchemaBreak_ID());

		// test also if seqNo is still respected

		schemaBreak1.setM_AttributeValue(null);
		InterfaceWrapperHelper.save(schemaBreak1);

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

		Assert.assertNotNull(actualSchemaBreak4);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak4.getM_DiscountSchemaBreak_ID());

	}

	@Test
	public void testPickApplyingBreak_AttributeInstances_EmptyInstance()
	{
		final I_M_DiscountSchema schema1 = createSchema();

		final I_M_Product_Category category1 = createM_ProductCategory("Category1");
		final I_M_Product product1 = createM_Product("Product1", category1);

		final I_M_Attribute attr1 = createAttr("Attr1");
		final I_M_AttributeValue attrValue1 = createAttrValue(attr1, "AttrValue1");

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 =
				InterfaceWrapperHelper.create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);

		InterfaceWrapperHelper.save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 =
				InterfaceWrapperHelper.create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(null);

		InterfaceWrapperHelper.save(schemaBreak2);

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

		Assert.assertNotNull(actualSchemaBreak1);
		Assert.assertTrue(schemaBreak2.getM_DiscountSchemaBreak_ID() == actualSchemaBreak1.getM_DiscountSchemaBreak_ID());
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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 =
				InterfaceWrapperHelper.create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);

		InterfaceWrapperHelper.save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 =
				InterfaceWrapperHelper.create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);

		InterfaceWrapperHelper.save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<I_M_AttributeInstance>();

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

		Assert.assertNotNull(actualSchemaBreak1);
		Assert.assertTrue(schemaBreak1.getM_DiscountSchemaBreak_ID() == actualSchemaBreak1.getM_DiscountSchemaBreak_ID());

		// mind the seqNo

		// schemaBreak1.setSeqNo(20);
		// InterfaceWrapperHelper.save(schemaBreak1);
		//
		// schemaBreak2.setSeqNo(10);
		// InterfaceWrapperHelper.save(schemaBreak2);
		//
		// breaks = dao.retrieveBreaks(schema1);
		//
		// final I_M_DiscountSchemaBreak actualSchemaBreak2 = bl.pickApplyingBreak(
		// breaks,
		// instances,
		// true,
		// product1.getM_Product_ID(),
		// category1.getM_Product_Category_ID(),
		// new BigDecimal(15), // not relevant in this test
		// new BigDecimal(30) // e.g. 15 * 2 ( not relevant in this test)
		// );
		//
		// Assert.assertNotNull(actualSchemaBreak2);
		// Assert.assertTrue(schemaBreak2.getM_DiscountSchemaBreak_ID() == actualSchemaBreak2.getM_DiscountSchemaBreak_ID());

		// TODO: As a working incremet, this is ok as is. This test shall be modified after we introduce the intermediate table.
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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 =
				InterfaceWrapperHelper.create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));

		InterfaceWrapperHelper.save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 =
				InterfaceWrapperHelper.create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));

		InterfaceWrapperHelper.save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<I_M_AttributeInstance>();

		instances.add(instance1);
		instances.add(instance2);

		// Discount 0 (because no breaks were applied)

		final BigDecimal price = bl.calculatePrice(
				schema1,
				new BigDecimal(100),
				new BigDecimal(1),
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(1));

		Assert.assertTrue("Expected price: " + Env.ONE + "but was " + price, Env.ONE.equals(price));

		schemaBreak1.setM_AttributeValue(null);
		InterfaceWrapperHelper.save(schemaBreak1);

		final BigDecimal price2 = bl.calculatePrice(
				schema1,
				new BigDecimal(100),
				new BigDecimal(1),
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				new BigDecimal(1));

		final BigDecimal expectedPrice = new BigDecimal("0.500000");

		Assert.assertTrue("Expected price: " + expectedPrice + "But was " + price2, expectedPrice.equals(price2));

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

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak1 =
				InterfaceWrapperHelper.create(createBreak(schema1, 10), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak1.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak1.setM_AttributeValue(attrValue1);
		schemaBreak1.setBreakDiscount(new BigDecimal(50));

		InterfaceWrapperHelper.save(schemaBreak1);

		final de.metas.adempiere.model.I_M_DiscountSchemaBreak schemaBreak2 =
				InterfaceWrapperHelper.create(createBreak(schema1, 20), de.metas.adempiere.model.I_M_DiscountSchemaBreak.class);

		schemaBreak2.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		schemaBreak2.setM_Product_ID(product1.getM_Product_ID());
		schemaBreak2.setM_AttributeValue(attrValue2);
		schemaBreak2.setBreakDiscount(new BigDecimal(25));

		InterfaceWrapperHelper.save(schemaBreak2);

		final I_M_AttributeInstance instance1 = createAttributeInstance(attr1, attrValue1);
		final I_M_AttributeInstance instance2 = createAttributeInstance(attr1, attrValue2);

		final List<I_M_AttributeInstance> instances = new ArrayList<I_M_AttributeInstance>();

		instances.add(instance1);
		instances.add(instance2);

		// Discount 0 (because no breaks were applied)

		final BigDecimal price = bl.calculatePrice(
				schema1,
				new BigDecimal(100),
				new BigDecimal(1),
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				instances,
				new BigDecimal(1));

		BigDecimal expectedPrice = new BigDecimal("0.500000");
		Assert.assertTrue("Expected price: " + expectedPrice + "but was " + price, expectedPrice.equals(price));

		final I_M_AttributeValue attrValue3 = createAttrValue(attr1, "Attr Value 3");

		schemaBreak1.setM_AttributeValue(attrValue3);
		InterfaceWrapperHelper.save(schemaBreak1);

		final BigDecimal price2 = bl.calculatePrice(
				schema1,
				new BigDecimal(100),
				new BigDecimal(1),
				product1.getM_Product_ID(),
				category1.getM_Product_Category_ID(),
				instances,
				new BigDecimal(1));

		expectedPrice = new BigDecimal("0.750000");

		Assert.assertTrue("Expected price: " + expectedPrice + "But was " + price2, expectedPrice.equals(price2));
	}

	private I_M_AttributeInstance createAttributeInstance(final I_M_Attribute attr, final I_M_AttributeValue attrValue)
	{
		final I_M_AttributeInstance attrInstance = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, contextProvider);
		attrInstance.setM_Attribute(attr);
		attrInstance.setM_AttributeValue(attrValue);
		InterfaceWrapperHelper.save(attrInstance);

		return attrInstance;
	}

	private I_M_AttributeValue createAttrValue(final I_M_Attribute attr, final String attrValueName)
	{
		final I_M_AttributeValue attrValue = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, contextProvider);
		attrValue.setM_Attribute(attr);
		attrValue.setName(attrValueName);
		InterfaceWrapperHelper.save(attrValue);

		return attrValue;
	}

	private I_M_Attribute createAttr(final String attrName)
	{
		final I_M_Attribute attr = InterfaceWrapperHelper.newInstance(I_M_Attribute.class, contextProvider);
		attr.setName(attrName);
		InterfaceWrapperHelper.save(attr);

		return attr;
	}

	private I_M_DiscountSchemaLine createLine(final I_M_DiscountSchema schema, final int seqNo)
	{
		final I_M_DiscountSchemaLine schemaLine = InterfaceWrapperHelper.newInstance(I_M_DiscountSchemaLine.class, contextProvider);
		schemaLine.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaLine.setSeqNo(seqNo);
		InterfaceWrapperHelper.save(schemaLine);
		return schemaLine;
	}

	private I_M_DiscountSchemaBreak createBreak(final I_M_DiscountSchema schema, final int seqNo)
	{
		final I_M_DiscountSchemaBreak schemaBreak = InterfaceWrapperHelper.newInstance(I_M_DiscountSchemaBreak.class, contextProvider);
		schemaBreak.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaBreak.setSeqNo(seqNo);
		InterfaceWrapperHelper.save(schemaBreak);
		return schemaBreak;
	}

	public I_M_DiscountSchema createSchema()
	{
		final I_M_DiscountSchema schema = InterfaceWrapperHelper.newInstance(I_M_DiscountSchema.class, contextProvider);
		InterfaceWrapperHelper.save(schema);
		return schema;
	}

	private I_M_Product createM_Product(final String value, final I_M_Product_Category category)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		product.setValue(value);
		product.setM_Product_Category(category);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private I_M_Product_Category createM_ProductCategory(final String value)
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class, contextProvider);
		productCategory.setValue(value);

		InterfaceWrapperHelper.save(productCategory);
		return productCategory;
	}
}
