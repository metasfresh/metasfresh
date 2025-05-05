/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.sequence.ProjectValueSequenceProvider;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.project.CreateServiceOrRepairProjectRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_R_StatusCategory;
import org.elasticsearch.core.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ProjectServiceTest
{
	private final static String MOCKED_DocNo = "NextDocNo";
	private ProjectService projectService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final ProjectTypeRepository projectTypeRepository = new ProjectTypeRepository();

		this.projectService = Mockito.spy(new ProjectService(
				projectTypeRepository,
				new ProjectRepository(),
				new ProjectLineRepository(),
				new DocumentNoBuilderFactory(Optional.of(List.of(new ProjectValueSequenceProvider(new ProjectTypeRepository())))),
				Optional.of(ImmutableList.of())));

		Mockito.doReturn(MOCKED_DocNo).when(projectService).getNextProjectValue(any(ProjectTypeId.class));
	}

	@Test
	void createProjectTest()
	{
		final I_R_StatusCategory statusCategory = newInstance(I_R_StatusCategory.class);
		save(statusCategory);
		// Given
		final I_C_ProjectType projectTypeRecord = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectTypeRecord.setProjectCategory("N");
		projectTypeRecord.setAD_Org_ID(1000000);
		projectTypeRecord.setR_StatusCategory_ID(statusCategory.getR_StatusCategory_ID());
		projectTypeRecord.setName("projectTypeName");

		saveRecord(projectTypeRecord);

		final I_C_UOM uomRecord = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final CreateServiceOrRepairProjectRequest request = CreateServiceOrRepairProjectRequest.builder()
				.orgId(OrgId.MAIN)
				.projectCategory(ProjectCategory.General)
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(1, 2))
				.contactId(BPartnerContactId.ofRepoId(1, 3))
				.currencyId(CurrencyId.ofRepoId(100))
				.priceListVersionId(PriceListVersionId.ofRepoId(4))
				.warehouseId(WarehouseId.ofRepoId(5))
				.line(CreateServiceOrRepairProjectRequest.ProjectLine.builder()
							  .productId(ProductId.ofRepoId(6))
							  .plannedQty(Quantity.zero(uomRecord))
							  .build())
				.build();

		// When
		final ProjectId projectId = projectService.createProject(request);

		// Then
		final I_C_Project projectRecord = InterfaceWrapperHelper.load(projectId, I_C_Project.class);

		assertThat(projectRecord.getAD_Org_ID()).isEqualTo(1000000);
		assertThat(projectRecord.getC_BPartner_ID()).isEqualTo(1);
		assertThat(projectRecord.getC_BPartner_Location_ID()).isEqualTo(2);
		assertThat(projectRecord.getAD_User_ID()).isEqualTo(3);
		assertThat(projectRecord.getC_Currency_ID()).isEqualTo(100);
		assertThat(projectRecord.getM_PriceList_Version_ID()).isEqualTo(4);
		assertThat(projectRecord.getM_Warehouse_ID()).isEqualTo(5);
		assertThat(projectRecord.getName()).isEqualTo(MOCKED_DocNo);
		assertThat(projectRecord.getC_ProjectType_ID()).isEqualTo(projectTypeRecord.getC_ProjectType_ID());
	}
}
