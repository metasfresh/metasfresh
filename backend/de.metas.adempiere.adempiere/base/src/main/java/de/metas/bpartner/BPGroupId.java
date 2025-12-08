package de.metas.bpartner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class BPGroupId implements RepoIdAware
{
	public static final BPGroupId STANDARD = new BPGroupId(1000000);

	int repoId;

	private BPGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_BP_Group_ID");
	}

	@JsonCreator
	public static BPGroupId ofRepoId(final int repoId)
	{
		final BPGroupId id = ofRepoIdOrNull(repoId);
		if (id == null)
		{
			throw new AdempiereException("Invalid C_BP_Group_ID: " + repoId);
		}
		return id;
	}

	@Nullable
	public static BPGroupId ofRepoIdOrNull(final int repoId)
	{
		if (repoId == STANDARD.repoId)
		{
			return STANDARD;
		}
		else if (repoId <= 0)
		{
			return null;
		}
		else
		{
			return new BPGroupId(repoId);
		}
	}

	public static Optional<BPGroupId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final BPGroupId bpGroupId)
	{
		return bpGroupId != null ? bpGroupId.getRepoId() : -1;
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(@Nullable final BPGroupId o1, @Nullable final BPGroupId o2)
	{
		return Objects.equals(o1, o2);
	}
}
