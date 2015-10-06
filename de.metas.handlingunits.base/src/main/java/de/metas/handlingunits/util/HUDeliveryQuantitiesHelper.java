package de.metas.handlingunits.util;

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


import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.handlingunits.model.IHUDeliveryQuantities;

/**
 * Helper class for manipulating {@link IHUDeliveryQuantities}
 *
 * @author tsa
 *
 */
public final class HUDeliveryQuantitiesHelper
{
	private HUDeliveryQuantitiesHelper()
	{
		super();
	}

	/**
	 * Adds quantities from <code>qtysToAdd</code> to <code>target</code> quantities.
	 *
	 * @param target
	 * @param qtysToAdd
	 */
	public static void addTo(final IHUDeliveryQuantities target, final IHUDeliveryQuantities qtysToAdd)
	{
		Check.assumeNotNull(target, "target not null");
		Check.assumeNotNull(qtysToAdd, "qtysToAdd not null");

		{
			final BigDecimal qtyOrderedLU = target.getQtyOrdered_LU();
			target.setQtyOrdered_LU(add(qtyOrderedLU, qtysToAdd.getQtyOrdered_LU()));
		}
		{
			final BigDecimal qtyOrderedTU = target.getQtyOrdered_TU();
			target.setQtyOrdered_TU(add(qtyOrderedTU, qtysToAdd.getQtyOrdered_TU()));
		}
		{
			final BigDecimal qtyToDeliverLU = target.getQtyToDeliver_LU();
			target.setQtyToDeliver_LU(add(qtyToDeliverLU, qtysToAdd.getQtyToDeliver_LU()));
		}
		{
			final BigDecimal qtyToDeliverTU = target.getQtyToDeliver_TU();
			target.setQtyToDeliver_TU(add(qtyToDeliverTU, qtysToAdd.getQtyToDeliver_TU()));
		}
		{
			final BigDecimal qtyDeliveredLU = target.getQtyDelivered_LU();
			target.setQtyDelivered_LU(add(qtyDeliveredLU, qtysToAdd.getQtyDelivered_LU()));
		}
		{
			final BigDecimal qtyDeliveredTU = target.getQtyDelivered_TU();
			target.setQtyDelivered_TU(add(qtyDeliveredTU, qtysToAdd.getQtyDelivered_TU()));
		}
	}

	/**
	 * Removes quantities <code>qtysToRemove</code> from <code>target</code> quantities.
	 *
	 * @param target
	 * @param qtysToAdd
	 */
	public static void removeFrom(final IHUDeliveryQuantities target, final IHUDeliveryQuantities qtysToRemove)
	{
		Check.assumeNotNull(target, "target not null");
		Check.assumeNotNull(qtysToRemove, "qtysToRemove not null");

		{
			final BigDecimal qtyOrderedLU = target.getQtyOrdered_LU();
			target.setQtyOrdered_LU(subtract(qtyOrderedLU, qtysToRemove.getQtyOrdered_LU()));
		}
		{
			final BigDecimal qtyOrderedTU = target.getQtyOrdered_TU();
			target.setQtyOrdered_TU(subtract(qtyOrderedTU, qtysToRemove.getQtyOrdered_TU()));
		}
		{
			final BigDecimal qtyToDeliverLU = target.getQtyToDeliver_LU();
			target.setQtyToDeliver_LU(subtract(qtyToDeliverLU, qtysToRemove.getQtyToDeliver_LU()));
		}
		{
			final BigDecimal qtyToDeliverTU = target.getQtyToDeliver_TU();
			target.setQtyToDeliver_TU(subtract(qtyToDeliverTU, qtysToRemove.getQtyToDeliver_TU()));
		}
		{
			final BigDecimal qtyDeliveredLU = target.getQtyDelivered_LU();
			target.setQtyDelivered_LU(subtract(qtyDeliveredLU, qtysToRemove.getQtyDelivered_LU()));
		}
		{
			final BigDecimal qtyDeliveredTU = target.getQtyDelivered_TU();
			target.setQtyDelivered_TU(subtract(qtyDeliveredTU, qtysToRemove.getQtyDelivered_TU()));
		}
	}

