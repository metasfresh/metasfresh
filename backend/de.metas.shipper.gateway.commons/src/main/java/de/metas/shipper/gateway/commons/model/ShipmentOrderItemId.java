/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class ShipmentOrderItemId implements RepoIdAware
{
	int repoId;

	private ShipmentOrderItemId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "Shipper_ShipmentOrder_Parcel_ID");
	}

	@JsonCreator
	public static ShipmentOrderItemId ofRepoId(final int repoId) {return new ShipmentOrderItemId(repoId);}

	@Nullable
	public static ShipmentOrderItemId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<ShipmentOrderItemId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final ShipmentOrderItemId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final ShipmentOrderItemId id1, @Nullable final ShipmentOrderItemId id2) {return Objects.equals(id1, id2);}
}
