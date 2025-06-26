/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.department;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class BPartnerDepartmentRepoTest
{

	private BPartnerDepartmentRepo bPartnerDepartmentRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		bPartnerDepartmentRepo = new BPartnerDepartmentRepo();
	}

	@Test
	void getById()
	{
		final I_C_BPartner_Department department1 = newInstance(I_C_BPartner_Department.class);
		department1.setC_BPartner_ID(10);
		department1.setValue("Value1");
		department1.setName("Name1");
		department1.setDescription("Description1");
		saveRecord(department1);
		final BPartnerDepartmentId id1 = BPartnerDepartmentId.ofRepoId(10, department1.getC_BPartner_Department_ID());

		final I_C_BPartner_Department department2 = newInstance(I_C_BPartner_Department.class);
		department2.setC_BPartner_ID(10);
		department2.setValue("Value2");
		department2.setName("Name2");
		department2.setDescription(null);
		saveRecord(department2);
		final BPartnerDepartmentId id2 = BPartnerDepartmentId.ofRepoId(10, department2.getC_BPartner_Department_ID());

		final BPartnerDepartment result1 = bPartnerDepartmentRepo.getById(id1);
		assertThat(result1).extracting("searchKey", "name", "description").containsExactly("Value1", "Name1", "Description1");

		final BPartnerDepartment result2 = bPartnerDepartmentRepo.getById(id2);
		assertThat(result2).extracting("searchKey", "name", "description").containsExactly("Value2", "Name2", null);
	}
}