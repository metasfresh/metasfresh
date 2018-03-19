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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.handlingunits.model.IHUDeliveryQuantities;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import lombok.NonNull;

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
	}

	/**
	 * Adds quantities from <code>qtysToAdd</code> to <code>target</code> quantities.
	 *
	 * @param target
	 * @param qtysToAdd
	 */
	public static void addTo(
			@NonNull final IHUDeliveryQuantities target,
			@NonNull final IHUDeliveryQuantities qtysToAdd)
	{
		{
			final BigDecimal qtyOrderedLU = target.getQtyOrdered_LU();
			target.setQtyOrdered_LU(add(qtyOrderedLU, qtysToAdd.getQtyOrdered_LU()));
		}
		{
			final BigDecimal qtyOrderedTU = target.getQtyOrdered_TU();
			target.setQtyOrdered_TU(add(qtyOrderedTU, qtysToAdd.getQtyOrdered_TU()));
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
	public static void removeFrom(
			@NonNull final IHUDeliveryQuantities target,
			@NonNull final IHUDeliveryQuantities qtysToRemove)
	{
		{
			final BigDecimal qtyOrderedLU = target.getQtyOrdered_LU();
			target.setQtyOrdered_LU(subtract(qtyOrderedLU, qtysToRemove.getQtyOrdered_LU()));
		}
		{
			final BigDecimal qtyOrderedTU = target.getQtyOrdered_TU();
			target.setQtyOrdered_TU(subtract(qtyOrderedTU, qtysToRemove.getQtyOrdered_TU()));
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
	public static void adjust(
			@NonNull final IHUDeliveryQuantities target,
			@Nullable final IHUDeliveryQuantities qtysToRemove,
			@Nullable final IHUDeliveryQuantities qtysToAdd)
	{
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
	 * Copy HU quantities from <code>from</code> to <code>to</code>.
	 *
	 * @param to
	 * @param shipmentSchedule
	 */
	public static void copy(
			@NonNull final IHUDeliveryQuantities to,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedList = //
				Services.get(IQueryBL.class)
						.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
						.create()
						.list();

		BigDecimal qtyDeliveredLU = BigDecimal.ZERO;
		BigDecimal qtyDeliveredTU = BigDecimal.ZERO;
		for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentScheduleQtyPickedList)
		{
			qtyDeliveredLU = add(qtyDeliveredLU, shipmentScheduleQtyPicked.getQtyLU());
			qtyDeliveredTU = add(qtyDeliveredTU, shipmentScheduleQtyPicked.getQtyTU());
		}

		to.setQtyOrdered_LU(shipmentSchedule.getQtyOrdered_LU());
		to.setQtyDelivered_LU(qtyDeliveredLU);

		to.setQtyOrdered_TU(shipmentSchedule.getQtyOrdered_TU());
		to.setQtyDelivered_TU(qtyDeliveredTU);
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
