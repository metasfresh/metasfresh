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

package de.metas.shipping;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * What {@link MPackageRepository#create(MPackageCreateRequest)} writes onto a new {@code M_Package}.
 * <p>
 * Every field is optional because {@code M_Package} carries no mandatory reference of its own: the caller that
 * knows a value passes it, and a caller that does not leaves the column empty - which is what the previous
 * in-line {@code newInstance(I_M_Package.class)} did too.
 */
@Value
@Builder
public class MPackageCreateRequest
{
	@Nullable ShipperId shipperId;

	@Nullable Timestamp shipDate;

	@Nullable BPartnerId bpartnerId;

	@Nullable BPartnerLocationId bpartnerLocationId;
}
