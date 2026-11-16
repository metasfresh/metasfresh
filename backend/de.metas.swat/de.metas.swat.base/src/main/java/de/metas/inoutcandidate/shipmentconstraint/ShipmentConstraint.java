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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.inoutcandidate.shipmentconstraint;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.ShipmentConstraintId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

/**
 * Immutable domain object backing {@code M_Shipment_Constraint}.
 * <p>
 * Owned by {@link ShipmentConstraintRepository}. Mutating callers go through
 * {@link ShipmentConstraintService}; record-style manipulation is forbidden.
 */
@Value
@Builder(toBuilder = true)
public class ShipmentConstraint
{
	@Nullable @With ShipmentConstraintId id;
	@NonNull OrgId orgId;
	@NonNull BPartnerId billBPartnerId;
	boolean active;
	boolean deliveryStop;
	boolean manual;
	@Nullable String deliveryStopReason;
	@Nullable SourceDocRef sourceDocRef;
}
