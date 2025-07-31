package de.metas.dimension;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;
import de.metas.material.event.commons.AttributesKeyPart;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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
	private AttributeListValue attr1_value1;
	private AttributeListValue attr2_value1;
	private AttributeListValue attr2_value2;
	private I_M_Attribute attr1;
	private I_M_Attribute attr2;

	@BeforeEach
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
		assertThat(groups.getFirst().isEmptyGroup()).isTrue();
		assertThat(groups.getFirst().getGroupName().getDefaultValue()).isEqualTo(DimensionConstants.MSG_NoneOrEmpty.toAD_Message());
		assertThat(groups.getFirst().getAttributesKey().isNone()).isTrue();

		// attr1 has two values, but just one of them is "explicitly" added to dimSpecAttr1 which has isIncludeAllAttributeValues=false
		assertThat(groups.get(1).isEmptyGroup()).isFalse();
		assertThat(groups.get(1).getGroupName().getDefaultValue()).isEqualTo("Name_test1_value1");
		assertThat(groups.get(1).getAttributesKey().getParts()).containsExactly(AttributesKeyPart.ofAttributeValueId(attr1_value1.getId()));

		// attr2 has two values, and dimSpecAttr2 has isIncludeAllAttributeValues=true and isValueAggregate=false
		assertThat(groups.get(2).isEmptyGroup()).isFalse();
		assertThat(groups.get(2).getGroupName().getDefaultValue()).isEqualTo("Name_test2_value1");
		assertThat(groups.get(2).getAttributesKey().getParts()).containsExactly(AttributesKeyPart.ofAttributeValueId(attr2_value1.getId()));

		assertThat(groups.get(3).isEmptyGroup()).isFalse();
		assertThat(groups.get(3).getGroupName().getDefaultValue()).isEqualTo("Name_test2_value2");
		assertThat(groups.get(3).getAttributesKey().getParts()).containsExactly(AttributesKeyPart.ofAttributeValueId(attr2_value2.getId()));
	}

	private I_DIM_Dimension_Spec createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_isIncludeAllAttributeValue()
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName("DIM_SPEC_INTERNAL_NAME");
		dimSpec.setIsIncludeEmpty(true);
		save(dimSpec);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr1 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr1.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr1.setM_Attribute_ID(attr1.getM_Attribute_ID());
		save(dimSpecAttr1);

		final I_DIM_Dimension_Spec_AttributeValue dimSpecAttributeValue = newInstance(I_DIM_Dimension_Spec_AttributeValue.class);
		dimSpecAttributeValue.setDIM_Dimension_Spec_Attribute(dimSpecAttr1);
		dimSpecAttributeValue.setM_AttributeValue_ID(attr1_value1.getId().getRepoId());
		save(dimSpecAttributeValue);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr2 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr2.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr2.setIsIncludeAllAttributeValues(true);
		dimSpecAttr2.setIsValueAggregate(false);
		dimSpecAttr2.setM_Attribute_ID(attr2.getM_Attribute_ID());
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
		assertThat(groups.getFirst().isEmptyGroup()).isTrue();
		assertThat(groups.getFirst().getGroupName().getDefaultValue()).isEqualTo(DimensionConstants.MSG_NoneOrEmpty.toAD_Message());
		assertThat(groups.getFirst().getAttributesKey().isNone()).isTrue();
		assertThat(groups.getFirst().getAttributeId()).isEmpty();

		// attr1 has two values, but just one of them is "explicitly" added to dimSpecAttr1 which has isIncludeAllAttributeValues=false
		assertThat(groups.get(1).isEmptyGroup()).isFalse();
		assertThat(groups.get(1).getGroupName().getDefaultValue()).isEqualTo("Name_test1_value1");
		assertThat(groups.get(1).getAttributeId()).contains(
				AttributeId.ofRepoId(attr1.getM_Attribute_ID()));
		assertThat(groups.get(1).getAttributesKey().getParts()).containsExactly(AttributesKeyPart.ofAttributeValueId(attr1_value1.getId()));

		// attr2 has two values, and dimSpecAttr2 which has isIncludeAllAttributeValues=true
		assertThat(groups.get(2).isEmptyGroup()).isFalse();
		assertThat(groups.get(2).getGroupName().getDefaultValue()).isEqualTo("dimSpecAttr2_ValueAggregateName");
		assertThat(groups.get(2).getAttributeId()).contains(
				AttributeId.ofRepoId(attr2.getM_Attribute_ID()));
		assertThat(groups.get(2).getAttributesKey().getParts()).containsOnly(
				AttributesKeyPart.ofAttributeValueId(attr2_value1.getId()),
				AttributesKeyPart.ofAttributeValueId(attr2_value2.getId()));
	}

	private I_DIM_Dimension_Spec createDimensionSpecRecord_with_two_dimSpecAttrs_one_with_aggregate_group_name()
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName("DIM_SPEC_INTERNAL_NAME");
		dimSpec.setIsIncludeEmpty(true);
		save(dimSpec);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr1 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr1.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr1.setM_Attribute_ID(attr1.getM_Attribute_ID());
		save(dimSpecAttr1);

		final I_DIM_Dimension_Spec_AttributeValue dimSpecAttributeValue = newInstance(I_DIM_Dimension_Spec_AttributeValue.class);
		dimSpecAttributeValue.setDIM_Dimension_Spec_Attribute(dimSpecAttr1);
		dimSpecAttributeValue.setM_AttributeValue_ID(attr1_value1.getId().getRepoId());
		save(dimSpecAttributeValue);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr2 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr2.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr2.setIsIncludeAllAttributeValues(true);
		dimSpecAttr2.setIsValueAggregate(true);
		dimSpecAttr2.setValueAggregateName("dimSpecAttr2_ValueAggregateName");
		dimSpecAttr2.setM_Attribute_ID(attr2.getM_Attribute_ID());
		save(dimSpecAttr2);

		return dimSpec;
	}
}
