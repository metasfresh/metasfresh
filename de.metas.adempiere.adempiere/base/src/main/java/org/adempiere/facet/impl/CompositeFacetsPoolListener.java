package org.adempiere.facet.impl;

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


import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.concurrent.ThreadSafe;

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetsPoolListener;

import de.metas.util.Check;

@ThreadSafe
public class CompositeFacetsPoolListener implements IFacetsPoolListener
{
	private final CopyOnWriteArrayList<IFacetsPoolListener> listeners = new CopyOnWriteArrayList<>();

	public void addListener(final IFacetsPoolListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		listeners.addIfAbsent(listener);
	}

	public void removeListener(final IFacetsPoolListener listener)
	{
		listeners.remove(listener);
	}
	
	@Override
	public void onFacetsInit()
	{
		for (final IFacetsPoolListener listener : listeners)
		{
			listener.onFacetsInit();
		}
	}


	@Override
	public void onFacetExecute(final IFacet<?> facet)
	{
		for (final IFacetsPoolListener listener : listeners)
		{
			listener.onFacetExecute(facet);
		}
	}
}
