package org.adempiere.mm.attributes.api.impl;

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


import java.util.Set;

import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;

public class AttributeDAOTest
{
	private AttributesTestHelper helper;

	/** service under test */
	private AttributeDAO attributeDAO;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new AttributesTestHelper();
		this.attributeDAO = (AttributeDAO)Services.get(IAttributeDAO.class);
	}

	@Test
	public void test_retrieveAttributeValueSubstitutes()
	{
		final I_M_Attribute attribute = helper.createM_Attribute_TypeList("attribute");
		final AttributeListValue av_A_B = helper.createM_AttributeValue(attribute, "A&B");
		final AttributeListValue av_A_C = helper.createM_AttributeValue(attribute, "A&C");
		final AttributeListValue av_A = helper.createM_AttributeValue(attribute, "A");
		final AttributeListValue av_B = helper.createM_AttributeValue(attribute, "B");
		final AttributeListValue av_C = helper.createM_AttributeValue(attribute, "C");
		helper.createM_AttributeValue(attribute, "D");
		helper.createM_AttributeValue(attribute, "E");
		helper.createM_AttributeValue(attribute, "F");
		helper.createM_AttributeValue_Mapping(av_A_B, av_A);
		helper.createM_AttributeValue_Mapping(av_A_B, av_B);
		helper.createM_AttributeValue_Mapping(av_A_C, av_A);
		helper.createM_AttributeValue_Mapping(av_A_C, av_C);

		test_retrieveAttributeValueSubstitutes(attribute, "A&B");
		test_retrieveAttributeValueSubstitutes(attribute, "A&C");
		test_retrieveAttributeValueSubstitutes(attribute, "A", "A&B", "A&C");
		test_retrieveAttributeValueSubstitutes(attribute, "B", "A&B");
		test_retrieveAttributeValueSubstitutes(attribute, "C", "A&C");
		test_retrieveAttributeValueSubstitutes(attribute, "D");
		test_retrieveAttributeValueSubstitutes(attribute, "E");
		test_retrieveAttributeValueSubstitutes(attribute, "F");
	}

	/**
	 * Runs {@link #test_retrieveAttributeValueSubstitutes()} multiple times to have more attributes created with more values with same code and to make sure they not overlap
	 */
	@Test
	public void test_retrieveAttributeValueSubstitutes_MultipleRuns()
	{
		for (int i = 1; i < 10; i++)
		{
			test_retrieveAttributeValueSubstitutes();
		}
	}

	public void test_retrieveAttributeValueSubstitutes(final I_M_Attribute attribute, final String value, final String... expectedSubstitutes)
	{
		final Set<String> actualSubstitutes = attributeDAO.retrieveAttributeValueSubstitutes(attribute, value);
		Assert.assertEquals("Invalid substitutes for: " + value,
				CollectionUtils.asSet(expectedSubstitutes),
				actualSubstitutes
				);

	}
}
