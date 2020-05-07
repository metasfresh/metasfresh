package de.metas.material.planning.impl;

import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_M_Attribute;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.business.BusinessTestHelper;

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

public class ProductPlanningDAO_findTest
{

	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private I_M_Warehouse warehouse;
	private I_M_Product product;
	private I_S_Resource plant;
	private AttributesTestHelper attributesTestHelper;

	private I_M_Attribute organicAttribute;
	private I_M_AttributeValue organicAttributeValue;

	private I_M_Attribute madeInCologneAttribute;
	private I_M_AttributeValue madeInCologneAttributeValue;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

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
		final I_PP_Product_Planning productPlanning = createAttributeIndependantProductPlanning();

		final I_PP_Product_Planning result = invokeFindMethodWithASI(AttributeConstants.M_AttributeSetInstance_ID_None);
		assertThat(result).isNotNull();
		assertThat(result.getPP_Product_Planning_ID()).isEqualTo(productPlanning.getPP_Product_Planning_ID());
	}

	@Test
	public void productPlanningWithASI_searchWithNoAsi()
	{
		final I_PP_Product_Planning productPlanningWithoutASI = createAttributeIndependantProductPlanning();
		final I_M_AttributeSetInstance organicAttributeSetInstance = createOrganicASI();

		final I_PP_Product_Planning productPlanningWithAsi = createAttributeIndependantProductPlanning();
		productPlanningWithAsi.setM_AttributeSetInstance_ID(organicAttributeSetInstance.getM_AttributeSetInstance_ID());

		final String attributesKeyFOrOrganicAttribute = Integer.toString(organicAttributeValue.getM_AttributeValue_ID());
		productPlanningWithAsi.setStorageAttributesKey(attributesKeyFOrOrganicAttribute);

		save(productPlanningWithAsi);

		final I_PP_Product_Planning resultWithoutASI = invokeFindMethodWithASI(AttributeConstants.M_AttributeSetInstance_ID_None);
		assertThat(resultWithoutASI).isNotNull();
		assertThat(resultWithoutASI.getPP_Product_Planning_ID()).isEqualTo(productPlanningWithoutASI.getPP_Product_Planning_ID());
	}

	@Test
	public void productPlanningWithASI_searchWithSameAsi()
	{
		createAttributeIndependantProductPlanning();
		final I_M_AttributeSetInstance organicAttributeSetInstance = createOrganicASI();

		final I_PP_Product_Planning productPlanningWithAsi = createAttributeIndependantProductPlanning();
		productPlanningWithAsi.setM_AttributeSetInstance_ID(organicAttributeSetInstance.getM_AttributeSetInstance_ID());

		final String attributesKeyFOrOrganicAttribute = Integer.toString(organicAttributeValue.getM_AttributeValue_ID());
		productPlanningWithAsi.setStorageAttributesKey(attributesKeyFOrOrganicAttribute);

		productPlanningWithAsi.setIsAttributeDependant(true);
		save(productPlanningWithAsi);

		final I_PP_Product_Planning resultWithAsi = invokeFindMethodWithASI(organicAttributeSetInstance.getM_AttributeSetInstance_ID());

		assertThat(resultWithAsi).isNotNull();
		assertThatModel(resultWithAsi).hasSameIdAs(productPlanningWithAsi);
	}

	@Test
	public void attributeDependantProductPlanningWithoutASI_searchWithASI_noResult()
	{
		final I_PP_Product_Planning productPlanningWithoutAsi = createAttributeIndependantProductPlanning();
		productPlanningWithoutAsi.setIsAttributeDependant(true);
		save(productPlanningWithoutAsi);

		final I_M_AttributeSetInstance organicAndMadeInCologneASI = createOrganicAndMadeInCologneASI();
		final I_PP_Product_Planning result = invokeFindMethodWithASI(organicAndMadeInCologneASI.getM_AttributeSetInstance_ID());

		assertThat(result).isNull();
	}

	@Test
	public void attributeInDependantProductPlanningWithoutASI_searchWithASI_result()
	{
		final I_PP_Product_Planning productPlanningWithoutAsi = createAttributeIndependantProductPlanning();
		productPlanningWithoutAsi.setIsAttributeDependant(false);
		save(productPlanningWithoutAsi);

		final I_M_AttributeSetInstance organicAndMadeInCologneASI = createOrganicASI();
		final I_PP_Product_Planning result = invokeFindMethodWithASI(organicAndMadeInCologneASI.getM_AttributeSetInstance_ID());

		assertThat(result.getPP_Product_Planning_ID()).isEqualTo(productPlanningWithoutAsi.getPP_Product_Planning_ID());
	}

	private I_M_Attribute createStorageRelevantListAttribute(final String name)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(name, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attribute.setIsStorageRelevant(true);
		save(attribute);

		return attribute;
	}

	private I_M_AttributeSetInstance createOrganicAndMadeInCologneASI()
	{
		final I_M_AttributeSetInstance organicAttributeSetInstance = createOrganicASI();

		final I_M_AttributeSetInstance organicAndMadeInCologneASI = ASICopy.newInstance(organicAttributeSetInstance).copy();

		final I_M_AttributeInstance madeInCologneAttributeInstance = newInstance(I_M_AttributeInstance.class);
		madeInCologneAttributeInstance.setM_AttributeSetInstance(organicAndMadeInCologneASI);
		madeInCologneAttributeInstance.setM_Attribute(madeInCologneAttribute);
		madeInCologneAttributeInstance.setM_AttributeValue(madeInCologneAttributeValue);
		madeInCologneAttributeInstance.setValue(madeInCologneAttribute.getValue());
		save(madeInCologneAttributeInstance);

		return organicAndMadeInCologneASI;
	}

	private I_M_AttributeSetInstance createOrganicASI()
	{

		final I_M_AttributeSetInstance organicAttributeSetInstance = newInstance(I_M_AttributeSetInstance.class);
		save(organicAttributeSetInstance);

		final I_M_AttributeInstance organicAttributeInstance = newInstance(I_M_AttributeInstance.class);
		organicAttributeInstance.setM_AttributeSetInstance(organicAttributeSetInstance);
		organicAttributeInstance.setM_Attribute(organicAttribute);
		organicAttributeInstance.setM_AttributeValue(organicAttributeValue);
		organicAttributeInstance.setValue(organicAttributeValue.getValue());
		save(organicAttributeInstance);

		return organicAttributeSetInstance;
	}

	private I_PP_Product_Planning createAttributeIndependantProductPlanning()
	{
		final I_PP_Product_Planning productPlanning = newInstance(I_PP_Product_Planning.class);
		productPlanning.setM_Product(product);
		productPlanning.setM_Warehouse(warehouse);
		productPlanning.setS_Resource(plant);
		productPlanning.setIsAttributeDependant(false);
		save(productPlanning);

		return productPlanning;
	}

	private I_PP_Product_Planning invokeFindMethodWithASI(final int attributeSetInstanceId)
	{
		final I_PP_Product_Planning result = new ProductPlanningDAO().find(
				warehouse.getAD_Org_ID(),
				warehouse.getM_Warehouse_ID(),
				plant.getS_Resource_ID(),
				product.getM_Product_ID(),
				attributeSetInstanceId);

		return result;
	}
}
