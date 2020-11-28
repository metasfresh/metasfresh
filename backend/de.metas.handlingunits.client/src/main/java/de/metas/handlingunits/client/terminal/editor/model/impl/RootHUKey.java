package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.compiere.util.KeyNamePair;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;

/**
 * Does not represent a particular HU, but the root under which particular HUs can be displayed.
 * Those HUs' {@link IHUKey}s are "manually" added to this instance by the code that had the factory create this instance.
 * <p>
 * Generally instances of this class are created by {@link IHUKeyFactory#createRootKey()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */class RootHUKey extends AbstractHUKey
{
	private final String id;
	private final KeyNamePair value;

	public RootHUKey(final IHUKeyFactory keyFactory)
	{
		super(keyFactory);

		id = getClass().getName() + "-";

		value = new KeyNamePair(0, "");
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		final String name = "Home"; // TODO ts: HARDCODED ?!?
		return new ConstantHUKeyNameBuilder(name);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public Object getAggregationKey()
	{
		return id;
	}

	/**
	 * @return always {@code false}.
	 */
	@Override
	public boolean isVirtualPI()
	{
		return false;
	}

	@Override
	public boolean isGrouping()
	{
		return false;
	}

	@Override
	public IAttributeStorage getAttributeSet()
	{
		return NullAttributeStorage.instance;
	}

	/**
	 * Returns list with the given children that is sorted according to {@link HUKeyComparator}.
	 */
	@Override
	public Collection<IHUKey> preprocessChildrenBeforeAdding(final Collection<IHUKey> children)
	{
		//
		// 07162: Order root HU Children by (Qty on HU <b>DESC</b>, HU Value <b>ASC</b>)
		final List<IHUKey> childrenSorted = new ArrayList<IHUKey>(children);
		Collections.sort(childrenSorted, new HUKeyComparator(getKeyFactory()));

		return childrenSorted;
	}

	@Override
	public void refresh()
	{
		// do nothing because there is NOTHING to refresh on this level
	}

	/**
	 * @return false, Root key is never destroyed
	 */
	@Override
	public boolean isDestroyed()
	{
		return false;
	}
}
