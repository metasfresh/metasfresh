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


import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;

public class ConstantHUKeyNameBuilder implements IHUKeyNameBuilder
{
	private final String name;

	public ConstantHUKeyNameBuilder(final String name)
	{
		super();
		this.name = name;
	}

	@Override
	public String getLastBuiltName()
	{
		return name;
	}

	@Override
	public String build()
	{
		return name;
	}

	@Override
	public void reset()
	{
		// nothing
	}

	@Override
	public boolean isStale()
	{
		// never gets stale
		return false;
	}

	@Override
	public void notifyAttributeValueChanged(final IAttributeValue attributeValue)
	{
		// nothing
	}

	@Override
	public void notifyChildChanged(final IHUKey childKey)
	{
		// nothing
	}
}
