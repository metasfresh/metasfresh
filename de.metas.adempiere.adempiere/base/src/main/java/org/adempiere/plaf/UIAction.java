package org.adempiere.plaf;

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


import java.beans.PropertyChangeListener;

/**
 * Immutable implementation of {@link javax.swing.Action}.
 * 
 * NOTE: this is a replacement of sun.swing.UIAction.
 * 
 * @author tsa
 *
 */
public abstract class UIAction implements javax.swing.Action
{
	private final String name;

	public UIAction(final String name)
	{
		super();
		this.name = name;
	}
	
	public final String getName()
	{
		return name;
	}

	@Override
	public final Object getValue(String key)
	{
		if (NAME.equals(key))
		{
			return name;
		}
		return null;
	}

	@Override
	public final void putValue(String key, Object value)
	{
		// immutable, nothing to do
	}

	@Override
	public final void setEnabled(boolean enabled)
	{
		// immutable, nothing to do
	}

	@Override
	public final boolean isEnabled()
	{
		return true;
	}

	@Override
	public final void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// immutable, nothing to do
	}

	@Override
	public final void removePropertyChangeListener(PropertyChangeListener listener)
	{
		// immutable, nothing to do
	}

}
