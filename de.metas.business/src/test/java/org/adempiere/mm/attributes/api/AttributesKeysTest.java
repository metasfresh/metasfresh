package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AttributesKeysTest
{
	private AttributesTestHelper attributesTestHelper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
	}

	@Test
	public void test_createAttributesKeyFromASI()
	{
		final I_M_Attribute attr1 = createStorageRelevantAttribute("test1");
		final I_M_AttributeValue attributeValue1 = attributesTestHelper.createM_AttributeValue(attr1, "testValue1");

		final I_M_Attribute attr2 = createStorageRelevantAttribute("test2");
		final I_M_AttributeValue attributeValue2 = attributesTestHelper.createM_AttributeValue(attr2, "testValue2");

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeValue1);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeValue2);

		final Optional<AttributesKey> result = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asi.getM_AttributeSetInstance_ID());
		assertThat(result).isPresent();

		final AttributesKey expectedResult = AttributesKey.ofAttributeValueIds(attributeValue1.getM_AttributeValue_ID(), attributeValue2.getM_AttributeValue_ID());
		assertThat(result.get()).isEqualTo(expectedResult);
	}

	private final I_M_Attribute createStorageRelevantAttribute(@NonNull final String name)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(name, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		save(attribute);
		return attribute;
	}
}
