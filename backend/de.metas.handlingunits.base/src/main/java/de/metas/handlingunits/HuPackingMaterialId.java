/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
@Value
public class HuPackingMaterialId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static HuPackingMaterialId ofRepoId(final int repoId)
	{
		return new HuPackingMaterialId(repoId);
	}

	@Nullable
	public static HuPackingMaterialId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static int toRepoId(@Nullable final HuPackingMaterialId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	private HuPackingMaterialId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_HU_PackingMaterial_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(final HuPackingMaterialId id1, final HuPackingMaterialId id2)
	{
		return Objects.equals(id1, id2);
	}

}
