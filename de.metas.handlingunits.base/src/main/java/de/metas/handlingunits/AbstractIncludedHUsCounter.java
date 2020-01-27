package de.metas.handlingunits;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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


/**
 * Counts included HUs (abstract class).
 *
 * Extending classes shall implement the abstract methods from here, which are some basic operators on <code>HUHolderType</code>.
 *
 * @param <HUHolderType> class which contains the HU
 */
public abstract class AbstractIncludedHUsCounter<HUHolderType>
{
	private final HUHolderType _rootHUObj;
	private final boolean _countVHUs;

	private int _counter = 0;

	protected AbstractIncludedHUsCounter(final HUHolderType rootHUObj, final boolean countVHUs)
	{
		this._rootHUObj = rootHUObj;
		this._countVHUs = countVHUs;
	}

	public final IHUIteratorListener.Result accept(final HUHolderType currentHUObj)
	{
		// In case our holder does not contain a proper HU, we shall continue searching
		if (currentHUObj == null)
		{
			return IHUIteratorListener.Result.CONTINUE;
		}

		if (isRootHUKey(currentHUObj))
		{
			if (!isAggregatedHU(currentHUObj))
			{
				// don't count current HU if it's not an aggregate HU
				return IHUIteratorListener.Result.CONTINUE;
			}

			incrementCounter(getAggregatedHUsCount(currentHUObj));
		}
		else
		{
			if (isAggregatedHU(currentHUObj))
			{
				incrementCounter(getAggregatedHUsCount(currentHUObj));
			}
			else
			{
				if (!isCountVHUs() && isVirtualPI(currentHUObj))
				{
					// skip virtual HUs; note that also aggregate HUs are "virtual", but that case is handled not here
					return IHUIteratorListener.Result.CONTINUE;
				}
				incrementCounter(1);
			}
		}

		// we are counting only first level => so skip downstream
		return IHUIteratorListener.Result.SKIP_DOWNSTREAM;
	}

	public final int getHUsCount()
	{
		return _counter;
	}

	protected final boolean isCountVHUs()
	{
		return _countVHUs;
	}

	private final void incrementCounter(final int increment)
	{
		Check.assume(increment >= 0, "increment >= 0 but it was {}", increment);
		_counter += increment;
	}

	protected final boolean isRootHUKey(final HUHolderType huObj)
	{
		return huObj == _rootHUObj;
	}

	/** @return true if the HU is an aggregated HU */
	protected abstract boolean isAggregatedHU(final HUHolderType huObj);

	/**
	 * Called in case the HU is an aggregated HU and it shall return how many HUs are really contained.
	 *
	 * @return how many HUs are aggregated
	 */
	protected abstract int getAggregatedHUsCount(final HUHolderType huObj);

	/** @return true if the HU is a virtual one */
	protected abstract boolean isVirtualPI(final HUHolderType huObj);
}