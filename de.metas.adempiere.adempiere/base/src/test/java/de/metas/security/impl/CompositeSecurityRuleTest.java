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

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import mockit.Mocked;

public class CompositeSecurityRuleTest
{
	@Mocked
	IUserRolePermissions role;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Make sure that an empty composite security rule, method {@link CompositeSecurityRule#filterOrgs(Properties, org.compiere.model.I_AD_Role, String, boolean, Set)} is not influencing the result
	 * 
	 * @task http://dewiki908/mediawiki/index.php/04983_Permissions_are_ignored
	 */
	@Test
	public void filterOrgs_EmptyRuleShallNotInfuenceTheResult()
	{
		final CompositeSecurityRule rule = new CompositeSecurityRule();
		final String tableName = "DummyTableName"; // does not matter
		final Access access = Access.READ; // does not matter

		{
			final Set<OrgId> expected = asOrgIdsSet();
			final Set<OrgId> actual = asOrgIdsSet();
			rule.filterOrgs(role, tableName, access, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
		{
			final Set<OrgId> expected = asOrgIdsSet(1);
			final Set<OrgId> actual = asOrgIdsSet(1);
			rule.filterOrgs(role, tableName, access, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
		{
			final Set<OrgId> expected = asOrgIdsSet(1, 2, 3);
			final Set<OrgId> actual = asOrgIdsSet(1, 2, 3);
			rule.filterOrgs(role, tableName, access, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
	}

	private static final Set<OrgId> asOrgIdsSet(Integer... ids)
	{
		final HashSet<OrgId> set = new HashSet<>();
		if (ids == null || ids.length == 0)
		{
			return set;
		}
		for (Integer id : ids)
		{
			set.add(OrgId.ofRepoId(id));
		}
		return set;
	}
}
