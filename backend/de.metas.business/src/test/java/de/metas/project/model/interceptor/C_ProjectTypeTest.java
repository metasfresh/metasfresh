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

package de.metas.project.model.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectTypeRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ProjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class C_ProjectTypeTest
{
	private final ProjectTypeRepository typeRepository = new ProjectTypeRepository();
	private final C_ProjectType cProjectType = new C_ProjectType(typeRepository);

	@BeforeEach
	public void init()
	{
		final AdempiereTestHelper adempiereTestHelper = AdempiereTestHelper.get();
		adempiereTestHelper.init();
		AdempiereTestHelper.setupContext_AD_Client_IfNotSet();
	}

	@Test
	void uniqueSalesPurchaseOrderPerOrg_shouldThrowExceptionWhenAnotherSOPOExistsInSameOrg()
	{
		// Given
		final I_C_ProjectType existingProjectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		existingProjectType.setAD_Org_ID(100);
		existingProjectType.setC_ProjectType_ID(300);
		existingProjectType.setProjectCategory(ProjectCategory.SalesPurchaseOrder.getCode());
		InterfaceWrapperHelper.save(existingProjectType);

		final I_C_ProjectType newProjectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		newProjectType.setAD_Org_ID(100);
		newProjectType.setC_ProjectType_ID(200);
		newProjectType.setProjectCategory(ProjectCategory.SalesPurchaseOrder.getCode());

		// When & Then
		assertThatThrownBy(() -> cProjectType.uniqueSalesPurchaseOrderPerOrg(newProjectType))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(AdMessageKey.of("C_ProjectType_One_SOPO_Per_Org").toAD_Message());
	}

	@Test
	void uniqueSalesPurchaseOrderPerOrg_shouldNotThrowExceptionWhenNoOtherSOPOExists()
	{
		// Given
		final I_C_ProjectType newProjectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		newProjectType.setAD_Org_ID(100);
		newProjectType.setC_ProjectType_ID(200);
		newProjectType.setProjectCategory(ProjectCategory.SalesPurchaseOrder.getCode());

		// When
		cProjectType.uniqueSalesPurchaseOrderPerOrg(newProjectType);

		// Then
		// No exception is expected
	}

	@Test
	void uniqueSalesPurchaseOrderPerOrg_shouldNotThrowExceptionWhenCategoryIsNotSOPO()
	{
		// Given
		final I_C_ProjectType projectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectType.setAD_Org_ID(100);
		projectType.setC_ProjectType_ID(200);
		projectType.setProjectCategory(ProjectCategory.General.getCode());

		// When
		cProjectType.uniqueSalesPurchaseOrderPerOrg(projectType);

		// Then
		// No exception is expected
	}

	@Test
	void uniqueSalesPurchaseOrderPerOrg_shouldNotThrowExceptionWhenCategoryIsNull()
	{
		// Given
		final I_C_ProjectType projectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectType.setAD_Org_ID(100);
		projectType.setC_ProjectType_ID(200);
		projectType.setProjectCategory(null);

		// When
		cProjectType.uniqueSalesPurchaseOrderPerOrg(projectType);

		// Then
		// No exception is expected
	}
}