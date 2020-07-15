package de.metas.invoicecandidate.spi.impl.aggregator.standard;

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

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.impl.InvoiceLineAttribute;
import de.metas.util.collections.CollectionUtils;

/**
 * Tests {@link CommonInvoiceLineAttributeAggregator}.
 *
 * @author tsa
 *
 */
public class CommonInvoiceLineAttributeAggregatorTest
{
	private CommonInvoiceLineAttributeAggregator aggregator;
	private Properties ctx;
	private I_M_Attribute attribute1;
	private I_M_Attribute attribute2;
	private I_M_Attribute attribute3;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		attribute1 = createM_Attribute("attribute1");
		attribute2 = createM_Attribute("attribute2");
		attribute3 = createM_Attribute("attribute3");

		aggregator = new CommonInvoiceLineAttributeAggregator();
	}

	@Test
	public void test_NothingAdded()
	{
		Assert.assertEquals(Collections.emptySet(), aggregator.aggregate());
	}

	@Test
	public void test_OneCommonAttribute()
	{
		aggregator.addAll(CollectionUtils.<IInvoiceLineAttribute> asSet(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute2, "2")));

		aggregator.addAll(CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute3, "3")));

		final Set<IInvoiceLineAttribute> expectedResult = CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1"));
		Assert.assertEquals(expectedResult, aggregator.aggregate());

		// Check: adding "attribute1"="1" several times shall not affect our result
		for (int i = 1; i <= 10; i++)
		{
			aggregator.addAll(CollectionUtils.asSet(
					createInvoiceLineAttribute(attribute1, "1")));
			Assert.assertEquals(expectedResult, aggregator.aggregate());
		}
	}

	@Test
	public void test_DifferentAttributes()
	{
		aggregator.addAll(CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1_1"), createInvoiceLineAttribute(attribute2, "2")));

		aggregator.addAll(CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1_2"), createInvoiceLineAttribute(attribute3, "3")));

		final Set<IInvoiceLineAttribute> expectedResult = CollectionUtils.asSet();
		Assert.assertEquals(expectedResult, aggregator.aggregate());

	}

	@Test
	public void test_AddingEmptySet()
	{
		final Set<IInvoiceLineAttribute> set1 = CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute2, "2"));
		aggregator.addAll(set1);
		Assert.assertEquals(set1, aggregator.aggregate());

		// add empty set
		aggregator.addAll(Collections.<IInvoiceLineAttribute> emptySet());
		Assert.assertEquals(Collections.<IInvoiceLineAttribute> emptySet(), aggregator.aggregate());
	}

	private I_M_Attribute createM_Attribute(final String name)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(ctx, I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setName(name);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	private IInvoiceLineAttribute createInvoiceLineAttribute(final I_M_Attribute attribute, final String value)
	{
		final I_M_AttributeInstance ai = InterfaceWrapperHelper.create(ctx, I_M_AttributeInstance.class, ITrx.TRXNAME_None);
		ai.setM_Attribute_ID(attribute.getM_Attribute_ID());
		ai.setValue(value);

		return new InvoiceLineAttribute(ai);
	}

}
