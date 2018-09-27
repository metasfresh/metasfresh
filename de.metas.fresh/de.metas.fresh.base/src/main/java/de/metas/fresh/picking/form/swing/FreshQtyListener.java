/**
 *
 */
package de.metas.fresh.picking.form.swing;

/*
 * #%L
 * de.metas.fresh.base
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

import de.metas.fresh.picking.PickingSlotKey;
import de.metas.fresh.picking.ProductKey;
import de.metas.util.collections.IdentityHashSet;
import lombok.NonNull;

/**
 * This listener only prevents the user from selecting a qty which is bigger than the actually available Qty from the current product's packing item.
 *
 * @author cg
 *
 */
class FreshQtyListener implements PropertyChangeListener
{
	private static final String ERR_Fresh_QTY_LISTENER_NEG_QTY = "@de.metas.fresh.picking.form.swing.FreshQtyListener.Neg_Qty@";

	private final FreshSwingPackageItems packageItems;
	private final IdentityHashSet<Object> activeComponents = new IdentityHashSet<>();

	FreshQtyListener(@NonNull final FreshSwingPackageItems packageItems)
	{
		this.packageItems = packageItems;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final Object source = evt.getSource();
		if (!activeComponents.add(source))
		{
			// avoid recursive calls, do nothing
			return;
		}

		try
		{
			propertyChange0(evt);
		}
		finally
		{
			activeComponents.remove(source);
		}
	}

	public void propertyChange0(final PropertyChangeEvent evt)
	{
		// If new value is null or value not changed, we do nothing
		if (evt.getNewValue() == null || evt.getNewValue().equals(evt.getOldValue()))
		{
			return;
		}

		final PickingSlotKey selectedSlot = packageItems.getSelectedPickingSlotKey();
		if (selectedSlot == null)
		{
			return;
		}

		final ProductKey selectedProduct = packageItems.getSelectedProduct();
		if (selectedProduct == null)
		{
			return;
		}

		final BigDecimal oldQty;
		final BigDecimal newQty;
		if (evt.getNewValue() instanceof Long)
		{
			newQty = new BigDecimal((Long)evt.getNewValue());
			oldQty = new BigDecimal((Long)evt.getOldValue());
		}
		else
		{
			newQty = (BigDecimal)evt.getNewValue();
			oldQty = (BigDecimal)evt.getOldValue();
		}

		//
		// In case the new quantity is not valid (i.e. it's negative)
		// put back the old quantity and warn the user.
		if (newQty.signum() < 0)
		{
			packageItems.warn(ERR_Fresh_QTY_LISTENER_NEG_QTY);
			packageItems.setQty(oldQty);
			return;
		}
	}
}
