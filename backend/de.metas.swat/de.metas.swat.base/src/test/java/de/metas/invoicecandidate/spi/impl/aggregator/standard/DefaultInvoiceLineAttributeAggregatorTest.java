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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.impl.InvoiceLineAttribute;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests {@link DefaultInvoiceLineAttributeAggregator}.
 */

public class DefaultInvoiceLineAttributeAggregatorTest
{
	private DefaultInvoiceLineAttributeAggregator aggregator;
	private Properties ctx;
	private I_M_Attribute attribute1;
	private I_M_Attribute attribute2;
	private I_M_Attribute attribute3;
	private I_M_Attribute attribute4;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.ctx = Env.getCtx();

		attribute1 = createM_Attribute("attribute1");
		attribute2 = createM_Attribute("attribute2");
		attribute3 = createM_Attribute("attribute3");
		attribute4 = createM_Attribute("attribute4");

		aggregator = new DefaultInvoiceLineAttributeAggregator();
	}

	@Test
	public void test_NothingAdded()
	{
		assertThat(aggregator.aggregate()).isEmpty();
	}

	@Test
	public void test_OneCommonAttribute()
	{
		aggregator.addToIntersection(ImmutableSet.of(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute2, "2")));

		aggregator.addToIntersection(ImmutableSet.of(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute3, "3")));

		aggregator.addToUnion(ImmutableList.of(createInvoiceLineAttribute(attribute4, "4")));
		
		final List<IInvoiceLineAttribute> expectedResult = ImmutableList.of(
				createInvoiceLineAttribute(attribute4, "4"),
				createInvoiceLineAttribute(attribute1, "1"));
		
		assertThat(aggregator.aggregate()).containsExactlyElementsOf(expectedResult);

		// Check: adding "attribute1"="1" several times shall not affect our result
		for (int i = 1; i <= 10; i++)
		{
			aggregator.addToIntersection(CollectionUtils.asSet(
					createInvoiceLineAttribute(attribute1, "1")));

			assertThat(aggregator.aggregate()).containsExactlyElementsOf(expectedResult);
		}
	}

	@Test
	public void test_DifferentAttributes()
	{
		aggregator.addToIntersection(CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1_1"), createInvoiceLineAttribute(attribute2, "2")));

		aggregator.addToIntersection(CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1_2"), createInvoiceLineAttribute(attribute3, "3")));

		final Set<IInvoiceLineAttribute> expectedResult = CollectionUtils.asSet();
		assertThat(aggregator.aggregate()).containsExactlyElementsOf(expectedResult);
	}

	@Test
	public void test_AddingEmptySet()
	{
		final Set<IInvoiceLineAttribute> set1 = CollectionUtils.asSet(
				createInvoiceLineAttribute(attribute1, "1"), createInvoiceLineAttribute(attribute2, "2"));
		aggregator.addToIntersection(set1);
		assertThat(aggregator.aggregate()).containsExactlyElementsOf(set1);

		// add empty set
		aggregator.addToIntersection(ImmutableSet.of());
		assertThat(aggregator.aggregate()).isEmpty();
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
