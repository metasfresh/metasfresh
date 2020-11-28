package org.adempiere.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrgIdTest
{
	@Test
	public void test_ofRepoIdOrNull()
	{
		assertThat(OrgId.ofRepoIdOrNull(-1)).isNull();
		assertThat(OrgId.ofRepoIdOrNull(0)).isSameAs(OrgId.ANY);
		assertThat(OrgId.ofRepoIdOrNull(1)).isEqualTo(OrgId.ofRepoId(1));
		assertThat(OrgId.ofRepoIdOrNull(2)).isEqualTo(OrgId.ofRepoId(2));
	}

	@Test
	public void test_ofRepoId()
	{
		assertThatThrownBy(() -> OrgId.ofRepoId(-1)).isNotNull();

		assertThat(OrgId.ofRepoId(0)).isSameAs(OrgId.ANY);
		assertThat(OrgId.ofRepoId(1)).isEqualTo(OrgId.ofRepoId(1));
		assertThat(OrgId.ofRepoId(2)).isEqualTo(OrgId.ofRepoId(2));
	}

	@Test
	public void testIsAny()
	{
		assertThat(OrgId.ANY.isAny()).isTrue();
		assertThat(OrgId.ofRepoId(1).isAny()).isFalse();
	}

}
