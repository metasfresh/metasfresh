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
 * Split strategy which is copying the attribute to children
 *
 * @author tsa
 * @see http://dewiki908/mediawiki/index.php/06008_Aggregation_Strategy_Top-Down_for_Wareneingang_%28and_others%29_%28103116846664%29#Kurzbeschreibung
 */
public class CopyAttributeSplitterStrategy implements IAttributeSplitterStrategy
{
	@Override
	public IAttributeSplitResult split(final IAttributeSplitRequest request)
	{
		// split value: initial value (because we are copying)
		final Object splitValue = request.getValueToSplit();

		// remaining value: always initial value because the same value shall be available for next children
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
