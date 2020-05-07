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


import java.util.Collection;
import java.util.UUID;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;

/* package */class CompositeHUKey extends AbstractHUKey
{
	private final String id;
	private final KeyNamePair value;
	private final Object aggregationKey;

	public CompositeHUKey(final IHUKeyFactory keyFactory, final Object aggregationKey)
	{
		super(keyFactory);

		Check.assumeNotNull(aggregationKey, "aggregationKey not null");
		this.aggregationKey = aggregationKey;

		id = getClass().getName() + "-" + aggregationKey.toString() + "-" + UUID.randomUUID(); // TODO this ensures key uniqueness, HOWEVER it might interfere with algorithms

		value = new KeyNamePair(0, aggregationKey.toString());
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		return new CompositeHUKeyNameBuilder(this);
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
		return aggregationKey;
	}

	@Override
	protected void assertCanAddChild(final IHUKey child)
	{
		// NOTE: at the moment, only HUKeys are accepted in aggregation
		Check.assumeInstanceOf(child, HUKey.class, "child");

		//
		// Make sure "child" has the same aggregationKey as this composite
		// else it's a development error
		final Object childAggregationKey = child.getAggregationKey();
		if (!getAggregationKey().equals(childAggregationKey))
		{
			throw new IllegalArgumentException("Child " + child + " is not accepted for " + this);
		}
	}

	@Override
	public boolean isGrouping()
	{
		return true;
	}

	/**
	 * Returns always {@code false}.
	 */
	@Override
	public boolean isVirtualPI()
	{
		return false;
	}

	@Override
	public boolean isReadonly()
	{
		// Inherit readonly attribute from it's parent
		final IHUKey parent = getParent();
		if (parent == null)
		{
			return false;
		}
		return parent.isReadonly();
	}

	/**
	 * Does nothing, just returns the given <code>children</code> unchanged.
	 */
	@Override
	protected Collection<IHUKey> preprocessChildrenBeforeAdding(final Collection<IHUKey> children)
	{
		return children;
	}

	@Override
	public String toString()
	{
		return "CompositeHUKey [id=" + id + ", value=" + value + ", aggregationKey=" + aggregationKey + "]";
	}

	@Override
	public boolean isDestroyed()
	{
		// TODO: i think in future we can consider this destroyed when there are no childrens
		return false;
	}
}
