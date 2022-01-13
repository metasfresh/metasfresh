/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.user.role.repository;

import de.metas.bpartner.user.role.UserAssignedRoleId;
import de.metas.bpartner.user.role.UserRole;
import de.metas.bpartner.user.role.UserRoleId;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_User_Assigned_Role;
import org.compiere.model.I_C_User_Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class UserRoleRepositoryTest
{
	private UserRoleRepository userRoleRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		userRoleRepository = new UserRoleRepository();
	}

	@Test
	public void getUserRolesTest()
	{
		// given
		final I_AD_User userRecord = newInstance(I_AD_User.class);
		saveRecord(userRecord);

		final I_C_User_Role roleRecord = newInstance(I_C_User_Role.class);
		roleRecord.setName("testName");
		saveRecord(roleRecord);

		final I_C_User_Assigned_Role assignedRoleRecord = newInstance(I_C_User_Assigned_Role.class);
		assignedRoleRecord.setAD_User_ID(userRecord.getAD_User_ID());
		assignedRoleRecord.setC_User_Role_ID(roleRecord.getC_User_Role_ID());
		saveRecord(assignedRoleRecord);

		// when
		final List<UserRole> roles = userRoleRepository.getUserRoles(UserId.ofRepoId(userRecord.getAD_User_ID()));

		// then
		assertThat(roles).isNotNull();
		assertThat(roles.size()).isEqualTo(1);

		final UserRole role = roles.get(0);
		assertThat(role.getName()).isEqualTo(roleRecord.getName());
		assertThat(role.isUniquePerBpartner()).isFalse();

		final UserAssignedRoleId assignedRoleId = UserAssignedRoleId.ofRepoId(UserRoleId.ofRepoId(roleRecord.getC_User_Role_ID()), assignedRoleRecord.getC_User_Assigned_Role_ID());
		assertThat(role.getUserAssignedRoleId()).isEqualTo(assignedRoleId);
	}
}
