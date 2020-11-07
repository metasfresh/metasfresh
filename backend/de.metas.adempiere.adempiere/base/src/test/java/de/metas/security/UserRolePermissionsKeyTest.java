package de.metas.security;

import static org.assertj.core.api.Assertions.assertThat;

import de.metas.common.util.time.SystemTime;
import org.adempiere.service.ClientId;
import org.junit.Test;

import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class UserRolePermissionsKeyTest
{
	@Test
	public void test()
	{
		testToFromString(UserRolePermissionsKey.of(
				RoleId.ofRepoId(1),
				UserId.ofRepoId(2),
				ClientId.ofRepoId(3),
				SystemTime.asLocalDate()));
	}

	public void testToFromString(final UserRolePermissionsKey key)
	{
		final String str = key.toPermissionsKeyString();
		final UserRolePermissionsKey keyDeserialized = UserRolePermissionsKey.fromString(str);
		assertThat(keyDeserialized).isEqualTo(key);
	}
}
