package de.metas.handlingunits.attribute.impl;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.util.Check;

public class CompositeAttributeValueListener implements IAttributeValueListener
{
	private final List<IAttributeValueListener> listeners = new ArrayList<IAttributeValueListener>();

	public void addAttributeValueListener(final IAttributeValueListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		if (listeners.contains(listener))
		{
			return;
		}

		listeners.add(listener);
	}

	public void removeAttributeValueListener(final IAttributeValueListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void onValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeValue attributeValue, final Object valueOld, final Object valueNew)
	{
		for (final IAttributeValueListener listener : listeners)
		{
			listener.onValueChanged(attributeValueContext, attributeValue, valueOld, valueNew);
		}
	}

	@Override
	public String toString()
	{
		return "CompositeAttributeValueListener [listeners=" + listeners + "]";
	}
}
