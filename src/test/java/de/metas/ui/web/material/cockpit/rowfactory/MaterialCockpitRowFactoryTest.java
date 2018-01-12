package de.metas.ui.web.material.cockpit.rowfactory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import org.compiere.model.I_S_Resource;
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
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest;
import lombok.NonNull;

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
	private static final BigDecimal TWELVE = new BigDecimal("12");
	private static final BigDecimal TEN = BigDecimal.TEN;
	private static final BigDecimal ONE = BigDecimal.ONE;
	private static final BigDecimal ELEVEN = new BigDecimal("11");
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

		final I_MD_Cockpit cockpitRecordWithAttributes = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithAttributes.setM_Product(product);
		cockpitRecordWithAttributes.setDateGeneral(SystemTime.asTimestamp());
		cockpitRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		cockpitRecordWithAttributes.setQtyReserved_Purchase(TEN);
		save(cockpitRecordWithAttributes);

		final AttributesKey attributesKey2 = AttributesKey.NONE;

		final I_MD_Cockpit cockpitRecordWithEmptyAttributesKey = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithEmptyAttributesKey.setM_Product(product);
		cockpitRecordWithEmptyAttributesKey.setDateGeneral(SystemTime.asTimestamp());
		cockpitRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		cockpitRecordWithEmptyAttributesKey.setQtyReserved_Purchase(ONE);
		save(cockpitRecordWithEmptyAttributesKey);

		final I_M_Warehouse warehouseWithPlant = createWarehousewithPlant("plantName");

		final I_MD_Stock stockRecordWithAttributes = newInstance(I_MD_Stock.class);
		stockRecordWithAttributes.setM_Product(product);
		stockRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		stockRecordWithAttributes.setM_Warehouse(warehouseWithPlant);
		stockRecordWithAttributes.setQtyOnHand(ELEVEN);
		save(stockRecordWithAttributes);

		final I_MD_Stock stockRecordWithEmptyAttributesKey = newInstance(I_MD_Stock.class);
		stockRecordWithEmptyAttributesKey.setM_Product(product);
		stockRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		stockRecordWithEmptyAttributesKey.setM_Warehouse(warehouseWithPlant);
		stockRecordWithEmptyAttributesKey.setQtyOnHand(TWELVE);
		save(stockRecordWithEmptyAttributesKey);

		final Timestamp today = TimeUtil.getDay(SystemTime.asTimestamp());

		final CreateRowsRequest request = CreateRowsRequest.builder()
				.date(today)
				.productsToListEvenIfEmpty(ImmutableList.of())
				.cockpitRecords(ImmutableList.of(cockpitRecordWithAttributes, cockpitRecordWithEmptyAttributesKey))
				.stockRecords(ImmutableList.of(stockRecordWithAttributes, stockRecordWithEmptyAttributesKey))
				.build();

		// invoke method under test
		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);

		assertThat(result).hasSize(1);
		final MaterialCockpitRow mainRow = result.get(0);
		assertThat(mainRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN.add(TWELVE));
		assertThat(mainRow.getQtyReservedPurchase()).isEqualByComparingTo(TEN.add(ONE));

		final List<MaterialCockpitRow> includedRows = mainRow.getIncludedRows();
		// there shall be 4 rows:
		// 2: the cockpitRecordWithAttributes and stockRecordWithAttributes are added to the matching non-empty groups of the dimension spec
		// 1: the cockpit and stock record with the empty attributesKey is added to the empty dimension spec group
		// 1: both stock records are added to a counting record
		assertThat(includedRows).hasSize(4);

		final MaterialCockpitRow emptyGroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_empty);
		assertThat(emptyGroupRow.getQtyOnHandStock()).isEqualByComparingTo(TWELVE);
		assertThat(emptyGroupRow.getQtyReservedPurchase()).isEqualByComparingTo(ONE);

		final MaterialCockpitRow attr1GroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_attr1_value1);
		assertThat(attr1GroupRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);
		assertThat(attr1GroupRow.getQtyReservedPurchase()).isEqualByComparingTo(TEN);

		final MaterialCockpitRow attr2GroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_attr2_value1);
		assertThat(attr2GroupRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);
		assertThat(attr2GroupRow.getQtyReservedPurchase()).isEqualByComparingTo(TEN);

		final MaterialCockpitRow countingRow = extractSingleCountingRow(includedRows);
		assertThat(countingRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN.add(TWELVE));
		assertThat(countingRow.getProductCategoryOrSubRowName()).isEqualTo("plantName");
	}

	private I_M_Warehouse createWarehousewithPlant(@NonNull final String plantName)
	{
		final I_S_Resource plant = newInstance(I_S_Resource.class);
		plant.setName(plantName);
		save(plant);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setPP_Plant(plant);
		save(warehouse);
		return warehouse;
	}

	private static MaterialCockpitRow extractRowWithDimensionSpecGroup(
			@NonNull final List<MaterialCockpitRow> rows,
			@NonNull final DimensionSpecGroup dimensionspecGroup)
	{
		final MaterialCockpitRow emptyGroupRow = rows.stream()
				.filter(row -> {

					return Objects.equals(
							row.getProductCategoryOrSubRowName(),
							dimensionspecGroup.getGroupName().getDefaultValue());
				})
				.findFirst().get();
		return emptyGroupRow;
	}

	private static MaterialCockpitRow extractSingleCountingRow(
			@NonNull final List<MaterialCockpitRow> rows)
	{
		final MaterialCockpitRow emptyGroupRow = rows.stream()
				.filter(row -> row.getId().toString().startsWith("countingRow")

				)
				.findFirst().get();
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
