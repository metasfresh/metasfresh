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

package de.metas.shipper.gateway.spi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

// FIXME why we call it DeliveryOrderParcelId when it shall be DeliveryOrderParcelId?
@Value
public class DeliveryOrderParcelId implements RepoIdAware
{
	int repoId;

	private DeliveryOrderParcelId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DeliveryOrderParcelId");
	}

	@JsonCreator
	public static DeliveryOrderParcelId ofRepoId(final int repoId) {return new DeliveryOrderParcelId(repoId);}

	@Nullable
	public static DeliveryOrderParcelId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<DeliveryOrderParcelId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final DeliveryOrderParcelId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final DeliveryOrderParcelId id1, @Nullable final DeliveryOrderParcelId id2) {return Objects.equals(id1, id2);}
}
