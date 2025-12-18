/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.project.command;

import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.order.model.I_C_Order;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.service.ProjectLineRepository;
import de.metas.project.service.ProjectRepository;
import de.metas.project.service.ProjectService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CreateSalesPurchaseOrderProjectCommandTest
{
	public static final int AD_ORG_ID = 0;
	private ProjectService projectService;

	private ProjectRepository projectRepository;

	@BeforeEach
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
		projectRepository = ProjectRepository.newInstanceForUnitTesting();
		final DocumentNoBuilderFactory documentNoBuilderFactory = DocumentNoBuilderFactory.newInstanceForUnitTesting();
		final ProjectTypeRepository projectTypeRepository = ProjectTypeRepository.newInstanceForUnitTesting();
		final ProjectLineRepository projectLineRepository = ProjectLineRepository.newInstanceForUnitTesting();
		projectService = new ProjectService(documentNoBuilderFactory, projectTypeRepository, projectRepository, projectLineRepository, Optional.empty());

		createProjectType();
	}

	private void createProjectType()
	{
		final I_C_ProjectType projectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);

		projectType.setProjectCategory(ProjectCategory.SalesPurchaseOrder.getCode());
		projectType.setName(ProjectCategory.SalesPurchaseOrder.getCode());
		projectType.setAD_Org_ID(AD_ORG_ID);
		InterfaceWrapperHelper.save(projectType);
	}

	/**
	 * Tests the execute method of CreateSalesPurchaseOrderProjectCommand to ensure
	 * that a project is correctly created and persisted based on the information
	 * from an I_C_Order object.
	 */
	@Test
	void testExecute_createsAndPersistsProject()
	{
		// Arrange: Create a new I_C_Order entity with mock data
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_Org_ID(AD_ORG_ID);
		order.setC_BPartner_ID(100);
		order.setC_BPartner_Location_ID(200);
		order.setAD_User_ID(10);
		order.setC_Currency_ID(300);
		order.setM_Warehouse_ID(400);
		InterfaceWrapperHelper.save(order);

		// Arrange: Create the command to create the project
		final CreateSalesPurchaseOrderProjectCommand command = CreateSalesPurchaseOrderProjectCommand.builder()
				.order(order)
				.projectService(projectService)
				.build();

		// Act: Execute the command
		final ProjectId createdProjectId = command.execute();

		// Assert: Verify the project was created and persisted
		assertThat(createdProjectId).isNotNull();

		// Retrieve the created project via repository
		final I_C_Project project = projectRepository.getById(createdProjectId);
		assertThat(project).isNotNull();
		assertThat(project.getAD_Org_ID()).isEqualTo(order.getAD_Org_ID());
		assertThat(project.getC_BPartner_ID()).isEqualTo(order.getC_BPartner_ID());
		assertThat(project.getC_BPartner_Location_ID()).isEqualTo(order.getC_BPartner_Location_ID());
		assertThat(project.getAD_User_ID()).isEqualTo(order.getAD_User_ID());
		assertThat(project.getC_Currency_ID()).isEqualTo(order.getC_Currency_ID());
		assertThat(project.getM_Warehouse_ID()).isEqualTo(order.getM_Warehouse_ID());
		assertThat(project.getProjectCategory()).isEqualTo(ProjectCategory.SalesPurchaseOrder.getCode());
	}

	/**
	 * Tests the execute method when no user/contact is provided in the order.
	 * Verifies that the project persists with the contactId as null.
	 */
	@Test
	void testExecute_createsProjectWithoutContact()
	{
		// Arrange: Create a new I_C_Order entity with mock data
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_Org_ID(AD_ORG_ID);
		order.setC_BPartner_ID(101);
		order.setC_BPartner_Location_ID(201);
		order.setAD_User_ID(AD_ORG_ID); // No user/contact
		order.setC_Currency_ID(301);
		order.setM_Warehouse_ID(401);
		InterfaceWrapperHelper.save(order);

		// Arrange: Create the command to create the project
		final CreateSalesPurchaseOrderProjectCommand command = CreateSalesPurchaseOrderProjectCommand.builder()
				.order(order)
				.projectService(projectService)
				.build();

		// Act: Execute the command
		final ProjectId createdProjectId = command.execute();

		// Assert: Verify the project was created and persisted
		assertThat(createdProjectId).isNotNull();

		// Retrieve the created project via repository
		final I_C_Project project = projectRepository.getById(createdProjectId);
		assertThat(project).isNotNull();
		assertThat(project.getAD_Org_ID()).isEqualTo(order.getAD_Org_ID());
		assertThat(project.getC_BPartner_ID()).isEqualTo(order.getC_BPartner_ID());
		assertThat(project.getC_BPartner_Location_ID()).isEqualTo(order.getC_BPartner_Location_ID());
		assertThat(project.getAD_User_ID()).isEqualTo(-1); // Contact/user is null
		assertThat(project.getC_Currency_ID()).isEqualTo(order.getC_Currency_ID());
		assertThat(project.getM_Warehouse_ID()).isEqualTo(order.getM_Warehouse_ID());
		assertThat(project.getProjectCategory()).isEqualTo(ProjectCategory.SalesPurchaseOrder.getCode());
	}
}