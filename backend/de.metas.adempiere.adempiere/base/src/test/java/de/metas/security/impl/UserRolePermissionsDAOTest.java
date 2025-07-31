package de.metas.security.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.common.util.time.SystemTime;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.model.interceptor.SecurityMainInterceptor;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRolePermissionsDAOTest
{
	private UserRolePermissionsDAO dao;

	private SecurityMainInterceptor securityMainInterceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new EventLogService(new EventLogsRepository()));
		securityMainInterceptor = new SecurityMainInterceptor();
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(securityMainInterceptor);

		dao = (UserRolePermissionsDAO)Services.get(IUserRolePermissionsDAO.class);
	}

	@Test
	public void test_retrieveUserRolePermissions_NotExistingRole()
	{
		Assertions.assertThrows(RolePermissionsNotFoundException.class, () -> dao.getUserRolePermissions(
				RoleId.ofRepoId(1),
				UserId.ofRepoId(2),
				ClientId.ofRepoId(3),
				SystemTime.asLocalDate()));
	}
}
