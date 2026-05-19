package de.metas.inoutcandidate.api;

import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IShipmentConstraintsDAO extends ISingletonService
{
	/**
	 * Retrieves the manual {@link I_M_Shipment_Constraint} (IsManual=Y) for the given Bill-BPartner,
	 * regardless of its IsActive flag. The active-filter is intentionally omitted so that re-toggling
	 * the BPartner's delivery-stop flag re-activates the existing row instead of creating a new one.
	 */
	@Nullable
	I_M_Shipment_Constraint retrieveManualConstraint(int billBPartnerId);

	/**
	 * Updates {@link de.metas.inoutcandidate.model.I_M_ReceiptSchedule#COLUMNNAME_IsDeliveryStop} for all
	 * unprocessed receipt schedules of the given BPartner whose flag currently differs from {@code isDeliveryStop}.
	 *
	 * @return number of receipt schedules updated
	 */
	int updateReceiptScheduleDeliveryStopForBPartner(int bpartnerId, boolean isDeliveryStop);
}
