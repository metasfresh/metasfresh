package de.metas.handlingunits.shipmentschedule.api;

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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.shipmentschedule.picked_tu.PickedHUsBySchedule;
import de.metas.handlingunits.shipmentschedule.picked_tu.QtyPickedRecords;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The rows to create shipment candidates for, and what is needed to do so.
 */
@Value
@Builder
public class CreateCandidatesRequest
{
	@NonNull QtyPickedRecords qtyPickedRecords;
	@NonNull IHUContext huContext;
	@NonNull M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse;
	/**
	 * The HUs that the picking of each schedule recorded as picked; if a schedule has some, only those CUs of a TU count.
	 * Empty if nothing is known: then every CU of a TU counts.
	 */
	@NonNull @Builder.Default PickedHUsBySchedule pickedHuIdsBySchedule = PickedHUsBySchedule.EMPTY;
}
