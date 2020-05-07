package org.adempiere.ad.security.impl;

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

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.model.validator.SecurityMainInterceptor;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.event.log.EventBus2EventLogHandler;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogUserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		EventBus2EventLogHandler.class, EventLogService.class, EventLogUserService.class
})
public class UserRolePermissionsDAOTest
{
	private UserRolePermissionsDAO dao;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(SecurityMainInterceptor.instance);

		dao = (UserRolePermissionsDAO)Services.get(IUserRolePermissionsDAO.class);
	}

	@Test(expected = RolePermissionsNotFoundException.class)
	public void test_retrieveUserRolePermissions_NotExistingRole()
	{
		dao.retrieveUserRolePermissions(1, 2, 3, SystemTime.asDayTimestamp());
	}
}
