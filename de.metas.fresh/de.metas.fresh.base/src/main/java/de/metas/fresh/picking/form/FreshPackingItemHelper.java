package de.metas.fresh.picking.form;

import java.math.BigDecimal;
import java.util.Map;

import org.adempiere.util.Check;

import de.metas.adempiere.form.IPackingItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class used to manage {@link IFreshPackingItem} instances.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public final class FreshPackingItemHelper
{
	private FreshPackingItemHelper()
	{
		super();
	}

	public static IFreshPackingItem cast(final IPackingItem item)
	{
		if (item == null)
		{
			return null;
		}
		Check.assumeInstanceOf(item, IFreshPackingItem.class, "item");
		return (IFreshPackingItem)item;
	}

	public static IFreshPackingItem copy(final IPackingItem item)
	{
		if (item instanceof IFreshPackingItem)
		{
			final IFreshPackingItem itemCasted = (IFreshPackingItem)item;
			return itemCasted.copy();
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Item " + item + " does not implement " + IFreshPackingItem.class);
		}
	}

	public static IFreshPackingItem create(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys)
	{
		return new TransactionalFreshPackingItem(scheds2Qtys);
	}
}
