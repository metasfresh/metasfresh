package de.metas.handlingunits.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.metas.util.Check.assumeGreaterThanZero;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class InventoryLineHUId implements RepoIdAware
{
	int repoId;

	private InventoryLineHUId(int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "M_InventoryLine_HU_ID");
	}

	public static InventoryLineHUId ofRepoId(int repoId)
	{
		return new InventoryLineHUId(repoId);
	}

	@Nullable
	public static InventoryLineHUId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@JsonCreator
	@Nullable
	public static InventoryLineHUId ofNullableObject(@Nullable final Object obj)
	{
		return RepoIdAwares.ofObjectOrNull(obj, InventoryLineHUId.class, InventoryLineHUId::ofRepoIdOrNull);
	}

	@NonNull
	public static InventoryLineHUId ofObject(@NonNull final Object obj)
	{
		return RepoIdAwares.ofObject(obj, InventoryLineHUId.class, InventoryLineHUId::ofRepoId);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
	
	public static boolean equals(final InventoryLineHUId id1, final InventoryLineHUId id2) { return Objects.equals(id1, id2);}
}
