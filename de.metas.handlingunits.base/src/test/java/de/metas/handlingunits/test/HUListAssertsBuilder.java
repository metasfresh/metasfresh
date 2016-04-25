package de.metas.handlingunits.test;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;

import org.junit.Assert;

import de.metas.handlingunits.model.I_M_HU;

public class HUListAssertsBuilder
{
	private final String name;
	private final String assertPrefix;
	private final List<I_M_HU> list;

	private final Object parent;

	public HUListAssertsBuilder(final Object parent, final String name, final List<I_M_HU> list)
	{
		this.list = list;
		if (name == null || name.isEmpty())
		{
			this.name = "";
			assertPrefix = "";
		}
		else
		{
			this.name = name.trim();
			assertPrefix = this.name + ": ";
		}

		this.parent = parent;
	}

	public HUListAssertsBuilder assumeCount(final int count)
	{
		Assert.assertNotNull(assertPrefix + "Not null list of HUs expected", list);
		Assert.assertEquals(assertPrefix + "Invalid count", count, list.size());
		return this;
	}

	public HUAssertsBuilder handlingUnitAt(final int index)
	{
		final I_M_HU hu = list.get(index);
		final String name = this.name + "/item=" + index;
		return new HUAssertsBuilder(this, name, hu);
	}

	public HUItemAssertsBuilder backToParentHandlingUnitItem()
	{
		return (HUItemAssertsBuilder)parent;
	}
}
