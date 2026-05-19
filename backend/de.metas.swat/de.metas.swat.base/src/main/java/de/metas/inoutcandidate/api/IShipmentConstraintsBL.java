package de.metas.inoutcandidate.api;

import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IShipmentConstraintsBL extends ISingletonService
{
	void createConstraint(@NonNull ShipmentConstraintCreateRequest request);

	int getDeliveryStopShipmentConstraintId(int bpartnerId);

	/**
	 * Creates or updates the manual delivery-stop constraint mirroring the BPartner's flag.
	 * - If a manual constraint row exists: updates the reason and (re)activates it.
	 * - Otherwise creates a new manual constraint row.
	 *
	 * @param adOrgId        organization of the BPartner (used when creating a fresh constraint)
	 */
	void createOrUpdateManualDeliveryStop(int billBPartnerId, int adOrgId, @Nullable String reason);

	/**
	 * Deactivates the manual delivery-stop constraint for the given BPartner (no-op if not present or already inactive).
	 */
	void deactivateManualDeliveryStop(int billBPartnerId);
}
