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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.compiere.model.X_AD_Role;
import org.compiere.model.X_AD_Table;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.security.TableAccessLevel;
import de.metas.util.Pair;

/**
 * Tests {@link TableAccessLevel} logic.
 * 
 * @author tsa
 *
 */
public class TableAccessLevelTest
{
	/**
	 * Pairs of {@link TableAccessLevel} which were checked.
	 */
	private LinkedHashSet<Pair<TableAccessLevel, TableAccessLevel>> checkedAccessLevelPairs;

	@Before
	public void init()
	{
		checkedAccessLevelPairs = new LinkedHashSet<>();
	}

	private void assertAllAccessLevelPairsWereChecked()
	{
		for (final TableAccessLevel a1 : TableAccessLevel.values())
		{
			for (final TableAccessLevel a2 : TableAccessLevel.values())
			{
				final Pair<TableAccessLevel, TableAccessLevel> pair = new Pair<>(a1, a2);
				final boolean checked = checkedAccessLevelPairs.contains(pair);

				if (!checked)
				{
					String message = "Pair should be checked: " + pair
							+ "\n Checked pairs were: " + checkedAccessLevelPairs
							+ "\n\n";
					Assert.fail(message);
				}
			}
		}
	}

	private final void markAccessLevelPairChecked(final TableAccessLevel a1, final TableAccessLevel a2)
	{
		final Pair<TableAccessLevel, TableAccessLevel> pair = new Pair<>(a1, a2);
		checkedAccessLevelPairs.add(pair);
	}

	@Test
	public void test_forAccessLevel()
	{
		test_forAccessLevel(TableAccessLevel.All, X_AD_Table.ACCESSLEVEL_All);
		test_forAccessLevel(TableAccessLevel.ClientOnly, X_AD_Table.ACCESSLEVEL_ClientOnly);
		test_forAccessLevel(TableAccessLevel.ClientPlusOrganization, X_AD_Table.ACCESSLEVEL_ClientPlusOrganization);
		test_forAccessLevel(TableAccessLevel.Organization, X_AD_Table.ACCESSLEVEL_Organization);
		test_forAccessLevel(TableAccessLevel.SystemOnly, X_AD_Table.ACCESSLEVEL_SystemOnly);
		test_forAccessLevel(TableAccessLevel.SystemPlusClient, X_AD_Table.ACCESSLEVEL_SystemPlusClient);
	}

	private void test_forAccessLevel(final TableAccessLevel expected, final String tableAccessLevelStr)
	{
		final TableAccessLevel actual = TableAccessLevel.forAccessLevel(tableAccessLevelStr);
		Assert.assertEquals("Invalid forAccessLevel(" + tableAccessLevelStr + ")", expected, actual);

		// now convert it back
		Assert.assertEquals("Invalid getAccessLevelString of " + actual,
				tableAccessLevelStr, // expected
				actual.getAccessLevelString() // actual
		);
	}

	@Test
	public void test_forUserLevel()
	{
		// test_forUserLevel(TableAccessLevel.All, X_AD_Role.USERLEVEL_);
		test_forUserLevel(TableAccessLevel.ClientOnly, X_AD_Role.USERLEVEL_Client);
		test_forUserLevel(TableAccessLevel.ClientPlusOrganization, X_AD_Role.USERLEVEL_ClientPlusOrganization);
		test_forUserLevel(TableAccessLevel.Organization, X_AD_Role.USERLEVEL_Organization);
		test_forUserLevel(TableAccessLevel.SystemOnly, X_AD_Role.USERLEVEL_System);
		// test_forUserLevel(TableAccessLevel.SystemPlusClient, X_AD_Role.USERLEVEL_);
	}

	private void test_forUserLevel(final TableAccessLevel expected, final String userLevelStr)
	{
		final TableAccessLevel actual = TableAccessLevel.forUserLevel(userLevelStr);
		Assert.assertEquals("Invalid forUserLevel(" + userLevelStr + ")", expected, actual);

		// now convert it back
		Assert.assertEquals("Invalid getAccessLevelString of " + actual,
				userLevelStr, // expected
				actual.getUserLevelString() // actual
		);
	}

