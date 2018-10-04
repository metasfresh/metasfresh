package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.junit.Before;
import org.junit.Test;

import de.metas.util.Services;

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

public class ImmutableAttributeSetTest
{
	private AttributesTestHelper attributesTestHelper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();

	}

	@Test
	public void ofAttributesetInstanceId()
	{

		final I_M_Attribute attrStringWithValue = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final I_M_AttributeValue attributeStringValue = attributesTestHelper.createM_AttributeValue(attrStringWithValue, "testValue1");

		final I_M_Attribute attributeStringNull = attributesTestHelper.createM_Attribute("AttrStringNullValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final I_M_AttributeValue attributeStringNullValue = attributesTestHelper.createM_AttributeValue(attributeStringNull, null);

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeStringValue);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeStringNullValue);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributeSet = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		assertTrue(attributeSet.getAttributes().contains(attrStringWithValue));
		assertTrue(attributeSet.getAttributes().contains(attributeStringNull));

		assertTrue(attributeSet.getValue(attrStringWithValue).equals(attributeStringValue.getValue()));
		assertNull(attributeSet.getValue(attributeStringNull));

	}

}
