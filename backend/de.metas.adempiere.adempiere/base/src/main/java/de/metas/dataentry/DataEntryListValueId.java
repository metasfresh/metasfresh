package de.metas.dataentry;

import static de.metas.util.Check.assumeGreaterThanZero;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class DataEntryListValueId implements RepoIdAware
{
	public static DataEntryListValueId ofRepoId(final int repoId)
	{
		return new DataEntryListValueId(repoId);
	}

	public static DataEntryListValueId ofRepoIdOrNull(final int repoId)
	{
		if (repoId <= 0)
		{
			return null;
		}
		return new DataEntryListValueId(repoId);
	}

	public static int getRepoId(@Nullable final DataEntryListValueId dataEntryListValueId)
	{
		if (dataEntryListValueId == null)
		{
			return 0;
		}
		return dataEntryListValueId.getRepoId();
	}

	int repoId;

	@JsonCreator
	public DataEntryListValueId(final int repoId)
	{
		this.repoId = assumeGreaterThanZero(repoId, "repoId");
	}

	@Override
	@JsonValue // note: annotating just the repoId member worked "often" which was very annoying
	public int getRepoId()
	{
		return repoId;
	}
}
