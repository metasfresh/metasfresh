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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import mockit.Mocked;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	 * Make sure that an empty composite security rule, method {@link CompositeSecurityRule#hasFormAccess(org.compiere.model.I_AD_Role, Boolean, int)} is not influencing the result
	 * 
	 * @task http://dewiki908/mediawiki/index.php/04983_Permissions_are_ignored
	 */
	@Test
	public void hasFormAccess_EmptyRuleShallNotInfuenceTheResult()
	{
		final CompositeSecurityRule rule = new CompositeSecurityRule();
		final int adFormId = 12345;

		Assert.assertEquals("Invalid returned access for given access", Boolean.FALSE, rule.hasFormAccess(role, Boolean.FALSE, adFormId));
		Assert.assertEquals("Invalid returned access for given access", Boolean.TRUE, rule.hasFormAccess(role, Boolean.TRUE, adFormId));
		Assert.assertEquals("Invalid returned access for given access", null, rule.hasFormAccess(role, null, adFormId));
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
		final boolean rw = false; // does not matter

		{
			final Set<Integer> expected = asHashSet();
			final Set<Integer> actual = asHashSet();
			rule.filterOrgs(role, tableName, rw, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
		{
			final Set<Integer> expected = asHashSet(1);
			final Set<Integer> actual = asHashSet(1);
			rule.filterOrgs(role, tableName, rw, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
		{
			final Set<Integer> expected = asHashSet(1, 2, 3);
			final Set<Integer> actual = asHashSet(1, 2, 3);
			rule.filterOrgs(role, tableName, rw, actual);
			Assert.assertEquals("Org IDs shall not be modified", expected, actual);
		}
	}

	private static final Set<Integer> asHashSet(Integer... ids)
	{
		final HashSet<Integer> set = new HashSet<Integer>();
		if (ids == null || ids.length == 0)
		{
			return set;
		}
		for (Integer id : ids)
		{
			set.add(id);
		}
		return set;
	}
}
