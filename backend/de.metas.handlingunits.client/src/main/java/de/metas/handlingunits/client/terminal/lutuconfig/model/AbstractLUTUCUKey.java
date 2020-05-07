package de.metas.handlingunits.client.terminal.lutuconfig.model;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

public abstract class AbstractLUTUCUKey extends TerminalKey implements ILUTUCUKey
{
	private String name = null;
	private final List<ILUTUCUKey> children = new ArrayList<ILUTUCUKey>();
	private final List<ILUTUCUKey> childrenRO = Collections.unmodifiableList(children);
	private BigDecimal suggestedQty = null;

	public AbstractLUTUCUKey(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	protected abstract String createName();

	@Override
	public final Object getName()
	{
		if (name == null)
		{
			name = createName();
			Check.assumeNotNull(name, "name not null");
		}
		return name;
	}

	@Override
	public final String getTableName()
	{
		throw new UnsupportedOperationException();
	}

	protected final void markStaled()
	{
		name = null;
	}

	@Override
	public final void setChildren(final ILUTUCUKey child)
	{
		setChildren(Collections.singleton(child));
	}

	@Override
	public final void setChildren(final Collection<? extends ILUTUCUKey> children)
	{
		this.children.clear();

		if (children == null || children.isEmpty())
		{
			return;
		}

		this.children.addAll(children);
	}

	@Override
	public final void mergeChildren(final Collection<? extends ILUTUCUKey> childrenToAdd)
	{
		// Particular case: there are no current children
		if (children.isEmpty())
		{
			setChildren(childrenToAdd);
			return;
		}

		// If there is nothing to add, stop here
		if (Check.isEmpty(childrenToAdd))
		{
			return;
		}

		//
		// Create an ID to Key map of existing children
		final Map<String, ILUTUCUKey> existingChildrenMap = new LinkedHashMap<>();
		for (final ILUTUCUKey child : children)
		{
			existingChildrenMap.put(child.getId(), child);
		}

		//
		// Iterate children to add and add/merge them to existing ones
		for (final ILUTUCUKey childToAdd : childrenToAdd)
		{
			final String id = childToAdd.getId();
			final ILUTUCUKey existingChild = existingChildrenMap.get(id);

			// Case: child already added and it's exacly the child we want to add
			// => do nothing
			if (existingChild == childToAdd)
			{
				continue;
			}
			// Case: child already added, but it's not the same instance
			// => merge it's children
			else if (existingChild != null)
			{
				existingChild.mergeChildren(childToAdd.getChildren());
			}
			// Case: child not already existing
			// => add it
			else
			{
				existingChildrenMap.put(id, childToAdd);
			}
		}

		//
		// Set the new children
		setChildren(existingChildrenMap.values());
	}

	@Override
	public final List<ILUTUCUKey> getChildren()
	{
		return childrenRO;
	}

	@Override
	public BigDecimal getSuggestedQty()
	{
		return suggestedQty;
	}

	@Override
	public void setSuggestedQty(final BigDecimal suggestedQty)
	{
		this.suggestedQty = suggestedQty;
	}

}
