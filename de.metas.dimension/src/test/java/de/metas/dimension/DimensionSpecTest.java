package de.metas.dimension;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.junit.Before;
import org.junit.Test;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;

/*
 * #%L
 * de.metas.dimension
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

public class DimensionSpecTest
{
	private I_M_AttributeValue attr1_value1;
	private I_M_AttributeValue attr2_value1;
	private I_M_AttributeValue attr2_value2;
	private I_M_Attribute attr1;
	private I_M_Attribute attr2;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

		attr1 = attributesTestHelper.createM_Attribute("test1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr1_value1 = attributesTestHelper.createM_AttributeValue(attr1, "test1_value1");
		attributesTestHelper.createM_AttributeValue(attr1, "test1_value2");

		attr2 = attributesTestHelper.createM_Attribute("test21", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr2_value1 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value1");
		attr2_value2 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value2");
	}

	@Test
	public void getGroups_with_two_dimSpecAttrs_one_with_isIncludeAllAttributeValues()
	{
		final I_DIM_Dimension_Spec record = createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_isIncludeAllAttributeValue();
		final DimensionSpec dimensionSpec = DimensionSpec.ofRecord(record);
		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();

		// one "empty", one for dimSpecAttr1 and two for dimSpecAttr2 with includeAllAttributeValues=true and it's two attribute-values
		assertThat(groups).hasSize(4);
		assertThat(groups.get(0).isEmptyGroup()).isTrue();
		assertThat(groups.get(0).getGroupName().getDefaultValue()).isEqualTo(DimensionConstants.MSG_NoneOrEmpty);
		assertThat(groups.get(0).getAttributesKey().isNone()).isTrue();

		// attr1 has two values, but just one of them is "explicitly" added to dimSpecAttr1 which has isIncludeAllAttributeValues=false
		assertThat(groups.get(1).isEmptyGroup()).isFalse();
		assertThat(groups.get(1).getGroupName().getDefaultValue()).isEqualTo("Name_test1_value1");
		assertThat(groups.get(1).getAttributesKey().getAttributeValueIds()).containsExactly(attr1_value1.getM_AttributeValue_ID());

		// attr2 has two values, and dimSpecAttr2 has isIncludeAllAttributeValues=true and isValueAggregate=false
		assertThat(groups.get(2).isEmptyGroup()).isFalse();
		assertThat(groups.get(2).getGroupName().getDefaultValue()).isEqualTo("Name_test2_value1");
		assertThat(groups.get(2).getAttributesKey().getAttributeValueIds()).containsExactly(attr2_value1.getM_AttributeValue_ID());

		assertThat(groups.get(3).isEmptyGroup()).isFalse();
		assertThat(groups.get(3).getGroupName().getDefaultValue()).isEqualTo("Name_test2_value2");
		assertThat(groups.get(3).getAttributesKey().getAttributeValueIds()).containsExactly(attr2_value2.getM_AttributeValue_ID());
	}

	private I_DIM_Dimension_Spec createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_isIncludeAllAttributeValue()
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName("DIM_SPEC_INTERNAL_NAME");
		dimSpec.setIsIncludeEmpty(true);
		save(dimSpec);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr1 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr1.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr1.setM_Attribute(attr1);
		save(dimSpecAttr1);

		final I_DIM_Dimension_Spec_AttributeValue dimSpecAttributeValue = newInstance(I_DIM_Dimension_Spec_AttributeValue.class);
		dimSpecAttributeValue.setDIM_Dimension_Spec_Attribute(dimSpecAttr1);
		dimSpecAttributeValue.setM_AttributeValue(attr1_value1);
		save(dimSpecAttributeValue);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr2 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr2.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr2.setIsIncludeAllAttributeValues(true);
		dimSpecAttr2.setIsValueAggregate(false);
		dimSpecAttr2.setM_Attribute(attr2);
		save(dimSpecAttr2);

		return dimSpec;
	}

	@Test
	public void getGroups_with_two_dimSpecAttrs_one_with_aggregate_group_name()
	{
		final I_DIM_Dimension_Spec record = createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_aggregate_group_name();
		final DimensionSpec dimensionSpec = DimensionSpec.ofRecord(record);
		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();

		// one "empty", one for dimSpecAttr1 and one for dimSpecAttr2 with its isValueAggregate=true
		assertThat(groups).hasSize(3);
		assertThat(groups.get(0).isEmptyGroup()).isTrue();
		assertThat(groups.get(0).getGroupName().getDefaultValue()).isEqualTo(DimensionConstants.MSG_NoneOrEmpty);
		assertThat(groups.get(0).getAttributesKey().isNone()).isTrue();

		// attr1 has two values, but just one of them is "explicitly" added to dimSpecAttr1 which has isIncludeAllAttributeValues=false
		assertThat(groups.get(1).isEmptyGroup()).isFalse();
		assertThat(groups.get(1).getGroupName().getDefaultValue()).isEqualTo("Name_test1_value1");
		assertThat(groups.get(1).getAttributesKey().getAttributeValueIds()).containsExactly(attr1_value1.getM_AttributeValue_ID());

		// attr2 has two values, and dimSpecAttr2 which has isIncludeAllAttributeValues=true
		assertThat(groups.get(2).isEmptyGroup()).isFalse();
		assertThat(groups.get(2).getGroupName().getDefaultValue()).isEqualTo("dimSpecAttr2_ValueAggregateName");
		assertThat(groups.get(2).getAttributesKey().getAttributeValueIds()).containsOnly(
				attr2_value1.getM_AttributeValue_ID(),
				attr2_value2.getM_AttributeValue_ID());
	}

	private I_DIM_Dimension_Spec createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_aggregate_group_name()
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName("DIM_SPEC_INTERNAL_NAME");
		dimSpec.setIsIncludeEmpty(true);
		save(dimSpec);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr1 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr1.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr1.setM_Attribute(attr1);
		save(dimSpecAttr1);

		final I_DIM_Dimension_Spec_AttributeValue dimSpecAttributeValue = newInstance(I_DIM_Dimension_Spec_AttributeValue.class);
		dimSpecAttributeValue.setDIM_Dimension_Spec_Attribute(dimSpecAttr1);
		dimSpecAttributeValue.setM_AttributeValue(attr1_value1);
		save(dimSpecAttributeValue);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr2 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr2.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr2.setIsIncludeAllAttributeValues(true);
		dimSpecAttr2.setIsValueAggregate(true);
		dimSpecAttr2.setValueAggregateName("dimSpecAttr2_ValueAggregateName");
		dimSpecAttr2.setM_Attribute(attr2);
		save(dimSpecAttr2);

		return dimSpec;
	}
}
