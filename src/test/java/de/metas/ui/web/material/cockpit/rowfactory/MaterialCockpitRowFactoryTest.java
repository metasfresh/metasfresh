package de.metas.ui.web.material.cockpit.rowfactory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.api.AttributesKeys;
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
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
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
	private DimensionSpecGroup dimensionspecGroup_attr1_value1;
	private DimensionSpecGroup dimensionspecGroup_attr2_value1;
	private DimensionSpecGroup dimensionspecGroup_empty;
	private I_M_AttributeValue attr2_value2;

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
		attr2_value2 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value2");

		initDimenstionSpec();

		materialCockpitRowFactory = new MaterialCockpitRowFactory();
	}

	protected void initDimenstionSpec()
	{
		dimensionSpec = createDimensionSpec(attr1, attr1_value1, attr2);
		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();

		assertThat(groups).hasSize(4);

		dimensionspecGroup_empty = groups.get(0);
		assertThat(dimensionspecGroup_empty.isEmptyGroup()).isTrue();

		dimensionspecGroup_attr1_value1 = groups.get(1);
		assertThat(dimensionspecGroup_attr1_value1.getAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(attr1_value1.getM_AttributeValue_ID()));

		dimensionspecGroup_attr2_value1 = groups.get(2);
		assertThat(dimensionspecGroup_attr2_value1.getAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(attr2_value1.getM_AttributeValue_ID()));

		final DimensionSpecGroup dimensionspecGroup_attr2_value2 = groups.get(3);
		assertThat(dimensionspecGroup_attr2_value2.getAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(attr2_value2.getM_AttributeValue_ID()));
	}


	private DimensionSpec createDimensionSpec(
			final I_M_Attribute attr1,
			final I_M_AttributeValue attr1_value1,
			final I_M_Attribute attr2)
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName(MaterialCockpitRowFactory.DIM_SPEC_INTERNAL_NAME);
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
		dimSpecAttr2.setM_Attribute(attr2);
		save(dimSpecAttr2);

		return DimensionSpec.ofRecord(dimSpec);
	}

	/**
	 * Verifies that the {@link I_MD_Cockpit} and {@link I_MD_Stock} data rows are correctly applied to {@link MaterialCockpitRow}s based on the given dimension spec.
	 * In this test, we have a dimension spec with tree groups, on of them is empty.
	 */
	@Test
	public void createRows_contains_attribute_groups()
	{
		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asi1, attr1_value1);
		attributeSetInstanceBL.getCreateAttributeInstance(asi1, attr2_value1);

		final AttributesKey attributesKeyWithAttr1_and_attr2 = AttributesKeys.createAttributesKeyFromASIAllAttributeValues(asi1.getM_AttributeSetInstance_ID()).get();

		final I_MD_Cockpit cockpitRecord1 = newInstance(I_MD_Cockpit.class);
		cockpitRecord1.setM_Product(product);
		cockpitRecord1.setDateGeneral(SystemTime.asTimestamp());
		cockpitRecord1.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		cockpitRecord1.setQtyReserved_Purchase(BigDecimal.TEN);
		save(cockpitRecord1);

		final AttributesKey attributesKey2 = AttributesKey.NONE;

		final I_MD_Cockpit cockpitRecordWithEmptyAttributesKey = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithEmptyAttributesKey.setM_Product(product);
		cockpitRecordWithEmptyAttributesKey.setDateGeneral(SystemTime.asTimestamp());
		cockpitRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		cockpitRecordWithEmptyAttributesKey.setQtyReserved_Purchase(BigDecimal.ONE);
		save(cockpitRecordWithEmptyAttributesKey);

		final I_MD_Stock stockRecord1 = newInstance(I_MD_Stock.class);
		stockRecord1.setM_Product(product);
		stockRecord1.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		stockRecord1.setM_Warehouse_ID(30);
		stockRecord1.setQtyOnHand(new BigDecimal("11"));
		save(stockRecord1);

		final I_MD_Stock stockRecordWithEmptyAttributesKey = newInstance(I_MD_Stock.class);
		stockRecordWithEmptyAttributesKey.setM_Product(product);
		stockRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		stockRecordWithEmptyAttributesKey.setM_Warehouse_ID(20);
		stockRecordWithEmptyAttributesKey.setQtyOnHand(new BigDecimal("12"));
		save(stockRecordWithEmptyAttributesKey);

		final Timestamp today = TimeUtil.getDay(SystemTime.asTimestamp());

		final CreateRowsRequest request = CreateRowsRequest.builder()
				.date(today)
				.productsToListEvenIfEmpty(ImmutableList.of())
				.cockpitRecords(ImmutableList.of(cockpitRecord1, cockpitRecordWithEmptyAttributesKey))
				.stockRecords(ImmutableList.of(stockRecord1, stockRecordWithEmptyAttributesKey))
				.build();

		// invoke method under test
		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);

		assertThat(result).hasSize(1);
		final MaterialCockpitRow mainRow = result.get(0);
		assertThat(mainRow.getQtyOnHandStock()).isEqualByComparingTo("23");
		assertThat(mainRow.getQtyReservedPurchase()).isEqualByComparingTo("11");


		final List<MaterialCockpitRow> includedRows = mainRow.getIncludedRows();
		// the cockpit and stock record with "attributesKeyWithAttr1_and_attr2" are added to matching non-empty groups of the dimension spec;
		// the cockpit and stock record with the empty attributesKey is added to the empty dimension spec group
		assertThat(includedRows).hasSize(3);

		final MaterialCockpitRow emptyGroupRow = filterForRowBy(includedRows, dimensionspecGroup_empty);
		assertThat(emptyGroupRow.getQtyOnHandStock()).isEqualByComparingTo("12");
		assertThat(emptyGroupRow.getQtyReservedPurchase()).isEqualByComparingTo("1");

		final MaterialCockpitRow attr1GroupRow = filterForRowBy(includedRows, dimensionspecGroup_attr1_value1);
		assertThat(attr1GroupRow.getQtyOnHandStock()).isEqualByComparingTo("11");
		assertThat(attr1GroupRow.getQtyReservedPurchase()).isEqualByComparingTo("10");

		final MaterialCockpitRow attr2GroupRow = filterForRowBy(includedRows, dimensionspecGroup_attr2_value1);
		assertThat(attr2GroupRow.getQtyOnHandStock()).isEqualByComparingTo("11");
		assertThat(attr2GroupRow.getQtyReservedPurchase()).isEqualByComparingTo("10");

	}

	protected MaterialCockpitRow filterForRowBy(final List<MaterialCockpitRow> includedRows, final DimensionSpecGroup dimensionspecGroup_empty2)
	{
		final MaterialCockpitRow emptyGroupRow = includedRows.stream().filter(row ->
			row.getProductCategoryOrSubRowName().equals(dimensionspecGroup_empty2.getGroupName().getDefaultValue())).findFirst().get();
		return emptyGroupRow;
	}

	@Test
	public void createEmptyRowBuckets()
	{
		final Timestamp today = TimeUtil.getDay(SystemTime.asTimestamp());

		// invoke method under test
		final Map<MainRowBucketId, MainRowWithSubRows> result = materialCockpitRowFactory.createEmptyRowBuckets(
				ImmutableList.of(product),
				today);

		assertThat(result).hasSize(1);
		final MainRowBucketId productIdAndDate = MainRowBucketId.createPlainInstance(product.getM_Product_ID(), today);
		assertThat(result).containsKey(productIdAndDate);
		final MainRowWithSubRows mainRowBucket = result.get(productIdAndDate);
		assertThat(mainRowBucket.getProductIdAndDate()).isEqualTo(productIdAndDate);

		final Map<DimensionSpecGroup, DimensionGroupSubRowBucket> subRowBuckets = mainRowBucket.getDimensionGroupSubRows();
		assertThat(subRowBuckets).hasSize(dimensionSpec.retrieveGroups().size());
		assertThat(subRowBuckets.keySet()).containsOnlyElementsOf(dimensionSpec.retrieveGroups());
	}
}
