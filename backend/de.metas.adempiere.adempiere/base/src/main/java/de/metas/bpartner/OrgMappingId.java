/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class OrgMappingId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static OrgMappingId ofRepoId(final int repoId)
	{
		return new OrgMappingId(repoId);
	}

	@Nullable
	public static OrgMappingId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new OrgMappingId(repoId) : null;
	}

	@Nullable
	public static OrgMappingId ofRepoIdOrNull(@Nullable final int repoId)
	{
		return repoId > 0 ? new OrgMappingId(repoId) : null;
	}

	public static int toRepoId(@Nullable final OrgMappingId orgMappingId)
	{
		return toRepoIdOr(orgMappingId, -1);
	}

	public static int toRepoIdOr(@Nullable final OrgMappingId orgMappingId, final int defaultValue)
	{
		return orgMappingId != null ? orgMappingId.getRepoId() : defaultValue;
	}

	private OrgMappingId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Org_Mapping_ID");
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

	public static boolean equals(final OrgMappingId o1, final OrgMappingId o2)
	{
		return Objects.equals(o1, o2);
	}
}
