package de.metas.security.permissions;

import static de.metas.security.permissions.PermissionAsserts.assertAccess;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Test;

import de.metas.security.permissions.ElementPermissions.Builder;

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
			builder.addPermission(ElementPermission.ofReadWriteFlag(resource, readWrite));
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
		builder.addPermission(ElementPermission.ofReadWriteFlag(ElementResource.of("AD_Window_Different", 127), false));
	}

	@Test(expected = AdempiereException.class)
	public void test_addPermissions_AlreadyExistingResource()
	{
		final Builder builder = ElementPermissions.builder()
				.setElementTableName("AD_Window");

		final boolean readWrite = false;
		builder.addPermission(ElementPermission.ofReadWriteFlag(ElementResource.of("AD_Window", 1), readWrite));

		// adding permission for an already existing resource shall fail
		builder.addPermission(ElementPermission.ofReadWriteFlag(ElementResource.of("AD_Window", 1), !readWrite));
	}

}
