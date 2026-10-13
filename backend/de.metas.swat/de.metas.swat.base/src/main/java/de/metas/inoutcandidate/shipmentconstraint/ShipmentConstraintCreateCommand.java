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

import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Input for {@link ShipmentConstraintService#createConstraint(ShipmentConstraintCreateCommand)}.
 * <p>
 * Carries the translatable {@code reason}: the service resolves to base language before
 * persisting on the {@code DeliveryStopReason} column.
 */
@Value
public class ShipmentConstraintCreateCommand
{
	@NonNull OrgId orgId;
	@NonNull BPartnerId billBPartnerId;
	@NonNull SourceDocRef sourceDocRef;
	boolean deliveryStop;
	@Nullable ITranslatableString reason;

	@Builder
	private ShipmentConstraintCreateCommand(
			@NonNull final OrgId orgId,
			@NonNull final BPartnerId billBPartnerId,
			@NonNull final SourceDocRef sourceDocRef,
			final boolean deliveryStop,
			@Nullable final ITranslatableString reason)
	{
		Preconditions.checkArgument(deliveryStop, "at least one constraint shall be set");

		this.orgId = orgId;
		this.billBPartnerId = billBPartnerId;
		this.sourceDocRef = sourceDocRef;
		this.deliveryStop = deliveryStop;
		this.reason = reason;
	}
}
