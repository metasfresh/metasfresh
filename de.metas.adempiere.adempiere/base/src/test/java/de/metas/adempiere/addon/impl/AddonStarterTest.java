package de.metas.adempiere.addon.impl;

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

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import de.metas.adempiere.addon.IAddOn;

public class AddonStarterTest
{

	@Test
	public void startAddon()
	{

		final Properties props = new Properties();
		props.put("SomeAddon", TestAddon.class.getName());

		final AddonStarter starter = new AddonStarter(props);

		starter.startAddons();

		assertEquals(TestAddon.invokationCount, 1);
	}

	public static class TestAddon implements IAddOn
	{
		private static int invokationCount = 0;

		@Override
		public void beforeConnection()
		{
			invokationCount++;
		}
	}

}