	/**
	 * Adjust the target by subtracting <code>qtysToRemove</code> and adding <code>qtysToAdd</code>
	 *
	 * @param target
	 * @param qtysToRemove qtys to remove or null
	 * @param qtysToAdd qtys to add or null
	 */
	public static void adjust(final IHUDeliveryQuantities target, final IHUDeliveryQuantities qtysToRemove, final IHUDeliveryQuantities qtysToAdd)
	{
		Check.assumeNotNull(target, "target not null");

		if (qtysToRemove != null)
		{
			removeFrom(target, qtysToRemove);
		}

		if (qtysToAdd != null)
		{
			addTo(target, qtysToAdd);
		}
	}

	/**
	 * Sets QtyToDeliver LU/TU as QtyOrdered - QtyDelivered.
	 *
	 * If the result is negative, QtyToDeliver will be set to ZERO.
	 *
	 * @param target
	 */
	public static void updateQtyToDeliver(final IHUDeliveryQuantities target)
	{
		Check.assumeNotNull(target, "target not null");

		final BigDecimal qtyOrderedLU = target.getQtyOrdered_LU();
		final BigDecimal qtyOrderedTU = target.getQtyOrdered_TU();
		final BigDecimal qtyDeliveredLU = target.getQtyDelivered_LU();
		final BigDecimal qtyDeliveredTU = target.getQtyDelivered_TU();

		BigDecimal qtyToDeliverLU = qtyOrderedLU.subtract(qtyDeliveredLU);
		if (qtyToDeliverLU.signum() < 0)
		{
			qtyToDeliverLU = BigDecimal.ZERO;
		}

		BigDecimal qtyToDeliverTU = qtyOrderedTU.subtract(qtyDeliveredTU);
		if (qtyToDeliverTU.signum() < 0)
		{
			qtyToDeliverTU = BigDecimal.ZERO;
		}

		target.setQtyToDeliver_LU(qtyToDeliverLU);
		target.setQtyToDeliver_TU(qtyToDeliverTU);
	}

	/**
	 * Copy HU quantities from <code>from</code> to <code>to</code>.
	 *
	 * @param to
	 * @param from
	 */
	public static void copy(final IHUDeliveryQuantities to, final IHUDeliveryQuantities from)
	{
		Check.assumeNotNull(to, "to not null");
		Check.assumeNotNull(from, "from not null");

		to.setQtyOrdered_LU(from.getQtyOrdered_LU());
		to.setQtyToDeliver_LU(from.getQtyToDeliver_LU());
		to.setQtyDelivered_LU(from.getQtyDelivered_LU());

		to.setQtyOrdered_TU(from.getQtyOrdered_TU());
		to.setQtyToDeliver_TU(from.getQtyToDeliver_TU());
		to.setQtyDelivered_TU(from.getQtyDelivered_TU());
	}

	/**
	 *
	 * @param bd1
	 * @param bd2
	 * @return bd1 + bd2
	 */
	private static final BigDecimal add(final BigDecimal bd1, final BigDecimal bd2)
	{
		BigDecimal result;
		if (bd1 != null)
		{
			result = bd1;
		}
		else
		{
			result = BigDecimal.ZERO;
		}

		if (bd2 != null)
		{
			result = result.add(bd2);
		}

		return result;
	}

	/**
	 *
	 * @param bd1
	 * @param bd2
	 * @return bd1 - bd2
	 */
	private static final BigDecimal subtract(final BigDecimal bd1, final BigDecimal bd2)
	{
		BigDecimal result;
		if (bd1 != null)
		{
			result = bd1;
		}
		else
		{
			result = BigDecimal.ZERO;
		}

		if (bd2 != null)
		{
			result = result.subtract(bd2);
		}

		return result;
	}
}
