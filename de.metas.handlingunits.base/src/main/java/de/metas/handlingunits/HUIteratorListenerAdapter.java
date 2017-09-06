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
import org.adempiere.util.Check;
import org.adempiere.util.lang.IMutable;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

/**
 * {@link IHUIteratorListener} adapter. To use it, call {@link #setDefaultResult(de.metas.handlingunits.IHUIteratorListener.Result)} to set a default result such as {@link Result#CONTINUE} and only
 * override the methods you need to do anything in particular.
 *
 * @author tsa
 *
 */
public abstract class HUIteratorListenerAdapter implements IHUIteratorListener
{
	private Result _defaultResult = Result.CONTINUE;
	private IHUIterator iterator = null;

	// we don't allow direct instantiation
	protected HUIteratorListenerAdapter()
	{
		init();
	}

	/**
	 * Method called when this object is constructed
	 *
	 * To be extended by implementors
	 */
	protected void init()
	{
		// nothing
	}

	public void setDefaultResult(final Result defaultResult)
	{
		Check.assumeNotNull(defaultResult, "defaultResult not null");
		_defaultResult = defaultResult;
	}

	/**
	 * Gets default result to be returned by all methods which were not implemented by extending class.
	 *
	 * @return default result
	 * @see #setDefaultResult(de.metas.handlingunits.IHUIteratorListener.Result)
	 */
	protected Result getDefaultResult()
	{
		return _defaultResult;
	}

	@Override
	public void setHUIterator(final IHUIterator iterator)
	{
		if (this.iterator != null && iterator != null && this.iterator != iterator)
		{
			throw new AdempiereException("Changing the iterator from " + this.iterator + " to " + iterator + " is not allowed for " + this + "."
					+ " You need to explicitelly set it to null first and then set it again.");
		}
		this.iterator = iterator;
	}

	public final IHUIterator getHUIterator()
	{
		return iterator;
	}

	@Override
	public Result beforeHU(final IMutable<I_M_HU> hu)
	{
		return getDefaultResult();
	}

	@Override
	public Result afterHU(final I_M_HU hu)
	{
		return getDefaultResult();
	}

	@Override
	public Result beforeHUItem(final IMutable<I_M_HU_Item> item)
	{
		return getDefaultResult();
	}

	@Override
	public Result afterHUItem(final I_M_HU_Item item)
	{
		return getDefaultResult();
	}

	@Override
	public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
	{
		return getDefaultResult();
	}

	@Override
	public Result afterHUItemStorage(final IHUItemStorage itemStorage)
	{
		return getDefaultResult();
	}
}
