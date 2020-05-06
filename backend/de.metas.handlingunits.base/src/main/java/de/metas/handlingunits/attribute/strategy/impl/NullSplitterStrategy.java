package de.metas.handlingunits.attribute.strategy.impl;

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


import de.metas.handlingunits.attribute.strategy.IAttributeSplitRequest;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitResult;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;

/**
 * Null Splitter Strategy
 *
 * @author tsa
 *
 */
public final class NullSplitterStrategy implements IAttributeSplitterStrategy
{
	public static final transient NullSplitterStrategy instance = new NullSplitterStrategy();

	private NullSplitterStrategy()
	{
		super();
	}

	@Override
	public IAttributeSplitResult split(final IAttributeSplitRequest request)
	{
		// split value: always null because we don't split
		final Object splitValue = null;
		// remaining value: always initial value
		final Object remainingValue = request.getValueToSplit();

		return new AttributeSplitResult(splitValue, remainingValue);
	}

	@Override
	public Object recalculateRemainingValue(final IAttributeSplitResult result, final Object valueSet)
	{
		// Does not matter, it won't affect the result on other children
		return result.getRemainingValue();
	}
}
