package org.adempiere.ad.security.permissions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.adempiere.ad.security.permissions.PermissionAsserts.assertAccess;

import java.util.Arrays;

import org.adempiere.ad.security.permissions.ElementPermissions.Builder;
import org.adempiere.exceptions.AdempiereException;
import org.junit.Test;

public class ElementPermissionsTest
{
	@Test
	public void test_addPermissions_and_check_hasAccess()
	{
		final Builder builder = ElementPermissions.builder()
				.setElementTableName("AD_Window");

		for (int windowId = 1; windowId <= 1000; windowId++)
		{
			final ElementResource resource = ElementResource.of("AD_Window", windowId);
			final boolean readWrite = windowId % 2 == 0;
			builder.addPermission(ElementPermission.of(resource, readWrite));
		}

		final ElementPermissions permissions0 = builder.build();

		//
		// Test access
		for (final ElementPermissions permissions : Arrays.asList(
				permissions0
				// Rebuild the permissions.
				// They shall NOT change, so we will do the same assertions again.
				, permissions0.asNewBuilder().build()
				))
		{
			for (int windowId = 1; windowId <= 100; windowId++)
			{
				final ElementResource resource = ElementResource.of("AD_Window", windowId);
				final boolean expectWriteAccess = windowId % 2 == 0;
				final boolean expectReadAccess = true; // always
				assertAccess(permissions, resource, Access.READ, expectReadAccess);
				assertAccess(permissions, resource, Access.WRITE, expectWriteAccess);
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_DifferentResourceElementTableName()
	{
		final Builder builder = ElementPermissions.builder()
				.setElementTableName("AD_Window");

		// shall fail because the resource shall have the same tablename as the permissions.
		builder.addPermission(ElementPermission.of(ElementResource.of("AD_Window_Different", 127), false));
	}

	@Test(expected = AdempiereException.class)
	public void test_addPermissions_AlreadyExistingResource()
	{
		final Builder builder = ElementPermissions.builder()
				.setElementTableName("AD_Window");

		final boolean readWrite = false;
		builder.addPermission(ElementPermission.of(ElementResource.of("AD_Window", 1), readWrite));

		// adding permission for an already existing resource shall fail
		builder.addPermission(ElementPermission.of(ElementResource.of("AD_Window", 1), !readWrite));
	}

}
