/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.sectionCode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_M_SectionCode;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class SectionCodeId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static SectionCodeId ofRepoId(final int repoId)
	{
		return new SectionCodeId(repoId);
	}

	@Nullable
	public static SectionCodeId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new SectionCodeId(repoId) : null;
	}

	@Nullable
	public static SectionCodeId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new SectionCodeId(repoId) : null;
	}

	public static Optional<SectionCodeId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final SectionCodeId sectionCodeId)
	{
		return toRepoIdOr(sectionCodeId, -1);
	}

	public static int toRepoIdOr(@Nullable final SectionCodeId sectionCodeId, final int defaultValue)
	{
		return sectionCodeId != null ? sectionCodeId.getRepoId() : defaultValue;
	}

	private SectionCodeId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_M_SectionCode.COLUMNNAME_M_SectionCode_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
