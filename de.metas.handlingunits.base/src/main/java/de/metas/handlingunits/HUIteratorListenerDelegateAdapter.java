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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.util.Check;

/**
 * {@link IHUIteratorListener} adapter, can be used if there is an existing listener implementation <b>"A"</b> and you want to use <b>most</b> most of <b>"A"</b>'s method implementations. In that case
 * you can create a subclass of <code>this</code> class, call its constructor with <b>"A"</b> as parameter and only override the methods you need a different implementation for.
 *
 * @author tsa
 *
 */
public class HUIteratorListenerDelegateAdapter implements IHUIteratorListener
{
	private final IHUIteratorListener delegate;

	public HUIteratorListenerDelegateAdapter(final IHUIteratorListener delegate)
	{
		super();

		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[" + delegate + "]";
	}

	@Override
	public void setHUIterator(final IHUIterator iterator)
	{
		delegate.setHUIterator(iterator);
	}

	protected IHUIterator getHUIterator()
	{
		if (delegate instanceof HUIteratorListenerAdapter)
		{
			return ((HUIteratorListenerAdapter)delegate).getHUIterator();
		}
		else
		{
			throw new AdempiereException("Cannot get HUIterator from delegate: " + delegate);
		}
	}

	@Override
	public Result beforeHU(final IMutable<I_M_HU> hu)
	{
		return delegate.beforeHU(hu);
	}

	@Override
	public Result afterHU(final I_M_HU hu)
	{
		return delegate.afterHU(hu);
	}

	@Override
	public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
	{
		return delegate.beforeHUItem(item);
	}

	@Override
	public Result afterHUItem(final I_M_HU_Item item)
	{
		return delegate.afterHUItem(item);
	}

	@Override
	public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
	{
		return delegate.beforeHUItemStorage(itemStorage);
	}

	@Override
	public Result afterHUItemStorage(final IHUItemStorage itemStorage)
	{
		return delegate.afterHUItemStorage(itemStorage);
	}
}
