package de.metas.location;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class CountryAreaId implements RepoIdAware
{

	@JsonCreator
	@NonNull
	public static CountryAreaId ofRepoId(final int repoId)
	{
		return new CountryAreaId(repoId);
	}

	@Nullable
	public static CountryAreaId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new CountryAreaId(repoId) : null;
	}

	public static int toRepoId(@Nullable final CountryAreaId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	int repoId;

	private CountryAreaId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_CountryArea_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static boolean equals(@Nullable final CountryAreaId countryAreaId1, @Nullable final CountryAreaId countryAreaId2)
	{
		return Objects.equals(countryAreaId1, countryAreaId2);
	}

	public boolean equalsToRepoId(final int repoId)
	{
		return this.repoId == repoId;
	}
}
