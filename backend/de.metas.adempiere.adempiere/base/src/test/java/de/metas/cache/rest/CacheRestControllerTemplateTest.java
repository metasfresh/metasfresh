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

package de.metas.cache.rest;

import de.metas.security.IUserRolePermissionsDAO;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class CacheRestControllerTemplateTest
{
	private TestableCacheRestController controller;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// reset() invalidates the user/role permissions cache; register a mock so the call is a no-op
		Services.registerService(IUserRolePermissionsDAO.class, Mockito.mock(IUserRolePermissionsDAO.class));

		controller = new TestableCacheRestController();
	}

	@Test
	void gcParamAbsent_gcNotInvoked_andNotLogged()
	{
		final JsonCacheResetResponse response = controller.reset(new JsonCacheResetRequest());

		assertThat(controller.gcInvocations).isZero();
		assertThat(response.toString()).doesNotContain("garbage collected");
	}

	@Test
	void gcParamTrue_gcInvokedOnce_andLogged()
	{
		final JsonCacheResetResponse response = controller.reset(new JsonCacheResetRequest().setValue("gc", true));

		assertThat(controller.gcInvocations).isEqualTo(1);
		assertThat(response.toString()).contains("garbage collected");
	}

	private static class TestableCacheRestController extends CacheRestControllerTemplate
	{
		int gcInvocations = 0;

		@Override
		protected void assertAuth()
		{
			// no-op for the unit test
		}

		@Override
		protected void invokeGc()
		{
			gcInvocations++;
		}
	}
}
