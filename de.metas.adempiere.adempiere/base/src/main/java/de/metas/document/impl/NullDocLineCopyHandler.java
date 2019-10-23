package de.metas.document.impl;

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


import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocLineCopyHandler;

/**
 * A copy handler that does nothing. If registered as handler, it is never invoked. Use {@link ICopyHandlerBL#getNullDocLineCopyHandler()} to get an instance.
 * 
 * @author ts
 *
 * @param <LT> ignored
 */
public final class NullDocLineCopyHandler<LT> implements IDocLineCopyHandler<LT>
{
	/* package */final static NullDocLineCopyHandler<?> instance = new NullDocLineCopyHandler<>();

	private NullDocLineCopyHandler()
	{
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void copyPreliminaryValues(LT from, LT to)
	{
		// does nothing
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void copyValues(LT from, LT to)
	{
		// does nothing
	}

	/**
	 * Throws an {@link UnsupportedOperationException}.
	 */
	@Override
	public Class<LT> getSupportedItemsClass()
	{
		throw new UnsupportedOperationException();
	}
}
