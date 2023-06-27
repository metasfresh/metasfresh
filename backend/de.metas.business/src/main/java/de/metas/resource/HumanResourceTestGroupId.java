/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_S_HumanResourceTestGroup;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class HumanResourceTestGroupId implements RepoIdAware
{
	@JsonCreator
	public static HumanResourceTestGroupId ofRepoId(final int repoId)
	{
		return new HumanResourceTestGroupId(repoId);
	}

	@Nullable
	public static HumanResourceTestGroupId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new HumanResourceTestGroupId(repoId) : null;
	}

	public static Optional<HumanResourceTestGroupId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final HumanResourceTestGroupId humanResourceTestGroupId)
	{
		return humanResourceTestGroupId != null ? humanResourceTestGroupId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final HumanResourceTestGroupId o1, @Nullable final HumanResourceTestGroupId o2)
	{
		return Objects.equals(o1, o2);
	}

	int repoId;

	private HumanResourceTestGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_S_HumanResourceTestGroup.COLUMNNAME_S_HumanResourceTestGroup_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
