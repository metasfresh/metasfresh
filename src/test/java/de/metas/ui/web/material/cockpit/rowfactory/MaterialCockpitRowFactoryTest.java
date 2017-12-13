package de.metas.ui.web.material.cockpit.rowfactory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;
import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest;

/*
 * #%L
 * metasfresh-webui-api
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

public class MaterialCockpitRowFactoryTest
{
	private AttributesTestHelper attributesTestHelper;
	private MaterialCockpitRowFactory materialCockpitRowFactory;
	private I_M_Product product;
	private I_M_Attribute attr1;
	private I_M_Attribute attr2;
	private I_M_AttributeValue attr1_value1;
	private I_M_AttributeValue attr2_value1;
	private DimensionSpec dimensionSpec;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setName("productCategoryName");
		save(productCategory);

		product = newInstance(I_M_Product.class);
		product.setValue("productValue");
		product.setName("productName");
		product.setIsStocked(true);
		product.setM_Product_Category(productCategory);
		save(product);

		attr1 = attributesTestHelper.createM_Attribute("test1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr1_value1 = attributesTestHelper.createM_AttributeValue(attr1, "test1_value1");

		attr2 = attributesTestHelper.createM_Attribute("test21", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr2_value1 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value1");
		attributesTestHelper.createM_AttributeValue(attr2, "test2_value2");

		dimensionSpec = createDimensionSpec(attr1, attr1_value1, attr2);

		materialCockpitRowFactory = new MaterialCockpitRowFactory();
	}

	@Test
	public void loadRowsFromStream_contains_attribute_groups()
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attr1_value1);
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attr2_value1);

		final I_X_MRP_ProductInfo_Detail_MV record = newInstance(I_X_MRP_ProductInfo_Detail_MV.class);
		record.setM_Product(product);
		record.setDateGeneral(SystemTime.asTimestamp());
		record.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		record.setQtyOnHand(BigDecimal.TEN);
		save(record);

		final Timestamp today = TimeUtil.getDay(SystemTime.asTimestamp());

		final CreateRowsRequest request = CreateRowsRequest.builder()
				.date(today)
				.relevantProducts(ImmutableList.of())
				.dataRecords(ImmutableList.of(record))
				.countings(ImmutableList.of())
				.build();

		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);
		assertThat(result).hasSize(1);
	}

	@Test
	public void createEmptyBuckets()
	{
		final Timestamp today = TimeUtil.getDay(SystemTime.asTimestamp());

		final Map<MainRowBucketId, MainRowBucket> result = materialCockpitRowFactory.createEmptyRowBuckets(
				ImmutableList.of(product),
				today);
		assertThat(result).hasSize(1);

		final MainRowBucketId productIdAndDate = MainRowBucketId.createPlainInstance(product.getM_Product_ID(), today);
		assertThat(result).containsKey(productIdAndDate);
		final MainRowBucket mainRowBucket = result.get(productIdAndDate);
		assertThat(mainRowBucket.getProductIdAndDate()).isEqualTo(productIdAndDate);

		final Map<DimensionSpecGroup, AttributeSubRowBucket> subRowBuckets = mainRowBucket.getAttributeSubRows();
		assertThat(subRowBuckets).hasSize(dimensionSpec.retrieveGroups().size());
		assertThat(subRowBuckets.keySet()).containsOnlyElementsOf(dimensionSpec.retrieveGroups());
	}

	private DimensionSpec createDimensionSpec(final I_M_Attribute attr1, final I_M_AttributeValue attr1_value1, final I_M_Attribute attr2)
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName(MaterialCockpitRowFactory.DIM_SPEC_INTERNAL_NAME);
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
		dimSpecAttr2.setM_Attribute(attr2);
		save(dimSpecAttr2);

		return DimensionSpec.ofRecord(dimSpec);
	}
}
