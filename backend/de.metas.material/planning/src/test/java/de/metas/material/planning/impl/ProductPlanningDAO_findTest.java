package de.metas.material.planning.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-planning
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

@ExtendWith(AdempiereTestWatcher.class)
public class ProductPlanningDAO_findTest
{
	private ProductPlanningDAO productPlanningDAO;

	private I_M_Warehouse warehouse;
	private I_M_Product product;
	private I_S_Resource plant;
	private AttributesTestHelper attributesTestHelper;

	private I_M_Attribute organicAttribute;
	private AttributeListValue organicAttributeValue;

	private I_M_Attribute madeInCologneAttribute;
	private AttributeListValue madeInCologneAttributeValue;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.productPlanningDAO = (ProductPlanningDAO)Services.get(IProductPlanningDAO.class);

		warehouse = BusinessTestHelper.createWarehouse("warehouse");

		product = BusinessTestHelper.createProduct("product", BusinessTestHelper.createUomKg());

		plant = newInstance(I_S_Resource.class);
		save(plant);

		attributesTestHelper = new AttributesTestHelper();

		organicAttribute = createStorageRelevantListAttribute("MadeInCologneAttributeValue");
		organicAttributeValue = attributesTestHelper.createM_AttributeValue(organicAttribute, "MadeInCologneAttributeValue");

		madeInCologneAttribute = createStorageRelevantListAttribute("OrganicAttribute");
		madeInCologneAttributeValue = attributesTestHelper.createM_AttributeValue(organicAttribute, "OrganicAttributeValue");
	}

	@Test
	public void productPlanningWithoutASI_searchWithNoAsi()
	{
		final ProductPlanning productPlanning = createAttributeIndependantProductPlanning();

		final ProductPlanning result = invokeFindMethodWithASI(AttributeSetInstanceId.NONE);
		assertThat(result).isEqualTo(productPlanning);
	}

	@Test
	public void productPlanningWithASI_searchWithNoAsi()
	{
		final ProductPlanning productPlanningWithoutASI = createAttributeIndependantProductPlanning();

		//noinspection unused
		final ProductPlanning productPlanningWithAsi = createProductPlanning(builder -> builder
				.attributeSetInstanceId(createOrganicASI())
				.storageAttributesKey(Integer.toString(organicAttributeValue.getId().getRepoId()))
		);

		final ProductPlanning resultWithoutASI = invokeFindMethodWithASI(AttributeSetInstanceId.NONE);
		assertThat(resultWithoutASI).isEqualTo(productPlanningWithoutASI);
	}

	@Test
	public void productPlanningWithASI_searchWithSameAsi()
	{
		createAttributeIndependantProductPlanning();

		final AttributeSetInstanceId organicAttributeSetInstance = createOrganicASI();
		final String attributesKeyForOrganicAttribute = Integer.toString(organicAttributeValue.getId().getRepoId());
		final ProductPlanning productPlanningWithAsi = createProductPlanning(builder -> builder
				.isAttributeDependant(true)
				.attributeSetInstanceId(organicAttributeSetInstance)
				.storageAttributesKey(attributesKeyForOrganicAttribute)
		);

		final ProductPlanning resultWithAsi = invokeFindMethodWithASI(organicAttributeSetInstance);
		assertThat(resultWithAsi).isEqualTo(productPlanningWithAsi);
	}

	@Test
	public void attributeDependantProductPlanningWithoutASI_searchWithASI_noResult()
	{
		//noinspection unused
		final ProductPlanning productPlanningWithoutAsi = createProductPlanning(builder -> builder.isAttributeDependant(true));

		final AttributeSetInstanceId organicAndMadeInCologneASI = createOrganicAndMadeInCologneASI();
		final ProductPlanning result = invokeFindMethodWithASI(organicAndMadeInCologneASI);

		assertThat(result).isNull();
	}

	@Test
	public void attributeInDependantProductPlanningWithoutASI_searchWithASI_result()
	{
		final ProductPlanning productPlanningWithoutAsi = createProductPlanning(builder -> builder.isAttributeDependant(false));

		final AttributeSetInstanceId organicAndMadeInCologneASI = createOrganicASI();
		final ProductPlanning result = invokeFindMethodWithASI(organicAndMadeInCologneASI);

		assertThat(result).isEqualTo(productPlanningWithoutAsi);
	}

	private I_M_Attribute createStorageRelevantListAttribute(final String name)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(name, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		save(attribute);

		return attribute;
	}

	private AttributeSetInstanceId createOrganicAndMadeInCologneASI()
	{
		final I_M_AttributeSetInstance organicAttributeSetInstance = createOrganicASIRecord();

		final I_M_AttributeSetInstance organicAndMadeInCologneASI = ASICopy.newInstance(organicAttributeSetInstance).copy();

		final I_M_AttributeInstance madeInCologneAttributeInstance = newInstance(I_M_AttributeInstance.class);
		madeInCologneAttributeInstance.setM_AttributeSetInstance(organicAndMadeInCologneASI);
		madeInCologneAttributeInstance.setM_Attribute_ID(madeInCologneAttribute.getM_Attribute_ID());
		madeInCologneAttributeInstance.setM_AttributeValue_ID(madeInCologneAttributeValue.getId().getRepoId());
		madeInCologneAttributeInstance.setValue(madeInCologneAttribute.getValue());
		save(madeInCologneAttributeInstance);

		return AttributeSetInstanceId.ofRepoId(organicAndMadeInCologneASI.getM_AttributeSetInstance_ID());
	}

	private AttributeSetInstanceId createOrganicASI()
	{
		final I_M_AttributeSetInstance organicAttributeSetInstance = createOrganicASIRecord();
		return AttributeSetInstanceId.ofRepoId(organicAttributeSetInstance.getM_AttributeSetInstance_ID());
	}

	private I_M_AttributeSetInstance createOrganicASIRecord()
	{
		final I_M_AttributeSetInstance organicAttributeSetInstance = newInstance(I_M_AttributeSetInstance.class);
		save(organicAttributeSetInstance);

		final I_M_AttributeInstance organicAttributeInstance = newInstance(I_M_AttributeInstance.class);
		organicAttributeInstance.setM_AttributeSetInstance(organicAttributeSetInstance);
		organicAttributeInstance.setM_Attribute_ID(organicAttribute.getM_Attribute_ID());
		organicAttributeInstance.setM_AttributeValue_ID(organicAttributeValue.getId().getRepoId());
		organicAttributeInstance.setValue(organicAttributeValue.getValue());
		save(organicAttributeInstance);

		return organicAttributeSetInstance;
	}

	private ProductPlanning createAttributeIndependantProductPlanning()
	{
		return createProductPlanning((builder) -> {});
	}

	private ProductPlanning createProductPlanning(final Consumer<ProductPlanning.ProductPlanningBuilder> customizer)
	{
		final ProductPlanning.ProductPlanningBuilder builder = ProductPlanning.builder()
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.plantId(ResourceId.ofRepoId(plant.getS_Resource_ID()))
				.isAttributeDependant(false);
		customizer.accept(builder);
		return productPlanningDAO.save(builder.build());
	}

	private ProductPlanning invokeFindMethodWithASI(final AttributeSetInstanceId attributeSetInstanceId)
	{
		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.orgId(OrgId.ofRepoId(warehouse.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.plantId(ResourceId.ofRepoId(plant.getS_Resource_ID()))
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		return productPlanningDAO.find(query).orElse(null);
	}
}
