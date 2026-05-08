/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order;

import de.metas.calendar.PeriodRepo;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.project.service.ProjectRepository;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link IOrderBL#updateASIFromProjectId(I_C_OrderLine)}
 */
@ExtendWith(AdempiereTestWatcher.class)
class IOrderBL_UpdateASIFromProjectIdTest
{
	private static final String PROJECT_VALUE = "PROJECT-001";
	private static final String PROJECT_VALUE_2 = "PROJECT-002";

	final private IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new PeriodRepo());
		SpringContextHolder.registerJUnitBean(ProjectRepository.newInstanceForUnitTesting());
		createProjectValueAttr();
	}

	private void createProjectValueAttr()
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(AttributeConstants.ATTR_Project.getCode());
		attribute.setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
	}

	private I_M_Product createProduct(final String productValue)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(productValue);
		product.setName(productValue);
		saveRecord(product);
		return product;
	}

	private I_C_Project createProject(final String projectValue)
	{
		final I_C_Project project = newInstance(I_C_Project.class);
		project.setValue(projectValue);
		project.setName(projectValue);
		saveRecord(project);
		return project;
	}

	private I_C_OrderLine createOrderLine(final I_M_Product product)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setM_Product_ID(product.getM_Product_ID());
		saveRecord(orderLine);
		return orderLine;
	}

	private I_M_AttributeSetInstance createASI(final I_M_Product product)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);
		return asi;
	}

	@Nullable
	private String getProjectValueFromASI(final AttributeSetInstanceId asiId)
	{
		return attributeSetInstanceBL.getImmutableAttributeSetById(asiId)
				.getValueAsStringIfExists(AttributeConstants.ATTR_Project)
				.orElse(null);
	}

	@Test
	void updateASIFromProjectId_WhenASIExists_AndProjectIsSet_ShouldUpdateASIWithProjectValue()
	{
		// Given
		final I_M_Product product = createProduct("Product-1");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_M_AttributeSetInstance asi = createASI(product);
		final I_C_OrderLine orderLine = createOrderLine(product);
		orderLine.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		orderLine.setC_Project_ID(project.getC_Project_ID());
		saveRecord(orderLine);

		// When
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Simulate the model change by calling the method directly
		// Note: In real scenarios, this would be triggered by the model validator framework
		// For now, we need to manually trigger it or verify the ASI is updated

		// Verify that the ASI has the project value
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		final String actualProjectValue = getProjectValueFromASI(asiId);

		// Then
		assertEquals(PROJECT_VALUE, actualProjectValue);
	}

	@Test
	void updateASIFromProjectId_WhenASIDoesNotExist_AndProjectIsSet_ShouldCreateASIAndSetProjectValue()
	{
		// Given
		final I_M_Product product = createProduct("Product-2");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_C_OrderLine orderLine = createOrderLine(product);
		orderLine.setC_Project_ID(project.getC_Project_ID());
		// Note: No ASI is set initially

		// When
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Then
		assertNotNull(orderLine.getM_AttributeSetInstance_ID(), "ASI should be created");
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		final String actualProjectValue = getProjectValueFromASI(asiId);
		assertEquals(PROJECT_VALUE, actualProjectValue);
	}

	@Test
	void updateASIFromProjectId_WhenASIExists_AndProjectIsNull_ShouldClearProjectAttribute()
	{
		// Given
		final I_M_Product product = createProduct("Product-3");
		final I_M_AttributeSetInstance asi = createASI(product);

		// First set a project value
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.setAttributeInstanceValue(asiId, AttributeConstants.ATTR_Project, PROJECT_VALUE);

		final I_C_OrderLine orderLine = createOrderLine(product);
		orderLine.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		saveRecord(orderLine);

		// Now clear the project
		orderLine.setC_Project_ID(-1);

		// When
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Then
		final AttributeSetInstanceId updatedAsiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		final String actualProjectValue = getProjectValueFromASI(updatedAsiId);
		assertNull(actualProjectValue, "Project attribute should be cleared");
	}

	@Test
	void updateASIFromProjectId_WhenASIDoesNotExist_AndProjectIsNull_ShouldNotCreateASI()
	{
		// Given
		final I_M_Product product = createProduct("Product-4");
		final I_C_OrderLine orderLine = createOrderLine(product);
		// No ASI and no project set

		// When
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Then
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		assertTrue(asiId.isNone(), "ASI should be none");
	}

	@Test
	void updateASIFromProjectId_WhenProjectChanges_ShouldUpdateASIWithNewProjectValue()
	{
		// Given
		final I_M_Product product = createProduct("Product-5");
		final I_C_Project project1 = createProject(PROJECT_VALUE);
		final I_C_Project project2 = createProject(PROJECT_VALUE_2);
		final I_M_AttributeSetInstance asi = createASI(product);
		final I_C_OrderLine orderLine = createOrderLine(product);
		orderLine.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());

		// First set project 1
		orderLine.setC_Project_ID(project1.getC_Project_ID());
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Change to project 2
		orderLine.setC_Project_ID(project2.getC_Project_ID());

		// When
		toBeTested.updateASIFromProjectId(orderLine);

		// Then
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		final String actualProjectValue = getProjectValueFromASI(asiId);
		assertEquals(PROJECT_VALUE_2, actualProjectValue);
	}

	@Test
	void updateASIFromProjectId_WhenASIIdIsNegative_ShouldTreatAsNull()
	{
		// Given
		final I_M_Product product = createProduct("Product-6");
		final I_C_Project project = createProject(PROJECT_VALUE);
		final I_C_OrderLine orderLine = createOrderLine(product);
		orderLine.setM_AttributeSetInstance_ID(-1); // Invalid ASI ID
		orderLine.setC_Project_ID(project.getC_Project_ID());

		// When
		final IOrderBL toBeTested = Services.get(IOrderBL.class);
		toBeTested.updateASIFromProjectId(orderLine);

		// Then
		assertNotNull(orderLine.getM_AttributeSetInstance_ID(), "ASI should be created");
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID());
		final String actualProjectValue = getProjectValueFromASI(asiId);
		assertEquals(PROJECT_VALUE, actualProjectValue);
	}
}
