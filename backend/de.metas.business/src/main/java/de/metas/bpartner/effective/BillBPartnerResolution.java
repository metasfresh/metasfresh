/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.effective;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Resolved bill-to partner, location, and (optionally) user for a sales order.
 */
@Value
public class BillBPartnerResolution
{
	@NonNull BPartnerId billBPartnerId;
	@Nullable BPartnerLocationId billLocationId;
	@Nullable UserId billUserId;

	public static BillBPartnerResolution of(
			@NonNull final BPartnerId billBPartnerId,
			@Nullable final BPartnerLocationId billLocationId,
			@Nullable final UserId billUserId)
	{
		return new BillBPartnerResolution(billBPartnerId, billLocationId, billUserId);
	}
}
