/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;

import javax.annotation.Nullable;
import java.util.Objects;

public class ShipperTransportationId implements RepoIdAware
{
	@JsonCreator
	public static ShipperTransportationId ofRepoId(final int repoId)
	{
		return new ShipperTransportationId(repoId);
	}

	@Nullable
	public static ShipperTransportationId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ShipperTransportationId(repoId) : null;
	}

	public static int toRepoId(@Nullable final ShipperTransportationId shipperTransportationId)
	{
		return shipperTransportationId != null ? shipperTransportationId.getRepoId() : -1;
	}

	int repoId;

	private ShipperTransportationId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_ShipperTransportation_id");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final OrderId id1, @Nullable final OrderId id2)
	{
		return Objects.equals(id1, id2);
	}
}