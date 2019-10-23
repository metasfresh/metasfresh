package de.metas.impexp.format;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
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
public class ImpFormatId implements RepoIdAware
{
	@JsonCreator
	public static ImpFormatId ofRepoId(final int repoId)
	{
		return new ImpFormatId(repoId);
	}

	public static ImpFormatId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ImpFormatId(repoId) : null;
	}

	int repoId;

	private ImpFormatId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_ImpFormat_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final ImpFormatId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
