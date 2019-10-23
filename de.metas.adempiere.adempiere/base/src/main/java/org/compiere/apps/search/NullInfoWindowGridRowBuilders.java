package org.compiere.apps.search;

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


import java.util.Collections;
import java.util.Set;

/**
 * Null implementation of {@link IInfoWindowGridRowBuilders} which does nothing.
 * 
 * It is always empty and does not support adding {@link IGridTabRowBuilder}s.
 * 
 * @author tsa
 * 
 */
public final class NullInfoWindowGridRowBuilders implements IInfoWindowGridRowBuilders
{
	public static final NullInfoWindowGridRowBuilders instance = new NullInfoWindowGridRowBuilders();

	private NullInfoWindowGridRowBuilders()
	{
		super();
	}

	/**
	 * @return {@link NullGridTabRowBuilder} always
	 */
	@Override
	public IGridTabRowBuilder getGridTabRowBuilder(int recordId)
	{
		return NullGridTabRowBuilder.instance;
	}

	/**
	 * @return empty set
	 */
	@Override
	public Set<Integer> getRecordIds()
	{
		return Collections.emptySet();
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void addGridTabRowBuilder(int recordId, IGridTabRowBuilder builder)
	{
		throw new UnsupportedOperationException("Not supported");
	}
}
