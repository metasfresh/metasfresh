package de.metas.material.dispo.commons.candidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.util.IdConstants;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.compiere.util.Util;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CandidateId implements RepoIdAware
{
	/**
	 * Use this constant in a {@link CandidatesQuery} to indicate that the ID shall not be considered.
	 */
	public static final CandidateId UNSPECIFIED = CandidateId.ofRepoId(IdConstants.UNSPECIFIED_REPO_ID);

	/**
	 * Use this constant in a {@link CandidatesQuery} to indicate that the ID be null (makes sense for parent-ID).
	 */
	public static final CandidateId NULL = CandidateId.ofRepoId(IdConstants.NULL_REPO_ID);

	int repoId;

	@JsonCreator
	public static CandidateId ofRepoId(final int repoId)
	{
		return new CandidateId(repoId);
	}

	public static CandidateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CandidateId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CandidateId candidateId)
	{
		return candidateId != null ? candidateId.getRepoId() : -1;
	}

	public static boolean isNull(@Nullable final CandidateId id)
	{
		if (id == null)
		{
			return true;
		}
		return id.isNull();
	}

	private CandidateId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		if (isUnspecified())
		{
			Check.fail("Illegal call of getRepoId() on the unspecified CandidateId instance-");
		}
		else if (isNull())
		{
			return -1;
		}

		return repoId;
	}

	public boolean isNull()
	{
		return repoId == IdConstants.NULL_REPO_ID;
	}

	public boolean isUnspecified()
	{
		return repoId == IdConstants.UNSPECIFIED_REPO_ID;
	}

	public static boolean isRegularNonNull(@Nullable final CandidateId candidateId)
	{
		return candidateId != null
				&& !candidateId.isUnspecified()
				&& !candidateId.isNull();
	}

	public static boolean equals(@Nullable CandidateId id1, @Nullable CandidateId id2) {return Util.equals(id1, id2);}
}
