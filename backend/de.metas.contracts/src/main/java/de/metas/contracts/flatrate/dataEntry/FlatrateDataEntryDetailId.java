package de.metas.contracts.flatrate.dataEntry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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
public class FlatrateDataEntryDetailId implements RepoIdAware
{
	@JsonCreator
	public static FlatrateDataEntryDetailId ofRepoId(@NonNull final FlatrateDataEntryId dataEntryId, final int repoId)
	{
		return new FlatrateDataEntryDetailId(dataEntryId, repoId);
	}

	@Nullable
	public static FlatrateDataEntryDetailId ofRepoIdOrNull(@NonNull final FlatrateDataEntryId dataEntryId, final int repoId)
	{
		return repoId > 0 ? new FlatrateDataEntryDetailId(dataEntryId, repoId) : null;
	}

	public static int toRepoId(@Nullable final FlatrateDataEntryDetailId dataEntryDetailId)
	{
		return dataEntryDetailId != null ? dataEntryDetailId.getRepoId() : -1;
	}

	FlatrateDataEntryId dataEntryId;

	int repoId;

	private FlatrateDataEntryDetailId(@NonNull final FlatrateDataEntryId dataEntryId, final int repoId)
	{
		this.dataEntryId = dataEntryId;
		this.repoId = Check.assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
