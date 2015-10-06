package de.metas.handlingunits;

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


import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

/**
 * Null {@link IHUIteratorListener} which does nothing
 *
 * @author tsa
 *
 */
public final class NullHUIteratorListener implements IHUIteratorListener
{
	public static final NullHUIteratorListener instance = new NullHUIteratorListener();

	private final Result defaultResult = Result.CONTINUE;

	private NullHUIteratorListener()
	{
		super();
	}

	@Override
	public void setHUIterator(final IHUIterator iterator)
	{
		// do nothing; this is an immutable class
	}

	@Override
	public Result beforeHU(final IMutable<I_M_HU> hu)
	{
		return defaultResult;
	}

	@Override
	public Result afterHU(final I_M_HU hu)
	{
		return defaultResult;
	}

	@Override
	public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
	{
		return defaultResult;
	}

	@Override
	public Result afterHUItem(final I_M_HU_Item item)
	{
		return defaultResult;
	}

	@Override
	public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
	{
		return defaultResult;
	}

	@Override
	public Result afterHUItemStorage(final IHUItemStorage itemStorage)
	{
		return defaultResult;
	}
}
