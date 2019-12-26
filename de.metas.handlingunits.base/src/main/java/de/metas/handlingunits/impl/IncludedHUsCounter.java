package de.metas.handlingunits.impl;

import org.adempiere.util.lang.IMutable;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.AbstractIncludedHUsCounter;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUIteratorListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.util.Services;

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
 * An {@link AbstractIncludedHUsCounter} implementation which works directly on I_M_HU instances
 */
public class IncludedHUsCounter extends AbstractIncludedHUsCounter<I_M_HU>
{
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	IncludedHUsCounter(final I_M_HU rootHU)
	{
		super(rootHU, false/*countVHUs*/);
	}

	@Override
	protected boolean isAggregatedHU(final I_M_HU hu)
	{
		return handlingUnitsBL.isAggregateHU(hu);
	}

	@Override
	protected int getAggregatedHUsCount(final I_M_HU hu)
	{
		final I_M_HU_Item parentHUItem = hu.getM_HU_Item_Parent();
		if (parentHUItem == null)
		{
			// shall not happen
			return 0;
		}
		return parentHUItem.getQty().intValueExact();
	}

	@Override
	protected boolean isVirtualPI(final I_M_HU huObj)
	{
		return handlingUnitsBL.isVirtual(huObj);
	}

	IHUIteratorListener toHUIteratorListener()
	{
		return new HUIteratorListenerAdapter()
		{
			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper(this).addValue(IncludedHUsCounter.this).toString();
			}

			@Override
			public Result beforeHU(final IMutable<I_M_HU> hu)
			{
				return IncludedHUsCounter.this.accept(hu.getValue());
			}
		};
	}
}