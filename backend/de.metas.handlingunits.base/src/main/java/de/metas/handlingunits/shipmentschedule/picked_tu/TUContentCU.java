package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

/**
 * A CU (virtual HU) inside a TU, with the quantity of the product it holds.
 */
@Value
public class TUContentCU
{
	@NonNull I_M_HU cu;
	/** in the product's stock UOM, like {@code M_ShipmentSchedule_QtyPicked.QtyPicked} */
	@NonNull Quantity qty;

	/**
	 * The rows' {@code QtyPicked} is in the stock UOM, so the CU counts in the stock UOM, whatever UOM its storage is kept in.
	 */
	public static TUContentCU ofStorage(@NonNull final IHUProductStorage storage)
	{
		return new TUContentCU(storage.getM_HU(), storage.getQtyInStockingUOM());
	}
}

