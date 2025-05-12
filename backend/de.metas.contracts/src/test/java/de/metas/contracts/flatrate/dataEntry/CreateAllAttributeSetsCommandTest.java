/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.flatrate.dataEntry;

import com.google.common.collect.ImmutableList;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CreateAllAttributeSetsCommandTest
{

	private AttributesTestHelper attributesTestHelper;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
	}

	@Test
	void run()
	{
		final AttributeCode timeOfDay = AttributeCode.ofString("TimeOfDay");
		final I_M_Attribute att1 = attributesTestHelper.createM_Attribute_TypeList(timeOfDay.getCode());
		final AttributeListValue listValue1_1 = attributesTestHelper.createM_AttributeValue(att1, "Breakfast");
		final AttributeListValue listValue1_2 = attributesTestHelper.createM_AttributeValue(att1, "Lunch");
		final AttributeListValue listValue1_3 = attributesTestHelper.createM_AttributeValue(att1, "Dinner");

		final AttributeCode typeOfMeal = AttributeCode.ofString("TypeOfMeal");
		final I_M_Attribute att2 = attributesTestHelper.createM_Attribute_TypeList(typeOfMeal.getCode());
		final AttributeListValue listValue2_1 = attributesTestHelper.createM_AttributeValue(att2, "Normal");
		final AttributeListValue listValue2_2 = attributesTestHelper.createM_AttributeValue(att2, "Extra");

		final List<AttributeListValue> attributeListValues = ImmutableList.of(listValue1_1, listValue1_2, listValue1_3, listValue2_1, listValue2_2);

		final List<ImmutableAttributeSet> attributeSets = CreateAllAttributeSetsCommand.withValues(attributeListValues).run();

		assertThat(attributeSets).hasSize(6);
		assertThat(attributeSets).allSatisfy(
				as -> {
					assertThat(as.getAttributeIdByCode(timeOfDay).getRepoId()).isEqualTo(att1.getM_Attribute_ID());
					assertThat(as.getAttributeIdByCode(typeOfMeal).getRepoId()).isEqualTo(att2.getM_Attribute_ID());
				}
		);
	}
}