	@Test
	public void test_canBeAccessedBy()
	{
		// AccessLevel can be accessed by itself.
		// Exception: None cannot be accessed by None
		for (final TableAccessLevel accessor : TableAccessLevel.values())
		{
			final boolean canBeAccessedExpected = accessor != TableAccessLevel.None;
			assertCanBeAccessBy(canBeAccessedExpected, accessor, accessor);
		}

		// AccessLevel "None": can NOT be accessed by any accessor
		for (final TableAccessLevel accessor : TableAccessLevel.values())
		{
			assertCanBeAccessBy(false, TableAccessLevel.None, accessor);
		}

		// AccessLevel "All": can be accessed by any accessor
		for (final TableAccessLevel accessor : TableAccessLevel.values())
		{
			assertCanBeAccessBy(true, TableAccessLevel.All, accessor);
		}

		// AccessLevel "System":
		assertCanBeAccessByAndExcludeTheOthers(TableAccessLevel.SystemOnly,
				// can be accessed by:
				TableAccessLevel.All, TableAccessLevel.SystemOnly, // All and self
				TableAccessLevel.SystemPlusClient);

		// AccessLevel "Client":
		assertCanBeAccessByAndExcludeTheOthers(TableAccessLevel.ClientOnly,
				// can be accessed by:
				TableAccessLevel.All, TableAccessLevel.ClientOnly, // All and self
				TableAccessLevel.ClientPlusOrganization, TableAccessLevel.SystemPlusClient);

		// AccessLevel "SystemPlusClient":
		assertCanBeAccessByAndExcludeTheOthers(TableAccessLevel.SystemPlusClient,
				// can be accessed by:
				TableAccessLevel.All, TableAccessLevel.SystemPlusClient, // All and self
				TableAccessLevel.SystemOnly, TableAccessLevel.ClientOnly, TableAccessLevel.ClientPlusOrganization);

		// AccessLevel "Organization":
		assertCanBeAccessByAndExcludeTheOthers(TableAccessLevel.Organization,
				// can be accessed by:
				TableAccessLevel.All, TableAccessLevel.Organization, // All and self
				TableAccessLevel.ClientPlusOrganization);

		// AccessLevel "ClientPlusOrganization":
		assertCanBeAccessByAndExcludeTheOthers(TableAccessLevel.ClientPlusOrganization,
				// can be accessed by:
				TableAccessLevel.All, TableAccessLevel.ClientPlusOrganization, // All and self
				TableAccessLevel.ClientOnly, TableAccessLevel.Organization, TableAccessLevel.SystemPlusClient);

		// Make sure we checked all combinations
		assertAllAccessLevelPairsWereChecked();
	}

	private final void assertCanBeAccessBy(final boolean expected, final TableAccessLevel accessLevel, final TableAccessLevel accessor)
	{
		final boolean actual = accessLevel.canBeAccessedBy(accessor);
		Assert.assertEquals("" + accessLevel + " can be accessed by " + accessor, expected, actual);

		markAccessLevelPairChecked(accessLevel, accessor);
	}

	private final void assertCanBeAccessByAndExcludeTheOthers(final TableAccessLevel accessLevel,
			final TableAccessLevel... accessorsGranted)
	{
		final List<TableAccessLevel> accessorsGrantedList = Arrays.asList(accessorsGranted);
		for (final TableAccessLevel a : TableAccessLevel.values())
		{
			final boolean accessGrantExpected = accessorsGrantedList.contains(a);
			assertCanBeAccessBy(accessGrantExpected, accessLevel, a);
		}
	}

	@Test
	public void test_IsSystemClientOrg()
	{
		// assertSystemClientOrgFlags(TableAccessLevel.None, false, false, false);
		assertSystemClientOrgFlags(TableAccessLevel.Organization, false, false, true);
		assertSystemClientOrgFlags(TableAccessLevel.ClientOnly, false, true, false);
		assertSystemClientOrgFlags(TableAccessLevel.ClientPlusOrganization, false, true, true);
		assertSystemClientOrgFlags(TableAccessLevel.SystemOnly, true, false, false);
		// assertSystemClientOrgFlags(TableAccessLevel.SystemPlusOrganization, true, false, true);
		assertSystemClientOrgFlags(TableAccessLevel.SystemPlusClient, true, true, false);
		assertSystemClientOrgFlags(TableAccessLevel.All, true, true, true);
	}

	private void assertSystemClientOrgFlags(final TableAccessLevel accessLevel, boolean system, boolean client, boolean org)
	{
		final boolean all = system && client && org;

		Assert.assertEquals("Expected System flag for " + accessLevel, system, accessLevel.isSystem());
		Assert.assertEquals("Expected Client flag for " + accessLevel, client, accessLevel.isClient());
		Assert.assertEquals("Expected Org flag for " + accessLevel, org, accessLevel.isOrganization());
		Assert.assertEquals("Expected All flag for " + accessLevel, all, accessLevel.isAll());
	}

	@Test
	public void testHasCommonLevels()
	{
		for (final TableAccessLevel a1 : TableAccessLevel.values())
		{
			for (final TableAccessLevel a2 : TableAccessLevel.values())
			{
				final boolean commonSystemSet = (a1.isSystem() && a2.isSystem());
				final boolean commonClientSet = (a1.isClient() && a2.isClient());
				final boolean commonOrgSet = (a1.isOrganization() && a2.isOrganization());
				final boolean commonLevelsExpected = commonSystemSet || commonClientSet || commonOrgSet;

				final String message = "Has common levels: " + a1 + ", " + a2
						+ "\n Common System: " + commonSystemSet
						+ "\n Common Client: " + commonClientSet
						+ "\n Common Org: " + commonOrgSet
						+ "\n\n";
				Assert.assertEquals(message, commonLevelsExpected, a1.hasCommonLevels(a2));

				markAccessLevelPairChecked(a1, a2);
			}
		}

		assertAllAccessLevelPairsWereChecked(); // shall not fail!
	}
}
