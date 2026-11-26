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

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Reads the catch weight that a {@code M_ShipmentSchedule_QtyPicked} row carries.
 */
final class PickedRowCatchWeight
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	/**
	 * @return the weight of the row in its catch UOM; {@code null} if the row has no catch UOM
	 */
	@Nullable
	Quantity extractOrNull(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		if (row.getCatch_UOM_ID() <= 0)
		{
			return null;
		}
		return Quantity.of(row.getQtyDeliveredCatch(), uomDAO.getById(UomId.ofRepoId(row.getCatch_UOM_ID())));
	}
}
